package com.miumg.chatbottelegram.Service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OpenAIService {
    // private static final String API_KEY = "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";
    // private static final String API_KEY = "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";
   
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String obtenerRespuesta(String prompt) throws Exception {
        // Configuración de la solicitud HTTP
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(API_URL);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + API_KEY);

        // Construcción del cuerpo de la solicitud
        // String jsonInputString = String.format(
        //     "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", 
        //     prompt
        // );
        String jsonInputString = String.format(
            "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", 
            prompt
        );

        request.setEntity(new StringEntity(jsonInputString));

        // Envío de la solicitud y manejo de la respuesta
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Extraer la respuesta del modelo
            return jsonNode.get("choices").get(0).get("message").get("content").asText();
        }
    } 
}
