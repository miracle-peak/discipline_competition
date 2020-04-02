package com.gxuwz.subject;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@EnableEncryptableProperties // 使用配置加密
@SpringBootApplication
@MapperScan("com.gxuwz.subject.mapper")
public class SubjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);

    }

    // 配置逻辑删除注入的Bean
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }



}
