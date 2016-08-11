package file;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WAVFileReader implements Runnable {

	/**
	 * ファイルを格納
	 */
	private File file;

	private Clip clip;

	private int soundFlag;

	private boolean loopFlag;

	/**
	 * コンストラクタ
	 *
	 * @param filePath
	 */
	public WAVFileReader(String filePath) {
		file = new File(filePath);

		clip = null;

		soundFlag = -1;

		loopFlag = false;

		new Thread(this).start();
	}

	/**
	 * 再生する
	 */
	private void soundPlay() {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
			AudioFormat audioFormat = audioInputStream.getFormat();
			Info info = new Info(Clip.class, audioFormat);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
			clip.loop(0);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		if(loopFlag) {
			soundFlag = 2;
		} else {
			soundFlag = 0;
		}
	}

	/**
	 * 無限に再生する
	 */
	private void soundLoop() {
		if(clip != null){
			if(!clip.isRunning()){
				soundPlay();
			}
		}else{
			soundPlay();
		}
	}

	/**
	 * 処理
	 */
	public void run() {
		while (true) {
			switch (soundFlag) {
			case 1:
				soundPlay();
				break;

			case 2:
				soundLoop();
				break;
			}
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 再生を促す
	 */
	public void play() {
		loopFlag = false;
		soundFlag = 1;
	}

	/**
	 * 再生を無限に促す
	 */
	public void loop() {
		loopFlag = true;
		soundFlag = 2;
	}

	/**
	 * 再生を停止
	 */
	public void stop() {
		soundFlag = 0;
		clip.stop();
	}
}
