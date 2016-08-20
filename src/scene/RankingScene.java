package scene;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import etc.Number;
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
	 * 数値をイメージ化する為のクラス
	 */
	private ArrayList<Number> numList;

	/**
	 * カーソルサイズ
	 */
	private Dimension cursorSize;

	/**
	 * RankingScene を新しく生成
	 */
	public RankingScene () {
		bg = new ImageFileReader("image/title_bg.jpg", 800, 600);

		logo = new ImageFileReader("image/ranking_logo.png");

		rankingOne = new ImageFileReader("image/ranking_one.png", 100, 100);

		rankingTwo = new ImageFileReader("image/ranking_two.png", 70, 70);

		rankingThree = new ImageFileReader("image/ranking_three.png", 50, 50);

		cursor = new ImageFileReader("image/player.png", 120, 80);

		menuBack = new ImageFileReader("image/menu_back.png", 81, 50);

		ranking = new TextFileReader("text/ranking.txt");

		dicideSE = new WAVFileReader("sound/dicide.wav");

		numList = new ArrayList<>();
		numList.add(new Number("image/number.png", 10, 2));
		numList.add(new Number("image/number.png", 10, 2, 40 * 10, 40 * 2));
		numList.add(new Number("image/number.png", 10, 2, 20 * 10, 20 * 2));

		cursorSize = new Dimension(40, 40);
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

		graphics.drawImage(logo.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2, Execute.WINDOW_HEIGHT / 8, null);

		graphics.drawImage(rankingOne.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 - rankingOne.getSize().height / 2, null);
		graphics.drawImage(rankingTwo.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2 + rankingOne.getSize().width / 2 - rankingTwo.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2, null);
		graphics.drawImage(rankingThree.getImage(), Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2 + rankingOne.getSize().width / 2 - rankingThree.getSize().width / 2, Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2 + rankingTwo.getSize().height, null);


		for (int i = ranking.getText().get(0).length() - 1; i >= 0; i--) {
			graphics.drawImage(numList.get(0).converter(Integer.valueOf(ranking.getText().get(0).substring(i, i + 1))),
					Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2 + logo.getSize().width - numList.get(0).getSize().width * (ranking.getText().get(0).length() -  i),
					Execute.WINDOW_HEIGHT / 2 - numList.get(0).getSize().height / 2, null);
		}
		for (int i = ranking.getText().get(1).length() - 1; i >= 0; i--) {
			graphics.drawImage(numList.get(1).converter(Integer.valueOf(ranking.getText().get(1).substring(i, i + 1))),
					Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2 + logo.getSize().width - numList.get(0).getSize().width * (ranking.getText().get(1).length() -  i) + numList.get(1).getSize().width / 4,
					Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2 + numList.get(1).getSize().height / 2, null);
		}
		for (int i = ranking.getText().get(2).length() - 1; i >= 0; i--) {
			graphics.drawImage(numList.get(2).converter(Integer.valueOf(ranking.getText().get(2).substring(i, i + 1))),
					Execute.WINDOW_WIDTH / 2 - logo.getSize().width / 2 + logo.getSize().width - numList.get(0).getSize().width * (ranking.getText().get(2).length() -  i) + numList.get(2).getSize().width,
					Execute.WINDOW_HEIGHT / 2 + rankingOne.getSize().height / 2 + rankingTwo.getSize().height + numList.get(2).getSize().height / 2, null);
		}

		graphics.drawImage(menuBack.getImage(), Execute.WINDOW_WIDTH - menuBack.getSize().width, Execute.WINDOW_HEIGHT - menuBack.getSize().height, null);
		graphics.drawImage(cursor.getImage().getSubimage(0, cursorSize.height, cursorSize.width, cursorSize.height), Execute.WINDOW_WIDTH - menuBack.getSize().width - cursorSize.width, Execute.WINDOW_HEIGHT - menuBack.getSize().height, null);
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
