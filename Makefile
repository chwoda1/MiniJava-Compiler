JC = javac
CUP = java -jar src/lib/java-cup-11a.jar
JFLEX = java -jar src/lib/jflex-1.6.1.jar

ifeq ($(OS),Windows_NT)
    MV = cmd \/C move
    RM = cmd \/C del \/Q
    CP = src\\lib\\*;src/
    PS = \\
else
    MV = mv
    RM = rm -f
    CP = src/lib/java-cup-11a.jar:src/
    PS = /
endif

all:
	$(CUP) -parser miniParser miniParser.cup
	$(MV) miniParser.java src/
	$(MV) sym.java src/
	$(JFLEX) miniLexer.jflex
	$(MV) miniLexer.java src/
	$(JC) -cp $(CP) src/*.java

clean:
	$(RM) src$(PS)*.class
	$(RM) src$(PS)syntaxtree$(PS)*.class
	$(RM) src$(PS)visitor$(PS)*.class
	$(RM) src$(PS)symboltable$(PS)*.class
	$(RM) src$(PS)irgen$(PS)*.class
	$(RM) src$(PS)semanticanalysis$(PS)*.class
	$(RM) src$(PS)sym.java
	$(RM) src$(PS)miniLexer.java
	$(RM) src$(PS)miniParser.java
