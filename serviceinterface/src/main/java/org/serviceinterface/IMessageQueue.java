package org.serviceinterface;

public interface IMessageQueue {
	
	public void sendMessage(String queueName, String message);

	public String readMessage(String queueName);
}
