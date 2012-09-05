package com.taykam.todolister;

import java.util.ArrayList;

public class ToDoItem {

	private String title;
	private int id;
	private String description;
	private int parentId;
		
	public ToDoItem(){
		parentId = 0;
		description = "";
		title = "";
		id = getNewId();
	}
	
	//TODO: get unique item id
	private int getNewId(){
		return 0;
	}
	
	public String toString(){
		return getTitle();
	}
	
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public int getId(){
		return id;
	}
	public int getParentId(){
		return parentId;
	}
	public void setParentId(int parentId){
		this.parentId = parentId;
	}
	
}
