package com.integration.camel.file.common.route.templates;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * This Route Template created for the reuse of a typical File To Topic Route.
 */

@Component
public class FileStreamingRouteTemplate extends RouteBuilder {

    private final CamelContext camelContext;


    public FileStreamingRouteTemplate(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void configure() throws Exception {

        String processorClass = camelContext.resolvePropertyPlaceholders("{{file-to-topic.processorClass}}");
        String persistClass = camelContext.resolvePropertyPlaceholders("{{file-to-topic.persistClass}}");
        String inputMapperClassName = camelContext.resolvePropertyPlaceholders("{{file-to-topic.mapperClass}}");


//
//        routeTemplate("file-to-topic-route-template")
//                .templateBean("timeGapBean")
//                .typeClass("com.integration.camel.file.common.route.utils.TimeGap")
//                .end();

        BindyCsvDataFormat b = new BindyCsvDataFormat(Class.forName(inputMapperClassName));
     //   b.setUnwrapSingleInstance(false);

        from("stream:file?fileName=C:/Users/geekr/OneDrive/Desktop/file_Data_2024_4_621_0.csv&groupLines=500&readLine=true")

             //   .routeId("file-to-topic-route")
            //    .bean("timeGap")
                .log(LoggingLevel.INFO, "Starting to process the file: ${header.CamelFileName} and ${header.camelFileLength} Bytes")
                .setProperty("startTime", simple("${date:now}"))
                .log(LoggingLevel.DEBUG, "Message in process after the initial split: ${body}")
                .threads(3)
                .process(exchange -> {
                    List<String> body = exchange.getIn().getBody(List.class);
                    var records = new ArrayList<>(body.size());
                    for(String line: body) {
                        records.add(b.unmarshal(exchange,new ByteArrayInputStream(line.getBytes())));
                    }
                    exchange.getIn().setBody(records);
                })
                //.unmarshal(b)
              //  .unmarshal().bindy(BindyType.Csv, Class.forName(inputMapperClassName))
                .log(LoggingLevel.DEBUG, "Message in process after the individual split: ${body}")
                .bean(processorClass)
                .bean(persistClass)
                .log(LoggingLevel.DEBUG, "Message in process at the end of individual split: ${body}")
                .end()
                .end()
                .setProperty("endTime", simple("${date:now}"))
                .log(LoggingLevel.INFO, "Done processing the file: ${header.CamelFileName}")
         //       .to("bean:{{timeGap}}?method=calculateTimeDifference(${exchangeProperty.startTime},${exchangeProperty.endTime})")
                .log(LoggingLevel.INFO, "Time Taken to complete the route file-to-topic-route on ${date:now} ${body}")
        ;

    }
}