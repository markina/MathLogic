
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;




/*
A->A->A
A->(A->A)->A
(A->(A->A))->((A->((A->A)->A))->(A->A))
((A->((A->A)->A))->(A->A))
A->A
 */
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
    public void solve() throws Exception {

        fillList();
        int number = 0;
        while(hasMore()) {
            number++;
            String s = nextWord();
            Expression exp = Parser.parseExp(s);
            if(isAxiom(exp) || isMP(exp)) {
                globalList.add(exp);
            } else {
                out.print("Доказательство некорректно начиная с " + number + " высказывания.");
                return;
            }

        }
        out.print("Доказательство корректно.");



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
                           // out.print("MP " + i + "  " + j + "\n");
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
              //  out.print("axiom " + i + "\n");
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