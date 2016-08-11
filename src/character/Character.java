package character;

import java.awt.Graphics;

/**
 * 各キャラクターに最低限必要な機能を定義する為のインタフェース
 * @author kudo
 *
 */
public interface Character {

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
	 * 描画
	 * @param graphics
	 */
	public void paint(Graphics graphics);
}
