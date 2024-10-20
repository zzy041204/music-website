package com.example.yin;

import com.example.yin.model.domain.Consumer;
import com.example.yin.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@Slf4j
@SpringBootTest
class MusicServerApplicationTests {

    @Autowired
    ConsumerService consumerService;

    @Test
    public void test01(){
        Consumer byId = consumerService.getById(1);
        log.info(byId.toString());
        File file = new File(System.getProperty("user.dir"));
        String parent = file.getParent();
        log.info(parent);
    }

}
