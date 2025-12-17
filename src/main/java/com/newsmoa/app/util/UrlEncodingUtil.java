package com.newsmoa.app.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public class UrlEncodingUtil {
    public static Set<String> encodeAll(Set<String> values){
        return values.stream()
                .map(val -> URLEncoder.encode(val, StandardCharsets.UTF_8))
                .collect(Collectors.toUnmodifiableSet());
    }
}
