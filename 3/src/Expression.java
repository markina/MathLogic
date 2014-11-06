import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by margarita on 2/3/14.
 */
public abstract class Expression {
    public abstract boolean equals(Expression b);

    public boolean equalsToAxiom(Expression b) {
        return equalsToAxiom(b, new HashMap<String, Expression>());
    }

    protected abstract boolean equalsToAxiom(Expression b, Map<String, Expression> map);
    public abstract String toString();

    public abstract void getAllConst(Map<String, Boolean> map);

    public abstract boolean checkEntry(Map<String, Boolean> map);

    public abstract void createdDevelopment(Map<String, Boolean> map, List<String> developmentWithPropositions);

}
