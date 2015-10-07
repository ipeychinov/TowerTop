package com.inanis.towerTop.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Cannonball extends Rectangle {
	public float elapsedTime;
	public float angle;
	private float vox, voy;

	public Cannonball(float angle) {
		this.angle = angle;
		this.elapsedTime = 0;
		this.vox = 20 * MathUtils.cosDeg(angle);
		this.voy = 20 * MathUtils.sinDeg(angle);
	}

	public float getCurrentX() {
		return vox * elapsedTime * 10 + this.x;
	}

	public float getCurrentY() {
		return (float) ((voy * elapsedTime - Math.pow(elapsedTime, 2) * 5) * 10 + this.y);
	}

	@Override
	public boolean overlaps(Rectangle r) {
		return this.getCurrentX() < r.x + r.width
				&& this.getCurrentX() + this.width > r.x
				&& this.getCurrentY() < r.y + r.height
				&& this.getCurrentY() + this.height > r.y;
	}
}
