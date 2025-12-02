package com.newsmoa.app.util;

import com.google.gson.JsonParser;
import com.newsmoa.app.domain.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AiUtil {
    private final String PROMPT_WORD = "[단어 포함된 문장 전체]. 이 문장에서 [선택 단어] 이라는 단어의 의미를 초등학생도 이해할 수 있게 1~2줄로 설명해줘.";
    private final String PROMPT_SIMPLIFY = "나는 경제 뉴스를 읽고 있는 초등학생이고, 너는 전문가야. 이 기사의 내용을 요약없이, 쉽게 풀어쓴 문장으로만 바꿔줘. 서론과 결론을 묻는 질문이나 다음 할 일을 제안하는 문장 없이, 오직 내용만 보내줘. 인물 이름은 그대로 표시해줘.\n" +
            "[뉴스 기사]";
    private final String PROMPT_SUMMARY = "나는 경제 뉴스를 읽고 있는 초등학생이고, 너는 전문가야. 이 기사의 내용을 4~5줄로 요약해줘. 서론과 결론을 묻는 질문이나 다음 할 일을 제안하는 문장 없이, 오직 내용만 보내줘.\n" +
            "[뉴스 기사]";
    
    private final String API_KEY;
    private final WebClient client;

    public AiUtil(@Value("${gemini-api-key}") String apiKey) {
        API_KEY = apiKey;
        client = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-goog-api-key", API_KEY)
                .build();
    }

//-----------------------public 메소드-----------------------------------------------
    //기사 내용 쉽게 해석
    public Article simplifyArticle(Article article){
        String prompt = PROMPT_SIMPLIFY.replace("[뉴스 기사]", article.getContent());
        article.setSimplifiedContent(queryGemini(prompt));
        return article;
    }
    
    //기사 내용 요약
    public Article summarizeArticle(Article article){
        String prompt = PROMPT_SUMMARY.replace("[뉴스 기사]", article.getContent());
        article.setSummaryContent(queryGemini(prompt));
        return article;
    }
    
    //맥락상 단어 뜻 질문
    public String queryWord(String word, String sentence){
        String prompt = PROMPT_WORD.replace("[선택 단어]", word).replace("[단어 포함된 문장 전체]", sentence);
        return queryGemini(prompt);
    }
    
    //임의 내용 쿼리
    public String queryGemini(String prompt){
        log.debug("gemini api 요청됨. 프롬프트: " + prompt);
        String jsonBody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                prompt.replace("\n", "\\n").replace("\"","\\\"")                                                                                                                                                                
        );

        // POST 요청 및 응답 읽기
        String response = client.post()
                .bodyValue(jsonBody)
                .retrieve()
                .onStatus(//json 전체 출력을 위해 삽입
                        HttpStatusCode::isError,
                        res -> res.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("❌ Gemini API 에러 바디 = {}", errorBody);
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class) // String으로 응답 받기
                .block(); // 동기 호출
        log.debug("gemini api 응답함. 내용: "+response);

        return getTextFromResponse(response);
    }
    
//-----------------------private 메소드-----------------------------------------------
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
