package com.cqu.roy.regex;

import java.util.ArrayList;
import java.util.HashMap;

import com.cqu.roy.exception.UncertainException;
/**
 * Dfa��node
 * @author Roy
 * @date: 2017��3��22��  ����3:36:19
 * version:1.0
 */
public class DfaNode {
	private String state = null;
	//ÿ��������״̬��,������ȷ��״̬��ÿ�������Ӧһ����
	private HashMap<Character, DfaNode> stateMoveTable;
	public boolean start;
	public boolean end;
	private ArrayList<NfaNode> nfaNodes;//�Ӽ����취��dfa�������洢��nfa���
	
	public DfaNode() {
		// TODO Auto-generated constructor stub
		stateMoveTable = new HashMap<>();
		nfaNodes = new ArrayList<>();
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
	
	//�ж�nfa�������Ƿ���end״̬�������򽫸�dfa����״̬����Ϊend
	public void checkEnd() {
		for(NfaNode node : nfaNodes){
			if (node.end) {
				end = true;
			}
		}
	}
	
	public ArrayList<NfaNode> getNfaNodesSet() {
		return nfaNodes;
	}
}
