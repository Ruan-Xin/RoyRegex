package com.cqu.roy.exception;

/**
 * ���ź��ַ�ѡ��� ͬʱΪ��
 * @author Roy
 * @date: 2017��3��26��  ����11:18:05
 * version:1.0.0
 */
public class SymbolCollision extends Exception{
	public SymbolCollision(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		System.out.println("Symbol and character options conflict");
	}
}
