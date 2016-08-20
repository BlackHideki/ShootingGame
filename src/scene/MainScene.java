package scene;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import character.BackGround;
import character.Boss;
import character.Bullet;
import character.Enemy;
import character.Explosion;
import character.Player;
import etc.DPoint;
import etc.Score;
import file.WAVFileReader;
import flg.ActionFlg;
import flg.CharacterFlg;
import flg.GameFlg;
import flg.SceneFlg;

/**
 * メインシーンを構築する為のクラス
 * @author kudo
 *
 */
public class MainScene implements Scene {

	/**
	 * シーンフラグ
	 */
	private SceneFlg sceneFlg;

	/**
	 * ゲームフラグ
	 */
	private GameFlg gameFlg;

	/**
	 * bgm
	 */
	private WAVFileReader bgm;

	/**
	 * ボスbgm
	 */
	private WAVFileReader bgmBoss;

	/**
	 * プレイヤーが弾を発射する音
	 */
	private WAVFileReader playerShotSE;

	/**
	 * ボスが弾を発射する音
	 */
	private WAVFileReader bossShotSE;

	/**
	 * 死んだ音
	 */
	private WAVFileReader deadSE;

	/**
	 * 背景
	 */
	private BackGround backGround;

	/**
	 * プレイヤー
	 */
	private Player player;

	/**
	 * ボス
	 */
	private Boss boss;

	/**
	 * エネミーをリストに格納
	 */
	private ArrayList<Enemy> enemyList;

	/**
	 * プレイヤーの弾をリストに格納
	 */
	private ArrayList<Bullet> playerBulletList;

	/**
	 * ボスの弾をリストに格納
	 */
	private ArrayList<Bullet> bossBulletList;

	/**
	 * エネミーの弾をリストに格納
	 */
	private ArrayList<Bullet> enemyBulletList;

	/**
	 * 爆発エフェクトをリストに格納
	 */
	private ArrayList<Explosion> explosionList;

	/**
	 * スコア
	 */
	private Score score;

	/**
	 * エネミーが出現されるまでの時間を計測
	 */
	private float appEnemyTimer;

	/**
	 * ゲームタイマー
	 */
	private float gameTimer;

	/**
	 * MainScene を新しく生成
	 */
	public MainScene () {
		bgm = new WAVFileReader("sound/main_bgm.wav");

		bgmBoss = new WAVFileReader("sound/boss_bgm.wav");

		playerShotSE = new WAVFileReader("sound/player_bullet.wav");

		bossShotSE = new WAVFileReader("sound/enemy_bullet.wav");

		deadSE = new WAVFileReader("sound/explosion.wav");

		backGround = new BackGround("image/main_bg.png");

		player = new Player("image/player.png", 3, 2);

		boss = new Boss("image/boss.png", 200, 112);

		enemyList = new ArrayList<>();
		int enemyNum = 50;
		for (int i = 0; i < enemyNum; i++) {
			enemyList.add(new Enemy("image/enemy.png"));
		}

		playerBulletList = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			playerBulletList.add(new Bullet("image/player_bullet.png", 8));
		}

