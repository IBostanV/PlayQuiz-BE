package com.ibos.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class Email extends GenericEntity<Long> {
    @Column
    private String content;

    @Column
    private String sender;

    @Column
    private LocalDateTime sendTime;
}
