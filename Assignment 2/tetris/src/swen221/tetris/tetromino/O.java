package swen221.tetris.tetromino;

import swen221.tetris.logic.Color;

public class O extends Tetromino{
  public O(int x, int y, Color color) {
    super(x, y, color);
  }

  /**
   * @return How far is the ith cell from the Tetromino center.
   * The modulo operator % is used in the x method to alternate between returning the same x-coordinate for both cells
   * because the remainder of dividing an even number by 2 is 0
   * */
  @Override
  public int x(int i) {
	return centerX() + i % 2;
  }

  /**
   * @return How far is the ith cell from the Tetromino center.
   *On the other hand, the integer division operator / is used in the y method
   * because the y-coordinate of each cell should be incremented by 1 for every two cells.
   * */
  @Override
  public int y(int i) {
	return centerY() + i / 2;
  }

    /**a square does not rotate*/
  @Override
  public void rotateRight() {
  }

}
