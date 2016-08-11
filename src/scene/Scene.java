package scene;

import java.awt.Graphics;
import java.awt.Point;

import flg.SceneFlg;

/**
 * 各シーンに最低限必要な機能を定義する為のインタフェース
 * @author kudo
 *
 */
public interface Scene {

	/**
	 * 初期化
	 */
	public void init();

	/**
	 * 処理
	 */
	public void action();

	/**
	 * キー押下
	 * @param key
	 */
	public void keyPressed(int key);

	/**
	 * キー解放
	 */
	public void keyReleased();

	/**
	 * マウスクリック時
	 * @param position
	 */
	public void mouseClick(Point position);

	/**
	 * 描画
	 * @param graphics
	 */
	public void paint(Graphics graphics);

	/**
	 * シーンフラグを取得
	 * @return
	 */
	public SceneFlg getSceneFlg();

	/**
	 * シーンフラグを格納
	 * @param sceneFlg
	 */
	public void setSceneFlg(SceneFlg sceneFlg);
}
