import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public void solve() throws Exception {


        while(hasMore()) {
            String s  = nextWord();

            Expression exp = Parser.parseExp(s);
            List<Pair<Set<Expression>, Set<Expression>>> listTrueUnkwon = new ArrayList<Pair<Set<Expression>, Set<Expression>>>();
            Pair<Set<Expression>, Set<Expression>> pair = new Pair<Set<Expression>, Set<Expression>>(new HashSet<Expression>(), new HashSet<Expression>());
            listTrueUnkwon.add(pair);
            exp.getListTrueUnknow(listTrueUnkwon);
            //writeListTrueUnknow(listTrueUnkwon);

            Node tree = getKr(listTrueUnkwon);
            if(tree.force(exp)) {
                //out.println("Высказывание" + exp + "общезначимо в интуиционистской логике.");
                out.println("Высказывание общезначимо в интуиционистской логике.");
            } else {
                //out.println("Высказывание " + exp + ":");
                writeKr(tree);
            }
            if(hasMore()) {
                out.println("---------------------------------------------------------------");
            }
        }
    }

    private void writeKr(Node tree, String prefix) {
        out.print(prefix);
        out.print("* ");
        List<Variable> forPrint = new ArrayList<Variable>();
        for(Expression var: tree.setTrue) {
            if(var.getClass() == Variable.class) {
                Variable varVar = (Variable) var;
                forPrint.add(varVar);
            }
        }
        for(int i = 0; i < forPrint.size(); i++) {
            out.print(forPrint.get(i).nameVariable);
            if(i != forPrint.size() - 1) {
                out.print(", ");
            }
        }
        out.println();
        for(Node child: tree.next) {
            writeKr(child, prefix + "\t");
        }
    }

    private void writeKr(Node tree) {
        writeKr(tree, "");
    }


    private Node getKr(List<Pair<Set<Expression>, Set<Expression>>> listTrueUnkwon) {
        Node tree;

        int find = 0;
        int start = -1;
        for(int i = 0; i < listTrueUnkwon.size(); i++) {
            if(listTrueUnkwon.get(i).getKey().size() == find) {
                start = i;
                break;
            }
        }
        tree = new Node(listTrueUnkwon.get(start).getKey());
        for(int j = 0; j < listTrueUnkwon.size(); j++) {
            for(int i = 0; i < listTrueUnkwon.size(); i++) {
                tree.insertChild(new Node(listTrueUnkwon.get(i).getKey()));
            }
        }
        return tree;
    }

    private void writeListTrueUnknow(List<Pair<Set<Expression>, Set<Expression>>> listTrueUnkwon) {
        for(int i = 0; i < listTrueUnkwon.size(); i++) {
            out.print("[");
            for(Expression expressions: listTrueUnkwon.get(i).getKey()) {
                out.print(expressions.toString());
                out.print(",");
            }
            out.print("]=");
            out.print("[");
            for(Expression expressions: listTrueUnkwon.get(i).getValue()) {
                out.print(expressions.toString());
                out.print(",");
            }
            out.println("]");
        }
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

}