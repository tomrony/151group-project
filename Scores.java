public class Scores {
    private static int score = 0;
    private DroneGame game;

    public Scores(DroneGame game) {
        this.game = game;
    }

    public void reset() {
        score = 0;
        String text = String.format("SCORE: %d of 5", score);
        game.setScoreLabel(text);
    }

    public void increase() {
        score++;
        String text = String.format("SCORE: %d of 5", score);
        game.setScoreLabel(text);
    }

}
