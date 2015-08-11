package org.exadel.training.controller.rest;

import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.model.Notification;
import org.exadel.training.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationRestController {
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/rest/notification/{notificationIndex}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getAllNotificationByUser(@PathVariable long notificationIndex) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        return notificationService.getAllNotificationsByUser(userId, notificationIndex);
    }

    @RequestMapping(value = "/rest/notification/{notificationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotification(@PathVariable("notificationId") long notificationId) {
        notificationService.removeNotification(notificationService.getNotificationById(notificationId));
    }
}
