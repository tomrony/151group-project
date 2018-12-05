import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/*
 * This class is used to represent a cloud, which is a whith oval shape
 * You can change the shape, position and size of a cloud
 * 
 */

public class Cloud {
    private int type;
    private int x;
    private int y;
    private int xa = 1;
    private int ya = 1;
    private int DIAMETER = 30;
    Random random = new Random();
    private DroneGame game;

    public Cloud(DroneGame inGame) {
        this.game = inGame;
        if (random.nextInt(2) == 1)
            type = 0;
        else
            type = 1;
        x = this.game.getWidth() + random.nextInt(500);
        y = random.nextInt(100);
        xa = random.nextInt(3) + 1;
    }

    public void setType(int val) {
        this.type = val;
        if (val == 3) {
            x = random.nextInt(1000);
            y = 400 - random.nextInt(140);
        }
        if (val == 4) {
            x = random.nextInt(1000);
            y = 400 - random.nextInt(170);
        }
    }

    void move(int gameWidth, int gameHeight) {
        if (type == 3 || type == 4) {
            if (x + xa + 100 < 0) {
                x = gameWidth;
            }
            xa = 3;
        } else if (type == 4) {
            if (x + xa + 200 < 0) {
                x = gameWidth;
            }
            xa = 4;
        } else {
            if (x + xa + 1 < 0) {
                x = gameWidth;
                y = random.nextInt(240) + 30;
                xa = (random.nextInt(1) + 1);
            }
        }
        x = x - xa;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.white);
        int ypos1 = y;
        if (type == 0) //For type 0 clouds
        {
            ypos1 += 10;
            g.fillOval(x - 3, ypos1, DIAMETER - 15, DIAMETER - 24);
            g.fillOval(x + 8, ypos1 - 3, DIAMETER - 7, DIAMETER - 20);
            g.fillOval(x, ypos1 - 2, DIAMETER - 10, DIAMETER - 20);
        } else if (type == 1) //For type 1 clouds
        {
            ypos1 += 10;
            g.fillOval(x - 3, ypos1 + 2, DIAMETER - 22, DIAMETER - 24);
            g.fillOval(x, ypos1 - 1, DIAMETER - 10, DIAMETER - 20);
            g.fillOval(x + 14, ypos1 + 2, DIAMETER - 22, DIAMETER - 24);
        } else {
            if (type == 3) //For near bunch of clouds
            {
                g.fillOval(x - 3, ypos1 + 2, 100, 100);
            }
            if (type == 4) //For opaque clouds
            {
                g.setColor(new Color(255, 255, 255, 45));
                g.fillOval(x - 3, ypos1 + 2, 200, 200);
            }
        }
    }
}
