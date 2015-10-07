package com.inanis.towerTop.model;

import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Enemy extends Rectangle{
	private Rectangle groundHitbox, characterHitbox;
	int keyFrame;
	float elapsedTime = 0;

	public Enemy() {
		x = 850;
		y = 62;
		width = 125;
		height = 125;
		keyFrame = 0;
		
		groundHitbox = new Rectangle();
		groundHitbox.width = 55;
		groundHitbox.height = 5;
		
		characterHitbox = new Rectangle();
		characterHitbox.width = 15;
		characterHitbox.height = 40;
		
		setHitbox();
	}

	private void setHitbox() {
		// TODO Auto-generated method stub
		groundHitbox.x = this.x + 35;
		groundHitbox.y = this.y + 38;

		characterHitbox.x = this.x + 55;
		characterHitbox.y = this.y + 40;
	}

	public void move(float deltaTime) {
		if (x > 152) {
			this.x -= 35 * deltaTime;
		} else {
			elapsedTime += deltaTime;
			if (keyFrame == 0)
				keyFrame++;
			if ((elapsedTime / keyFrame > 1 / 24f) && (keyFrame < 32))
				keyFrame++;
		}
		setHitbox();
	}

	public boolean isHit(Cannonball cannonball) {
		return cannonball.overlaps(groundHitbox)
				|| cannonball.overlaps(characterHitbox);
	}

	public int getKeyFrameIndex() {
		return keyFrame;
	}

}
