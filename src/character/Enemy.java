package character;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import base.Execute;
import etc.DPoint;
import file.ImageFileReader;
import flg.ActionFlg;
import flg.CharacterFlg;

/**
 * エネミーを構築する為のクラス
 * @author kudo
 *
 */
public class Enemy implements Character {

	/**
	 * キャラクターフラグ
	 */
	private CharacterFlg characterFlg;

	/**
	 * 行動フラグ
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
	 * 表示されているX座標
	 */
	private DPoint position;

	/**
	 * X座標の表示位置パターンを配列に格納
	 */
	private double[] xArray;

	/**
	 * 移動角度
	 */
	private double angle;

	/**
	 * 弾を発射するまでの時間
	 */
	private float shotTimer;

	/**
	 * 弾が発射フラグ
	 */
	private boolean shotFlg;

	/**
	 * Enemy を新しく生成
	 */
	public Enemy(String imagePath) {
		image = new ImageFileReader(imagePath);

		size = new Dimension(image.getSize().width, image.getSize().height);

		position = new DPoint();

		xArray = new double[Execute.WINDOW_WIDTH / size.width];
		for (int i = 0; i < xArray.length; i++) {
			xArray[i] = i * size.width;
		}
	}

	/**
	 * Enemy を新しく生成
	 */
	public Enemy(String imagePath, int width, int height) {
		image = new ImageFileReader(imagePath, width, height);

		size = new Dimension(image.getSize().width, image.getSize().height);

		position = new DPoint();

		xArray = new double[Execute.WINDOW_WIDTH / size.width];
		for (int i = 0; i < xArray.length; i++) {
			xArray[i] = i * size.width;
		}
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		characterFlg = CharacterFlg.ALIVE;
		actionFlg = ActionFlg.READY;

		position.x = -1;
		position.y = -1;

		angle = 0;

		shotTimer = 0.0f;

		shotFlg = false;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		if (characterFlg.equals(CharacterFlg.ALIVE) && actionFlg.equals(ActionFlg.MOVE)) {
			int speed = 3;
			position.x += speed * Math.cos(Math.toRadians(angle));
			position.y += speed * Math.sin(Math.toRadians(angle));

			shotFlg = isShot();

			if (position.x < -size.width || position.x > Execute.WINDOW_WIDTH || position.y < -size.height || position.y > Execute.WINDOW_HEIGHT) {
				actionFlg = ActionFlg.STOP;
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
		if (characterFlg.equals(CharacterFlg.ALIVE) && actionFlg.equals(ActionFlg.MOVE)) {
			graphics.drawImage(image.getImage(), (int)position.x, (int)position.y, null);
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
	 * サイズを格納
	 * @param size
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * 表示座標を取得
	 */
	public DPoint getPosition() {
		return position;
	}

	/**
	 * 表示座標を格納
	 */
	public void setPosition(DPoint position) {
		this.position = position;
	}

	/**
	 * キャラクターフラグを取得
	 * @return
	 */
	public CharacterFlg getCharacterFlg() {
		return characterFlg;
	}

	/**
	 * キャラクターフラグを格納
	 * @param charFlg
	 */
	public void setCharacterFlg(CharacterFlg charFlg) {
		this.characterFlg = charFlg;
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
	 * 移動角度を取得
	 * @return
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * 移動角度を格納
	 * @param angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * 表示座標をランダムに格納
	 * @param x
	 */
	public void setPositionRandom() {
		Random random = new Random();
		position.x = xArray[random.nextInt(xArray.length)];
		position.y = -size.height;
	}

	/**
	 * 重複チェック
	 * @param x
	 * @return
	 */
	public boolean isOverlapPosition(double x) {
		boolean result = false;
		if (x == position.x) {
			result = true;
		}

		return result;
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

	/**
	 * 弾の発射タイミングを取得
	 * @return
	 */
	private boolean isShot() {
		boolean result = false;
		float shotTime = 0.6f;

		shotTimer -= 1 / 60.0f;
		if (shotTimer <= 0.0f) {
			result = true;
			shotTimer = shotTime;
		}

		return result;
	}

}
