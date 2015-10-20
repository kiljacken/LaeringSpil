package dk.kiljacken.laeringsspil;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class WrappedText {
	private float x, y;
	private String text;
	
	public WrappedText(GameContainer container, float x, float y, float width, String text) {
		this.x = x;
		this.y = y;
		
		Font font = container.getDefaultFont();

		String line = "";
		StringBuilder sb = new StringBuilder();
		
		String[] words = text.split(" ");
		
		for (int i=0; i < words.length; i++) {
			String word = words[i];
			
			if (!line.isEmpty())
				line += " ";
			
			int lineWidth = font.getWidth(line);
			int wordWidth = font.getWidth(word);
			
			if (wordWidth > width - lineWidth) {
				sb.append(line);
				sb.append('\n');
				
				line = "";
			}
			
			line += word;
		}
		
		sb.append(line);
		
		this.text = sb.toString();
	}
	
	public void render(GameContainer continer, Graphics g) {
		g.drawString(text, x, y);
	}
	
	public String getText() {
		return text;
	}

}
