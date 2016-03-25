package com.photoasgift.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Тестовый класс по devcalibre.
 * Является по сути сервлетом.
 */

@Controller
@RequestMapping (value = "/reminder")
public class ReminderController {

    /**
     * RequestMapping - по какому url мы можем обратиться к методу.
     * @return
     */
    @RequestMapping (value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getReminder(ModelMap model) {
        return "My reminder";
    }


}
