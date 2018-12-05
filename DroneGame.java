import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DroneGame extends JPanel {
    private MovingPlain background = new MovingPlain(this); //Moving plain such as clouds
    private List<Airplane> airForce = new ArrayList<>(); //List of airplanes
    public Drone mydrone = new Drone(this); //Drone object
    public Scores myscores = new Scores(this); //Score object
    public LaserBeam droneBeam = new LaserBeam(this);
    private int numAirPlane = 1; //Number of airplanes appear in a level
    private int numInitAirPlane = 6;
    private JLabel countDownLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel scoreLabel = new JLabel("SCORE: 0 of 5", SwingConstants.CENTER);
    private JLabel gameCountLabel = new JLabel("(Game: 0)", SwingConstants.CENTER);
    private JPanel infoPanel = new JPanel(); //Panel contains timer, score and game count
    private JPanel scorePanel = new JPanel(); //Panel contain score and game count only
    public Timer timer = new Timer(this);
    private int gameplayed = 0; //Game count
    private int collision = 0; //Number of collission
    private static Color bgcolor = new Color(116, 196, 255); //Background color

    private void createAirForce() {
        airForce.clear();
        for (int i = 0; i < numInitAirPlane; i++) {
            Airplane anAirplan = new Airplane(this);
            airForce.add(anAirplan);
        }
    }
    
    //Method to assignKeyListener to game object to enable key pressing events
    public void assignKeyListener()
    {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                mydrone.keyReleased(e);
                droneBeam.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                mydrone.keyPressed(e);
                droneBeam.keyPressed(e);
            }
        });
    }

    //Game object initialize
    public DroneGame() {
        this.setPreferredSize(new Dimension(600, 400));
        this.assignKeyListener(); //Assign key listener to game to allow controlling using key inputs
        setLayout(new BorderLayout());
        setFocusable(true); //Set focus to this object
        // create air lanes and add them into air force
        createAirForce();
        // add component
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setSize(600, 400);
        infoPanel.add(countDownLabel, BorderLayout.PAGE_START);
        //Score panel contains SCORE and GAME COUNT
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        scorePanel.add(scoreLabel);
        scorePanel.add(gameCountLabel);
        //Add scorePanel to infoPanel to the end
        infoPanel.add(scorePanel, BorderLayout.PAGE_END);
        infoPanel.setOpaque(false);
        add(infoPanel);
        //Set background color
        setBackground(bgcolor);
    }

    //Reset all game information
    public void reset() {
        background = new MovingPlain(this);
        mydrone = new Drone(this);
        myscores = new Scores(this);
        numAirPlane = 1;
        numInitAirPlane = 6;
        collision = 0;
        timer = new Timer(this);
        //Reset game count
        gameplayed = 0;
        setGamePlayed();
        myscores.reset(); //Reset score
        // create air lanes and add them into air force
        createAirForce();
    }

    //This method is called as the game progress
    private void move() {
        for (int i = 0; i < numAirPlane; i++) {
            Airplane anAirplan = airForce.get(i);
            anAirplan.move();
        }
        background.move();
        mydrone.move();
        droneBeam.move();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        background.paint(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < numAirPlane; i++) {
            Airplane anAirplan = airForce.get(i);
            anAirplan.paint(g2d);
        }
        mydrone.paint(g2d);
        droneBeam.paint(g2d);
        infoPanel.paint(g2d);
    }

    //Show dialog when the game is finished
    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }

    //Timer clicking to increase the time
    public void tick() {
        timer.tick();
    }

    //Increase the score whenever a level is finished
    public void addScore() {
        myscores.increase();
    }
    
    //Change the game count lable to display the new number of game played
    public void setGamePlayed() {
        this.gameCountLabel.setText("(Game: " + String.valueOf(this.gameplayed) + ")");
    }

    // set time string
    public void setScoreLabel(String text) {
        scoreLabel.setText(text);
    }

    // set time string
    public void setCountDownLabel(String text) {
        countDownLabel.setText(text);
    }

    // show next challenge after time out
    public void nextStage() {
        // add more air plane;
        numAirPlane++;
        if (numAirPlane > numInitAirPlane) {
            numAirPlane = 1;
        }
    }

    //Increase the number of collision
    public void increaseCollision() {
        this.collision++;
        System.out.println("Collision"+collision);
        if (this.collision > 2) //If the drone hit a plane for more than 2 times
        {
            this.collision = 0;
            this.gameplayed++;
            System.out.println("Game played" + gameplayed);
        }
        this.setGamePlayed();
    }

    //Return the current number of collision
    public int getCollision() {
        return this.collision;
    }

    //Return the current number of games played
    public int getGamePlayed() {
        return this.gameplayed;
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("A User Controlled Drone");
        DroneGame game = new DroneGame();
        game.setSize(600, 400);
        frame.setSize(600, 440);
        frame.add(game, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Control panel contains buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.GRAY);
        controlPanel.setPreferredSize(new Dimension(600, 40));
        //Exit button
        JButton exitBT = new JButton("EXIT");
        exitBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Reset button
        JButton resetBT = new JButton("RESET");
        resetBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
                game.requestFocus(); //We need to re-focus the game after clicking
            }
        });
        //Up button
        JButton upBT = new JButton("UP");
        upBT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.mydrone.setYA(-1);
                }
                game.requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.mydrone.setYA(0);
                }
                game.requestFocus();
            }
        });
        //Down button
        JButton downBT = new JButton("DOWN");
        downBT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.mydrone.setYA(1);
                }
                game.requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.mydrone.setYA(0);
                }
                game.requestFocus();
            }
        });
        //Shoot button
        JButton shootBT = new JButton("SHOOT");
        shootBT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.droneBeam.start_beam();
                }
                game.requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.droneBeam.stop_beam();
                }
                game.requestFocus();
            }
        });
        //Add buttons into panel
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(exitBT);
        controlPanel.add(resetBT);
        controlPanel.add(upBT);
        controlPanel.add(downBT);
        controlPanel.add(shootBT);
        controlPanel.setVisible(true);
        //Add panel into main frame
        frame.add(controlPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
        //Game loop to progress the game
        while (true) {
            game.move();
            game.repaint();
            game.tick();
            Thread.sleep(10);
        }
    }
}
