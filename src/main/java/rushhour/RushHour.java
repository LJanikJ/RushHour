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

    public RushHour() {
        blocks = new ArrayList<Block>();
        allStates = new HashMap<Long, GameState>();
        stateQueue = new ArrayList<GameState>();

        solutionDepth = 10000;

        width = -1;
        height = -1;
        exitLocation = new Point(-1, -1);
    }

    public RushHour(RushHourParser parser) {
        blocks = new ArrayList<Block>();
        allStates = new HashMap<Long, GameState>();
        stateQueue = new ArrayList<GameState>();

        solutionDepth = 10000;
        width = parser.getWidth();
        height = parser.getHeight();
        exitLocation = parser.getExitLocation();

        Map currBlock = parser.nextBlock();

        while (currBlock != null) {
            addBlock(currBlock);
            currBlock = parser.nextBlock();
        }

        makeGameTree();
    }

    private void addBlock(Map toAdd) {
        Block block = new Block();

        int x = Integer.parseInt(toAdd.get("x").toString());
        int y = Integer.parseInt(toAdd.get("y").toString());

        block.setXyLocation(new Point (x, y));
        block.setId(Integer.parseInt(toAdd.get("id").toString()));
        block.setLength(Integer.parseInt(toAdd.get("length").toString()));
        block.setDirection(toAdd.get("direction").toString());
        block.setPrimary(toAdd.get("primary").toString());

        block.createArea();

        blocks.add(block);
    }

    public void makeGameTree() {
        startingState = new GameState(this, blocks, 0);

        while (stateQueue.size() > 0) {
            allStates.put(stateQueue.get(0).getHashKey(), stateQueue.get(0));
            stateQueue.get(0).checkSolved();
            if (stateQueue.get(0).isSolved()) {
                break;
            }

            stateQueue.remove(0);
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

    public void findPath(GameState rootState) {
        //Find the optimal path to the solution
    }

    public String displayRoot() {
        System.out.println(allStates.size());
        System.out.println(getSolutionDepth());
        //System.out.println(stateQueue.get(2).displayState());
        return startingState.displayState();
    }
}
