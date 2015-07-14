
/*
* 
* Sun Jul  5 03:56:16 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.field;

public class ElectricField {

	public static final double E0 = (8.85418 * (Math.pow(10, -12))); // permittivity

	private double E;
	private double sigma;

	public ElectricField(double sigma) {
		this.sigma = sigma;
		this.calculateElectricField();
	}

	/* setters */

	public void set(double E) { this.E = E; }

	public void setSigma(double sigma) { this.sigma = sigma; }

	/* getters */

	public double get() { return this.E; }

	public double getSigma() { return this.sigma; }

	/* methods */

	public void calculateElectricField() { this.E = (this.sigma/ElectricField.E0); } // calculates the electric field
}
