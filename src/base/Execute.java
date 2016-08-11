package base;

/**
 * プログラムを実行する為のクラス
 * @author kudouhideki
 *
 */
public class Execute {

	/**
	 * ウィンドウの幅
	 */
	public static final int WINDOW_WIDTH = 800;

	/**
	 * ウィンドウの高さ
	 */
	public static final int WINDOW_HEIGHT = 600;

	/**
	 * メインメソッド
	 * @param args
	 */
	public static void main(String[] args){
		GameFunctionBase gw = new GameFunctionBase("FastestFingerFirst2", WINDOW_WIDTH, WINDOW_HEIGHT);
		gw.setVisible(true);
	}

}
