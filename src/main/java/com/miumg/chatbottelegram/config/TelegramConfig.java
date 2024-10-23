package com.miumg.chatbottelegram.config;

import com.miumg.chatbottelegram.bot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Slf4j
public class TelegramConfig {

    @Bean
    public TelegramBot telegramBot(@Value("${bot.name}") String botName,
                       @Value("${bot.token}")String botToken){
        TelegramBot telegramBot = new TelegramBot(botName,botToken);
        try {
            var telegramBotApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotApi.registerBot(telegramBot);
        }catch (TelegramApiException e){
            log.error("Exception during TelegramBot registration: {}", e.getMessage());
        }
        return telegramBot;

    }
}
