import java.util.*;

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

    public abstract void getPeremennes(Set<String> setNamePeremennes);

    public abstract boolean canChenge(Peremennay a, Set<String> setNearKvantor);

}
