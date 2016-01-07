package blockService;



import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.example.lockscreentest.Global;
import com.example.lockscreentest.R;

/** 
 * @author Chan Kong
 * 
 * 
 */



public class Lock extends Activity{
	private Button giveup, goon;
	private MediaPlayer player;
	
public MediaPlayer createLocalMp3(){
	MediaPlayer player = MediaPlayer.create(this, R.raw.tfboys);
	player.stop();
	return player;
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xueba_giveup);
		
		
		giveup = (Button) findViewById(R.id.give_up);
		goon = (Button) findViewById(R.id.cont);
		
		giveup.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Ç¿º·Ä£Ê½
				if(Global.getSuperviseMode() == 0){
					player = createLocalMp3();
					player.setOnCompletionListener(new OnCompletionListener(){

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.release();
							if(player != null){
								player.release();
								player = null;
							}
								
						}
						
					});
					try{
						player.prepare();
						player.start();
					}
					catch (IllegalStateException e){
						e.printStackTrace();
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				v.setVisibility(View.INVISIBLE);
				goon.setVisibility(View.INVISIBLE);
			}
			
		});
		
		goon.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(player == null){
			Intent MyIntent = new Intent(Intent.ACTION_MAIN);
		MyIntent.addCategory(Intent.CATEGORY_HOME);
		startActivity(MyIntent);

		finish();
		}
		
		return;
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_HOME){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	


}
