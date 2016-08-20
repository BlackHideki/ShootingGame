package scene;

import java.awt.Dimension;
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
	 * カーソルサイズ
	 */
	private Dimension cursorSize;

	/**
	 * カーソルの表示座標
	 */
	private ArrayList<Point> cursorPositionList;

	/**
	 * カーソルの現在の表示座標
	 */
	private Point currentPosition;

	/**
	 * キーフラグ
	 */
	private boolean keyFlg;

	/**
	 * TitleScene を新しく生成
	 */
	public TitleScene () {
		bg = new ImageFileReader("image/title_bg.jpg", 800, 600);

		logo = new ImageFileReader("image/title_logo.png");

		menuStart = new ImageFileReader("image/menu_start.png", 86, 50);

		menuRanking = new ImageFileReader("image/menu_ranking.png", 138, 50);

		cursor = new ImageFileReader("image/player.png", 120, 80);

		bgm = new WAVFileReader("sound/title_bgm.wav");

		cursorSE = new WAVFileReader("sound/cursor.wav");

		dicideSE = new WAVFileReader("sound/dicide.wav");

		cursorSize = new Dimension(40, 40);

		cursorPositionList = new ArrayList<>();
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuStart.getSize().width - cursorSize.width / 2, Execute.WINDOW_HEIGHT / 2 - menuStart.getSize().height));
		cursorPositionList.add(new Point(Execute.WINDOW_WIDTH / 2 - menuRanking.getSize().width, Execute.WINDOW_HEIGHT / 2));

		currentPosition = cursorPositionList.get(0);
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;

		bgm.loop();

		keyFlg = false;
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
			if (!keyFlg) {
				if(currentPosition.equals(cursorPositionList.get(1))) {
					cursorSE.play();
					currentPosition = cursorPositionList.get(0);
				}
				keyFlg = true;
			}
			break;

		case KeyEvent.VK_DOWN:
			if (!keyFlg) {
				if(currentPosition.equals(cursorPositionList.get(0))) {
					cursorSE.play();
					currentPosition = cursorPositionList.get(1);
				}
				keyFlg = true;
			}
			break;

		case KeyEvent.VK_ENTER:
			if (!keyFlg) {
				dicideSE.play();
				if(currentPosition.equals(cursorPositionList.get(0))) {
					sceneFlg = SceneFlg.MAIN;
					bgm.stop();
				} else if (currentPosition.equals(cursorPositionList.get(1))) {
					sceneFlg = SceneFlg.RANKING;
				}
				keyFlg = true;
			}
			break;
		}
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased(int key) {
		keyFlg = false;
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

		graphics.drawImage(logo.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2, Execute.WINDOW_HEIGHT / 8, null);

		graphics.drawImage(menuStart.getImage(), Execute.WINDOW_WIDTH / 2 - menuStart.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 - menuStart.getSize().height, null);

		graphics.drawImage(menuRanking.getImage(), Execute.WINDOW_WIDTH / 2 - menuRanking.getSize().width / 2, Execute.WINDOW_HEIGHT / 2, null);

		graphics.drawImage(cursor.getImage().getSubimage(0, cursorSize.height, cursorSize.width, cursorSize.height), currentPosition.x, currentPosition.y, null);
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
