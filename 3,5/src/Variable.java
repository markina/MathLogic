import javafx.util.Pair;

import java.util.*;

/**
 * Created by margarita on 2/3/14.
 */
public class Variable extends Expression{
    protected String nameVariable;

    public Variable(String s) {
        this.nameVariable = s;
    }


    public boolean equalsExp(Expression b) {
        if(getClass() != b.getClass())
            return false;

        Variable q = (Variable)b;

        if(!nameVariable.equals(q.nameVariable))
            return false;

        return true;
    }

    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        String c = nameVariable;
        if(map.containsKey(c)) {
            if(!(map.get(c).equalsExp(b))) {
                return false;
            }
        } else {
            map.put(c, b);
        }
        return true;
    }

    @Override
    public String toString() {
        return nameVariable;
    }

    @Override
    public void getListTrueUnknow(List<Pair<Set<Expression>, Set<Expression>>> listCur) {
        List<Integer> listRem = new ArrayList<Integer>();
        List<Pair<Set<Expression>, Set<Expression>>> listAdd = new ArrayList<Pair<Set<Expression>, Set<Expression>>>();
        for(int i = 0; i < listCur.size(); i++) {
            Pair<Set<Expression>, Set<Expression>> pair;
            pair = listCur.get(i);

            boolean AisTrue = pair.getKey().contains(Parser.parseExp(nameVariable));
            boolean AisUnknow = pair.getValue().contains(Parser.parseExp(nameVariable));

            if(AisTrue) {
                continue;
            }
            if(AisUnknow) {
                continue;
            }
            listRem.add(i);
            Pair<Set<Expression>, Set<Expression>> pairFirst = new Pair<Set<Expression>, Set<Expression>>(new HashSet<Expression>(pair.getKey()),new HashSet<Expression>(pair.getValue()));
            pairFirst.getKey().add(Parser.parseConst(nameVariable));
            listAdd.add(pairFirst);
            Pair<Set<Expression>, Set<Expression>> pairSecond = new Pair<Set<Expression>, Set<Expression>>(new HashSet<Expression>(pair.getKey()),new HashSet<Expression>(pair.getValue()));
            pairSecond.getValue().add(Parser.parseConst(nameVariable));
            listAdd.add(pairSecond);
        }
        for(int i = listRem.size() - 1; i >= 0; i--) {
            listCur.remove((int)listRem.get(i));
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
