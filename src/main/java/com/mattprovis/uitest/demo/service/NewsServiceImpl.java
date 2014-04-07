package com.mattprovis.uitest.demo.service;

import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    @Override
    public String getLatestNews() {
        return "We just added a bunch of new features!";
    }
}
