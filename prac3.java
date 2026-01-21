import java.io.*;
import java.util.*;
import java.util.regex.*;

public class prac3 {

    static Set<String> keywords = new HashSet<>(Arrays.asList(
            "int", "float", "char", "void", "return",
            "long", "struct", "scanf", "printf"
    ));

    static Set<String> operators = new HashSet<>(Arrays.asList(
            "+", "-", "*", "/", "=", "==", "<", ">", "<=", ">=", "!="
    ));

    static Set<String> punctuations = new HashSet<>(Arrays.asList(
            "(", ")", "{", "}", "[", "]", ";", ",", "&"
    ));

    static Set<String> symbolTable = new LinkedHashSet<>();
    static List<String> tokens = new ArrayList<>();
    static List<String> lexicalErrors = new ArrayList<>();

    static boolean isIdentifier(String s) {
        return s.matches("[A-Za-z_][A-Za-z0-9_]*");
    }

    static boolean isConstant(String s) {
        return s.matches("\\d+(\\.\\d+)?") || s.matches("'.'");
    }

    static String removeComments(String code) {
        code = code.replaceAll("//.*", "");
        code = code.replaceAll("/\\*[\\s\\S]*?\\*/", "");
        return code;
    }

    static void analyze(String code) {
        String[] lines = code.split("\n");

        Pattern pattern = Pattern.compile(
                "[A-Za-z_][A-Za-z0-9_]*|\\d+\\.\\d+|\\d+|'." +
                        "'|==|!=|<=|>=|[+\\-*/=<>;,(){}&]"
        );

        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = pattern.matcher(lines[i]);
           while (matcher.find()) {
                String token = matcher.group();

                if (keywords.contains(token)) {
                    tokens.add("Keyword: " + token);
                } else if (operators.contains(token)) {
                    tokens.add("Operator: " + token);
                } else if (punctuations.contains(token)) {
                    tokens.add("Punctuation: " + token);
                } else if (isConstant(token)) {
                    tokens.add("Constant: " + token);
                } else if (isIdentifier(token)) {
                    tokens.add("Identifier: " + token);
                    symbolTable.add(token);
                } else {
                    lexicalErrors.add("Line " + (i + 1) +
                            " : " + token + " invalid lexeme");
                }
            }

            
            String[] parts = lines[i].split("\\s+");
            for (String p : parts) {
                if (p.matches("\\d+[A-Za-z_]+")) {
                    lexicalErrors.add("Line " + (i + 1) +
                            " : " + p + " invalid lexeme");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter C source file name: ");
        String fileName = sc.nextLine();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder code = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            code.append(line).append("\n");
        }
        br.close();

        String cleanCode = removeComments(code.toString());
        analyze(cleanCode);

        System.out.println("\nTOKENS");
        for (String t : tokens) {
            System.out.println(t);
        }

        System.out.println("\nLEXICAL ERRORS");
        if (lexicalErrors.isEmpty()) {
            System.out.println("None");
        } else {
            for (String e : lexicalErrors) {
                System.out.println(e);
            }
        }

        System.out.println("\nSYMBOL TABLE ENTRIES");
        int i = 1;
        for (String s : symbolTable) {
            System.out.println(i++ + ") " + s);
        }
    }
}