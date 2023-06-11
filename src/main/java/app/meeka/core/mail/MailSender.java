package app.meeka.core.mail;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailSender {
    private static final String USER = "barrk999@qq.com"; // 发件人称号，同邮箱地址※
    private static final String PASSWORD = "gkpzflzkbfvvdiba"; // 授权码，开启SMTP时显示※
    public static final String CODE_INFORMATION = "。该验证码用于meeka登录身份确认，五分钟内有效，请勿泄露和转发。如非本人操作，请忽略此邮件。";
    public static final String CODE_MESSAGE = "欢迎登录meeka，验证码是:";

    /**
     * @param to    收件人邮箱
     * @param text  邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static void sendMail(String to, String text, String title) {
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");
            // 发件人的账号
            props.put("mail.user", USER);
            // 发件人的密码
            props.put("mail.password", PASSWORD);
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);
            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            // 设置邮件标题
            message.setSubject(title);
            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MailSender.sendMail("1552585576@qq.com", CODE_MESSAGE + 772143 + CODE_INFORMATION, CODE_MESSAGE);
    }
}
