import java.util.List;
import java.util.Map;

/**
 * Created by margarita on 2/3/14.
 */
public class Consecution extends BinaryOperation{
    public Consecution(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getString() {
        return "->";
    }
    @Override
    public boolean checkEntry(Map<String, Boolean> map) {
        return (!this.l.checkEntry(map)|this.r.checkEntry(map));
    }

    @Override
    public void createdDevelopment(Map<String, Boolean> map, List<String> developmentWithPropositions) {
        this.l.createdDevelopment(map, developmentWithPropositions);
        this.r.createdDevelopment(map, developmentWithPropositions);
        boolean b1 = this.l.checkEntry(map);
        boolean b2 = this.r.checkEntry(map);

        String s1 = l.toString();
        String s2 = r.toString();
        if(b1 & b2) {
            String out =
                   "B" + "#" +
                   "B->A->B" + "#" +
                   "A->B" + "#";
            String st = new String();
            for(int i = 0; i < out.length(); i++) {
                if(out.charAt(i) == '#') {
                    developmentWithPropositions.add(st);
                    st = "";
                    continue;
                }
                if(out.charAt(i) == 'A') {
                    st += s1;
                } else if(out.charAt(i) == 'B') {
                    st += s2;
                } else {
                    st += out.charAt(i);
                }
            }
        }
        if(b1 & !b2) {
            String out =
                   "(A->B)->(A->B)->A->B" + "#" +
                   "((A->B)->(A->B)->A->B)->((A->B)->((A->B)->A->B)->A->B)->(A->B)->A->B" + "#" +
                   "((A->B)->((A->B)->A->B)->A->B)->(A->B)->A->B" + "#" +
                   "(A->B)->((A->B)->A->B)->A->B" + "#" +
                   "(A->B)->A->B" + "#" +
                   "A" + "#" +
                   "A->(A->B)->A" + "#" +
                   "(A->B)->A" + "#" +
                   "((A->B)->A)->((A->B)->A->B)->(A->B)->B" + "#" +
                   "((A->B)->A->B)->(A->B)->B" + "#" +
                   "(A->B)->B" + "#" +
                   "B->!A|B" + "#" +
                   "(B->!A|B)->(A->B)->B->!A|B" + "#" +
                   "(A->B)->B->!A|B" + "#" +
                   "((A->B)->B)->((A->B)->B->!A|B)->(A->B)->!A|B" + "#" +
                   "((A->B)->B->!A|B)->(A->B)->!A|B" + "#" +
                   "(A->B)->!A|B" + "#" +
                   "(!A->A)->(!A->!A)->!!A" + "#" +
                   "A->!A->A" + "#" +
                   "A" + "#" +
                   "!A->A" + "#" +
                   "(!A->!A)->!!A" + "#" +
                   "!A->!A->!A" + "#" +
                   "(!A->!A->!A)->(!A->(!A->!A)->!A)->!A->!A" + "#" +
                   "(!A->(!A->!A)->!A)->!A->!A" + "#" +
                   "!A->(!A->!A)->!A" + "#" +
                   "!A->!A" + "#" +
                   "!!A" + "#" +
                   "!A->!A->!A" + "#" +
                   "(!A->!A->!A)->(!A->(!A->!A)->!A)->!A->!A" + "#" +
                   "(!A->(!A->!A)->!A)->!A->!A" + "#" +
                   "!A->(!A->!A)->!A" + "#" +
                   "!A->!A" + "#" +
                   "(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B)" + "#" +
                   "((!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B))->!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B)" + "#" +
                   "!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B)" + "#" +
                   "!A->!!A&!B->!A" + "#" +
                   "(!A->!!A&!B->!A)->!A->!A->!!A&!B->!A" + "#" +
                   "!A->!A->!!A&!B->!A" + "#" +
                   "(!A->!A)->(!A->!A->!!A&!B->!A)->!A->!!A&!B->!A" + "#" +
                   "(!A->!A->!!A&!B->!A)->!A->!!A&!B->!A" + "#" +
                   "!A->!!A&!B->!A" + "#" +
                   "(!A->!!A&!B->!A)->(!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B))->!A->(!!A&!B->!!A)->!(!!A&!B)" + "#" +
                   "(!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B))->!A->(!!A&!B->!!A)->!(!!A&!B)" + "#" +
                   "!A->(!!A&!B->!!A)->!(!!A&!B)" + "#" +
                   "!!A&!B->!!A" + "#" +
                   "(!!A&!B->!!A)->!A->!!A&!B->!!A" + "#" +
                   "!A->!!A&!B->!!A" + "#" +
                   "(!A->!!A&!B->!!A)->(!A->(!!A&!B->!!A)->!(!!A&!B))->!A->!(!!A&!B)" + "#" +
                   "(!A->(!!A&!B->!!A)->!(!!A&!B))->!A->!(!!A&!B)" + "#" +
                   "!A->!(!!A&!B)" + "#" +
                   "B->B->B" + "#" +
                   "(B->B->B)->(B->(B->B)->B)->B->B" + "#" +
                   "(B->(B->B)->B)->B->B" + "#" +
                   "B->(B->B)->B" + "#" +
                   "B->B" + "#" +
                   "(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B)" + "#" +
                   "((!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B))->B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B)" + "#" +
                   "B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B)" + "#" +
                   "B->!!A&!B->B" + "#" +
                   "(B->!!A&!B->B)->B->B->!!A&!B->B" + "#" +
                   "B->B->!!A&!B->B" + "#" +
                   "(B->B)->(B->B->!!A&!B->B)->B->!!A&!B->B" + "#" +
                   "(B->B->!!A&!B->B)->B->!!A&!B->B" + "#" +
                   "B->!!A&!B->B" + "#" +
                   "(B->!!A&!B->B)->(B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B))->B->(!!A&!B->!B)->!(!!A&!B)" + "#" +
                   "(B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B))->B->(!!A&!B->!B)->!(!!A&!B)" + "#" +
                   "B->(!!A&!B->!B)->!(!!A&!B)" + "#" +
                   "!!A&!B->!B" + "#" +
                   "(!!A&!B->!B)->B->!!A&!B->!B" + "#" +
                   "B->!!A&!B->!B" + "#" +
                   "(B->!!A&!B->!B)->(B->(!!A&!B->!B)->!(!!A&!B))->B->!(!!A&!B)" + "#" +
                   "(B->(!!A&!B->!B)->!(!!A&!B))->B->!(!!A&!B)" + "#" +
                   "B->!(!!A&!B)" + "#" +
                   "(!A->!(!!A&!B))->(B->!(!!A&!B))->!A|B->!(!!A&!B)" + "#" +
                   "(B->!(!!A&!B))->!A|B->!(!!A&!B)" + "#" +
                   "!A|B->!(!!A&!B)" + "#" +
                   "!!A->!B->!!A&!B" + "#" +
                   "!B->!!A&!B" + "#" +
                   "!B" + "#" +
                   "!!A&!B" + "#" +
                   "!!A&!B->!A|B->!!A&!B" + "#" +
                   "!A|B->!!A&!B" + "#" +
                   "(!A|B->!!A&!B)->(!A|B->!(!!A&!B))->!(!A|B)" + "#" +
                   "(!A|B->!(!!A&!B))->!(!A|B)" + "#" +
                   "!(!A|B)" + "#" +
                   "!(!A|B)->(A->B)->!(!A|B)" + "#" +
                   "(A->B)->!(!A|B)" + "#" +
                   "((A->B)->!A|B)->((A->B)->!(!A|B))->!(A->B)" + "#" +
                   "((A->B)->!(!A|B))->!(A->B)" + "#" +
                   "!(A->B)" + "#";
            String st = new String();
            for(int i = 0; i < out.length(); i++) {
                if(out.charAt(i) == '#') {
                    developmentWithPropositions.add(st);
                    st = "";
                    continue;
                }
                if(out.charAt(i) == 'A') {
                    st += s1;
                } else if(out.charAt(i) == 'B') {
                    st += s2;
                } else {
                    st += out.charAt(i);
                }
            }
        }
        if(!b1 & b2) {
            String out =
                    "B" + "#" +
                    "B->A->B" + "#" +
                    "A->B" + "#";
            String st = new String();
            for(int i = 0; i < out.length(); i++) {
                if(out.charAt(i) == '#') {
                    developmentWithPropositions.add(st);
                    st = "";
                    continue;
                }
                if(out.charAt(i) == 'A') {
                    st += s1;
                } else if(out.charAt(i) == 'B') {
                    st += s2;
                } else {
                    st += out.charAt(i);
                }
            }
        }
        if(!b1 & !b2) {
            String out =
                 "(!B->A)->(!B->!A)->!!B" + "#" +
                 "((!B->A)->(!B->!A)->!!B)->A->(!B->A)->(!B->!A)->!!B" + "#" +
                 "A->(!B->A)->(!B->!A)->!!B" + "#" +
                 "!A->!B->!A" + "#" +
                 "(!A->!B->!A)->A->!A->!B->!A" + "#" +
                 "A->!A->!B->!A" + "#" +
                 "A->!B->A" + "#" +
                 "(A->!B->A)->A->A->!B->A" + "#" +
                 "A->A->!B->A" + "#" +
                 "A->A->A" + "#" +
                 "(A->A->A)->(A->(A->A)->A)->A->A" + "#" +
                 "(A->(A->A)->A)->A->A" + "#" +
                 "A->(A->A)->A" + "#" +
                 "A->A" + "#" +
                 "!A" + "#" +
                 "!A->A->!A" + "#" +
                 "A->!A" + "#" +
                 "(A->A)->(A->A->!B->A)->A->!B->A" + "#" +
                 "(A->A->!B->A)->A->!B->A" + "#" +
                 "A->!B->A" + "#" +
                 "(A->!A)->(A->!A->!B->!A)->A->!B->!A" + "#" +
                 "(A->!A->!B->!A)->A->!B->!A" + "#" +
                 "A->!B->!A" + "#" +
                 "(A->!B->A)->(A->(!B->A)->(!B->!A)->!!B)->A->(!B->!A)->!!B" + "#" +
                 "(A->(!B->A)->(!B->!A)->!!B)->A->(!B->!A)->!!B" + "#" +
                 "A->(!B->!A)->!!B" + "#" +
                 "(A->!B->!A)->(A->(!B->!A)->!!B)->A->!!B" + "#" +
                 "(A->(!B->!A)->!!B)->A->!!B" + "#" +
                 "A->!!B" + "#" +
                 "!!B->B" + "#" +
                 "(!!B->B)->A->!!B->B" + "#" +
                 "A->!!B->B" + "#" +
                 "(A->!!B)->(A->!!B->B)->A->B" + "#" +
                 "(A->!!B->B)->A->B" + "#" +
                 "A->B" + "#";
            String st = new String();
            for(int i = 0; i < out.length(); i++) {
                if(out.charAt(i) == '#') {
                    developmentWithPropositions.add(st);
                    st = "";
                    continue;
                }
                if(out.charAt(i) == 'A') {
                    st += s1;
                } else if(out.charAt(i) == 'B') {
                    st += s2;
                } else {
                    st += out.charAt(i);
                }
            }
        }
    }

}
