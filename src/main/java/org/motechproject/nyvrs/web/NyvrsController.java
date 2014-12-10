package org.motechproject.nyvrs.web;

import org.motechproject.nyvrs.service.NyvrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for NYVRS
 */
@Controller
public class NyvrsController {

    @Autowired
    private NyvrsService nyvrsService;

    private static final String OK = "OK";

    @RequestMapping("/web-api/status")
    @ResponseBody
    public String status() {
        return OK;
    }
}
