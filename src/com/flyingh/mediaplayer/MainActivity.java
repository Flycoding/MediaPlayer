package com.flyingh.mediaplayer;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText musicText;
	private MediaPlayer player;
	private Button playOrPauseButton;
	private boolean reset = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		musicText = (EditText) findViewById(R.id.music);
		playOrPauseButton = (Button) findViewById(R.id.playOrPause);
		player = new MediaPlayer();
	}

	public void playOrPause(View view) {
		if (player.isPlaying()) {
			pause();
		} else {
			try {
				if (!reset) {
					start();
					return;
				}
				player.reset();
				String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + musicText.getText().toString();
				player.setDataSource(path);
				player.prepare();
				player.setOnPreparedListener(new OnPreparedListener() {

					@Override
					public void onPrepared(MediaPlayer mp) {
						start();
					}
				});
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private void start() {
		player.start();
		playOrPauseButton.setText(R.string.pause);
		setReset(false);
	}

	private void pause() {
		player.pause();
		setReset(false);
		playOrPauseButton.setText(R.string.play);
	}

	public void reset(View view) {
		setReset(true);
		if (player.isPlaying()) {
			player.seekTo(0);
		} else {
			playOrPause(view);
		}
	}

	public void stop(View view) {
		if (player.isPlaying()) {
			stop();
		}
	}

	private void stop() {
		player.stop();
		setReset(true);
		playOrPauseButton.setText(R.string.play);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

}
