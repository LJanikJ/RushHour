package rushhour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
* The game parser.
*/
public class RushHourParser {
    private ArrayList<Map<String, String>> blocks = new ArrayList<>();
    private int width;
    private int height;

    private Iterator<Map<String, String>> blockIterator;

    /**
    Default constructor.
    */
    public RushHourParser() {

    }

    /**
    Constructor from filename.
    @param filename
    */
    public RushHourParser(String filename) {
        parse(filename);
        blockIterator = blocks.iterator();
    }

    //Getters and setters

    /**
    Returns the next block in the blocks arraylist.
    @return (Map) block hashMap
    */
    public Map nextBlock() {
        if (blockIterator.hasNext()) {
            return blockIterator.next();
        } else {
            return null;
        }
    }

    /**
    Gets the width variable.
    @return (int) width
    */
    public int getWidth() {
        return width;
    }

    /**
    Sets the width variable.
    @param newWidth
    */
    public void setWidth(int newWidth) {
        width = newWidth;
    }

    /**
    Gets the height variable.
    @return (int) height
    */
    public int getHeight() {
        return height;
    }

    /**
    Sets the height variable.
    @param newHeight
    */
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    /**
    Gets the blocks arraylist.
    @return (ArrayList) blocks
    */
    public ArrayList<Map<String, String>> getBlocks() {
        return blocks;
    }

    private void parse(String filename) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject puzzleJSON = (JSONObject) obj;
            readFile(puzzleJSON);
        } catch (FileNotFoundException e) {
            throw new NullPointerException();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new NullPointerException();
        }
    }

    private void readFile(JSONObject puzzleJSON) {
        setWidth(Integer.parseInt(puzzleJSON.get("width").toString()));
        setHeight(Integer.parseInt(puzzleJSON.get("height").toString()));

        JSONArray blocksJSONArray = (JSONArray) puzzleJSON.get("block");

        for(int i = 0; i < blocksJSONArray.size(); i++) {
            blocks.add(singleBlock((JSONObject) blocksJSONArray.get(i)));
        }
    }

    private Map<String, String> singleBlock(JSONObject blockJSON) {
        HashMap<String, String> block = new HashMap<>();

        block.put("id", blockJSON.get("id").toString());
        block.put("x", blockJSON.get("x").toString());
        block.put("y", blockJSON.get("y").toString());
        block.put("length", blockJSON.get("length").toString());
        block.put("direction", blockJSON.get("direction").toString());
        block.put("primary", blockJSON.get("primary").toString());

        return block;
    }
}
