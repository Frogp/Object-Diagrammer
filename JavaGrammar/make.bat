del Parse\*.class Lex\*.class modeler\*.class Figure\*.class
java -cp ..\java_cup_v10k java_cup.Main -parser Grm15 -symbols Sym Parse\java15.cup
move Grm15.java Parse\Grm15.java
REM copy Sym.java Parse\Sym.java
javac -classpath .\.;java_cup_v10k modeler/DOM.java
pause