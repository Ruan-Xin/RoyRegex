package com.cqu.roy.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.cqu.roy.exception.UncertainException;
/**
 * ͨ���Ӽ����취��nfa�����dfa
 * @author Roy
 * @date: 2017��3��23��  ����4:33:47
 * version:
 */
public class Dfa {
	private ArrayList<DfaNode> dfa;
	private Nfa nfa;

	public Dfa(Nfa nfa) {
		// TODO Auto-generated constructor stub
		dfa = new ArrayList<>();
		this.nfa = nfa;
	}

	public DfaNode getStart() {
		for (DfaNode node : dfa) {
			if (node.start) {
				return node;
			}
		}
		return null;
	}
	/**
	 * ����dfa���ܲ�ֹһ����ֹ��㣬���Է���һ����ֹ���ļ���
	 */
	public ArrayList<DfaNode> getEnd() {
		ArrayList<DfaNode> dfaEndSet = new ArrayList<>();
		for (DfaNode node : dfa) {
			if (node.end) {
				dfaEndSet.add(node);
			}
		}
		return dfaEndSet;
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

	/**
	 * ����dfa
	 */
	public void generaterDfa() {
		NfaNode nfaNode = nfa.getStart();
		// ��һ��ͨ��'\0'�����ɵ��Ӽ�Ϊ��һ��״̬
		DfaNode dfaStartNode = new DfaNode();
		dfaStartNode.start = true;// start״̬
		/**
		 * nfaNodes��dfa���ӽ�㼯�ϣ�ÿ����ͨ��һ���ַ�ת�ƾ��ܵ����״̬
		 * ������ӣ����dfanode һ��״̬���
		 */
		ArrayList<NfaNode> nfaNodesSet = dfaStartNode.getNfaNodesSet();
		
		/**
		 * ���Ѳ���dfa���Ӽ�״̬���
		 */
		nfaNodesSet.add(nfaNode);//dfsState()���ܷ����ʼ���
		dfsState('\0', nfaNode, nfaNodesSet);
		
		/**
		 * �Ƿ�����ս���
		 */
		dfaStartNode.checkEnd();
		dfa.add(dfaStartNode);
		
		/**
		 * inputNode��ֻת�Ʊ�ֻ����'\0'�ߵĽ�㣬���������������
		 */
		ArrayList<NfaNode> inputNode = new ArrayList<>();
		dfsInput('\0', nfaNode, inputNode);

		SubsetConstruction(inputNode, dfaStartNode);
	}

	/**
	 * ͨ���Ӽ����취������dfa״̬����
	 * @param nfaNodesSet ��ǰ�ܽ���input��nfa����״̬�ļ���
	 * @param dfaNode     ��ǰdfaNode,�������Ա㽨����
	 */
	public void SubsetConstruction(ArrayList<NfaNode> nfaNodesSet,
			DfaNode dfaNode) {
		/**
		 * ����Ҫ���ַ���������
		 */
		HashSet<Character> charCache = new HashSet<>();
		for (NfaNode node : nfaNodesSet) {
			HashMap<Character, ArrayList<NfaNode>> stateMoveTable = node.getStateTable();
			for (Character c : stateMoveTable.keySet()) {
				/**
				 * ������'\0'�ߵ�ת�ƽ��
				 */
				if (c != '\0') {
					charCache.add(c);
				}
			}
		}
		
		/**
		 * ���set�����л�����Ϊ0����˵�������ڽ���б��ˣ���ֹ
		 */
		if (charCache.size() == 0) {
			return;
		}
		/**
		 * ����ط�Ӧ�ÿ����Ż� nn�������ַ�ת�ƺ��node��
		 * �Ա��������'\0'���ѣ�ͬ���ᴦ����ͬ�ļ���
		 */
		ArrayList<NfaNode> nn = new ArrayList<>();
		for (Character c : charCache) {
			// ÿһ��dfanode����������nfanode����
			ArrayList<NfaNode> arr = new ArrayList<>();
			for (NfaNode node : nfaNodesSet) {
				HashMap<Character, ArrayList<NfaNode>> hm = node.getStateTable();
				/**
				 * ����·��
				 */
				if (hm.get(c) != null) {
					ArrayList<NfaNode> nno = hm.get(c);
					for(NfaNode node2 : nno){
						arr.add(node2);
						dfsState('\0', node2, arr);
					}
				}
			}
			/**
			 * ����dfa���
			 */
			DfaNode dNode = new DfaNode();
			dNode.setNfaNodesSet(arr);
			dNode.checkEnd();
			/**
			 * ���ñ߹�ϵ
			 */
			try {
				dfaNode.addMoveTable(c, dNode);
			} catch (UncertainException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/**
			 * ��һ�ο�ʼ�Ľ�㼯��
			 */
			ArrayList<NfaNode> noo = new ArrayList<>();
					
			for(NfaNode node : nfaNodesSet){
				dfsInput('\0', node, noo);
			}
			
			SubsetConstruction(noo, dfaNode);
		}
	}
	
	/**
	 *
	 * ���������������dfa��״̬�Ӽ����
	 */
	private void dfsState(char c, NfaNode node, ArrayList<NfaNode> nfaNodeSet) {
		if (node == null) {
			return;
		}
		ArrayList<NfaNode> nfaNodes = node.getStateTable().get('\0');
		if (nfaNodes == null) {
			nfaNodeSet.add(node);
			return;
		}
		for (NfaNode node2 : nfaNodes) {
			dfsState(c, node2, nfaNodeSet);
			if (node2 != null) {
				nfaNodeSet.add(node2);
			}
		}
	}
	/**
	 * ���������������input�Ľ��
	 * @param c��ֵһ��Ϊ'\0'
	 */
	
	private void dfsInput(char c, NfaNode node,ArrayList<NfaNode> inputNodes) {
		if (node == null) {
			return;
		}
		
		ArrayList<NfaNode> nn = node.getStateTable().get(c);
		//���ѵľ�ͷ
		if (nn == null) {
			inputNodes.add(node);
			return;
		}
		
		HashMap<Character, ArrayList<NfaNode>> hm = node.getStateTable();
		/*
		 * ת�Ʊ���ܻ��һ��'\0'������Ϊcharacter
		 */
		if (hm.size() >= 2) {
			inputNodes.add(node);
		}
		
		for(NfaNode node2 : inputNodes){
			dfsInput(c, node2, inputNodes);
		}
	}
	
	/**
	 * ʹ��dfa״̬ͼƥ���ַ���
	 * @param s
	 */
	public boolean match(String s) {
		DfaNode tmpNode = new DfaNode(getStart());
		for(int i = 0; i < s.length();i++){
			HashMap<Character, DfaNode> moveStateTable = tmpNode.getMoveTable();
			/**
			 * ����·�������н��ת��
			 * ������·��������startNode
			 */
			if (moveStateTable.get(s.charAt(i)) != null) {
				tmpNode = moveStateTable.get(s.charAt(i));
			}else {
				tmpNode = getStart();
			}
		}
		/**
		 *�ж��Ƿ�����ֹ���
		 */
		if (tmpNode.end) {
			return true;
		}
		return false;
	}
}
