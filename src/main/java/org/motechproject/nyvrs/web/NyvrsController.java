package org.motechproject.nyvrs.web;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
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
    public ResponseEntity<String> isRegistered(HttpServletRequest request) throws IOException {
        String callerId = request.getParameter("callerId");

        if (callerId == null) {
            LOG.info("The request doesn't contain callerId parameter");
            return new ResponseEntity<>("The request doesn't callerId parameter", HttpStatus.BAD_REQUEST);
        }

        if (clientRegistrationService.findClientRegistrationByNumber(callerId) != null) {
            LOG.info("Client with callerId " + callerId + " is already registered in NYVRS");
            return new ResponseEntity<>("Already registered", HttpStatus.OK);
        } else {
            LOG.info("Client with callerId " + callerId + " is NOT registered in NYVRS");
            return new ResponseEntity<>("Client is not registered", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/register")
    public ResponseEntity<String> register(HttpServletRequest request) {
        String callerId = request.getParameter("callerId");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String educationLevel = request.getParameter("educationLevel");
        String channel = request.getParameter("channel");

        List<ValidationError> errors = new ArrayList<ValidationError>();
        RegistrationRequest registrationRequest = null;
        try {
            registrationRequest = new RegistrationRequest(callerId, age, gender, educationLevel, channel);
            errors = registrationRequest.validate();
            if (errors.isEmpty() && clientRegistrationService.findClientRegistrationByNumber(
                    registrationRequest.getCallerId().toString()) != null) {
                errors.add(new ValidationError("Already registered"));
            }
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationError("Invalid or missing arguments"));
        }

        if (registrationRequest != null && errors.isEmpty()) {
            ClientRegistration clientRegistration = new ClientRegistration(registrationRequest.getCallerId().toString(),
                    registrationRequest.getGender().getValue(), registrationRequest.getAge().toString(),
                    registrationRequest.getEducationLevel(), registrationRequest.getChannel());
            clientRegistrationService.add(clientRegistration);
            return new ResponseEntity<String>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(StringUtils.join(errors, ", "), HttpStatus.OK);
        }
    }

}
