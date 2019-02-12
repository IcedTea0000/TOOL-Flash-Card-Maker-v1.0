package com.makingapps.flashcard;

import java.io.Serializable;

public class FlashCard implements Serializable {
	String question, answer;

	public FlashCard() {

	}

	public FlashCard(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
