package com.sas.refactoring.mail.service;

import java.util.List;

public interface MailService {
    void sendCheckMail(List<String> data) throws Exception;
}
