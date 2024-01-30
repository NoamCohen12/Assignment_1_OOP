import java.util.ArrayList;

public abstract class ConcretePiece implements Piece {
    /**
     * variable that represents how much steps piece passed
     */
    protected int numOfSteps;
    /**
     * variable that represents ID of piece like A27
     */
    protected String id;
    /**
     * variable that save all the steps that piece passed
     */
    protected ArrayList<Position> steps;
    protected Player owner;
    /**
     * variable that represents the dest that piece passed
     */
    protected int distanceTraveled;

    public ConcretePiece(Player owner, String id, Position initPos) {
        this.owner = owner;
        this.id = id;
        this.steps = new ArrayList<>();
        steps.add(initPos);
        numOfSteps = 0;
        distanceTraveled = 0;
    }
//getters
    public abstract int getKills();

    @Override
    public Player getOwner() {
        return owner;
    }

    /**
     * updates the variable "steps"
     * @param xy
     */
    public void addSteps(Position xy) {
        this.steps.add(xy);
    }

    /**
     * updates the variable "distanceTraveled"
     * @param dest
     */
    public void addDest(int dest) {
        distanceTraveled += dest;
    }
public int getDistanceTraveled(){
        return this.distanceTraveled;
}
   public String getID() {
        return this.id;
    }

    /**
     *this function converts an array of positions to an array of String of positions for print at game finished
     * @return String[]
     */
    public String[] getStepsArr() {
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
