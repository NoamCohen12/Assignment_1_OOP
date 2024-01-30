import java.util.Comparator;

import static java.util.Collections.replaceAll;

class PieceKillsCompare implements Comparator<Pawn> {
     private Player winner;
     public PieceKillsCompare(Player winner) {
         this.winner = winner;
     }
    public int compare(Pawn p1, Pawn p2) {
        if (p1.getKills() < p2.getKills())
            return 1;
        else if (p1.getKills() > p2.getKills())
            return -1;
        String stringOne = p1.getID().replaceAll("[^0-9]", "");//for example if string =A27 than 27
        int idFromStringOne = Integer.parseInt(stringOne);
        String stringTwo = p2.getID().replaceAll("[^0-9]", "");
        int idFromStringTwo = Integer.parseInt(stringTwo);

        if(idFromStringOne>idFromStringTwo){
            return 1;
        } else if (idFromStringTwo>idFromStringOne) {
            return -1;
        }
        if (p1.getOwner() == winner)
            return 1; // not true maybe
        return 0;
    }
}
