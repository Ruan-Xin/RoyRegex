package com.cqu.roy.exception;
/**
 * 
 * @author Roy
 * @date: 2017��3��21��  ����4:55:05
 * ���ÿ���������Ƿ���ڶ��end���
 */

public class EndNodeException extends Exception{
	public EndNodeException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		System.err.println("There are multiple start nodes");
	}
}
