import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Position {
    //Data
    private final int x;
    private final int y;
    private final Set<String> whoWasIn = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;

    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Functions
    public int getWhoWasIn() {
        return whoWasIn.size();
    }
    public void addWhoWasIn (String ID){
        this.whoWasIn.add(ID);
    }
    public boolean isInside() {
        return this.x >= 0 && this.x < 11 && this.y >= 0 && this.y < 11;
    }

    public boolean equals(Position dest) {
        return this.x == dest.x && this.y == dest.y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
