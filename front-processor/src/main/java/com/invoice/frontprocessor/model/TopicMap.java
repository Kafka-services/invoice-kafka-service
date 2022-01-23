package com.invoice.frontprocessor.model;

import java.util.List;

public class TopicMap {
	private String topicName;
	private List<String> fields;

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "TopicMap [name=" + topicName + ", fields=" + fields + "]";
	}

}
