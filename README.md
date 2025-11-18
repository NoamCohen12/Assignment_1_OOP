# VikingsChess – Academic OOP Assignment
*Implementation of the Hnefatafl (Viking Chess) board game as required in the course assignment.*

## 1. Project Overview
This project implements the game **Hnefatafl (Viking Chess)** using advanced Object-Oriented Programming principles taught in the course.

The implementation includes:
- Full **game logic engine**
- **Graphical user interface (GUI)** written in Java Swing
- Proper **OOP hierarchy** (inheritance, interfaces, abstract classes)
- Full implementation of **movement rules, capture rules, and win conditions**
- **Statistical output** using *Comparators* (Part B requirement)
- Optional **Undo (Back)** feature (bonus part)

All logic is implemented exclusively in the classes written by the student.  
The provided files (`Main.java`, `GUI_for_chess_like_games.java`, `Piece.java`, `Player.java`, `PlayableLogic.java`) were **not modified**, as required.

## 2. Game Rules (Summary)
- **Board size**: 11×11  
- **Defenders (light/blue)**: 13 pawns + King  
- **Attackers (dark/red)**: 24 pawns  
- **Goal for defenders**: Move the King to a corner square  
- **Goal for attackers**: Capture the King by surrounding him  
- **Movement**: orthogonal only, no diagonal movement, no jumping over pieces  
- **Capturing**: sandwich rule, corners can act as capturing partners  
- **Victory**: King escapes or King is captured  

## 3. OOP Architecture (Part A)
### Class Hierarchy
Piece (interface) → ConcretePiece (abstract) → Pawn / King

### Logic Components
PlayableLogic (interface) → GameLogic

### Additional Structures
Position, ConcretePlayer, Comparators:
- PieceLengthCompare  
- PieceKillsCompare  
- PiecePositionCompare  
- PieceSquaresCompare  

## 4. Implemented Classes
Descriptions of ConcretePiece, Pawn, King, Position, ConcretePlayer, and GameLogic follow the assignment requirements.

## 5. GUI Preview
(Insert image in repository at: images/board_preview.png)

## 6. Statistical Output (Part B – Comparators)
### 1. Movement History  
### 2. Kill Count  
### 3. Distance Walked  
### 4. Square Usage  

(All sorted according to assignment rules.)

## 7. Undo Feature (Bonus)
Implemented using a stack of game state snapshots.

## 8. How to Run
Requirements: Java 17+.  
Compile and run:
```
javac Main.java
java Main
```

## 9. Notes
- All required interfaces were respected.
- No provided files were modified.
- All sorting uses Comparator implementations.

## 10. Author
Developer: **Noam**

## 11. Reference
Game rules and assignment instructions based on the official course document.
