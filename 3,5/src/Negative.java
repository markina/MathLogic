import javafx.util.Pair;

import java.util.*;

/**
 * Created by margarita on 2/3/14.
 */
public class Negative extends Expression{
    protected Expression e;
    public Negative(Expression e) {
        this.e = e;
    }


    public boolean equalsExp(Expression b) {
        if(getClass() != b.getClass())
            return false;

        Negative q = (Negative)b;

        if(!e.equalsExp(q.e))
            return false;

        if(!e.equalsExp(q.e))
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
    public void getListTrueUnknow(List<Pair<Set<Expression>, Set<Expression>>> listCur) {
        Expression expA = this.e;
        expA.getListTrueUnknow(listCur);

        List<Pair<Set<Expression>, Set<Expression>>> listAdd = new ArrayList<Pair<Set<Expression>, Set<Expression>>>();

        for(int i = 0; i < listCur.size(); i++) {
            Pair<Set<Expression>, Set<Expression>> pair = new Pair<Set<Expression>, Set<Expression>>(new HashSet<Expression>(listCur.get(i).getKey()), new HashSet<Expression>(listCur.get(i).getValue()));
            boolean AisTrue = pair.getKey().contains(expA);
            boolean AisUnknow = pair.getValue().contains(expA);

            boolean EXPisTrue = pair.getKey().contains(this);
            boolean EXPisUnknow = pair.getValue().contains(this);

            if(EXPisTrue) {
                continue;
            }
            if(EXPisUnknow) {
                continue;
            }

            if(AisTrue) {
                listCur.get(i).getValue().add(this);
                continue;
            }
            if(AisUnknow) {
                listCur.get(i).getKey().add(this);
                pair.getValue().add(this);
                listAdd.add(pair);
                continue;

            }
        }
        for(int i = 0; i < listAdd.size(); i++) {
            listCur.add(listAdd.get(i));
        }
    }


    @Override
    public boolean equals(Object o) {
        Expression oo = (Expression) o;
        if(this.equalsExp(oo)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
