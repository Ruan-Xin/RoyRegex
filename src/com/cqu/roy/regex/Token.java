package com.cqu.roy.regex;

import com.cqu.roy.exception.SymbolCollision;

/**
 * token�����ַ�[1-9a-zA-Z]
 * ���Ű���()|+?*[]\
 * @author Roy
 * @date: 2017��3��26��  ����11:11:10
 * version:
 */
public class Token {
	private char c;//�����ַ�
	private boolean symbol;//�Ƿ��Ƿ���
	private boolean cc;
	public Token(Character c, boolean symbol, boolean cc) {
		// TODO Auto-generated constructor stub
		this.c = c;
		this.symbol = symbol;
		this.cc = cc;
	}
	
	private void check() throws SymbolCollision{
		if ((cc && symbol) || ((!cc) &&(!symbol))) {
			throw new SymbolCollision(getClass().toString());
		}
	}
	
}
