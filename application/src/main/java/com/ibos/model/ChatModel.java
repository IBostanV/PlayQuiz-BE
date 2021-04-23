package com.ibos.model;

import org.springframework.data.annotation.Id;

public class ChatModel {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
}
