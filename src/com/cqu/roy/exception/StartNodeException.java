package com.cqu.roy.exception;

/**
 * 
 * @author Roy
 * @date: 2017��3��21��  ����4:55:23
 * ���ÿ���������Ƿ���ڶ��start���
 */

public class StartNodeException extends Exception{
	
	public StartNodeException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		System.err.println("There are multiple start nodes");
	}
}
