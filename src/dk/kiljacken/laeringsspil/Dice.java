package dk.kiljacken.laeringsspil;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dice extends Button {
	private Random random;
	private int value;
	
	public Dice(GameContainer container, float x, float y) throws SlickException {
		super(container, x, y, 64, 64);
		
		this.random = new Random();
		this.value = 6;
	}

	@Override
	public void onClick(float x, float y) {
		value = random.nextInt(6) + 1;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		super.render(container, g);
		
		float x = getBounds().getCenterX();
		float y = getBounds().getCenterY();

		Assets.dice.startUse();
		Image face = Assets.dice.getSprite(0,  getValue() - 1);
		face.drawEmbedded(x - face.getWidth() / 2, y - face.getHeight() / 2);
		Assets.dice.endUse();
	}
	
	public int getValue() {
		return value;
	}

}
