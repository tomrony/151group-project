import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovingPlain extends JPanel {
    private DroneGame game;

    public List<Cloud> clouds = new ArrayList<>();
    public List<Cloud> nearclouds = new ArrayList<>();
    public List<Cloud> opaqueclouds = new ArrayList<>();

    //Initialize
    public MovingPlain(DroneGame inGame) {
        this.game = inGame;
        for (int i = 0; i < 10; ++i) {
            Cloud tmpCloud = new Cloud(this.game);
            clouds.add(tmpCloud);
        }
        for (int i = 0; i < 50; ++i) {
            Cloud tmpCloud = new Cloud(this.game);
            tmpCloud.setType(3);
            nearclouds.add(tmpCloud);
        }
        for (int i = 0; i < 50; ++i) {
            Cloud tmpCloud = new Cloud(this.game);
            tmpCloud.setType(4);
            opaqueclouds.add(tmpCloud);
        }
    }

    void move() {
        for (int i = 0; i < clouds.size(); ++i) {
            clouds.get(i).move(this.game.getWidth(), this.game.getHeight());
        }
        for (int i = 0; i < nearclouds.size(); ++i) {
            nearclouds.get(i).move(this.game.getWidth(), this.game.getHeight());
        }
        for (int i = 0; i < opaqueclouds.size(); ++i) {
            opaqueclouds.get(i).move(this.game.getWidth(), this.game.getHeight());
        }
    }

    public void paint(Graphics2D g) {
        for (int i = 0; i < clouds.size(); ++i) {
            clouds.get(i).paint(g);
        }
        for (int i = 0; i < opaqueclouds.size(); ++i) {
            opaqueclouds.get(i).paint(g);
        }
        for (int i = 0; i < nearclouds.size(); ++i) {
            nearclouds.get(i).paint(g);
        }
    }
}
