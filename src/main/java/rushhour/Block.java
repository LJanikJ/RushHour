package rushhour;

import java.awt.Point;
import java.util.ArrayList;

/**
* The game object.
*/
public class Block {
    private Point xyLocation;
    private int id;
    private int length;
    private String direction;
    private ArrayList<Point> area;
    private boolean primaryBlock;

    public Block() {
        xyLocation = new Point(-1, -1);
        area = new ArrayList<Point>();
        id = -1;
        length = -1;
        direction = "DEFAULT";
        primaryBlock = false;
    }

    public Block(Point newXyLocation, int newId, int newLength, String newDirection, boolean primary) {
        xyLocation = newXyLocation;
        id = newId;
        length = newLength;
        direction = newDirection;
        primaryBlock = primary;

        createArea();
    }

    //Getters and setters
    public Point getXyLocation() {
        return xyLocation;
    }

    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;

        createArea();
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

    public boolean getPrimary() {
        return primaryBlock;
    }

    public void setPrimary(String primaryValue) {
        if (primaryValue.equals("true")) {
            primaryBlock = true;
        } else {
            primaryBlock = false;
        }
    }

    public ArrayList<Point> getArea() {
        return area;
    }

    public void createArea() {
        area = new ArrayList<>();

        if (direction.equals("vertical")) {
            for (int i = 0; i < length; i++) {
                area.add(new Point(xyLocation.x, xyLocation.y + i));
            }
        } else if (direction.equals("horizontal")) {
            for (int i = 0; i < length; i++) {
                area.add(new Point(xyLocation.x + i, xyLocation.y));
            }
        }
    }
}
