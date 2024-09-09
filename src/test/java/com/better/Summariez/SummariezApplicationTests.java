package com.better.Summariez;

import com.better.Summariez.services.ChatGPTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SummariezApplicationTests {

	@Autowired private ChatGPTService aiService;

	@Test
	void testAISummary() {
		String bookTitle = "The fountainHead";
		aiService.getBookSummary(bookTitle);
	}

}
