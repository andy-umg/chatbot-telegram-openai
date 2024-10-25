package com.miumg.chatbottelegram.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miumg.chatbottelegram.Service.OpenAIService;

@RestController
public class ChatBotController {

    private final OpenAIService openAIService;

    public ChatBotController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/preguntar")
    public String preguntar(@RequestParam String mensaje) {
        try {
            return openAIService.obtenerRespuesta(mensaje);
        } catch (Exception e) {
            return "Error al obtener la respuesta: " + e.getMessage();
        }
    }
}
