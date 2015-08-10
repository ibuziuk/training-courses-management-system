package org.exadel.training.controller.rest;

import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.model.Notification;
import org.exadel.training.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class NotificationRestController {
    @Autowired
    private NotificationService notificationService;

    private final Map<DeferredResult<List<Notification>>, Long> chatRequests = new ConcurrentHashMap<>();

    @RequestMapping(value = "/rest/notification/{notificationIndex}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public DeferredResult<List<Notification>> getAllNotificationByUser(@PathVariable long notificationIndex) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        final DeferredResult<List<Notification>> deferredResult = new DeferredResult<>(100000L, Collections.emptyList());
        this.chatRequests.put(deferredResult, notificationIndex);

        deferredResult.onCompletion(() -> chatRequests.remove(deferredResult));

        List<Notification> notifications = notificationService.getAllNotificationsByUser(userId, notificationIndex);
        if (!notifications.isEmpty()) {
            deferredResult.setResult(notifications);
        }

        return deferredResult;
    }

    @RequestMapping(value = "/rest/notification/delete/{notificationId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotification(@PathVariable("notificationId") long notificationId) {
        notificationService.removeNotification(notificationService.getNotificationById(notificationId));
    }
}
