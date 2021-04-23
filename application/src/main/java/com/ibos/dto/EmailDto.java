package com.ibos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    private String from;
    private String text;
    private String subject;
    private String[] to;
}
