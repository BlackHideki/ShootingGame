package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import file.ImageFileReader;
import file.TextFileReader;
import file.WAVFileReader;
import flg.SceneFlg;

/**
 * ランキングシーンを構築する為のクラス
 * @author kudo
 *
 */
public class RankingScene implements Scene {

	/**
	 * シーンフラグ
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
	 * ランキング1イメージ
	 */
	private ImageFileReader rankingOne;

	/**
	 * ランキング2イメージ
	 */
	private ImageFileReader rankingTwo;

	/**
	 * ランキング3イメージ
	 */
	private ImageFileReader rankingThree;

	/**
	 * カーソル
	 */
	private ImageFileReader cursor;

	/**
	 * メニューバック
	 */
	private ImageFileReader menuBack;

	/**
	 * ランキング
	 */
	private TextFileReader ranking;

	/**
	 * 決定音
	 */
	private WAVFileReader dicideSE;

	/**
	 * RankingScene を新しく生成
	 */
	public RankingScene () {
		bg = new ImageFileReader("images/bg.png");

		logo = new ImageFileReader("images/ranking_logo.png", 480, 150);

		rankingOne = new ImageFileReader("images/ranking_one.png", 100, 100);

		rankingTwo = new ImageFileReader("images/ranking_two.png", 70, 70);

		rankingThree = new ImageFileReader("images/ranking_three.png", 50, 50);

		cursor = new ImageFileReader("images/player.png", 120, 160);

		menuBack = new ImageFileReader("images/menu_back.png", 88, 50);

		ranking = new TextFileReader("text/ranking.txt");

		dicideSE = new WAVFileReader("sound/dicide.wav");
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;
		ranking.textRead();
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

		graphics.drawImage(logo.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2, Execute.WINDOW_HEIGHT / 5, null);

		graphics.drawImage(rankingOne.getImage(), Execute.WINDOW_WIDTH / 2 - rankingOne.getSize().width * 2, Execute.WINDOW_HEIGHT / 2 - rankingOne.getSize().height / 2, null);
		graphics.drawImage(rankingTwo.getImage(), Execute.WINDOW_WIDTH / 2 - rankingOne.getSize().width * 2 + rankingOne.getSize().width / 2 - rankingTwo.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2, null);
		graphics.drawImage(rankingThree.getImage(), Execute.WINDOW_WIDTH / 2 - rankingOne.getSize().width * 2 + rankingOne.getSize().width / 2 - rankingThree.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2 + rankingTwo.getSize().height, null);

		graphics.setColor(Color.BLACK);

		graphics.setFont(new Font("メイリオ", Font.BOLD, 100));
		graphics.drawString(ranking.getText().get(0), Execute.WINDOW_WIDTH / 2 , Execute.WINDOW_HEIGHT / 2 - rankingOne.getSize().height / 2 + rankingOne.getSize().height);

		graphics.setFont(new Font("メイリオ", Font.BOLD, 70));
		graphics.drawString(ranking.getText().get(1), Execute.WINDOW_WIDTH / 2 + 25, Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2 + rankingTwo.getSize().height);

		graphics.setFont(new Font("メイリオ", Font.BOLD, 50));
		graphics.drawString(ranking.getText().get(2), Execute.WINDOW_WIDTH / 2 + 40, Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2 + rankingTwo.getSize().height + rankingThree.getSize().height);

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
