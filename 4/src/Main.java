import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    List<Expression> listAxiom = new ArrayList<Expression>();
    List<Expression> listG = new ArrayList<Expression>();
    List<Expression> listForCheckOnFree = new ArrayList<Expression>();
    List<Expression> listDelta = new ArrayList<Expression>();
    
    Expression a = null;
    Expression result = null;
    int CNT_G = 0;
    boolean doLog = false; //todo
    public void solve() throws Exception {


        CNT_G = 0;
        creatListAxiom();
        String s = nextWord();
        int lastI = 0;
        int bal = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                bal++;
                continue;
            }
            if(s.charAt(i) == ')') {
                bal--;
                continue;
            }
            if(bal == 0) {
                if(s.charAt(i) == ',') {
                    CNT_G++;
                    String t = s.substring(lastI, i);
                    Expression ttt = Parser.parseExp(t);
                    listG.add(ttt);
                    listForCheckOnFree.add(ttt);
                    listDelta.add(ttt);
                    lastI = i+1;
                }
            }
        }
        for(int i = lastI; i < s.length(); i++) {
            if(s.charAt(i) == '|' && s.charAt(i+1) == '-') {
                a = Parser.parseExp(s.substring(lastI, i));

                lastI = i + 2;
                break;
            }
        }
        listForCheckOnFree.add(a);
        result = Parser.parseExp(a.toString() + "->" + s.substring(lastI, s.length()));

        for(int i = 0; i < listG.size(); i++) {
            out.print(listG.get(i).toString());
            if(i != listG.size() - 1) {
                out.print(",");
            }
        }
        out.print("|-");
        out.print(result.toString());
        writeEnd();

        int y = 0;
        while(hasMore()) {
            y++;
            s = nextWord();
            Expression exp = Parser.parseExp(s);
            if(doLog) {
                out.print(y + "  :");
            }
            if(!writeAllEvidence(exp)) {
                //out.println("NotCorr " + y);
                out.println("Вывод некорректен начиная с формулы номер " + y);
                //continue;
                return; //todo
            }
            listDelta.add(exp);
            //listG.add(new Consecution(a,exp));
        }
    }

    private boolean writeAllEvidence(Expression exp) {

        if(isAxiom(exp)) {
            if(doLog) {
                out.println("it is axiom ");
            }
            writeLikeAxiom(exp);
            return true;
        }
        for(int i = 0; i < listG.size(); i++) {
            if(exp.equals(listG.get(i))) {
                if(doLog) {
                    out.println("совпадает с предположением " + (i+1));
                }
                writeLikeAxiom(exp);
                return true;
            }
        }
        if(a.equals(exp)) {
            if(doLog) {
                out.println("совпадает с альфа");
            }
            writeAA(exp);
            return true;
        }
        if(isMP(exp)) {
            writeMP(exp);
            return true;
        }

        if(isFirstNewRule(exp)) {
            if(doLog) {
                out.println("it was FirstNewRule");
            }
            return true;
        }

        if(isSecondNewRule(exp)) {
            if(doLog) {
                out.println("it was SecondNewRule");
            }
            return true;
        }

        return false;

    }

    private boolean isSecondNewRule(Expression exp) {
        if(exp.getClass() == Consecution.class) {
            Consecution q = (Consecution)exp;
            if(q.l.getClass() == Exist.class) {
                Exist leftQQ = (Exist)q.l;
                Expression rightQQ = q.r;
                Set<String> setForFree = new HashSet<String>();
                if(!xNotEnterInG(leftQQ.x)) {
                    return false;
                }
                if(rightQQ.canChenge(leftQQ.x, setForFree)) {
                    for(int i = 0; i < listDelta.size(); i++) {
                        if(listDelta.get(i).getClass() == Consecution.class) {
                            Consecution find = (Consecution)listDelta.get(i);
                            if(find.r.equals(rightQQ)) {
                                Consecution doAxiomForCheck = new Consecution(find.l, leftQQ);
                                if(isAxiom(doAxiomForCheck)) {
                                    writeSecondRule(leftQQ, leftQQ.e, rightQQ);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void writeSecondRule(Expression e, Expression bWithoutE, Expression c) {
        String wrString =
            "A->(A->A)->A" +"#" +
            "(A->(A->A)->A)->B->A->(A->A)->A" +"#" +
            "B->A->(A->A)->A" +"#" +
            "A->A->A" +"#" +
            "(A->A->A)->B->A->A->A" +"#" +
            "B->A->A->A" +"#" +
            "(A->A->A)->(A->(A->A)->A)->A->A" +"#" +
            "((A->A->A)->(A->(A->A)->A)->A->A)->B->(A->A->A)->(A->(A->A)->A)->A->A" +"#" +
            "B->(A->A->A)->(A->(A->A)->A)->A->A" +"#" +
            "(B->A->A->A)->(B->(A->A->A)->(A->(A->A)->A)->A->A)->B->(A->(A->A)->A)->A->A" +"#" +
            "(B->(A->A->A)->(A->(A->A)->A)->A->A)->B->(A->(A->A)->A)->A->A" +"#" +
            "B->(A->(A->A)->A)->A->A" +"#" +
            "(B->A->(A->A)->A)->(B->(A->(A->A)->A)->A->A)->B->A->A" +"#" +
            "(B->(A->(A->A)->A)->A->A)->B->A->A" +"#" +
            "B->A->A" +"#" +
            "B->(B->B)->B" +"#" +
            "B->B->B" +"#" +
            "(B->B->B)->(B->(B->B)->B)->B->B" +"#" +
            "(B->(B->B)->B)->B->B" +"#" +
            "B->B" +"#" +
            "B->A->B" +"#" +
            "(B->A->B)->B->B->A->B" +"#" +
            "B->B->A->B" +"#" +
            "(B->B)->(B->B->A->B)->B->A->B" +"#" +
            "(B->B->A->B)->B->A->B" +"#" +
            "B->A->B" +"#" +
            "A->B->C" +"#" +
            "(A->B->C)->B->A->B->C" +"#" +
            "B->A->B->C" +"#" +
            "(A->B->C)->A->A->B->C" +"#" +
            "((A->B->C)->A->A->B->C)->B->(A->B->C)->A->A->B->C" +"#" +
            "B->(A->B->C)->A->A->B->C" +"#" +
            "(B->A->B->C)->(B->(A->B->C)->A->A->B->C)->B->A->A->B->C" +"#" +
            "(B->(A->B->C)->A->A->B->C)->B->A->A->B->C" +"#" +
            "B->A->A->B->C" +"#" +
            "(A->A)->(A->A->B->C)->A->B->C" +"#" +
            "((A->A)->(A->A->B->C)->A->B->C)->B->(A->A)->(A->A->B->C)->A->B->C" +"#" +
            "B->(A->A)->(A->A->B->C)->A->B->C" +"#" +
            "(B->A->A)->(B->(A->A)->(A->A->B->C)->A->B->C)->B->(A->A->B->C)->A->B->C" +"#" +
            "(B->(A->A)->(A->A->B->C)->A->B->C)->B->(A->A->B->C)->A->B->C" +"#" +
            "B->(A->A->B->C)->A->B->C" +"#" +
            "A->B->C" +"#" +
            "(A->B->C)->B->A->B->C" +"#" +
            "B->A->B->C" +"#" +
            "(A->B)->(A->B->C)->A->C" +"#" +
            "((A->B)->(A->B->C)->A->C)->B->(A->B)->(A->B->C)->A->C" +"#" +
            "B->(A->B)->(A->B->C)->A->C" +"#" +
            "(B->A->B)->(B->(A->B)->(A->B->C)->A->C)->B->(A->B->C)->A->C" +"#" +
            "(B->(A->B)->(A->B->C)->A->C)->B->(A->B->C)->A->C" +"#" +
            "B->(A->B->C)->A->C" +"#" +
            "(B->A->B->C)->(B->(A->B->C)->A->C)->B->A->C" +"#" +
            "(B->(A->B->C)->A->C)->B->A->C" +"#" +
            "B->A->C" +"#" +
            "E->A->C" +"#" +
            "E->(E->E)->E" +"#" +
            "(E->(E->E)->E)->A->E->(E->E)->E" +"#" +
            "A->E->(E->E)->E" +"#" +
            "E->E->E" +"#" +
            "(E->E->E)->A->E->E->E" +"#" +
            "A->E->E->E" +"#" +
            "(E->E->E)->(E->(E->E)->E)->E->E" +"#" +
            "((E->E->E)->(E->(E->E)->E)->E->E)->A->(E->E->E)->(E->(E->E)->E)->E->E" +"#" +
            "A->(E->E->E)->(E->(E->E)->E)->E->E" +"#" +
            "(A->E->E->E)->(A->(E->E->E)->(E->(E->E)->E)->E->E)->A->(E->(E->E)->E)->E->E" +"#" +
            "(A->(E->E->E)->(E->(E->E)->E)->E->E)->A->(E->(E->E)->E)->E->E" +"#" +
            "A->(E->(E->E)->E)->E->E" +"#" +
            "(A->E->(E->E)->E)->(A->(E->(E->E)->E)->E->E)->A->E->E" +"#" +
            "(A->(E->(E->E)->E)->E->E)->A->E->E" +"#" +
            "A->E->E" +"#" +
            "A->(A->A)->A" +"#" +
            "A->A->A" +"#" +
            "(A->A->A)->(A->(A->A)->A)->A->A" +"#" +
            "(A->(A->A)->A)->A->A" +"#" +
            "A->A" +"#" +
            "A->E->A" +"#" +
            "(A->E->A)->A->A->E->A" +"#" +
            "A->A->E->A" +"#" +
            "(A->A)->(A->A->E->A)->A->E->A" +"#" +
            "(A->A->E->A)->A->E->A" +"#" +
            "A->E->A" +"#" +
            "E->A->C" +"#" +
            "(E->A->C)->A->E->A->C" +"#" +
            "A->E->A->C" +"#" +
            "(E->A->C)->E->E->A->C" +"#" +
            "((E->A->C)->E->E->A->C)->A->(E->A->C)->E->E->A->C" +"#" +
            "A->(E->A->C)->E->E->A->C" +"#" +
            "(A->E->A->C)->(A->(E->A->C)->E->E->A->C)->A->E->E->A->C" +"#" +
            "(A->(E->A->C)->E->E->A->C)->A->E->E->A->C" +"#" +
            "A->E->E->A->C" +"#" +
            "(E->E)->(E->E->A->C)->E->A->C" +"#" +
            "((E->E)->(E->E->A->C)->E->A->C)->A->(E->E)->(E->E->A->C)->E->A->C" +"#" +
            "A->(E->E)->(E->E->A->C)->E->A->C" +"#" +
            "(A->E->E)->(A->(E->E)->(E->E->A->C)->E->A->C)->A->(E->E->A->C)->E->A->C" +"#" +
            "(A->(E->E)->(E->E->A->C)->E->A->C)->A->(E->E->A->C)->E->A->C" +"#" +
            "A->(E->E->A->C)->E->A->C" +"#" +
            "E->A->C" +"#" +
            "(E->A->C)->A->E->A->C" +"#" +
            "A->E->A->C" +"#" +
            "(E->A)->(E->A->C)->E->C" +"#" +
            "((E->A)->(E->A->C)->E->C)->A->(E->A)->(E->A->C)->E->C" +"#" +
            "A->(E->A)->(E->A->C)->E->C" +"#" +
            "(A->E->A)->(A->(E->A)->(E->A->C)->E->C)->A->(E->A->C)->E->C" +"#" +
            "(A->(E->A)->(E->A->C)->E->C)->A->(E->A->C)->E->C" +"#" +
            "A->(E->A->C)->E->C" +"#" +
            "(A->E->A->C)->(A->(E->A->C)->E->C)->A->E->C" +"#" +
            "(A->(E->A->C)->E->C)->A->E->C" +"#" +
            "A->E->C" + "#";
        String st = new String();
        for(int i = 0; i < wrString.length(); i++) {
            if(wrString.charAt(i) == '#') {
                out.println(st);
                st = "";
                continue;
            }
            if(wrString.charAt(i) == 'A') {
                st += '(' + a.toString() + ')';
            } else if(wrString.charAt(i) == 'B') {
                st += '(' + bWithoutE.toString() + ')';
            } else if(wrString.charAt(i) == 'C') {
                st += '(' + c.toString() + ')';
            } else if(wrString.charAt(i) == 'E') {
                st += '(' + e.toString() + ')';
            } else {
                st += wrString.charAt(i);
            }
        }

    }


    private boolean xNotEnterInG(Peremennay a) {
        for(int i = 0; i < listForCheckOnFree.size(); i++) {
            Set<String> setForFree = new HashSet<String>();
            if(!listForCheckOnFree.get(i).canChenge(a, setForFree)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFirstNewRule(Expression exp) {
        if(exp.getClass() == Consecution.class) {
            Consecution q = (Consecution)exp;
            if(q.r.getClass() == Any.class) {
                Expression leftQ = q.l;
                Any rightQ = (Any)q.r;
                if(!xNotEnterInG(rightQ.x)) {
                    return false;
                }
                Set<String> setForFree = new HashSet<String>();
                if(leftQ.canChenge(rightQ.x, setForFree)) {
                    for(int i = 0; i < listDelta.size(); i++) {
                        if(listDelta.get(i).getClass() == Consecution.class) {
                            Consecution find = (Consecution)listDelta.get(i);
                            if(find.l.equals(leftQ)) {
                                Consecution doAxiomForCheck = new Consecution(rightQ, find.r);
                                if(isAxiom(doAxiomForCheck)) {
                                    writeFirstRule(leftQ, rightQ.e, rightQ);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void writeFirstRule(Expression b, Expression c, Expression f) {
        writeAllEvidence(new Consecution(b, c));
        String wrString =
                "A->B->C" + "#" +
                "A&B->(A&B->A&B)->A&B" + "#" +
                "A&B->A&B->A&B" + "#" +
                "(A&B->A&B->A&B)->(A&B->(A&B->A&B)->A&B)->A&B->A&B" + "#" +
                "(A&B->(A&B->A&B)->A&B)->A&B->A&B" + "#" +
                "A&B->A&B" + "#" +
                "A&B->A" + "#" +
                "(A&B->A)->A&B->A&B->A" + "#" +
                "A&B->A&B->A" + "#" +
                "A&B->B" + "#" +
                "(A&B->B)->A&B->A&B->B" + "#" +
                "A&B->A&B->B" + "#" +
                "(A&B->A&B)->(A&B->A&B->A)->A&B->A" + "#" +
                "(A&B->A&B->A)->A&B->A" + "#" +
                "A&B->A" + "#" +
                "(A&B->A&B)->(A&B->A&B->B)->A&B->B" + "#" +
                "(A&B->A&B->B)->A&B->B" + "#" +
                "A&B->B" + "#" +
                "(A->B->C)->A&B->A->B->C" + "#" +
                "A&B->A->B->C" + "#" +
                "(A&B->A)->(A&B->A->B->C)->A&B->B->C" + "#" +
                "(A&B->A->B->C)->A&B->B->C" + "#" +
                "A&B->B->C" + "#" +
                "(A&B->B)->(A&B->B->C)->A&B->C" + "#" +
                "(A&B->B->C)->A&B->C" + "#" +
                "A&B->C" + "#" +
                "A&B->F" + "#" +
                "A->(A->A)->A" + "#" +
                "A->A->A" + "#" +
                "(A->A->A)->(A->(A->A)->A)->A->A" + "#" +
                "(A->(A->A)->A)->A->A" + "#" +
                "A->A" + "#" +
                "A->B->A" + "#" +
                "(A->B->A)->A->A->B->A" + "#" +
                "A->A->B->A" + "#" +
                "(A->A)->(A->A->B->A)->A->B->A" + "#" +
                "(A->A->B->A)->A->B->A" + "#" +
                "A->B->A" + "#" +
                "B->(B->B)->B" + "#" +
                "(B->(B->B)->B)->A->B->(B->B)->B" + "#" +
                "A->B->(B->B)->B" + "#" +
                "B->B->B" + "#" +
                "(B->B->B)->A->B->B->B" + "#" +
                "A->B->B->B" + "#" +
                "(B->B->B)->(B->(B->B)->B)->B->B" + "#" +
                "((B->B->B)->(B->(B->B)->B)->B->B)->A->(B->B->B)->(B->(B->B)->B)->B->B" + "#" +
                "A->(B->B->B)->(B->(B->B)->B)->B->B" + "#" +
                "(A->B->B->B)->(A->(B->B->B)->(B->(B->B)->B)->B->B)->A->(B->(B->B)->B)->B->B" + "#" +
                "(A->(B->B->B)->(B->(B->B)->B)->B->B)->A->(B->(B->B)->B)->B->B" + "#" +
                "A->(B->(B->B)->B)->B->B" + "#" +
                "(A->B->(B->B)->B)->(A->(B->(B->B)->B)->B->B)->A->B->B" + "#" +
                "(A->(B->(B->B)->B)->B->B)->A->B->B" + "#" +
                "A->B->B" + "#" +
                "A->B->A&B" + "#" +
                "(A->B->A&B)->A->A->B->A&B" + "#" +
                "A->A->B->A&B" + "#" +
                "(A->B->A&B)->B->A->B->A&B" + "#" +
                "((A->B->A&B)->B->A->B->A&B)->A->(A->B->A&B)->B->A->B->A&B" + "#" +
                "A->(A->B->A&B)->B->A->B->A&B" + "#" +
                "(A->A->B->A&B)->(A->(A->B->A&B)->B->A->B->A&B)->A->B->A->B->A&B" + "#" +
                "(A->(A->B->A&B)->B->A->B->A&B)->A->B->A->B->A&B" + "#" +
                "A->B->A->B->A&B" + "#" +
                "(B->A)->(B->A->B->A&B)->B->B->A&B" + "#" +
                "((B->A)->(B->A->B->A&B)->B->B->A&B)->A->(B->A)->(B->A->B->A&B)->B->B->A&B" + "#" +
                "A->(B->A)->(B->A->B->A&B)->B->B->A&B" + "#" +
                "(A->B->A)->(A->(B->A)->(B->A->B->A&B)->B->B->A&B)->A->(B->A->B->A&B)->B->B->A&B" + "#" +
                "(A->(B->A)->(B->A->B->A&B)->B->B->A&B)->A->(B->A->B->A&B)->B->B->A&B" + "#" +
                "A->(B->A->B->A&B)->B->B->A&B" + "#" +
                "(A->B->A->B->A&B)->(A->(B->A->B->A&B)->B->B->A&B)->A->B->B->A&B" + "#" +
                "(A->(B->A->B->A&B)->B->B->A&B)->A->B->B->A&B" + "#" +
                "A->B->B->A&B" + "#" +
                "(B->B)->(B->B->A&B)->B->A&B" + "#" +
                "((B->B)->(B->B->A&B)->B->A&B)->A->(B->B)->(B->B->A&B)->B->A&B" + "#" +
                "A->(B->B)->(B->B->A&B)->B->A&B" + "#" +
                "(A->B->B)->(A->(B->B)->(B->B->A&B)->B->A&B)->A->(B->B->A&B)->B->A&B" + "#" +
                "(A->(B->B)->(B->B->A&B)->B->A&B)->A->(B->B->A&B)->B->A&B" + "#" +
                "A->(B->B->A&B)->B->A&B" + "#" +
                "(A->A)->(A->A->B->A&B)->A->B->A&B" + "#" +
                "(A->A->B->A&B)->A->B->A&B" + "#" +
                "A->B->A&B" + "#" +
                "(A&B->F)->A->A&B->F" + "#" +
                "A->A&B->F" + "#" +
                "(A&B->F)->B->A&B->F" + "#" +
                "((A&B->F)->B->A&B->F)->A->(A&B->F)->B->A&B->F" + "#" +
                "A->(A&B->F)->B->A&B->F" + "#" +
                "(A->A&B->F)->(A->(A&B->F)->B->A&B->F)->A->B->A&B->F" + "#" +
                "(A->(A&B->F)->B->A&B->F)->A->B->A&B->F" + "#" +
                "A->B->A&B->F" + "#" +
                "(B->A&B)->(B->A&B->F)->B->F" + "#" +
                "((B->A&B)->(B->A&B->F)->B->F)->A->(B->A&B)->(B->A&B->F)->B->F" + "#" +
                "A->(B->A&B)->(B->A&B->F)->B->F" + "#" +
                "(A->B->A&B)->(A->(B->A&B)->(B->A&B->F)->B->F)->A->(B->A&B->F)->B->F" + "#" +
                "(A->(B->A&B)->(B->A&B->F)->B->F)->A->(B->A&B->F)->B->F" + "#" +
                "A->(B->A&B->F)->B->F" + "#" +
                "(A->B->A&B->F)->(A->(B->A&B->F)->B->F)->A->B->F" + "#" +
                "(A->(B->A&B->F)->B->F)->A->B->F" + "#" +
                "A->B->F" + "#";
        String st = new String();
        for(int i = 0; i < wrString.length(); i++) {
            if(wrString.charAt(i) == '#') {
                out.println(st);
                st = "";
                continue;
            }
            if(wrString.charAt(i) == 'A') {
                st += '(' + a.toString() + ')';
            } else if(wrString.charAt(i) == 'B') {
                st += '(' + b.toString() + ')';
            } else if(wrString.charAt(i) == 'C') {
                st += '(' + c.toString() + ')';
            } else if(wrString.charAt(i) == 'F') {
                st += '(' + f.toString() + ')';
            } else {
                st += wrString.charAt(i);
            }
        }
    }

    private void writeMP(Expression exp) {
        Expression expJ = null;
        Expression expK = null;
        int first = 0;
        int second = 0;
        for(int k = 0; k < listDelta.size(); k++) {
            if(listDelta.get(k).getClass() == Consecution.class) {
                second = k;
                BinaryOperation q = (BinaryOperation) listDelta.get(k);
                boolean f = false;
                if(q.r.equals(exp)) {
                    for(int j = 0; j < listDelta.size(); j++) {
                        if(q.l.equals(listDelta.get(j))) {
                            first = j;
                            expJ = listDelta.get(j);
                            expK = listDelta.get(k);
                            f = true;
                            break;
                        }
                    }
                    if(f) break;
                }
            }
        }
        if(doLog) {
            out.println("правило вывода MP " + first + " + " + second);
        }
        //writeAllEvidence(expJ);
        //writeAllEvidence(expK);

        //(A->J)->((A->(J->I))->(A->I))
        printStringAndAddToGlobalList("(" + a.toString() + "->" + expJ.toString() + ")" + "->" + "((" + a + "->"
                + "(" + expJ.toString() + "->" + exp.toString() + "))"
                + "->" + "(" + a.toString() + "->" + exp.toString() + "))");

        ///
        writeAllEvidence(expJ);

        //((A->(J->I))->(A->I))
        printStringAndAddToGlobalList("((" + a.toString() + "->"
                + "(" + expJ.toString() + "->" + exp.toString() + "))"
                + "->" + "(" + a.toString() + "->" + exp.toString() + "))");

        writeAllEvidence(new Consecution(expJ, exp));
        //A->I
        printStringAndAddToGlobalList(a.toString() + "->" + exp.toString());
    }

    private void printStringAndAddToGlobalList(String s) {
        Expression exp = Parser.parseExp(s);
        //listG.add(exp);
        if(exp.equals(result)) {
            out.print(result.toString());
            return;
        }
        out.print(s);
        writeEnd();
    }

    private void writeAA(Expression exp) {
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

    private void writeLikeAxiom(Expression exp) {
        printStringAndAddToGlobalList(exp.toString());

        printStringAndAddToGlobalList(exp.toString() + "->" + "(" + a.toString() + "->" + exp.toString() + ")");

        printStringAndAddToGlobalList(a.toString() + "->" + exp.toString());
    }

    private void writeEnd() {
        out.print("\n");
    }

    private boolean isMP(Expression exp) {
        for(int i = 0; i < listDelta.size(); i++) {
            if(listDelta.get(i).getClass() == Consecution.class) {
                BinaryOperation q = (BinaryOperation) listDelta.get(i);
                Expression left = q.l;
                Expression right = q.r;
                if(right.equals(exp)) {
                    for(int j = 0; j < listDelta.size(); j++) {
                        if(left.equals(listDelta.get(j))) {
                           // out.print("MP " + i + " " + j + "\n");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void creatListAxiom() {
        listAxiom.add(Parser.parseExp("@xA->A"));
        listAxiom.add(Parser.parseExp("A->?xA"));
        listAxiom.add(Parser.parseExp("A->B->A"));
        listAxiom.add(Parser.parseExp("(A->B)->(A->B->C)->(A->C)"));
        listAxiom.add(Parser.parseExp("A->B->A&B"));
        listAxiom.add(Parser.parseExp("A&B->A"));
        listAxiom.add(Parser.parseExp("A&B->B"));
        listAxiom.add(Parser.parseExp("A->A|B"));
        listAxiom.add(Parser.parseExp("A->B|A"));
        listAxiom.add(Parser.parseExp("(A->B)->(C->B)->(A|C->B)"));
        listAxiom.add(Parser.parseExp("(A->B)->(A->!B)->!A"));
        listAxiom.add(Parser.parseExp("!!A->A"));
    }

    private boolean isAxiom(Expression exp) {
        for(int i = 0; i < listAxiom.size(); i++) {
            if((listAxiom.get(i)).equalsToAxiom(exp)) {
                //out.print("axiom " + i + "\n");
                return true;
            }
        }
        //out.print("NOOOTaxiom\n");
        return false;
    }

    public static void main(String[] args) throws Exception {
        String st = "a";//todo
        //String st = "axiom";
        //String st = "intro2";
        //String st = "must-fail";
        Main problem = new Main(st);
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

    public void close() throws Exception {
        in.close();
        out.close();
    }

}