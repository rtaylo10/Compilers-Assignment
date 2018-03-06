JAVAC=javac
JFLEX=jflex
#JFLEX=/home/fsong/jflex/bin/jflex

all: Scanner.class

Scanner.class: Scanner.java Lexer.java Token.java

%.class: %.java
	$(JAVAC) $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

clean:
	rm -f Lexer.java *.class *~
