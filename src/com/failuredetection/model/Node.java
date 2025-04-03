package com.failuredetection.model;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private String name;
	private Integer executionTime;
	private Integer startTime;
	private Integer endTime;
	private Integer level;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Integer executionTime) {
		this.executionTime = executionTime;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Node getClone() {
		
		Node node = new Node();
		
		node.setEndTime(this.endTime);
		node.setExecutionTime(this.executionTime);
		node.setLevel(this.level);
		node.setName(this.name);
		node.setStartTime(this.startTime);
		
		return node;
	}

}
