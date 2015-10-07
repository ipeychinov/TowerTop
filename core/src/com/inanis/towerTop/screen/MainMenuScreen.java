package com.inanis.towerTop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inanis.towerTop.WatchTower;

public class MainMenuScreen implements Screen, InputProcessor {

	final WatchTower game;

	OrthographicCamera camera;

	Texture menuTileImg;

	Array<Rectangle> menuTiles;

	Vector3 touchPos;

	public MainMenuScreen(final WatchTower game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);

		menuTileImg = new Texture(Gdx.files.internal("menu/menuTile.png"));
		menuTiles = new Array<Rectangle>();
		loadTiles();

		touchPos = new Vector3();

		Gdx.input.setInputProcessor(this);
	}

	private void loadTiles() {
		for (int i = 0; i < 3; i++) {
			Rectangle tile = new Rectangle();
			tile.height = 104;
			tile.width = 500;
			tile.x = 240;
			tile.y = 100 + 144 * i;
			menuTiles.add(tile);
		}

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
		for (Rectangle tile : menuTiles) {
			game.batch.draw(menuTileImg, tile.x, tile.y);
		}
		game.font.draw(game.batch, "NEW GAME", 382, 455);
		game.font.draw(game.batch, "HIGHSCORE", 365, 310);
		game.font.draw(game.batch, "ABOUT", 415, 165);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
		}
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
		for (int i = 0; i < 3; i++) {
			Rectangle tile = menuTiles.get(i);
			if (tile.contains(touchPos.x, touchPos.y)) {
				switch (i) {
				case 0:
					game.setScreen(new CreditsScreen(game));
					break;
				case 1:
					game.setScreen(new ScoresScreen(game));
					break;
				case 2:
					game.setScreen(new GameScreen(game));
					break;
				}
			}
		}
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
