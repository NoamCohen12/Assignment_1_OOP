import java.util.Comparator;

/**
 * This comparator help to print all the squares sorted in descending order
 * according to the amount of different pieces
 * that stepped on them, if the amount is equal, sort by the X of the square,
 * if it is also equal, sort by the Y. Show only squares that have been stepped on
 * by at least two different pieces
 */
public class PiecePositionCompare implements Comparator<Position> {
    public int compare(Position p1, Position p2) {
        //Sort in descending order by the numbers of pieces that passed in "positions"
        if (p1.getWhoWasIn() != p2.getWhoWasIn()) {
            return p2.getWhoWasIn() - p1.getWhoWasIn();
        }
        //if the above numbers is the same, sort by the "X" value of the positions
        // if the "X"'s also the same, sort by the "Y" value in descending order
        if (p1.getX() != p2.getX()) {
            return p1.getX() - p2.getX();
        }
        return p1.getY() - p2.getY();
    }
}
