import java.io.*;
import java.util.*;
import java.lang.*;

public class compilerClass 
{
    public static void main(String[] args) throws IOException {
        
        if (args.length == 0) {
            System.out.println("ERROR: no arguments.");
            return;
        }
        
        File f = new File(args[0]);
        
        lexicalAnalyzer pass1 = new lexicalAnalyzer();

        Object[] lexResults = lexicalAnalyzer.runScan(f);
        
        tokenObject[][] parseTokens = (tokenObject[][])lexResults[0];
        
        parser parseMethod = new parser();
        
        Boolean parseResults = parser.parser(parseTokens);
    }
}

class lexicalAnalyzer {
    lexicalAnalyzer() 
    {
    }

    public static Object[] runScan(File f) throws IOException {
        File input = f;
        Scanner counter = new Scanner(input);
        Scanner reader = new Scanner(input);
        int lines = 0;
        while (counter.hasNextLine()) {
            ++lines;
            counter.nextLine();
        }
        counter.close();
        lexStateTable stateTable = new lexStateTable();
        int currentLine = 0;
        tokenObject[][] tokens = new tokenObject[lines][20];
        for (int i = 0; i < tokens.length; ++i) {
            for (int j = 0; j < tokens[i].length; ++j) {
                tokens[i][j] = new tokenObject();
            }
        }
        idTable identifiers = new idTable();
        Object[] returns = new Object[2];
        while (currentLine < lines) {
            String fullLine = reader.nextLine();
            System.out.println("INPUT: " + fullLine);
            if (fullLine != null && !fullLine.trim().isEmpty()) 
            {
                String[] lineArray = fullLine.trim().split("\\s+");
                returns = lexStateTable.parseLine(lineArray, currentLine, tokens, identifiers);
            }
            ++currentLine;
        }
        returns[0] = tokens;
        returns[1] = identifiers;
        identifiers.printTable();
        return returns;
    }
}


class lexStateTable {
    public static int scope = 0;
    public static boolean isSingleComment = false;
    public static boolean isMultiComment = false;
    public static int commentDepth = 0;
    public static int tempCurrentLine = -1;
    public static boolean isTokenized = false;
    public static int currentChar = 0;
    public static int readChar = 0;
    public static int currentToken = 0;
    public static char[] inputChars;
    public static boolean endOfLine;

    lexStateTable() {
    }

    public static Object[] parseLine(String[] lineArray, int currentLine, tokenObject[][] tokens, idTable identifiers) 
    {
        for (int i = 0; i < lineArray.length; ++i) 
        {
            if (i == lineArray.length - 1) 
            {
                endOfLine = true;
            }
            inputChars = lineArray[i].toCharArray();
            lexStateTable.start(inputChars, currentLine, tokens, identifiers, endOfLine);
        }
        endOfLine = false;
        Object[] returns = new Object[]{tokens, identifiers};
        return returns;
    }

