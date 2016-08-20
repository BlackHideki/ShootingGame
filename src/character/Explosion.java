package character;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import etc.DPoint;
import file.ImageFileReader;
import flg.ActionFlg;

/**
 * 爆発エフェクトを構築する為のクラス
 * @author kudo
 *
 */
public class Explosion implements Character {

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
	 * チップをリストに格納
	 */
	private ArrayList<Point> chipList;

	/**
	 * 現在のチップ
	 */
	private int chipListIndex;

	/**
	 * アニメーションタイマー
	 */
	private float animeTimer;

	/**
	 * Explosion を新しく生成
	 */
	public Explosion(String imagePath, int chipNumX, int chipNumY) {
		image = new ImageFileReader(imagePath);

		size = new Dimension(image.getSize().width / chipNumX, image.getSize().height / chipNumY);

		position = new DPoint();

		chipList = new ArrayList<>();
		for (int i = 0; i < chipNumY; i++) {
			for (int j = 0; j < chipNumX; j++) {
				chipList.add(new Point(size.width * j, size.height * i));
			}
		}
	}

	/**
	 * Explosion を新しく生成
	 * @param imagePath
	 */
	public Explosion(String imagePath, int chipNumX, int chipNumY, int width, int height) {
		image = new ImageFileReader(imagePath, width, height);

		size = new Dimension(image.getSize().width / chipNumX, image.getSize().height / chipNumY);

		position = new DPoint();

		chipList = new ArrayList<>();
		for (int i = 0; i < chipNumY; i++) {
			for (int j = 0; j < chipNumX; j++) {
				chipList.add(new Point(size.width * j, size.height * i));
			}
		}
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		actionFlg = ActionFlg.READY;

		position.x = 0;
		position.y = 0;

		chipListIndex = 29;

		animeTimer = 0.0f;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		if (actionFlg.equals(ActionFlg.MOVE)) {
			float animeTime = 0.1f;

			animeTimer -= 1 / 60.0f;
			if (animeTimer <= 0.0f) {
				switch (chipListIndex) {
				case 29:
						chipListIndex = 30;
					break;

				case 30:
					chipListIndex = 31;
					break;

				case 31:
					chipListIndex = 32;
					break;

				case 32:
					chipListIndex = 33;
					break;

				case 33:
					chipListIndex = 34;
					break;

				case 34:
					chipListIndex = 35;
					break;

				case 35:
					init();
					return;
				}
				animeTimer = animeTime;
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
			graphics.drawImage(image.getImage().getSubimage(chipList.get(chipListIndex).x, chipList.get(chipListIndex).y, size.width, size.height), (int)position.x, (int)position.y, null);
		}
	}

	/**
	 * 移動フラグを取得
	 * @return
	 */
	public ActionFlg getActionFlg() {
		return actionFlg;
	}

	/**
	 * 移動フラグを格納
	 * @param actionFlg
	 */
	public void setActionFlg(ActionFlg actionFlg) {
		this.actionFlg = actionFlg;
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

}
