package com.miumg.chatbottelegram.Service;


import com.miumg.chatbottelegram.model.ChatMessage;
import com.miumg.chatbottelegram.repository.ChatMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public boolean deleteById(Long productId) {
        //return products.removeIf(p -> p.getProductId().equals(productId));
        if(chatMessageRepository.existsById(productId)) {
            chatMessageRepository.deleteById(productId);
            return true;
        }
        return false;
    }

}
