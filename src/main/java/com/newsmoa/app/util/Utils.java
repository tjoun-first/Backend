package com.newsmoa.app.util;

import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class Utils {
    private final String API_KEY;
    private final WebClient client;

    public Utils (@Value("${gemini-api-key}") String apiKey) {
        API_KEY = apiKey;
        client = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-goog-api-key", API_KEY)
                .build();
    }
    
    public String queryGemini(String prompt){
        String jsonBody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                prompt.replace("\n", "\\n")
        );

        // POST 요청 및 응답 읽기
        String response = client.post()
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(String.class) // String으로 응답 받기
                .block(); // 동기 호출

        return getTextFromResponse(response);
    }
    private String getTextFromResponse(String response){
        if(response == null) {
            return null;
            }
        String replaced = response.replace("\\n", "\n");
        
        try {
            String text = JsonParser.parseString(replaced)
                    .getAsJsonObject()
                    .getAsJsonArray("candidates")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0)
                    .getAsJsonObject()
                    .get("text")
                    .getAsString();

            return text;
        }
        catch (NullPointerException e){
            log.warn("gemini api 응답 파싱 중 오류 발생. 응답은 아래와 같음\n"+response);
            return null;
        }
    }
}
