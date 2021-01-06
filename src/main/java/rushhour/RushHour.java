package rushhour;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
* The game object.
*/
public class RushHour {
    private int width;
    private int height;
    private ArrayList<Block> blocks;
    private Point exitLocation;
    private GameState startingState;
    private HashMap<Long, GameState> allStates;
    private int solutionDepth;
    private ArrayList<GameState> stateQueue;
    private ArrayList<GameState> optimalPath;

    /**
    Default constructor.
    */
    public RushHour() {
        //Declare all arraylists
        blocks = new ArrayList<Block>();
        allStates = new HashMap<Long, GameState>();
        stateQueue = new ArrayList<GameState>();
        optimalPath = new ArrayList<GameState>();

        //Set solution depth
        solutionDepth = 100;

        //Set default exit location
        exitLocation = new Point(-1, -1);
    }

    /**
    Constructor with parser.
    @param parser The RushHourParser object
    */
    public RushHour(RushHourParser parser) {
        //Declare all arraylists
        blocks = new ArrayList<Block>();
        allStates = new HashMap<Long, GameState>();
        stateQueue = new ArrayList<GameState>();
        optimalPath = new ArrayList<GameState>();

        //Set important values
        solutionDepth = 100;
        width = parser.getWidth();
        height = parser.getHeight();

        //Iteratively add all blocks from parser
        Map currBlock = parser.nextBlock();
        while (currBlock != null) {
            addBlock(currBlock);
            currBlock = parser.nextBlock();
        }

        //Set exit location
        exitLocation = findExitLocation();

        //Begin creating the game tree
        makeGameTree();
    }

    private Point findExitLocation() {
        //Search all blocks for the primary block
        for (Block block : blocks) {
            if (block.getPrimary()) {
                //Calculate the exit location based on the block direction/length
                if (block.getDirection().equals("horizontal")) {
                    int exitX = width - block.getLength();
                    int exitY = block.getXyLocation().y;

                    return new Point(exitX, exitY);
                } else {
                    int exitX = block.getXyLocation().x;
                    int exitY = height - block.getLength();

                    return new Point(exitX, exitY);
                }
            }
        }

        //This should never be reached, only reached with no primary
        System.out.println("Exit error");
        System.exit(1);
    }

    private void addBlock(Map toAdd) {
        //Allocate space by creating new block object
        Block block = new Block();

        //Find the x and y position of the upper-left point of the block
        int x = Integer.parseInt(toAdd.get("x").toString());
        int y = Integer.parseInt(toAdd.get("y").toString());

        //Set all variables for the block
        block.setXyLocation(new Point (x, y));
        block.setId(toAdd.get("id").toString());
        block.setLength(Integer.parseInt(toAdd.get("length").toString()));
        block.setDirection(toAdd.get("direction").toString());
        block.setPrimary(toAdd.get("primary").toString());

        //Use location, direction, and length to create an array of points
        //Forming the blocks area
        block.createArea();

        //Add block to arraylist
        blocks.add(block);
    }

    /**
    Generates all gameStates and searches through them until it finds a solution.
    */
    public void makeGameTree() {
        //Record the initial state as the root of the game tree
        startingState = new GameState(this, blocks, 0);

        //Iterate through the gameState queue, in order of depth
        while (stateQueue.size() > 0) {
            //Add state to hashmap
            allStates.put(stateQueue.get(0).getHashKey(), stateQueue.get(0));
            //Check if the state is solved, if not then add moves to queue
            stateQueue.get(0).checkSolved();
            //If solved, exit the while loop
            if (stateQueue.get(0).isSolved()) {
                break;
            }

            //Remove the state from front of queue
            stateQueue.remove(0);
        }

        //If the queue exhasted all possible moves, then the puzzle cannot be solved
        if (stateQueue.size() == 0) {
            System.out.println("Puzzle has no solution");
            System.exit(1);
        }
    }


    //Getters and setters
    public int getWidth() {
        return width;
    }

    public void setWidth(int newWidth) {
        width = newWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
    }

    public int getMax() {
        if (height > width) {
            return height;
        } else {
            return width;
        }
        
    }

    public int getSolutionDepth() {
        return solutionDepth;
    }

    public void setSolutionDepth(int newSolutionDepth) {
        solutionDepth = newSolutionDepth;
    }

    public Point getExitLocation() {
        return exitLocation;
    }

    public void setExitLocation(Point newExit) {
        exitLocation = newExit;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> newBlocks) {
        blocks = newBlocks;
    }

    public Map getAllStates() {
        return allStates;
    }

    public boolean findState(long hashKey) {
        if (allStates.get(hashKey) != null) {
            return true;
        }

        return false;
    }

    public GameState getState(long hashKey) {
        return allStates.get(hashKey);
    }

    public void addState(GameState newState) {
        stateQueue.add(newState);
    }

    public void findPath() {
        hasSolution(startingState);
    }

    public ArrayList<GameState> getPath() {
        findPath();
        return optimalPath;
    }

    private boolean hasSolution(GameState state) {
        optimalPath.add(state);

        if (state.isSolved()) {
            return true;
        }

        for (GameState move : state.getMoves()) {
            if (hasSolution(move)) {
                return true;
            }
        }

        optimalPath.remove(state);

        return false;
    }

    public String displayPath() {
        findPath();

        String displayString = "";

        for (GameState state : optimalPath) {
            displayString += state.displayState() + "\n";
        }

        return displayString;
    }
}
