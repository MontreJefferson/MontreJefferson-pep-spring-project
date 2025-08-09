package com.example.service;

import java.util.*;

import javax.transaction.Transactional;

import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message persistMessage(Message message){
        return messageRepository.save(message);
    } 

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByPostedBy(Integer postedBy){
        return messageRepository.findAllMessagesByPostedBy(postedBy);
    }

    public Message getMessageByMessageId(Integer messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }

    @Transactional
    public Message createNewMessage(Integer messageId, String messageText, Long timePostedEpoch){
        return persistMessage(new Message(messageId, messageText, timePostedEpoch));
    }

    @Transactional
    public void deleteMessage(Integer messageId){
        messageRepository.deleteMessageByMessageId(messageId);
    }

    @Transactional
    public void updateMessage(Integer messageId, String newMessageText){
    Message message = messageRepository.findMessageByMessageId(messageId);
        if(message != null){
            message.setMessageText(newMessageText);
            messageRepository.save(message);
        }
    }
}
