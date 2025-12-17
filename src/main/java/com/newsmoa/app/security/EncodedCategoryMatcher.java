package com.newsmoa.app.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class EncodedCategoryMatcher implements RequestMatcher {
    private static final Pattern PATTERN = Pattern.compile("^/api/article/([^/]+)$");
    private final Set<String> encodedCategories;

    public EncodedCategoryMatcher(Set<String> encodedCategories) {
        this.encodedCategories = encodedCategories;
        log.debug("인코딩된 url: {}",encodedCategories.toString());
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String uri = request.getRequestURI(); // 인코딩된 상태
        log.debug("URI 매칭요청됨: {}", uri);
        Matcher matcher = PATTERN.matcher(uri);
        if (!matcher.matches()) {
            log.debug("매칭 실패!");
            return false;
        }

        String encodedCategory = matcher.group(1);
        return encodedCategories.contains(encodedCategory);
    }
}
