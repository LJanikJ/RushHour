package rushhour;

import java.awt.Point;
import java.util.ArrayList;

/**
* A single state of the game.
*/
public class GameState {
    private int hashKey;
    private int depth;
    private ArrayList<Block> blocks;
    private ArrayList<GameState> moves;
    private boolean solved;

    public GameState() {
        blocks = new ArrayList<Block>();
        moves = new ArrayList<GameState>();
    }

    public GameState(Block oldBlocks, int newDepth) {
        copyBlocks(oldBlocks);
        moves = new ArrayList<GameState>();
        depth = newDepth;

        setHashKey();

        isSolved();
    }

    //Getters and setters
    public int getHashKey() {
        return hashKey;
    }

    public void setHashKey() {
        //Generate hash value
    }

    public void setHashKey(int newHashKey) {
        hashKey = newHashKey;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int newDepth) {
        depth = newDepth;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> newBlocks) {
        blocks = newBlocks;
    }

    public ArrayList<GameState> getMoves() {
        return moves;
    }

    public boolean getSolved() {
        return solved;
    }

    public void setSolved(boolean newSolved) {
        solved = newSolved;
    }



    public isSolved() {
        if (/* SOLVED */) {
            solved = true;
        } else {
            solved = false;
            checkAllMoves();
        }
    }

    public void copyBlocks(ArrayList<Block> oldBlocks) {
        for (Block block : oldBlocks) {
            blocks.add(new Block(block.getXyLocation(), block.getID(), block.getLength(), block.getDirection()));
        }
    }

    public void findValidMoves() {
        //Find all valid moves and create game state
    }
}
