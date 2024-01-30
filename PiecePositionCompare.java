import java.util.Comparator;

public class PiecePositionCompare implements Comparator<Position> {
    public int compare(Position p1, Position p2) {
        if (p1.getWhoWasIn() != p2.getWhoWasIn()) {
            return p2.getWhoWasIn() - p1.getWhoWasIn();
        }

        if (p1.getX() != p2.getX()) {
            return p1.getX() - p2.getX();
        }
        return p1.getY() - p2.getY();
    }
}
