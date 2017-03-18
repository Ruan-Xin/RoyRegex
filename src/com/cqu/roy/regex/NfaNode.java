package com.cqu.roy.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NfaNode {
	String state;// ��ǰ�ڵ�״̬��
	// �����ַ�״̬ת�Ʊ�,'\0'ת�ƶ��״̬����ȷ������
	private Map<Character, ArrayList<NfaNode>> stateMoveTable;
	boolean start;
	boolean end;

	public NfaNode(String state) {
		// TODO Auto-generated constructor stub
		stateMoveTable = new HashMap<>();
		this.state = state;
		start = false;
		end = false;
	}

	/*
	 * @param s ���յ��ַ�
	 * 
	 * @param newNode ת�Ƶ�״̬
	 */
	public void addMoveState(Character s, NfaNode newNode) {
		if (stateMoveTable.get(s) == null) {
			// ��һ״̬�ڵ㼯��
			ArrayList<NfaNode> desNode = new ArrayList<>();
			desNode.add(newNode);
			stateMoveTable.put(s, desNode);
		} else {
			ArrayList<NfaNode> desNode = stateMoveTable.get(s);
			desNode.add(newNode);
			stateMoveTable.put(s, desNode);
		}
	}
	
	public HashMap<Character, ArrayList<NfaNode>> getStateTable() {
		return (HashMap<Character, ArrayList<NfaNode>>) stateMoveTable;
	}
}
