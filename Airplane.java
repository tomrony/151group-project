import java.awt.*;
import java.util.Random;

public class Airplane {
    int x = 400;
    int y = 200;
    int xa = -1;
    int airplane_width = 120;
    int airplane_heigh = 30;
    Polygon AirplaneHead = null;
    Rectangle AirplaneBody = null;
    Polygon AirplaneWing1 = null;
    Polygon AirplaneWing2 = null;

    private DroneGame game;
    Random random = new Random();

    public Airplane(DroneGame game) {
        this.game = game;
        y = random.nextInt(400 - 30) + 30;
        xa = -1 * (random.nextInt(4) + 1);
        this.calculateShape();
    }

    private void calculateShape() {
        //Calculate position of the body
        AirplaneBody = new Rectangle(x, y, airplane_width, airplane_heigh);
        int[] xarrH = {x - 30, x + 1, x + 5};
        int[] yarrH = {y + 15, y - 1, y + 32};
        //Calculate position of the head
        AirplaneHead = new Polygon(xarrH, yarrH, 3);
        int[] xarrW1 = {x + 40, x + 90, x + 115};
        int[] yarrW1 = {y + 5, y, y - 32};
        //Calculate position of the wings
        AirplaneWing1 = new Polygon(xarrW1, yarrW1, 3);
        int[] xarrW2 = {x + 20, x + 90, x + 135};
        int[] yarrW2 = {y + 13, y + 20, y + 55};
        AirplaneWing2 = new Polygon(xarrW2, yarrW2, 3);
    }

    void move() {
            if (x + xa + airplane_width < 0) {
                x = game.getWidth();
                y = random.nextInt(240) + 30;
                xa = -1 * (random.nextInt(4) + 1);
            }
            x = x + xa;
            this.calculateShape();
            //Check if there is any collision
            if (collision()) {
                boolean prevStatus = game.mydrone.getCollide();
                if (prevStatus == false)
                    game.increaseCollision();
                game.mydrone.setCollide(true);
            } else {
                game.mydrone.setCollide(false);
            }

    }

    private boolean collision() {
        //Check beam collision
        if(collision_detection(game.droneBeam.getBounds())){
            x = game.getWidth();
            y = random.nextInt(240) + 30;
            xa = -1 * (random.nextInt(4) + 1);
        }
        //Check drone collision
        return collision_detection(game.mydrone.getBounds());
    }

    private boolean collision_detection(Rectangle target){
        if (target.intersects(this.getBounds())) {
            return true;
        } else {
            //Check if collide into the wing or head
            for (int i = (int) (target.getMinX()); i < target.getMaxX(); ++i) {
                for (int j = (int) (target.getMinY()); j < target.getMaxY(); ++j) {
                    Point tmp = new Point(i, j);
                    if (AirplaneHead.contains(tmp) || AirplaneWing1.contains(tmp) || AirplaneWing2.contains(tmp))
                        return true;
                }
            }
            return false; //No collision
        }
    }

    public void paint(Graphics2D g) {
        if (game != null) {
            //Draw body
            g.setColor(Color.white);
            g.fillRect(x, y, airplane_width, airplane_heigh);
            g.setColor(Color.black);
            g.drawRect(x, y, airplane_width, airplane_heigh);
            //Draw head
            g.setColor(Color.white);
            g.fillPolygon(AirplaneHead);
            g.setColor(Color.black);
            g.drawPolygon(AirplaneHead);
            //Draw 1st wing
            g.setColor(Color.white);
            g.fillPolygon(AirplaneWing1);
            g.setColor(Color.black);
            g.drawPolygon(AirplaneWing1);
            //Draw 2nd wing
            g.setColor(Color.white);
            g.fillPolygon(AirplaneWing2);
            g.setColor(Color.black);
            g.drawPolygon(AirplaneWing2);
        }
    }

    public Rectangle getBounds() {
        return AirplaneBody;
    }
}
