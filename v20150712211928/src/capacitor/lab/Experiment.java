
/*
* 
* Sun Jul 12 21:36:04 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.lab;

import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import capacitor.field.ElectricField;
import capacitor.core.Capacitor;
import capacitor.charge.ElectricCharge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

public class Experiment {

	private static double t;

	private static final int EXP_W = 1200; // width
	private static final int EXP_H = 300; // heigth
	private static final int PADDING = 10;
	private static final int EXTREME_POSITION = (EXP_W - (17 * Experiment.PADDING)); // max width

	private static final double FPS = 0.016; // frames per second

	private static final String TITLE = new String("Capacitor");

	/* main panes */

	private Stage stage;
	private Scene scene;
	private VBox root;
	private GridPane grid;

	/* additional pane */

	private Information info;

	/* simulation animation */

	private Timeline delay;
	private KeyFrame frame;

	/* experiment components */

	private ElectricField field;
	private Capacitor capacitor;
	private ElectricCharge charge;

	/* images */

	private ImageView particle;
	private ImageView ce;
	private ImageView cp;

	public Experiment(Stage stage) {
		Experiment.t = 0; // time
		this.stage = stage;
		this.delay = new Timeline();
		this.info = new Information(this.delay);
		this.buildExperiment();
	}

	private Parent createContent() { // creates main content

		this.grid = new GridPane(); // capacitor grid
		this.grid.setPadding(new Insets(Experiment.PADDING));

		this.createCapacitor();

		this.grid.getChildren().addAll(this.cp, this.particle, this.ce); // adding components to grid

		this.root = new VBox(); // main structure
		this.root.setPadding(new Insets(Experiment.PADDING));
		root.setPrefSize(Experiment.EXP_W, Experiment.EXP_H); // setting size

		this.setEngine();

		this.delay.getKeyFrames().add(this.frame);
		this.delay.setCycleCount(Timeline.INDEFINITE);

		root.getChildren().addAll(this.info.getHeader(), this.grid, this.info.getFooter());

		return root;
	}

	private void setEngine() { // builds simulation engine
		this.frame = new KeyFrame(Duration.seconds(Experiment.FPS), event -> { // for each event updates particle position
			this.particle.setTranslateX(this.particle.getTranslateX() + (this.charge.getPosition(Experiment.t)));
			this.info.velocity.setText((new Double(this.charge.getVelocity(Experiment.t))).toString()); // updates velocity info
			Experiment.t += 0.0000001; // increments time
			if(this.particle.getTranslateX() >= this.ce.getTranslateX()) { // stops, when particle reaches capacitor's wall
				this.particle.setTranslateX(this.ce.getTranslateX());
				this.delay.stop();
			} else if(this.particle.getTranslateX() <= this.cp.getTranslateX()) {  // stops, when particle reaches capacitor's wall
				this.particle.setTranslateX(this.cp.getTranslateX());
				this.delay.stop();
			}
		});
	}

	private void buildExperiment() {
		this.scene = new Scene(this.createContent()); // creates scene

		this.info.set.setOnAction(e -> { // setting action for 'Set' button
			this.info.setAll();
			this.newExperiment();
		});

		this.info.start.setOnAction(e -> { // setting action for 'Start' button
			this.startExperiment();
		});

		this.stage.setTitle(Experiment.TITLE);
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	private void startExperiment() { this.delay.play(); } // starts experiment

	private void stopExperiment() { this.delay.stop(); } // stops experiment

	private void newExperiment() { // create nes experiment
		Experiment.t = 0;

		/* collects information */

		this.field = new ElectricField(this.info.getSigma());
		this.capacitor = new Capacitor(this.field, this.info.getD());
		this.charge = new ElectricCharge(this.field, this.info.getM(), this.info.getQ(), 0);

		/* creates particle based on charge */

		if(this.charge.get() < 0) this.particle = new ImageView(new Image(getClass().getResourceAsStream("/images/e.png")));
		else this.particle = new ImageView(new Image(getClass().getResourceAsStream("/images/p.png")));

		/* sets particle position and add to grid */

		this.setParticlePosition();

		GridPane.setConstraints(this.particle, 1, 0); // adding to the middle
		this.grid.getChildren().add(this.particle);
	}

	private void setParticlePosition() {
		this.particle.setTranslateX(((this.ce.getTranslateX() - this.cp.getTranslateX())/2));
		this.particle.setTranslateY(Experiment.PADDING);
	}

	private void createCapacitor() { // create capacitor components/images
		this.cp = new ImageView(new Image(getClass().getResourceAsStream("/images/cp.png")));
		this.cp.setTranslateX(0); // positive capacitor on the left

		this.ce = new ImageView(new Image(getClass().getResourceAsStream("/images/ce.png")));
		this.ce.setTranslateX(Experiment.EXTREME_POSITION); // negative capacitor on the right

		this.particle = new ImageView();
		this.setParticlePosition(); // particle on the middle

		GridPane.setConstraints(this.cp, 0, 0); // setting positive capacitor to first column
		GridPane.setConstraints(this.particle, 1, 0); // setting particle to second column
		GridPane.setConstraints(this.ce, 2, 0); // setting negative capacitor to third column
	}
}
