package com.cqu.roy.exception;
/**
 * 
 * @author Roy
 * @date: 2017��3��21��  ����4:55:34
 * ȷ����dfa��ÿ�������룬������һ����
 */
public class UncertainException extends Exception{
	
	public UncertainException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		System.err.println("There multiple edge map input!");
		
	}
}
