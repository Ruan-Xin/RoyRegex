package com.cqu.roy.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Dfa {
	private ArrayList<DfaNode> dfa;
	private Nfa nfa;
	
	public Dfa(Nfa nfa) {
		// TODO Auto-generated constructor stub
		dfa = new ArrayList<>();
		this.nfa = nfa;
	}
	
	public DfaNode getStart() {
		for(DfaNode node : dfa){
			if (node.start) {
				return node;
			}
		}
		return null;
	}
	
	public DfaNode getEnd() {
		for(DfaNode node : dfa){
			if (node.end) {
				return node;
			}
		}
		return null;
	}
	
	public void addNodeToNfa(DfaNode node) {
		if (dfa == null) {
			return;
		}
		dfa.add(node);
	}
	
	public ArrayList<DfaNode> getNodeSet() {
		return dfa;
	}
	
	//ͨ���Ӽ����취������dfa״̬����
	public void SubsetConstruction() {
		NfaNode nfaNode = nfa.getStart();
		//��һ��ͨ��'\0'�����ɵ��Ӽ�Ϊ��һ��״̬
		DfaNode dfaStartNode = new DfaNode();
		dfaStartNode.start = true;//start״̬
		ArrayList<NfaNode> nfaNodesSet = dfaStartNode.getNfaNodesSet();
		dfs('\0', nfaNode, nfaNodesSet);
		dfaStartNode.checkEnd();
		dfa.add(dfaStartNode);//����һ��dfanode���뼯��
		
		HashSet<Character> charCache = new HashSet<>();//����Ҫ���ַ����������������ò����ظ�
		for(NfaNode node : nfaNodesSet){
			HashMap<Character, ArrayList<NfaNode>> stateMoveTable = node.getStateTable();
			for(Character c : stateMoveTable.keySet()){
				charCache.add(c);
			}
		}
	}
	
	public void dfs(char c,NfaNode node,ArrayList<NfaNode> nfaNodeSet) {
		if (node == null) {
			return;
		}
		ArrayList<NfaNode> nfaNodes = node.getStateTable().get('\0');
		if (nfaNodes == null) {
			nfaNodeSet.add(node);
			return;
		}
		for(NfaNode node2 : nfaNodes){
			dfs(c, node2, nfaNodeSet);
			if (node2 != null) {
				nfaNodeSet.add(node2);
			}
		}
	}
}
