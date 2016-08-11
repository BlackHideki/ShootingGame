package scene;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import character.Enemy;
import character.Player;
import file.ImageFileReader;
import file.WAVFileReader;
import flg.GameFlg;
import flg.SceneFlg;

/**
 * メインシーンを構築する為のクラス
 * @author kudo
 *
 */
public class MainScene implements Scene {

	/**
	 * シーンフラグ
	 */
	private SceneFlg sceneFlg;

	/**
	 * ゲームフラグ
	 */
	private GameFlg gameFlg;

	/**
	 * 背景
	 */
	private ImageFileReader bg;

	/**
	 * bgm
	 */
	private WAVFileReader bgm;

	/**
	 * 死んだ音
	 */
	private WAVFileReader deadSE;

	/**
	 * プレイヤー
	 */
	private Player player;

	/**
	 * エネミー
	 */
	private ArrayList<Enemy> enemyList;

	/**
	 * エネミーが出現されるまでの時間を計測
	 */
	private float appEnemyTimer;

	/**
	 * スコア
	 */
	private int score;

	/**
	 * MainScene を新しく生成
	 */
	public MainScene () {
		bg = new ImageFileReader("images/bg.png");

		bgm = new WAVFileReader("sound/main_bgm.wav");

		deadSE = new WAVFileReader("sound/dead.wav");

		player = new Player();

		enemyList = new ArrayList<>();
		int enemyNum = 100;
		for (int i = 0; i < enemyNum; i++) {
			enemyList.add(new Enemy());
		}
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;

		bgm.loop();

		gameFlg = GameFlg.READY;

		player.init();

		appEnemyTimer = 0.0f;

		score = 0;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		player.action();
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
		player.keyPressed(key);
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased() {
		player.keyReleased();
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

		player.paint(graphics);
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
	 * スコアを格納
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * 当たり判定
	 * @param p1
	 * @param p2
	 * @param d1
	 * @param d2
	 * @return
	 */
	private boolean isCollision(Point p1, Point p2, Dimension d1, Dimension d2) {
		boolean result = false;

		if (p1.x < p2.x + (d2.width - 10) && p1.x + (d1.width - 10) > p2.x && p1.y < p2.y + (d2.height - 10) && p1.y + (d1.height - 10) > p2.y) {
			result = true;
		}

		return result;
	}

}
