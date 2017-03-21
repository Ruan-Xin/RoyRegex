package com.cqu.roy.regex;

import java.util.HashMap;

import com.cqu.roy.exception.UncertainException;

public class DfaNode {
	private String state = null;
	//ÿ��������״̬��,������ȷ��״̬��ÿ�������Ӧһ����
	private HashMap<Character, DfaNode> stateMoveTable;
	public boolean start;
	public boolean end;
	
	public DfaNode(String state) {
		// TODO Auto-generated constructor stub
		this.state = state;
		stateMoveTable = new HashMap<>();
		start = false;
		end = false;
	}
	
	public void addMoveTable (Character c, DfaNode node) throws UncertainException {
		if (stateMoveTable.get(c) != null) {
				throw new UncertainException(getClass().toString());
		}
		stateMoveTable.put(c, node);
	}
	public HashMap<Character, DfaNode> getMoveTable() {
		return stateMoveTable;
	}
	
}
