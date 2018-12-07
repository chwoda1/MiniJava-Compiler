package syntaxtree;
import java.util.Vector;

public class StatementList extends MyASTNode{
   private Vector list;

   public StatementList() {
     super(0,0);
      list = new Vector();
   }

   public StatementList(int line, int col) {
      super(line, col);
      list = new Vector();
   }

   public void addElement(Statement n) {
      list.addElement(n);
   }

   public void addFront(Statement n) {
      list.add(0, n);
   }

   public Statement elementAt(int i)  {
      return (Statement)list.elementAt(i);
   }

   public int size() {
      return list.size();
   }

}