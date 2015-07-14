
/*
* 
* Sun Jul 12 22:28:11 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.lab;

import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class Information {

	private static final int PADDING = 10;

	/* values to be caught */

	private double sigma;
	private double d;
	private double m;
	private double q;

	/* fields to read values */

	private TextField sigmaInput;
	private TextField dInput;
	private TextField mInput;
	private TextField qInput;

	/* velocity label */

	public Label velocity;

	/* buttons */

	public Button set;
	public Button start;

	/* header and footer */

	private HBox header;
	private HBox footer;

	public Information() {

		this.header = new HBox(Information.PADDING);
		this.header.setPadding(new Insets(Information.PADDING));

		this.footer = new HBox(Information.PADDING);
		this.footer.setPadding(new Insets(Information.PADDING));

		this.set = new Button("Set");
		this.start = new Button("Start");

		this.createContent();
	}

	private void createContent() {
		this.sigmaInput = new TextField();
		this.dInput = new TextField();
		this.mInput = new TextField();
		this.qInput = new TextField();
		this.velocity = new Label();

		this.header.getChildren().addAll(new Label("Sigma (C/m²):"), this.sigmaInput, new Label("d (m):"), this.dInput,
			new Label("m (kg):"), this.mInput, new Label("q (C):"), this.qInput, this.set, this.start); // adding contents do header 

		this.footer.getChildren().addAll(new Label("Velocity:"), this.velocity, new Label("m/s")); // adding contents do footer
	}

	public HBox getHeader() { return this.header; }

	public HBox getFooter() { return this.footer; }

	public void setAll() { // setts all values
		this.sigma = Double.parseDouble(this.sigmaInput.getText());
		this.d = Double.parseDouble(this.dInput.getText());
		this.m = Double.parseDouble(this.mInput.getText());
		this.q = Double.parseDouble(this.qInput.getText());
	}

	public double getSigma() { return this.sigma; }

	public double getD() { return this.d; }

	public double getM() { return this.m; }

	public double getQ() { return this.q; }
}
