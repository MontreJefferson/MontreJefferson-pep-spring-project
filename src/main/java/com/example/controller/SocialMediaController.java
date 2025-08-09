package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.*;

import com.example.entity.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.accountService = accountService;
        this.messageService = messageService;
    }



    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account pojo){
        if(accountService.getAccountByUsername(pojo.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        if(pojo.getUsername().length() > 0 && pojo.getPassword().length() >= 4){
            Account account = new Account(pojo.getUsername(), pojo.getPassword());
            return ResponseEntity.status(200)
            .body(accountService.persistAccount(account));
        }
        return ResponseEntity.status(400).body(null);

    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account pojo){
        if(accountService.getAccountByUsernameAndPassword(pojo.getUsername(), pojo.getPassword()) != null){
            return ResponseEntity.status(200)
            .body(accountService.getAccountByUsernameAndPassword(pojo.getUsername(), pojo.getPassword()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/accounts/{accountId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessagesByAccountId(@PathVariable Integer accountId){
        return messageService.getAllMessagesByPostedBy(accountId);
    }

    @GetMapping("/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public Message getMessageById(@PathVariable Integer messageId){
        if(messageService.getMessageByMessageId(messageId) != null){
            return messageService.getMessageByMessageId(messageId);
        }
        return null;
    }

    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message pojo){
        if(pojo.getMessageText().length() > 0 && pojo.getMessageText().length() <= 255 && accountService.getAccountByAccountId(pojo.getPostedBy()) != null){
            return ResponseEntity.status(200)
            .body(messageService.createNewMessage(pojo.getPostedBy(), pojo.getMessageText(), pojo.getTimePostedEpoch()));
        }
        return ResponseEntity.status(400).body(null);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId){
        if(messageService.getMessageByMessageId(messageId) != null){
            messageService.deleteMessage(messageId);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable Integer messageId, @RequestBody Message message){
        Message oldMessage = messageService.getMessageByMessageId(messageId);
        if(oldMessage != null && message.getMessageText().length() > 0 && message.getMessageText().length() <= 255){
            messageService.updateMessage(messageId, message.getMessageText());
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body(null);
    }
}
