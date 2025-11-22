/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.nvd2603;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author NguyenDuc
 */
public class MailService {

    private static final String FROM_EMAIL = "ducnvce191611@gmail.com";
    ; // Gmail của bạn
    private static final String APP_PASSWORD = "amsz jorw lgmz hiwj";   // App Password Gmail

    public static void sendEmail(String to, String subject, String htmlBody, List<File> attachments)
            throws MessagingException, UnsupportedEncodingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL, "Active_Net_Ticketing"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(htmlBody, "text/html; charset=UTF-8");

        // multipart (body + attachments)
        Multipart multipart = new MimeMultipart();

        // body
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(htmlBody, "text/html; charset=UTF-8");
        multipart.addBodyPart(bodyPart);

        // attachments
        if (attachments != null) {
            for (File file : attachments) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(file);
                multipart.addBodyPart(attachPart);
            }
        }

        message.setContent(multipart);
        Transport.send(message);
    }
    // =======================
// Gửi OTP Verification Code
// =======================

    public static void sendOTP(String to, String otp) {
        String subject = "Mã OTP xác thực tài khoản của bạn";

        String htmlBody = ""
                + "<div style='font-family:Arial,sans-serif;'>"
                + "<h2 style='color:#ff6b6b;'>Xin chào!</h2>"
                + "<p>Mã OTP của bạn là:</p>"
                + "<h1 style='letter-spacing:4px;color:#d9534f;'>" + otp + "</h1>"
                + "<p><b>Mã chỉ có hiệu lực trong 1 phút.</b></p>"
                + "<p>Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>"
                + "<br>"
                + "<p>Trân trọng,<br><b>Active-Net Ticketing</b></p>"
                + "</div>";

        try {
            sendEmail(to, subject, htmlBody, null); // gọi hàm sendEmail có sẵn
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Lỗi gửi OTP đến email: " + to);
        }
    }
}
