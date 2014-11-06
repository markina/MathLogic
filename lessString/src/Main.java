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
    List<Expression> globalListPropositions = new ArrayList<Expression>();
    List<Expression> globalListDevelopment = new ArrayList<Expression>();
    Expression PropositionA = null;
    Expression resultDeduction = null;
    List<String> listForLemmaAboutException = new ArrayList<String>();
    public void solve() throws Exception {

        fillList();
        List<Expression> allExp = new ArrayList<Expression>();
        while(hasMore()) {
            String s = nextWord();
            Expression exp = Parser.parseExp(s);
            Boolean b = false;
            for(int i = 0; i < allExp.size(); i++) {
                if(allExp.get(i).equals(exp)) {
                    b = true;
                    break;
                }
            }
            if(b) continue;
            allExp.add(exp);
        }
        for(int i = 0 ; i < allExp.size(); i++) {
            out.print(allExp.get(i).toString());
            writeEnd();
        }
    }


    private void lemmaAboutException(List<String> listForLemmaAboutException) {
        List<String> newListForLemmaAboutException = new ArrayList<String>();

        if(listForLemmaAboutException.size() == 1) return;
        for(int i = 0; i < listForLemmaAboutException.size(); i += 2) {
            BinaryOperation h = (BinaryOperation)Parser.parseExp(listForLemmaAboutException.get(i));
            Expression notP = h.l;
            String notPSt = notP.toString();
            Expression a = h.r;
            String aSt = a.toString();

            h = (BinaryOperation)Parser.parseExp(listForLemmaAboutException.get(i+1));
            Expression p = h.l;
            String pSt = p.toString();
            newListForLemmaAboutException.add(aSt);
            String s1 = p.toString();
            String outSt =
                    "A->A|!A" + "#" +
                    "(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(((A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->((A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->((A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->((A->A|!A)->((A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->((A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->!(A|!A)->A->A|!A" + "#" +
                    "(!(A|!A)->A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((!(A|!A)->A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->(!(A|!A)->A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->(!(A|!A)->A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->!(A|!A)->A->A|!A)->((A->A|!A)->(!(A|!A)->A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->(!(A|!A)->A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->((A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "((A->A|!A)->(!(A|!A)->(A->A|!A)->(A->!(A|!A))->!A)->!(A|!A)->(A->!(A|!A))->!A)->(A->A|!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "(A->A|!A)->!(A|!A)->(A->!(A|!A))->!A" + "#" +
                    "!(A|!A)->A->!(A|!A)" + "#" +
                    "(!(A|!A)->A->!(A|!A))->(A->A|!A)->!(A|!A)->A->!(A|!A)" + "#" +
                    "(A->A|!A)->!(A|!A)->A->!(A|!A)" + "#" +
                    "(!(A|!A)->A->!(A|!A))->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A" + "#" +
                    "((!(A|!A)->A->!(A|!A))->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A)->(A->A|!A)->(!(A|!A)->A->!(A|!A))->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A" + "#" +
                    "(A->A|!A)->(!(A|!A)->A->!(A|!A))->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A" + "#" +
                    "((A->A|!A)->!(A|!A)->A->!(A|!A))->((A->A|!A)->(!(A|!A)->A->!(A|!A))->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A)->(A->A|!A)->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A" + "#" +
                    "((A->A|!A)->(!(A|!A)->A->!(A|!A))->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A)->(A->A|!A)->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A" + "#" +
                    "(A->A|!A)->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A" + "#" +
                    "((A->A|!A)->!(A|!A)->(A->!(A|!A))->!A)->((A->A|!A)->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A)->(A->A|!A)->!(A|!A)->!A" + "#" +
                    "((A->A|!A)->(!(A|!A)->(A->!(A|!A))->!A)->!(A|!A)->!A)->(A->A|!A)->!(A|!A)->!A" + "#" +
                    "(A->A|!A)->!(A|!A)->!A" + "#" +
                    "!(A|!A)->!A" + "#" +
                    "!A->A|!A" + "#" +
                    "(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(((!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->((!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->((!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->((!A->A|!A)->((!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->((!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->!(A|!A)->!A->A|!A" + "#" +
                    "(!(A|!A)->!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!(A|!A)->!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->(!(A|!A)->!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->(!(A|!A)->!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->!(A|!A)->!A->A|!A)->((!A->A|!A)->(!(A|!A)->!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->(!(A|!A)->!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->((!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "((!A->A|!A)->(!(A|!A)->(!A->A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->(!A->!(A|!A))->!!A)->(!A->A|!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "(!A->A|!A)->!(A|!A)->(!A->!(A|!A))->!!A" + "#" +
                    "!(A|!A)->!A->!(A|!A)" + "#" +
                    "(!(A|!A)->!A->!(A|!A))->(!A->A|!A)->!(A|!A)->!A->!(A|!A)" + "#" +
                    "(!A->A|!A)->!(A|!A)->!A->!(A|!A)" + "#" +
                    "(!(A|!A)->!A->!(A|!A))->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A" + "#" +
                    "((!(A|!A)->!A->!(A|!A))->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A)->(!A->A|!A)->(!(A|!A)->!A->!(A|!A))->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A" + "#" +
                    "(!A->A|!A)->(!(A|!A)->!A->!(A|!A))->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A" + "#" +
                    "((!A->A|!A)->!(A|!A)->!A->!(A|!A))->((!A->A|!A)->(!(A|!A)->!A->!(A|!A))->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A)->(!A->A|!A)->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A" + "#" +
                    "((!A->A|!A)->(!(A|!A)->!A->!(A|!A))->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A)->(!A->A|!A)->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A" + "#" +
                    "(!A->A|!A)->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A" + "#" +
                    "((!A->A|!A)->!(A|!A)->(!A->!(A|!A))->!!A)->((!A->A|!A)->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A)->(!A->A|!A)->!(A|!A)->!!A" + "#" +
                    "((!A->A|!A)->(!(A|!A)->(!A->!(A|!A))->!!A)->!(A|!A)->!!A)->(!A->A|!A)->!(A|!A)->!!A" + "#" +
                    "(!A->A|!A)->!(A|!A)->!!A" + "#" +
                    "!(A|!A)->!!A" + "#" +
                    "(!(A|!A)->!A)->(!(A|!A)->!!A)->!!(A|!A)" + "#" +
                    "(!(A|!A)->!!A)->!!(A|!A)" + "#" +
                    "!!(A|!A)" + "#" +
                    "!!(A|!A)->A|!A" + "#" +
                    "A|!A" + "#";
            String st = new String();
            for(int j = 0; j < outSt.length(); j++) {
                if(outSt.charAt(j) == '#') {
                    out.print(st);
                    writeEnd();
                    st = "";
                    continue;
                }
                if(outSt.charAt(j) == 'A') {
                    st += s1;
                } else {
                    st += outSt.charAt(j);
                }
            }
            //out.print("Это была лемма 4.5");
            //writeEnd();

            out.print("(" + pSt + "->" + aSt + ")"
                    + "->" + "(" + notPSt + "->" + aSt + ")"
                    + "->" + "(" + pSt + "|" + notPSt + ")" + "->" + aSt);
            writeEnd();

            out.print("(" + notPSt + "->" + aSt + ")"
                    + "->" + "(" + pSt + "|" + notPSt + ")" + "->" + aSt);
            writeEnd();

            out.print(pSt + "|" + notPSt + "->" + aSt);
            writeEnd();

            out.print(aSt);
            writeEnd();
        }
        lemmaAboutException(newListForLemmaAboutException);

    }


    private void nextBooleansList(List<Boolean> b) {
        for(int i = 0; i < b.size(); i++) {
            if(!b.get(i)) {
                b.set(i, true);
                return;
            }
            if(b.get(i)) {
                b.set(i, false);
            }
        }
        return;
    }

    private void writeDeductionTheoremWithoutPropositions(List<String> allString) {
        //List<String> :
        //A,B|-(A&B)
        //A
        //B
        //...
        //(A&B)
        //RESULT :
        // //|-(A&B)
        //...
        //A->B->(A&B)

        /*
        List<String> readAll = new ArrayList<String>();
        String s = nextWord();
        readAll.add(s);
        while(hasMore()) {
            s = nextWord();
            readAll.add(s);
        }
        writeDeductionTheoremWithoutPropositions(readAll);
        */

        while(true) {
            allString = deductionTheorem(allString);

            if(allString.get(0).length() >= 2 && allString.get(0).charAt(0) == '|' && allString.get(0).charAt(1) == '-') {
                String result = allString.get(0).substring(3, allString.get(0).length()-1);
                listForLemmaAboutException.add(result);
                break;
            }
        }
        for(int i = 1; i < allString.size(); i++) {
            out.print(allString.get(i));
            writeEnd();
        }

    }

    private List<String> deductionTheorem(List<String> readAll) {
        globalListPropositions = new ArrayList<Expression>();
        globalListDevelopment = new ArrayList<Expression>();
        PropositionA = null;
        resultDeduction = null;

        List<String> newReadAll = new ArrayList<String>();
        int cnt = 0;
        String s = readAll.get(cnt);

        int lastI = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ',') {
                String t = s.substring(lastI, i);
                globalListPropositions.add(Parser.parseExp(t));
                lastI = i+1;
            }
            if(s.charAt(i) == '|' && s.charAt(i+1) == '-') {
                PropositionA = Parser.parseExp(s.substring(lastI, i));
                lastI = i + 2;
                break;
            }
        }

        resultDeduction = Parser.parseExp(PropositionA.toString() + "->" + s.substring(lastI, s.length()));

        s = "";
        for(int i = 0; i < globalListPropositions.size(); i++) {
            s += (globalListPropositions.get(i).toString());
            if(i != globalListPropositions.size() - 1) {
                s += ",";
            }
        }
        s += ("|-");
        s += (resultDeduction.toString());
        newReadAll.add(s);
        for(int i = 1; i < readAll.size(); i++) {
            s = readAll.get(i);
            Expression exp = Parser.parseExp(s);
            List<String> t = writeAll(exp);
            for(int j = 0; j < t.size(); j++) {
                newReadAll.add(t.get(j));
            }
            globalListDevelopment.add(exp);
        }
        return newReadAll;
    }

    private List<String> writeAll(Expression exp) {

        if(isAxiom(exp)) {
            return writeAxiom(exp);
        }
        for(int i = 0; i < globalListPropositions.size(); i++) {
            if(exp.equals(globalListPropositions.get(i))) {
                return writeAxiom(exp);
            }
        }
        if(PropositionA.equals(exp)) {
            return writeEqualsA(exp);
        }
        return writeMP(exp);
    }

    private List<String> writeMP(Expression exp) {
        List<String> outString = new ArrayList<String>();
        Expression expJ = null;
        Expression expK = null;
        for(int k = 0; k < globalListDevelopment.size(); k++) {
            if(globalListDevelopment.get(k).getClass() == Consecution.class) {
                BinaryOperation q = (BinaryOperation) globalListDevelopment.get(k);
                boolean f = false;
                if(q.r.equals(exp)) {
                    for(int j = 0; j < globalListDevelopment.size(); j++) {
                        if(q.l.equals(globalListDevelopment.get(j))) {
                            expJ = globalListDevelopment.get(j);
                            expK = globalListDevelopment.get(k);
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
        outString.add("(" + PropositionA.toString() + "->" + expJ.toString() + ")" + "->" + "((" + PropositionA + "->"
                + "(" + expJ.toString() + "->" + exp.toString() + "))"
                + "->" + "(" + PropositionA.toString() + "->" + exp.toString() + "))");

        //((A->(J->I))->(A->I))
        outString.add("((" + PropositionA.toString() + "->"
                + "(" + expJ.toString() + "->" + exp.toString() + "))"
                + "->" + "(" + PropositionA.toString() + "->" + exp.toString() + "))");

        //A->I
        outString.add(PropositionA.toString() + "->" + exp.toString());
        return outString;
    }

    private List<String> writeEqualsA(Expression exp) {
        List<String> outString = new ArrayList<String>();
        String stringExp = exp.toString();

        //(A->A)
        String smallString = "(" + stringExp +"->" + stringExp + ")";


        //A->(A->A)
        String firstLine = stringExp + "->" + smallString;
        outString.add(firstLine);

        //(A->(A->A))->(A->((A->A)->A))->(A->A)
        outString.add("(" + firstLine + ")" + "->" + "(" + stringExp + "->" + "(" + smallString + "->" + stringExp + "))"
                + "->" + smallString);

        //(A->((A->A)->A))->(A->A)
        outString.add("(" + stringExp + "->" + "(" + smallString + "->" + stringExp + "))" + "->" + smallString);

        //A->((A->A)->A)
        outString.add(stringExp + "->" + "(" + smallString + "->" + stringExp + ")");

        //A->A
        outString.add(stringExp + "->" + stringExp);
        return outString;
    }

    private List<String> writeAxiom(Expression exp) {
        List<String> outString = new ArrayList<String>();
        outString.add(exp.toString());

        outString.add(exp.toString() + "->" + "(" + PropositionA.toString() + "->" + exp.toString() + ")");

        outString.add(PropositionA.toString() + "->" + exp.toString());
        return outString;
    }

    private void writeEnd() {
        out.print("\n");
    }

    private boolean isMP(Expression exp) {
        for(int i = 0; i < globalListPropositions.size(); i++) {
            if(globalListPropositions.get(i).getClass() == Consecution.class) {
                BinaryOperation q = (BinaryOperation) globalListPropositions.get(i);
                Expression left = q.l;
                Expression right = q.r;
                if(right.equals(exp)) {
                    for(int j = 0; j < globalListPropositions.size(); j++) {
                        if(left.equals(globalListPropositions.get(j))) {
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
}