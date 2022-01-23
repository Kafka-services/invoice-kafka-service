package com.invoice.frontprocessor.model;

import java.util.List;

public class TopicMetaData {
	private String templateId;
	private List<TopicMap> topicMap;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public List<TopicMap> getTopicMap() {
		return topicMap;
	}

	public void setTopicMap(List<TopicMap> topicMap) {
		this.topicMap = topicMap;
	}

	@Override
	public String toString() {
		return "TopicMetaData [templateId=" + templateId + ", topicMap=" + topicMap + "]";
	}

}
