package com.cqu.roy.regex;

/**
 * һ��ASTNode�а�����һ��AST���ڹ���NFA��ʱ����չ����
 * @author Roy
 * @date: 2017��3��26��  ����8:27:57
 * version:
 */
public class ASTNode {
	private ASTNode rootNode;//ÿ��ASTNode����һ��rootNode��Ҫô����һ�������򷵻�һ�����
	private Token token;
	private ASTNode leftChild;
	private ASTNode rightChild;
	private Nfa nfa;
	private boolean isLeaf;
	public ASTNode(Token token, ASTNode leftChild, ASTNode rightChild,boolean isLeaf) {
		// TODO Auto-generated constructor stub
		this.token = token;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		nfa = new Nfa();
		this.isLeaf = isLeaf;
		//����Ҷ�ӽ�㣬ֱ��������Ӧ��NFA���ַ�start->end(c)
		if (isLeaf) {
			nfa.addNodeToNfa("start");
			nfa.addStart("start");
			nfa.addNodeToNfa("end");
			nfa.addEnd("end");
			nfa.getStart().addMoveState(token.getCharacter(), nfa.getNode("end"));
		}
	}
	
	public void setLeftChild(ASTNode leftNode) {
		this.leftChild = leftNode;
	}
	public void setRightChild(ASTNode rightNode) {
		this.rightChild = rightNode;
	}
	
	public ASTNode getLeftChild() {
		return leftChild;
	}
	
	public ASTNode getRightChild() {
		return rightChild;
	}
	
	public void setRootNode(ASTNode rootNode) {
		this.rootNode = rootNode;
	}
	public ASTNode getRootNode() {
		return rootNode;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public Token getToken() {
		return token;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean getIsLeaf() {
		return isLeaf;
	}
	public void setNfa(Nfa nfa) {
		this.nfa = nfa;
	}
	public Nfa getNfa() {
		return nfa;
	}
}
