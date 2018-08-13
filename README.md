# Tiny-C-Compiler-In-Java

This is a compiler made with java that translates a language called C-.

Lexical conventions allowed:

1. Keywords: else if int return void while
2. Special symbols: + - * / < <= > >= == != = ; , ( ) [ ] { } /* */
3. Comments are accounted for, either single line // or multi line /* */

Enclosed is a combined project over a semester of 4 projects:
1. Lexical analyzer: this reads in files using C- code and tokenizes into an array to be passed to the parser

2. Recursive descent parser: this take tokens from part 1 and parses based on the grammar definition of C-
   (The grammar had to be modified by eliminating left recursion and other reducing techniques applied from automata)
   
3. Semantic analyzer: additional checking not included in the grammar were applied 

4. Intermediate code generation: generate simple quadruple code that could be passed to the Sic/Xe assembler located in my other repository
