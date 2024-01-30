import java.util.ArrayList;

public class King extends ConcretePiece {
    //Data
    private static final String type = "♔";


    //constructor
    public King(Player owner, String id, Position xy) {//use the abstract class
        super(owner, id, xy);
    }

    @Override
    public int getKills() {//to king hasn't kills
        return 0;
    }
        @Override
        public String getType () {
            return type;
        }
    }

