package character;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import base.Execute;
import file.ImageFileReader;

public class BackGround implements Character {

	/**
	 * イメージをリストに格納
	 */
	private ArrayList<ImageFileReader> imageList;

	/**
	 * 表示座標をリストに格納
	 */
	private ArrayList<Point> positionList;

	/**
	 * BackGround を新しく生成
	 */
	public BackGround(String imagePath) {
		imageList = new ArrayList<>();
		imageList.add(new ImageFileReader(imagePath));
		imageList.add(new ImageFileReader(imagePath));

		positionList = new ArrayList<>();
	}

	@Override
	public void init() {
		positionList.clear();
		positionList.add(new Point(0, 0));
		positionList.add(new Point(0, -Execute.WINDOW_HEIGHT));
	}

	@Override
	public void action() {
		for (Point p : positionList) {
			if (p.y >= Execute.WINDOW_HEIGHT) {
				p.y = -Execute.WINDOW_HEIGHT;
			}
			p.y += 5;
		}
	}

	@Override
	public void keyPressed(int key) {}

	@Override
	public void keyReleased(int key) {}

	@Override
	public void paint(Graphics graphics) {
		for (ImageFileReader ifr : imageList) {
			graphics.drawImage(ifr.getImage(), positionList.get(imageList.indexOf(ifr)).x, positionList.get(imageList.indexOf(ifr)).y, null);
		}
	}

}
