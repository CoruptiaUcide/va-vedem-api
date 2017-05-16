package ro.vavedem.services;

import com.sendgrid.*;
import ro.vavedem.exceptions.VaVedemEmailException;
import ro.vavedem.interfaces.MailService;
import ro.vavedem.models.EmailModel;

import java.io.IOException;


/**
 * @author CoruptiaUcide
 */
@org.springframework.stereotype.Service
public class EmailService implements MailService<EmailModel> {

    public void send(EmailModel model) throws VaVedemEmailException{

        Email from = new Email(model.getFrom());
        String subject = model.getSubject();
        Email to = new Email(model.getTo());
        Content content = new Content("text/plain", model.getContent());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
        } catch (IOException ex) {
            throw new VaVedemEmailException("Eroare la trimiterea emailului.");
        }
    }

}
