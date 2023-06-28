package swen221.tetris.tetromino;

import swen221.tetris.logic.Color;

public class S extends Tetromino{
  /**
   * a S is either horizontal or vertical.
   * It is horizontal when looks like the 's' letter.
   * */
  boolean horizontal= true;

  /**
   * x coordinate: obtained by summing the center with an offset.
   * */
  @Override
  public int x(int i) {
    if (horizontal) { return centerX() + xOffset(i); }      //if horizontal, we use xOffset to get the x coordinate.
    return centerX() + yOffset(i) * -1;                     //if vertical, we use yOffset to get the x coordinate.
  }

  /**
   * @return How far is the ith cell from the Tetromino center.
   * */
  public int xOffset(int i) {
    if (i < 2) { return i - 1; } //if i < 2, we return i - 1 because the first two cells are at the left of the center.
    return i - 2;                       //if i >= 2, we return i - 2 because the last two cells are at the right of the center.
  }

  /**
   * y coordinate: obtained by summing the center with an offset.
   * */
  @Override
  public int y(int i) {
    if (horizontal) { return centerY() + yOffset(i); }
    return centerY() + xOffset(i);
  }

  /**
   * @return How far is the ith cell from the Tetromino center.
   * */
  public int yOffset(int i) {
    if (i < 2) { return 0; }
    return 1;
  }

  public S(int x, int y, Color color) {
    super(x, y, color);
  }

  /**iterate between the two possibilities*/
  @Override
  public void rotateRight() {
    horizontal =! horizontal;
  }
}
