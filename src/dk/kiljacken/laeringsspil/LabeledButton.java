package dk.kiljacken.laeringsspil;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class LabeledButton extends Button {
	private String option;

	public LabeledButton(GameContainer container, float x, float y, String option) {
		super(container, x, y, container.getDefaultFont().getWidth(option), container.getDefaultFont().getLineHeight());
		this.option = option;
	}

	@Override
	public void onClick(float x, float y) {

	}

	@Override
	public void render(GameContainer container, Graphics g) {
		super.render(container, g);

		g.setColor(Color.white);
		g.drawString(option, getBounds().getX(), getBounds().getY());
	}
}
