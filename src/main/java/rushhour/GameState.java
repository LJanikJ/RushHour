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

        game.addState(this);
    }

    //Getters and setters
    public long getHashKey() {
        return hashKey;
    }

    public void setHashKey() {
        hashKey = 0;
        int max = game.getMax() - 1;

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
            if (block.getPrimary()) {
                if (block.getXyLocation().equals(game.getExitLocation())) {
                    if (depth < game.getSolutionDepth()) {
                        game.setSolutionDepth(depth);
                    }
                    solved = true;
                    return true;
                }
            }
        }

        solved = false;
        checkAllMoves();
        return false;
    }

    public void copyBlocks(ArrayList<Block> oldBlocks) {
        blocks = new ArrayList<>();

        for (Block block : oldBlocks) {
            Point copyXy = block.getXyLocation();
            String copyId = block.getId();
            int copyLength = block.getLength();
            String copyDirection = block.getDirection();
            boolean copyPrimary = block.getPrimary();
            blocks.add(new Block(copyXy, copyId, copyLength, copyDirection, copyPrimary));
        }
    }

    public void checkAllMoves() {
        for (Block block : blocks) {
            for (int i = 1; i < block.getLength() + 1; i++) {
                checkMove(block, -i);
                checkMove(block, i);
            }
        }
    }

    public void checkMove(Block block, int direction) {
        boolean valid = true;

        moveBlock(block, direction);

        for (Point point : block.getArea()) {
            if (!checkBounds(point) || !checkCollision(block, point)) {
                valid = false;
                break;
            }
        }

        if (valid && verifyHash()) {
            moves.add(new GameState(game, blocks, depth + 1));
        }

        moveBlock(block, -direction);
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
        if (block.getDirection().equals("vertical")) {
            block.setXyLocation(new Point (block.getXyLocation().x, block.getXyLocation().y + direction));
        } else {
            block.setXyLocation(new Point (block.getXyLocation().x + direction, block.getXyLocation().y));
        }

        block.createArea();
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

    public String[][] createDisplay() {
        String[][] display = new String[game.getHeight()][game.getWidth()];

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                display[i][j] = "-1";
            }
        }

        for (Block block : blocks) {
            for (Point point : block.getArea()) {
                display[point.y][point.x] = block.getId();
            }
        }

        return display;
    }

    public String displayState() {
        String[][] displayArray = createDisplay();
        String display = "";

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                if (displayArray[i][j].equals("-1")) {
                    display += "- ";
                } else {
                    display += displayArray[i][j] + " ";
                }
            }

            display += "\n";
        }

        return display;
    }
}
