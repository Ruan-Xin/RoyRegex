package com.cqu.roy.regex;

import java.util.ArrayList;
import java.util.Stack;

/*@author royruan
 * @date 2017/3/18
 * */
public class Nfa {
	private ArrayList<NfaNode> nfa;

	
	public Nfa() {
		// TODO Auto-generated constructor stub
		nfa = new ArrayList<>();
	}
	/*
	 * @s
	 *��״̬s�Ľڵ�����Ϊ��ʼ�ڵ� */
	public void addStart(String s){
		if (nfa == null) {
			return;
		}
		getNode(s).start = true;
	}
	
	//��״̬s�Ľڵ�����Ϊ��ֹ�ڵ�
	public void addEnd(String s) {
		if (nfa == null) {
			return;
		}
		getNode(s).end = true;
	}
	//��ȡ��ʼ״̬
	public NfaNode getStart(){
		for(NfaNode node : nfa){
			if (node.start) {
				return node;
			}
		}
		return null;
	}
	
	//��ȡ�ս�״̬
	public NfaNode getEnd() {
		for(NfaNode node : nfa){
			if (node.end) {
				return node;
			}
		}
		return null;
	}
	
	//����״̬��
	public void updateState(){
		for(int i = 0; i < nfa.size();i++){
			nfa.get(i).state = String.valueOf(i);
		}
	}
	public void addNodeToNfa(String state) {
		if (nfa == null) {
			return;
		}
		nfa.add(new NfaNode(state));
	}
	private void addNodeToNfa(NfaNode node) {
		if (nfa == null) {
			return;
		}
		nfa.add(node);
	}
	
	public ArrayList<NfaNode> getNfaSet() {
		return nfa;
	}
	/*
	 * like this a* 
	 * *�հ����㣬�����������*/
	
	public void closure_1() {
		if (nfa.size() == 0) {
			return;
		}
		NfaNode node_start = getStart();
		NfaNode node_end = getEnd();
		node_start.start = false;
		node_end.end = false;
		
		addNodeToNfa(new NfaNode("S"));
		addNodeToNfa(new NfaNode("E"));
		
		addStart("S");
		addEnd("E");
		
		NfaNode new_Start = getStart();
		NfaNode new_end = getEnd();
		
		new_Start.addMoveState('\0', node_start);
		new_Start.addMoveState('\0', new_end);
		
		node_end.addMoveState('\0', node_start);
		node_end.addMoveState('\0', new_end);
	
		updateState();
	}
	
	/*
	 * like this a+
	 * �հ����� ����1������*/
	public void closure_2() {
		if (nfa.size() == 0) {
			return;
		}
		NfaNode node_start = getStart();
		NfaNode node_end = getEnd();
		
		node_start.start = false;
		node_end.end = false;
		
		addNodeToNfa(new NfaNode("S"));
		addNodeToNfa(new NfaNode("E"));
		addStart("S");
		addEnd("E");
		
		NfaNode new_Start = getStart();
		NfaNode new_end = getEnd();
		
		new_Start.addMoveState('\0', node_start);
		node_end.addMoveState('\0', new_end);
		node_end.addMoveState('\0', node_start);
		
		updateState();
	}
	/*
	 * like this a?
	 * �հ����� ����1����0��*/
	public void closure_3() {
		if (nfa.size() == 0) {
			return;
		}
		NfaNode node_Start = getStart();
		NfaNode node_End = getEnd();
		node_Start.start = false;
		node_End.end = false;
		
		addNodeToNfa(new NfaNode("S"));
		addNodeToNfa(new NfaNode("E"));
		
		addStart("S");
		addEnd("E");
		
		NfaNode new_Start = getStart();
		NfaNode new_End = getEnd();
		
		new_Start.addMoveState('\0', node_Start);
		new_Start.addMoveState('\0', node_End);
		node_End.addMoveState('\0', new_End);
		
		updateState();
		
	}
	/*
	 * @param nfa
	 * like this ab
	 * ˳��Ϊ self->nfa_tmp
	 * ��������*/
	public void connect(Nfa nfa_tmp) {
		if (nfa_tmp.getNfaSet().size() == 0) {
			return;
		}
		if (nfa.size() == 0) {
			nfa = nfa_tmp.getNfaSet();
			return;
		}
		
		NfaNode s1 = nfa_tmp.getStart();
		s1.start = false;
		
		NfaNode e0 = getEnd();
		e0.end = false;
		
		e0.addMoveState('\0', s1);
		
		for(NfaNode node : nfa_tmp.getNfaSet()){
			nfa.add(node);
		}
	}
	
