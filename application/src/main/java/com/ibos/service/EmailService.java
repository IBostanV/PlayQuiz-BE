package com.ibos.service;

import com.ibos.dto.EmailDto;

public interface EmailService {
    void sendEmail(EmailDto emailDto);
}
