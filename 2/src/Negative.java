import java.util.HashMap;
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


}
