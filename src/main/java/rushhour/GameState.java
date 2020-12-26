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

    public GameState() {
        blocks = new ArrayList<Block>();
        moves = new ArrayList<GameState>();
    }

    public GameState(RushHour newGame, ArrayList<Block> oldBlocks, int newDepth) {
        moves = new ArrayList<GameState>();

        setGame(newGame);

        copyBlocks(oldBlocks);
        setDepth(newDepth);
        setHashKey();

        solved = checkSolved();

        game.addState(this);
    }

    //Getters and setters
    public long getHashKey() {
        return hashKey;
    }

    public void setHashKey() {
        hashKey = 0;
        int max = game.getWidth() - 1;

        if (game.getHeight() > game.getWidth()) {
            max = game.getHeight() - 1;
        }

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getDirection().equals("vertical")) {
                hashKey += blocks.get(i).getXyLocation().y * (long) Math.pow(max, i);
            } else if (blocks.get(i).getDirection().equals("horizontal")) {
                hashKey += blocks.get(i).getXyLocation().x * (long) Math.pow(max, i);
            }
        }
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int newDepth) {
        depth = newDepth;
    }

    public RushHour getGame() {
        return game;
    }

    public void setGame(RushHour newGame) {
        game = newGame;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> newBlocks) {
        blocks = newBlocks;
    }

    public ArrayList<GameState> getMoves() {
        return moves;
    }

    public boolean isSolved() {
        return solved;
    }


    public boolean checkSolved() {
        for (Block block : blocks) {
            if (block.getStart()) {
                if (block.getXyLocation().equals(game.getExitLocation())) {
                    if (depth < game.getSolutionDepth()) {
                        game.setSolutionDepth(depth);
                    }
                    return true;
                }
            }
        }

        checkAllMoves();
        return false;
    }

    public void copyBlocks(ArrayList<Block> oldBlocks) {
        blocks = new ArrayList<>();

        for (Block block : oldBlocks) {
            Point copyXy = block.getXyLocation();
            int copyId = block.getId();
            int copyLength = block.getLength();
            String copyDirection = block.getDirection();
            boolean copyStart = block.getStart();
            blocks.add(new Block(copyXy, copyId, copyLength, copyDirection, copyStart));
        }
    }

    public void checkAllMoves() {
        for (Block block : blocks) {
            checkMove(block, -1);
            checkMove(block, 1);
        }
    }

    public void checkMove(Block block, int direction) {
        boolean valid = true;

        if (depth + 1 > game.getSolutionDepth()) {
            valid = false;
        } else {
            for (Point point : block.getArea()) {
                if (!checkBounds(point) || !checkCollision(block, point)) {
                    valid = false;
                    break;
                }
            }
        }

        if (valid) {
            moveBlock(block, direction);
            if(verifyHash()) {
                moves.add(new GameState(game, blocks, depth + 1));
            }

            moveBlock(block, -direction);
        }
    }

    private boolean checkBounds(Point point) {
        if (point.x < 0 || point.y < 0 || point.x >= game.getWidth() || point.y >= game.getHeight()) {
            return false;
        }

        return true;
    }

    private boolean checkCollision(Block block, Point point) {
        for (Block otherBlock : blocks) {
            if (!otherBlock.equals(block)) {
                for (Point otherPoint : otherBlock.getArea()) {
                    if (otherPoint.equals(point)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void moveBlock(Block block, int direction) {
        for (Point point : block.getArea()) {
            if (block.getDirection().equals("vertical")) {
                point.setLocation(point.x, point.y + direction);
            } else if (block.getDirection().equals("horizontal")) {
                point.setLocation(point.x + direction, point.y);
            }
        }

        setHashKey();
    }

    private boolean verifyHash() {
        if (game.findState(hashKey)) {
            if (game.getState(hashKey).getDepth() > depth) {
                game.getState(hashKey).setDepth(depth + 1);
                moves.add(game.getState(hashKey));
            }

            return false;
        }

        return true;
    }

    public int[][] createDisplay() {
        int[][] display = new int[game.getHeight()][game.getWidth()];

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                display[j][i] = -1;
            }
        }

        for (Block block : blocks) {
            for (Point point : block.getArea()) {
                display[point.x][point.y] = block.getId();
            }
        }

        return display;
    }

    public String displayState() {
        int[][] displayArray = createDisplay();
        String display = "";

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                if (displayArray[j][i] < 0) {
                    display += "- ";
                } else {
                    display += displayArray[j][i] + " ";
                }
            }

            display += "\n";
        }

        return display;
    }
}
