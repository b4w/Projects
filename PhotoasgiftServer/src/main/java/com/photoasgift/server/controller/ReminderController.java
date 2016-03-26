package com.photoasgift.server.controller;

import com.photoasgift.server.entity.Remind;
import com.photoasgift.server.repository.RemindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Тестовый класс по devcalibre.
 * Является по сути сервлетом как в JavaEE.
 */

@RestController
@RequestMapping(value = "/reminder")
public class ReminderController {

    /**
     * Инициализируем наш объект (@ManagedProperty).
     */
    @Autowired
    private RemindRepository remindRepository;

    /**
     * RequestMapping - по какому url мы можем обратиться к методу.
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Remind getReminder() {
        List<Remind> list = remindRepository.findAll();
        return createMockRemind();
    }

    private Remind createMockRemind() {
        Remind remind = new Remind();
        remind.setId(1);
        remind.setRemindDate(new Date());
        remind.setTitle("My first remind");
        return remind;
    }


}
