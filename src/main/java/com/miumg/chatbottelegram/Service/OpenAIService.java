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
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String obtenerRespuesta(String prompt) throws Exception {
        log.info("Este es el prompt: " + prompt);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(API_URL);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + API_KEY);

        // Construcción del cuerpo de la solicitud
        String jsonInputString = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\",\"content\": \"%s\"}]}",
                prompt);

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
