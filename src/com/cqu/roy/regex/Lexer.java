package com.cqu.roy.regex;

import java.util.Stack;

/**
 * �ʷ�����
 * @author Roy
 * @date: 2017��3��26��  ����11:09:50
 * version:
 */
public class Lexer {
	private Stack<Token> symbolStack;//����ջ
	private Stack<Token> astNodeStack;//�﷨�����ջ
	private String message;//��Ҫ�����Ĵ�
	public Lexer(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
		symbolStack = new Stack<>();
		astNodeStack = new Stack<>();
	}
	/**
	 * �ȹ����﷨������
	 * ��ʾ���ӷ���ʱ��,��message��ab,������Ҫ��bѹ��ջ��ǰ,�Ƚ�$����ѹ�����ջ
	 * �Ե����Ϲ����﷨������
	 */
	public void buildASTree() {
		for(int i = 0; i < message.length();i++){
			if (message.charAt(i) == '(' || message.charAt(i) == ')'
					|| message.charAt(i) == '|' || message.charAt(i) == '*'
					|| message.charAt(i) == '?' || message.charAt(i) == '+') {
				
			}
		}
	}
}
