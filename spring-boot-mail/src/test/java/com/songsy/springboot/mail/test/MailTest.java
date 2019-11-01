package com.songsy.springboot.mail.test;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songsy
 * @date 2019/11/1 16:52
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MailTest {
    /**
     * 获取JavaMailSender bean
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 获取配置文件的username
     */
    @Value("${spring.mail.username}")
    private String fromUser;

    private final String toUser = "songshuiyang@foxmail.com";

    /**
     * 发送简单邮件
     */
    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者
        message.setFrom(fromUser);
        // 接收者
        message.setTo(toUser);
        // 主题
        message.setSubject("测试主题");
        // 邮件内容
        message.setText("发了封邮件");
        // 发送邮件
        javaMailSender.send(message);
    }

    /**
     * 发送附件邮箱
     * @throws Exception
     */
    @Test
    public void sendAttachmentsMail() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromUser);
        helper.setTo(toUser);
        helper.setSubject("测试主题：有附件");
        helper.setText("测试内容:有附件的邮件");
        ClassPathResource classPathResource1 = new ClassPathResource("fujian.txt");
        ClassPathResource classPathResource2 = new ClassPathResource("wn.jpg");

        helper.addAttachment("fujian.txt", classPathResource1);
        helper.addAttachment("wn.jpg", classPathResource2);
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送静态邮箱
     * @throws Exception
     */
    @Test
    public void sendStaticMail() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromUser);
        helper.setTo(toUser);
        helper.setSubject("测试主题：嵌入静态资源");
        helper.setText("<html><body><img src=\"cid:wn\" ></body></html>", true);

        ClassPathResource classPathResource2 = new ClassPathResource("wn.jpg");
        // addInline函数中资源名称需要与正文中cid对应起来
        helper.addInline("wn", classPathResource2);
        javaMailSender.send(mimeMessage);

    }

    /**
     * 发送模板信息
     * @throws Exception
     */
    @Test
    public void sendTemplateMail() throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromUser);
        helper.setTo(toUser);
        helper.setSubject("测试主题：模板邮件");
        /**
         * 模板内需要的参数,保持一致
         */
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "songsy");
        /**
         * 配置FreeMarker模板路径
         */
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), params);
        helper.setText(html, true);

        javaMailSender.send(mimeMessage);
    }

}
