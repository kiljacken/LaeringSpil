package dk.kiljacken.laeringsspil.particle;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ParticleSystem {
	private ArrayList<Particle> allParticles;
	private ArrayList<Particle> livingParticles;

	public ParticleSystem() {
		this.allParticles = new ArrayList<>();
		this.livingParticles = new ArrayList<>();
	}
	
	public void addParticle(Particle p) {
		allParticles.add(p);
	}
	
	public void update(GameContainer container, int delta) {
		for (int i = 0; i < allParticles.size(); i++) {
			Particle p = allParticles.get(i);
			
			if (p.update(container, delta, this))
				livingParticles.add(p);
		}
		
		ArrayList<Particle> temp = allParticles;
		allParticles = livingParticles;
		livingParticles = temp;
		livingParticles.clear();
	}
	
	public void render(GameContainer container, Graphics g) {
		for (int i = 0; i < allParticles.size(); i++) {
			Particle p = allParticles.get(i);
			
			p.render(container, g);
		}
	}
	
	public int getNumParticles() {
		return allParticles.size();
	}
}
