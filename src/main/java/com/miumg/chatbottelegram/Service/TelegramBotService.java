package com.miumg.chatbottelegram.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class TelegramBotService extends TelegramLongPollingBot  {

    private final String botName;
    private final Map<Long, String> userStates = new HashMap<>();

    @Autowired
    private MessageService messageService;

    public TelegramBotService(String botName, String botToken) {
        super (botToken);
        this.botName = botName;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String messageText = message.getText();

            log.info("Mensaje recibido: {}", messageText);
            log.info("Id del Chat: {}", chatId);

            try {
                if (Objects.equals(messageText, "/start")) {
                    iniciarConversacion(chatId);
                } else if ("waitting_name".equals(userStates.get(chatId))) {
                    obtenerNombreUsuario(chatId, messageText);
                } else {
                    enviarMensaje(chatId, "No entiendo ese comando. Prueba con /start.");
                }
            } catch (TelegramApiException e) {
                log.error("Error durante la ejecución de la API de Telegram: {}", e.getMessage());
            }
        }
    }

    /**
     * Inicia la conversación y solicita el nombre del usuario.
     */
    private void iniciarConversacion(Long chatId) throws TelegramApiException {
        userStates.put(chatId, "waitting_name");
        enviarMensaje(chatId, "¡Hola! ¿Cuál es tu nombre?");
    }

    /**
     * Procesa el nombre del usuario y envía un saludo personalizado.
     */
    private void obtenerNombreUsuario(Long chatId, String name) throws TelegramApiException {
        userStates.remove(chatId); // Limpiamos el estado
        String response = "¡Mucho gusto, " + name + "! ¿En qué puedo ayudarte?";
        enviarMensaje(chatId, response);
    }

    /**
     * Envía un mensaje al usuario a través de Telegram.
     */
    private void enviarMensaje(Long chatId, String texto) throws TelegramApiException {
        SendMessage mensaje = new SendMessage(chatId.toString(), texto);
        execute(mensaje);
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}