package com.example.testdemo.net;

public class GetDataResult {
	
	public static int SUCCESS = 0;
	public static int FAILURE = -1;

	private int status;
	private String message;
	
	public GetDataResult(int status) {
		super();
		this.status = status;
	}
	
	public GetDataResult(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isSuccess(){
		return status == SUCCESS ? true : false; 
	}

	@Override
	public String toString() {
		return "GetDataResult [status=" + status + ", message=" + message + "]";
	}
}
