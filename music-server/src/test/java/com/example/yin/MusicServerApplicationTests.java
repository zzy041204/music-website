package com.example.yin;

import com.example.yin.domain.Consumer;
import com.example.yin.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MusicServerApplicationTests {

    @Autowired
    ConsumerService consumerService;

    @Test
    public void test01(){
        Consumer byId = consumerService.getById(1);
        log.info(byId.toString());
    }

}
