import java.util.HashSet;
import java.util.Set;

/**
 * Created by margarita on 4/7/14.
 */
public class Node {
    protected Set<Node> next;
    Set<Expression> setTrue;

    public Node(Set<Expression> setTrue) {
        this.setTrue = setTrue;
        this.next = new HashSet<Node>();
    }

    public Node(Node node) {
        this.next = node.next;
        this.setTrue = node.setTrue;
    }

    public void insertChild(Node child) {
        if(!child.equals(this)) {
            for(Node ch: this.next) {
                ch.insertChild(child);
            }
            if(child.setTrue.containsAll(this.setTrue)) {
                this.next.add(new Node(child));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        Node oo = (Node) o;
        if(setTrue.containsAll(oo.setTrue) && oo.setTrue.containsAll(setTrue)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return setTrue.toString();
    }

    public boolean force(Expression exp) {
        if(exp.getClass() == Variable.class) {
            return setTrue.contains(exp);
        }
        if(exp.getClass() == Or.class) {
            Or expOr = (Or) exp;
            return force(expOr.l) || force(expOr.r);
        }
        if(exp.getClass() == And.class) {
            And expAnd = (And) exp;
            return force(expAnd.l) && force(expAnd.r);

        }
        if(exp.getClass() == Negative.class) {
            Negative expNegative = (Negative) exp;
            if(force(expNegative.e)) {
                return false;
            }
            for(Node child: next) {
                if(!child.force(exp)) {
                    return false;
                }
            }
            return true;
        }
        if(exp.getClass() == Implication.class) {
            Implication expCon = (Implication) exp;
            if(force(expCon.l) && !force(expCon.r)) {
                return false;
            }
            for(Node child: next) {
                if(!child.force(exp)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
