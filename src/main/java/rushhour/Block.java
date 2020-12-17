package rushhour;

import java.awt.Point;

/**
* The game object.
*/
public class Block {
    private Point xyLocation;
    private int id;
    private int length;
    private String direction;

    public Block() {
        xyLocation = new Point(-1, -1);
        id = -1;
        length = -1;
        direction = "DEFAULT";
    }

    public Block(Point newXyLocation, int newId, int newLength, int newDirection) {
        xyLocation = newXyLocation;
        id = newId;
        length = newLength;
        direction = newDirection;
    }

    //Getters and setters
    public Point getXyLocation() {
        return xyLocation;
    }

    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int newLength) {
        length = newLength;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String newDirection) {
        direction = newDirection;
    }
}
