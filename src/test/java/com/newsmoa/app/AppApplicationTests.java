package com.newsmoa.app;

import com.newsmoa.app.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//전체 스프링 컨텍스트를 올려서 테스트
//@SpringBootTest

//스프링 부분 컨텍스트만 띄워서 테스트 
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Utils.class) //여기에 테스트할 클래스 지정
@TestPropertySource(locations = "classpath:application.properties")
class AppApplicationTests {
    String prompt = "여기에 프롬프트 입력";
    @Autowired
    Utils utils;
    
	@Test
	void contextLoads() {
        System.out.println("테스트 시작");
        String response = utils.queryGemini(prompt);
        System.out.println(response);
    }

}
