package base;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import scene.SceneManager;

/**
 * ゲームの仕組みを構築する為に必要な機能を構築する為のクラス
 * @author kudouhideki
 *
 */
public class GameMechanism extends JPanel implements Runnable {

	/**
	 * ゲームの仕組みを簡略化したクラス
	 */
	private SceneManager sceneManager;

	/**
	 * 指定された幅、高さのパネルを新しく生成する
	 */
	public GameMechanism(int width, int height){
		setSize(width, height);

		sceneManager = new SceneManager();
	}

	/**
	 * 初期化
	 */
	public void init() {
		sceneManager.init();

		new Thread(this).start();
	}

	/**
	 * ゲームループ
	 */
	@Override
	public void run() {
		while(true){
			action();
			repaint();
			try{
				Thread.sleep(1000/60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 処理
	 */
	public void action(){
		sceneManager.action();
	}

	/**
	 * 描画
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		sceneManager.paint(graphics);
	}

	/**
	 * キーが押された瞬間に呼び出される
	 * @param key 押されたキーコード
	 */
	public void keyPressed(int key) {
		sceneManager.keyPressed(key);
	}

	/**
	 * キーが離された瞬間に呼び出される
	 */
	public void keyReleased() {
		sceneManager.keyReleased();
	}

	/**
	 * マウスがクリックした瞬間に呼び出される
	 * @param position クリックした瞬間の座標
	 */
	public void mouseClick(Point position) {
		sceneManager.mouseClick(position);
	}
}
