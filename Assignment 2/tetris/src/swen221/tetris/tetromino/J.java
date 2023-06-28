package swen221.tetris.tetromino;

import swen221.tetris.logic.Color;

public class J extends Tetromino {

    /**
     * J block is horizontal initially.
     */
    private boolean horizontal = true;

    /**
     * The J block constructor initializes the Tetromino object with the given position and color.
     *
     * @param x     x-coordinate of the center of the J block.
     * @param y     y-coordinate of the center of the J block.
     * @param color color of the J block.
     */
    public J(int x, int y, Color color) {
        super(x, y, color);
    }

    /**
     * @param i index of the cell.
     * @return x-coordinate of the ith cell.
     */
    @Override
    public int x(int i) {
        if (horizontal) {
            return centerX() + xOffset(i);    //if horizontal, we use xOffset to get the x coordinate.
        } else {
            return centerX() + yOffset(3 - i) * -1;     //if vertical, we use yOffset to get the x coordinate.
        }
    }

    /**
     * @param i index of the cell.
     * @return y-coordinate of the ith cell.
     */
    @Override
    public int y(int i) {
        if (horizontal) {
            return centerY() + yOffset(i);
        } else {
            return centerY() + xOffset(3 - i);
        }
    }


    /**
     * @return How far is the ith cell from the Tetromino center.
     */
    public int xOffset(int i) {
        if (i < 3) {                //if i < 3, we return i - 1 because the first three cells are at the left of the center.
            return i - 1;
        }
        return i - 2;        //if i >= 3, we return i - 2 because the last cell is at the right of the center.
    }

    /**
     * @return How far is the ith cell from the Tetromino center.
     */
    public int yOffset(int i) {
        if (i < 3) {
            return 0;       //if i < 3, we return 0 because the first three cells are at the same height of the center.
        }
        return -1;          //if i >= 3, we return -1 because the last cell is at the height below the center.
    }

    /**
     * Rotate the J block to the right.
     * The J block can either be horizontal or vertical, depending on the current state.
     */
    @Override
    public void rotateRight() {
        horizontal = !horizontal;
    }
}
