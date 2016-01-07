package com.example.lockscreentest.wangsz.model;

public class Question {

	//�???????�?
	private String mContent;
	
	//�????�?�?
	private String mAnswer;
	
	//�????混�??�?�????�?
	private String mWrongAnswer;
	
	//�?�????�?
	private String mType;
	
	//�?�???�度
	private int mAnswerLength;
	
	public char[] getAnswerChars(){
		return mAnswer.toCharArray();
	}

	public char[] getWrongAnswerChars(){
		return mWrongAnswer.toCharArray();
	}
	
	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		this.mContent = content;
	}

	public String getAnswer() {
		return mAnswer;
	}

	public void setAnswer(String answer) {
		this.mAnswer = answer;
		this.mAnswerLength = answer.length();
	}
	
	public String getWrongAnswer() {
		return mWrongAnswer;
	}

	public void setWrongAnswer(String wrongAnswer) {
		this.mWrongAnswer = wrongAnswer;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		this.mType = type;
	}

	public int getAnswerLength() {
		return mAnswerLength;
	}
	
}