    public static void start(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers, boolean endOfLine) {
        isTokenized = false;
        while (!isTokenized) {
            if (tempCurrentLine == currentLine) {
                if (isSingleComment) 
                {
                    isTokenized = true;
                    break;
                }
            } else {
                isSingleComment = false;
                tempCurrentLine = -1;
            }
            if (isMultiComment) {
                lexStateTable.state1(inputChars, currentLine, tokens, identifiers);
                if (isMultiComment) {
                    currentChar = 0;
                    isTokenized = true;
                    return;
                }
                isMultiComment = false;
                if (currentChar == inputChars.length) {
                    currentChar = 0;
                    return;
                }
            }
            switch (inputChars[currentChar]) {
                case 'e': 
                    lexStateTable.state2(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case 'i': 
                    lexStateTable.state2(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case 'r': 
                    lexStateTable.state2(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case 'v': 
                    lexStateTable.state2(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case 'w': 
                    lexStateTable.state2(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case 'f': 
                    lexStateTable.state2(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '+': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '-': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '*': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '<': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '>': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '=': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '!': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case ';': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case ',': 
                    lexStateTable.state3(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '(': 
                    lexStateTable.state6(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case ')': 
                    lexStateTable.state6(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '[': 
                    lexStateTable.state6(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case ']': 
                    lexStateTable.state6(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '{': 
                    lexStateTable.state6(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '}': 
                    lexStateTable.state6(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '0': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '1': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '2': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '3': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '4': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '5': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '6': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '7': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '8': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '9': 
                    lexStateTable.state5(inputChars, currentLine, tokens, identifiers);
                    break;
                
                case '/': 
                    lexStateTable.state1(inputChars, currentLine, tokens, identifiers);
                    break;
                default:
                   if( ((inputChars[currentChar] >= 'a' && inputChars[currentChar]<= 'z') ||
                    inputChars[currentChar] >= 'A' && inputChars[currentChar]<= 'Z')    )          
                    state4(inputChars, currentLine, tokens, identifiers);
                    else
                    error(inputChars, currentLine, tokens, identifiers);
            }

        }
    }

    public static void error(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers) {
        if (readChar + currentChar != inputChars.length - 1 && endOfLine) {
            lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
            return;
        }
        if (readChar + currentChar == inputChars.length - 1) {
            lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
            return;
        }
        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
    }

    public static void state1(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers) {
        boolean found = false;
        while (isMultiComment) {
            if (currentChar == inputChars.length) {
                return;
            }
            if (currentChar < inputChars.length) {
                if (inputChars[currentChar] == '/' && currentChar + 1 != inputChars.length) {
                    if (inputChars[currentChar + 1] == '*') {
                        ++commentDepth;
                        currentChar += 2;
                        found = true;
                    }
                } else if (inputChars[currentChar] == '*' && currentChar + 1 != inputChars.length && inputChars[currentChar + 1] == '/') {
                    --commentDepth;
                    currentChar += 2;
                    found = true;
                }
            }
            if (commentDepth == 0) {
                isMultiComment = false;
                return;
            }
            if (found) {
                found = false;
                continue;
            }
            ++currentChar;
        }
        if (readChar + currentChar == inputChars.length - 1 && endOfLine) {
            lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "SP_SYM");
            return;
        }
        if (readChar + currentChar == inputChars.length - 1) {
            lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "SP_SYM");
            return;
        }
        if (readChar + currentChar != inputChars.length - 1 && inputChars[readChar + currentChar + 1] != '/' && inputChars[readChar + currentChar + 1] != '*') {
            lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "SP_SYM");
            return;
        }
        if (inputChars[readChar + currentChar + 1] == '/' && readChar + currentChar + 1 == inputChars.length - 1) {
            tempCurrentLine = currentLine;
            isSingleComment = true;
            currentToken = 0;
            currentChar = 0;
            readChar = 0;
            return;
        }
        if (inputChars[readChar + currentChar + 1] == '/') {
            tempCurrentLine = currentLine;
            isSingleComment = true;
            currentToken = 0;
            currentChar = 0;
            readChar = 0;
            return;
        }
        if (inputChars[readChar + currentChar + 1] == '*' && readChar + currentChar + 1 == inputChars.length - 1) {
            tempCurrentLine = currentLine;
            isMultiComment = true;
            readChar = 0;
            return;
        }
        if (inputChars[readChar + currentChar + 1] == '*') {
            tempCurrentLine = currentLine;
            isMultiComment = true;
            readChar = 0;
            return;
        }
    }

    public static void state2(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers) {
        switch (inputChars[readChar + currentChar]) {
            case 'e': {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar + 1] == 'l') {
                        if (++readChar + currentChar == inputChars.length - 1) {
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == 's') {
                            if (++readChar + currentChar == inputChars.length - 1) {
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            if (inputChars[readChar + currentChar + 1] == 'e') {
                                if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    return;
                                }
                                if (readChar + currentChar == inputChars.length - 1) {
                                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    return;
                                }
                                if (inputChars[readChar + currentChar + 1] == '{') {
                                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    return;
                                }
                                if (inputChars[readChar + currentChar + 1] == '(') {
                                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    return;
                                }
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    readChar = 0;
                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                    return;
                }
                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                return;
            }
            case 'i': {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar + 1] == 'f') {
                        if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                            lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                            return;
                        }
                        if (readChar + currentChar == inputChars.length - 1) {
                            lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == '(') {
                            lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    if (inputChars[readChar + currentChar + 1] == 'n') {
                        if (++readChar + currentChar == inputChars.length - 1) {
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == 't') {
                            if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                                lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                return;
                            }
                            if (readChar + currentChar == inputChars.length - 1) {
                                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                return;
                            }
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    readChar = 0;
                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                    return;
                }
                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                return;
            }
            case 'r': {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar + 1] == 'e') {
                        if (++readChar + currentChar == inputChars.length - 1) {
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == 't') {
                            if (++readChar + currentChar == inputChars.length - 1) {
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            if (inputChars[readChar + currentChar + 1] == 'u') {
                                if (++readChar + currentChar == inputChars.length - 1) {
                                    readChar = 0;
                                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                    return;
                                }
                                if (inputChars[readChar + currentChar + 1] == 'r') {
                                    if (++readChar + currentChar == inputChars.length - 1) {
                                        readChar = 0;
                                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                        return;
                                    }
                                    if (inputChars[readChar + currentChar + 1] != 'n') break;
                                    if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                                        lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        return;
                                    }
                                    if (readChar + currentChar == inputChars.length - 1) {
                                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        return;
                                    }
                                    if (inputChars[readChar + currentChar + 1] == ';') {
                                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        return;
                                    }
                                    readChar = 0;
                                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                    return;
                                }
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    readChar = 0;
                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                    return;
                }
                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                return;
            }
            case 'v': {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar + 1] == 'o') {
                        if (++readChar + currentChar == inputChars.length - 1) {
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == 'i') {
                            if (++readChar + currentChar == inputChars.length - 1) {
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            if (inputChars[readChar + currentChar + 1] == 'd') {
                                if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    return;
                                }
                                if (readChar + currentChar == inputChars.length - 1) {
                                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    return;
                                }
                                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                readChar = 0;
                                return;
                            }
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    readChar = 0;
                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                    return;
                }
                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                return;
            }
            case 'w': {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar + 1] == 'h') {
                        if (++readChar + currentChar == inputChars.length - 1) {
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == 'i') {
                            if (++readChar + currentChar == inputChars.length - 1) {
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            if (inputChars[readChar + currentChar + 1] == 'l') {
                                if (++readChar + currentChar == inputChars.length - 1) {
                                    readChar = 0;
                                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                    return;
                                }
                                if (inputChars[readChar + currentChar + 1] == 'e') {
                                    if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                                        lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        return;
                                    }
                                    if (readChar + currentChar == inputChars.length - 1) {
                                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        return;
                                    }
                                    if (inputChars[readChar + currentChar + 1] != '\u0000') {
                                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        return;
                                    }
                                    readChar = 0;
                                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                    return;
                                }
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    readChar = 0;
                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                    return;
                }
                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                return;
            }
            case 'f': {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar + 1] == 'l') {
                        if (++readChar + currentChar == inputChars.length - 1) {
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        if (inputChars[readChar + currentChar + 1] == 'o') {
                            if (++readChar + currentChar == inputChars.length - 1) {
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            if (inputChars[readChar + currentChar + 1] == 'a') {
                                if (++readChar + currentChar == inputChars.length - 1) {
                                    readChar = 0;
                                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                    return;
                                }
                                if (inputChars[readChar + currentChar + 1] == 't') {
                                    if (++readChar + currentChar == inputChars.length - 1 && endOfLine) {
                                        lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                    }
                                    if (readChar + currentChar == inputChars.length - 1) {
                                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "KEY");
                                        break;
                                    }
                                    readChar = 0;
                                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                    return;
                                }
                                readChar = 0;
                                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                                return;
                            }
                            readChar = 0;
                            lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                            return;
                        }
                        readChar = 0;
                        lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    readChar = 0;
                    lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                    return;
                }
                lexStateTable.state4(inputChars, currentLine, tokens, identifiers);
                return;
            }
        }
    }

    public static void state3(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers) {
        while (readChar + currentChar < inputChars.length) {
            if (inputChars[readChar + currentChar] == '+' || inputChars[readChar + currentChar] == '-' || inputChars[readChar + currentChar] == '*' || inputChars[readChar + currentChar] == ';' || inputChars[readChar + currentChar] == ',' || inputChars[readChar + currentChar] == '=') {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[readChar + currentChar] == '=' && inputChars[readChar + currentChar + 1] == '=' && readChar + currentChar + 1 == inputChars.length - 1 && endOfLine) {
                        ++readChar;
                        lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                        return;
                    }
                    if (inputChars[readChar + currentChar] == '=' && inputChars[readChar + currentChar + 1] == '=' && readChar + currentChar + 1 != inputChars.length - 1) {
                        ++readChar;
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                        return;
                    }
                    if (inputChars[readChar + currentChar] == '=' && inputChars[readChar + currentChar + 1] == '=' && readChar + currentChar + 1 == inputChars.length - 1) {
                        ++readChar;
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                        return;
                    }
                    if (inputChars[readChar + currentChar] == '*' && inputChars[readChar + currentChar + 1] == '/' && readChar + currentChar + 1 != inputChars.length - 1) {
                        lexStateTable.state1(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    if (inputChars[readChar + currentChar] == '*' && inputChars[readChar + currentChar + 1] == '/' && readChar + currentChar + 1 == inputChars.length - 1) {
                        lexStateTable.state1(inputChars, currentLine, tokens, identifiers);
                        return;
                    }
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "SP_SYM");
                    return;
                }
                if (endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "SP_SYM");
                } else {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "SP_SYM");
                }
                return;
            }
            boolean isNot = false;
            boolean bl = isNot = inputChars[readChar + currentChar] == '!';
            if (inputChars[readChar + currentChar] != '<' && inputChars[readChar + currentChar] != '>' && inputChars[readChar + currentChar] != '=' && inputChars[readChar + currentChar] != '!') continue;
            if (readChar + currentChar != inputChars.length - 1) {
                if (inputChars[readChar + currentChar + 1] == '=' && readChar + currentChar + 1 != inputChars.length - 1) {
                    ++readChar;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                    return;
                }
                if (inputChars[readChar + currentChar + 1] == '=' && readChar + currentChar + 1 == inputChars.length - 1 && endOfLine) {
                    ++readChar;
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                    return;
                }
                if (inputChars[readChar + currentChar + 1] == '=' && readChar + currentChar + 1 == inputChars.length - 1) {
                    ++readChar;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                    return;
                }
                if (!isNot && inputChars[readChar + currentChar + 1] != '=' && readChar + currentChar + 1 != inputChars.length - 1) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                    return;
                }
                if (!isNot && inputChars[readChar + currentChar + 1] != '=' && readChar + currentChar + 1 == inputChars.length - 1 && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                    return;
                }
                if (!isNot && inputChars[readChar + currentChar + 1] != '=' && readChar + currentChar + 1 == inputChars.length - 1) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                    return;
                }
                if (isNot && readChar + currentChar != inputChars.length - 1) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                    return;
                }
                if (endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                } else {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                }
                return;
            }
            if (!isNot) {
                if (endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                } else {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "REL");
                }
                return;
            }
            if (endOfLine) {
                lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
            } else {
                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
            }
            return;
        }
    }

