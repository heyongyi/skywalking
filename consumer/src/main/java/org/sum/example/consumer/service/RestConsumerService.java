package org.sum.example.consumer.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.sum.example.consumer.po.FileExtraceWord;

import java.util.HashMap;
import java.util.List;

public interface RestConsumerService {
    HashMap<String, Object> demo2(Long fileId);
    HashMap<String, Object> demo3(Long fileId);
}
