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

    public boolean equalsExp(Expression b) {
        if(getClass() != b.getClass())
            return false;

        BinaryOperation q = (BinaryOperation)b;

        if(!l.equalsExp(q.l))
            return false;

        if(!r.equalsExp(q.r))
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

    @Override
    public boolean equals(Object o) {
        Expression oo = (Expression) o;
        if(this.equalsExp(oo)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result;
    }
}
