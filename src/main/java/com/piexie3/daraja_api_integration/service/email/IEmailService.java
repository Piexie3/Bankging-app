package com.piexie3.daraja_api_integration.service.email;

import com.piexie3.daraja_api_integration.models.EmailDetails;

public interface IEmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
