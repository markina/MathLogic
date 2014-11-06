import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by margarita on 2/3/14.
 */

public class Peremennay extends Expression{
    protected String namePeremennay;
    protected List<Expression> listTerms;

    public Peremennay(String namePeremennay, List<Expression> listTerms) {
        this.namePeremennay = namePeremennay;
        this.listTerms = listTerms;
    }

    @Override
    public boolean equals(Expression b) {
        if(getClass() != b.getClass()) {
            return false;
        }

        Peremennay q = (Peremennay) b;
        if(!namePeremennay.equals(q.namePeremennay)) {
            return false;
        }
        if(q.listTerms.size() != listTerms.size()) {
            return false;
        }
        for(int i = 0; i < listTerms.size(); i++) {
            if(!listTerms.get(i).equals(q.listTerms.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        throw new IllegalStateException("вызов equalsToAxiom в Peremennay");
    }

    @Override
    public String toString() {
        String s = namePeremennay;
        if(listTerms.size() == 0) {
            return s;
        }
        s += '(';
        for(int i = 0; i < listTerms.size(); i++) {
            s += listTerms.get(i).toString();
            if(i != listTerms.size() - 1) {
                s += ',';
            }
        }
        s += ')';
        return s;
    }

    @Override
    public void getPeremennes(Set<String> setNamePeremennes) {
        for(int i = 0; i < listTerms.size(); i++) {
            listTerms.get(i).getPeremennes(setNamePeremennes);
        }
        setNamePeremennes.add(namePeremennay);
    }

    @Override
    public boolean canChenge(Peremennay a, Set<String> setNearKvantor) {
        if(!a.namePeremennay.equals(namePeremennay)) {
            return true;
        }
        if(setNearKvantor.contains(namePeremennay)) {
            return true;
        }
        return false;

    }



}
