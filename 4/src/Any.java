import java.util.Map;
import java.util.Set;

/**
 * Created by margarita on 3/23/14.
 */

public class Any extends Expression{
    protected Peremennay x;
    protected Expression e;

    public Any(Peremennay x, Expression e) {
        this.x = x;
        this.e = e;
    }

    public boolean equals(Expression b) {
        if(getClass() != b.getClass())
            return false;

        Any q = (Any)b;

        if(!x.equals(q.x)) {
            return false;
        }

        if(!e.equals(q.e)) {
            return false;
        }

        return true;
    }

    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        throw new IllegalStateException("вызов equalsToAxiom в Any");
    }

    @Override
    public String toString() {
        return "@" + x.toString() + e.toString();
    }

    @Override
    public void getPeremennes(Set<String> setNamePeremennes) {
        setNamePeremennes.add(x.toString());
        e.getPeremennes(setNamePeremennes);
    }

    @Override
    public boolean canChenge(Peremennay a, Set<String> setNearKvantor) {
        boolean have = false;
        if(a.namePeremennay.equals(x.namePeremennay)) {
            return true;
        }
        if(setNearKvantor.contains(x.namePeremennay)) {
            have = true;
        } else {
            setNearKvantor.add(x.namePeremennay);
        }
        if(!e.canChenge(a, setNearKvantor)) {
            return false;
        } else {
            if(!have) {
                setNearKvantor.remove(x.namePeremennay);
            }
            return true;
        }
    }


}

