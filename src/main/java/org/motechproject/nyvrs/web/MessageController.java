package org.motechproject.nyvrs.web;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.motechproject.nyvrs.domain.ClientRegistration;
import org.motechproject.nyvrs.domain.MessageRequest;
import org.motechproject.nyvrs.domain.MessageRequestStatus;
import org.motechproject.nyvrs.domain.SettingsDto;
import org.motechproject.nyvrs.service.ClientRegistrationService;
import org.motechproject.nyvrs.service.MessageRequestService;
import org.motechproject.nyvrs.service.MessageService;
import org.motechproject.nyvrs.web.domain.RegistrationRequest;
import org.motechproject.nyvrs.web.domain.ValidationError;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/web-api/message")
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    private SettingsFacade settingsFacade;

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Autowired
    private MessageRequestService messageRequestService;

    public MessageController(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ResponseEntity<String> register(HttpServletRequest request) {
        String callerId = request.getParameter("callerId");
        Boolean isSuccessful = Boolean.valueOf(request.getParameter("isSuccessful"));

        if (StringUtils.isBlank(callerId) || isSuccessful == null) {
            return new ResponseEntity<String>("Missing parameters.", HttpStatus.OK);
        }
        ClientRegistration clientRegistration = clientRegistrationService.findClientRegistrationByNumber(callerId);
        MessageRequest messageRequest = messageRequestService.findActiveMessageRequestByCallerId(callerId);
        if (clientRegistration == null || messageRequest == null) {
            return new ResponseEntity<String>("Could not find message request for the given callerId.", HttpStatus.OK);
        }

        if (isSuccessful) {
            messageRequest.setStatus(MessageRequestStatus.COMPLETED);
            messageRequestService.update(messageRequest);
            clientRegistration.setNyWeeks(clientRegistration.getNyWeeks() + 1);
            clientRegistrationService.update(clientRegistration);
        } else {
            String callDir = settingsFacade.getProperty(SettingsDto.ASTERISK_CALL_DIR);
            File callFile = new File(callDir, callerId + ".call");
            if (callFile.exists()) {
                Pattern p = Pattern.compile("^StartRetry:\\s*(.*)$", Pattern.MULTILINE);
                try {
                    Matcher m = p.matcher(FileUtils.readFileToString(callFile));
                    if (m.find()) {
                        if (m.group(1).equals(String.valueOf(messageRequest.getRetryCount() - 1))) {
                            messageRequest.setStatus(MessageRequestStatus.FAILED);
                            messageRequestService.update(messageRequest);
                        }
                    } else {
                        messageRequest.setStatus(MessageRequestStatus.FAILED);
                        messageRequestService.update(messageRequest);
                    }
                } catch (IOException ioe) {
                    LOG.error("Could not open " + callFile.getAbsolutePath());
                }
            }
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
