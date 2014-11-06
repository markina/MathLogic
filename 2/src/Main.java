
import org.omg.CORBA._IDLTypeStub;
import sun.net.www.content.text.plain;
import sun.print.resources.serviceui_it;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/*
A->B->A
(A->B)->(A->B->C)->(A->C)
A->B->A&B
A&B->A
A&B->B
A->A|B
A->B|A
(A->B)->(C->B)->(A|C->B)
(A->B)->(A->!B)->!A
!!A->A
 */
public class Main {
    List<Expression> list = new ArrayList<Expression>();
    List<Expression> globalList = new ArrayList<Expression>();
    List<Expression> globalDelta = new ArrayList<Expression>();
    Expression a = new Parser().parseExp("A");
    Expression result = new Parser().parseExp("A");
    public void solve() throws Exception {

        fillList();

        String s = nextWord();
        int lastI = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ',') {
                String t = s.substring(lastI, i);
                globalList.add(Parser.parseExp(t));
                lastI = i+1;
            }
        }
        for(int i = lastI; i < s.length(); i++) {
            if(s.charAt(i) == '|' && s.charAt(i+1) == '-') {
                a = Parser.parseExp(s.substring(lastI, i));
                lastI = i + 2;
            }
        }
        result = Parser.parseExp(a.toString() + "->" + s.substring(lastI, s.length()));

        for(int i = 0; i < globalList.size(); i++) {
            out.print(globalList.get(i).toString());
            if(i != globalList.size() - 1) {
                out.print(",");
            }
        }
        out.print("|-");
        out.print(result.toString());
        writeEnd();

        while(hasMore()) {
            s = nextWord();
            Expression exp = Parser.parseExp(s);
            writeAll(exp);
            globalDelta.add(exp);
        }
    }

    private void writeAll(Expression exp) {

        if(isAxiom(exp)) {
            writeAxiom(exp);
            return;
        }
        for(int i = 0; i < globalList.size(); i++) {
            if(exp.equals(globalList.get(i))) {
                writeAxiom(exp);
                return;
            }
        }
        if(a.equals(exp)) {
            writeEqualsA(exp);
            return;
        }
        writeMP(exp);
        return;

    }

    private void writeMP(Expression exp) {
        Expression expJ = null;
        Expression expK = null;
        for(int k = 0; k < globalDelta.size(); k++) {
            if(globalDelta.get(k).getClass() == Consecution.class) {
                BinaryOperation q = (BinaryOperation)globalDelta.get(k);
                boolean f = false;
                if(q.r.equals(exp)) {
                    for(int j = 0; j < globalDelta.size(); j++) {
                        if(q.l.equals(globalDelta.get(j))) {
                            expJ = globalDelta.get(j);
                            expK = globalDelta.get(k);
                            f = true;
                            break;
                        }
                    }
                    if(f) break;
                }
            }
        }
        //writeAll(expJ);
        //writeAll(expK);

        //(A->J)->((A->(J->I))->(A->I))
        printStringAndAddToGlobalList("(" + a.toString() + "->" + expJ.toString() + ")" + "->" + "((" + a + "->"
                + "(" + expJ.toString() + "->" + exp.toString() + "))"
                + "->" + "(" + a.toString() + "->" + exp.toString() + "))");

        //((A->(J->I))->(A->I))
        printStringAndAddToGlobalList("((" + a.toString() + "->"
                + "(" + expJ.toString() + "->" + exp.toString() + "))"
                + "->" + "(" + a.toString() + "->" + exp.toString() + "))");

        //A->I
        printStringAndAddToGlobalList(a.toString() + "->" + exp.toString());
    }

    private void printStringAndAddToGlobalList(String s) {
        Expression exp = Parser.parseExp(s);
        if(exp.equals(result)) {
            out.print(result.toString());
            return;
        }
        out.print(s);
        writeEnd();
    }

    private void writeEqualsA(Expression exp) {
        String stringExp = exp.toString();

        //(A->A)
        String smallString = "(" + stringExp +"->" + stringExp + ")";


        //A->(A->A)
        String firstLine = stringExp + "->" + smallString;
        printStringAndAddToGlobalList(firstLine);

        //(A->(A->A))->(A->((A->A)->A))->(A->A)
        printStringAndAddToGlobalList("(" + firstLine + ")" + "->" + "(" + stringExp + "->" + "(" + smallString + "->" + stringExp + "))"
                + "->" + smallString);

        //(A->((A->A)->A))->(A->A)
        printStringAndAddToGlobalList("(" + stringExp + "->" + "(" + smallString + "->" + stringExp + "))" + "->" + smallString);

        //A->((A->A)->A)
        printStringAndAddToGlobalList(stringExp + "->" + "(" + smallString + "->" + stringExp + ")");

        //A->A
        printStringAndAddToGlobalList(stringExp + "->" + stringExp);
    }

    private void writeAxiom(Expression exp) {
        printStringAndAddToGlobalList(exp.toString());

        printStringAndAddToGlobalList(exp.toString() + "->" + "(" + a.toString() + "->" + exp.toString() + ")");

        printStringAndAddToGlobalList(a.toString() + "->" + exp.toString());
    }

    private void writeEnd() {
        out.print("\n");
    }

    private boolean isMP(Expression exp) {
        for(int i = 0; i < globalList.size(); i++) {
            if(globalList.get(i).getClass() == Consecution.class) {
                BinaryOperation q = (BinaryOperation)globalList.get(i);
                Expression left = q.l;
                Expression right = q.r;
                if(right.equals(exp)) {
                    for(int j = 0; j < globalList.size(); j++) {
                        if(left.equals(globalList.get(j))) {
                           // out.print("MP " + i + " " + j + "\n");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void fillList() {
        list.add(Parser.parseExp("A->B->A"));
        list.add(Parser.parseExp("(A->B)->(A->B->C)->(A->C)"));
        list.add(Parser.parseExp("A->B->A&B"));
        list.add(Parser.parseExp("A&B->A"));
        list.add(Parser.parseExp("A&B->B"));
        list.add(Parser.parseExp("A->A|B"));
        list.add(Parser.parseExp("A->B|A"));
        list.add(Parser.parseExp("(A->B)->(C->B)->(A|C->B)"));
        list.add(Parser.parseExp("(A->B)->(A->!B)->!A"));
        list.add(Parser.parseExp("!!A->A"));
    }

    private boolean isAxiom(Expression exp) {
        for(int i = 0; i < list.size(); i++) {
            if((list.get(i)).equalsToAxiom(exp)) {
                //out.print("axiom " + i + "\n");
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String a = "a";
        Main problem = new Main(a);
        problem.solve();
        problem.close();
    }

    BufferedReader in;
    PrintWriter out;
    String curLine;
    StringTokenizer tok;
    final String delimeter = " ";
    final String endOfFile = "";

    public Main(BufferedReader in, PrintWriter out) throws Exception {
        this.in = in;
        this.out = out;
        curLine = in.readLine();
        if (curLine == null || curLine == endOfFile) {
            tok = null;
        } else {
            tok = new StringTokenizer(curLine, delimeter);
        }
    }

    public Main() throws Exception {
        this(new BufferedReader(new InputStreamReader(System.in)),
                new PrintWriter(System.out));
    }

    public Main(String filename) throws Exception {
        this(new BufferedReader(new FileReader(filename + ".in")),
                new PrintWriter(filename + ".out"));
    }

    public boolean hasMore() throws Exception {
        if (tok == null || curLine == null) {
            return false;
        } else {
            while (!tok.hasMoreTokens()) {
                curLine = in.readLine();
                if (curLine == null || curLine.equalsIgnoreCase(endOfFile)) {
                    tok = null;
                    return false;
                } else {
                    tok = new StringTokenizer(curLine);
                }
            }
            return true;
        }
    }

    public String nextWord() throws Exception {
        if (!hasMore()) {
            return null;
        } else {
            return tok.nextToken();
        }
    }

    public int nextInt() throws Exception {
        return Integer.parseInt(nextWord());
    }

    public double nextDouble() throws Exception {
        return Double.parseDouble(nextWord());
    }

    public long nextLong() throws Exception {
        return Long.parseLong(nextWord());
    }

    public int[] readIntArray(int n) throws Exception {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = nextInt();
        }
        return res;
    }

    public void close() throws Exception {
        in.close();
        out.close();
    }

    private class T<A,B> {
        private A x;
        private B y;

        public T(A x, B y) {
            super();
            this.x = x;
            this.y = y;
        }
    }
}