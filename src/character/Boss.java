package character;

import java.awt.Dimension;
import java.awt.Graphics;

import base.Execute;
import etc.DPoint;
import file.ImageFileReader;
import flg.ActionFlg;
import flg.MoveFlg;

public class Boss implements Character {

	/**
	 * 行動フラグ
	 */
	private ActionFlg actionFlg;

	/**
	 * 移動フラグ
	 */
	private MoveFlg moveFlg;

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
	 * 弾が発射するまでの時間
	 */
	private float shotTimer;

	/**
	 * 弾が発射フラグ
	 */
	private boolean shotFlg;

	/**
	 * 弾を発射させる角度を配列に格納
	 */
	private double[] shotAngle;

	/**
	 * Boss を新しく生成
	 */
	public Boss(String imagePath) {
		image = new ImageFileReader(imagePath);

		size = new Dimension(image.getSize().width, image.getSize().height);

		position = new DPoint(Execute.WINDOW_WIDTH / 2 - size.width / 2, -size.height);

		shotAngle = new double[180 / 15 + 1];
		for (int i = 0; i < shotAngle.length; i++) {
			shotAngle[i] = i * 15;
		}
	}

	/**
	 * Boss を新しく生成
	 */
	public Boss(String imagePath, int width, int height) {
		image = new ImageFileReader(imagePath, width, height);

		size = new Dimension(image.getSize().width, image.getSize().height);

		position = new DPoint(Execute.WINDOW_WIDTH / 2 - size.width / 2, -size.height);

		shotAngle = new double[180 / 15 + 1];
		for (int i = 0; i < shotAngle.length; i++) {
			shotAngle[i] = i * 15;
		}
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		actionFlg = ActionFlg.READY;

		moveFlg = MoveFlg.DOWN;

		position.x = Execute.WINDOW_WIDTH / 2 - size.width / 2;
		position.y = -size.height;

		shotTimer = 0.0f;

		shotFlg = false;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		if (actionFlg.equals(ActionFlg.MOVE)) {
			switch (moveFlg) {
			case DOWN:
				position.y += 3;
				if (position.y >= 0) {
					moveFlg = MoveFlg.LEFT;
				}
				break;

			case UP:
				break;

			case LEFT:
				position.x -= 3;
				if (position.x <= 0) {
					moveFlg = MoveFlg.RIGHT;
				}
				break;

			case RIGHT:
				position.x += 3;
				if (position.x >= Execute.WINDOW_WIDTH - size.width) {
					moveFlg = MoveFlg.LEFT;
				}
				break;
			}

			if (!shotFlg) {
				shotTimeManager();
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
	 * 弾の発射のタイミングを取得
	 * @return
	 */
	public void shotTimeManager() {
		float shotTime = 0.5f;

		shotTimer -= 1 / 60.0f;
		if (shotTimer <= 0.0f) {
			shotFlg = true;
			shotTimer = shotTime;
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
	 * 表示座標を格納
	 * @param position
	 */
	public void setPosition(DPoint position) {
		this.position = position;
	}

	/**
	 * 発射フラグを取得
	 * @return
	 */
	public boolean isShotFlg() {
		return shotFlg;
	}

	/**
	 * 発射フラグを格納
	 * @param shotFlg
	 */
	public void setShotFlg(boolean shotFlg) {
		this.shotFlg = shotFlg;
	}

	/**
	 * 発射させる弾の角度の配列を取得
	 * @return
	 */
	public double[] getShotAngle() {
		return shotAngle;
	}

	/**
	 * 発射させる弾の角度の配列を格納
	 * @return
	 */
	public void setShotAngle(double[] shotAngle) {
		this.shotAngle = shotAngle;
	}

}
