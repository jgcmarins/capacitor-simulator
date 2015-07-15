
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
import capacitor.particle.Particle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Experiment {

	private static final int EXP_W = 1200; // width
	private static final int EXP_H = 300; // heigth

	private static final int PADDING = 10;

	private static final int EXTREME_POSITION = (EXP_W - (17 * Experiment.PADDING)); // max width

	private static final double FPS = 0.016; // frames per second

	private static final String TITLE = new String("Capacitor");

	private static final String CP_PATH = new String("/images/cp.png");
	private static final String CE_PATH = new String("/images/ce.png");

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
	private Particle particle;

	/* images */

	private ImageView ce; // electrons side
	private ImageView cp; // protons side

	/* experiment time */

	private double time;

	public Experiment(Stage stage) {
		this.stage = stage;

		this.delay = new Timeline();
		this.info = new Information();

		this.time = 0.0;

		this.buildExperiment();
	}

	private Parent createContent() { // creates main content

		/* capacitor */

		this.grid = new GridPane(); // capacitor grid
		this.grid.setPadding(new Insets(Experiment.PADDING));

		this.createCapacitor();
		this.grid.getChildren().addAll(this.cp, this.particle.getImage(), this.ce); // adding components to grid

		/* main structure */

		this.root = new VBox();
		this.root.setPadding(new Insets(Experiment.PADDING));
		root.setPrefSize(Experiment.EXP_W, Experiment.EXP_H); // setting size

		this.setEngine();

		this.info.velocity.setText("0.0"); // updates velocity info
		this.info.time.setText("0.0"); // updates time info
		this.info.field.setText("0.0"); // updates field info
		this.info.acceleration.setText("0.0"); // updates acceleration info

		this.delay.getKeyFrames().add(this.frame);
		this.delay.setCycleCount(Timeline.INDEFINITE);

		root.getChildren().addAll(this.info.getHeader(), this.grid, this.info.getFooter());

		return root;
	}

	private void createCapacitor() { // create capacitor components/images
		this.cp = new ImageView(new Image(getClass().getResourceAsStream(Experiment.CP_PATH)));
		this.cp.setTranslateX(0); // positive capacitor on the left

		this.ce = new ImageView(new Image(getClass().getResourceAsStream(Experiment.CE_PATH)));
		this.ce.setTranslateX(Experiment.EXTREME_POSITION); // negative capacitor on the right

		this.particle = new Particle(null, null, 0); // initializing particle

		GridPane.setConstraints(this.cp, 0, 0); // setting positive side to first column
		GridPane.setConstraints(this.particle.getImage(), 1, 0); // setting particle to second column
		GridPane.setConstraints(this.ce, 2, 0); // setting negative side to third column
	}

	private void setEngine() { // builds simulation engine
		this.frame = new KeyFrame(Duration.seconds(Experiment.FPS), event -> { // for each event updates particle position
			if(this.particle.getCharge().get() < 0) { // for negative particles, position in x need to be subtracted
				double nextX = this.particle.getParticlePosition(this.getTotalLength(), this.getTime(), this.capacitor.getDistance());
				this.particle.getImage().setTranslateX(this.ce.getTranslateX() - nextX); // setting position by subtracting
			} else { // for positive particles, just set position
				this.particle.getImage().setTranslateX(
					this.particle.getParticlePosition(this.getTotalLength(), this.getTime(), this.capacitor.getDistance())
				);
			}

			this.info.velocity.setText((new Double(this.particle.getVelocity(this.getTime()))).toString()); // updates velocity info
			this.info.time.setText((new Double(this.getTime())).toString()); // updates time info

			if(this.particle.getImage().getTranslateX() >= this.ce.getTranslateX()) { // stops, when particle reaches capacitor's wall
				this.particle.getImage().setTranslateX(this.ce.getTranslateX());
				this.stopExperiment();
				this.info.velocity.setText((new Double(this.particle.getFinalVelocity(this.capacitor.getDistance()))).toString());
				this.info.time.setText((new Double(this.particle.getTotalTime(this.capacitor.getDistance()))).toString());
			} else if(this.particle.getImage().getTranslateX() <= this.cp.getTranslateX()) { // stops, when particle reaches capacitor's wall
				this.particle.getImage().setTranslateX(this.cp.getTranslateX());
				this.stopExperiment();
				this.info.velocity.setText((new Double(this.particle.getFinalVelocity(this.capacitor.getDistance()))).toString());
				this.info.time.setText((new Double(this.particle.getTotalTime(this.capacitor.getDistance()))).toString());
			}
		});
	}

	private void buildExperiment() {
		this.scene = new Scene(this.createContent()); // creates scene

		this.info.set.setOnAction(e -> { // setting action for 'Set' button
			this.stopExperiment();
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

	private void startExperiment() { // starts experiment
		this.time = System.currentTimeMillis();
		this.delay.play();
	}

	private void stopExperiment() { // stops experiment
		this.delay.stop();
		this.time = 0.0;
	}

	private void newExperiment() { // create nes experiment

		/* initialize capacitor components */

		this.field = new ElectricField(this.info.getSigma());
		this.capacitor = new Capacitor(this.field, this.info.getD());
		this.charge = new ElectricCharge(this.info.getQ());
		this.particle = new Particle(this.field, this.charge, this.info.getM());

		/* setting footer information */

		this.info.field.setText((new Double(this.field.get())).toString()); // updates field info
		this.info.acceleration.setText((new Double(this.particle.getAcceleration())).toString()); // updates acceleration info

		/* creates particle image based on charge */

		this.particle.setImage();

		/* sets particle position and add to grid */

		this.setParticlePosition();

		GridPane.setConstraints(this.particle.getImage(), 1, 0); // adds to the second column
		this.grid.getChildren().add(this.particle.getImage()); // adds to grid
	}

	private void setParticlePosition() { // sets particle position based on charge
		if(this.particle.getCharge().get() < 0) this.particle.setParticlePosition(this.ce.getTranslateX(), Experiment.PADDING);
		else this.particle.setParticlePosition(this.cp.getTranslateX(), Experiment.PADDING);
	}

	private double getTotalLength() {
		return (this.ce.getTranslateX() - this.cp.getTranslateX());
	}

	private double getTime() {
		double seconds = (System.currentTimeMillis() - this.time);
		return (seconds/1000);
	}
}
