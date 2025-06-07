public class Score {
    private int currentScore;
    private int highScore;

    public void increment() {
        currentScore++;
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }
    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void reset() {
        currentScore = 0;
    }
}
