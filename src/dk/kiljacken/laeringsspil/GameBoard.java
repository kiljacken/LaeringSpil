package dk.kiljacken.laeringsspil;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import dk.kiljacken.laeringsspil.MainClass.Category;
import dk.kiljacken.laeringsspil.graph.Edge;
import dk.kiljacken.laeringsspil.graph.Node;

public class GameBoard extends Button {
	public static final float ORIGIN_X = 32.0f;
	public static final float ORIGIN_Y = 32.0f;
	public static final float FIELD_SIZE = 72.0f;
	
	private final Field[] fields;
	private final ArrayList<Field> traversedFields;
	private Field center;
	private boolean moved;
	private Field movedField;
	
	private String _catMap = "cpbgs";
	private String _catArr[] = ("pgcbpgc\n" +
	                            "b  p  p\n" +
			                    "g  c  b\n" +
	                            "cgpsbcg\n" +
			                    "p  g  c\n" +
	                            "b  b  p\n" +
			                    "gcbpgcb\n").split("\n");
	
	public GameBoard(GameContainer container, float xOff, float yOff) {
		super(container, xOff, yOff, 7 * FIELD_SIZE, 7 * FIELD_SIZE);
		this.fields = new Field[33];
		int i = 0;
		
		// Construct board nodes
		for (int r = 0; r < 7; r++) {
			// Every third row (including zero) get filled completely (skip 0)
			// All the other get filled only on edges and in middle (skip 2)
			int o = r % 3 == 0 ? 1 : 3;
			
			for (int c = 0; c < 7; c += o) {
				Category category = getCategoryFromPosition(c, r);
				
				this.fields[i++] = new Field(GameBoard.ORIGIN_X + c * GameBoard.FIELD_SIZE + xOff, GameBoard.ORIGIN_Y + r * GameBoard.FIELD_SIZE + yOff, category);
				
				if (c == 3 && r == 3)
					this.center = this.fields[i - 1];
			}
		}
		
		// Construct board edges
		for (int n = 0; n < this.fields.length; n++) {
			Node node = this.fields[n];
			
			// Loop through all other nodes, and check for neighbourship based on distance
			// Expensive but simple
			for (int on = 0; on < this.fields.length; on++) {
				Node otherNode = this.fields[on];
				
				if (node.distance(otherNode) <= FIELD_SIZE) {
					// Make sure we don't duplicate edges
					if (node.isConnectedTo(otherNode))
						continue;
					
					Edge edge = new Edge();
					edge.setNodeA(node);
					edge.setNodeB(otherNode);
				}
			}
		}
		
		this.traversedFields = new ArrayList<>();
	}
	
	public Category getCategoryFromPosition(int c, int r) {
		return Category.values()[_catMap.indexOf(_catArr[r].charAt(c))];
	}
	
	public Field getCenter() {
		return center;
	}
	
	public Field getFieldNear(float x, float y, float tolerance) {
		for (int n = 0; n < fields.length; n++) {
			Field field = fields[n];
			
			float dx = Math.abs(field.getX() - x);
			float dy = Math.abs(field.getY() - y);
			
			if (Math.sqrt(dx*dx + dy*dy) <= tolerance)
				return field;
		}
		
		return null;
	}
	
	public void setMovable(Field field, int numEdges) {
		traversedFields.add(field);
		
		if (numEdges <= 0) {
			return;
		} else if (numEdges == 1) {
			field.setMovable(true);
			return;
		} 
		
		for (Edge edge: field.getEdges()) {
			Field other = (Field) edge.getOther(field);
			
			if (!traversedFields.contains(other))
				setMovable(other, numEdges - 1);
		}
	}
	
	public void clearMovable() {
		traversedFields.clear();
		
		for (int i=0; i < fields.length; i++) {
			fields[i].setMovable(false);
		}
	}
	
	public boolean hasMoved() {
		return moved;
	}
	
	public Field getMovedField() {
		return movedField;
	}
	
	public void clearMoved() {
		moved = false;
		movedField = null;
	}
	
	public void render(GameContainer container, Graphics g) {
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			
			field.render(container, g);
		}
	}

	@Override
	public void onClick(float x, float y) {
		for (Field field: fields) {
			if (field.getBounding().contains(x, y) && field.isMovable()) {
				moved = true;
				movedField = field;
			}
		}
	}
}
