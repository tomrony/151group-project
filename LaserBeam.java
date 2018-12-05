import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class LaserBeam extends JPanel {
    private boolean isCollided = false;
    private DroneGame game;
    int x_start;
    int y_start;
    boolean isLaser = false;
    public LaserBeam(DroneGame game){
        this.game = game;

    }

    public void paint(Graphics2D g) {
        if(isLaser) {
            g.setColor(Color.green);
            g.fillRect(x_start, y_start, game.getWidth() - x_start, 5);
            g.setColor(Color.black);
            g.drawRect(x_start, y_start, game.getWidth() - x_start, 5);
        }
    }

    public void keyReleased(KeyEvent e) {
        //If space key is released
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            stop_beam();
        }

    }

    public void keyPressed(KeyEvent e) {
        //If space key is pressed
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
           start_beam();
        }
    }

    public void start_beam (){
        x_start = game.mydrone.getTopX()+(game.mydrone.getWIDTH()/2);
        y_start = game.mydrone.getTopY()+(game.mydrone.getHEIGHT()/2)-3 ;
        isLaser = true;
    }

    public void stop_beam(){
        isLaser = false;
    }

    @Override
    public Rectangle getBounds() {
        if(isLaser)
            return new Rectangle(x_start, y_start, game.getWidth() - x_start, 5);
        else
            return new Rectangle(0, 0, 0, 0);
    }

    public boolean getCollide() {
        return this.isCollided;
    }

    public void setCollide(boolean collide) {
        this.isCollided = collide;
    }

    //This method move the laser beam along with the drone
    void move() {
        x_start = game.mydrone.getTopX()+(game.mydrone.getWIDTH()/2);
        y_start = game.mydrone.getTopY()+(game.mydrone.getHEIGHT()/2)-3;
    }


}
