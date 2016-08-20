package scene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import file.ImageFileReader;
import file.TextFileReader;
import file.WAVFileReader;
import flg.SceneFlg;

/**
 * ゲームオーバーシーンを構築する為のクラス
 * @author kudo
 *
 */
public class GameOverScene implements Scene {

	/**
	 * シーンフラグ
	 */
	private SceneFlg sceneFlg;

	/**
	 * 背景
	 */
	private ImageFileReader bg;

	/**
	 * ロゴ
	 */
	private ImageFileReader gameOverLogo;

	/**
	 * メニューリトライ
	 */
	private ImageFileReader menuRetry;

	/**
	 * メニュータイトル
	 */
	private ImageFileReader menuTitle;

	/**
	 * カーソル
	 */
	private ImageFileReader cursor;

	/**
	 * ランキング
	 */
	private TextFileReader ranking;

	/**
	 * bgm
	 */
	private WAVFileReader bgm;

	/**
	 * カーソル音
	 */
	private WAVFileReader cursorSE;

	/**
	 * 決定音
	 */
	private WAVFileReader dicideSE;

	/**
	 * カーソルの表示座標
	 */
	private ArrayList<Point> cursorPositionList;

	/**
	 * カーソルの現在の表示座標
	 */
	private Point currentPosition;

	/**
	 * スコア
	 */
	private int score;

	/**
	 * スコアとランキングの比較フラグ
	 */
	private boolean scoreCheckFlg;

	/**
	 * GameOverScene を新しく生成
	 */
	public GameOverScene () {
		bg = new ImageFileReader("image/game_over_bg.png");

		gameOverLogo = new ImageFileReader("image/game_over_logo.png");

		menuRetry = new ImageFileReader("image/menu_retry.png", 90, 50);

		menuTitle = new ImageFileReader("image/menu_title.png", 77, 50);

		cursor = new ImageFileReader("image/player.png", 120, 80);

		ranking = new TextFileReader("text/ranking.txt");

		bgm = new WAVFileReader("sound/game_over_bgm.wav");

		cursorSE = new WAVFileReader("sound/cursor.wav");

		dicideSE = new WAVFileReader("sound/dicide.wav");

		cursorPositionList = new ArrayList<>();
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuRetry.getSize().width, Execute.WINDOW_HEIGHT / 2));
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuTitle.getSize().width, Execute.WINDOW_HEIGHT / 2 + menuRetry.getSize().height));

		currentPosition = cursorPositionList.get(0);
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;

		ranking.textRead();

		bgm.play();

		scoreCheckFlg = false;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		if (!scoreCheckFlg) {
			ranking.getText().add(String.valueOf(score));
			int[] tmp = new int[ranking.getText().size()];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = Integer.valueOf(ranking.getText().get(i));
			}

			for (int i = 0; i < tmp.length - 1; i++) {
				for (int j = tmp.length - 1; j > i; j--) {
					if (tmp[i] < tmp[j]) {
						int n = tmp[i];
						tmp[i] = tmp[j];
						tmp[j] = n;
					}
				}
			}

			ranking.getText().clear();
			for (int i = 0; i < tmp.length - 1; i++) {
				ranking.getText().add(String.valueOf(tmp[i]));
			}

			ranking.textWrite(ranking.getText());

			scoreCheckFlg = true;
		}
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
		switch (key) {
		case KeyEvent.VK_UP:
			if (currentPosition.equals(cursorPositionList.get(1))) {
				cursorSE.play();
				currentPosition = cursorPositionList.get(0);
			}
			break;

		case KeyEvent.VK_DOWN:
			if (currentPosition.equals(cursorPositionList.get(0))) {
				cursorSE.play();
				currentPosition = cursorPositionList.get(1);
			}
			break;

		case KeyEvent.VK_ENTER:
			dicideSE.play();
			bgm.stop();
			if (currentPosition.equals(cursorPositionList.get(0))) {
				sceneFlg = SceneFlg.MAIN;
			} else {
				sceneFlg = SceneFlg.TITLE;
			}
			break;
		}

	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased(int key) {
	}

	/**
	 * マウスクリック
	 */
	@Override
	public void mouseClick(Point position) {
	}

	/**
	 * 描画
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(bg.getImage(), 0, 0, null);

		graphics.drawImage(gameOverLogo.getImage(), Execute.WINDOW_WIDTH / 2 - gameOverLogo.getSize().width / 2, Execute.WINDOW_HEIGHT / 8, null);

		graphics.drawImage(menuRetry.getImage(), Execute.WINDOW_WIDTH / 2 - menuRetry.getSize().width / 2, Execute.WINDOW_HEIGHT / 2, null);

		graphics.drawImage(menuTitle.getImage(), Execute.WINDOW_WIDTH / 2 - menuTitle.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 + menuRetry.getSize().height, null);

		Dimension cursorSize = new Dimension(40, 40);
		graphics.drawImage(cursor.getImage().getSubimage(0, cursorSize.height, cursorSize.width, cursorSize.height), currentPosition.x, currentPosition.y, null);

		graphics.setColor(Color.BLUE);
		graphics.setFont(new Font("メイリオ", Font.BOLD, 50));
		graphics.drawString("SCORE : " + score, Execute.WINDOW_WIDTH / 2 - 150, Execute.WINDOW_HEIGHT / 2 + menuRetry.getSize().height * 3);
	}

	/**
	 * ゲームフラグを取得
	 */
	@Override
	public SceneFlg getSceneFlg() {
		return sceneFlg;
	}

	/**
	 * ゲームフラグを格納
	 */
	@Override
	public void setSceneFlg(SceneFlg sceneFlg) {
		this.sceneFlg = sceneFlg;
	}

	/**
	 * スコアを取得
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * スコアを参照
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
