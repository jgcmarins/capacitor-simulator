
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

	private static final String E_PATH = new String("/images/e.png");
	private static final String P_PATH = new String("/images/p.png");
	private static final String BLANK_PATH = new String("/images/blank.png");

	private ElectricField Ef;
	private ElectricCharge q;
	private double m; // mass

	private double a; // acceleration a = ((q * Ef)/m)
	private double v; // velocity v = (v0 + (a * t)) -> (a * t)
	private double s; // position s = (s0 + (v0 * t) + ((a * (t*t))/2)) -> ((a * (t*t))/2)

	private double t; // total time

	private double particlePosition; // position on capacitor

	private ImageView image; // image of particle

	private double vf;

	public Particle(ElectricField Ef, ElectricCharge q, double m) {
		this.Ef = Ef;
		this.q = q;
		this.m = m;
		
		this.a = 0.0;
		this.v = 0.0;
		this.s = 0.0;
		this.t = 0.0;

		this. particlePosition = 0.0;

		this.image = new ImageView(new Image(getClass().getResourceAsStream(Particle.BLANK_PATH)));

		this.vf = 0.0;
	}

	/* setters */

	public void setElectricField(ElectricField Ef) { this.Ef = Ef; }

	public void setCharge(ElectricCharge q) { this.q = q; }

	public void setMass(double m) { this.m = m; }

	//public void setAcceleration(double a) { this.a = a; }

	//public void setVelocity(double v) { this.v = v; }

	//public void setPosition(double s) { this.s = s; }

	public void setTotalTime(double t) { this.t = t; }

	public void setParticlePosition(double x, double y) {
		this.image.setTranslateX(x);
		this.image.setTranslateY(y);
	}

	public void setImage() { // sets image based on charge
		if(this.q.get() < 0) this.image = new ImageView(new Image(getClass().getResourceAsStream(Particle.E_PATH)));
		else this.image = new ImageView(new Image(getClass().getResourceAsStream(Particle.P_PATH)));
	}

	public void setFinalVelocity(double vf) {
		this.vf = vf;
	}

	/* getters */

	public ElectricField getElectricField() { return this.Ef; }

	public ElectricCharge getCharge() { return this.q; }

	public double getMass() { return this.m; }

	public double getAcceleration() {
		this.calculateAcceleration();
		return this.a;
	}

	public double getVelocity(double t) {
		this.calculateVelocity(t);
		return this.v;
	}

	public double getPosition(double t) {
		this.calculatePosition(t);
		return this.s;
	}

	public double getTotalTime(double d) {
		this.calculateTotalTime(d);
		return this.t;
	}

	public double getParticlePosition(double length, double t, double d) {
		this.calculateParticlePosition(length, t, d);
		return this.particlePosition;
	}

	public ImageView getImage() { return this.image; }

	public double getFinalVelocity(double d) {
		this.calculateFinalVelocity(d);
		return this.vf;
	}

	/* methods */

	private void calculateAcceleration() {
		this.a = ((this.q.get() * this.Ef.get())/this.getMass()); // calculates the acceleration
	}

	private void calculateVelocity(double t) { // calculates the velocity
		double a = this.getAcceleration();
		if(a < 0) a *= -1;
		this.v = (a * t);
		this.v /= Math.pow(10, 10);
	}

	private void calculatePosition(double t) { // calculates the position
		double a = this.getAcceleration();
		if(a < 0) a *= -1;
		this.s = ((a * (t * t))/2);
	}

	private void calculateTotalTime(double d) { // calculates time taken to travel a distance d
		double a = this.getAcceleration();
		if(a < 0) a *= -1;
		this.t = (double) Math.sqrt((2 * d)/a);
	}

	private void calculateParticlePosition(double length, double t, double d) { // calculates particle position inside capacitor
		this.particlePosition = ((length * this.getPosition(t))/d);
	}

	private void calculateFinalVelocity(double d) {
		double a = this.getAcceleration();
		if(a < 0) a *= -1;
		this.vf = Math.sqrt(2 * a * d);
	}
}
