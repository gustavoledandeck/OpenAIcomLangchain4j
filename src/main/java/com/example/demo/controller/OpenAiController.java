package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MyQuestion;

import dev.ai4j.openai4j.image.ImageModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;

@RestController
public class OpenAiController {

	@Value("demo")
	private String apiKey;
	
	//@Autowired
	//private ChatLanguageModel chatModel;
	
	@Autowired
	private OpenAiChatModel chatModel;
		
	@GetMapping("/answer")
	public String chatWithOpenAI(@RequestBody MyQuestion question) {
		OpenAiChatModel customModel = OpenAiChatModel.builder()
									  				 .apiKey(apiKey)
									  				 .modelName("gpt-3.5-turbo")
									  				 .temperature(0.9)
									  				 .build();
											
			return customModel.generate(question.question());
	}
	
	@PostMapping("/imagem")
	public String generateImage(@RequestBody MyQuestion question) {
		try {
				OpenAiImageModel imageModel = OpenAiImageModel.builder()
															  .apiKey(apiKey)
															  .modelName("dall-e")
															  .build();
				
					return imageModel.generate(question.question()).content().url().toURL().toString();
		} catch (Exception e) {
			return e.toString();
		}
	}
}
