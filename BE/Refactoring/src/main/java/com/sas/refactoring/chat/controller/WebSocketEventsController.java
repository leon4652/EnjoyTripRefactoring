package com.sas.refactoring.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Controller
public class WebSocketEventsController {
    private Set<String> connectedUsers = ConcurrentHashMap.newKeySet();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleSessionConnectedEvent(SessionConnectedEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        connectedUsers.add(sessionId);
        System.out.println("User Connected. Total Users: " + connectedUsers.size());

        simpMessagingTemplate.convertAndSend("/connected", connectedUsers.size());
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        connectedUsers.remove(sessionId);
        System.out.println("User Disconnected. Total Users: " + connectedUsers.size());

        simpMessagingTemplate.convertAndSend("/connected", connectedUsers.size());
    }
}

