import java.util.ArrayList;

public class King extends ConcretePiece {
    //Data
    private static final String type = "♔";


    //constructor
    public King(Player owner, String id, Position xy) {
        super(owner, id, xy);
    }

    @Override
    public int getKills() {
        return 0;
    }
        @Override
        public String getType () {
            return type;
        }
    }

