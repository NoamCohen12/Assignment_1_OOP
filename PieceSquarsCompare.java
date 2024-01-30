import java.util.Comparator;

class PieceSquarsCompare implements Comparator<ConcretePiece> {

    private Player winner;

    public PieceSquarsCompare(Player winner) {
        this.winner = winner;
    }

    public int compare(ConcretePiece p1, ConcretePiece p2) {
        if (p1.getDistanceTraveled() != p2.getDistanceTraveled()){
            return p2.getDistanceTraveled() - p1.getDistanceTraveled();
        }

        String stringOne = p1.getID().replaceAll("[^0-9]", "");//for example if string =A27 than 27
        int idFromStringOne = Integer.parseInt(stringOne);
        String stringTwo = p2.getID().replaceAll("[^0-9]", "");
        int idFromStringTwo = Integer.parseInt(stringTwo);

        if (idFromStringOne != idFromStringTwo) {
            return idFromStringOne - idFromStringTwo;
        }
        if (p1.getOwner() == winner)
            return 1;
        return 0;
    }
}