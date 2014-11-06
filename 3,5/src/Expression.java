import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by margarita on 2/3/14.
 */
public abstract class Expression {
    public abstract boolean equalsExp(Expression b);

    public boolean equalsToAxiom(Expression b) {
        return equalsToAxiom(b, new HashMap<String, Expression>());
    }

    protected abstract boolean equalsToAxiom(Expression b, Map<String, Expression> map);
    public abstract String toString();

    public abstract void getListTrueUnknow(List<Pair<Set<Expression>, Set<Expression>>> listCur);

}
