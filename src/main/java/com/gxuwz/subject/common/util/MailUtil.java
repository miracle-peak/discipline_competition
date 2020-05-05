package com.gxuwz.subject.common.util;

import com.gxuwz.subject.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件工具
 *
 * author: 蔡奇峰
 * date: 2020/5/5 21:50
 **/
@Slf4j
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;


    /**
     * 发送邮件
     *
     * @param user
     * @return
     */
    public boolean sendMail(UserModel user){
        // 邮件主题
        String subject = "学科竞赛管理系统";
        // 邮件内容
        String content = "您已经注册成功，欢迎使用学科竞赛系统。";
        // 收件人
        String to = user.getMail();

        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            log.info("邮件已经发送 ^_^ ~_~ ");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
            return false;
        }

        return true;

    }


}
