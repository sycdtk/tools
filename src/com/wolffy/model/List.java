package com.wolffy.model;

import java.util.ArrayList;

public class List implements java.util.List{

	private ArrayList<List> list = null;
	
	List(){
		list = new ArrayList<List>();
	}

}
