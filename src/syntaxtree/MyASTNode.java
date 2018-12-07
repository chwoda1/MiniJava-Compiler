package syntaxtree;

public class MyASTNode {
  protected int line;
  protected int column;

  public MyASTNode(int line, int col) {
    this.line = line;
    this.column = col;
  }

  public int getLine() { return line; }
  public int getColumn() { return column; }

  public void setLine(int line)
  {
    this.line = line;
  }

  public void setColumn(int column)
  {
    this.column = column;
  }
}