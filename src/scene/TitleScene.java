package scene;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import file.ImageFileReader;
import file.WAVFileReader;
import flg.SceneFlg;

/**
 * タイトルシーンを構築する為のクラス
 * @author kudo
 *
 */
public class TitleScene implements Scene {

	/**
	 * ゲームフラグ
	 */
	private SceneFlg sceneFlg;

	/**
	 * 背景
	 */
	private ImageFileReader bg;

	/**
	 * タイトルロゴ
	 */
	private ImageFileReader logo;

	/**
	 * メニュースタート
	 */
	private ImageFileReader menuStart;

	/**
	 * メニューランキング
	 */
	private ImageFileReader menuRanking;

	/**
	 * メニュールール
	 */
	private ImageFileReader menuRule;

	/**
	 * カーソル
	 */
	private ImageFileReader cursor;

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
	 * TitleScene を新しく生成
	 */
	public TitleScene () {
		bg = new ImageFileReader("images/bg.png");

		logo = new ImageFileReader("images/title_logo.png", 500, 150);

		menuStart = new ImageFileReader("images/menu_start.png", 101, 50);

		menuRanking = new ImageFileReader("images/menu_ranking.png", 152, 50);

		menuRule = new ImageFileReader("images/menu_rule.png", 88, 50);

		cursor = new ImageFileReader("images/player.png", 120, 160);

		bgm = new WAVFileReader("sound/title_bgm.wav");

		cursorSE = new WAVFileReader("sound/cursor.wav");

		dicideSE = new WAVFileReader("sound/dicide.wav");

		cursorPositionList = new ArrayList<>();
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuStart.getSize().width, Execute.WINDOW_HEIGHT / 2 - menuStart.getSize().height));
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuRanking.getSize().width, Execute.WINDOW_HEIGHT / 2));
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuRule.getSize().width, Execute.WINDOW_HEIGHT / 2 + menuRule.getSize().height));

		currentPosition = cursorPositionList.get(0);
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;

		bgm.loop();
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {

	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
		switch (key) {
		case KeyEvent.VK_UP:
			if(currentPosition.equals(cursorPositionList.get(1))) {
				cursorSE.play();
				currentPosition = cursorPositionList.get(0);
			} else if (currentPosition.equals(cursorPositionList.get(2))) {
				cursorSE.play();
				currentPosition = cursorPositionList.get(1);
			}
			break;

		case KeyEvent.VK_DOWN:
			if(currentPosition.equals(cursorPositionList.get(0))) {
				cursorSE.play();
				currentPosition = cursorPositionList.get(1);
			} else if (currentPosition.equals(cursorPositionList.get(1))) {
				cursorSE.play();
				currentPosition = cursorPositionList.get(2);
			}
			break;

		case KeyEvent.VK_ENTER:
			dicideSE.play();
			if(currentPosition.equals(cursorPositionList.get(0))) {
				sceneFlg = SceneFlg.MAIN;
				bgm.stop();
			} else if (currentPosition.equals(cursorPositionList.get(1))) {
				sceneFlg = SceneFlg.RANKING;
			} else {
				sceneFlg = SceneFlg.RULE;
			}
			break;
		}
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased() {
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

		graphics.drawImage(logo.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2, Execute.WINDOW_HEIGHT / 5, null);

		graphics.drawImage(menuStart.getImage(), Execute.WINDOW_WIDTH / 2 - menuStart.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 - menuStart.getSize().height, null);

		graphics.drawImage(menuRanking.getImage(), Execute.WINDOW_WIDTH / 2 - menuRanking.getSize().width / 2, Execute.WINDOW_HEIGHT / 2, null);

		graphics.drawImage(menuRule.getImage(), Execute.WINDOW_WIDTH / 2 - menuRule.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 + menuRule.getSize().height, null);

		graphics.drawImage(cursor.getImage().getSubimage(40, 0, 40, 40), currentPosition.x, currentPosition.y, null);
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

}
