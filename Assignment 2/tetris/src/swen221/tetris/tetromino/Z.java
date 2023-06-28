package swen221.tetris.tetromino;

import swen221.tetris.logic.Color;

/**
 * Z is either horizontal or vertical.
 * Z is simply the mirror of S. So we can use the same code with a different offset.
 */
public class Z extends S {
  public Z(int x, int y, Color color) {
    super(x, y, color);
  }

  /**
   * @return How far is the ith cell from the Tetromino center.
   * This is the same as S, but with a different offset. if (i < 2) we return 1, otherwise 0.
   * This is because the Z is the mirror of S. which means that the offset is the same, but the
    * direction is different.
   */
  @Override
  public int yOffset(int i) {
    if (i < 2) { return 1; }
    return 0;
  }
}

