public class Timer {

    private static final int TICK = 10;
    private static final int SECOND = 1000;
    private static int count = 0;
    protected static int TIME = 90;
    private DroneGame game;

    public String getTimeString(int time) {
        String min = Integer.toString(time / 60);
        if (time / 60 < 10) {
            min = "0" + min;
        }
        String second = Integer.toString(time % 60);
        if (time % 60 < 10) {
            second = "0" + second;
        }
        return String.format("TIME: %s:%s", min, second);
    }

    public Timer(DroneGame game) {
        this.game = game;
        String timeString = getTimeString(TIME);
        game.setCountDownLabel(timeString);
    }

    public void tick() {
        count += TICK;
        if (count >= SECOND) {
            TIME--;
            String timeString = getTimeString(TIME);
            game.setCountDownLabel(timeString);
            count = 0;
            if (TIME == 0) {
                game.addScore();
                game.nextStage();
                TIME = 90;
            }
        }

    }

}
