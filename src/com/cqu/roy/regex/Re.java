package com.cqu.roy.regex;

import java.util.Stack;

/**
 * ����������ʽ������nfa ��nfaתdfa
 * @author Roy
 * @date: 2017��3��26��  ����10:25:45
 * version:
 */
public class Re {
	private String regex;
	private ASTNode node;
	public Re() {
		// TODO Auto-generated constructor stub
	}
	public Re(String regex) {
		// TODO Auto-generated constructor stub
		//�﷨���������ˡ����������ˡ��������������Ĳ���reverseһ���ٱ���
		this.regex = reverse(regex);
	}
	/**
	 * �Ǵ��α���
	 */
	public void compile() {
		com(this.regex);
	}
	/**
	 * ���α���
	 * @param re
	 */
	public void compile(String re) {
		com(reverse(re));
	}
	public boolean match(String target) {
		return node.getNfa().match_(target);
	}
	/**
	 * ��������
	 * @param ss
	 */
	private void com(String message){
		Parser parser = new Parser(message);
		//�����ַ������õ��﷨������
		ASTNode root = parser.resolveAST();
		generateNFA(root);
		node = root;
	}
	
	private void generateNFA(ASTNode root){
		if (root == null) {
			return;
		}
		generateNFA(root.getLeftChild());
		generateNFA(root.getRightChild());
		if (!root.getIsLeaf()) {
			if (root.getToken().getCharacter() == '$') {
				//ʹ�ÿ������캯�������µ�nfa�������ڽ��й���ʱ�򲻻�ı� ����е�nfa
				Nfa nfa_left = new Nfa(root.getLeftChild().getNfa());
				Nfa nfa_right = new Nfa(root.getRightChild().getNfa());
				nfa_left.connect(nfa_right);
				
				//System.out.println(root.getRightChild().getNfa());
				root.setNfa(nfa_left);
			}else if (root.getToken().getCharacter() == '|') {
				//ʹ�ÿ������캯�������µ�nfa�������ڽ��й���ʱ�򲻻�ı� ����е�nfa
				Nfa nfa_left = new Nfa(root.getLeftChild().getNfa());
				Nfa nfa_right = new Nfa(root.getRightChild().getNfa());
				nfa_left.and(nfa_right);
				
				//System.out.println(root.getRightChild().getNfa());
				root.setNfa(nfa_left);
			}
		}
	}
	private String reverse(String message){
		Stack<Character> stack = new Stack<>();
		for(int i = 0; i < message.length();i++){
			stack.add(message.charAt(i));
		}
		String str = "";
		while(!stack.isEmpty()){
			str += stack.pop();
		}
		return str;
	}
}
