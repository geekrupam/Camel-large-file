//package com.integration.camel.sample.builders;
//
//import com.integration.camel.file.common.route.templates.FileStreamingRouteTemplate;
//import org.apache.camel.CamelContext;
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FileToTopicRoute extends RouteBuilder {
//
//    @Autowired
//    CamelContext camelContext;
//
//    @Override
//    public void configure() throws Exception {
//
//        camelContext.addRoutes(new FileStreamingRouteTemplate(camelContext));
//        templatedRoute("file-to-topic-route-template").routeId(camelContext.resolvePropertyPlaceholders("{{file-to-topic.routeId}}"));
//
//    }
//
//}