package com.cqu.roy.exception;

/**
 * �������쳣�����Ԫ����Ҫƥ��������㣬��ֻ����һ������ʱ���׳�
 * ���쳣��
 * @author Roy
 * @date: 2017��3��30��  ����8:57:45
 * version:
 */
public class NodeExistException extends Exception{
	public NodeExistException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		System.out.println("There are nodes exception!");
	}
}
