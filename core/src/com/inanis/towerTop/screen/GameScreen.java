package com.inanis.towerTop.screen;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.inanis.towerTop.WatchTower;
import com.inanis.towerTop.model.Cannonball;
import com.inanis.towerTop.model.Enemy;

public class GameScreen implements Screen, InputProcessor {

	final WatchTower game;

	OrthographicCamera camera;

	Texture groundImg, towerPieceImg, cannonballImg, heroImg;
	Texture enemyWalkImg;
	TextureAtlas enemyAnimationImg;

	Sprite cannonSprite;

	Array<Rectangle> tower;
	Array<Enemy> enemies;
	Array<Cannonball> cannonballs;

	long lastEnemyTime;
	float elapsedTime, cooldown;
	int score;

	Preferences scorePrefs;

	public GameScreen(final WatchTower game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);

		groundImg = new Texture(Gdx.files.internal("objects/ground.png"));
		towerPieceImg = new Texture(
				Gdx.files.internal("objects/towerPiece.png"));
		cannonballImg = new Texture(
				Gdx.files.internal("objects/cannonball.png"));
		heroImg = new Texture(Gdx.files.internal("characters/hero.png"));
		enemyWalkImg = new Texture(Gdx.files.internal("characters/walk.png"));
		enemyAnimationImg = new TextureAtlas(
				Gdx.files.internal("characters/enemyAnimation.atlas"));

		cannonSprite = new Sprite(new Texture(
				Gdx.files.internal("objects/cannon.png")));
		cannonSprite.setScale(0.14f);
		cannonSprite.setOrigin(135, 92);
		cannonSprite.setPosition(-5, 377);

		tower = new Array<Rectangle>();
		enemies = new Array<Enemy>();
		cannonballs = new Array<Cannonball>();
		loadTower();

		elapsedTime = 2;
		lastEnemyTime = 0;
		cooldown = 0;
		score = 0;

		scorePrefs = Gdx.app.getPreferences("scores");
		if (!scorePrefs.contains("highScore")) {
			scorePrefs.putInteger("highScore", 0);
		}
		if (!scorePrefs.contains("lastScore")) {
			scorePrefs.putInteger("lastScore", 0);
		}

		Gdx.input.setInputProcessor(this);
	}

	private void loadTower() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			Rectangle towerPiece = new Rectangle();
			towerPiece.height = 35;
			towerPiece.width = 150;
			towerPiece.x = 50;
			towerPiece.y = 100 + i * 35;
			tower.add(towerPiece);
		}
	}

	private void spawnCannonball(float angle) {
		Cannonball cannonball = new Cannonball(angle);
		cannonball.x = MathUtils.cosDeg(angle) * 75 + 112;
		cannonball.y = MathUtils.sinDeg(angle) * 75 + 108 + tower.size * 35;
		cannonball.width = 15;
		cannonball.height = 15;
		cannonballs.add(cannonball);
	}

	private void spawnEnemy() {
		enemies.add(new Enemy());
		lastEnemyTime = TimeUtils.millis();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0.8f, 1f, 0.8f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(groundImg, -20, -15, 1025, 120);
		for (Rectangle towerPiece : tower) {
			game.batch.draw(towerPieceImg, towerPiece.x, towerPiece.y,
					towerPiece.width, towerPiece.height);
		}
		for (Enemy enemy : enemies) {
			if (enemy.getKeyFrameIndex() == 0) {
				game.batch.draw(enemyWalkImg, enemy.x, enemy.y, enemy.width,
						enemy.height);
			} else {
				game.batch.draw(enemyAnimationImg.findRegion(String.format(
						"%04d", enemy.getKeyFrameIndex())), enemy.x, enemy.y,
						enemy.width, enemy.height);
			}
		}
		for (Cannonball cannonball : cannonballs) {
			cannonball.elapsedTime += Gdx.graphics.getDeltaTime();
			game.batch.draw(cannonballImg, cannonball.getCurrentX(),
					cannonball.getCurrentY(), cannonball.width,
					cannonball.height);
		}
		cannonSprite.draw(game.batch);
		game.batch.draw(heroImg, 75, tower.size * 35 + 100, 75, 135);
		game.font.setScale(1f);
		game.font.draw(game.batch, "Score : " + score, 800, 550);
		game.font.draw(game.batch, "Cooldown : " + String.format("%.2f", cooldown), 750, 500);
		game.font.setScale(1.5f);
		game.batch.end();

		elapsedTime += Gdx.graphics.getDeltaTime();
		if (elapsedTime >= 2) {
			cooldown = 0;
		} else {
			cooldown = 2 - elapsedTime;
		}

		if (Gdx.input.isTouched()) {
			if ((cannonSprite.getRotation() < 90) && (elapsedTime >= 2)) {
				cannonSprite.setRotation(cannonSprite.getRotation() + 0.7f);
			}
		}

		Iterator<Enemy> iter = enemies.iterator();
		while (iter.hasNext()) {
			Enemy enemy = iter.next();
			enemy.move(Gdx.graphics.getDeltaTime());
			if (enemy.getKeyFrameIndex() == 32) {
				if (tower.size - 1 == 0) {
					endGame();
				} else {
					tower.removeIndex(tower.size - 1);
					iter.remove();
					cannonSprite.setY(cannonSprite.getY() - 35);
				}
			}
			Iterator<Cannonball> cballIter = cannonballs.iterator();
			while (cballIter.hasNext()) {
				Cannonball cball = cballIter.next();
				if (enemy.isHit(cball)) {
					score++;
					iter.remove();
					cballIter.remove();
				}
				if ((cball.getCurrentX() > 1016) || (cball.getCurrentY() < 84)) {
					cballIter.remove();
				}
				if (cball.overlaps(tower.get(tower.size - 1))) {
					if (tower.size - 1 == 0) {
						endGame();
					} else {
						tower.removeIndex(tower.size - 1);
						cballIter.remove();
						cannonSprite.setY(cannonSprite.getY() - 35);
					}
				}
			}
		}

		if (TimeUtils.millis() - lastEnemyTime > MathUtils.random(4500, 6900))
			spawnEnemy();
	}

	private void endGame() {
		scorePrefs.putInteger("lastScore", score);
		if (scorePrefs.getInteger("highScore") < score) {
			scorePrefs.putInteger("highScore", score);
		}
		scorePrefs.flush();
		game.setScreen(new GameOverScreen(game));
		dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		groundImg.dispose();
		towerPieceImg.dispose();
		cannonballImg.dispose();
		heroImg.dispose();
		enemyWalkImg.dispose();
		enemyAnimationImg.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if ((elapsedTime >= 2) && (cannonSprite.getRotation() > (33 - tower.size*3))) {
			spawnCannonball(cannonSprite.getRotation());
			elapsedTime = 0;
		}
		cannonSprite.setRotation(0);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
