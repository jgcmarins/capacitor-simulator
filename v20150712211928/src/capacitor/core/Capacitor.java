
/*
* 
* Sun Jul  5 04:04:21 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.core;

import capacitor.field.ElectricField;

public class Capacitor {

	private ElectricField Ef; // electric field
	private double d; // distance

	public Capacitor(ElectricField Ef, double d) {
		this.Ef = Ef;
		this.d = d;
	}

	/* setters */	

	public void setElectricField(ElectricField Ef) { this.Ef = Ef; }

	public void setDistance(double d) { this.d = d; }

	/* getters */

	public ElectricField getElectricField() { return this.Ef; }

	public double getDistance() { return this.d; }
}
