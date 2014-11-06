import sun.print.resources.serviceui_zh_TW;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by margarita on 2/3/14.
 */
public class Negative extends Expression{
    protected Expression e;
    public Negative(Expression e) {
        this.e = e;
    }


    public boolean equals(Expression b) {
        if(getClass() != b.getClass())
            return false;

        Negative q = (Negative)b;

        if(!e.equals(q.e))
            return false;

        if(!e.equals(q.e))
            return false;

        return true;
    }

    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        if(getClass() != b.getClass())
            return false;

        Negative q = (Negative)b;

        if(!e.equalsToAxiom(q.e, map))
            return false;

        if(!e.equalsToAxiom(q.e, map))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "!" + e.toString();
    }

    @Override
    public void getAllConst(Map<String, Boolean> map) {
        e.getAllConst(map);
    }

    @Override
    public boolean checkEntry(Map<String, Boolean> map) {
        return !e.checkEntry(map);
    }

    @Override
    public void createdDevelopment(Map<String, Boolean> map, List<String> developmentWithPropositions) {
        boolean b;
        e.createdDevelopment(map, developmentWithPropositions);
        b = e.checkEntry(map);
        String a = e.toString();
        if(b) {
            String out =
                    "A" + "#" +
                    "A->!A->A" + "#" +
                    "!A->A" + "#" +
                    "!A->!A->!A" + "#" +
                    "(!A->!A->!A)->(!A->(!A->!A)->!A)->!A->!A" + "#" +
                    "(!A->(!A->!A)->!A)->!A->!A" + "#" +
                    "!A->(!A->!A)->!A" + "#" +
                    "!A->!A" + "#" +
                    "(!A->A)->(!A->!A)->!!A" + "#" +
                    "(!A->!A)->!!A" + "#" +
                    "!!A" + "#";
            String st = new String();
            for(int i = 0; i < out.length(); i++) {
                if(out.charAt(i) == '#') {
                    developmentWithPropositions.add(st);
                    st = "";
                    continue;
                } else if(out.charAt(i) == 'A') {
                    st += a;
                } else {
                    st += out.charAt(i);
                }
            }
        }
        if(!b) {
            String out =
                    "!A" + "#";
            String st = new String();
            for(int i = 0; i < out.length(); i++) {
                if(out.charAt(i) == '#') {
                    developmentWithPropositions.add(st);
                    st = "";
                    continue;
                } else if(out.charAt(i) == 'A') {
                    st += a;
                } else {
                    st += out.charAt(i);
                }
            }

        }

    }


}
