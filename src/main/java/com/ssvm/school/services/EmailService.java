package com.ssvm.school.services;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.ssvm.school.constants.NotificationType;
import com.ssvm.school.event.NotificationEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
class EmailService implements NotificationService {

    @Value("${spring.mail.username:rathpintu90@gmail.com}")
    private String emailAddress;

    private final JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(NotificationEvent event) {
        if (event.templateName() != null && !event.templateName().isEmpty()) {
            byte[] pdfBytes = this.generatePdfFromHtml(event.templateName(), event.templateModel());
            sendHtmlEmail(
                    event.recipient(),
                    event.subject(),
                    event.attachmentName(),
                    event.templateModel(),
                    pdfBytes,
                    event.message()
            );
        } else {
            sendEmail(
                    event.recipient(),
                    event.subject(),
                    event.message()
            );
        }
    }

    public void sendHtmlEmail(String toEmail, String subject, String attachmentName,
                              Map<String, Object> templateModel, byte[] pdfBytes, String plainTextBody) {
        try {
            Context context = new Context();
            context.setVariables(templateModel);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(emailAddress);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(plainTextBody);
            helper.addAttachment(attachmentName, new ByteArrayResource(pdfBytes));

            mailSender.send(mimeMessage);
            log.info("Email sent to {}", toEmail);

        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailAddress);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send plain email: " + e.getMessage());
        }
    }

    public byte[] generatePdfFromHtml(String templateName, Map<String, Object> templateModel) {
        try {
            templateModel.put("schoolLogoBase64", loadAndConvertToBase64("static/images.jpg"));
            templateModel.put("signatureBase64", loadAndConvertToBase64("static/signature.png"));

            Context context = new Context();
            context.setVariables(templateModel);
            String htmlContent = templateEngine.process(templateName, context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ConverterProperties properties = new com.itextpdf.html2pdf.ConverterProperties();
            HtmlConverter.convertToPdf(htmlContent, outputStream, properties);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF from template: " + e.getMessage(), e);
        }
    }

    @Override
    public NotificationType getSupportedType() {
        return NotificationType.EMAIL;
    }

    private String loadAndConvertToBase64(String classpathResourcePath) {
        try {
            byte[] imageBytes = new ClassPathResource(classpathResourcePath).getContentAsByteArray();
            String mimeType = classpathResourcePath.endsWith(".png") ? "image/png" : "image/jpeg";
            return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            System.err.println("Could not load image: " + classpathResourcePath);
            return "";
        }
    }

}
