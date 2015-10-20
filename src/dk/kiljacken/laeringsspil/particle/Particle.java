package dk.kiljacken.laeringsspil.particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Particle {
	private Vector2f position;
	private Vector2f velocity;
	private int maxLife;
	private int life;
	
	private Image sprite;
	
	public Particle(float x, float y, float xVel, float yVel, int life, Image sprite) {
		this.position = new Vector2f(x, y);
		this.velocity = new Vector2f(xVel, yVel);
		this.maxLife = life;
		this.life = life;
		this.sprite = sprite;
	}
	
	public boolean update(GameContainer container, int delta, ParticleSystem particleSystem) {
		this.life -= delta;
		
		this.position.add(velocity.copy().scale(delta).scale(1.0f / 1000.0f));
		
		if (life > 0.0f)
			return true;
		else
			return false;
	}
	
	public void render(GameContainer container, Graphics g) {
		sprite.draw(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public int getMaxLife() {
		return maxLife;
	}
	
	public int getLifeLeft() {
		return life;
	}
	
	public Image getSprite() {
		return sprite;
	}
}
