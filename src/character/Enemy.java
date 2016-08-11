package character;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import base.Execute;
import file.ImageFileReader;

/**
 * エネミーを構築する為のクラス
 * @author kudo
 *
 */
public class Enemy implements Character {

	/**
	 * イメージ
	 */
	private ImageFileReader image;

	/**
	 * サイズ
	 */
	private Dimension size;

	/**
	 * ランダムを生成するクラス
	 */
	private Random random;

	/**
	 * 表示するX座標のパターンを配列に格納
	 */
	private int[] xArray;

	/**
	 * 表示されているX座標
	 */
	private Point position;

	/**
	 * 表示するカラーパターンを配列に格納
	 */
	private int[] enemyColor;

	/**
	 * 表示されるエネミーの色
	 */
	private int paintColor;

	/**
	 * 移動速度
	 */
	private int speed;

	/**
	 * 移動フラグ
	 */
	private boolean moveFlg;

	/**
	 * Enemy を新しく生成
	 */
	public Enemy() {
		image = new ImageFileReader("images/enemy.png", 700, 500);

		size = new Dimension(50, 50);

		random = new Random();

		xArray = new int[Execute.WINDOW_WIDTH / size.width];
		for (int i = 0; i < xArray.length; i++) {
			xArray[i] = size.width * i;
		}
		position = new Point();

		enemyColor = new int[9];
		for (int i = 0; i < enemyColor.length; i++) {
			enemyColor[i] = size.height * (i + 1);
		}

		speed = 12;

		moveFlg = false;
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		paintColor = enemyColor[random.nextInt(enemyColor.length)];

		moveFlg = false;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		if (moveFlg) {
			if (position.y < Execute.WINDOW_HEIGHT) {
				position.y += speed;
			} else {
				init();
			}
		}
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased() {
	}

	/**
	 * 描画
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(image.getImage().getSubimage(90, paintColor, size.width, size.height), position.x, position.y, null);
	}

	/**
	 * サイズを取得
	 * @return
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * サイズを参照
	 * @param size
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * 表示座標を取得
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * 表示座標を取得
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * 移動フラグを取得
	 * @return
	 */
	public boolean getMoveFlg() {
		return moveFlg;
	}

	/**
	 * 移動フラグを格納
	 * @param moveFlg
	 */
	public void setMoveFlg(boolean moveFlg) {
		this.moveFlg = moveFlg;
	}

	/**
	 * 表示座標をランダムに格納
	 * @param x
	 */
	public void setPositionRandom(int x) {
		position.x =xArray[random.nextInt(xArray.length)];
		while (x == position.x) {
			position.x =xArray[random.nextInt(xArray.length)];
		}

		position.y = -size.height;
	}

}
