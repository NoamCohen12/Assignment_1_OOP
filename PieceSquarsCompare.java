import java.util.Comparator;

/**
 * Print all pieces sorted in descending order by cumulative distance
 * traveled during the game, if the distance is equal
 * Sort by the number of the piece in ascending order,
 * if the number is equal, sort by the winning team first.
 * Show only pieces that have taken at least one step
 */
class PieceSquarsCompare implements Comparator<ConcretePiece> {

    private Player winner;

    public PieceSquarsCompare(Player winner) {
        this.winner = winner;
    }

    public int compare(ConcretePiece p1, ConcretePiece p2) {
        //Sort in descending order by the distance that the piece passed
        if (p1.getDistanceTraveled() != p2.getDistanceTraveled()){
            return p2.getDistanceTraveled() - p1.getDistanceTraveled();
        }
        //if the distances are equals,Sort by the piece's ID number in ascending order
        String stringOne = p1.getID().replaceAll("[^0-9]", "");//for example if string =A27 than 27
        int idFromStringOne = Integer.parseInt(stringOne);
        String stringTwo = p2.getID().replaceAll("[^0-9]", "");
        int idFromStringTwo = Integer.parseInt(stringTwo);

        if (idFromStringOne != idFromStringTwo) {
            return idFromStringOne - idFromStringTwo;
        }
        //if the IDs are equals Sort by the first winning team
        if (p1.getOwner() == winner)
            return 1;
        return 0;
    }
}