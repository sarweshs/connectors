package org.gcp.handlers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

import org.gcp.handlers.GcpApiConnectorHandler.PubSubMessage;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;


public class GcpApiConnectorHandler implements BackgroundFunction<PubSubMessage> {
  private static final Logger logger = Logger.getLogger(GcpApiConnectorHandler.class.getName());

  @Override
  public void accept(PubSubMessage message, Context context) {
    String name = "world";
    if (message != null && message.getData() != null) {
      name = new String(
          Base64.getDecoder().decode(message.getData().getBytes(StandardCharsets.UTF_8)),
          StandardCharsets.UTF_8);
    }
    logger.info(String.format("Hello %s!", name));
    return;
  }
  
  static class PubSubMessage {
	  // Cloud Functions uses GSON to populate this object.
	  // Field types/names are specified by Cloud Functions
	  // Changing them may break your code!
	  private String data;
	  private Map<String, String> attributes;
	  private String messageId;
	  private String publishTime;

	  public String getData() {
	    return data;
	  }

	  public void setData(String data) {
	    this.data = data;
	  }

	  public Map<String, String> getAttributes() {
	    return attributes;
	  }

	  public void setAttributes(Map<String, String> attributes) {
	    this.attributes = attributes;
	  }

	  public String getMessageId() {
	    return messageId;
	  }

	  public void setMessageId(String messageId) {
	    this.messageId = messageId;
	  }

	  public String getPublishTime() {
	    return publishTime;
	  }

	  public void setPublishTime(String publishTime) {
	    this.publishTime = publishTime;
	  }
	}
}

