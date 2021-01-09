package rushhour;

import java.awt.Point;
import java.util.ArrayList;
import java.lang.Math;

/**
* A single state of the game.
*/
public class GameState {
    private long hashKey;
    private int depth;
    private ArrayList<Block> blocks;
    private ArrayList<GameState> moves;
    private boolean solved;
    private RushHour game;

    /**
    Default constructor.
    */
    public GameState() {
        blocks = new ArrayList<Block>();
        moves = new ArrayList<GameState>();
    }

    /**
    Recursive constructor.
    @param newGame The game object
    @param oldBlocks The set of blocks from the previous state
    @param newDepth The depth of the new state (old state plus one)
    */
    public GameState(RushHour newGame, ArrayList<Block> oldBlocks, int newDepth) {
        moves = new ArrayList<GameState>();

        //Keeps track of game object
        setGame(newGame);

        //Set important values, copies block values
        copyBlocks(oldBlocks);
        setDepth(newDepth);
        setHashKey();

        //Adds state to queue to be evaluated
        game.addState(this);
    }

    //Getters and setters

    /**
    Gets the hashKey of the state.
    @return (long) hashKey
    */
    public long getHashKey() {
        return hashKey;
    }

    public void setHashKey() {
        //Initializes hashKey and max
        //Max represents the larger value between width and height of the board
        hashKey = 0;
        int max = game.getMax() - 1;

        //Creates hashKey based on the position of each block using the formula pos*max^index
        //Can be thought of as a number in the base of the value max with each digit
        //representing the position of a block, converted to base 10
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getDirection().equals("vertical")) {
                hashKey += blocks.get(i).getXyLocation().y * (long) Math.pow(max, i);
            } else if (blocks.get(i).getDirection().equals("horizontal")) {
                hashKey += blocks.get(i).getXyLocation().x * (long) Math.pow(max, i);
            }
        }
    }

    /**
    Gets the depth of the state.
    @return (int) depth
    */
    public int getDepth() {
        return depth;
    }

    /**
    Sets the depth of the game state.
    @param newDepth
    */
    public void setDepth(int newDepth) {
        depth = newDepth;
    }

    /**
    Gets the game object.
    @return (RushHour) game
    */
    public RushHour getGame() {
        return game;
    }

    /**
    Sets the game object.
    @param newGame
    */
    public void setGame(RushHour newGame) {
        game = newGame;
    }

    /**
    Gets the arraylist of blocks in the state.
    @return (ArrayList) blocks
    */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
    Sets the blocks in the game state.
    @param newBlocks
    */
    public void setBlocks(ArrayList<Block> newBlocks) {
        blocks = newBlocks;
    }

    /**
    Gets the arraylist of possible moves.
    @return (ArrayList) moves
    */
    public ArrayList<GameState> getMoves() {
        return moves;
    }

    /**
    Gets the solved variable.
    @return (boolean) solved
    */
    public boolean isSolved() {
        return solved;
    }

    /**
    Checks if the state is in a solved position and sets the solved variable.
    @return (boolean) solved
    */
    public boolean checkSolved() {
        //Finds primary block
        for (Block block : blocks) {
            if (block.getPrimary()) {
                //If the block xy is at the exit location
                if (block.getXyLocation().equals(game.getExitLocation())) {
                    //Set solved and set games solution depth
                    if (depth < game.getSolutionDepth()) {
                        game.setSolutionDepth(depth);
                    }
                    solved = true;
                    return true;
                }

                break;
            }
        }

        //If exit location not reached, set unsolved and check for valid moves
        solved = false;
        checkAllMoves();
        return false;
    }

    /**
    Copies the values of the old blocks without copying their reference.
    @param oldBlocks
    */
    public void copyBlocks(ArrayList<Block> oldBlocks) {
        blocks = new ArrayList<>();

        //Manually copies the value of each blocks variables
        //This is create a new set of blocks unaffected by changes to the first set
        for (Block block : oldBlocks) {
            Point copyXy = block.getXyLocation();
            String copyId = block.getId();
            int copyLength = block.getLength();
            String copyDirection = block.getDirection();
            boolean copyPrimary = block.getPrimary();
            //Add to blocks arraylist
            blocks.add(new Block(copyXy, copyId, copyLength, copyDirection, copyPrimary));
        }
    }

    /**
    Checks all possible moves in the game state.
    */
    public void checkAllMoves() {
        for (Block block : blocks) {
            //Checks the moves in either direction up to the length of the block
            for (int i = 1; i < block.getLength() + 1; i++) {
                checkMove(block, -i);
                checkMove(block, i);
            }
        }
    }

    private void checkMove(Block block, int direction) {
        boolean valid = true;

        //Move blocks first in order to verify validity
        moveBlock(block, direction);

        //For every point in the moving block, check validity
        for (Point point : block.getArea()) {
            if (!checkBounds(point) || !checkCollision(block, point)) {
                valid = false;
                break;
            }
        }

        //If valid, create a new game state and copy blocks values to new state
        if (valid && verifyHash()) {
            moves.add(new GameState(game, blocks, depth + 1));
        }

        //Return blocks to original location by reversing move
        moveBlock(block, -direction);
    }

    private boolean checkBounds(Point point) {
        //Check if point is within boundaries of board
        if (point.x < 0 || point.y < 0 || point.x >= game.getWidth() || point.y >= game.getHeight()) {
            return false;
        }

        return true;
    }

    private boolean checkCollision(Block block, Point point) {
        //Checks all other blocks
        for (Block otherBlock : blocks) {
            if (!otherBlock.equals(block)) {
                //Checks each point for collision
                for (Point otherPoint : otherBlock.getArea()) {
                    if (otherPoint.equals(point)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void moveBlock(Block block, int direction) {
        //Add direction to block xy value based on direction
        if (block.getDirection().equals("vertical")) {
            block.setXyLocation(new Point (block.getXyLocation().x, block.getXyLocation().y + direction));
        } else {
            block.setXyLocation(new Point (block.getXyLocation().x + direction, block.getXyLocation().y));
        }

        //Recreate the blocks area and hashKey, as they have changed
        block.createArea();
        setHashKey();
    }

    private boolean verifyHash() {
        //Check if the hashValue of the state is within the allStates hashMap
        if (game.findState(hashKey)) {
            //If it exists at a greater depth, change its depth to reflect it can be reached earlier
            if (game.getState(hashKey).getDepth() > depth) {
                game.getState(hashKey).setDepth(depth + 1);
                moves.add(game.getState(hashKey));
            }

            return false;
        }

        return true;
    }

    /**
    Returns the display of the game state in array form.
    @return (String[][]) display array
    */
    public String[][] createDisplay() {
        String[][] display = new String[game.getHeight()][game.getWidth()];

        //Set all base values to a "blank space" string
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                display[i][j] = "-";
            }
        }

        //For each point in the block areas, set that array value to the block id
        for (Block block : blocks) {
            for (Point point : block.getArea()) {
                display[point.y][point.x] = block.getId();
            }
        }

        return display;
    }

    /**
    Returns the display of the game state.
    @return (String) display
    */
    public String displayState() {
        String[][] displayArray = createDisplay();
        String display = "";

        //Print the array into a string with each layer split by newlines
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                display += displayArray[i][j] + " ";
            }

            display += "\n";
        }

        return display;
    }
}
