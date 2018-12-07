package syntaxtree;

import java.util.Vector;

public class FormalList extends MyASTNode{
   private Vector list;

   public FormalList() {
     super(0,0);
      list = new Vector();
   }

   public FormalList(int line, int col) {
      super(line, col);
      list = new Vector();
   }

   public void addElement(Formal n) {
      list.addElement(n);
   }

   public void addFront(Formal n) {
      list.add(0, n);
   }

   public Formal elementAt(int i)  {
      return (Formal)list.elementAt(i);
   }

   public int size() {
      return list.size();
   }
}