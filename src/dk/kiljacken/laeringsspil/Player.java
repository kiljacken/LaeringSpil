package dk.kiljacken.laeringsspil;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import dk.kiljacken.laeringsspil.MainClass.Category;

public class Player {
	private Vector2f position;
	private Vector2f velocity;
	private Category category;
	private Field target;
	private boolean answered[];
	
	public Player(float x, float y, Category category) {
		this.position = new Vector2f(x, y);
		this.velocity = new Vector2f();
		this.category = category;
		this.answered = new boolean[]{false, false, false, false};
	}
	
	public Category getCategory() {
		return category;
	}

	public void update(GameContainer container, int delta) {
		if (isMoving()) {
			Vector2f diff = new Vector2f(target.getX() + 4, target.getY() + 4);
			diff.sub(position);
			
			diff.scale(delta / 2000.0f);
			
			velocity.add(diff);
		}
		
		position.add(velocity);
		velocity.scale(0.8f);
	}
	
	public void render(GameContainer container, Graphics g) {
		Assets.players.startUse();
		Image sprite = Assets.players.getSprite(category.ordinal(), 0);
		sprite.drawEmbedded(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
		Assets.players.endUse();
		
		Assets.points.startUse();
		for (int i = 0; i < answered.length; i++) {
			if (answered[i]) {
				Image pointSprite = Assets.points.getSprite(0, i);
				
				pointSprite.drawEmbedded(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight()/2);
			}
		}
		Assets.points.endUse();
	}

	public boolean isMoving() {
		if (target == null)
			return false;
		
		float dx = Math.abs(position.x - (target.getX() + 4));
		float dy = Math.abs(position.y - (target.getY() + 4));
		
		return (float)Math.sqrt(dx*dx + dy*dy) >= 1.0f;
	}
	
	public boolean arrived() {
		if (target == null)
			return false;
		
		float dx = Math.abs(position.x - (target.getX() + 4));
		float dy = Math.abs(position.y - (target.getY() + 4));
		
		return (float)Math.sqrt(dx*dx + dy*dy) <= 1.0f;
	}
	
	public boolean couldWin() {
		for (boolean answered: this.answered)
			if (!answered)
				return false;
		
		return true;
	}
	
	public void setTarget(Field movedField) {
		this.target = movedField;
	}

	public void clearTarget() {
		this.target = null;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public Field getTarget() {
		return target;
	}

	public void setAnswered(Category category, boolean value) {
		answered[category.ordinal()] = value;
	}
}
