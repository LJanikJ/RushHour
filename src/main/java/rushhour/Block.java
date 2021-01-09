package rushhour;

import java.awt.Point;
import java.util.ArrayList;

/**
* The game object.
*/
public class Block {
    private Point xyLocation;
    private String id;
    private int length;
    private String direction;
    private ArrayList<Point> area;
    private boolean primaryBlock;

    /**
    Default constructor.
    */
    public Block() {
        xyLocation = new Point(-1, -1);
        area = new ArrayList<Point>();
        id = "-1";
        length = -1;
        direction = "DEFAULT";
        primaryBlock = false;
    }

    /**
    Constructor with parameters.
    @param newXyLocation
    @param newId
    @param newLength
    @param newDirection
    @param primary
    */
    public Block(Point newXyLocation, String newId, int newLength, String newDirection, boolean primary) {
        xyLocation = newXyLocation;
        id = newId;
        length = newLength;
        direction = newDirection;
        primaryBlock = primary;

        createArea();
    }

    //Getters and setters

    /**
    Gets the x,y coordinate of the top-left point of the block.
    @return (Point) xyLocation
    */
    public Point getXyLocation() {
        return xyLocation;
    }

    /**
    Sets the x,y coordinate of the top-left point of the block and creates its area.
    @param newXyLocation
    */
    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;

        createArea();
    }

    /**
    Gets the id of the block.
    @return (String) id
    */
    public String getId() {
        return id;
    }

    /**
    Sets the id of the block.
    @param newId
    */
    public void setId(String newId) {
        id = newId;
    }

    /**
    Gets the length of the block.
    @return (int) length
    */
    public int getLength() {
        return length;
    }

    /**
    Sets the length of the block.
    @param newLength
    */
    public void setLength(int newLength) {
        length = newLength;
    }

    /**
    Gets the direction of the block.
    @return (String) direction
    */
    public String getDirection() {
        return direction;
    }

    /**
    Sets the direction of the block.
    @param newDirection
    */
    public void setDirection(String newDirection) {
        direction = newDirection;
    }

    /**
    Checks if the block is the primary block.
    @return (boolean) primaryBlock
    */
    public boolean getPrimary() {
        return primaryBlock;
    }

    /**
    Sets the primaryBlock variable to true or false.
    @param primaryValue
    */
    public void setPrimary(String primaryValue) {
        if (primaryValue.equals("true")) {
            primaryBlock = true;
        } else {
            primaryBlock = false;
        }
    }

    /**
    Gets the area of the block as an arraylist of points.
    @return (ArrayList) area
    */
    public ArrayList<Point> getArea() {
        return area;
    }

    /**
    Creates the area of the block based off of its position, length, and direction.
    */
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
