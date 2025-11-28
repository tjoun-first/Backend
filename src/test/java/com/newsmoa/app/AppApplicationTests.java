package com.newsmoa.app;

import com.newsmoa.app.util.AiUtil;
import com.newsmoa.app.util.CrawlingUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//전체 스프링 컨텍스트를 올려서 테스트
//@SpringBootTest

//스프링 부분 컨텍스트만 띄워서 테스트 
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AiUtil.class, CrawlingUtil.class}) //여기에 테스트할 클래스 지정
@TestPropertySource(locations = "classpath:application.properties")
class AppApplicationTests {
    String prompt = "여기에 프롬프트 입력";
    @Autowired
    AiUtil aiUtil;
    
    @Disabled("gemini api 단독 테스트는 완료되었으므로 테스트 미실행")
	@Test
	void testGemini() {
        System.out.println("테스트 시작");
        String response = aiUtil.queryGemini(prompt);
        System.out.println(response);
    }
    
    String url = "https://news.naver.com/section/101";

    @Test
    void testCrowl(){
        System.out.println("크롤링 테스트 시작");
        System.out.println(CrawlingUtil.getNewsUrls(CrawlingUtil.URL_SCIENCE));
    }
}
