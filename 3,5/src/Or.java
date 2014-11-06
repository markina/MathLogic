import javafx.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by margarita on 2/3/14.
 */
public class Or extends BinaryOperation {
    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getString() {
        return "|";
    }


    @Override
    public void getListTrueUnknow(List<Pair<Set<Expression>, Set<Expression>>> listCur) {
        Expression expA = this.l;
        Expression expB= this.r;
        expA.getListTrueUnknow(listCur);
        expB.getListTrueUnknow(listCur);

        for(int i = 0; i < listCur.size(); i++) {
            Pair<Set<Expression>, Set<Expression>> pair = new Pair<Set<Expression>, Set<Expression>>(new HashSet<Expression>(listCur.get(i).getKey()), new HashSet<Expression>(listCur.get(i).getValue()));
            boolean AisTrue = pair.getKey().contains(expA);
            boolean BisTrue = pair.getKey().contains(expB);
            boolean AisUnknow = pair.getValue().contains(expA);
            boolean BisUnknow = pair.getValue().contains(expB);


            boolean EXPisTrue = pair.getKey().contains(this);
            boolean EXPisUnknow = pair.getValue().contains(this);

            if(EXPisTrue) {
                continue;
            }
            if(EXPisUnknow) {
                continue;
            }

            if(AisTrue && BisTrue) {
                listCur.get(i).getKey().add(this);
                continue;
            }
            if(AisTrue && BisUnknow) {
                listCur.get(i).getKey().add(this);
                continue;
            }
            if(BisTrue && AisUnknow) {
                listCur.get(i).getKey().add(this);
                continue;
            }
            if(AisUnknow && BisUnknow) {
                listCur.get(i).getValue().add(this);
                continue;
            }
        }
    }
}
