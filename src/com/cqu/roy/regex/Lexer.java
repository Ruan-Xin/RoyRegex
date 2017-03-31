package com.cqu.roy.regex;

import java.util.HashMap;
import java.util.Stack;

import com.cqu.roy.exception.BracketsNotMatchedException;
import com.cqu.roy.exception.NodeExistException;

/**
 * �ʷ�����
 * @author Roy
 * @date: 2017��3��26��  ����11:09:50
 * version:
 */
public class Lexer {
	private Stack<ASTNode> symbolStack;//����ջ
	private Stack<ASTNode> astNodeStack;//�﷨�����ջ
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
	 * �������ȼ�*=+=?>()>$>|
	 * ջ�е�������Զ����������
	 */
	public void buildASTree() throws NodeExistException,
	BracketsNotMatchedException{
		//ջ���Ƿ���������
		boolean left_parenthese = false;
		for(int i = 0; i < message.length();i++){
			if (message.charAt(i) == '(') {
				//ջ�д���������
				left_parenthese = true;
				symbolStack.push(new ASTNode(new Token('(', true, false), null, null));
			}else if (message.charAt(i) == ')') {
				if (left_parenthese) {//�����������ţ�
					/**
					 * ���Ų������﷨�����������ȼ��ж�
					 * �����ڴ����������
					 * ()
					 * (N)
					 * (N-M)-�����Ԫ�����
					 */
					ASTNode astNode = symbolStack.pop();
					Character c = astNode.getToken().getCharacter();
					if (c == '(') {
						//����()��(N)������������������������������
					}else {
						//(N-M)���
						//���Ŵ���ɽ��
						ASTNode an = new ASTNode(new Token(c, true, false), null, null);
						//M��N����ɽ��
						if (astNodeStack.size() != 2) {
							throw new NodeExistException(getClass().toString());
						}else {
							ASTNode leftNode = astNodeStack.pop();
							ASTNode rightNode = astNodeStack.pop();
							an.setLeftChild(leftNode);
							an.setRightChild(rightNode);
							//Ȼ��������������һ��ASTNode���
							ASTNode newTree = new ASTNode(null, null, null);
							//����root����㣬�Ա�����ܹ���ȡ����������Ϣ
							newTree.setRootNode(an);
							//Ȼ�������ɵĽڵ���ѹ��
							astNodeStack.push(newTree);
							//����')'
							symbolStack.pop();
						}
					}		
				}else {
					//����ƥ���쳣
					throw new BracketsNotMatchedException(getClass().toString());
				}
			}else if (message.charAt(i) == '|') {
				//ѹ��ջʱ��Ҫ�Ƚ����ȼ�
				ASTNode node = symbolStack.peek();
				char c = node.getToken().getCharacter();
				if (c == '|' || c == '$') {
					//˵��M|N��MN����������|����ʱ��Ҫ�ȹ���M|N��MN���﷨��
					symbolStack.pop();//����| $
					if (astNodeStack.size() != 2) {
						throw new NodeExistException(getClass().toString());
					}else {
						ASTNode leftNode = astNodeStack.pop();
						ASTNode rightNode = astNodeStack.pop();
						node.setLeftChild(leftNode);
						node.setRightChild(rightNode);
						//����������װ��һ��node���
						ASTNode newTree = new ASTNode(null, null, null);
						//����root���
						newTree.setRootNode(node);
						//ѹ�������ɵĽ��ջ
						astNodeStack.push(newTree);
						//�������ջ��ѹ��|
						ASTNode andNode = new ASTNode(new Token('|', true, false)
								, null, null);
						symbolStack.push(andNode);
					}
				}
				else if (c == '(') {
					//��ջ��ֻ��(��ֱ��ѹ��|������
					ASTNode node2 = new ASTNode(new Token('|', true, false)
							, null, null);
					symbolStack.push(node2);
				}
			}else if (message.charAt(i) == '*' || message.charAt(i) == '+' 
					|| message.charAt(i) == '?') {
				//����������Ϊ���ȼ���ߣ����Բ��ý�ջ��ֱ�ӵ���
				//�ṹ��   *
				//       |
				//       N
				if (astNodeStack.size() == 0) {
					throw new NodeExistException(getClass().toString());
				}
				ASTNode charNode = astNodeStack.pop();
				ASTNode symbolNode = new ASTNode(new Token(message.charAt(i), true, false), charNode, null);
				//����������װ��һ��node���
				ASTNode newTree = new ASTNode(null, null, null);
				newTree.setRootNode(symbolNode);
				//ѹ����ջ
				astNodeStack.push(newTree);
			}	
			else {
				//�ַ�[a-zA-Z0-9]��ab���ӣ��м���Ҫ���$
				ASTNode astNode = new ASTNode(new Token(message.charAt(i), false, true)
						, null, null);
				//��i��Ԫ�����ַ����жϵ�i + 1��Ԫ���ǲ����ַ�
				astNodeStack.push(astNode);
			}
		}
		//ջ���п���ʣ��Ԫ��
	}
	//������ű�������ASTNode�е���
	public ASTNode getTree() throws NodeExistException{
		if (astNodeStack.size() != 1 || symbolStack.size() != 0) {
			throw new NodeExistException(getClass().toString());
		}
		return astNodeStack.pop();
	}
}
