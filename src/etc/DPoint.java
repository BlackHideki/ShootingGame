package etc;

/**
 * double型のPointクラス
 * @author kudo
 *
 */
public class DPoint {

	/**
	 * X座標
	 */
	public double x;

	/**
	 * Y座標
	 */
	public double y;

	/**
	 * DPoint を新しく生成
	 */
	public DPoint() {
		this.x = 0;

		this.y = 0;
	}

	/**
	 * DPoint を新しく生成
	 */
	public DPoint(double x, double y) {
		this.x = x;

		this.y = y;
	}

}
