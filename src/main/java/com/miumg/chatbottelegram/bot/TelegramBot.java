package com.miumg.chatbottelegram.bot;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot  {


    private final String botName;
    public TelegramBot(String botName, String botToken) {
        super (botToken);
        this.botName = botName;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            var chatId = message.getChatId();
            log.info("Message recibido: {}", message.toString());
            var messageText = message.getText();
            log.info(messageText);
            try {
                execute(new SendMessage(chatId.toString(),"Hola como estas? :)"));
            } catch (TelegramApiException e) {
                log.error("Exception during processing telegram API {}", e.getMessage());
            }
        }

    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}