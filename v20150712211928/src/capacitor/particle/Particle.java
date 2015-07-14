
/*
* 
* Tue Jul 14 09:55:04 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.particle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import capacitor.field.ElectricField;
import capacitor.charge.ElectricCharge;

public class Particle {

	private ElectricField Ef;
	private ElectricCharge q;
	private double m; // mass

	private double a; // acceleration a = ((q * Ef)/m)
	private double v; // velocity v = (v0 + (a * t))
	private double s; // position s = ((v0 * t) + ((a * (t*t))/2))

	private double t; // total time

	private double particlePosition;

	private ImageView image;

	public Particle(ElectricField Ef, ElectricCharge q, double m) {
		this.Ef = Ef;
		this.q = q;
		this.m = m;
		
		this.a = 0.0;
		this.v = 0.0;
		this.s = 0.0;
		this.t = 0.0;

		this. particlePosition = 0.0;

		this.image = new ImageView();
	}

	/* setters */

	public void setCharge(ElectricCharge q) { this.q = q; }

	public void setElectricField(ElectricField Ef) { this.Ef = Ef; }

	public void setMass(double m) { this.m = m; }

	//public void setAcceleration(double a) { this.a = a; }

	//public void setVelocity(double v) { this.v = v; }

	//public void setPosition(double s) { this.s = s; }

	public void setTotalTime(double t) { this.t = t; }

	public void setParticlePosition(double x, double y) {
		this.image.setTranslateX(x);
		this.image.setTranslateY(y);
	}

	public void setImage() {
		if(this.q.get() < 0) this.image = new ImageView(new Image(getClass().getResourceAsStream("/images/e.png")));
		else this.image = new ImageView(new Image(getClass().getResourceAsStream("/images/p.png")));
	}

	/* getters */

	public ElectricCharge getCharge() { return this.q; }

	public ElectricField getElectricField() { return this.Ef; }

	public double getMass() { return this.m; }

	public double getAcceleration() {
		this.calculatesAcceleration();
		return this.a;
	}

	public double getVelocity(double t) {
		this.calculatesVelocity(t);
		return this.v;
	}

	public double getPosition(double t) {
		this.calculatesPosition(t);
		return this.s;
	}

	public double getTotalTime(double d) {
		this.calculatesTotalTime(d);
		return this.t;
	}

	public double getParticlePosition(double length, double t, double d) {
		this.calculatesParticlePosition(length, t, d);
		return this.particlePosition;
	}

	public ImageView getImage() { return this.image; }

	/* methods */

	private void calculatesAcceleration() {
		this.a = ((this.q.get() * this.Ef.get())/this.getMass()); // calculates the acceleration
	}

	private void calculatesVelocity(double t) { // calculates the velocity
		this.v = (this.getAcceleration() * t);
		if(this.v < 0) this.v *= -1;
	}

	private void calculatesPosition(double t) { this.s = (t + ((this.getAcceleration() * (t * t))/2)); } // calculates the position

	private void calculatesTotalTime(double d) {
		a = this.getAcceleration();
		if(a < 0) a *= -1;
		this.t = Math.sqrt((2 * d)/a);
	}

	private void calculatesParticlePosition(double length, double t, double d) {
		this.particlePosition = ((length * this.getPosition(t))/d);
	}
}
