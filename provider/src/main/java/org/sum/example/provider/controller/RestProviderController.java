package org.sum.example.provider.controller;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sum.example.provider.domain.po.FileExtraceWord;
import org.sum.example.provider.repository.ExcludeHotwordRepository;
import org.sum.example.provider.repository.FileExtraceWordRepository;
import org.sum.example.provider.repository.FileSummaryRepository;
import org.sum.example.provider.repository.SensitiveWordRepository;

import java.util.List;

@RestController
public class RestProviderController {

    @Autowired
    private FileExtraceWordRepository fileExtraceWordRepository;

    @Autowired
    private SensitiveWordRepository sensitiveWordRepository;

    @Autowired
    private ExcludeHotwordRepository excludeHotwordRepository;

    @Autowired
    private FileSummaryRepository fileSummaryRepository;

    private static Logger logger = LoggerFactory.getLogger(RestProviderController.class);

    @Trace
    @GetMapping("/pdemo1")
    private List<FileExtraceWord> demo1(@RequestParam(required = false) Long fileId) {
        logger.info("requestparam fileId: " + fileId);
        System.out.println(TraceContext.traceId());
        ActiveSpan.tag("fileId", "requestparam fileId: " + fileId);
        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId);
        return fileExtraceWordRepository.findCompleteNerByFileId(fileId);
    }
}
