package org.sum.example.consumer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.sum.example.consumer.configuration.RedisDao;
import org.sum.example.consumer.po.FileExtraceWord;
import org.sum.example.consumer.service.ProviderClient;
import org.sum.example.consumer.service.RestConsumerService;
import org.sum.example.consumer.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Service
public class RestConsumerServiceImpl implements RestConsumerService {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private ProviderClient providerClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static Logger logger = LoggerFactory.getLogger(RestConsumerServiceImpl.class);


    @Trace
    @Override
    public HashMap<String, Object> demo2(Long fileId) {
        logger.info("demo2 requestparam fileId: " + fileId);
        List<FileExtraceWord> fileExtraceWords = providerClient.demo1(fileId);
        if(fileExtraceWords != null && fileExtraceWords.size()>0){
            redisDao.save("demo2",fileId+"--"+TraceContext.traceId(),fileExtraceWords.get(0) ,3000);
        }
        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId+"--return:"+redisDao.read("demo2",fileId+"--"+TraceContext.traceId()));
        return redisDao.read("demo2",fileId+"--"+TraceContext.traceId());
    }

    @Trace
    @Override
    public HashMap<String, Object> demo3(Long fileId) {
        logger.info("demo3 requestparam fileId: " + fileId);
        List<FileExtraceWord> fileExtraceWords = providerClient.demo1(fileId);
        Jedis jedis = JedisUtil.getInstance().getJedis("192.168.3.106",6379);
        ObjectMapper mapper = new ObjectMapper();

        if(fileExtraceWords != null && fileExtraceWords.size()>0){
            String json=null;
            try {
                json =  mapper.writeValueAsString(fileExtraceWords.get(0));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedis.hset("demo3",fileId+"--"+TraceContext.traceId(),json);
        }

        ActiveSpan.info(TraceContext.traceId()+"--fileId:"+fileId+"--return:"+jedis.hget("demo3",fileId+"--"+TraceContext.traceId()));
        try {
            HashMap<String, Object> map = mapper.readValue(jedis.hget("demo3",fileId+"--"+TraceContext.traceId()), HashMap.class);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
