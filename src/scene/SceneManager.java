package scene;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

/**
 * 各シーンを管理する為のクラス
 * @author kudouhideki
 *
 */
public class SceneManager {

	/**
	 * シーンをリストに格納
	 */
	private LinkedList<Scene> sceneList;

	/**
	 * 現在のシーン
	 */
	private Scene currentScene;

	/**
	 * 新しく生成する
	 */
	public SceneManager() {
		sceneList = new LinkedList<>();
		sceneList.add(new TitleScene());
		sceneList.add(new MainScene());
		sceneList.add(new GameOverScene());
		sceneList.add(new RankingScene());
		sceneList.add(new RuleScene());

		currentScene = sceneList.get(0);
	}

	/**
	 * 初期化
	 */
	public void init() {
		currentScene.init();
	}

	/**
	 * 処理
	 */
	public void action() {
		if (currentScene.getSceneFlg() != null) {
			switch (currentScene.getSceneFlg()) {
			case TITLE:
					currentScene = sceneList.get(0);
				break;

			case MAIN:
				currentScene = sceneList.get(1);
				break;

			case GAMEOVER:
				GameOverScene gos = new GameOverScene();
				MainScene ms = (MainScene)currentScene;
				gos.setScore(ms.getScore());
				sceneList.set(2, gos);
				currentScene = sceneList.get(2);
				break;

			case RANKING:
				currentScene = sceneList.get(3);
				break;

			case RULE:
				currentScene = sceneList.get(4);
				break;
			}
			init();
		}

		currentScene.action();
	}

	/**
	 * キーが押された瞬間に呼び出される
	 * @param key
	 */
	public void keyPressed(int key) {
		currentScene.keyPressed(key);
	}

	/**
	 * キーが離された瞬間に呼び出される
	 */
	public void keyReleased() {
		currentScene.keyReleased();
	}

	/**
	 * マウスがクリックした瞬間に呼び出される
	 * @param position クリックした瞬間の座標
	 */
	public void mouseClick(Point position) {
		currentScene.mouseClick(position);
	}

	/**
	 * 描画
	 */
	public void paint(Graphics graphics) {
		currentScene.paint(graphics);
	}
}
