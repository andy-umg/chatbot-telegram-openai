package com.miumg.chatbottelegram.Service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class OpenAIService {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions" ;

    public String obtenerRespuesta(String prompt) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(API_URL);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + API_KEY);

        // Construcción del cuerpo de la solicitud
        String jsonInputString = String.format(
                "{\n" +
                        "  \"messages\": [\n" +
                        "    {\n" +
                        "      \"role\": \"user\",\n" +
                        "      \"content\": \"%s\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"model\": \"llama3-8b-8192\",\n" +
                        "  \"temperature\": 1,\n" +
                        "  \"max_tokens\": 1024,\n" +
                        "  \"top_p\": 1,\n" +
                        "  \"stream\": false,\n" +
                        "  \"stop\": null\n" +
                        "}",
                prompt);
        log.info("Esto te mando: " + jsonInputString);
        request.setEntity(new StringEntity(jsonInputString));

        // Envío de la solicitud y manejo de la respuesta
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            log.info("Respuesta de la API: {}", jsonResponse);  // Agregar log para depuración
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Verificar que la respuesta contiene los datos esperados
            if (jsonNode.has("choices") && jsonNode.get("choices").isArray() && jsonNode.get("choices").size() > 0) {
                JsonNode choice = jsonNode.get("choices").get(0);
                if (choice.has("message") && choice.get("message").has("content")) {
                    return choice.get("message").get("content").asText();
                } else {
                    log.error("La respuesta no contiene el campo 'message' o 'content'.");
                    return "No se pudo obtener la respuesta.";
                }
            } else {
                log.error("La respuesta no contiene 'choices' o está vacía.");
                return "No se pudo obtener la respuesta.";
            }
        }
    }

}
