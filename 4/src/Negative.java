import java.util.Map;
import java.util.Set;

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
    public void getPeremennes(Set<String> setNamePeremennes) {
        e.getPeremennes(setNamePeremennes);
    }

    @Override
    public boolean canChenge(Peremennay a, Set<String> setNearKvantor) {
       return e.canChenge(a, setNearKvantor);
    }


}
