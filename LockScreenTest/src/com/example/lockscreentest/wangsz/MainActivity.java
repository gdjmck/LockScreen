package com.example.lockscreentest.wangsz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.data.Const;
import com.example.lockscreentest.wangsz.model.IAlertDialogButtonListener;
import com.example.lockscreentest.wangsz.model.IWordButtonClickListener;
import com.example.lockscreentest.wangsz.model.Question;
import com.example.lockscreentest.wangsz.model.WordButton;
import com.example.lockscreentest.wangsz.myui.MyGridView;
import com.example.lockscreentest.wangsz.util.Util;

public class MainActivity extends Activity implements IWordButtonClickListener,
		OnClickListener {

	/** �?��??��??�???? */
	public final static int STATUS_ANSWER_RIGHT = 1;
	/** �?��??����???�? */
	public final static int STATUS_ANSWER_WRONG = 2;
	/** �?��??��??��?�����? */
	public final static int STATUS_ANSWER_LACK = 3;

	/** �������????�� */
	public final static int SPASH_TIMES = 4;

	/** ������??��???����???��?��??���?�����?�? */
	public final static int ID_DIALOG_DELETE_WORD = 4;
	/** �?��??�����????��??���?�����?�? */
	public final static int ID_DIALOG_TIP_ANSWER = 5;
	/** ����??��??��????���?���?�����?�? */
	public final static int ID_DIALOG_LACK_COINS = 6;
	/** ������????����??��??���?�? */
	public final static int ID_DIALOG_ALT_F4 = 7;

	// ����??��??��?��?���?
	private ArrayList<WordButton> mAllWords;

	// 宸�?������???��??�?
	private ArrayList<WordButton> mBtnSelectWords;

	// 寰�������???��??��??��??�?
	private MyGridView mMyGridView;

	// 宸�?������??��???��??�?UI??��?���?
	private LinearLayout mViewWordsContainer;

	// ??�����?�����?
	private View mPassView;

	// 褰����棰����
	private Question mCurrentQuestion;

	// 褰�������????��??�?
	private int mCurrentStageIndex = -1;

	// 褰�������???���伴��
	private int mCurrentCoins = Const.TOTAL_COINT;

	// ??�����?View
	private ImageButton mBack;
	// ����??�?View
	private TextView mViewCurrentCoins;
	//��???��浣����?
	private ImageButton mAboutAuthor;
	//��???��??�����?
	private ImageButton mStagesPics;
	// ������??��???��??��??�?View
	private ImageButton mViewDelete;
	// �?��??�����?�?View
	private ImageButton mViewTip;
	// 棰�����?���???����??�?
	private ImageButton mViewShare;
	// �???������??��������????��??�?
	private TextView mCurrentStageView;
	// 棰����?
	private ImageView mCurrentQuestionBG;
	// ����??�?
	private TextView mCurrentQuestionTip;

	// ??�����?�����?
	// 褰�������????��??����?��??
	private TextView mCurrentStagePassView;
	// 褰�������??���?�?
	private TextView mCurrentAnswerPassView;
	// �?��??����View
	private ImageButton mViewNext;
	// 寰��???����??�?View
	private ImageButton mViewSharedWeixin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ???诲���??��??����??��
		int[] datas = Util.loadData(this);
		mCurrentStageIndex = datas[Const.INDEX_LOAD_DATA_STAGE];
		mCurrentCoins = datas[Const.INDEX_LOAD_DATA_COINS];

		// ���濮������??�?
		mBack = (ImageButton) findViewById(R.id.back);
		mBack.setOnClickListener(this);

		mAboutAuthor = (ImageButton) findViewById(R.id.about_author);
		mAboutAuthor.setOnClickListener(this);
		
		mStagesPics = (ImageButton) findViewById(R.id.stage_index_pics);
		mStagesPics.setOnClickListener(this);
		
		mMyGridView = (MyGridView) findViewById(R.id.gridview);
		// �???���������?
		mMyGridView.registOnWordButtonClickListener(this);

		mViewWordsContainer = (LinearLayout) findViewById(R.id.word_select_container);

		mViewCurrentCoins = (TextView) findViewById(R.id.money);
		mViewCurrentCoins.setText(mCurrentCoins + "");

		mCurrentQuestionBG = (ImageView) findViewById(R.id.imgv_question_bg);
		mCurrentQuestionTip = (TextView) findViewById(R.id.tv_tip_for_answer);

		// ������??��???��??��??��??��??�?
		mViewDelete = (ImageButton) findViewById(R.id.btn_delete_word);
		mViewDelete.setOnClickListener(this);
		// �?��??�����?轰���??�?
		mViewTip = (ImageButton) findViewById(R.id.btn_tip_answer);
		mViewTip.setOnClickListener(this);
		// 棰�����?���???����??�?
		mViewShare = (ImageButton) findViewById(R.id.imgbtn_question_share);
		mViewShare.setOnClickListener(this);

		// ���濮�����????����???��
		initCurrentStageData();

	}

	@Override
	public void onPause() {
		super.onPause();
		// �?��??����??��
		Util.saveData(MainActivity.this, mCurrentStageIndex - 1, mCurrentCoins);
	}

	/**
	 * ����?????��������??��??��
	 */
	private void initCurrentStageData() {
		// ???诲��褰������??��������???��
		mCurrentQuestion = loadStageQuestionInfo(++mCurrentStageIndex);
		// 褰�������?������+����??�?
		setQuestion(mCurrentQuestion);
		// ���濮����宸�?������??��
		mBtnSelectWords = initWordSelect();

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// �?��??轰���??����??���?�?
		mViewWordsContainer.removeAllViews();
		// ���濮����������??���?����???��??�?
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton,
					params);
		}

		// ��峰��褰�������???��??�?
		mCurrentStageView = (TextView) findViewById(R.id.tx_current_stage);
		if (mCurrentStageView != null) {
			mCurrentStageView.setText((mCurrentStageIndex + 1) + "");
		}
		// ��峰�����??��
		mAllWords = initAllWord();
		// ���?��??��??��- MyGridView
		mMyGridView.updateData(mAllWords);
	}

	/**
	 * 褰�������?��������??��??+����?????��?��??
	 * 
	 * @param mCurrentQuestion
	 */
	private void setQuestion(Question mCurrentQuestion) {
		if (mCurrentQuestionBG != null) {
			mCurrentQuestionBG
					.setImageBitmap(getCurrentQuestionBG(mCurrentQuestion));
		}

		if (mCurrentQuestionTip != null) {
			mCurrentQuestionTip.setText(mCurrentQuestion.getType());
		}
	}

	/**
	 * 寰���??��������??��??��??������?���?
	 * 
	 * @param mCurrentQuestion
	 * @return
	 */
	private Bitmap getCurrentQuestionBG(Question mCurrentQuestion) {
		AssetManager assets = getAssets();
		InputStream is = null;
		try {
			is = assets.open(mCurrentQuestion.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
		return bitmap;
	}

	/**
	 * ��峰��褰������??������
	 * 
	 * @param stageIndex
	 * @return
	 */
	private Question loadStageQuestionInfo(int stageIndex) {
		Question question = new Question();

		String[] stage = Const.QUESTION_ANSWER[stageIndex];
		question.setContent(stage[Const.QUESTION_INDEX]);
		question.setAnswer(stage[Const.ANSWER_INDEX]);
		question.setWrongAnswer(stage[Const.WRONG_INDEX]);
		question.setType(Const.ANSWER_TYPE[stageIndex]);

		return question;
	}

	/**
	 * ���濮����寰�������???��??�?
	 */
	private ArrayList<WordButton> initAllWord() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();

		// ��峰���������?��������???�?
		String[] words = generateWords();
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			WordButton button = new WordButton();

			button.mWordString = words[i];

			data.add(button);
		}

		return data;
	}

	/**
	 * ���濮����宸�?������??��???��??�?
	 * 
	 * @return
	 */
	private ArrayList<WordButton> initWordSelect() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();

		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			View view = Util.getView(MainActivity.this,
					R.layout.self_ui_gridview_item);

			final WordButton holder = new WordButton();

			holder.mViewButton = (Button) view.findViewById(R.id.item_btn);
			holder.mViewButton.setTextColor(Color.WHITE);
			holder.mViewButton.setText("");
			holder.mIsVisiable = false;

			holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
			holder.mViewButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					clearTheAnswer(holder);
					// �?��??����???����?������?���?��???����??������??��??����?????��??����??��????��???��(checkResultIndex ==
					// STATUS_ANSWER_LACK)
					checkResult();
				}

			});

			data.add(holder);
		}
		return data;
	}

	// 寰�������???��??����?��讳���??�?
	@Override
	public void onWordButtonClick(WordButton wordButton) {
		// ?????����?��??������??��??����
		int mount = 0;
		// 宸�?������???��??��??����
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			if ("".equals(mBtnSelectWords.get(i).mWordString)) {
				mBtnSelectWords.get(i).mViewButton
						.setText(wordButton.mWordString);
				mBtnSelectWords.get(i).mIsVisiable = true;
				mBtnSelectWords.get(i).mWordString = wordButton.mWordString;
				mBtnSelectWords.get(i).mIndex = wordButton.mIndex;

				break;
			} else {
				mount++;
			}
		}

		if (mount < mBtnSelectWords.size()) {
			// 寰�������???��??��????��??
			/*
			 * wordButton.mViewButton.setVisibility(View.INVISIBLE);
			 * wordButton.mIsVisiable = false;
			 */
			setButtonVisiable(wordButton, View.INVISIBLE);
		}

		// �?��??�澶����
		checkResult();

	}

	/**
	 * �?��??����?���妫�娴����澶����?
	 * 
	 */
	private void checkResult() {
		// ��峰���??��??����?���?
		int checkResultIndex = checkTheAnswer();

		// ��??���澶����
		if (checkResultIndex == STATUS_ANSWER_RIGHT) {
			// ??�����?+��峰���??����
			handlePassEvent();
		} else if (checkResultIndex == STATUS_ANSWER_WRONG) {
			// ����������??�?
			sparkTheWords();
		} else if (checkResultIndex == STATUS_ANSWER_LACK) {
			// ?????��??����??��????��???��
			for (int i = 0; i < mBtnSelectWords.size(); i++) {
				mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
			}
		}
	}

	/**
	 * 澶�����?�����?����???���?��??�?
	 */
	private void handlePassEvent() {
		mPassView = (LinearLayout) this.findViewById(R.id.pass_view);
		mPassView.setVisibility(View.VISIBLE);

		// ��???�����?������??��????��??��???��+�?��??�? ��??��??
		mCurrentStagePassView = (TextView) findViewById(R.id.stage_index);
		if (mCurrentStagePassView != null) {
			mCurrentStagePassView.setText((mCurrentStageIndex + 1) + "");
		}
		mCurrentAnswerPassView = (TextView) findViewById(R.id.pass_answer);
		if (mCurrentAnswerPassView != null) {
			mCurrentAnswerPassView.setText(mCurrentQuestion.getAnswer());
		}

		// �?��??����??�����?
		mViewNext = (ImageButton) findViewById(R.id.btn_next);
		mViewNext.setOnClickListener(this);
		// 寰��???����??�������?
		mViewSharedWeixin = (ImageButton) findViewById(R.id.btn_share);
		mViewSharedWeixin.setOnClickListener(this);

	}

	// 宸�?����??����?��讳���??�?---�?����
	private void clearTheAnswer(WordButton wordButton) {
		wordButton.mViewButton.setText("");
		wordButton.mWordString = "";
		wordButton.mIsVisiable = false;

		// 寰�����??����???�澶����
		/*
		 * mAllWords.get(wordButton.mIndex).mViewButton
		 * .setVisibility(View.VISIBLE);
		 * mAllWords.get(wordButton.mIndex).mIsVisiable = true;
		 */
		setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
	}

	/**
	 * ?????��??寰�������???��??����������???�?
	 * 
	 * @param button
	 * @param visibiablity
	 */
	private void setButtonVisiable(WordButton button, int visibility) {
		button.mViewButton.setVisibility(visibility);
		button.mIsVisiable = (visibility == View.VISIBLE) ? true : false;
	}

	/**
	 * ������������寰�������???�?
	 * 
	 * @return
	 */
	private String[] generateWords() {
		String[] words = new String[MyGridView.COUNTS_WORDS];

		Random random = new Random();

		int wordIndex = 0;
		// ???����?����??�?
		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			words[i] = mCurrentQuestion.getAnswerChars()[i] + "";
			wordIndex ++;
		}

		//��峰��娣???���?��??����椤�
		for (int i = 0; i < mCurrentQuestion.getWrongAnswer().length(); i++) {
			words[wordIndex] = mCurrentQuestion.getWrongAnswerChars()[i] + "";
			wordIndex ++;
		}
		// ��峰�������???��???�?
		for (int i = wordIndex; i < MyGridView.COUNTS_WORDS; i++) {
			words[i] = getRandomChar() + "";
		}

		// ����?????���?�椤???��
		// �?������??�����洪���?��??��??��??��??��??��??��??��??��??��??����浣��?��??���跺����??��???������??��??��??��������??��??��??��??��??��??��??����???�?...
		for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
			int index = random.nextInt(i + 1);

			String buf = words[index];
			words[index] = words[i];
			words[i] = buf;
		}

		return words;
	}

	/**
	 * �������?��??������???��???�?
	 * 
	 * @return
	 */
	private char getRandomChar() {
		String str = "";
		int highPos;
		int lowPos;

		Random random = new Random();

		highPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));

		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(highPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();

		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str.charAt(0);
	}

	/**
	 * �?��??�妫�娴�?
	 * 
	 * @return
	 */
	private int checkTheAnswer() {
		// �?��??����??害妫�娴�?
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			// 濡��������????���?��??�����?��??��?���?��?�����?
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				return STATUS_ANSWER_LACK;
			}
		}

		// �?��??��??�????��??��娴�
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			sb.append(mBtnSelectWords.get(i).mWordString);
		}

		if (sb.toString().equals(mCurrentQuestion.getAnswer())) {
			return STATUS_ANSWER_RIGHT;
		} else {
			return STATUS_ANSWER_WRONG;
		}
	}

	/**
	 * ����??�������?
	 */
	private void sparkTheWords() {
		// ??����跺�??��稿��?
		TimerTask timerTask = new TimerTask() {
			boolean mChange = false;
			int mSpardTimes = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {
						if (++mSpardTimes > SPASH_TIMES) {
							return;
						}

						// ��??����������???��???��?????��??��?��??绾�?��???��??��
						for (int i = 0; i < mBtnSelectWords.size(); i++) {
							mBtnSelectWords.get(i).mViewButton
									.setTextColor(mChange ? Color.RED
											: Color.WHITE);
						}

						mChange = !mChange;
					}
				});
			}
		};

		Timer timer = new Timer();
		timer.schedule(timerTask, 1, 150);
	}

	// ����?????��������?��讳���??�?
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		// ??����������???���?�?
		case R.id.back:
			showConfirmDialog(ID_DIALOG_ALT_F4);
			break;
		//��???��浣����?
		case R.id.about_author:
			Util.StartActivity(this, AboutAuthorView.class);
			break;
		case R.id.stage_index_pics:
			Util.StartActivity(this, MyPicsView.class);
			break;
		// ������??���?�?
		case R.id.btn_delete_word:
			// deleteOneWord();
			showConfirmDialog(ID_DIALOG_DELETE_WORD);
			break;
		// �?��??�����?�?
		case R.id.btn_tip_answer:
			// tipOneWord();
			showConfirmDialog(ID_DIALOG_TIP_ANSWER);
			break;
		// 棰�����?���???����??�?
		case R.id.imgbtn_question_share:
			Toast.makeText(getApplicationContext(), "�?�������?��???��?���???����??����?��??������?����??�������?~",
					Toast.LENGTH_SHORT).show();
			break;
		// �?��??�棰�?
		case R.id.btn_next:
			if (judegAppPassed()) {
				// ??����??������??�����?
				Util.StartActivity(MainActivity.this, AllPassView.class);
			} else {
				// �?�濮����??�����?
				mPassView.setVisibility(View.GONE);
				// ����?????��?��???��??��
				handleCoins(3);
				initCurrentStageData();
			}
			break;
		// 寰��???����??�?
		case R.id.btn_share:
			Toast.makeText(getApplicationContext(), "�?�������?��???��?���???����??����?��??������?����??�������?~",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * ��??��??��稿������????��??���?�?
	 * 
	 * @param id
	 */
	private void showConfirmDialog(int id) {
		switch (id) {
		case ID_DIALOG_DELETE_WORD:
			Util.showDialog(MainActivity.this, "�?��?????�辨��" + getDeleteWordsCoins()
					+ "�?����???����??���?��??����???��??��??��??�?", mBtnOkDeleteWordListener);
			break;
		case ID_DIALOG_TIP_ANSWER:
			Util.showDialog(MainActivity.this, "�?��?????�辨��" + getTipAnswerCoins()
					+ "�?����???���峰���?��??����???�����???���?", mBtnOkTipAnswerListener);
			break;
		case ID_DIALOG_LACK_COINS:
			Util.showDialog(MainActivity.this, "����??��??��??筹���?????��������?����??��??��??�?",
					mBtnOkLackCoinsListener);
			break;
		case ID_DIALOG_ALT_F4:
			Util.showDialog(MainActivity.this, "������������????����???�?", mBtnOkAltF4Listener);
			break;
		}
	}

	// ����?���?�?AlertDialog�?��??跺���?��
	// ������??��???��??��??�?
	private IAlertDialogButtonListener mBtnOkDeleteWordListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			deleteOneWord();
		}
	};

	// ����?????��?????�?��??�?
	private IAlertDialogButtonListener mBtnOkTipAnswerListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			tipOneWord();
		}
	};

	// ����??��??��??�?
	private IAlertDialogButtonListener mBtnOkLackCoinsListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub

		}
	};

	// ������????����
	private IAlertDialogButtonListener mBtnOkAltF4Listener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			MainActivity.this.finish();
		}
	};

	/**
	 * ������??��???�?
	 */
	private void deleteOneWord() {
		// ����??����???�?
		if (!handleCoins(-getDeleteWordsCoins())) {
			// ����??��??�澶��??����?��??����?????��??���?�?
			showConfirmDialog(ID_DIALOG_LACK_COINS);
			return;
		}

		// ???����??��Wordbutton?????��??�?轰�����???�?
		setButtonVisiable(findNotAnswerWord(), View.INVISIBLE);
	}

	/**
	 * ��??���?���?��??�����?��??��������??��??��????���褰��������??�?
	 * 
	 * @return
	 */
	private WordButton findNotAnswerWord() {
		Random random = new Random();
		WordButton buf = null;

		while (true) {
			int index = random.nextInt(MyGridView.COUNTS_WORDS);

			buf = mAllWords.get(index);

			if (buf.mIsVisiable && !isTheAnswerWord(buf)) {
				return buf;
			}
		}
	}

	/**
	 * ��???������������??��??��??��������??�?
	 * 
	 * @param button
	 * @return true ����??��??��??��������??��??�? false �?����
	 */
	private boolean isTheAnswerWord(WordButton word) {
		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			if (word.mWordString.equals(mCurrentQuestion.getAnswerChars()[i]
					+ "")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ����????����??�?
	 */
	private void tipOneWord() {
		// ����??����???�?
		if (!handleCoins(-getTipAnswerCoins())) {
			// ����??��??�澶��??����?��??����?????��??���?�?
			showConfirmDialog(ID_DIALOG_LACK_COINS);
			return;
		}

		boolean tipWord = false;
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				// ���?��褰�����??��??��??����?��������???��?����������??��??����
				onWordButtonClick(findIsAnswerWord(i));
				// 濡����褰�����????��?������???��?��??����??��?�����?��??��������??����?��??��???��?????���浣��??��??��?????���������?����������???�?
				if (!findIsAnswerWord(i).mIsVisiable) {
					for (int j = 0; j < mBtnSelectWords.size(); j++) {
						if (mBtnSelectWords.get(j).mWordString
								.equals(mCurrentQuestion.getAnswerChars()[i]
										+ "")) {
							mBtnSelectWords.get(j).mViewButton.setText("");
							mBtnSelectWords.get(j).mWordString = "";
							mBtnSelectWords.get(j).mIsVisiable = false;
							break;
						}
					}
				}

				tipWord = true;
				break;
			}
		}

		// �????����??��??����????��??������浣��?�?
		if (!tipWord) {
			// ����������??�����???��???��
			sparkTheWords();
			// ??��������???�?
			handleCoins(getTipAnswerCoins());
		}
	}

	/**
	 * ��??���?���?��??��??����???�?
	 * 
	 * @param index
	 *            褰�������???��??����?����??��??����绱�??��
	 * @return
	 */
	private WordButton findIsAnswerWord(int index) {
		WordButton buf = null;

		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			buf = mAllWords.get(i);

			if (buf.mWordString.equals(mCurrentQuestion.getAnswerChars()[index]
					+ "")) {
				return buf;
			}
		}

		return null;
	}

	/**
	 * 澧�������������???����??����伴���������??�?
	 * 
	 * @param data
	 * @return true 澧�����������??�������? ???�? false 澶辫�?
	 */
	private boolean handleCoins(int data) {
		// ��???��褰�������???����??�伴������������??����???�?
		if (mCurrentCoins + data >= 0) {
			mCurrentCoins += data;

			mViewCurrentCoins.setText(mCurrentCoins + "");

			return true;
		} else {
			// ����??��??�澶�?
			return false;
		}
	}

	/**
	 * �?�����?�����???��??��峰�������???��???����浣�������?����??�?
	 * 
	 * @return
	 */
	private int getDeleteWordsCoins() {
		return this.getResources().getInteger(R.integer.pay_delete_words);
	}

	/**
	 * �?�����?�����???��??��峰������???����??����???����浣�������?����??�?
	 * 
	 * @return
	 */
	private int getTipAnswerCoins() {
		return this.getResources().getInteger(R.integer.pay_tip_answer);
	}

	/**
	 * ��???��������������
	 * 
	 * @return
	 */
	private boolean judegAppPassed() {
		return (mCurrentStageIndex == Const.QUESTION_ANSWER.length - 1);
	}

	/**
	 * ??����������轰���??�?
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showConfirmDialog(ID_DIALOG_ALT_F4);
			return false;
		}
		return false;
	}
}
