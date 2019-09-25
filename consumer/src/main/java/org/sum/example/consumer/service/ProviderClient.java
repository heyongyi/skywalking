package org.sum.example.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sum.example.consumer.po.FileExtraceWord;

import java.util.List;

@Service
@FeignClient(name = "zuul-demo")
public interface ProviderClient {
    @GetMapping("/provider/pdemo1")
    public List<FileExtraceWord> demo1(@RequestParam(required = false) Long fileId);
}
