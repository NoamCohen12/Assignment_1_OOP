import java.util.Comparator;

class PieceLengthCompare implements Comparator<ConcretePiece> {

    @Override
    public int compare(ConcretePiece p1, ConcretePiece p2) {

        if (p1.getNumOfSteps() != p2.getNumOfSteps()) {
            return p1.getNumOfSteps() - p2.getNumOfSteps();
        }

        String stringOne = p1.getID().replaceAll("[^0-9]", "");//for example if string =A27 than 27
        int idFromStringOne = Integer.parseInt(stringOne);
        String stringTwo = p2.getID().replaceAll("[^0-9]", "");
        int idFromStringTwo = Integer.parseInt(stringTwo);

        if(idFromStringOne != idFromStringTwo){
            return idFromStringOne - idFromStringTwo;
        }
        return 0;
    }
}


