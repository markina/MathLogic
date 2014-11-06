/**
 * Created by margarita on 3/23/14.
 */
import java.util.Map;
import java.util.Set;

public class Exist extends Expression{
    protected Peremennay x;
    protected Expression e;

    public Exist(Peremennay x, Expression e) {
        this.x = x;
        this.e = e;
    }

    public boolean equals(Expression b) {
        if(getClass() != b.getClass())
            return false;

        Exist q = (Exist)b;

        if(!x.equals(q.x)) {
            return false;
        }

        if(!e.equals(q.e)) {
            return false;
        }

        return true;
    }

    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        throw new IllegalStateException("вызов equalsToAxiom в Exist");
    }

    @Override
    public String toString() {
        return "?" + x.toString() + e.toString();
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

