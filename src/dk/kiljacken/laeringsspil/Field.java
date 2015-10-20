package dk.kiljacken.laeringsspil;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import dk.kiljacken.laeringsspil.MainClass.Category;
import dk.kiljacken.laeringsspil.graph.Edge;
import dk.kiljacken.laeringsspil.graph.Node;

public class Field extends Node {
	private Category category;
	private boolean movable;
	private Rectangle bounding;
	
	public Field(float x, float y, Category category) {
		super(x, y);
		
		this.category = category;
		this.bounding = new Rectangle(x - GameBoard.ORIGIN_X, y - GameBoard.ORIGIN_Y, GameBoard.FIELD_SIZE, GameBoard.FIELD_SIZE);
	}
	
	public void update(GameContainer container, int delta) {
		
	}
	
	public void render(GameContainer container, Graphics g) {
		Assets.fields.startUse();
		Assets.fields.getSprite(0, category.ordinal()).drawEmbedded(getX() - GameBoard.ORIGIN_X, getY() - GameBoard.ORIGIN_Y, GameBoard.FIELD_SIZE, GameBoard.FIELD_SIZE);
		Assets.fields.endUse();
		
		if (isMovable()) {
			Assets.selected.draw(getX() - GameBoard.ORIGIN_X, getY() - GameBoard.ORIGIN_Y, GameBoard.FIELD_SIZE, GameBoard.FIELD_SIZE);
		}
		
		if (MainClass.DEBUG_SHOW_BOUNDS) {
			g.setColor(Color.red);
			g.draw(getBounding());
		}
		
		if (MainClass.DEBUG_DRAW_EDGES) {
			for (Edge edge: getEdges()) {
				g.setColor(Color.green);
				g.drawLine(edge.getNodeA().getX(), edge.getNodeA().getY(), edge.getNodeB().getX(), edge.getNodeB().getY());
			}
		}
		
	}
	
	public boolean isMovable() {
		return movable;
	}
	
	public void setMovable(boolean value) {
		movable = value;
	}

	public Category getCategory() {
		return category;
	}

	public Rectangle getBounding() {
		return bounding;
	}
}
