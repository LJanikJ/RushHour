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
    private ArrayList<GameState> allStates;

    public RushHour() {
        blocks = new ArrayList<Block>();

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

    public void setWidth(int newHeight) {
        height = newHeight;
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

    public void addState(GameState newState) {
        allStates.add(newState);
    }
}
