package com.ecolivros.service;

import com.ecolivros.dto.MessageDTO.*;
import com.ecolivros.entity.Message;
import com.ecolivros.repository.MessageRepository;
import com.ecolivros.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageResponse send(String senderEmail, MessageRequest request) {
        var sender = userRepository.findByEmail(senderEmail).orElseThrow();
        var receiver = userRepository.findById(request.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Destinatário não encontrado"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(request.text());

        return MessageResponse.from(messageRepository.save(message));
    }

    public List<MessageResponse> getConversation(String email, Long otherUserId) {
        var user = userRepository.findByEmail(email).orElseThrow();
        return messageRepository.findConversation(user.getId(), otherUserId)
                .stream().map(MessageResponse::from).toList();
    }

    public List<ContactResponse> getContacts(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        return messageRepository.findContacts(user.getId())
                .stream().map(u -> new ContactResponse(u.getId(), u.getName())).toList();
    }
}
