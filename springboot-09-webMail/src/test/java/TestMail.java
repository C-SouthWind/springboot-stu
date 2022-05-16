import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest(classes = TestMail.class)
@EnableAutoConfiguration
public class TestMail {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void test(){
        //一个简单的邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("chj，你好");
        simpleMailMessage.setText("chj的邮件测试");
        simpleMailMessage.setTo("1298365022@qq.com");
        simpleMailMessage.setFrom("1298365022@qq.com");

        mailSender.send(simpleMailMessage);
    }

    @Test
    public void test2() throws Exception{
        //一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("chj，你好！====");
        helper.setText("<p style='color:red'>这是一段话</p>",true);
        //附件
        helper.addAttachment("1.jpg",new File("F:\\ideaWork\\springboot\\stu\\springBootStu\\springboot-09-webMail\\src\\main\\resources/1.jpg"));
        helper.setTo("1298365022@qq.com");
        helper.setFrom("1298365022@qq.com");

        mailSender.send(mimeMessage);
    }

}
