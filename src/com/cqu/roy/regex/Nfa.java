package com.cqu.roy.regex;

import java.util.ArrayList;
import java.util.HashMap;

import com.cqu.roy.exception.EndNodeException;
import com.cqu.roy.exception.StartNodeException;

/**
 * ������ֵ�nfa����һ��end����һ��start���
 * @author Roy
 * @date: 2017��3��22��  ����3:48:25
 * version:1.0
 */
public class Nfa {
	private ArrayList<NfaNode> nfa;

	public Nfa() {
		// TODO Auto-generated constructor stub
		nfa = new ArrayList<>();
	}

	/*
	 * @s ��״̬s�Ľڵ�����Ϊ��ʼ�ڵ�
	 */
	public void addStart(String s) {
		if (nfa == null) {
			return;
		}
		getNode(s).start = true;
	}

	// ��״̬s�Ľڵ�����Ϊ��ֹ�ڵ�
	public void addEnd(String s) {
		if (nfa == null) {
			return;
		}
		getNode(s).end = true;
	}

	// ��ȡ��ʼ״̬
	public NfaNode getStart() {
		for (NfaNode node : nfa) {
			if (node.start) {
				return node;
			}
		}
		return null;
	}

	// ��ȡ�ս�״̬
	public NfaNode getEnd() {
		for (NfaNode node : nfa) {
			if (node.end) {
				return node;
			}
		}
		return null;
	}

	// ����״̬��
	public void updateState() {
		for (int i = 0; i < nfa.size(); i++) {
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
	 * like this a* *�հ����㣬�����������
	 */

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
		
		try {
			checkStartNode();
			checkEndNode();
		} catch (StartNodeException | EndNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * like this a+ �հ����� ����1������
	 */
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
		
		try {
			checkEndNode();
			checkStartNode();
		} catch (EndNodeException | StartNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * like this a? �հ����� ����1����0��
	 */
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
		
		try {
			checkStartNode();
			checkEndNode();
		} catch (StartNodeException | EndNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * @param nfa like this ab ˳��Ϊ self->nfa_tmp ��������
	 */
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

		for (NfaNode node : nfa_tmp.getNfaSet()) {
			nfa.add(node);
		}
		updateState();
		
		try {
			checkStartNode();
			checkEndNode();
		} catch (StartNodeException | EndNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @param nfa like this a|b ������,������С��nfa����|�ϳ�
	 */
	public void and(Nfa nfa_tmp) {
		Nfa new_nfa = new Nfa();
		// Ϊ�µ�nfa����һ��start�ڵ�
		new_nfa.addNodeToNfa(new NfaNode("S"));
		new_nfa.addStart("S");
		// Ϊ�µ�nfa����һ��end�ڵ�
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

		// ���Ϲ�����ϵ�����ڽ����еĽڵ�����µ�nfa
		for (NfaNode node : nfa) {
			new_nfa.addNodeToNfa(node);
		}
		for (NfaNode node : nfa_tmp.getNfaSet()) {
			new_nfa.addNodeToNfa(node);
		}
		new_nfa.updateState();
		nfa = new_nfa.getNfaSet();// ˢ�µ�ǰnfa
		//�ı���start����end����check
		try {
			checkStartNode();
			checkEndNode();
		} catch (StartNodeException | EndNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NfaNode getNode(String state) {
		if (nfa == null) {
			return null;
		}
		for (NfaNode node : nfa) {
			if (node.state.equals(state)) {
				return node;
			}
		}
		return null;
	}

	// nfaƥ�䣬��Ҫ��������
	public boolean match_(String target) {
		NfaNode start_node = getStart();
		ArrayList<NfaNode> nodeSet = new ArrayList<>();
		// ��ȡ��һ��û��'\0'����Ľ��,���ڵ�һ��ֻ��һ����㣬����ֱ�����ѻ�ȡȫ������'\0'�ߵĽ��
		dfs(start_node, nodeSet, '\0');

		ArrayList<NfaNode> res_set = new ArrayList<>();
		for (int i = 0; i < target.length(); i++) {
			//״̬ת��
			nodeSet = moveState(nodeSet, target.charAt(i));
			// ��������'\0'�ı�
			ArrayList<NfaNode> arr = new ArrayList<>();
			for (NfaNode node : nodeSet) {
				dfs(node, arr, '\0');
			}
			nodeSet = new ArrayList<>(arr);
			for (NfaNode node : nodeSet) {
				if (node.end) {
					return true;
				}
			}
		}
		ArrayList<NfaNode> tmp_nn = (ArrayList<NfaNode>) nodeSet.clone();
		nodeSet.clear();
		for (NfaNode node : tmp_nn) {
			dfs(node, nodeSet, '\0');
		}

		for (NfaNode node : nodeSet) {
			if (node.end) {
				return true;
			}
		}
		return false;
	}
	//��������Ҫ��������'\0'��
	private void dfs(NfaNode node, ArrayList<NfaNode> nodeSet, char c) {
		if (node == null) {
			return;
		}
		ArrayList<NfaNode> nn = node.getStateTable().get(c);
		if (nn == null) {
			nodeSet.add(node);
			return;
		}
		//���ܳ���һ����Ϊ'\0'����һ����Ϊ'char'����ý��ҲӦ�ü��뼯�ϡ���������
		HashMap<Character, ArrayList<NfaNode>> hm = node.getStateTable();
		if (hm.keySet().size() >= 2) {
			nodeSet.add(node);
		}
		for (NfaNode node2 : nn) {
			dfs(node2, nodeSet, c);
		}
	}
	
	private ArrayList<NfaNode> moveState(ArrayList<NfaNode> nodeSet, char c){
		ArrayList<NfaNode> arr = new ArrayList<>();
		for(NfaNode node : nodeSet){
			ArrayList<NfaNode> tmp = node.getStateTable().get(c);
			if (tmp == null) {
				//��û����cƥ����ַ�����start���add������֤������ֻ��һ��start���
				if (!arr.contains(getStart())) {
					arr.add(getStart());
				}
			}else {
				for(NfaNode node2 : tmp){
					arr.add(node2);
				}
			}
		}
		return arr;
	}

	public int size() {
		return nfa.size();
	}
	
	private void checkStartNode() throws StartNodeException{
		int count = 0;
		for(NfaNode node : nfa){
			if (node.start) {
				count++;
				if (count >= 2) {
					throw new StartNodeException(getClass().toString());
				}
			}
		}
	}
	
	private void checkEndNode() throws EndNodeException{
		int count = 0;
		for(NfaNode node : nfa){
			if (node.end) {
				count++;
				if (count >= 2) {
					throw new EndNodeException(getClass().toString());
				}
			}
		}
	}
}
