package com.inanis.towerTop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.inanis.towerTop.WatchTower;

public class ScoresScreen implements Screen, InputProcessor {

	final WatchTower game;

	OrthographicCamera camera;

	Texture gameOverTileImg;

	Preferences scorePrefs;

	public ScoresScreen(final WatchTower game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);

		gameOverTileImg = new Texture(
				Gdx.files.internal("menu/gameOverTile.png"));

		scorePrefs = Gdx.app.getPreferences("scores");
		if (!scorePrefs.contains("highScore")) {
			scorePrefs.putInteger("highScore", 0);
		}
		if (!scorePrefs.contains("lastScore")) {
			scorePrefs.putInteger("lastScore", 0);
		}

		Gdx.input.setInputProcessor(this);
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
		game.batch.draw(gameOverTileImg, 100, 54);
		game.font.draw(game.batch,
				"HIGH SCORE : " + scorePrefs.getInteger("highScore"), 310, 455);
		game.font.draw(game.batch,
				"LAST RUN : " + scorePrefs.getInteger("lastScore"), 340, 310);
		game.font.setScale(0.8f);
		game.font.draw(game.batch, "TAP ANYWHERE TO GET BACK TO THE MENU", 250,
				165);
		game.font.setScale(1.5f);
		game.batch.end();
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
		// TODO Auto-generated method stub
		game.setScreen(new MainMenuScreen(game));
		dispose();
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
