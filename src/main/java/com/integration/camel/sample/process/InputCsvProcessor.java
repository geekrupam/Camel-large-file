package com.integration.camel.sample.process;


import com.integration.camel.sample.mapper.InputCsvMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InputCsvProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        if (exchange.getIn().getBody() instanceof List) {
            List<InputCsvMapper> list = exchange.getIn().getBody(List.class);
            list.forEach(l -> getCSVRecord(l));
        }
        else{
            getCSVRecord(exchange.getIn().getBody(InputCsvMapper.class));
        }
    }

    private void getCSVRecord(InputCsvMapper csvRecord) {
        csvRecord.setFullName(csvRecord.getFirstName() + csvRecord.getLastName());
    }
}