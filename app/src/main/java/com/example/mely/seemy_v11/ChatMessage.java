package com.example.mely.seemy_v11;


public class ChatMessage {

	
	public boolean side; //vrai si emetteur , faux sinon
	public String message;

	public boolean getSide() {
		return side;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSide(boolean side) {
		this.side = side;

	}

	public ChatMessage(boolean side , String message) {

		super();
		this.side=side;
		this.message = message;
	}

}
