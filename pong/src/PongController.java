
// import java.awt.event.*;
// import java.net.URI;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.beans.binding.Bindings;
import java.net.URL;

public class PongController implements Initializable {

    @FXML
    private Pane board; // panneau central

    @FXML
    private Rectangle player;

    @FXML
    private Rectangle computer;

    @FXML
    private Circle ball;

    @FXML
    private Label score;

    private AnimationTimer timer;

    private double playerVelY = 10; // velocite de la raquette

    // Constructeur du Controller
    public PongController() {
        timer = new AnimationTimer() {
            // La méthode handle() représente la boucle de jeu
            @Override
            public void handle(long L) {
                // On récupère les entrées de notre joueur (méthode static)
                PongUtils.handlePlayer(player, playerVelY);
                // On met à jour les déplacements des objets (méthode static)
                PongUtils.updateGame(player, computer, ball);
                // On check si la partie est terminée
                checkEndGame();
            }

        };
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (ball.getCenterX() > 15) {
            switch (keyEvent.getCode()) {
                case UP:
                    if (player.getY() >= 0) {
                        playerVelY = -10;
                        player.setY(player.getY() + playerVelY);
                    }
                    break;
                case DOWN:
                    if (player.getY() <= 600) {
                        playerVelY = 10;
                        player.setY(player.getY() + playerVelY);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    public void onKeyReleased(KeyEvent keyEvent) {
        // On passe la vélocité à 0 quand on lâche les touches
        playerVelY = 0;
    }

    @FXML
    void reset(ActionEvent event) {
        timer.stop();
        // Et on remet les éléments à leur état initial
        ball.setCenterX(450);
        ball.setCenterY(350);
        player.setY(150);
        computer.setY(ball.getCenterY());
        PongUtils.resetGame();
    }

    @FXML
    void run(ActionEvent event) {
        board.requestFocus();
        timer.start();
        computer.setY(ball.getCenterY());
    }

    /***
     * // la balle a t-elle depasse cette valeur sur la gauche ? si oui alors on est
     * derriere la raquette !
     */
    public void checkEndGame() {
        if (ball.getCenterX() < 15) {
            timer.stop();
        }

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // on lie la variable 'score' qu iest dans POngUtils avec la variable score qui
        // est dans le controller cad avec le label
        // on fera un Binding
        score.textProperty().bind(Bindings.convert(PongUtils.GetscoreProperty()));
    }
}
