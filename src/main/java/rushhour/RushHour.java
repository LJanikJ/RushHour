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

    public RushHour() {
        width = -1;
        height = -1;
        blocks = new ArrayList<Block>();
        exitLocation = new Point(-1, -1);
    }

    public RushHour(RushHourParser parser) {
        
    }

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
}
