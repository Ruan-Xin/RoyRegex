package com.cqu.roy.regex;

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
		this.regex = regex;
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
		com(re);
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
				Nfa nfa = root.getLeftChild().getNfa();
				nfa.connect(root.getRightChild().getNfa());
				root.setNfa(nfa);
			}else if (root.getToken().getCharacter() == '|') {
				Nfa nfa = root.getLeftChild().getNfa();
				nfa.and(root.getRightChild().getNfa());
				root.setNfa(nfa);
			}
		}
	}
}
