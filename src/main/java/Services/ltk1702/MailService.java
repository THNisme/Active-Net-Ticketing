/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.ltk1702;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {

    private static final String FROM_EMAIL = "clbfactive8386@gmail.com";
    private static final String FROM_PASSWORD = "zefhnzdprcwslttc";

    public static void sendAccountEmail(String toEmail, String username, String password) {
        String subject = "Tài khoản Active Net Ticketing";
        String body = "Xin chào " + username + ",\n\n"
                + "Tài khoản của bạn đã được tạo thành công.\n"
                + "Tên đăng nhập: " + username + "\n"
                + "Mật khẩu: " + password + "\n\n"
                + "Vui lòng đăng nhập và đổi mật khẩu sau khi sử dụng lần đầu.\n\n"
                + "Trân trọng,\nActive Net Ticketing";

        sendMail(toEmail, subject, body);
    }

    public static void sendUpdateNotificationEmail(String toEmail, String oldUsername, String newUsername,
            boolean passwordChanged, boolean roleChanged, int newRole, String password) {
        String subject = "Thông báo cập nhật tài khoản";

        StringBuilder body = new StringBuilder();
        body.append("Xin chào ").append(oldUsername).append(",\n\n");
        body.append("Tài khoản của bạn đã được cập nhật với các thay đổi sau:\n\n");

        if (!oldUsername.equals(newUsername)) {
            body.append("- Tên đăng nhập mới: ").append(newUsername).append("\n");
        }
        if (passwordChanged) {
            body.append("- Mật khẩu đã được thay đổi.\n");
            body.append("- Mật khẩu mới: ").append(password).append("\n");
        }
        if (roleChanged) {
            body.append("- Quyền hệ thống mới: ").append(newRole == 1 ? "Admin" : "User").append("\n");
        }

        body.append("\nNếu bạn không thực hiện thay đổi này, vui lòng liên hệ hỗ trợ ngay.\n\n");
        body.append("Trân trọng,\nActive Net Ticketing");

        sendMail(toEmail, subject, body.toString());
    }

    private static void sendMail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
