
/*
* 
* Sun Jul 12 22:28:11 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.lab;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
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

	private TextField sigmaNotationInput;
	private TextField dNotationInput;
	private TextField mNotationInput;
	private TextField qNotationInput;

	private Label sigmaLabel;
	private Label dLabel;
	private Label mLabel;
	private Label qLabel;
	private Label notationLabel;

	/* velocity label */

	public Label velocity;

	/* buttons */

	public Button set;
	public Button start;

	/* header and footer */

	private GridPane header;
	private HBox footer;

	public Information() {

		this.header = new GridPane();
		this.header.setVgap(5);
		this.header.setHgap(5);

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

		this.sigmaNotationInput = new TextField();
		this.dNotationInput = new TextField();
		this.mNotationInput = new TextField();
		this.qNotationInput = new TextField();

		this.sigmaLabel = new Label("Sigma (C/m²):");
		this.dLabel = new Label("d (m):");
		this.mLabel = new Label("m (kg):");
		this.qLabel = new Label("q (C):");
		this.notationLabel = new Label("Notation (x10):");

		GridPane.setConstraints(this.sigmaLabel, 0, 0);
		GridPane.setConstraints(this.sigmaInput, 1, 0);
		GridPane.setConstraints(this.dLabel, 2, 0);
		GridPane.setConstraints(this.dInput, 3, 0);
		GridPane.setConstraints(this.mLabel, 4, 0);
		GridPane.setConstraints(this.mInput, 5, 0);
		GridPane.setConstraints(this.qLabel, 6, 0);
		GridPane.setConstraints(this.qInput, 7, 0);
		GridPane.setConstraints(this.set, 8, 0);
		GridPane.setConstraints(this.start, 9, 0);
		GridPane.setConstraints(this.notationLabel, 0, 1);
		GridPane.setConstraints(this.sigmaNotationInput, 1, 1);
		GridPane.setConstraints(this.dNotationInput, 3, 1);
		GridPane.setConstraints(this.mNotationInput, 5, 1);
		GridPane.setConstraints(this.qNotationInput, 7, 1);

		this.header.getChildren().addAll(this.sigmaLabel, this.sigmaInput, this.dLabel, this.dInput,
			this.mLabel, this.mInput, this.qLabel, this.qInput, this.set, this.start,
			this.notationLabel, this.sigmaNotationInput, this.dNotationInput, this.mNotationInput, this.qNotationInput);

		this.footer.getChildren().addAll(new Label("Velocity:"), this.velocity, new Label("m/s")); // adding contents do footer
	}

	public GridPane getHeader() { return this.header; }

	public HBox getFooter() { return this.footer; }

	public void setAll() { // setts all values

		/* collects information */

		this.sigma = this.scientificNotationConverter(this.sigmaInput.getText(),
						this.sigmaNotationInput.getText()); // information about superficial density

		this.d = this.scientificNotationConverter(this.dInput.getText(),
					this.dNotationInput.getText()); // information about distance

		this.m = this.scientificNotationConverter(this.mInput.getText(),
					this.mNotationInput.getText()); // information about mass

		this.q = this.scientificNotationConverter(this.qInput.getText(),
					this.qNotationInput.getText()); // information about charge
	}

	public double getSigma() { return this.sigma; }

	public double getD() { return this.d; }

	public double getM() { return this.m; }

	public double getQ() { return this.q; }

	private double scientificNotationConverter(String coenficient, String expoent) {
		double number = Double.parseDouble(coenficient);
		double exp = Double.parseDouble(expoent);
		return (number * Math.pow(10, exp));
	}
}
