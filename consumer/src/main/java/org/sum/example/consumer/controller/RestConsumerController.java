package org.sum.example.consumer.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sum.example.consumer.po.FileExtraceWord;
import org.sum.example.consumer.service.ProviderClient;
import org.sum.example.consumer.service.RestConsumerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
public class RestConsumerController {
    @Autowired
    private ProviderClient providerClient;

    @Autowired
    private RestConsumerService restConsumerService;

    @Value("${spring.rabbitmq.host}")
    private String mqhost;

    @Value("${spring.rabbitmq.username}")
    private String mqusername;

    @Value("${spring.rabbitmq.password}")
    private String mqpassword;

    private static Logger logger = LoggerFactory.getLogger(RestConsumerController.class);

    @Trace
    @GetMapping("cdemo1")
    public List<FileExtraceWord> demo1(@RequestParam(required = false) Long fileId) {
        logger.info("requestparam fileId: " + fileId);
        System.out.println(TraceContext.traceId());
        ActiveSpan.tag("fileId", "requestparam fileId: " + fileId);
        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId);
        return providerClient.demo1(fileId);
    }

    @Trace
    @GetMapping("cdemo2")
    public HashMap<String, Object> demo2(@RequestParam(required = false) Long fileId) {
        logger.info("requestparam fileId: " + fileId);
        ActiveSpan.tag("fileId", "requestparam fileId: " + fileId);
        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId);
        return restConsumerService.demo2(fileId);
    }

    @Trace
    @GetMapping("cdemo3")
    public HashMap<String, Object> demo3(@RequestParam(required = false) Long fileId) {
        logger.info("requestparam fileId: " + fileId);
        ActiveSpan.tag("fileId", "requestparam fileId: " + fileId);
        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId);
        return restConsumerService.demo3(fileId);
    }

    @Trace
    @GetMapping("cdemo4")
    public List<FileExtraceWord> demo4(@RequestParam(required = false) Long fileId) {
        logger.info("requestparam fileId: " + fileId);
        ActiveSpan.tag("fileId", "requestparam fileId: " + fileId);
        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(mqhost);
        factory.setUsername(mqusername);
        factory.setPassword(mqpassword);
        factory.setPort(5672);
        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("abc", true, false, false, null);

            // 发行消息到队列
            channel.basicPublish("", "abc", null, (TraceContext.traceId()+"--fileId:"+fileId).toString().getBytes());
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            logger.error(e.getMessage());
        }
        return providerClient.demo1(fileId);
    }
}