		bossBulletList = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			bossBulletList.add(new Bullet("image/enemy_bullet.png", 16, 16, 6));
		}

		enemyBulletList = new ArrayList<>();
		for (int i = 0; i < 50 * enemyNum; i++) {
			enemyBulletList.add(new Bullet("image/enemy_bullet.png", 16, 16, 8));
		}

		explosionList = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			explosionList.add(new Explosion("image/exploision.png", 6, 10));
		}

		score = new Score("image/number.png", 10, 2, 40 * 10, 40 * 2);
	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		sceneFlg = null;

		gameFlg = GameFlg.READY;

		bgm.loop();

		backGround.init();

		player.init();

		boss.init();

		for (Enemy e : enemyList) {
			e.init();
		}

		for (Bullet b : playerBulletList) {
			b.init();
		}

		for (Bullet b : bossBulletList) {
			b.init();
		}

		for (Bullet b : enemyBulletList) {
			b.init();
		}

		for (Explosion ex : explosionList) {
			ex.init();
		}

		score.init();

		appEnemyTimer = 0.0f;

		gameTimer = 0.0f;
	}

	/**
	 * 処理
	 */
	@Override
	public void action() {
		backGround.action();

		player.action();

		for (Bullet b : playerBulletList) {
			b.action();
		}

		for (Bullet b : enemyBulletList) {
			b.action();
		}

		for (Explosion ex : explosionList) {
			ex.action();
		}

		if (player.isShotFlg()) {
			for (Bullet b : playerBulletList) {
				if (b.getActionFlg().equals(ActionFlg.READY)) {
					b.setPosition(new DPoint(player.getPosition().x + player.getSize().width / 2 - b.getSize().width / 2, player.getPosition().y - b.getSize().height));
					b.setAngle(270);
					b.setActionFlg(ActionFlg.MOVE);
					break;
				}
			}
			player.setShotFlg(false);
			playerShotSE.play();
		}

		switch (gameFlg) {
		case READY:
			gameTimer += 1 / 60.0f;
			if(gameTimer >= 3.0f) {
				if (gameFlg.equals(GameFlg.READY)) {
					gameFlg = GameFlg.START;
				}
			}
			break;

		case START:
			appEnemy();

			for (Enemy e : enemyList) {
				e.action();
			}

			for (Enemy e : enemyList) {
				if (e.isShotFlg() && e.getCharacterFlg().equals(CharacterFlg.ALIVE) && e.getActionFlg().equals(ActionFlg.MOVE)) {
					for (Bullet b : enemyBulletList) {
						if (b.getActionFlg().equals(ActionFlg.READY)) {
							b.setPosition(new DPoint(e.getPosition().x + e.getSize().width / 2 - b.getSize().width / 2, e.getPosition().y + e.getSize().height + b.getSize().height));
							b.setAngle(player.getPosition(), player.getSize());
							b.setActionFlg(ActionFlg.MOVE);
							break;
						}
					}
				}
			}

			/**
			 * プレイヤー弾と敵の当たり判定
			 */
			for (Enemy e : enemyList) {
				if (e.getCharacterFlg().equals(CharacterFlg.ALIVE) && e.getActionFlg().equals(ActionFlg.MOVE)) {
					for (Bullet b : playerBulletList) {
						if (b.getActionFlg().equals(ActionFlg.MOVE)) {
							if (isCollision(b.getPosition(), e.getPosition(), b.getSize(), e.getSize())) {
								score.add();
								deadSE.play();
								for (Explosion ex : explosionList) {
									if (ex.getActionFlg().equals(ActionFlg.READY)) {
										ex.setPosition(e.getPosition());
										ex.setActionFlg(ActionFlg.MOVE);
										break;
									}
								}
								e.setCharacterFlg(CharacterFlg.DEAD);
								b.init();
							}
						}
					}
				}
			}

			/**
			 * プレイヤーと敵の当たり判定
			 */
			for (Enemy e : enemyList) {
				if (isCollision(player.getPosition(), e.getPosition(), player.getSize(), e.getSize())) {
					deadSE.play();
					gameFlg = GameFlg.END;
				}
			}

			boolean enemyCheck = false;
			for (Enemy e : enemyList) {
				if (e.getCharacterFlg().equals(CharacterFlg.DEAD) || e.getActionFlg().equals(ActionFlg.STOP)) {
					enemyCheck = true;
				} else {
					enemyCheck = false;
					break;
				}
			}

			if(enemyCheck) {
				bgm.stop();
				bgmBoss.loop();
				gameFlg = GameFlg.BOSS;
				boss.setActionFlg(ActionFlg.MOVE);
			}
			break;

		case BOSS:
			boss.action();

			for (Bullet b : bossBulletList) {
				b.action();
			}

			/**
			 * プレイヤーとボスの当たり判定
			 */
			if (isCollision(player.getPosition(), boss.getPosition(), player.getSize(), boss.getSize())) {
				deadSE.play();
				gameFlg = GameFlg.END;
			}

			/**
			 * プレイヤーとボス弾の当たり判定
			 */
			for (Bullet b : bossBulletList) {
				if (isCollision(player.getPosition(), b.getPosition(), player.getSize(), b.getSize())) {
					deadSE.play();
					gameFlg = GameFlg.END;
				}
			}

			if (boss.isShotFlg()){
				int i = 0;
				for (Bullet b : bossBulletList) {
					if (b.getActionFlg().equals(ActionFlg.READY)) {
						b.setPosition(new DPoint(boss.getPosition().x + boss.getSize().width / 2 - b.getSize().width / 2, boss.getPosition().y + boss.getSize().height + b.getSize().height));
						b.setAngle(boss.getShotAngle()[i]);
						b.setActionFlg(ActionFlg.MOVE);
						i++;
						if (i > boss.getShotAngle().length - 1) {
							break;
						}
					}
				}
				bossShotSE.play();
				boss.setShotFlg(false);
			}

			/**
			 * プレイヤー弾とボスの当たり判定
			 */
			for (Bullet b : playerBulletList) {
				if (b.getActionFlg().equals(ActionFlg.MOVE)) {
					if (isCollision(b.getPosition(), boss.getPosition(), b.getSize(), boss.getSize())) {
						score.add();
						deadSE.play();
						b.init();
					}
				}
			}
			break;

		case END:
			if (bgm.isPlay()) {
				bgm.stop();
			}

			if (bgmBoss.isPlay()) {
				bgmBoss.stop();
			}
			sceneFlg = SceneFlg.GAMEOVER;
			break;

		default:
			break;
		}

		/**
		 * プレイヤーとエネミー弾の当たり判定
		 */
		for(Bullet b : enemyBulletList) {
			if (b.getActionFlg().equals(ActionFlg.MOVE)) {
				if (isCollision(player.getPosition(), b.getPosition(), player.getSize(), b.getSize())) {
					deadSE.play();
					gameFlg = GameFlg.END;
				}
			}
		}
	}

	/**
	 * キー押下
	 */
	@Override
	public void keyPressed(int key) {
		player.keyPressed(key);
	}

	/**
	 * キー解放
	 */
	@Override
	public void keyReleased(int key) {
		player.keyReleased(key);
	}

	/**
	 * マウスクリック
	 */
	@Override
	public void mouseClick(Point position) {}

	/**
	 * 描画
	 */
	@Override
	public void paint(Graphics graphics) {
		backGround.paint(graphics);

		player.paint(graphics);

		boss.paint(graphics);

		for (Enemy e : enemyList) {
			e.paint(graphics);
		}

		for (Bullet b : playerBulletList) {
			b.paint(graphics);
		}

		for (Bullet b : bossBulletList) {
			b.paint(graphics);
		}

		for (Bullet b : enemyBulletList) {
			b.paint(graphics);
		}

		for (Explosion ex : explosionList) {
			ex.paint(graphics);
		}

		score.paint(graphics);
	}

	/**
	 * ゲームフラグを取得
	 */
	@Override
	public SceneFlg getSceneFlg() {
		return sceneFlg;
	}

	/**
	 * ゲームフラグを格納
	 */
	@Override
	public void setSceneFlg(SceneFlg sceneFlg) {
		this.sceneFlg = sceneFlg;
	}

	/**
	 * エネミーを出現
	 */
	private void appEnemy() {
		float appEnemyTime = 1.0f;

		appEnemyTimer -= 1 / 60.0f;
		if (appEnemyTimer < 0.0f) {
			for (Enemy e : enemyList) {
				if (e.getActionFlg().equals(ActionFlg.READY)) {
					int i = enemyList.indexOf(e);
					if (i > 0) {
						e.setPositionRandom();
						while (e.isOverlapPosition(enemyList.get(i - 1).getPosition().x)) {
							e.setPositionRandom();
						}
					} else {
						e.setPositionRandom();
					}
					e.setAngle(90);
					e.setActionFlg(ActionFlg.MOVE);
					appEnemyTimer = appEnemyTime;
					break;
				}
			}
		}
	}

	/**
	 * 当たり判定
	 * @param p1
	 * @param p2
	 * @param d1
	 * @param d2
	 * @return
	 */
	private boolean isCollision(DPoint p1, DPoint p2, Dimension d1, Dimension d2) {
		boolean result = false;

		if (p1.x < p2.x + (d2.width - 10) && p1.x + (d1.width - 10) > p2.x && p1.y < p2.y + (d2.height - 10) && p1.y + (d1.height - 10) > p2.y) {
			result = true;
		}

		return result;
	}

}
