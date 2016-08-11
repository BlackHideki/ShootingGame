package file;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * イメージファイルを読み込み、及び操作をする為のクラス
 * @author kudo
 *
 */
public class ImageFileReader {

	/**
	 * イメージ
	 */
	private BufferedImage image;

	/**
	 * イメージサイズ
	 */
	private Dimension size;

	/**
	 * コンストラクタ
	 * イメージファイルの読み込み、及び指定されたサイズに変更する
	 * @param filePath
	 * @param width
	 * @param height
	 */
	public ImageFileReader(String filePath, int width, int height){
		try{
				image = ImageIO.read(new File(filePath));
		}catch(IOException e){
			e.printStackTrace();
		}

		image = resize(image, new Dimension(width, height));
		size = new Dimension(image.getWidth(), image.getHeight());
	}

	/**
	 * コンストラクタ
	 * イメージファイルを読み込む
	 * @param filePath
	 * @param width
	 * @param height
	 */
	public ImageFileReader(String filePath){
		try{
				image = ImageIO.read(new File(filePath));
		}catch(IOException e){
			e.printStackTrace();
		}

		size = new Dimension(image.getWidth(), image.getHeight());
	}

	/**
	 * イメージファイルのサイズ変更を行う
	 * @param bi
	 * @param d
	 * @return tmp
	 */
	private BufferedImage resize(BufferedImage bi, Dimension d){
		BufferedImage tmp = new BufferedImage(d.width, d.height, bi.getType());
		tmp.getGraphics().drawImage(bi.getScaledInstance(d.width, d.height, Image.SCALE_AREA_AVERAGING), 0, 0, d.width, d.height, null);
		return tmp;
	}

	/**
	 * イメージを取得
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * イメージを格納
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
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