    public static void state4(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers)//_____Identifiers
    {

        while(currentChar + readChar < inputChars.length) // if char is a letter
        {
            if(inputChars[readChar + currentChar] != '+' && inputChars[readChar + currentChar] != '-' && inputChars[readChar + currentChar] != '*'&&
                    inputChars[readChar + currentChar] != '/' && inputChars[readChar + currentChar] != '<' && inputChars[readChar + currentChar] != '>' &&
                    inputChars[readChar + currentChar] != '=' && inputChars[readChar + currentChar] != '!' && inputChars[readChar + currentChar] != ';' &&
                    inputChars[readChar + currentChar] != ',' && inputChars[readChar + currentChar] != '(' && inputChars[readChar + currentChar] != ')' &&
                    inputChars[readChar + currentChar] != '[' && inputChars[readChar + currentChar] != ']' && inputChars[readChar + currentChar] != '{'&&
                    inputChars[readChar + currentChar] != '}' && inputChars[readChar + currentChar] != '0' && inputChars[readChar + currentChar] != '1' &&
                    inputChars[readChar + currentChar] != '3' && inputChars[readChar + currentChar] != '4' && inputChars[readChar + currentChar] != '5' &&
                    inputChars[readChar + currentChar] != '6' && inputChars[readChar + currentChar] != '7' && inputChars[readChar + currentChar] != '8' &&
                    inputChars[readChar + currentChar] != '9' && inputChars[readChar + currentChar] != '2' && inputChars[readChar + currentChar] != '!' &&
                    inputChars[readChar + currentChar] != '@' && inputChars[readChar + currentChar] != '#' && inputChars[readChar + currentChar] != '_' &&
                    inputChars[readChar + currentChar] != '`' && inputChars[readChar + currentChar] != '~' && inputChars[readChar + currentChar] != '%' &&
                    inputChars[readChar + currentChar] != '^' && inputChars[readChar + currentChar] != '&')
            {
                if(readChar + currentChar == (inputChars.length - 2) && (lookAhead(inputChars[readChar + currentChar + 1])!= 3))
                {

                    makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
                if (readChar + currentChar == inputChars.length - 2 && lexStateTable.lookAhead(inputChars[readChar + currentChar + 1]) == 3 && endOfLine) {
                    ++readChar;
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
                
                if(readChar + currentChar == (inputChars.length - 2) && (lookAhead(inputChars[readChar + currentChar + 1])== 3))
                {
                    readChar++;
                    makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
                if (readChar + currentChar == inputChars.length - 1 && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
                if(readChar + currentChar == (inputChars.length - 1))
                {
                    makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
            }
            else
            {   
                //look for way to stop Key showing up as ID
                if(inputChars[readChar + currentChar] == '/')
                {
                readChar--;
                makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                
                state1(inputChars, currentLine, tokens, identifiers);
                return;
                }
                if(!(readChar + currentChar == (inputChars.length - 1)))
                {
                    readChar--;
                    makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
                else
                {
                    readChar--;
                    makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ID");
                    return;
                }
            }
            readChar++;
        }
    }

    public static void state5(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers) {
        boolean numberComplete = false;
        boolean decimalFound = false;
        boolean decimalFoundCount = false;
        boolean eFound = false;
        boolean isPlusMinus = false;
        boolean intE = false;
        boolean errorFound = false;
        if (inputChars[currentChar + readChar] >= '0' && inputChars[currentChar + readChar] <= '9') {
            if (readChar + currentChar == inputChars.length - 1 && endOfLine) {
                lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                return;
            }
            if (readChar + currentChar == inputChars.length - 1) {
                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                return;
            }
        }
        if (inputChars[currentChar + readChar] >= '0' && inputChars[currentChar + readChar] <= '9') {
            ++readChar;
            block7 : while (!decimalFound) {
                if (readChar + currentChar != inputChars.length - 1) {
                    switch (inputChars[currentChar + readChar]) {
                        case '.': {
                            if (readChar + currentChar + 1 != inputChars.length - 1 && inputChars[currentChar + readChar] == '.' && (inputChars[currentChar + readChar + 1] < '0' || inputChars[currentChar + readChar + 1] > '9')) {
                                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            if (readChar + currentChar + 1 == inputChars.length - 1 && endOfLine && inputChars[currentChar + readChar] == '.' && (inputChars[currentChar + readChar + 1] < '0' || inputChars[currentChar + readChar + 1] > '9')) {
                                ++currentChar;
                                lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            if (readChar + currentChar + 1 == inputChars.length - 1 && inputChars[currentChar + readChar] == '.' && (inputChars[currentChar + readChar + 1] < '0' || inputChars[currentChar + readChar + 1] > '9')) {
                                ++currentChar;
                                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            decimalFound = true;
                            ++readChar;
                            continue block7;
                        }
                        case 'E': {
                            if (readChar + currentChar + 1 != inputChars.length - 1) {
                                if (inputChars[currentChar + readChar] == 'E' && inputChars[currentChar + readChar + 1] == '+' && inputChars[currentChar + readChar + 1] == '-' && inputChars[currentChar + readChar + 1] >= '0' && inputChars[currentChar + readChar + 1] <= '9') {
                                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                    return;
                                }
                                if (inputChars[currentChar + readChar] == 'E' && (inputChars[currentChar + readChar + 1] == '+' || inputChars[currentChar + readChar + 1] == '-')) {
                                    isPlusMinus = true;
                                }
                            }
                            if (readChar + currentChar + 1 == inputChars.length - 1 && endOfLine && inputChars[currentChar + readChar] == 'E' && inputChars[currentChar + readChar + 1] == '+' && inputChars[currentChar + readChar + 1] == '-') {
                                ++currentChar;
                                lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            if (readChar + currentChar + 1 == inputChars.length - 1 && inputChars[currentChar + readChar] == 'E' && inputChars[currentChar + readChar + 1] == '+' && inputChars[currentChar + readChar + 1] == '-') {
                                ++currentChar;
                                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            eFound = true;
                            decimalFound = true;
                            intE = true;
                            if (isPlusMinus) {
                                readChar += 2;
                                continue block7;
                            }
                            ++readChar;
                            continue block7;
                        }
                    }
                    if (inputChars[currentChar + readChar] < '0' || inputChars[currentChar + readChar] > '9') {
                        --readChar;
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                        return;
                    }
                    ++readChar;
                    continue;
                }
                if (inputChars[currentChar + readChar] == '.' && readChar + currentChar == inputChars.length - 1 && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                    return;
                }
                if (inputChars[currentChar + readChar] == '.' && readChar + currentChar == inputChars.length - 1) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                    return;
                }
                if (inputChars[currentChar + readChar] < '0' || inputChars[currentChar + readChar] > '9') {
                    --readChar;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                    return;
                }
                if (endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                } else {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                }
                return;
            }
            block8 : while (!eFound) {
                if (readChar + currentChar != inputChars.length - 1) {
                    switch (inputChars[currentChar + readChar]) {
                        case 'E': {
                            if (readChar + currentChar + 1 != inputChars.length - 1) {
                                if (inputChars[currentChar + readChar] == 'E' && inputChars[currentChar + readChar + 1] == '+' && inputChars[currentChar + readChar + 1] == '-' && inputChars[currentChar + readChar + 1] >= '0' && inputChars[currentChar + readChar + 1] <= '9') {
                                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                    return;
                                }
                                if (inputChars[currentChar + readChar] == 'E' && (inputChars[currentChar + readChar + 1] == '+' || inputChars[currentChar + readChar + 1] == '-')) {
                                    isPlusMinus = true;
                                }
                            }
                            if (readChar + currentChar + 1 == inputChars.length - 1 && endOfLine && inputChars[currentChar + readChar] == 'E' && inputChars[currentChar + readChar + 1] == '+' && inputChars[currentChar + readChar + 1] == '-') {
                                ++currentChar;
                                lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            if (readChar + currentChar + 1 == inputChars.length - 1 && inputChars[currentChar + readChar] == 'E' && inputChars[currentChar + readChar + 1] == '+' && inputChars[currentChar + readChar + 1] == '-') {
                                ++currentChar;
                                lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                                return;
                            }
                            eFound = true;
                            if (isPlusMinus) {
                                readChar += 2;
                                continue block8;
                            }
                            ++readChar;
                            continue block8;
                        }
                    }
                    if (inputChars[currentChar + readChar] < '0' || inputChars[currentChar + readChar] > '9') {
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                        return;
                    }
                    ++readChar;
                    continue;
                }
                if (inputChars[currentChar + readChar] == 'E' && readChar + currentChar == inputChars.length - 1 && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                    return;
                }
                if (inputChars[currentChar + readChar] == 'E' && readChar + currentChar == inputChars.length - 1) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "ERROR");
                    return;
                }
                if ((inputChars[currentChar + readChar] < '0' || inputChars[currentChar + readChar] > '9') && inputChars[currentChar + readChar] != 'E') {
                    --readChar;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                    return;
                }
                if (endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                } else {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                }
                return;
            }
            while (!errorFound) {
                if (readChar + currentChar != inputChars.length - 1) {
                    if (inputChars[currentChar + readChar] < '0' || inputChars[currentChar + readChar] > '9') {
                        if (intE) {
                            lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                            return;
                        }
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                        return;
                    }
                    ++readChar;
                    continue;
                }
                if (inputChars[currentChar + readChar] < '0' || inputChars[currentChar + readChar] > '9') {
                    --readChar;
                    if (intE) {
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                        return;
                    }
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                    return;
                }
                if (intE) {
                    if (endOfLine) {
                        lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                    } else {
                        lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "INT");
                    }
                    return;
                }
                if (endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                } else {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, "FLOAT");
                }
                return;
            }
        }
    }

    public static void state6(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers) {
        String leftRight;
        if (inputChars[readChar + currentChar] == '{' || inputChars[readChar + currentChar] == '}') 
        {
            if(inputChars[readChar + currentChar] == '{')
            leftRight = "LD";
            else
            leftRight = "RD";
            
            if (readChar + currentChar != inputChars.length - 1) 
            {
                if (leftRight.equals("LD")) {
                    ++scope;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD")) {
                    --scope;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
            } else {
                if (leftRight.equals("LD") && endOfLine) {
                    ++scope;
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD") && endOfLine) {
                    --scope;
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("LD")) {
                    ++scope;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD")) {
                    --scope;
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
            }
        }
        if (inputChars[readChar + currentChar] == '[' || inputChars[readChar + currentChar] == ']') {
            leftRight = inputChars[readChar + currentChar] == '[' ? "LD" : "RD";
            if (readChar + currentChar != inputChars.length - 1) {
                if (leftRight.equals("LD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
            } else {
                if (leftRight.equals("LD") && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD") && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("LD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
            }
        }
        if (inputChars[readChar + currentChar] == '(' || inputChars[readChar + currentChar] == ')') {
            leftRight = inputChars[readChar + currentChar] == '(' ? "LD" : "RD";
            if (readChar + currentChar != inputChars.length - 1) {
                if (leftRight.equals("LD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
            } else {
                if (leftRight.equals("LD") && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("LD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD") && endOfLine) {
                    lexStateTable.makeTokenIsEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
                if (leftRight.equals("RD")) {
                    lexStateTable.makeTokenNotEndLine(inputChars, currentLine, tokens, identifiers, leftRight);
                    return;
                }
            }
        }
    }

    public static void makeTokenNotEndLine(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers, String tokenType) {
        String name = new String(inputChars, currentChar, readChar + 1);
        boolean isArray = false;
        tokens[currentLine][currentToken].setTokenType(tokenType);
        tokens[currentLine][currentToken].setValue(name);
        tokens[currentLine][currentToken].setScope(scope);
        System.out.println("Type: " + tokens[currentLine][currentToken].getTokenType() );
        System.out.println("Value: " + tokens[currentLine][currentToken].getValue() );
        System.out.println("Scope: " + tokens[currentLine][currentToken].getScope() );
        System.out.print("\n");
        
        if (tokenType.equals("ID")) 
        {
            if(!identifiers.alreadyExists(name))
            {
               if(inputChars[readChar + 1] == '[')
               isArray = true;
               else
               isArray = false;
               
               identifiers.createEntry(name, scope, tokens[currentLine][currentToken - 1].getValue().toUpperCase(), isArray);  
            }
             
        }
        if (readChar + currentChar == inputChars.length - 1 && endOfLine) {
            currentToken = 0;
            currentChar = 0;
            readChar = 0;
            isTokenized = true;
            return;
        }
        else if (readChar + currentChar == inputChars.length - 1) {
            ++currentToken;
            currentChar = 0;
            readChar = 0;
            isTokenized = true;
            return;
        }
        ++currentToken;
        ++readChar;
        currentChar += readChar;
        readChar = 0;
    }

    public static void makeTokenIsEndLine(char[] inputChars, int currentLine, tokenObject[][] tokens, idTable identifiers, String tokenType) {
        String name = new String(inputChars, currentChar, readChar + 1);
        boolean isArray = false;
        tokens[currentLine][currentToken].setTokenType(tokenType);
        tokens[currentLine][currentToken].setValue(name);
        tokens[currentLine][currentToken].setScope(scope);
        System.out.println("Type: " + tokens[currentLine][currentToken].getTokenType() );
        System.out.println("Value: " + tokens[currentLine][currentToken].getValue() );
        System.out.println("Scope: " + tokens[currentLine][currentToken].getScope() );
        System.out.print("\n");

        if (tokenType.equals("ID")) 
        {
            if(!identifiers.alreadyExists(name))
            {
               if(inputChars[readChar + 1] == '[')
               isArray = true;
               else
               isArray = false;
               
               identifiers.createEntry(name, scope, tokens[currentLine][currentToken - 1].getValue().toUpperCase(), isArray);  
            }
        }
        
        if (readChar + currentChar == inputChars.length - 1 && endOfLine) {
            currentToken = 0;
            currentChar = 0;
            readChar = 0;
            isTokenized = true;
            return;
        }
        else if (readChar + currentChar == inputChars.length - 1) {
            ++currentToken;
            currentChar = 0;
            readChar = 0;
            isTokenized = true;
            return;
        }
        
        currentToken = 0;
        currentChar = 0;
        readChar = 0;
        isTokenized = true;
    }

    public static int lookAhead(char c) {
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
            return 1;
        }
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '=' || c == '!' || c == ';' || c == ',' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}') {
            return 2;
        }
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
            return 3;
        }
        return 4;
    }

    static {
        endOfLine = false;
    }
}

class Values                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
{
    public String idName;
    public int initialScope;
    public String variableType;
    public boolean isArray;
    public int arraySize;

    public Values(String idName,int initialScope,String variableType, boolean isArray)
    {
        this.idName = idName;
        this.initialScope = initialScope;
        this.variableType = variableType;
        this.isArray = isArray;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public int getInitialScope() {
        return initialScope;
    }

    public void setInitialScope(int initialScope) {
        this.initialScope = initialScope;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public boolean getIsArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
    
    public String toString()
    {
    return "Name: " + this.idName + " Scope: " + this.initialScope + " Type: " + this.variableType  + " Is Array? " + this.isArray;
    }
    
}


class idTable 
{
    Hashtable<String, Values> identifiers = new Hashtable();

    idTable() {}
    
    public boolean alreadyExists(String name)
    {
    return (this.identifiers.containsKey(name));
    }

    public void createEntry(String name, int scope, String varType, boolean isArray) 
    {
        if (!this.identifiers.containsKey(name)) 
        {
            Values v = new Values(name, scope, varType, isArray);
            this.identifiers.put(name, v);
        }
    }

    public Values returnValues(String name) 
    {
        if (this.identifiers.containsKey(name)) 
        {
            return this.identifiers.get(name);
        }
        return null;
        
    }

    public void printTable() 
    {
        System.out.println(this.identifiers.toString());
    }
}

class tokenObject {
    String tokenType = null;
    String value = null;
    int scope = 0;
    int numValueInt = 0;
    float numValueFloat = 0;

    tokenObject() {
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNumValueInt() {
        return this.numValueInt;
    }

    public void setNumValueInt(int numValueInt) {
        this.numValueInt = numValueInt;
    }

    public float getNumValueFloat() {
        return this.numValueFloat;
    }

    public void setNumValueFloat(float numValueFloat) {
        this.numValueFloat = numValueFloat;
    }

    public int getScope() {
        return this.scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}




class parser {

    public static boolean result = true;
    public static int current = 0;
    public static tokenObject[] parseTokens;
    public static boolean accepted = false;
    
    //___________
    //area for semantic variables
    //___________
    public static String semantic_methodType = null;
    
    parser() 
    {
    }

    public static boolean parser(tokenObject[][] parseTokens) 
    {
        
        tokenObject[][] globalTokens = parseTokens;
        tokenObject[] currentTokens = parser.condenseTokens(globalTokens);
        
        for (int i = 0; i < currentTokens.length; ++i) 
        {
            System.out.println(currentTokens[i].getValue() + " " + currentTokens[i].getTokenType() );
        }
        
        for (int i = 0; i < currentTokens.length; ++i) 
        {
            if (currentTokens[i].getTokenType().equals("ERROR"))
            {
            System.out.print("REJECT");
            result = false;
            return result;
            }
        }
        
        parser.parseGrammar(currentTokens);
        
        if (result) 
        {
            System.out.print("ACCEPT");
        } else 
        {
            System.out.print("REJECT");
        }
        return result;
    }

    public static tokenObject[] condenseTokens(tokenObject[][] parseTokens) {
        
        int size = 0;
        String compare = null;
        
        for (int i = 0; i < parseTokens.length; i++) 
        {
            for (int j = 0; j < parseTokens[i].length; j++) 
            {
            System.out.println( "line" + i + " " + "row" + j + " " + parseTokens[i][j].getValue() + " | ");
            }
        }
        
        
        
        
        
        
        
        
        for (int i = 0; i < parseTokens.length; i++) 
        {
            for (int j = 0; j < parseTokens[i].length; j++) 
            {
                compare = parseTokens[i][j].getValue();
                
                if(compare != null)
                {
                size++;
                
                if(compare.equals("==") ||
                compare.equals("<=") ||
                compare.equals(">=") ||
                compare.equals("!=") )
                size++;
                }
            }
        }
        
        tokenObject[] passTokens = new tokenObject[size + 1];
        
        for (int j = 0; j < passTokens.length; j++) 
        {
            passTokens[j] = new tokenObject();
        }
        
        int passIterator = 0;
        
        for (int k = 0; k < parseTokens.length; k++) 
        {
            for (int l = 0; l < parseTokens[k].length; l++) 
            {
                compare = parseTokens[k][l].getValue();
                
                if (compare != null) 
                {
                if(parseTokens[k][l].getValue().equals("==") ||
                parseTokens[k][l].getValue().equals("<=") ||
                parseTokens[k][l].getValue().equals(">=") ||
                parseTokens[k][l].getValue().equals("!=") )
                {
                                  passTokens[passIterator].setValue( Character.toString( parseTokens[k][l].getValue().charAt(0) ) );
                                  passTokens[passIterator].setTokenType(parseTokens[k][l].getTokenType());
                                  passTokens[passIterator].setNumValueInt(parseTokens[k][l].getNumValueInt());
                                  passTokens[passIterator].setNumValueFloat(parseTokens[k][l].getNumValueFloat());
                                  passTokens[passIterator].setScope(parseTokens[k][l].getScope());
                                  ++passIterator;
                                  
                                  passTokens[passIterator].setValue(Character.toString( parseTokens[k][l].getValue().charAt(1) ));
                                  passTokens[passIterator].setTokenType(parseTokens[k][l].getTokenType());
                                  passTokens[passIterator].setNumValueInt(parseTokens[k][l].getNumValueInt());
                                  passTokens[passIterator].setNumValueFloat(parseTokens[k][l].getNumValueFloat());
                                  passTokens[passIterator].setScope(parseTokens[k][l].getScope());
                                  ++passIterator;
                
                }
                else{
                passTokens[passIterator].setValue(parseTokens[k][l].getValue());
                passTokens[passIterator].setTokenType(parseTokens[k][l].getTokenType());
                passTokens[passIterator].setNumValueInt(parseTokens[k][l].getNumValueInt());
                passTokens[passIterator].setNumValueFloat(parseTokens[k][l].getNumValueFloat());
                passTokens[passIterator].setScope(parseTokens[k][l].getScope());
                ++passIterator;}
                }
            }
        }
        
        passTokens[passIterator].setValue("$");
        passTokens[passIterator].setTokenType("END");
        passTokens[passIterator].setNumValueInt(0);
        passTokens[passIterator].setNumValueFloat(0);
        passTokens[passIterator].setScope(0);
        return passTokens;
    }

    public static void parseGrammar(tokenObject[] token) 
    {
        A(token);
    }

    public static void A(tokenObject[] token)//A 
    {
        System.out.println("In A" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals("int") || 
        token[current].getValue().equals("float") || 
        token[current].getValue().equals("void")) 
        {
            B1(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning A" + token[current].getValue());
    }

    public static void B1(tokenObject[] token) //B
    {
        System.out.println("In B" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("int") ||
            token[current].getValue().equals("float") || 
            token[current].getValue().equals("void")) 
            {
            C1(token);
            B2(token);
            } 
        else 
        {
            result = false;
        }
        System.out.println("Returning B" + token[current].getValue());
    }

    public static void B2(tokenObject[] token) //B*
    {
        System.out.println("In B*" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals("int") || 
        token[current].getValue().equals("float") || 
        token[current].getValue().equals("void")) 
        {
            C1(token);
            B2(token);
            if(token[current].getValue().equals("$"))
            accepted = true;
        } 
        
        else if (token[current].getValue().equals("$"))
         {
          return;
         }
         
         else{result = false;} 
         System.out.println("Returning B*" + token[current].getValue());   
    }

    public static void C1(tokenObject[] token) //C
    {
        System.out.println("In C" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("int") || 
            token[current].getValue().equals("float") || 
            token[current].getValue().equals("void")) 
            {
            
            T(token);
            
            if (token[current].getTokenType().equals("ID")) 
            {
                ++current;
            } 
            else 
            {
                result = false;
                return;
            }
            
            C2(token); 
            } 
            
        else {
            result = false;
            }
            System.out.println("Returning C" + token[current].getValue());
    }

    public static void C2(tokenObject[] token) //C'
    {
        System.out.println("In C'" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals(";") ||
            token[current].getValue().equals("[")) 
            {
            C3(token);
            } 
            
        else if (token[current].getValue().equals("(")) 
        {
            semantic_methodType = token[current - 2].getValue().toUpperCase();
            
            current++;
            
            Ps1(token);
            
            if(token[current].getValue().equals(")")) 
            { ++current; } 
            else 
            {
            result = false; return;}
            
            Cs(token);
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning C'" + token[current].getValue());
    }

    public static void C3(tokenObject[] token) //C'+
    {
        System.out.println("In C'+" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals(";")) 
        {
            ++current;
        }
        else if (token[current].getValue().equals("[")) 
        {
            current++;
        
            if (token[current].getTokenType().equals("INT") ||
                  token[current].getTokenType().equals("FLOAT") ) 
            {
                  current++;
            }
            else
            {result = false; return;}
        
            if (token[current].getValue().equals("]")) 
            {
                  current++;
            }
            else
            {result = false; return;}
            
            if(token[current].getValue().equals(";"))
            {
            current++;
            }
            else
            {result = false; return;}
        }
        else{
        result = false;}
        System.out.println("Returning C'+" + token[current].getValue());
        
    }

    public static void T(tokenObject[] token) //T
    {
        System.out.println("In T" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("int") ||
         token[current].getValue().equals("float") || 
         token[current].getValue().equals("void")) 
         {
            ++current;
        } 
        else {
       result = false;
        }
        System.out.println("Returning T" + token[current].getValue());
    }

    public static void Ps1(tokenObject[] token) //Ps
    {
        System.out.println("In Ps" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("int") ||
         token[current].getValue().equals("float")) 
         {
            current++;
            
            if (token[current].getTokenType().equals("ID")) 
            {
                ++current;
            } 
            else 
            {
                result = false;
                return;
            }
            
            Ps2(token);
            H1(token);
        } 
        else if (token[current].getValue().equals("void")) 
        {
            ++current;
            G(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning Ps" + token[current].getValue());
    }

    public static void Ps2(tokenObject[] token) //Ps+
    {
        System.out.println("In Ps+" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("[")) 
        {
        current++;
        
            if (token[current].getValue().equals("]")) 
            {
                ++current;
            } 
            else 
            {
                result = false;
            }
        } 
        else if (token[current].getValue().equals(",") || token[current].getValue().equals(")")) 
        {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning Ps+" + token[current].getValue());
    }

    public static void H1(tokenObject[] token) //H
    {
        System.out.println("In H" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals(",")) 
        {
            ++current;
            parser.H2(token);
        } 
        else if(token[current].getValue().equals(")"))
        {
            
        }
        else
        {
            result = false;
        }
        System.out.println("Returning H" + token[current].getValue());
    }

    public static void H2(tokenObject[] token) //H+
    {
        System.out.println("In H+" + token[current].getValue());
        
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals("int") || 
        token[current].getValue().equals("float") || 
        token[current].getValue().equals("void")) 
        {
            current++;

            if (token[current].getTokenType().equals("ID")) 
            {
                ++current;
            } 
            else 
            {
                result = false;
                return;
            }
            Ps2(token);
            H1(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning H+" + token[current].getValue());
    }

    public static void G(tokenObject[] token) //G
    {
        System.out.println("In G" + token[current].getValue());
        
        if (!result) 
        {
            return;
        }
        
        if (token[current].getTokenType().equals("ID")) 
        {
            ++current;
            
            Ps2(token);
            H1(token);
        } 
        else if (token[current].getValue().equals(")")) 
        {
           
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning G" + token[current].getValue());
    }

    public static void Cs(tokenObject[] token) //Cs
    {
        System.out.println("In Cs" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals("{")) 
        {
            current++;
        
        
         LD2(token);
         SL1(token);
        
         if (token[current].getValue().equals("}")) 
          {
            ++current;
         }
         else
         {
         result = false; return;
         }
         
        }
        else 
        {
            result = false;
        }
        System.out.println("Returning Cs" + token[current].getValue());
    }

    public static void LD1(tokenObject[] token) //LD
    {
        System.out.println("In LD" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("int") || 
        token[current].getValue().equals("float") || 
        token[current].getValue().equals("void")) 
        {
            LD2(token);
        } 
        else if (token[current].getTokenType().equals("ID") || 
        token[current].getTokenType().equals("FLOAT") || 
        token[current].getTokenType().equals("INT") || 
        token[current].getValue().equals("}") || 
        token[current].getValue().equals("{") || 
        token[current].getValue().equals("(") || 
        token[current].getValue().equals(";") || 
        token[current].getValue().equals("if") || 
        token[current].getValue().equals("while") || 
        token[current].getValue().equals("return")) 
        {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning LD" + token[current].getValue());
    }

    public static void LD2(tokenObject[] token) //LD*
    {
        System.out.println("In LD*" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("int") ||
         token[current].getValue().equals("float") ||
          token[current].getValue().equals("void")) 
          {
            T(token);
            
            if (token[current].getTokenType().equals("ID")) 
            {
                ++current;
            } else 
            {
                result = false;
                return;
            }
           C3(token);
           LD2(token);
           
        } 
        else if (token[current].getTokenType().equals("ID") || 
        token[current].getTokenType().equals("FLOAT") || 
        token[current].getTokenType().equals("INT") || 
        token[current].getValue().equals("}") || 
        token[current].getValue().equals("{") || 
        token[current].getValue().equals("(") || 
        token[current].getValue().equals(";") || 
        token[current].getValue().equals("if") || 
        token[current].getValue().equals("while") || 
        token[current].getValue().equals("return")) 
        {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning LD*" + token[current].getValue());
    }

    public static void SL1(tokenObject[] token) //SL
    {
        System.out.println("In SL" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getTokenType().equals("ID") || 
        token[current].getValue().equals("(") || 
        token[current].getTokenType().equals("INT") || 
        token[current].getTokenType().equals("FLOAT") || 
        token[current].getValue().equals(";") || 
        token[current].getValue().equals("{") || 
        token[current].getValue().equals("if") || 
        token[current].getValue().equals("while") || 
        token[current].getValue().equals("return")) 
        {
            SL2(token);
        } 
        else if (token[current].getValue().equals("}")) 
        {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning SL" + token[current].getValue());
    }

    public static void SL2(tokenObject[] token) //SL*
    {
        System.out.println("In SL*" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT") ||
           token[current].getTokenType().equals("FLOAT") ||
            token[current].getValue().equals(";") ||
             token[current].getValue().equals("{") ||
              token[current].getValue().equals("if") ||
               token[current].getValue().equals("while") ||
                token[current].getValue().equals("return")) 
        {
            S1(token);
            SL2(token);
            
        } 
        else if(token[current].getValue().equals("}"))
        {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning SL*" + token[current].getValue());
    }

    public static void S1(tokenObject[] token) //S'
    {
        System.out.println("In S'" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("FLOAT") ||
           token[current].getTokenType().equals("INT") ||
            token[current].getValue().equals(";"))
             {
            Es(token);
        } 
        else if (token[current].getValue().equals("{")) 
        {
            Cs(token);
        } 
        else if (token[current].getValue().equals("if")) 
        {
            Ss1(token);
        } 
        else if (token[current].getValue().equals("while")) 
        {
            Is(token);
        } 
        else if (token[current].getValue().equals("return")) 
        {
            Rs1(token);
        }
         else 
         {
            result = false;
        }
        System.out.println("Returning S'" + token[current].getValue());
    }

    public static void Es(tokenObject[] token) //Es
    {
        System.out.println("In Es" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals(";")) 
        {
            ++current;
        } 
        else if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT") ||
           token[current].getTokenType().equals("FLOAT")) 
        {
            E1(token);
            
            if(token[current].getValue().equals(";"))
            {current++;}
            else
            {result = false; return;} 
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning Es" + token[current].getValue());
    }

    public static void Ss1(tokenObject[] token) //Ss
    {
        System.out.println("In Ss" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("if")) 
        {
            current++;
            
            if (token[current].getValue().equals("(")) 
            {
               current++;
            }
            else{result = false; return;}
            
            E1(token);
            
            if (token[current].getValue().equals(")")) 
            {
                ++current;
            } 
            else 
            {
                result = false;
                return;
            }
            S1(token);//CHANGE HERE
            Ss2(token);
        } 
        else {
            result = false;
            }
            System.out.println("Returning Ss" + token[current].getValue());
    }

    public static void Ss2(tokenObject[] token) //Ss+
    {
        System.out.println("In Ss+" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT") ||
           token[current].getTokenType().equals("FLOAT") ||
            token[current].getValue().equals(";") ||
             token[current].getValue().equals("{") ||
              token[current].getValue().equals("if") ||
               token[current].getValue().equals("while") ||
                token[current].getValue().equals("return")) 
                {
            S1(token);
        } 
        else if (token[current].getValue().equals("else")) 
        {
            Ss3(token);
        } 
        else if (token[current].getValue().equals("}")) 
        {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning Ss+" + token[current].getValue());
    }

    public static void Ss3(tokenObject[] token) //Ss++
    {
        System.out.println("In Ss++" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("else")) 
        {
            ++current;
            S1(token);
        } 
        else if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT") ||
           token[current].getTokenType().equals("FLOAT") ||
            token[current].getValue().equals(";") ||
             token[current].getValue().equals("{") ||
              token[current].getValue().equals("if") ||
               token[current].getValue().equals("while") ||
                token[current].getValue().equals("return")) 
                {
            
        }
         else {
            result = false;
        }
        System.out.println("Returning Ss++" + token[current].getValue());
    }

    public static void Is(tokenObject[] token) //Is
    {
        System.out.println("In Is" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("while")) 
        {
        current++;
            
            if (token[current].getValue().equals("("))
             {
               current++;
            }
            else{ result = false; return;}
            
            parser.E1(token);
            
            if (token[current].getValue().equals(")")) 
            {
                ++current;
            } 
            else{ result = false; return;}

            S1(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning Is" + token[current].getValue());
    }

    public static void Rs1(tokenObject[] token) //Rs
    {
        System.out.println("In Rs" + token[current].getValue());
        
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals("return")) 
        {
            ++current;
            Rs2(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning Rs" + token[current].getValue());
    }

    public static void Rs2(tokenObject[] token) //Rs+
    {
        System.out.println("In Rs+" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("FLOAT") ||
           token[current].getTokenType().equals("INT")) 
           {
            
            E1(token);
            
            if (token[current].getValue().equals(";")) 
            {
                ++current;
            }
            else
            {
            result = false;
            return;
            }
           }
            
            else if (token[current].getValue().equals(";"))
            {++current;}
            
            else{result = false;}
            System.out.println("Returning Rs+" + token[current].getValue());

    }

    public static void E1(tokenObject[] token) //E'
    {
        System.out.println("In E'" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getTokenType().equals("ID")) 
        {
           
            ++current;
            E2(token);
            
        } 
        else if (token[current].getValue().equals("(")) {
            ++current;
            
            E1(token);
            
            if(token[current].getValue().equals(")") )
            current++;
            else
            {result = false; return;}
            
            X1(token);
            Y1(token);
            SE1(token);
            
        } else if (token[current].getTokenType().equals("INT") || token[current].getTokenType().equals("FLOAT")) {
            current++;
            
            X1(token);
            Y1(token);
            SE1(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning E'" + token[current].getValue());
    }

    public static void E2(tokenObject[] token) //E'+
    {
        System.out.println("In E'+" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("(")) 
        {
            ++current;
            
            ARGS(token);
            
            if(token[current].getValue().equals(")"))
            current++;
            else
            {result = false; return;}
            
            X1(token);
            Y1(token);
            SE1(token);
            
        } 
        else if (token[current].getValue().equals("+") ||
         token[current].getValue().equals("-") ||
          token[current].getValue().equals("*") ||
           token[current].getValue().equals("/") ||
            token[current].getValue().equals("<") ||
             token[current].getValue().equals(">") ||
              token[current].getValue().equals("!")) 
              {
            X1(token);
            Y1(token);
            SE1(token);
        }
        else if (token[current].getValue().equals("=") && token[current+1].getValue().equals("=")) {
            ++current;//ADDED RULE HERE
            ++current;
            E1(token);
        }   
        else if (token[current].getValue().equals("=")) {
            ++current;
            E1(token);
        }
        else if (token[current].getValue().equals("[")) {
            current++;
            
            E1(token);
       
       if (token[current].getValue().equals("]")) 
       {
                ++current;
       } 
       else {result = false; return;}
            
            E3(token);
        
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]")) {
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning E'+" + token[current].getValue());
    }

    public static void E3(tokenObject[] token) //E'++
    {
        System.out.println("In E'++" + token[current].getValue());
        
        if (!result) 
        {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT") ||
           token[current].getTokenType().equals("FLOAT")) 
           {
            E1(token);
        } 
        else if (token[current].getValue().equals("+") ||
         token[current].getValue().equals("-") ||
          token[current].getValue().equals("*") ||
           token[current].getValue().equals("/") ||
            token[current].getValue().equals("<") ||
             token[current].getValue().equals(">") ||
              token[current].getValue().equals("!")) 
              {
            X1(token);
            Y1(token);
            SE1(token);
        }
        else if (token[current].getValue().equals("=") && token[current + 1].getValue().equals("=")) {
            ++current;//MADE CHANGE HERE
            ++current;
            E1(token);
        }   
        else if (token[current].getValue().equals("=")) {
            ++current;
            E1(token);
        }
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]")) 
           {
            
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning E'++" + token[current].getValue());
    }


    public static void two(tokenObject[] token) //2
    {
        System.out.println("In 2" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("(")) 
        {
        current++;
        
            ARGS(token);
            
            if (token[current].getValue().equals(")")) 
            {
                ++current;
            }
            else{
            result = false;
            return;}
          }
            
        
            else if (token[current].getValue().equals("[")) 
            {
            current++;
            
            E1(token);
            
            if (token[current].getValue().equals("]")) 
            {
                ++current;
            }
            else{
            result = false;
            return;}
            }
        
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]") ||
            token[current].getValue().equals("+") ||
             token[current].getValue().equals("-") ||
              token[current].getValue().equals("*") ||
               token[current].getValue().equals("/") ||
                token[current].getValue().equals("<") ||
                 token[current].getValue().equals(">") ||
                  token[current].getValue().equals("=") ||
                   token[current].getValue().equals("!")) 
                   {
                   
                   }
        
        else{result = false;}
        System.out.println("Returning 2" + token[current].getValue());

    }

    public static void SE1(tokenObject[] token) //SE
    {
        System.out.println("In SE" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("<") ||
         token[current].getValue().equals(">") ||
          token[current].getValue().equals("=") ||
           token[current].getValue().equals("!")) 
           {
            RE1(token);
            SE2(token);
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]")) 
           {
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning SE" + token[current].getValue());
    }

    public static void SE2(tokenObject[] token) //SE+
    {
        System.out.println("In SE+" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getTokenType().equals("ID")) 
        {
            ++current;
            SE3(token);
        } 
        else if (token[current].getValue().equals("(")) 
        {
            E1(token);
            
            if (token[current].getValue().equals(")")) 
            {
                ++current;
            } 
            else 
            {
                result = false;
                return;
            }
            
            X1(token);
            Y1(token);
            
        } 
        else if (token[current].getTokenType().equals("INT") ||
         token[current].getTokenType().equals("FLOAT")) 
         {
         current++;
            parser.X1(token);
            parser.Y1(token);
        } 
        else 
        {
            result = false;
        }
        System.out.println("Returning SE+" + token[current].getValue());
    }

    public static void SE3(tokenObject[] token) //SE++
    {
        System.out.println("In SE++" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("(") ||
         token[current].getValue().equals("+") ||
          token[current].getValue().equals("-") ||
           token[current].getValue().equals("*") ||
            token[current].getValue().equals("/") ||
             token[current].getValue().equals("[")) 
             {
            two(token);
            X1(token);
            Y1(token);
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]")) 
           {
           
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning SE++" + token[current].getValue());
    }

    public static void RE1(tokenObject[] token) //RE
    {
        System.out.println("In RE" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        
        if (token[current].getValue().equals("<")) 
        {
            ++current;
            RE2(token);
            return;
        }
        else if (token[current].getValue().equals(">")) 
        {
            ++current;
            RE2(token);
            return;
        }
        else if (token[current].getValue().equals("=")) 
        {
        current++;
        
            if (token[current].getValue().equals("=")) 
            {
                ++current;
            }
            else{result = false;
            return;}
        }
        else if (token[current].getValue().equals("!")) 
        {
            current++;
            
        if (token[current].getValue().equals("="))
            ++current;
        else{result = false; return;}
        }
        else{result = false;}
        System.out.println("Returning RE" + token[current].getValue());
    }

    public static void RE2(tokenObject[] token) //RE+
     {
        System.out.println("In RE+" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("=")) 
        {
            ++current;
        } 
        else if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("FLOAT") ||
           token[current].getTokenType().equals("INT")) 
           {
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning RE+" + token[current].getValue());
    }

    public static void Y1(tokenObject[] token) //Y 
    {
        System.out.println("In Y" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getValue().equals("+") ||
         token[current].getValue().equals("-")) 
         {
            parser.Ao(token);
            parser.Y2(token);
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]") ||
            token[current].getValue().equals("<") ||
             token[current].getValue().equals(">") ||
              token[current].getValue().equals("=") ||
               token[current].getValue().equals("!")) 
               {
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning Y" + token[current].getValue());
    }


    public static void Y2(tokenObject[] token) //Y+
    {
        System.out.println("In Y+" + token[current].getValue());
        if (!result) 
        {
            return;
        }
        if (token[current].getTokenType().equals("ID")) 
        {
            ++current;
            Y3(token);

        }
        else if (token[current].getValue().equals("(")) 
        {
        current++;
            
            E1(token);
            
            if (token[current].getValue().equals(")")) 
            {
                ++current;
            }
            else{result = false;
            return;}
            
            X1(token);
            Y1(token);
        }
        else if (token[current].getTokenType().equals("INT") ||
                token[current].getTokenType().equals("FLOAT")) 
        {
        current++;
        parser.X1(token);
        parser.Y1(token);
        }
        else{result = false; return;}
        System.out.println("Returning Y+" + token[current].getValue());
    }

    public static void Y3(tokenObject[] token) //Y++
    {
        System.out.println("In Y++" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("(") ||
         token[current].getValue().equals("+") ||
          token[current].getValue().equals("-") ||
           token[current].getValue().equals("*") ||
            token[current].getValue().equals("/") ||
             token[current].getValue().equals("[")) 
         {
            two(token);
            X1(token);
            Y1(token);
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]") ||
            token[current].getValue().equals("<") ||
             token[current].getValue().equals(">") ||
              token[current].getValue().equals("=") ||
               token[current].getValue().equals("!")) 
               {
            
        } else {
            result = false;
        }
        System.out.println("Returning Y++" + token[current].getValue());
    }

    public static void Ao(tokenObject[] token) //Ao
    {
        System.out.println("In Ao" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("+") ||
         token[current].getValue().equals("-")) 
         {
            ++current;
        } else 
        {
            result = false;
        }
        System.out.println("Returning Ao" + token[current].getValue());
    }

    public static void X1(tokenObject[] token) //X
    {
        System.out.println("In X" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("*") ||
         token[current].getValue().equals("/"))
          {
            Mo(token);
            X2(token);
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]") ||
            token[current].getValue().equals("+") ||
             token[current].getValue().equals("-") ||
              token[current].getValue().equals("<") ||
               token[current].getValue().equals(">") ||
                token[current].getValue().equals("=") ||
                 token[current].getValue().equals("!")) 
                 {
            
        } else {
            result = false;
        }
        System.out.println("Returning X" + token[current].getValue());
    }

    public static void X2(tokenObject[] token)//X+
    {
        System.out.println("In X+" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getTokenType().equals("ID")) 
        {
            ++current;
            X3(token);
        } 
        else if (token[current].getValue().equals("(")) 
        {
            current++;
            
            E1(token);
            
            if (token[current].getValue().equals(")")) {
                ++current;
            } else {
                result = false;
                return;
            }
            
            X1(token);
        } 
        else if (token[current].getTokenType().equals("INT") ||
         token[current].getTokenType().equals("FLOAT")) 
         {
            ++current;
            X1(token);
        } else {
            result = false;
        }
        System.out.println("X+" + token[current].getValue());
    }

    public static void X3(tokenObject[] token) //X++
    {
        System.out.println("In X++" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("(") ||
         token[current].getValue().equals("*") ||
          token[current].getValue().equals("/") ||
           token[current].getValue().equals("[")) 
           {
            two(token);
            X1(token);
        } 
        else if (token[current].getValue().equals(";") ||
         token[current].getValue().equals(")") ||
          token[current].getValue().equals(",") ||
           token[current].getValue().equals("]") ||
            token[current].getValue().equals("+") ||
             token[current].getValue().equals("-") ||
              token[current].getValue().equals("<") ||
               token[current].getValue().equals(">") ||
                token[current].getValue().equals("=") ||
                 token[current].getValue().equals("!")) 
                 {
            
        } else {
            result = false;
        }
        System.out.println("Returning X++" + token[current].getValue());
    }

    public static void Mo(tokenObject[] token) //Mo
    {
        System.out.println("In Mo" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals("*") || token[current].getValue().equals("/")) {
            ++current;
        } else {
            result = false;
        }
       // System.out.println("Returning Mo" + token[current].getValue());
    }

    public static void ARGS(tokenObject[] token) //ARGS
    {
        System.out.println("ARGS" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getTokenType().equals("FLOAT") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT")) 
          {
            ARGL1(token);
        } 
        else if (token[current].getValue().equals(")")) 
        {
            
        } 
        else {
            result = false;
        }
        System.out.println("Returning ARGS" + token[current].getValue());
    }

    public static void ARGL1(tokenObject[] token) //ARGL
    {
        System.out.println("ARGL" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getTokenType().equals("ID") ||
         token[current].getValue().equals("(") ||
          token[current].getTokenType().equals("INT") ||
           token[current].getTokenType().equals("FLOAT")) 
           {
            E1(token);
            ARGL2(token);
        } else {
            result = false;
        }
        System.out.println("Returning ARGL1" + token[current].getValue());
    }

    public static void ARGL2(tokenObject[] token) //ARGL+
    {
        System.out.println("In ARGL+" + token[current].getValue());
        if (!result) {
            return;
        }
        if (token[current].getValue().equals(",")) 
        {
            ++current;
            E1(token);
            ARGL2(token);
        } 
        else if (token[current].getValue().equals(")")) 
        {
            
        } else {
            result = false;
        }
        System.out.println("Returning ARGL+" + token[current].getValue());
    }
}
