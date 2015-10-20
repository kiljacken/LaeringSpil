package dk.kiljacken.laeringsspil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Assets {
	public static SpriteSheet dice = loadSpriteSheet("gfx/dice.png", 64, 64);
	public static SpriteSheet fields = loadSpriteSheet("gfx/fields.png", 72, 72);
	public static SpriteSheet players = loadSpriteSheet("gfx/players.png", 64, 64);
	public static SpriteSheet points = loadSpriteSheet("gfx/points.png", 64, 64);
	public static Image selected = loadImage("gfx/selected.png");
	public static Image card = loadImage("gfx/cards.png");
	public static Image background = loadImage("gfx/background.png");
	public static Image correct = loadImage("gfx/korrekt.png");
	public static Image wrong = loadImage("gfx/forkert.png");
	public static Image particle = loadImage("gfx/particle.png");
	public static Image victory = loadImage("gfx/sejr.png");
	public static String questionList = loadFile("questions.txt");
	
	public static Image loadImage(String ref) {
		try {
			return new Image(ref);
		} catch (SlickException e) {
			throw new RuntimeException(e);
		}
	}

	public static SpriteSheet loadSpriteSheet(String ref, int tw, int th) {
		try {
			return new SpriteSheet(ref, tw, th);
		} catch (SlickException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String loadFile(String ref) {
		try {
			InputStream is = Assets.class.getResourceAsStream("/"+ref);
			InputStreamReader isr = new InputStreamReader(is, "UTF8");
			StringBuilder sb = new StringBuilder();
		
			int data = 0;
			while ((data = isr.read()) != -1)
				sb.append((char)data);
			
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
