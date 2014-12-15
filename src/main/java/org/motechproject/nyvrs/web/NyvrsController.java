package org.motechproject.nyvrs.web;

import org.apache.commons.io.IOUtils;
import org.motechproject.nyvrs.service.ClientRegistrationService;
import org.motechproject.nyvrs.service.NyvrsService;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Controller for NYVRS
 */
@Controller
@RequestMapping("/web-api")
public class NyvrsController {

    @Autowired
    ClientRegistrationService clientRegistrationService;

    @Autowired
    private SettingsFacade settingsFacade;

    @Autowired
    private NyvrsService nyvrsService;

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

}
