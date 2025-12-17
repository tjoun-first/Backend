package com.newsmoa.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MentionStatResponse {
    String name;
    Long mentionCount;
}
