
/*
* 
* Sun Jul  5 21:22:52 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.charge;

import capacitor.field.ElectricField;

public class ElectricCharge {

	private ElectricField Ef; // electric field
	private double m; // mass
	private double q; // charge

	private double v0; // initial velocity

	private double a; // acceleration
	private double v; // velocity v = (v0 + (a * t))
	private double s; // position s = ((v0 * t) + ((a * (t*t))/2))

	public ElectricCharge(ElectricField Ef, double m, double q, double v0) {
		this.Ef = Ef;
		this.m = m;
		this.q = q;

		this.v0 = v0;
		
		this.calculateAcceleration();
		this.calculateVelocity(0.0); // at t = 0;
		this.calculatePosition(0.0); // at t = 0;
	}

	/* setters */

	public void set(double q) { this.q = q; }

	public void setElectricField(ElectricField Ef) { this.Ef = Ef; }

	public void setMass(double m) { this.m = m; }

	//public void setAcceleration(double a) { this.a = a; }

	//public void setVelocity(double v) { this.v = v; }

	//public void setPosition(double s) { this.s = s; }

	/* getters */

	public double get() { return this.q; }

	public ElectricField getElectricField() { return this.Ef; }

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

	/* methods */

	private void calculateAcceleration() { this.a = ((this.q * this.Ef.get())/this.m); } // calculates the acceleration

	private void calculateVelocity(double t) { this.v = (this.v0 + (this.getAcceleration() * t)); } // calculates the velocity

	private void calculatePosition(double t) { this.s = ((this.v0 * t) + ((this.getAcceleration() * (t*t))/2)); } // calculates the position
}
