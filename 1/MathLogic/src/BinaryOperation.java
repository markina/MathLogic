import java.util.Map;

/**
 * Created by margarita on 2/3/14.
 */
public abstract class BinaryOperation extends Expression{
    protected Expression l, r;
    public BinaryOperation(Expression left, Expression right) {
        l = left;
        r = right;
    }

    @Override
    public String toString() {
        return '(' + l.toString() + getString() + r.toString() + ')';
    }

    protected abstract String getString();

    public boolean equals(Expression b) {
        if(getClass() != b.getClass())
            return false;

        BinaryOperation q = (BinaryOperation)b;

        if(!l.equals(q.l))
            return false;

        if(!r.equals(q.r))
            return false;

        return true;
    }

    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        if(getClass() != b.getClass())
            return false;

        BinaryOperation q = (BinaryOperation)b;

        if(!l.equalsToAxiom(q.l, map))
            return false;

        if(!r.equalsToAxiom(q.r, map))
            return false;

        return true;
    }

}
