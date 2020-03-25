package com.gxuwz.subject;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableEncryptableProperties // 使用配置加密
@SpringBootApplication
@MapperScan("com.gxuwz.subject.mapper")
public class SubjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);

    }




}
