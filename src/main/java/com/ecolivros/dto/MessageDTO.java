package com.ecolivros.dto;

import com.ecolivros.entity.Message;
import java.time.LocalDateTime;

public class MessageDTO {

    public record MessageRequest(Long receiverId, String text) {}

    public record MessageResponse(
            Long id,
            Long senderId,
            String senderName,
            Long receiverId,
            String receiverName,
            String text,
            LocalDateTime sentAt
    ) {
        public static MessageResponse from(Message m) {
            return new MessageResponse(
                    m.getId(),
                    m.getSender().getId(),
                    m.getSender().getName(),
                    m.getReceiver().getId(),
                    m.getReceiver().getName(),
                    m.getText(),
                    m.getSentAt()
            );
        }
    }

    public record ContactResponse(Long id, String name, String avatarUrl) {}
}
