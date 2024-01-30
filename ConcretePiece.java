import java.util.ArrayList;

public abstract class ConcretePiece implements Piece {
    /**
     * variable that represents how much steps piece passed
     */
    int numOfSteps;
    /**
     * variable that represents ID of piece like A27
     */
    String id;
    /**
     * variable that save all the steps that piece passed
     */
    ArrayList<Position> steps;
    Player owner;
    /**
     * variable that represents the dest that piece passed
     */
    int distanceTraveled;

    public ConcretePiece(Player owner, String id, Position initPos) {
        this.owner = owner;
        this.id = id;
        this.steps = new ArrayList<>();
        steps.add(initPos);
        numOfSteps = 0;
        distanceTraveled = 0;
    }

    public abstract int getKills();

    @Override
    public Player getOwner() {
        return owner;
    }

    public void addSteps(Position xy) {//updates the variable "steps"
        this.steps.add(xy);
    }

    public void addDest(int dest) {
        distanceTraveled += dest;
    }
public int getDistanceTraveled(){
        return this.distanceTraveled;
}
    String getID() {
        return this.id;
    }

    String[] getStepsArr() {
        String[] stepsString = new String[steps.size()];
        for (int i = 0; i < steps.size(); i++) {
            stepsString[i] = steps.get(i).toString();
        }
        return stepsString;
    }

    public int getNumOfSteps() {
        return steps.size();
    }
}
