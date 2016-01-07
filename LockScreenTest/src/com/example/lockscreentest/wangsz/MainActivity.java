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

	/** ç»?ï¿½å??ï¿½å??ï½???? */
	public final static int STATUS_ANSWER_RIGHT = 1;
	/** ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½???ï¿? */
	public final static int STATUS_ANSWER_WRONG = 2;
	/** ç»?ï¿½å??ï¿½æ??ï¿½ç?¹ï¿½ï¿½ï¿½ï¿? */
	public final static int STATUS_ANSWER_LACK = 3;

	/** ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å¨????ï¿½ï¿½ */
	public final static int SPASH_TIMES = 4;

	/** ï¿½ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½???ï¿½ç?µç??ï¿½ï¿½å¦?ï¿½ï¿½ï¿½ï¿½è¹?ï¿? */
	public final static int ID_DIALOG_DELETE_WORD = 4;
	/** ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½ç»????ï¿½ç??ï¿½ï¿½å¦?ï¿½ï¿½ï¿½ï¿½è¹?ï¿? */
	public final static int ID_DIALOG_TIP_ANSWER = 5;
	/** ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½ç????²ï¿½ç¡?ï¿½ï¿½å¦?ï¿½ï¿½ï¿½ï¿½è¹?ï¿? */
	public final static int ID_DIALOG_LACK_COINS = 6;
	/** ï¿½ï¿½ï¿½ï¿½ï¿½ç????¶ï¿½ï¿½ï¿½??µç??ï¿½ï¿½å¦?ï¿? */
	public final static int ID_DIALOG_ALT_F4 = 7;

	// ï¿½ï¿½ï¿½ç??ï¿½å??ï¿½ç?¹ç?°ï¿½ï¿?
	private ArrayList<WordButton> mAllWords;

	// å®¸æ?¥ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿?
	private ArrayList<WordButton> mBtnSelectWords;

	// å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿½ç??ï¿½ç??ï¿?
	private MyGridView mMyGridView;

	// å®¸æ?¥ï¿½ï¿½ï¿½ï¿½â??ï¿½ï¿½???ï¿½å??ï¿?UI??¹ç?°ï¿½ï¿?
	private LinearLayout mViewWordsContainer;

	// ??©ï¿½ï¿½ï¿½å´?ï¿½ï¿½ï¿½ï¿½ï¿?
	private View mPassView;

	// è¤°ï¿½ï¿½ï¿½ï¿½æ£°ï¿½ï¿½ï¿½ï¿½
	private Question mCurrentQuestion;

	// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å????¨å??ï¿?
	private int mCurrentStageIndex = -1;

	// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ä¼´ï¿½ï¿½
	private int mCurrentCoins = Const.TOTAL_COINT;

	// ??©ï¿½ï¿½ï¿½ï¿?View
	private ImageButton mBack;
	// ï¿½ï¿½ï¿½ç??ï¿?View
	private TextView mViewCurrentCoins;
	//ï¿½ï¿½???ï¿½ï¿½æµ£ï¿½ï¿½ï¿½ï¿?
	private ImageButton mAboutAuthor;
	//ï¿½ï¿½???ï¿½ç??ï¿½ï¿½ï¿½ï¿½ï¿?
	private ImageButton mStagesPics;
	// ï¿½ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½???ï¿½ç??ï¿½å??ï¿?View
	private ImageButton mViewDelete;
	// ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½ç»?ï¿?View
	private ImageButton mViewTip;
	// æ£°ï¿½ï¿½ï¿½ï¿½å?°ï¿½æ·???³ï¿½ï¿½æ??ï¿?
	private ImageButton mViewShare;
	// æ¶???¤ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å????¨å??ï¿?
	private TextView mCurrentStageView;
	// æ£°ï¿½ï¿½ï¿½ï¿?
	private ImageView mCurrentQuestionBG;
	// ï¿½ï¿½ï¿½ç??ï¿?
	private TextView mCurrentQuestionTip;

	// ??©ï¿½ï¿½ï¿½å´?ï¿½ï¿½ï¿½ï¿½ï¿?
	// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å????¨å??ï¿½ï¿½ï¿½å?§ã??
	private TextView mCurrentStagePassView;
	// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å??ï¿½ï¿½å¦?ï¿?
	private TextView mCurrentAnswerPassView;
	// æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½View
	private ImageButton mViewNext;
	// å¯°ï¿½æ·???³ï¿½ï¿½æ??ï¿?View
	private ImageButton mViewSharedWeixin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ???è¯²ï¿½ï¿½æ??ï¿½ç??ï¿½ï¿½ï¿½ç??ï¿½ï¿½
		int[] datas = Util.loadData(this);
		mCurrentStageIndex = datas[Const.INDEX_LOAD_DATA_STAGE];
		mCurrentCoins = datas[Const.INDEX_LOAD_DATA_COINS];

		// ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??æ¬?
		mBack = (ImageButton) findViewById(R.id.back);
		mBack.setOnClickListener(this);

		mAboutAuthor = (ImageButton) findViewById(R.id.about_author);
		mAboutAuthor.setOnClickListener(this);
		
		mStagesPics = (ImageButton) findViewById(R.id.stage_index_pics);
		mStagesPics.setOnClickListener(this);
		
		mMyGridView = (MyGridView) findViewById(R.id.gridview);
		// å¨???¥ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?
		mMyGridView.registOnWordButtonClickListener(this);

		mViewWordsContainer = (LinearLayout) findViewById(R.id.word_select_container);

		mViewCurrentCoins = (TextView) findViewById(R.id.money);
		mViewCurrentCoins.setText(mCurrentCoins + "");

		mCurrentQuestionBG = (ImageView) findViewById(R.id.imgv_question_bg);
		mCurrentQuestionTip = (TextView) findViewById(R.id.tv_tip_for_answer);

		// ï¿½ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½???ï¿½ç??ï¿½å??ï¿½æ??ï¿½æ??ï¿?
		mViewDelete = (ImageButton) findViewById(R.id.btn_delete_word);
		mViewDelete.setOnClickListener(this);
		// ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½ç»?è½°ï¿½ï¿½æ??ï¿?
		mViewTip = (ImageButton) findViewById(R.id.btn_tip_answer);
		mViewTip.setOnClickListener(this);
		// æ£°ï¿½ï¿½ï¿½ï¿½å?°ï¿½æ·???³ï¿½ï¿½æ??ï¿?
		mViewShare = (ImageButton) findViewById(R.id.imgbtn_question_share);
		mViewShare.setOnClickListener(this);

		// ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½å¨????ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½
		initCurrentStageData();

	}

	@Override
	public void onPause() {
		super.onPause();
		// æ·?ï¿½ç??ï¿½ï¿½ï¿½ç??ï¿½ï¿½
		Util.saveData(MainActivity.this, mCurrentStageIndex - 1, mCurrentCoins);
	}

	/**
	 * ï¿½ï¿½ï¿½æ?????ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è??ï¿½ç??ï¿½ï¿½
	 */
	private void initCurrentStageData() {
		// ???è¯²ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½
		mCurrentQuestion = loadStageQuestionInfo(++mCurrentStageIndex);
		// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ?½ï¿½ï¿½ï¿½ï¿½ï¿½+ï¿½ï¿½ï¿½ç??ï¿?
		setQuestion(mCurrentQuestion);
		// ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½å®¸æ?¥ï¿½ï¿½ï¿½ï¿½â??ï¿½ï¿½
		mBtnSelectWords = initWordSelect();

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// å¨?ï¿½ç??è½°ï¿½ï¿½æ??ï¿½ï¿½ï¿½å??ï¿½ï¿½å¦?ï¿?
		mViewWordsContainer.removeAllViews();
		// ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å??ï¿½ï¿½å¦?ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿?
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton,
					params);
		}

		// ï¿½ï¿½å³°ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å´???¨å??ï¿?
		mCurrentStageView = (TextView) findViewById(R.id.tx_current_stage);
		if (mCurrentStageView != null) {
			mCurrentStageView.setText((mCurrentStageIndex + 1) + "");
		}
		// ï¿½ï¿½å³°ï¿½ï¿½ï¿½ï¿½ç??ï¿½ï¿½
		mAllWords = initAllWord();
		// ï¿½ï¿½å­?ï¿½ç??ï¿½ç??ï¿½ï¿½- MyGridView
		mMyGridView.updateData(mAllWords);
	}

	/**
	 * è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ?½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??§ã??+ï¿½ï¿½ï¿½ç?????ï¿½å?§ã??
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
	 * å¯°ï¿½ï¿½ï¿½??¿ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??²ï¿½??³ï¿½??°ï¿½ï¿½ï¿½ï¿½å?§ï¿½ï¿?
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
	 * ï¿½ï¿½å³°ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??½ï¿½ï¿½ï¿½ï¿½ï¿½
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
	 * ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿?
	 */
	private ArrayList<WordButton> initAllWord() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();

		// ï¿½ï¿½å³°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿?
		String[] words = generateWords();
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			WordButton button = new WordButton();

			button.mWordString = words[i];

			data.add(button);
		}

		return data;
	}

	/**
	 * ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½å®¸æ?¥ï¿½ï¿½ï¿½ï¿½â??ï¿½ï¿½???ï¿½å??ï¿?
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
					// ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½è?¹ï¿½ï¿½ï¿½ï¿½ç?°ï¿½ç»?ï¿½ï¿½???ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½ï¿½ï¿½ï¿½?????§ç??ï¿½ï¿½ï¿½ç??ï¿½æ????¹ï¿½???ï¿½ï¿½(checkResultIndex ==
					// STATUS_ANSWER_LACK)
					checkResult();
				}

			});

			data.add(holder);
		}
		return data;
	}

	// å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿½ï¿½ï¿½ç?°ï¿½è®³ï¿½ï¿½æ??ï¿?
	@Override
	public void onWordButtonClick(WordButton wordButton) {
		// ?????¿ï¿½ï¿½å?¸å??ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½ï¿½ï¿½ï¿½
		int mount = 0;
		// å®¸æ?¥ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿½æ??ï¿½ï¿½ï¿½ï¿½
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
			// å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿½ç????§â??
			/*
			 * wordButton.mViewButton.setVisibility(View.INVISIBLE);
			 * wordButton.mIsVisiable = false;
			 */
			setButtonVisiable(wordButton, View.INVISIBLE);
		}

		// ç»?ï¿½å??ï¿½æ¾¶ï¿½ï¿½ï¿½ï¿½
		checkResult();

	}

	/**
	 * ç»?ï¿½å??ï¿½ï¿½ï¿½è?µï¿½ï¿½å¦«ï¿½å¨´ï¿½ï¿½ï¿½ï¿½æ¾¶ï¿½ï¿½ï¿½ï¿?
	 * 
	 */
	private void checkResult() {
		// ï¿½ï¿½å³°ï¿½ï¿½ç??ï¿½å??ï¿½ï¿½ï¿½è?µï¿½ï¿?
		int checkResultIndex = checkTheAnswer();

		// ï¿½ï¿½??µï¿½ï¿½æ¾¶ï¿½ï¿½ï¿½ï¿½
		if (checkResultIndex == STATUS_ANSWER_RIGHT) {
			// ??©ï¿½ï¿½ï¿½ï¿?+ï¿½ï¿½å³°ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½
			handlePassEvent();
		} else if (checkResultIndex == STATUS_ANSWER_WRONG) {
			// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿?
			sparkTheWords();
		} else if (checkResultIndex == STATUS_ANSWER_LACK) {
			// ?????§ç??ï¿½ï¿½ï¿½ç??ï¿½æ????¹ï¿½???ï¿½ï¿½
			for (int i = 0; i < mBtnSelectWords.size(); i++) {
				mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
			}
		}
	}

	/**
	 * æ¾¶ï¿½ï¿½ï¿½ï¿½æ?©ï¿½ï¿½ï¿½å´?ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½æµ?ï¿½æ??ï¿?
	 */
	private void handlePassEvent() {
		mPassView = (LinearLayout) this.findViewById(R.id.pass_view);
		mPassView.setVisibility(View.VISIBLE);

		// ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½å´?ï¿½ï¿½ï¿½ï¿½ï¿½æ??ï¿½ç????§ç??ï¿½ï¿½???ï¿½ï¿½+ç»?ï¿½å??ï¿? ï¿½ï¿½??§ã??
		mCurrentStagePassView = (TextView) findViewById(R.id.stage_index);
		if (mCurrentStagePassView != null) {
			mCurrentStagePassView.setText((mCurrentStageIndex + 1) + "");
		}
		mCurrentAnswerPassView = (TextView) findViewById(R.id.pass_answer);
		if (mCurrentAnswerPassView != null) {
			mCurrentAnswerPassView.setText(mCurrentQuestion.getAnswer());
		}

		// æ¶?ï¿½æ??ï¿½ï¿½ï¿½è??ï¿½ï¿½ï¿½ï¿½ï¿?
		mViewNext = (ImageButton) findViewById(R.id.btn_next);
		mViewNext.setOnClickListener(this);
		// å¯°ï¿½æ·???³ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?
		mViewSharedWeixin = (ImageButton) findViewById(R.id.btn_share);
		mViewSharedWeixin.setOnClickListener(this);

	}

	// å®¸æ?¥ï¿½ï¿½å??ï¿½ï¿½ï¿½ç?°ï¿½è®³ï¿½ï¿½æ??ï¿?---å¨?ï¿½ï¿½ï¿½ï¿½
	private void clearTheAnswer(WordButton wordButton) {
		wordButton.mViewButton.setText("");
		wordButton.mWordString = "";
		wordButton.mIsVisiable = false;

		// å¯°ï¿½ï¿½ï¿½ï¿½å??ï¿½ï¿½ï¿½ï¿½???ï¿½æ¾¶ï¿½ï¿½ï¿½ï¿½
		/*
		 * mAllWords.get(wordButton.mIndex).mViewButton
		 * .setVisibility(View.VISIBLE);
		 * mAllWords.get(wordButton.mIndex).mIsVisiable = true;
		 */
		setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
	}

	/**
	 * ?????§ç??å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿?
	 * 
	 * @param button
	 * @param visibiablity
	 */
	private void setButtonVisiable(WordButton button, int visibility) {
		button.mViewButton.setVisibility(visibility);
		button.mIsVisiable = (visibility == View.VISIBLE) ? true : false;
	}

	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿?
	 * 
	 * @return
	 */
	private String[] generateWords() {
		String[] words = new String[MyGridView.COUNTS_WORDS];

		Random random = new Random();

		int wordIndex = 0;
		// ???ï¿½ï¿½ï¿½ã?§ï¿½ï¿½å??ï¿?
		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			words[i] = mCurrentQuestion.getAnswerChars()[i] + "";
			wordIndex ++;
		}

		//ï¿½ï¿½å³°ï¿½ï¿½å¨£???ï¿½ï¿½ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½æ¤¤ï¿½
		for (int i = 0; i < mCurrentQuestion.getWrongAnswer().length(); i++) {
			words[wordIndex] = mCurrentQuestion.getWrongAnswerChars()[i] + "";
			wordIndex ++;
		}
		// ï¿½ï¿½å³°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½???ï¿?
		for (int i = wordIndex; i < MyGridView.COUNTS_WORDS; i++) {
			words[i] = getRandomChar() + "";
		}

		// ï¿½ï¿½ï¿½æ?????ï¿½ï¿½ç»?ï¿½æ¤¤???ï¿½ï¿½
		// æµ?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??¤ï¿½ï¿½ï¿½ï¿½æ´ªï¿½ï¿½æ¶?ï¿½æ??ï¿½ç??ï¿½ç??ï¿½æ??ï¿½ç??ï¿½æ??ï¿½æ??ï¿½ç??ï¿½ç??ï¿½æ??ï¿½ï¿½ï¿½ï¿½æµ£ï¿½ç¼?ï¿½é??ï¿½ï¿½ï¿½è·ºï¿½ï¿½ï¿½ï¿½??¥ï¿½???ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½ç??ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ??ï¿½æ??ï¿½æ??ï¿½ç??ï¿½æ??ï¿½æ??ï¿½æ??ï¿½ï¿½ï¿½ï¿½???ï¿?...
		for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
			int index = random.nextInt(i + 1);

			String buf = words[index];
			words[index] = words[i];
			words[i] = buf;
		}

		return words;
	}

	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½???ï¿?
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
	 * ç»?ï¿½å??ï¿½å¦«ï¿½å¨´ï¿?
	 * 
	 * @return
	 */
	private int checkTheAnswer() {
		// ç»?ï¿½å??ï¿½ï¿½ï¿½å??å®³å¦«ï¿½å¨´ï¿?
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			// æ¿¡ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç»????ï¿½ï¿½ç»?ï¿½é??ï¿½ï¿½ï¿½ï¿½ç»?ï¿½å??ï¿½æ?©ï¿½æ¶?ï¿½ç?¹ï¿½ï¿½ï¿½ï¿?
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				return STATUS_ANSWER_LACK;
			}
		}

		// ç»?ï¿½å??ï¿½å??ï½????ï¿½ï¿½??ï¿½ï¿½å¨´ï¿½
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
	 * ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?
	 */
	private void sparkTheWords() {
		// ??¹ï¿½ï¿½ï¿½è·ºï¿½??§ï¿½ç¨¿ï¿½ï¿?
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

						// ï¿½ï¿½??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½???ï¿½æ?????ï¿½æ??ï¿½å?§ã??ç»¾ã?£ï¿½???ï¿½å??ï¿½ï¿½
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

	// ï¿½ï¿½ï¿½ç?????ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç?°ï¿½è®³ï¿½ï¿½æ??ï¿?
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		// ??©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ç»?ï¿?
		case R.id.back:
			showConfirmDialog(ID_DIALOG_ALT_F4);
			break;
		//ï¿½ï¿½???ï¿½ï¿½æµ£ï¿½ï¿½ï¿½ï¿?
		case R.id.about_author:
			Util.StartActivity(this, AboutAuthorView.class);
			break;
		case R.id.stage_index_pics:
			Util.StartActivity(this, MyPicsView.class);
			break;
		// ï¿½ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½ç»?ï¿?
		case R.id.btn_delete_word:
			// deleteOneWord();
			showConfirmDialog(ID_DIALOG_DELETE_WORD);
			break;
		// ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½ç»?ï¿?
		case R.id.btn_tip_answer:
			// tipOneWord();
			showConfirmDialog(ID_DIALOG_TIP_ANSWER);
			break;
		// æ£°ï¿½ï¿½ï¿½ï¿½å?°ï¿½æ·???³ï¿½ï¿½æ??ï¿?
		case R.id.imgbtn_question_share:
			Toast.makeText(getApplicationContext(), "æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è¾?ï¿½ï¿½???ï¿½å?°ï¿½æ·???³ï¿½ï¿½æ??ï¿½ï¿½ï¿½ç?¿ï¿½??²ï¿½ï¿½ï¿½ï¿½è?¥ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?~",
					Toast.LENGTH_SHORT).show();
			break;
		// æ¶?ï¿½æ??ï¿½æ£°ï¿?
		case R.id.btn_next:
			if (judegAppPassed()) {
				// ??©ï¿½ï¿½ï¿½??©ï¿½ï¿½ï¿½ï¿½å??ï¿½ï¿½ï¿½ï¿½ï¿?
				Util.StartActivity(MainActivity.this, AllPassView.class);
			} else {
				// å¯?ï¿½æ¿®ï¿½ï¿½ï¿½é??ï¿½ï¿½ï¿½ï¿½ï¿?
				mPassView.setVisibility(View.GONE);
				// ï¿½ï¿½ï¿½æ?????ï¿½å?²ï¿½???ï¿½ç??ï¿½ï¿½
				handleCoins(3);
				initCurrentStageData();
			}
			break;
		// å¯°ï¿½æ·???³ï¿½ï¿½æ??ï¿?
		case R.id.btn_share:
			Toast.makeText(getApplicationContext(), "æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è¾?ï¿½ï¿½???ï¿½å?°ï¿½æ·???³ï¿½ï¿½æ??ï¿½ï¿½ï¿½ç?¿ï¿½??²ï¿½ï¿½ï¿½ï¿½è?¥ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?~",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * ï¿½ï¿½??§ã??ï¿½ï¿½ç¨¿ï¿½ï¿½ï¿½ï¿½ï¿½ç»????ï¿½ç??ï¿½ï¿½å¦?ï¿?
	 * 
	 * @param id
	 */
	private void showConfirmDialog(int id) {
		switch (id) {
		case ID_DIALOG_DELETE_WORD:
			Util.showDialog(MainActivity.this, "çº?ï¿½ç?????ï¿½è¾¨ï¿½ï¿½" + getDeleteWordsCoins()
					+ "æ¶?ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ç??ï¿½ï¿½æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½???ï¿½ç??ï¿½å??ï¿½é??ï¿?", mBtnOkDeleteWordListener);
			break;
		case ID_DIALOG_TIP_ANSWER:
			Util.showDialog(MainActivity.this, "çº?ï¿½ç?????ï¿½è¾¨ï¿½ï¿½" + getTipAnswerCoins()
					+ "æ¶?ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½å³°ï¿½ï¿½æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½ç»???´ï¿½ï¿?", mBtnOkTipAnswerListener);
			break;
		case ID_DIALOG_LACK_COINS:
			Util.showDialog(MainActivity.this, "ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½ç??ç­¹ï¿½ï¿½ç?????ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?¸ï¿½ï¿½é??ï¿½é??ï¿½é??ï¿?",
					mBtnOkLackCoinsListener);
			break;
		case ID_DIALOG_ALT_F4:
			Util.showDialog(MainActivity.this, "ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç????¶ï¿½ï¿½ï¿½???ï¿?", mBtnOkAltF4Listener);
			break;
		}
	}

	// ï¿½ï¿½ï¿½ç?¹ï¿½æ¶?ï¿?AlertDialogæµ?ï¿½æ??è·ºï¿½ï¿½æ?´ï¿½
	// ï¿½ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½???ï¿½ç??ï¿½å??ï¿?
	private IAlertDialogButtonListener mBtnOkDeleteWordListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			deleteOneWord();
		}
	};

	// ï¿½ï¿½ï¿½ç?????ï¿½ï?????ç»?ï¿½å??ï¿?
	private IAlertDialogButtonListener mBtnOkTipAnswerListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			tipOneWord();
		}
	};

	// ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½ç??ï¿?
	private IAlertDialogButtonListener mBtnOkLackCoinsListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub

		}
	};

	// ï¿½ï¿½ï¿½ï¿½ï¿½ç????¶ï¿½ï¿½ï¿½
	private IAlertDialogButtonListener mBtnOkAltF4Listener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			MainActivity.this.finish();
		}
	};

	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ã??ï¿½ï¿½???ï¿?
	 */
	private void deleteOneWord() {
		// ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½???ï¿?
		if (!handleCoins(-getDeleteWordsCoins())) {
			// ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½æ¾¶ï¿½é??ï¿½ï¿½ï¿½å?§ã??ï¿½ï¿½ï¿½ç?????ï¿½ç??ï¿½ï¿½å¦?ï¿?
			showConfirmDialog(ID_DIALOG_LACK_COINS);
			return;
		}

		// ???ï¿½ï¿½ï¿½ï¿½??¹ï¿½Wordbutton?????§ç??æ¶?è½°ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿?
		setButtonVisiable(findNotAnswerWord(), View.INVISIBLE);
	}

	/**
	 * ï¿½ï¿½??§ï¿½é¢?ï¿½ï¿½æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½ç»?ï¿½å??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½é??ï¿½é????µï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿?
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
	 * ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½å??ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿?
	 * 
	 * @param button
	 * @return true ï¿½ï¿½ï¿½ç??ï¿½å??ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½é??ï¿? false æ¶?ï¿½ï¿½ï¿½ï¿½
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
	 * ï¿½ï¿½ï¿½ç????¹ï¿½ï¿½å??ï¿?
	 */
	private void tipOneWord() {
		// ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½???ï¿?
		if (!handleCoins(-getTipAnswerCoins())) {
			// ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½æ¾¶ï¿½é??ï¿½ï¿½ï¿½å?§ã??ï¿½ï¿½ï¿½ç?????ï¿½ç??ï¿½ï¿½å¦?ï¿?
			showConfirmDialog(ID_DIALOG_LACK_COINS);
			return;
		}

		boolean tipWord = false;
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				// ï¿½ï¿½è§?ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ç??ï¿½å??ï¿½å??ï¿½ï¿½ï¿½è?µï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ç?¿ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½ï¿½ï¿½ï¿½
				onWordButtonClick(findIsAnswerWord(i));
				// æ¿¡ï¿½ï¿½ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ç»????ï¿½è?©ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½å?¸è??ï¿½ï¿½ï¿½ï¿½??¥å?¡ï¿½ï¿½ï¿½å¦?ï¿½é??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½å?§ã??ï¿½ï¿½???ï¿½ï?????ï¿½ï¿½ï¿½æµ£ï¿½ç??ï¿½é??ï¿½é?????ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿?
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

		// å¨????ï¿½ï¿½ï¿½ï¿½??§ï¿½??¿ï¿½ï¿½æ????¥ï??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æµ£ï¿½ç¼?ï¿?
		if (!tipWord) {
			// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½ç»???¹ï¿½???ï¿½ï¿½
			sparkTheWords();
			// ??©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿?
			handleCoins(getTipAnswerCoins());
		}
	}

	/**
	 * ï¿½ï¿½??§ï¿½é¢?ï¿½ï¿½æ¶?ï¿½ç??ï¿½å??ï¿½ï¿½ï¿½ï¿½???ï¿?
	 * 
	 * @param index
	 *            è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½æ??ï¿½ï¿½ï¿½ã?§ï¿½ï¿½å??ï¿½å??ï¿½ï¿½ï¿½ï¿½ç»±ã??ï¿½ï¿½
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
	 * æ¾§ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½??¹ï¿½ï¿½ï¿½ä¼´ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿?
	 * 
	 * @param data
	 * @return true æ¾§ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿? ???ï¿? false æ¾¶è¾«è§?
	 */
	private boolean handleCoins(int data) {
		// ï¿½ï¿½???ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ç??ï¿½ä¼´ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½???ï¿?
		if (mCurrentCoins + data >= 0) {
			mCurrentCoins += data;

			mViewCurrentCoins.setText(mCurrentCoins + "");

			return true;
		} else {
			// ï¿½ï¿½ï¿½ç??ï¿½æ??ï¿½æ¾¶ï¿?
			return false;
		}
	}

	/**
	 * æµ?ï¿½ï¿½ï¿½ï¿½ç¼?ï¿½ï¿½ï¿½ï¿½æµ???µè??ï¿½ï¿½å³°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½æµ£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ã?©ï¿½ï¿½ç??ï¿?
	 * 
	 * @return
	 */
	private int getDeleteWordsCoins() {
		return this.getResources().getInteger(R.integer.pay_delete_words);
	}

	/**
	 * æµ?ï¿½ï¿½ï¿½ï¿½ç¼?ï¿½ï¿½ï¿½ï¿½æµ???µè??ï¿½ï¿½å³°ï¿½ï¿½ï¿½ï¿½ï¿½ç»???¹ï¿½ï¿½å??ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½æµ£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ã?©ï¿½ï¿½ç??ï¿?
	 * 
	 * @return
	 */
	private int getTipAnswerCoins() {
		return this.getResources().getInteger(R.integer.pay_tip_answer);
	}

	/**
	 * ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	 * 
	 * @return
	 */
	private boolean judegAppPassed() {
		return (mCurrentStageIndex == Const.QUESTION_ANSWER.length - 1);
	}

	/**
	 * ??©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è½°ï¿½ï¿½æ??ï¿?
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
