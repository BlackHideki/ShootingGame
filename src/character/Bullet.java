package character;

import java.awt.Dimension;
import java.awt.Graphics;

import base.Execute;
import etc.DPoint;
import file.ImageFileReader;
import flg.ActionFlg;

/**
 * 弾を構築する為のクラス
 * @author kudo
 *
 */
public class Bullet implements Character {

	/**
	 * 移動フラグ
	 */
	private ActionFlg actionFlg;

	/**
	 * イメージ
	 */
	private ImageFileReader image;

	/**
	 * サイズ
	 */
	private Dimension size;

	/**
	 * 表示座標
	 */
	private DPoint position;

	/**
	 * 移動角度
	 */
	private double angle;

	/**
	 * 弾の移動速度
	 */
	private int speed;

	/**
	 * Bullet を新しく生成
	 */
	public Bullet(String imagePath, int speed) {
		image = new ImageFileReader(imagePath);

		size = new Dimension(image.getSize().width, image.getSize().height);

		position = new DPoint(-size.width, -size.height);

		this.speed = speed;
	}

	/**
	 * Bullet を新しく生成
	 */
	public Bullet(String imagePath, int width, int height, int speed) {
		image = new ImageFileReader(imagePath, width, height);

		size = new Dimension(image.getSize().width, image.getSize().height);

		position = new DPoint(-size.width, -size.height);

		this.speed = speed;
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		actionFlg = ActionFlg.READY;

		position.x = 0;
		position.y = 0;

		angle = 0;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		if (actionFlg.equals(ActionFlg.MOVE)) {
			speed = 8;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));

			if (position.x < -size.width || position.x > Execute.WINDOW_WIDTH || position.y < -size.height || position.y > Execute.WINDOW_HEIGHT) {
				init();
			}
		}
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased(int key) {}

	/**
	 * 描画
	 */
	@Override
	public void paint(Graphics graphics) {
		if (actionFlg.equals(ActionFlg.MOVE)) {
			graphics.drawImage(image.getImage(), (int)position.x, (int)position.y, null);
		}
	}

	/**
	 * 行動フラグを取得
	 * @return
	 */
	public ActionFlg getActionFlg() {
		return actionFlg;
	}

	/**
	 * 行動フラグを格納
	 * @param actionFlg
	 */
	public void setActionFlg(ActionFlg actionFlg) {
		this.actionFlg = actionFlg;
	}

	/**
	 * サイズを取得
	 * @return
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * サイズを格納
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
	 * 表示座標を設定
	 * @param position
	 */
	public void setPosition(DPoint position) {
		this.position = position;
	}

	/**
	 * 角度を取得
	 * @return
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * 角度を格納
	 * @param angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * 角度を格納
	 * @param otherPosition
	 */
	public void setAngle(DPoint otherPosition, Dimension otherSize) {
		double tmp = Math.atan2((otherPosition.y + otherSize.height / 2) - (position.y + size.height / 2), (otherPosition.x + size.width / 2) - (position.x + size.width / 2));
		angle = tmp * 180d / Math.PI;
	}

}
