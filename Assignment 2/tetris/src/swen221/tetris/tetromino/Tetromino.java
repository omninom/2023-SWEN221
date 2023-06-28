package swen221.tetris.tetromino;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Color;

/**
 * every round the alive Tetormino perform exactly one of the following 5 options:
 * 1  rotate left
 * 2  rotate right
 * 3  move left
 * 4  move right
 * 5  move down
 * if after the option the piece has touch() then its content is copied
 * on the board, erasure of full lines happens, and a random new tetromino is created.
 * This encodes a variation of the so called "easy spin mode". By continuously rotating,
 * or moving left/right a piece, it never goes down.
 * */
public abstract class Tetromino {

  /**column coordinate of the center of the Tetromino*/
  private int x;

  /**row coordinate of the center of the Tetromino*/
  private int y;

  /** color of the Tetromino cells*/
  Color color;

  Tetromino(int x, int y, Color color){
    this.x= x;
    this.y= y;
    this.color= color;
  }
   /** A valid Tetromino has all its cells inside of the board limits*/
  public boolean valid() {
    return Board.rangeT().allMatch(i->
      Board.xOk(x(i)) && Board.yOk(y(i)));
  }

  /** column coordinate of the center of the Tetromino*/
  public int centerX() { return x; }

  /** row coordinate of the center of the Tetromino*/
  public int centerY() { return y; }

  /** color of the Tetromino cells*/
  public Color color() { return color; }

  /** moves the tetromino one step down*/
  public void moveDown() { y -= 1; }

  /** moves the tetromino one step up*/
  public void moveUp() { y += 1; }

  /** moves the tetromino one step left*/
  public void moveLeft() { x -= 1; }

  /** moves the tetromino one step right*/
  public void moveRight() { x += 1; }

  /** true if the tetromino is in contact with anything
   *  on its bottom, false otherwise
   *  */
  public boolean touch(Board b) {
    moveDown();
    boolean over= overlap(b);
    moveUp();
    return over;
  }

  /**
   * Every Tetromino covers 4 points, they can be retrieved
   * by x(0),y(0), x(1),y(1) and so on.
   * */
  public abstract int x(int i);

  /**
   * Every Tetromino covers 4 points, they can be retrieved
   * by x(0),y(0), x(1),y(1) and so on.
   * */
  public abstract int y(int i);

  /**Modify the state of the Tetromino so that it is now rotated 90' clockwise */
  public abstract void rotateRight();

  /**Modify the state of the Tetromino so that it is now rotated 90' counter-clockwise */
  public final void rotateLeft() {
    rotateRight();
    rotateRight();
    rotateRight();
  }

  /**true if the tetromino is penetrating any element
   * on the board, false otherwise.
   * The result is false also if part of the Tetromino
   * sits outside of the board
   * */
  public boolean overlap(Board b) {
    return Board.rangeT().anyMatch(i -> {       //We use anyMatch because we want to stop as soon as we find a match
      int absX = x(i);    //absolute coordinates
      int absY = y(i);
      return !Board.xOk(absX) || !Board.yOk(absY) || (b.read(absX, absY).isPresent() && b.read(absX, absY).get() != Color.EMPTY);   //if the cell is not empty, then there is an overlap.
      //We use isPresent() because we want to check if the cell is empty or not. if the tetromino is outside of the board, then there is an overlap.
    });
  }

  /**
   * modifies the content of the board by adding the cells of this Tetromino
   */
  public void copyOnBoard(Board b) {
    Board.rangeT()
      .forEach(i-> b.write(x(i), y(i), color()));
  }
}