public class ConcretePlayer implements Player {
    private int wins;
    private  boolean isPlayerOne;

    public ConcretePlayer(boolean isPlayerOne) {
        this.wins = 0;
        this.isPlayerOne = isPlayerOne;

    }

    @Override
    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    @Override
    public int getWins() {
        return this.wins;
    }


    public void addWins() {
        this.wins = this.wins + 1;
    }
}



