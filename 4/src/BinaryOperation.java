import java.util.*;

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

        ////////////////////////////////////////////////////////////////начало разбора для Any
        if(getClass() == Consecution.class && l.getClass() == Any.class && q.l.getClass() == Any.class) {
            Any leftQ = (Any)q.l;
            Expression rightQ = q.r;
            String x = leftQ.x.namePeremennay;
            Set<String> setLeftQ = new HashSet<String>();
            leftQ.e.getPeremennes(setLeftQ);
            Set<String> setRightQ = new HashSet<String>();
            rightQ.getPeremennes(setRightQ);
            if(!setLeftQ.contains(x)) {
                Set<String> setLeftQFikt = new HashSet<String>(setLeftQ);
                for(String s: setLeftQFikt) {
                    if(setRightQ.contains(s) && setLeftQ.contains(s)) {
                        setLeftQ.remove(s);
                        setRightQ.remove(s);
                    } else {
                        if(!s.equals(x)) {
                            return false;
                        }
                    }
                }
                if(setRightQ.size() == 0 && setLeftQ.size() == 0) {
                    if(leftQ.e.equals(rightQ)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(setLeftQ.contains(x)) {
                Set<String> setLeftQFikt = new HashSet<String>(setLeftQ);
                for(String s: setLeftQFikt) {
                    if(setRightQ.contains(s) && setLeftQ.contains(s)) {
                        setLeftQ.remove(s);
                        setRightQ.remove(s);
                    } else {
                        if(!s.equals(x)) {
                            return false;
                        }
                    }
                }
                if(setRightQ.size() == 0 && setLeftQ.size() == 0) {
                    if(leftQ.e.equals(rightQ)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if(setRightQ.size() == 1 && setLeftQ.size() == 1) {
                    Expression newLeftQ = null;
                    for(String s : setRightQ) {
                        newLeftQ = getExp(leftQ.e, x, s);
                    }
                    if(newLeftQ.equals(rightQ)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }

        if(getClass() == Consecution.class && l.getClass() == Any.class) {
            return false;
        }
        ////////////////////////////////////////////////////////////////конец разбора для Any

        ////////////////////////////////////////////////////////////////начало разбора для Exist
        if(getClass() == Consecution.class && r.getClass() == Exist.class && q.r.getClass() == Exist.class) {
            Exist rightQQ = (Exist)q.r;
            Expression leftQQ = q.l;
            String x = rightQQ.x.namePeremennay;
            Set<String> setRightQQ = new HashSet<String>();
            rightQQ.e.getPeremennes(setRightQQ);
            Set<String> setLeftQQ = new HashSet<String>();
            leftQQ.getPeremennes(setLeftQQ);
            if(!setRightQQ.contains(x)) {
                Set<String> setLeftQFikt = new HashSet<String>(setRightQQ);
                for(String s: setLeftQFikt) {
                    if(setRightQQ.contains(s) && setLeftQQ.contains(s)) {
                        setLeftQQ.remove(s);
                        setRightQQ.remove(s);
                    } else {
                        if(!s.equals(x)) {
                            return false;
                        }
                    }
                }
                if(setLeftQQ.size() == 0 && setRightQQ.size() == 0) {
                    if(rightQQ.e.equals(leftQQ)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(setRightQQ.contains(x)) {
                Set<String> setLeftQFikt = new HashSet<String>(setRightQQ);
                for(String s: setLeftQFikt) {
                    if(setRightQQ.contains(s) && setLeftQQ.contains(s)) {
                        setLeftQQ.remove(s);
                        setRightQQ.remove(s);
                    } else {
                        if(!s.equals(x)) {
                            return false;
                        }
                    }
                }
                if(setRightQQ.size() == 0 && setLeftQQ.size() == 0) {
                    if(rightQQ.e.equals(leftQQ)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if(setLeftQQ.size() == 1 && setRightQQ.size() == 1) {
                    Expression newRightQQ = null;
                    for(String s : setLeftQQ) {//////////
                        newRightQQ = getExp(rightQQ.e, x, s);
                    }
                    if(newRightQQ.equals(leftQQ)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }

        if(getClass() == Consecution.class && r.getClass() == Exist.class) {
            return false;
        }
        ////////////////////////////////////////////////////////////////конец разбора для Exist



        if(!l.equalsToAxiom(q.l, map))
            return false;

        if(!r.equalsToAxiom(q.r, map))
            return false;

        return true;
    }

    public Expression getExp(Expression exp, String x, String s) {
        String str = exp.toString();
        String newStr = new String();
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == x.charAt(0)) {
                newStr += s.charAt(0);
                continue;
            }
            newStr += str.charAt(i);
        }
        return Parser.parseExp(newStr);
    }


    @Override
    public void getPeremennes(Set<String> setNamePeremennes) {
        l.getPeremennes(setNamePeremennes);
        r.getPeremennes(setNamePeremennes);
    }

    @Override
    public boolean canChenge(Peremennay a, Set<String> setNearKvantor) {
        return l.canChenge(a, setNearKvantor) && r.canChenge(a, setNearKvantor);
    }


}























