package com.example.repository;

import com.example.entity.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>{
    
    Message findMessageByMessageId(Integer messageId);

    List<Message> findAllMessagesByPostedBy(Integer postedBy);

    void deleteMessageByMessageId(Integer messageId);

}
