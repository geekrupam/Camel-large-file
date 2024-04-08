package com.integration.camel.file.common.route.templates;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.support.processor.idempotent.FileIdempotentRepository;
import org.springframework.stereotype.Component;

import java.io.File;

/*
 * This Route Template created for the reuse of a typical File To Topic Route.
 */

@Component
public class FileToTopicRouteTemplate extends RouteBuilder {

    private final CamelContext camelContext;


    public FileToTopicRouteTemplate(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void configure() throws Exception {

        String routeId = camelContext.resolvePropertyPlaceholders("{{file-to-topic.routeId}}");
        String fileUri = camelContext.resolvePropertyPlaceholders("{{file-to-topic.file.uri}}");
        String token = camelContext.resolvePropertyPlaceholders("{{file-to-topic.file.token}}");
        int noOfLinesToReadAtOnce = Integer.parseInt(camelContext.resolvePropertyPlaceholders("{{file-to-topic.file.noOfLinesToReadAtOnce}}"));
        boolean skipFirstLine = Boolean.parseBoolean(camelContext.resolvePropertyPlaceholders("{{file-to-topic.file.skipFirstLine}}"));
        boolean streaming = Boolean.parseBoolean(camelContext.resolvePropertyPlaceholders("{{file-to-topic.file.streaming}}"));
        String inputMapperClassName = camelContext.resolvePropertyPlaceholders("{{file-to-topic.mapperClass}}");
        String processorClass = camelContext.resolvePropertyPlaceholders("{{file-to-topic.processorClass}}");
        String persistClass = camelContext.resolvePropertyPlaceholders("{{file-to-topic.persistClass}}");
       // String endpointUri = camelContext.resolvePropertyPlaceholders("{{file-to-topic.endpoint.uri}}");
        String trackFile = camelContext.resolvePropertyPlaceholders("{{file-to-topic.trackFile}}");

        routeTemplate("file-to-topic-route-template")
                .templateBean("timeGapBean")
                .typeClass("com.integration.camel.file.common.route.utils.TimeGap")
                .end()

                .from(fileUri)
                .delay(simple("${random(1000,5000)}"))
                .idempotentConsumer(header("CamelFileName"),
                        FileIdempotentRepository.fileIdempotentRepository(new File(trackFile)))
                .routeId(routeId)
                .log(LoggingLevel.INFO, "Starting to process the file: ${header.CamelFileName} and ${header.camelFileLength} Bytes")
                .setProperty("startTime", simple("${date:now}"))
                .split(body().tokenize(token, noOfLinesToReadAtOnce, skipFirstLine)).streaming(streaming)
                .log(LoggingLevel.DEBUG, "Message in process after the initial split: ${body}")
                .unmarshal().bindy(BindyType.Csv, Class.forName(inputMapperClassName))
                //   .split(body(), new JsonAggregationStrategy())
                .log(LoggingLevel.DEBUG, "Message in process after the individual split: ${body}")
                .bean(processorClass)
                .bean(persistClass)
                // .marshal().json(JsonLibrary.Jackson)
                .log(LoggingLevel.DEBUG, "Message in process at the end of individual split: ${body}")
                .end()
                //   .log(LoggingLevel.DEBUG, "Message will be sent after aggregation: ${body}")
                //.to(endpointUri)
                .end()
                .setProperty("endTime", simple("${date:now}"))
                .log(LoggingLevel.INFO, "Done processing the file: ${header.CamelFileName}")
                .to("bean:{{timeGapBean}}?method=calculateTimeDifference(${exchangeProperty.startTime},${exchangeProperty.endTime})")
                .log(LoggingLevel.INFO, "Time Taken to complete the route " + routeId + " on ${date:now} ${body}")
                ;

    }
}