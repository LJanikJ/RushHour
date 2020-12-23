package rushhour;

import java.awt.Point;
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

    public RushHour() {
        blocks = new ArrayList<Block>();
        solutionDepth = 9999999;

        width = -1;
        height = -1;
        exitLocation = new Point(-1, -1);
    }

    public RushHour(RushHourParser parser) {
        blocks = new ArrayList<Block>();
        allStates = new ArrayList<GameState>();

        width = parser.getWidth();
        height = parser.getHeight();
        exitLocation = parser.getExitLocation();

        Map currBlock = parser.nextBlock();

        while (currRoom != null) {
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
        block.setStart(toAdd.get("start").toString());

        block.createArea();

        blocks.add(block);
    }

    public void makeGameTree() {
        startingState = new GameState(this, blocks, 0);
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

    public int getSolutionDepth() {
        return solutionDepth;
    }

    public void setSolutionDepth(int newSolutionDepth) {
        solutionDepth = newSolutionDepth;
    }

    public Point getExitLocation() {
        return exitLocation;
    }

    public void setExitLocation(int newExit) {
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
        allStates.put(newState.gethashKey(), newState);
    }

    public void findPath(GameState rootState) {
        
    }
}
