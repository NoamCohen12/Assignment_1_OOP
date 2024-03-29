import java.util.Comparator;

/**
 * This comparator help to print all the pieces sorted in descending order
 * according to the amount eaten, if the amount is equal, sort according to the ID number
 * in ascending order, if the number is equal, sort according to the winning team first
 */
class PieceKillsCompare implements Comparator<Pawn> {
    private final Player winner;
    public PieceKillsCompare(Player winner) {
        this.winner = winner;
    }
    // Sort in descending order by number of kills
    public int compare(Pawn p1, Pawn p2) {
        if (p1.getKills() != p2.getKills()) {
            return p2.getKills() - p1.getKills();
        }
        // if p1.getKills() == p2.getKills(). Sort by the string tag of the pieces in ascending order
        String stringOne = p1.getID().replaceAll("[^0-9]", "");//for example if string =A27 than 27
        int idFromStringOne = Integer.parseInt(stringOne);
        String stringTwo = p2.getID().replaceAll("[^0-9]", "");
        int idFromStringTwo = Integer.parseInt(stringTwo);

        if(idFromStringOne != idFromStringTwo) {
            return idFromStringOne - idFromStringTwo;
        }
        // Sort by the first winning team
        if (p1.getOwner() == winner)
            return 1;
        return 0;
    }
}


