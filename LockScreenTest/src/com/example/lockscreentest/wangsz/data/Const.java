package com.example.lockscreentest.wangsz.data;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Const {
	//��Ŀ�±�
	public static final int QUESTION_INDEX = 0;
	//���±�
	public static final int ANSWER_INDEX = 1; 
	//������ѡ��
	public static final int WRONG_INDEX = 2;
	//��ʼ�ؿ�
	public static final int BEGIN_STAGE = -1; 
	//��ʼ���
	public static final int TOTAL_COINT = 10; 
	//
	public static final String FILE_NAME_SAVE_DATA = "data.dat";
	//����Ĺؿ��±�
	public static final int INDEX_LOAD_DATA_STAGE = 0;
	//����Ľ���±�
	public static final int INDEX_LOAD_DATA_COINS = 1;
	//
	public static final String IS_FIRST_TIME_GAME = "is_first_time_game";
	//����+��
	public static final String[][] QUESTION_ANSWER = {
		{"bds.png","Ϊ��","����"},
		{"cmqz.png","����",""},
		{"bls.png","�Է�","ϴ��"},
		{"pkq.png","��Ӿ",""},
		{"ssbls.png","˯��","�Ȳ�"},
		{"xd.png","����","�̲�"},
		{"yb.png","���","С��"},
		{"lcf.png","����","����"},
		{"xch.png","����","ɽ��"},
		{"sjs.png","ǹ��","�Ϸ�"}	  
	};
	
	//��ʾ
	public static final String[] ANSWER_TYPE = {
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
		"ҩ��",
	};
	
}
