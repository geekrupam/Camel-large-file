package com.integration.camel.sample.process;


import com.integration.camel.sample.mapper.InputCsvMapper;
import com.integration.camel.sample.service.BatchInsertService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PersistProcessor implements Processor {

    @Autowired
    private BatchInsertService userService;

    @Override
    public void process(Exchange exchange) throws Exception {

        if (exchange.getIn().getBody() instanceof List) {
            List<InputCsvMapper> list = exchange.getIn().getBody(List.class);
            userService.insertBatch(list);
        } else {
            userService.insertBatch(Arrays.asList(exchange.getIn().getBody(InputCsvMapper.class)));
        }

    }
}