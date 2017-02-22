
/*
* 
* Sun Jul 12 21:22:41 BRT 2015
* author: Joao Gustavo Cabral de Marins
* e-mail: jgcmarins@gmail.com
* 
*/

package capacitor.main;

import javafx.application.Application;
import javafx.stage.Stage;

import capacitor.lab.Experiment;

public class Main extends Application {

	public void start(Stage stage) {
		Experiment e = new Experiment(stage);
	}

	public static void main(String args[]) {
		launch(args);
	}
}
