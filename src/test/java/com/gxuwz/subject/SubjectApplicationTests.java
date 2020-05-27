package com.gxuwz.subject;


import com.gxuwz.subject.service.IFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectApplicationTests {

    @Autowired
    private IFileService service;

    @Test
    public void contextLoads() {
        Map<String, Object> params = new HashMap<>();


        boolean b = service.uploadFile(params);
        System.out.println(b);

    }

}
