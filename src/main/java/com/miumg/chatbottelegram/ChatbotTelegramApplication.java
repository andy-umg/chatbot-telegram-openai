package com.miumg.chatbottelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class ChatbotTelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotTelegramApplication.class, args);
    }

}
