package etc;

import java.awt.Graphics;

import base.Execute;

/**
 * スコアを表示させる為のクラス
 * @author kudo
 *
 */
public class Score {

	/**
	 * 数値をイメージ化する為のクラス
	 */
	private Number number;

	/**
	 * スコア
	 */
	private static int score;

	/**
	 * Score を新しく生成
	 */
	public Score(String imagePath, int chipNumX, int chipNumY) {
		number = new Number(imagePath, chipNumX, chipNumY);
	}

	/**
	 * Score を新しく生成
	 */
	public Score(String imagePath, int chipNumX, int chipNumY, int width, int height) {
		number = new Number(imagePath, chipNumX, chipNumY, width, height);
	}

	/**
	 * 初期化
	 */
	public void init() {
		score = 0;
	}

	/**
	 * スコアを加算
	 */
	public void add() {
		score++;
	}

	/**
	 * 描画
	 */
	public void paint(Graphics graphics) {
		String tmp = String.valueOf(score);

		for (int i = 0; i < tmp.length(); i++) {
			graphics.drawImage(number.converter(tmp.substring(tmp.length() - (i + 1), tmp.length() - i)), Execute.WINDOW_WIDTH - number.getSize().width * (i + 1), Execute.WINDOW_HEIGHT - number.getSize().height, null);
		}
	}

	/**
	 * スコアを取得
	 * @return
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * スコアを格納
	 */
	public static void setScore(int _score) {
		score = _score;
	}

}
