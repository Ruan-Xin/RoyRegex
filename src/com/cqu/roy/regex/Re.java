package com.cqu.roy.regex;

/**
 * ����������ʽ������nfa ��nfaתdfa
 * @author Roy
 * @date: 2017��3��26��  ����10:25:45
 * version:
 */
public class Re {
	private String regex;
	public Re() {
		// TODO Auto-generated constructor stub
	}
	public Re(String regex) {
		// TODO Auto-generated constructor stub
		this.regex = regex;
	}
	/**
	 * �Ǵ��α���
	 */
	public void compile() {
		
	}
	/**
	 * ���α���
	 * @param re
	 */
	public void compile(String re) {
		
	}
	public boolean match() {
		return true;
	}
	/**
	 * ��������
	 * @param ss
	 */
	private void com(String message){
		Parser parser = new Parser(message);
		//�����ַ������õ��﷨������
		ASTNode root = parser.resolveAST();
	}
	
	private void generateNFA(){
		
	}
}
