import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class GameLogic implements PlayableLogic {
    /**
     * variable that represents the position of the king for to know if the game finished
     */
    private Position posKing;
    /**
     * variable that represents the blue Player
     */
    private final ConcretePlayer player1;
    /**
     * variable that represents the red Player
     */
    private final ConcretePlayer player2;
    /**
     * variable that represents the board with pieces as in GUI
     */
    private final ConcretePiece[][] board;
    /**
     * variable that save all the positions by "board"
     */
    private final Position[][] boardOfPosition;
    /**
     * variable that indicates whether the game is over
     */
    private boolean gameFinish = false;
    /**
     * variable that indicates Who won this turn?
     */
    private boolean playerOneWin;
    /**
     * variable that indicate who player won
     */
    private Player winner;
    /**
     * class variable that help us to know whose turn it is?,
     * At first It is initialized true because is the turn of player 2
     */
    private boolean isSecondPlayerTurn = true;
    /**
     * all the pieces on the board in array that help us to print a statistic when the game done
     */
    private final ConcretePiece[] pieces = new ConcretePiece[37];

    public GameLogic() {
        this.player1 = new ConcretePlayer(true);
        this.player2 = new ConcretePlayer(false);
        this.board = new ConcretePiece[11][11];
        this.posKing = new Position(5, 5);
        this.boardOfPosition = new Position[11][11];
        startOfGame();
        initBoardOfPos(board);
    }
    //Check if it's legal step - piece moves on straight lines (up, down, left, right)
    // piece move only to free location
    // piece can't pass over another piece
    public boolean isLegalStep(Position a, Position b) {
        if (a.equals(b))
            return false;

        if (!a.isInside() || !b.isInside())
            return false;

        // check if the moves a piece, and not null
        if (getPieceAtPosition(a) == null)
            return false;

        // is the b cell is not empty, return false
        if (getPieceAtPosition(b) != null)
            return false;

        if (getPieceAtPosition(a) instanceof Pawn &&
                (b.getX() == 10 && b.getY() == 0 ||
                        b.getX() == 0 && b.getY() == 10
                        || b.getX() == 10 && b.getY() == 10
                        || b.getX() == 0 && b.getY() == 0)) {
            return false;
        }


        // check if he tries to move in diagonal
        if ((a.getY() != b.getY() && a.getX() != b.getX()))
            return false;

        for (int i = a.getX() + 1; i < b.getX(); i++) {
            if (board[b.getY()][i] != null)
                return false;
        }
        for (int i = a.getX() - 1; i > b.getX(); i--) {
            if (board[b.getY()][i] != null)
                return false;
        }


        for (int i = a.getY() + 1; i < b.getY(); i++) {
            if (board[i][b.getX()] != null)
                return false;
        }
        for (int i = a.getY() - 1; i > b.getY(); i--) {
            if (board[i][b.getX()] != null)
                return false;
        }
        return true;
    }

    /**
     * "eatOptions" is function that realizes eats according to the rules of the game
     * @param b
     */
    public void eatOptions(Position b) {

        ConcretePiece current = board[b.getY()][b.getX()];
        Piece up = getPieceAtPosition(new Position(b.getX(), b.getY() - 1));
        Piece down = getPieceAtPosition(new Position(b.getX(), b.getY() + 1));
        Piece right = getPieceAtPosition(new Position(b.getX() + 1, b.getY()));
        Piece left = getPieceAtPosition(new Position(b.getX() - 1, b.getY()));

        //eat to right side
        if (b.getX() + 1 < 11 && board[b.getY()][b.getX() + 1] != null &&
                (board[b.getY()][b.getX() + 1].getOwner() != current.getOwner()) &&
                (board[b.getY()][b.getX() + 1] instanceof Pawn)) {
            // check if the neighbour is not king and in the edge, if it is - eat
            if (b.getX() + 1 == 10) {
                if (!(board[b.getY()][b.getX() + 1] instanceof King)) {
                    ((Pawn) current).addKill();//add kill to current pawn
                    board[b.getY()][b.getX() + 1] = null;
                }
                //if neighbour right x+1 and x+2 are not null
            } else if (b.getX() + 2 < 11 && board[b.getY()][b.getX() + 2] != null &&
                    (board[b.getY()][b.getX() + 2].getOwner() == current.getOwner()) &&
                    (board[b.getY()][b.getX() + 2] instanceof Pawn)) {
                ((Pawn) current).addKill();//add kill to current pawn
                board[b.getY()][b.getX() + 1] = null;

                // eating with the green square when moving right
            } else if ((b.getX() + 2 == 10 && b.getY() == 10) || ((b.getX() + 2 == 10) && (b.getY() == 0))) {
                ((Pawn) current).addKill();//add kill to current pawn
                board[b.getY()][b.getX() + 1] = null;
            }
        }

        //eat to left side
        if (b.getX() - 1 >= 0 && board[b.getY()][b.getX() - 1] != null &&
                (board[b.getY()][b.getX() - 1].getOwner() != current.getOwner()) &&
                (board[b.getY()][b.getX() - 1] instanceof Pawn)) {
            // check if the neighbour is not king and in the edge, if it is - eat
            if (b.getX() - 1 == 0) {
                if (!(board[b.getY()][b.getX() - 1] instanceof King))
                    ((Pawn) current).addKill();//add kill to current pawn
                board[b.getY()][b.getX() - 1] = null;
                //if neighbour left x-1 and x-2 are not null
            } else if (b.getX() - 2 >= 0 && board[b.getY()][b.getX() - 2] != null &&
                    (board[b.getY()][b.getX() - 2].getOwner() == current.getOwner()) &&
                    (board[b.getY()][b.getX() - 2] instanceof Pawn)) {
                ((Pawn) current).addKill();//add kill to current pawn
                board[b.getY()][b.getX() - 1] = null;
                // eating with the green square when moving left
            } else if ((b.getX() - 2 == 0 && b.getY() == 10) || ((b.getX() - 2 == 0) && (b.getY() == 0))) {
                ((Pawn) current).addKill();//add kill to current pawn
                board[b.getY()][b.getX() - 1] = null;
            }
        }

        //eat to upside
        if (b.getY() - 1 >= 0 && board[b.getY() - 1][b.getX()] != null &&
                (board[b.getY() - 1][b.getX()].getOwner() != current.getOwner()) &&
                (board[b.getY() - 1][b.getX()] instanceof Pawn)) {
            // check if the neighbour is not king and in the edge, if it is - eat
            if (b.getY() - 1 == 0) {
                if (!(board[b.getY() - 1][b.getX()] instanceof King))
                    ((Pawn) current).addKill();//add kill to current pawn

                board[b.getY() - 1][b.getX()] = null;
                //if neighbour up y-1 and y-2 are not null
            } else if (b.getY() - 2 >= 0 && board[b.getY() - 2][b.getX()] != null &&
                    (board[b.getY() - 2][b.getX()].getOwner() == current.getOwner()) &&
                    (board[b.getY() - 2][b.getX()] instanceof Pawn)) {
                ((Pawn) current).addKill();//add kill to current pawn

                board[b.getY() - 1][b.getX()] = null;
                // eating with the green square when moving UP
            } else if ((b.getY() - 2 == 0 && b.getX() == 10) || ((b.getY() - 2 == 0) && (b.getX() == 0))) {
                ((Pawn) current).addKill();//add kill to current pawn

                board[b.getY() - 1][b.getX()] = null;
            }
        }

        //eat to downside
        if (b.getY() + 1 < 11 && board[b.getY() + 1][b.getX()] != null &&
                board[b.getY() + 1][b.getX()].getOwner() != current.getOwner() &&
                (board[b.getY() + 1][b.getX()] instanceof Pawn)) {
            // check if the neighbour is not king and in the edge, if it is - eat
            if (b.getY() + 1 == 10) {
                if (!(board[b.getY() + 1][b.getX()] instanceof King))
                    ((Pawn) current).addKill();//add kill to current pawn

                board[b.getY() + 1][b.getX()] = null;
                //if neighbour down y+1 and y+2 are not null
            } else if (b.getY() + 2 < 11 && board[b.getY() + 2][b.getX()] != null &&
                    (board[b.getY() + 2][b.getX()].getOwner() == current.getOwner()) &&
                    (board[b.getY() + 2][b.getX()] instanceof Pawn)) {
                ((Pawn) current).addKill();//add kill to current pawn

                board[b.getY() + 1][b.getX()] = null;
                // eating with the green square when moving UP
            } else if ((b.getY() + 2 == 10 && b.getX() == 10) || ((b.getY() + 2 == 10) && (b.getX() == 0))) {
                ((Pawn) current).addKill();//add kill to current pawn

                board[b.getY() + 1][b.getX()] = null;
            }
        }
    }

    @Override
    public boolean move(Position a, Position b) {//TODO add bad location in corner
        if (!isLegalStep(a, b)) {//input test
            return false;
        }
        ConcretePlayer currentPlayer = isSecondPlayerTurn() ? player2 : player1;

        // if the tries to move piece not in his color
        if (board[a.getY()][a.getX()].getOwner() != currentPlayer)
            return false;

        if (getPieceAtPosition(a) instanceof King) {
            this.posKing = new Position(b.getX(), b.getY());
        }
        ConcretePiece conPe = board[a.getY()][a.getX()];
        conPe.addSteps(b);

       // Position PosFrom = boardOfPosition[a.getY()][a.getX()];
        Position PosTo = boardOfPosition[b.getY()][b.getX()];

        //PosFrom.addWhoWasIn(conPe.getID());
        PosTo.addWhoWasIn(conPe.getID());


        board[b.getY()][b.getX()] = board[a.getY()][a.getX()];
        board[a.getY()][a.getX()] = null;
        isGameFinishHelp();

        // only pawn can eat
        if (board[b.getY()][b.getX()] instanceof Pawn) {
            eatOptions(b);
        }
        // switch the turns
        this.isSecondPlayerTurn = !this.isSecondPlayerTurn;
        return true;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        if (!position.isInside()) return null;
        return board[position.getY()][position.getX()];
    }

    @Override
    public Player getFirstPlayer() {
        return this.player1;
    }

    @Override
    public Player getSecondPlayer() {
        return this.player2;
    }

    @Override
    public boolean isGameFinished( ) {
       return gameFinish;
    }

     public void isGameFinishHelp() {
         //if the king found in these positions player1 win!
         Position rightUP = new Position(10, 0);
         Position leftUP = new Position(0, 0);
         Position rightDown = new Position(10, 10);
         Position leftDown = new Position(0, 10);
         if (posKing.equals(rightUP) || posKing.equals(leftUP) || posKing.equals(rightDown)
                 || posKing.equals(leftDown)) {
             winner = player1;//update the winner
             playerOneWin = true;//player1 win
             player1.addWins();//add win to player1
             gameFinish = true;//the game finish
             printStatistics();//Print the all statistics
         }
// check if the king is in the extreme ranks
         Piece upEnemy = getPieceAtPosition(new Position(posKing.getX(), posKing.getY() - 1));
         Piece downEnemy = getPieceAtPosition(new Position(posKing.getX(), posKing.getY() + 1));
         Piece leftEnemy = getPieceAtPosition(new Position(posKing.getX() - 1, posKing.getY()));
         Piece rightEnemy = getPieceAtPosition(new Position(posKing.getX() + 1, posKing.getY()));



         // check if the king is in the down column
         if (posKing.getY() == 10 &&
                 upEnemy != null && upEnemy.getOwner() == player2 &&
                 rightEnemy != null && rightEnemy.getOwner() == player2 &&
                 leftEnemy != null && leftEnemy.getOwner() == player2) {
             winner = player2;//update the winner
             playerOneWin = false;//player2 win
             player2.addWins();//add win to player2
             gameFinish = true;//the game finish
             printStatistics();//Print the all statistics
         }
         // check if the king is in the right row
         if (posKing.getX() == 10 &&
                 leftEnemy != null && leftEnemy.getOwner() == player2 &&
                 downEnemy != null && downEnemy.getOwner() == player2 &&
                 upEnemy != null && upEnemy.getOwner() == player2) {
             winner = player2;//update the winner
             playerOneWin = false;//player2 win
             player2.addWins();//add win to player2
             gameFinish = true;//the game finish
             printStatistics();//Print the all statistics
         }
         // check if the king is in the up column
         if (posKing.getY() == 0 &&
                 leftEnemy != null && leftEnemy.getOwner() == player2 &&
                 downEnemy != null && downEnemy.getOwner() == player2 &&
                 rightEnemy != null && rightEnemy.getOwner() == player2) {
             winner = player2;//update the winner
             playerOneWin = false;//player2 win
             player2.addWins();//add win to player2
             gameFinish = true;//the game finish
             printStatistics();//Print the all statistics
         }
         // check if the king is in the left row
         if (posKing.getX() == 0 &&
                 rightEnemy != null && rightEnemy.getOwner() == player2 &&
                 downEnemy != null && downEnemy.getOwner() == player2 &&
                 upEnemy != null && upEnemy.getOwner() == player2) {
             winner = player2;//update the winner
             playerOneWin = false;//player2 win
             player2.addWins();//add win to player2
             gameFinish = true;//the game finish
             printStatistics();//Print the all statistics
         }
         //in the case the king in center and the enemy's around
         Position upEnemyE = new Position(posKing.getX(), posKing.getY() - 1);
         Position downEnemyE = new Position(posKing.getX(), posKing.getY() + 1);
         Position leftEnemyE = new Position(posKing.getX() - 1, posKing.getY());
         Position rightEnemyE = new Position(posKing.getX() + 1, posKing.getY());

         if ((getPieceAtPosition(upEnemyE) != null && !getPieceAtPosition(upEnemyE).getOwner().isPlayerOne()) &&
                 (getPieceAtPosition(downEnemyE) != null && !getPieceAtPosition(downEnemyE).getOwner().isPlayerOne()) &&
                 (getPieceAtPosition(leftEnemyE) != null && !getPieceAtPosition(leftEnemyE).getOwner().isPlayerOne()) &&
                 (getPieceAtPosition(rightEnemyE) != null && !getPieceAtPosition(rightEnemyE).getOwner().isPlayerOne())) {
             winner = player2;//update the winner
             playerOneWin = false;//player2 win
             player2.addWins();//add win to player2
             gameFinish = true;//the game finish
             printStatistics();//Print the all statistics
         }
         //in the case that all the red pieces eaten
         else {
             boolean allRedPawnsEaten = true;//flag for check if there are any player2 on the board
             for (int i = 0; i < 11; i++) {
                 for (int j = 0; j < 11; j++) {
                     if (this.board[j][i] != null) {
                         if (this.board[j][i].getOwner() == player2) {
                             allRedPawnsEaten = false;
                         }
                     }
                 }
             }
             if (allRedPawnsEaten) {
                 winner = player1;//update the winner
                 playerOneWin = true;//player1 win
                 player1.addWins();//add win to player1
                 gameFinish = true;//the game finish
                 printStatistics();//Print the all statistics
             }
         }

     }

    @Override
    public boolean isSecondPlayerTurn() {
        return this.isSecondPlayerTurn;
    }

    @Override
    public void reset() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                this.board[j][i] = null;//clear all the board
            }
        }
        posKing = new Position(5, 5);//returning the king to his original place
        this.isSecondPlayerTurn = true;//returning the turn to the player2 like in beginning

        startOfGame();//putting all the tools on the board again
    }

    @Override
    public void undoLastMove() {//Bonus we didn't have time to do
        //TODO-----------------------------------------

    }

    @Override
    public int getBoardSize() {//11X11
        return 11;
    }

    public void startOfGame() {
        //king position
        board[5][5] = new King(player1, "K7", new Position(5, 5));
        //player 1 pawns position
        board[3][5] = new Pawn(player1, "D1", new Position(5, 3));
        board[4][4] = new Pawn(player1, "D2", new Position(4, 4));
        board[4][5] = new Pawn(player1, "D3", new Position(5, 4));
        board[4][6] = new Pawn(player1, "D4", new Position(6, 4));
        board[5][3] = new Pawn(player1, "D5", new Position(3, 5));
        board[5][4] = new Pawn(player1, "D6", new Position(4, 5));
        board[5][6] = new Pawn(player1, "D8", new Position(6, 5));
        board[5][7] = new Pawn(player1, "D9", new Position(7, 5));
        board[6][4] = new Pawn(player1, "D10", new Position(4, 6));
        board[6][5] = new Pawn(player1, "D11", new Position(5, 6));
        board[6][6] = new Pawn(player1, "D12", new Position(6, 6));
        board[7][5] = new Pawn(player1, "D13", new Position(5, 7));

        //player 2 pawns position
//up
        board[0][3] = new Pawn(player2, "A1", new Position(3, 0));
        board[0][4] = new Pawn(player2, "A2", new Position(4, 0));
        board[0][5] = new Pawn(player2, "A3", new Position(5, 0));
        board[0][6] = new Pawn(player2, "A4", new Position(6, 0));
        board[0][7] = new Pawn(player2, "A5", new Position(7, 0));
        board[1][5] = new Pawn(player2, "A6", new Position(5, 1));
//left
        board[3][0] = new Pawn(player2, "A7", new Position(0, 3));
        board[4][0] = new Pawn(player2, "A9", new Position(0, 4));
        board[5][0] = new Pawn(player2, "A11", new Position(0, 5));
        board[6][0] = new Pawn(player2, "A15", new Position(0, 6));
        board[7][0] = new Pawn(player2, "A17", new Position(0, 7));
        board[5][1] = new Pawn(player2, "A12", new Position(1, 5));
//right
        board[3][10] = new Pawn(player2, "A8", new Position(10, 3));
        board[4][10] = new Pawn(player2, "A10", new Position(10, 4));
        board[5][10] = new Pawn(player2, "A14", new Position(10, 5));
        board[6][10] = new Pawn(player2, "A16", new Position(10, 6));
        board[7][10] = new Pawn(player2, "A18", new Position(10, 7));
        board[5][9] = new Pawn(player2, "A13", new Position(9, 5));
//down
        board[10][3] = new Pawn(player2, "A20", new Position(3, 10));
        board[10][4] = new Pawn(player2, "A21", new Position(4, 10));
        board[10][5] = new Pawn(player2, "A22", new Position(5, 10));
        board[10][6] = new Pawn(player2, "A23", new Position(6, 10));
        board[10][7] = new Pawn(player2, "A24", new Position(7, 10));
        board[9][5] = new Pawn(player2, "A19", new Position(5, 9));

//Initializing the pieces array
        //king
        pieces[0] = board[5][5];
        //defence
        pieces[1] = board[3][5];
        pieces[2] = board[4][4];
        pieces[3] = board[4][5];
        pieces[4] = board[4][6];
        pieces[5] = board[5][3];
        pieces[6] = board[5][4];
        pieces[7] = board[5][6];
        pieces[8] = board[5][7];
        pieces[9] = board[6][4];
        pieces[10] = board[6][5];
        pieces[11] = board[6][6];
        pieces[12] = board[7][5];
//attack
        //up
        pieces[13] = board[0][3];
        pieces[14] = board[0][4];
        pieces[15] = board[0][5];
        pieces[16] = board[0][6];
        pieces[17] = board[0][7];
        pieces[18] = board[1][5];
        //left
        pieces[19] = board[3][0];
        pieces[20] = board[4][0];
        pieces[21] = board[5][0];
        pieces[22] = board[6][0];
        pieces[23] = board[7][0];
        pieces[24] = board[5][1];
//right

        pieces[25] = board[3][10];
        pieces[26] = board[4][10];
        pieces[27] = board[5][10];
        pieces[28] = board[6][10];
        pieces[29] = board[7][10];
        pieces[30] = board[5][9];
//down

        pieces[31] = board[10][3];
        pieces[32] = board[10][4];
        pieces[33] = board[10][5];
        pieces[34] = board[10][6];
        pieces[35] = board[10][7];
        pieces[36] = board[9][5];


    }

    public void printKillData(ConcretePiece[] pieces) {//prints the statistics associated with the kill
        ArrayList<Pawn> list = new ArrayList<>();
        for (int i = 1; i < pieces.length; i++) {//without the king in picese[0]
            if ((pieces[i]).getKills() > 0) {
                list.add(((Pawn) pieces[i]));
            }
        }
        list.sort(new PieceKillsCompare(winner));
        for (Pawn current : list) {
            System.out.println(current.getID() + ": " + current.getKills() + " kills");
        }
    }

    public void printLengthData(ConcretePiece[] pieces) {//prints the statistics associated with all the steps of piece
        ArrayList<ConcretePiece> listPlayer1 = new ArrayList<>();
        ArrayList<ConcretePiece> listPlayer2 = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            if (pieces[i].getNumOfSteps() > 1) {
                listPlayer1.add(pieces[i]);
            }
        }

        listPlayer1.sort(new PieceLengthCompare());//piece in array sorted by length steps in defended set

        for (int i = 13; i < 37; i++) {
            if (pieces[i].getNumOfSteps() > 1) {
                listPlayer2.add(pieces[i]);
            }
        }
        listPlayer2.sort(new PieceLengthCompare());//piece in array sorted by length steps in attack set

        if (playerOneWin) {
            for (int i = 0; i < listPlayer1.size(); i++) {
                ConcretePiece curent = listPlayer1.get(i);
                System.out.println(curent.getID() + ": " + Arrays.toString(curent.getStepsArr()));
            }
            for (int j = 0; j < listPlayer2.size(); j++) {
                ConcretePiece curent = listPlayer2.get(j);
                System.out.println(curent.getID() + ": " + Arrays.toString(curent.getStepsArr()));
            }
        } else {
            for (int j = 0; j < listPlayer2.size(); j++) {
                ConcretePiece curent = listPlayer2.get(j);
                System.out.println(curent.getID() + ": " + Arrays.toString(curent.getStepsArr()));
            }
            for (int i = 0; i < listPlayer1.size(); i++) {
                ConcretePiece curent = listPlayer1.get(i);
                System.out.println(curent.getID() + ": " + Arrays.toString(curent.getStepsArr()));
            }

        }
    }

    public void printDestData(ConcretePiece[] pieces) {//prints the statistics associated with the length of piece steps
        for (int i = 0; i < pieces.length; i++) {
            ConcretePiece current = pieces[i];
            for (int j = 0; j < current.steps.size() - 1; j++) {
                Position pos = current.steps.get(j);
                Position posCurernt = current.steps.get(j + 1);

                if (pos.getX() == posCurernt.getX()) {
                    int distance = Math.abs(pos.getY() - posCurernt.getY());
                    current.addDest(distance);
                } else if (pos.getY() == posCurernt.getY()) {
                    int distance = Math.abs(pos.getX() - posCurernt.getX());
                    current.addDest(distance);
                }
            }
        }
        ArrayList<ConcretePiece> listPlayers = new ArrayList<>();
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getDistanceTraveled() > 0) {
                listPlayers.add(pieces[i]);
            }
        }
        listPlayers.sort(new PieceSquarsCompare(winner));
        for (int i = 0; i < listPlayers.size(); i++) {
            ConcretePiece curent = listPlayers.get(i);
            System.out.println(curent.getID() + ": " + curent.getDistanceTraveled() + " squares");
        }

    }

    public void initBoardOfPos(ConcretePiece[][] board) {//initialization matrix of position
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                boardOfPosition[j][i] = new Position(i, j);
            }
        }
        for (int i = 0; i < 11; i++) {//add to positions in beaning the pieceID of beginning that stand on them
            for (int j = 0; j < 11; j++) {
                if (board[j][i] != null) {//there is any piece
                    Position temp = boardOfPosition[j][i];
                    temp.addWhoWasIn(board[j][i].getID());
                }
            }
        }
    }

    public void printPositionData(Position[][] pos) {//prints the statistics associated with the num steps on positions
        ArrayList<Position> posComp = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
               // if (pos[j][i] != null) {
                    posComp.add(pos[j][i]);
               // }
            }
        }
        posComp.sort(new PiecePositionCompare());
        for (int i = 0; i < posComp.size(); i++) {
            if (posComp.get(i).getWhoWasIn() > 1) {
                Position current = posComp.get(i);
                System.out.println(current.toString() + current.getWhoWasIn() + " pieces");
            }
        }
    }

    public void printStatistics() {//function responsible for print all statistics
        printLengthData(pieces);
        System.out.println("***************************************************************************");
        printKillData(pieces);
        System.out.println("***************************************************************************");
        printDestData(pieces);
        System.out.println("***************************************************************************");
        printPositionData(boardOfPosition);
        System.out.println("***************************************************************************");

    }

}






