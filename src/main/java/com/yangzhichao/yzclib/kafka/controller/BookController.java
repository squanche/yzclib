package com.yangzhichao.yzclib.kafka.controller;

import com.yangzhichao.yzclib.kafka.entity.Book;
import com.yangzhichao.yzclib.kafka.producer.BookProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Value("my-topic")
    String myTopic;
    @Value("my-topic2")
    String myTopic2;

    @Autowired
    BookProducer producer;

    private AtomicLong atomicLong = new AtomicLong();


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void sendMessageToKafkaTopic(@RequestParam("name") String name) {
        this.producer.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
        this.producer.sendMessage(myTopic2, new Book(atomicLong.addAndGet(1), name));
    }
}
