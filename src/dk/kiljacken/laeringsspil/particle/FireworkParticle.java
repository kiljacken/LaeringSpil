package dk.kiljacken.laeringsspil.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import dk.kiljacken.laeringsspil.Assets;

public class FireworkParticle extends Particle {
	private Color color;

	public FireworkParticle(float x, float y, float xVel, float yVel, int life, Color color) {
		super(x, y, xVel, yVel, life, Assets.particle);
		this.color = color;
	}
	
	@Override
	public boolean update(GameContainer container, int delta, ParticleSystem particleSystem) {
		if (Math.random() < 0.125) {
			FireworkParticle trail = new FireworkParticle(getPosition().x, getPosition().y, 0, 4, 100, color);
		
			particleSystem.addParticle(trail);
		}
		
		return super.update(container, delta, particleSystem);
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		Image sprite = getSprite();
		
		sprite.draw(getPosition().x - sprite.getWidth() / 2, getPosition().y - sprite.getHeight() / 2, color.darker(getLifeLeft() / getMaxLife()));
	}
}
