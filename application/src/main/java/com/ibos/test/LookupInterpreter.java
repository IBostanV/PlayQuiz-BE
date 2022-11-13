package com.ibos.test;

import com.ibos.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Lookup;

public abstract class LookupInterpreter {
    @Lookup
    public abstract EmailServiceImpl emailService();
}
