package com.mattprovis.uitest.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ServiceConfig.class})
public class RootConfig {
}
