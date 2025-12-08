package com.newsmoa.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsmoa.app.util.AiUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/util")
public class UtilController {
    
    private final AiUtil aiUtil;

    @Autowired
    public UtilController(AiUtil aiUtil) {
        this.aiUtil = aiUtil;
    }

    @GetMapping("/word-meaning")
    public String getWordMeaning(@RequestParam("word") String word, @RequestParam("sentence") String sentence){
        return aiUtil.queryWord(word, sentence);
    }
    
    
}