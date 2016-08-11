package scene;

import java.awt.Graphics;
import java.awt.Point;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import file.ImageFileReader;
import file.WAVFileReader;
import flg.SceneFlg;

/**
 * ルールシーンを構築する為のクラス
 * @author kudo
 *
 */
public class RuleScene implements Scene {

	/**
	 * シーンフラグ
	 */
	private SceneFlg sceneFlg;

	/**
	 * 背景
	 */
	private ImageFileReader bg;

	/**
	 * カーソル
	 */
	private ImageFileReader cursor;

	/**
	 * メニューバック
	 */
	private ImageFileReader menuBack;

	/**
	 * 決定音
	 */
	private WAVFileReader dicideSE;

	/**
	 * TitleScene を新しく生成
	 */
	public RuleScene () {
		bg = new ImageFileReader("images/rule.png");

		cursor = new ImageFileReader("images/player.png", 120, 160);

		menuBack = new ImageFileReader("images/menu_back.png", 88, 50);

		dicideSE = new WAVFileReader("sound/dicide.wav");
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;
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
		if (key == KeyEvent.VK_ENTER) {
			dicideSE.play();
			sceneFlg = SceneFlg.TITLE;
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

		graphics.drawImage(menuBack.getImage(), Execute.WINDOW_WIDTH - menuBack.getSize().width, Execute.WINDOW_HEIGHT - menuBack.getSize().height, null);

		graphics.drawImage(cursor.getImage().getSubimage(40, 0, 40, 40), Execute.WINDOW_WIDTH - menuBack.getSize().width - 40, Execute.WINDOW_HEIGHT - menuBack.getSize().height, null);
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
