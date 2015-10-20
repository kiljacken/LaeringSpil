package dk.kiljacken.laeringsspil;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Rectangle;

public abstract class Button implements MouseListener {
	private final Rectangle bounds;
	private boolean clicked;
	private float x, y;
	private boolean acceptingInput = false;
	
	public Button(GameContainer container, float x, float y, float width, float height) {
		this.bounds = new Rectangle(x, y, width, height);
		this.clicked = false;
		
		container.getInput().addMouseListener(this);
	}
	
	public boolean update(GameContainer container, int delta) {
		if (clicked) {
			onClick(x, y);
			clicked = false;
			
			return true;
		}
		
		return false;
	}
	
	public void render(GameContainer container, Graphics g) {
		if (MainClass.DEBUG_SHOW_BOUNDS) {
			g.setColor(Color.red);
			g.draw(getBounds());
		}
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public abstract void onClick(float x, float y);
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		clicked = bounds.contains(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return acceptingInput;
	}
	
	public void setAcceptingInput(boolean value) {
		acceptingInput = value;
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
	}

	@Override
	public void mouseWheelMoved(int arg0) {
	}
}
