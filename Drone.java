/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Drone extends JPanel {
    private boolean isCollided = false;
    private int WIDTH = 0; //Width of the Drone
    private int HEIGHT = 0; //Height of the Drone
    private int xpos = 0; //Starting x position
    private int xa = 0; //x accelaration
    private int ypos = 200; //Starting y position
    private int ya = 0; //y accelaration
    private int velocity = 1; //Velocity of the Drone
    boolean freeze = false;
    int freeze_time_in_second = 5;
    int second = 1000;
    int tick = 10;
    int count = 0;
    private DroneGame game;

    private BufferedImage img;

    public Drone(DroneGame game) {
        this.game = game;
        try {
            this.img = ImageIO.read(new File("drone.png"));
            this.img = resize(this.img, (int) (this.img.getWidth() * 0.5), (int) (this.img.getHeight() * 0.5));
            this.WIDTH = this.img.getWidth();
            this.HEIGHT = this.img.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public void move() {

            if (xpos + xa > 0 && xpos + xa < game.getWidth() - WIDTH)
                xpos = xpos + xa;
            if (ypos + ya > 0 && ypos + ya < game.getHeight() - HEIGHT)
                ypos = ypos + ya;

    }

    public void paint(Graphics2D g) {
        if (this.img != null) {
            g.drawImage(this.img, xpos, ypos, this);
        }
        // freeze drone
        if(freeze){
            count+= tick;
            if(count>=second){
                freeze_time_in_second --;
                count = 0;
                if(freeze_time_in_second==0){
                    freeze_time_in_second = 5;
                    freeze = false;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        //If left or right key released then x accelaration is 0
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
            xa = 0;
        //If up or down key released then y accelaration is 0
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
            ya = 0;
    }

    public void keyPressed(KeyEvent e) {
        //If any arrow key is pressed
        if(!freeze) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                xa = -1 * velocity;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                xa = 1 * velocity;
            if (e.getKeyCode() == KeyEvent.VK_UP)
                ya = -1 * velocity;
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
                ya = 1 * velocity;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(xpos, ypos, WIDTH, HEIGHT);
    }

    public void setYA(int val) {
        ya = val * velocity;
    }

    public int getTopY() {
        return ypos;
    }
    public int getTopX() {
        return xpos;
    }
    public int getWIDTH(){
        return WIDTH;
    }
    public int getHEIGHT(){
        return HEIGHT;
    }
    public boolean getCollide() {
        return this.isCollided;
    }

    public void setCollide(boolean collide) {
        this.isCollided = collide;
        if(collide == true)
            freeze = true;
    }
}
