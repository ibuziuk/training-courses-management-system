package org.exadel.training.controller.rest;

import org.exadel.training.model.Notification;
import org.exadel.training.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationRestController {
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/rest/notification/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getAllNotificationByUser(@PathVariable("userId") long userId) {
        return notificationService.getAllNotificationsByUser(userId);
    }

    @RequestMapping(value = "/rest/notification/delete/{notificationId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotification(@PathVariable("notificationId") long notificationId) {
        notificationService.removeNotification(notificationService.getNotificationById(notificationId));
    }
}
