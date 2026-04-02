package com.marea.Proyecto.Services;

import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Method;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${correo.remitente}")
    private String remitente;

    public void enviarCorreoConfirmacion(String destinatario, String asunto, String contenidoHtml) throws IOException {

        Email from = new Email(remitente);
        Email to = new Email(destinatario);
        Content content = new Content("text/html", contenidoHtml);

        Mail mail = new Mail(from, asunto, to, content);

        SendGrid sg = new SendGrid(apiKey);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        sg.api(request); 
    }
}
