package etc;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import file.ImageFileReader;

/**
 * 数値をイメージ化する為のクラス
 * @author kudo
 *
 */
public class Number {

	/**
	 * イメージ
	 */
	private ImageFileReader image;

	/**
	 * 数字のサイズ
	 */
	private Dimension size;

	/**
	 * 数値の座標を格納
	 */
	private int[] number;

	/**
	 * Number を新しく生成
	 */
	public Number(String imagePath, int chipNumX, int chipNumY) {
		image = new ImageFileReader(imagePath);

		size = new Dimension(image.getSize().width / chipNumX, image.getSize().height / chipNumY);

		number = new int[chipNumX * chipNumY];
		for (int i = 0; i < chipNumY; i++) {
			for (int j = 0; j < chipNumX; j++) {
				number[i * chipNumX  + j] = image.getSize().width / chipNumX * j;
			}
		}
	}

	/**
	 * Number を新しく生成
	 */
	public Number(String imagePath, int chipNumX, int chipNumY, int width, int height) {
		image = new ImageFileReader(imagePath, width, height);

		size = new Dimension(image.getSize().width / chipNumX, image.getSize().height / chipNumY);

		number = new int[chipNumX * chipNumY];
		for (int i = 0; i < chipNumY; i++) {
			for (int j = 0; j < chipNumX; j++) {
				number[i * chipNumX  + j] = image.getSize().width / chipNumX * j;
			}
		}
	}

	/**
	 * 引数で指定された数値をイメージに変換
	 * @param num
	 * @return
	 */
	public BufferedImage converter(int num) {
		return image.getImage().getSubimage(number[num], 0, size.width, size.height);
	}

	/**
	 * 引数で指定された数値をイメージに変換
	 * @param num
	 * @return
	 */
	public BufferedImage converter(String num) {
		int n = 0;
		try {
			n = Integer.valueOf(num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return image.getImage().getSubimage(number[n], 0, size.width, size.height);
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

}
