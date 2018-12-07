# 1622Project3b
Chris Wood crw62@pitt.edu 

Sam Sedivy sls195@pitt.edu

Compiling MiniJava - Errors and IR

To compile navigate to the base folder and type 'make all'

To run, chmod the shell script and type ./run.sh [INPUTFILE]
  Windows uses ; and \\ for class path so it may not work for windows.  No problem we can come back to it later.

# TODO

IR Issue seems to be with the double function call on line 223 in BinaryTree.txt. The previous call is being stored in the symbol table as $t57 : null. As a result when you lookup the type within the visit(Call n) function, you get a null pointer exception. The type of that function is never stored. 