	/*
	 * @param nfa
	 * like this a|b
	 * ������,������С��nfa����|�ϳ�*/
	public void and(Nfa nfa_tmp){
		Nfa new_nfa = new Nfa();
		//Ϊ�µ�nfa����һ��start�ڵ�
		new_nfa.addNodeToNfa(new NfaNode("S"));
		new_nfa.addStart("S");
		//Ϊ�µ�nfa����һ��end�ڵ�
		new_nfa.addNodeToNfa(new NfaNode("E"));
		new_nfa.addEnd("E");
		
		NfaNode s0 = getStart();
		NfaNode s1 = nfa_tmp.getStart();
		s0.start = false;
		s1.start = false;
		NfaNode new_start = new_nfa.getStart();
		new_start.addMoveState('\0', s0);
		new_start.addMoveState('\0', s1);
		
		NfaNode e0 = getEnd();
		NfaNode e1 = nfa_tmp.getEnd();
		e0.end = false;
		e1.end = false;
		NfaNode new_end = new_nfa.getEnd();
		e0.addMoveState('\0', new_end);
		e1.addMoveState('\0', new_end);
		
		//���Ϲ�����ϵ�����ڽ����еĽڵ�����µ�nfa
		for(NfaNode node : nfa){
			new_nfa.addNodeToNfa(node);
		}
		for(NfaNode node : nfa_tmp.getNfaSet()){
			new_nfa.addNodeToNfa(node);
		}
		new_nfa.updateState();
		nfa = new_nfa.getNfaSet();//ˢ�µ�ǰnfa
	}
	public NfaNode getNode(String state){
		if (nfa == null) {
			return null;
		}
		for(NfaNode node: nfa){
			if (node.state.equals(state)) {
				return node;
			}
		}
		return null;
	}
	//nfaƥ�䣬��Ҫ��������
	//something wrong
	public boolean match(String using_case) {
		NfaNode start_node = getStart();
		//��ǰ״̬����
		ArrayList<NfaNode> new_state = new ArrayList<>();
		/*nfa start��input Ϊ'\0'ʱ��ת�Ƶ�״̬����
		 * ��startֻ��һ��״̬*/
		ArrayList<NfaNode> nfaNodes = start_node.getStateTable().get('\0');;
		boolean empty = true;
		while(empty){
			empty = false;
			ArrayList<NfaNode> tmp = new ArrayList<>();
			for(NfaNode node : nfaNodes){
				ArrayList<NfaNode> nn = node.getStateTable().get('\0');
				if (nn != null) {
					for(NfaNode node2 : nn){
						tmp.add(node2);
					}
					empty = true;
				}
			}
		}
		
		new_state = nfaNodes;
		for(int i = 0; i < using_case.length();i++){
			ArrayList<NfaNode> myNfa = new ArrayList<>();
			for(int j = 0; j < new_state.size();j++){
				if (new_state.get(j).getStateTable().get(using_case.charAt(i)) != null) {
					ArrayList<NfaNode> nn = new_state.get(j).getStateTable().get(using_case.charAt(i));
					
					for(int k = 0; k < nn.size();k++){
						myNfa.add(nn.get(k));
					}
				}
			}
			new_state = myNfa;
		}
		while(true){
			ArrayList<NfaNode> restNode = new ArrayList<>();
			for(int i = 0; i < new_state.size();i++){
				//'\0'�հ��������һ��״̬
				ArrayList<NfaNode> node = new_state.get(i).getStateTable().get('\0');
				if (node == null) {
					continue;
				}
				for(int j = 0; j < node.size();j++){
					if (node.get(j).end) {
						return true;
					}
					restNode.add(node.get(j));
				}
			}
			if (restNode.size() == 0) {
				break;
			}
			new_state = restNode;
		}
		return false;
	}
	public int size() {
		return nfa.size();
	}
}
