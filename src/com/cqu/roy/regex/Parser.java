package com.cqu.roy.regex;

import java.util.Stack;

import com.cqu.roy.exception.BracketsNotMatchedException;
import com.cqu.roy.exception.NodeExistException;
import com.cqu.roy.exception.UncertainException;

/**
 * �����������﷨��
 * @author Roy
 * @date: 2017��3��26��  ����11:09:50
 * version:
 */
public class Parser {
	private Stack<ASTNode> symbolStack;//����ջ
	//*+?�������������ȼ���ߣ������ջ����|��$���ջ��һ�����ڵ����Ƚ�ջ�����ȼ�
	private Stack<ASTNode> astNodeStack;//�﷨�����ջ
	private String message;//��Ҫ�����Ĵ�
	private final static String SS_STRING = "qwertyuioplkjhgfdsazxcvbnm"
			+ "QWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
	private final static int CHARSTATE = 1;
	private final static int SYMBOLSTATE = 2;
	public Parser(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
		symbolStack = new Stack<>();
		astNodeStack = new Stack<>();
	}
	
	/**
	 * �����﷨��,�����������
	 */
	public ASTNode resolveAST() {
		try {
			buildASTree();
		} catch (NodeExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BracketsNotMatchedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (astNodeStack.size() == 0) {
			return null;
		}
		ASTNode root = astNodeStack.pop().getRootNode();
		TreeOpen(root);
		PreTravel(root);
		System.out.println();
		InTravel(root);
		return root;
	}
	/**
	 * ����������ASTNode�е�treeչ��
	 * @param root
	 */
	private void TreeOpen(ASTNode root) {
		if (root == null) {
			return;
		}
		if (root.getRightChild() != null && root.getRightChild().getRootNode() != null) {
			root.setRightChild(root.getRightChild().getRootNode());
			TreeOpen(root.getRightChild());
		}
		if (root.getLeftChild() != null && root.getLeftChild().getRootNode() != null) {
			root.setLeftChild(root.getLeftChild().getRootNode());
			TreeOpen(root.getLeftChild());
		}
	}
	/**
	 * �������
	 * @param root
	 */
	private void PreTravel(ASTNode root){
		if (root == null) {
			return;
		}
		System.out.print(root.getToken().getCharacter());
		PreTravel(root.getLeftChild());
		PreTravel(root.getRightChild());
	}
	/**
	 * �������
	 * @param root
	 */
	private void InTravel(ASTNode root) {
		if (root == null) {
			return;
		}
		InTravel(root.getLeftChild());
		//System.out.println(root.getLeftChild());
		System.out.print(root.getToken().getCharacter());
		InTravel(root.getRightChild());
	}
	/**
	 * �ȹ����﷨������
	 * ��ʾ���ӷ���ʱ��,��message��ab,������Ҫ��bѹ��ջ��ǰ,�Ƚ�$����ѹ�����ջ
	 * �Ե����Ϲ����﷨������
	 * �������ȼ�*=+=?>()>$>|
	 * ?+*)���ĸ������ǲ�������ջ�г��ֵ�,����ջ��ֻ���ܳ��� | $ (��
	 * ����ջ����������������| $��������һ��|��$���ᵼ������
	 * ���з���ջ�е�Ԫ�شӵ׵��������ȼ��������ߣ��ҽ�������
	 */
	public void buildASTree() throws NodeExistException,
	BracketsNotMatchedException{
		//ջ���ж��ٸ������Ŵ�ƥ��
		int left_parenthese = 0;
		for(int i = 0; i < message.length();i++){
			if (message.charAt(i) == '(') {
				//ջ�д���������
				left_parenthese++;
				symbolStack.push(new ASTNode(new Token('(', true, false), null, null,false));
			}else if (message.charAt(i) == ')') {
				if (left_parenthese > 0) {//�����������ţ�
					/**
					 * ���Ų������﷨�����������ȼ��ж�
					 * �����ڴ����������
					 * ()
					 * (N)
					 * (N-M)-�����Ԫ�����
					 */
					//System.out.println(symbolStack.size());
					ASTNode symNode = symbolStack.pop();
					Character c = symNode.getToken().getCharacter();
					if (c == '(') {
						//����()��(N)������������������������������
					}else {
						//c != (  
						//(N-M)���

						//M��N����ɽ��
						if (astNodeStack.size() < 2) {
							throw new NodeExistException(getClass().toString());
						}else {
							
							while(c != '('){
								ASTNode leftNode = astNodeStack.pop();
								ASTNode rightNode = astNodeStack.pop();
								//���Ŵ���ɽ��
								ASTNode an = new ASTNode(new Token(c, 
										true, false), null, null,false);
								//�������
								ASTNode newTree = PackASTNode(an, leftNode, rightNode);
								//Ȼ�������ɵĽڵ���ѹ��
								astNodeStack.push(newTree);
								ASTNode tmp = symbolStack.pop();
								c =  tmp.getToken().getCharacter();
							}
						}
					}
					//ƥ��һ�������ţ���һ
					left_parenthese--;
				}else {
					//����ƥ���쳣
					throw new BracketsNotMatchedException(getClass().toString());
				}
			}else if (message.charAt(i) == '|') {
				//����ʱ����ջ��Ԫ��Ϊ�գ���ֱ��ѹջ����ֱ�Ӻ��������,|���ȼ���ͣ��ײ����
				if (symbolStack.size() == 0) {
					ASTNode symNode = new ASTNode(new Token('|', true, false)
							, null, null,false);
					symbolStack.push(symNode);
					continue;
				}
				//ѹ��ջʱ��Ҫ�Ƚ����ȼ�
				ASTNode node = symbolStack.pop();//���������������node��'('��������Ҫ�ٽ��ý��ѹջ
				char c = node.getToken().getCharacter();
				if (c == '|' || c == '$') {
					//˵��M|N��MN����������|����ʱ��Ҫ�ȹ���M|N��MN���﷨��
					if (astNodeStack.size() < 2) {
						throw new NodeExistException(getClass().toString());
					}else {
						ASTNode leftNode = astNodeStack.pop();
						ASTNode rightNode = astNodeStack.pop();
						ASTNode newTree = PackASTNode(node, leftNode, rightNode);
						//ѹ�������ɵĽ��ջ
						astNodeStack.push(newTree);
						//�������ջ��ѹ��|
						ASTNode andNode = new ASTNode(new Token(message.charAt(i)
								, true, false), null, null,false);
						symbolStack.push(andNode);
					}
				}
				else if (c == '(') {
					//��ջ��ֻ��(��ֱ��ѹ��|������
					ASTNode node2 = new ASTNode(new Token(message.charAt(i)
							, true, false), null, null,false);
					ASTNode leftBra = new ASTNode(new Token('(',
							true, false), null, null,false);
					//��'('ѹջ
					symbolStack.push(leftBra);
					symbolStack.push(node2);
				}else {
					throw new NodeExistException(getClass().toString());
				}
			}else if (message.charAt(i) == '$') {
				//����ջ��ֻ���ܳ���'|'��'*'��Ϊ��
				if (symbolStack.size() == 0) {
					//����ջΪ�գ���'$'ѹջ������������һ���ַ�
					ASTNode astNode = new ASTNode(new Token('$', true, false)
							, null, null,false);
					symbolStack.push(astNode);
					continue;
				}
				ASTNode symNode = symbolStack.pop();
				char c = symNode.getToken().getCharacter();
				if (c == '$') {
					//ջ�д��ڵ���$,��Ϊ$�Ƕ�Ԫ��������ջ�в���������2��Ԫ��
					if (astNodeStack.size() < 2) {
						throw new NodeExistException(getClass().toString());
					}
					//��ջ�д��ڵ�$����ΪM$N���﷨��
					ASTNode leftNode = astNodeStack.pop();
					ASTNode rightNode = astNodeStack.pop();
					//���﷨��������ASTNode
					ASTNode newTree = PackASTNode(symNode, leftNode, rightNode);
					//ѹ����ջ
					astNodeStack.push(newTree);
					//����message.charAt(i)����ɷ��Ž��������ջ��
					ASTNode sNode = new ASTNode(new Token(message.charAt(i)
							, true, false), null, null,false);
					symbolStack.push(sNode);
				}else if (c == '|' || c == '(') {
					//ջ�д�����|,��ʱ�����ҽ�ϣ���Ϊ��֪����Ĳ��������ȼ����
					//����ֻ�ܽ�$�����ѹ��symStack��
					//��ֻ���������ţ�Ҳ��ֱ�Ӵ��ѹ���OK��
					ASTNode sNode = new ASTNode(new Token(message.charAt(i)
							, true, false), null, null,false);
					if (c == '(') {
						//����'('���������ˣ���Ҫ����ѹ��ȥ
						ASTNode leftBra = new ASTNode(new Token('(', true, false),
								null, null,false);
						symbolStack.push(leftBra);
					}else {
						//����'|'���������ˣ���Ҫ����ѹ��ȥ
						ASTNode and = new ASTNode(new Token('|', true, false),
								null, null,false);
						symbolStack.push(and);
					}
					symbolStack.push(sNode);
				}else {
					throw new NodeExistException(getClass().toString());
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
				ASTNode symbolNode = new ASTNode(new Token(message.charAt(i),
						true, false), null, null,false);
				//����������װ��һ��node���
				ASTNode newTree = PackASTNode(symbolNode, charNode);
				//ѹ����ջ
				astNodeStack.push(newTree);
			}	
			else {
				//�ַ�[a-zA-Z0-9]��ab���ӣ��м���Ҫ���$
				ASTNode astNode = new ASTNode(new Token(message.charAt(i)
						, false, true), null, null,true);
				//������ǰ����ջ�У���Ϊ��ջ�д���$������ַ����
				astNodeStack.push(astNode);
				//��i��Ԫ�����ַ����жϵ�i + 1��Ԫ���ǲ����ַ�
				try {
					if (i + 1 < message.length() && 
							getState(message.charAt(i + 1)) == CHARSTATE) {
						//˵������i��i+1�����ַ�������Ҫ��symbolStack��push $ ASTNode
						//��push֮ǰ����Ҫ�����stack�з��ŵ����ȼ�
						if (symbolStack.size() == 0) {
							//������ջ��Ϊ�գ���ֱ��ѹ��
							ASTNode symNode = new ASTNode(new Token('$', true, false),
									null, null,false);
							symbolStack.push(symNode); 
						}else {
							ASTNode sNode = symbolStack.pop();
							char c = sNode.getToken().getCharacter();
							if (c == '$') {
								//��ջ�е������ӷ��ţ�����Ҫ����
								if (astNodeStack.size() < 2) {
									throw new NodeExistException(getClass().toString());
								}
								//�������ϵĽ�㣬������ѹ��ջ��
								ASTNode leftNode = astNodeStack.pop();
								ASTNode rightNode = astNodeStack.pop();
								ASTNode newTree = PackASTNode(sNode, leftNode, rightNode);
								astNodeStack.push(newTree);
								
								//��$ѹ��ջ��
								ASTNode node = new ASTNode(new Token('$',
										true, false), null, null, false);
								symbolStack.push(node);
							}else if (c == '|' || c == '(') {
								//��sNodeѹ��ջ��
								symbolStack.push(sNode);
								
								//��ջ�е���| �� (���ţ���ֱ��ѹ�����
								ASTNode node = new ASTNode(new Token('$',
										true, false), null, null, false);
								symbolStack.push(node);
							}
						}
					}
				} catch (UncertainException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//ջ���п���ʣ��Ԫ��,����*?+�������Ÿ��������ջ��
		if (left_parenthese > 0) {
			//�ж�������ƥ����û�У���δƥ���꣬���׳��쳣
			throw new BracketsNotMatchedException(getClass().toString());
		}else {
			while(symbolStack.size() != 0){
				//ջ�У��������ȼ�һ�����ڵײ������ȼ�
				ASTNode newTree = PackASTNode(symbolStack.pop()
						, astNodeStack.pop(), astNodeStack.pop());
				astNodeStack.push(newTree);
			}
		}
		//�����ڽ��ջ��ֻ��ʣ��һ��������ASTNode������������
	}
	/**
	 * ������������ASTNode�е������﷨��
	 * @return
	 * @throws NodeExistException
	 */
	public ASTNode getTree() throws NodeExistException{
		if (astNodeStack.size() != 1 || symbolStack.size() != 0) {
			throw new NodeExistException(getClass().toString());
		}
		return astNodeStack.pop();
	}
	
	/**
	 * �жϸ��ַ��Ƿ��Ż��������ַ�
	 * @param c
	 * @return
	 */
	public int getState(char c) throws UncertainException{
		String charSeq = "qwertyuioplkjhgfdsazxcvbnm"
			+ "QWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
		String symbolSeq = "$*?+|()";
		for(int i = 0; i < charSeq.length();i++){
			if (charSeq.charAt(i) == c) {
				return CHARSTATE;
			}
		}
		for(int i = 0; i < symbolSeq.length();i++){
			if (symbolSeq.charAt(i) == c) {
				return SYMBOLSTATE;
			}
		}
		throw new UncertainException(getClass().toString());
	}
	
	/**
	 * | $ ��Ԫ���Ĵ����������һ�ż򵥵��﷨�������һ��ASTNode
	 * @param symNode 
	 * @param left
	 * @param right
	 */
	private ASTNode PackASTNode(ASTNode symNode,ASTNode left,ASTNode right){
		symNode.setLeftChild(left);
		symNode.setRightChild(right);
		ASTNode newTree = new ASTNode(null, null, null,false);
		newTree.setRootNode(symNode);
		return newTree;
	}
	/**
	 * *?+һԪ�������������һ�ż򵥵��﷨�������һ��ASTNode
	 * @param symNode
	 * @param left
	 * @return
	 */
	private ASTNode PackASTNode(ASTNode symNode, ASTNode left){
		symNode.setLeftChild(left);
		ASTNode newTree = new ASTNode(null, null, null,false);
		newTree.setRootNode(symNode);
		return newTree;
	}
	/**
	 * �ж�c�Ƿ�����[0-9A-Za-z]
	 * @param c
	 */
	private boolean checkChar(char c){
		for(int i = 0; i < SS_STRING.length();i++){
			if (c == SS_STRING.charAt(i)) {
				return true;
			}
		}
		return false;
	}
}
