package character;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import base.Execute;
import etc.DPoint;
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
	private DPoint position;

	/**
	 * チップ座標
	 */
	private ArrayList<Point> chipList;

	/**
	 * 表示するチップを格納
	 */
	private Point currentChip;

	/**
	 * キー
	 */
	private int keyUp;
	private int keyDown;
	private int keyLeft;
	private int keyRight;

	/**
	 * 弾が発射するまでの時間
	 */
	private float shotTimer;

	/**
	 * 弾が発射フラグ
	 */
	private boolean shotFlg;

	/**
	 * Player を新しく生成
	 */
	public Player(String imagePath, int chipNumX, int chipNumY) {
		image = new ImageFileReader(imagePath);

		size = new Dimension(image.getSize().width / chipNumX, image.getSize().height / chipNumY);

		position = new DPoint(0, Execute.WINDOW_HEIGHT - size.height);

		chipList = new ArrayList<>();
		for (int i = 0; i < chipNumY; i++) {
			for (int j = 0; j < chipNumX; j++) {
				chipList.add(new Point(j * size.width, i * size.height));
			}
		}

		currentChip = null;
	}

	/**
	 * Player を新しく生成
	 */
	public Player(String imagePath, int chipNumX, int chipNumY, int width, int height) {
		image = new ImageFileReader(imagePath, width, height);

		size = new Dimension(image.getSize().width / chipNumX, image.getSize().height / chipNumY);

		position = new DPoint(Execute.WINDOW_WIDTH / 2 - size.width / 2, Execute.WINDOW_HEIGHT - size.height);

		chipList = new ArrayList<>();
		chipList = new ArrayList<>();
		for (int i = 0; i < chipNumY; i++) {
			for (int j = 0; j < chipNumX; j++) {
				chipList.add(new Point(j * size.width, i * size.height));
			}
		}

		currentChip = null;
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		position.x = Execute.WINDOW_WIDTH / 2 - size.width / 2;
		position.y = Execute.WINDOW_HEIGHT - size.height;

		keyUp = 0;
		keyDown = 0;
		keyLeft = 0;
		keyRight = 0;

		currentChip = chipList.get(0);

		shotTimer = 0.0f;

		shotFlg = false;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		int pressed = 1;
		int speed = 5;
		double angle = 0;

		if (keyUp == pressed && keyDown != pressed && keyLeft != pressed && keyRight != pressed) {
			if (!currentChip.equals(chipList.get(0))) {
				currentChip = chipList.get(0);
			}

			angle = 270;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyDown == pressed && keyUp != pressed && keyLeft != pressed && keyRight != pressed) {
			if (!currentChip.equals(chipList.get(0))) {
				currentChip = chipList.get(0);
			}

			angle = 90;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyLeft == pressed && keyUp != pressed && keyDown != pressed && keyRight != pressed) {
			if (!currentChip.equals(chipList.get(1))) {
				currentChip = chipList.get(1);
			}

			angle = 180;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyRight == pressed && keyUp != pressed && keyDown != pressed && keyLeft != pressed) {
			if (!currentChip.equals(chipList.get(2))) {
				currentChip = chipList.get(2);
			}

			angle = 0;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyUp == pressed && keyLeft == pressed && keyDown != pressed && keyRight != pressed) {
			if (!currentChip.equals(chipList.get(1))) {
				currentChip = chipList.get(1);
			}

			angle = 225;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyUp == pressed && keyRight == pressed && keyDown != pressed && keyLeft != pressed) {
			if (!currentChip.equals(chipList.get(2))) {
				currentChip = chipList.get(2);
			}

			angle = 315;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyDown == pressed && keyLeft == pressed && keyUp != pressed && keyRight != pressed) {
			if (!currentChip.equals(chipList.get(1))) {
				currentChip = chipList.get(1);
			}

			angle = 135;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (keyDown == pressed && keyRight == pressed && keyUp != pressed && keyLeft != pressed) {
			if (!currentChip.equals(chipList.get(2))) {
				currentChip = chipList.get(2);
			}

			angle = 45;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));
		}

		if (position.x < 0) {
			position.x = 0;
		}

		if (position.x > Execute.WINDOW_WIDTH - size.width) {
			position.x = Execute.WINDOW_WIDTH - size.width;
		}

		if (position.y < 0) {
			position.y = 0;
		}

		if (position.y > Execute.WINDOW_HEIGHT - size.height) {
			position.y = Execute.WINDOW_HEIGHT - size.height;
		}

		if (!shotFlg) {
			shotTimeManager();
		}
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
		int pressed = 1;
		switch (key) {
		case KeyEvent.VK_UP:
			if (keyUp != pressed) {
				keyUp = pressed;
			}
			break;

		case KeyEvent.VK_DOWN:
			if (keyDown != pressed) {
				keyDown = pressed;
			}
			break;

		case KeyEvent.VK_LEFT:
			if (keyLeft != pressed) {
				keyLeft = pressed;
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (keyRight != pressed) {
				keyRight = pressed;
			}
			break;
		}
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased(int key) {
		int released = 0;
		switch (key) {
		case KeyEvent.VK_UP:
			if (keyUp != released) {
				keyUp = released;
			}
			break;

		case KeyEvent.VK_DOWN:
			if (keyDown != released) {
				keyDown = released;
			}
			break;

		case KeyEvent.VK_LEFT:
			if (keyLeft != released) {
				keyLeft = released;
			}

			if (!currentChip.equals(chipList.get(0))) {
				currentChip = chipList.get(0);
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (keyRight != released) {
				keyRight = released;
			}

			if (!currentChip.equals(chipList.get(0))) {
				currentChip = chipList.get(0);
			}
			break;
		}
	}

	/**
	 * 描画
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(image.getImage().getSubimage(currentChip.x, currentChip.y, size.width, size.height), (int)position.x, (int)position.y, null);
	}

	/**
	 * 弾の発射のタイミングを取得
	 * @return
	 */
	public void shotTimeManager() {
		float shotTime = 0.1f;

		shotTimer -= 1 / 60.0f;
		if (shotTimer <= 0.0f) {
			shotFlg = true;
			shotTimer = shotTime;
		}
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
	public DPoint getPosition() {
		return position;
	}

	/**
	 * 表示座標を格納
	 * @param position
	 */
	public void setPosition(DPoint position) {
		this.position = position;
	}

	/**
	 * 弾の発射フラグを取得
	 * @return
	 */
	public boolean isShotFlg() {
		return shotFlg;
	}

	/**
	 * 弾の発射フラグを格納
	 * @param shotFlg
	 */
	public void setShotFlg(boolean shotFlg) {
		this.shotFlg = shotFlg;
	}

}
