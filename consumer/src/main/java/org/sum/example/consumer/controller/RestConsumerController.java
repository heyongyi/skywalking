package org.sum.example.consumer.controller;

import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sum.example.consumer.po.FileExtraceWord;
import org.sum.example.consumer.service.ProviderClient;

import java.util.List;

@RestController
public class RestConsumerController {
    @Autowired
    private ProviderClient providerClient;

    @Trace
    @GetMapping("cdemo1")
    public List<FileExtraceWord> demo1(@RequestParam(required = false) Long fileId) {
        return providerClient.demo1(fileId);
    }
}
