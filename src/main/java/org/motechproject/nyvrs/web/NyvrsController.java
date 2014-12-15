package org.motechproject.nyvrs.web;

import org.apache.commons.io.IOUtils;
import org.motechproject.nyvrs.domain.ClientRegistration;
import org.motechproject.nyvrs.service.ClientRegistrationService;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Controller for NYVRS
 */
@Controller
@RequestMapping("/web-api")
public class NyvrsController {

    @Autowired
    private SettingsFacade settingsFacade;

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    private static final String OK = "OK";
    private static final Logger LOG = LoggerFactory.getLogger(NyvrsController.class);

    @RequestMapping("/status")
    @ResponseBody
    public String status() {
        return OK;
    }

    @RequestMapping("/manifest")
    @ResponseBody
    public String manifest(HttpServletRequest request) throws IOException {

        String manifestFileName = settingsFacade
                .getProperty("verboice.manifest.file");
        InputStream manifest = settingsFacade.getRawConfig(manifestFileName);
        String manifestBody = IOUtils.toString(manifest);

        String callbackBaseUrl = settingsFacade.getProperty("motech.base.url")
                + settingsFacade.getProperty("verboice.callback.path");

        return manifestBody.replace("URL_BASE", callbackBaseUrl);
    }

    @RequestMapping(value = "/isRegistered")
    @ResponseBody
    public String isRegistered(HttpServletRequest request) throws IOException {
        String number = request.getParameter("number");

        if (number == null) {
            LOG.info("The request doesn't contain number attribute");
            return "The request doesn't contain number attribute";
        }

        if (clientRegistrationService.findClientRegistrationByNumber(number) != null) {
            LOG.info("The client with number " + number + " is registered in NYVRS");
            return "{ isRegistered: true }";
        } else {
            LOG.info("The client with number " + number + " is NOT registered in NYVRS");
            return "{ isRegistered: false }";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest registrationRequest) {

        List<ValidationError> errors = registrationRequest.validate();
        if (errors.isEmpty() && clientRegistrationService.findClientRegistrationByNumber(
                registrationRequest.getCallerId().toString()) != null) {
            errors.add(new ValidationError("Already registered"));
        }

        if (errors.isEmpty()) {
            ClientRegistration clientRegistration = new ClientRegistration(registrationRequest.getCallerId().toString(),
                    registrationRequest.getGender().getValue(), registrationRequest.getAge().toString(),
                    registrationRequest.getEducationLevel(), registrationRequest.getChannel());
            clientRegistrationService.add(clientRegistration);
            return new ResponseEntity<Object>("Registered successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(errors, HttpStatus.OK);
        }
    }

}
