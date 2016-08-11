package character;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import file.ImageFileReader;

/**
 * プレイヤーを構築する為のクラス
 * @author kudo
 *
 */
public class Player implements Character {

	/**
	 * イメージ
	 */
	private ImageFileReader image;

	/**
	 * サイズ
	 */
	private Dimension size;

	/**
	 * 表示されているX座標
	 */
	private Point position;

	/**
	 * 左を向いている時のイメージ
	 */
	private int dirLeft;

	/**
	 * 右を向いている時のイメージ
	 */
	private int dirRight;

	/**
	 * 向き
	 */
	private int dir;

	/**
	 * イメージの中のX座標(アニメーション処理用)
	 */
	private int imageInX;

	/**
	 * イメージの中のX座標(切り替え前)
	 */
	private int imageInXBefore;

	/**
	 * 切り替えるまでの時間
	 */
	private float animeTimer;

	/**
	 * 最後に押下したキー
	 */
	private int key;

	/**
	 * Player を新しく生成
	 */
	public Player() {
		image = new ImageFileReader("images/player.png", 150, 200);

		size = new Dimension(50, 50);

		position = new Point(0, Execute.WINDOW_HEIGHT - 131);
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		dirLeft = 50;

		dirRight = 100;

		dir = dirRight;

		imageInX = 0;

		imageInXBefore = 0;

		animeTimer = 0.0f;

		key = -1;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		switch (key) {
		case KeyEvent.VK_LEFT:
			if (dir != dirLeft) {
				dir = dirLeft;
			}

			if (position.x >= - 9) {
				position.x -= 5;
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (dir != dirRight) {
				dir = dirRight;
			}
			if (position.x <= Execute.WINDOW_WIDTH - size.width + 9) {
				position.x += 5;
			}
			break;
		}

		if ( key != -1) {
			anime();
		} else {
			animeStop();
		}
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
		this.key = key;
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased() {
		key = -1;
	}

	/**
	 * 描画
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(image.getImage().getSubimage(imageInX, dir, size.width, size.height), position.x, position.y, null);
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
	 * @return
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * 表示座標を格納
	 * @param position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * 移動アニメーション
	 */
	private void anime() {
		float animeTime = 0.05f;

		if (animeTimer > 0.0f) {
			animeTimer -= 1 / 60.0f;
		} else {
			switch (imageInX) {
			case 0:
				imageInX = 50;
				imageInXBefore = 0;
				break;

			case 50:
				if (imageInXBefore == 100) {
					imageInX = 0;
				} else {
					imageInX = 100;
				}
				imageInXBefore = 50;
				break;

			case 100:
				imageInX = 50;
				imageInXBefore = 100;
				break;
			}
			animeTimer = animeTime;
		}
	}

	/**
	 * 移動アニメーション停止
	 */
	private void animeStop() {
		if (animeTimer != 0.0f) {
			animeTimer = 0.0f;
		}

		if (imageInX != 50) {
			imageInX = 50;
		}
	}

}
