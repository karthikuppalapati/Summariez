package com.better.Summariez.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface IAIService {
    public String getAISummary(String bookName);
}
