import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by margarita on 3/23/14.
 */

public class Predikat extends Expression{
    protected String namePredikat;
    protected List<Expression> listTerms;

    public Predikat(String namePredikat, List<Expression> listTerms) {
        this.namePredikat = namePredikat;
        this.listTerms = listTerms;
    }

    @Override
    public boolean equals(Expression b) {
        if(getClass() != b.getClass()) {
            return false;
        }

        Predikat q = (Predikat) b;
        if(!namePredikat.equals(q.namePredikat)) {
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
        if(listTerms.size() != 0) {
            throw new IllegalStateException("вызов equalsToAxiom в Predikat с не нулевум listTrems");
        }
        String c = namePredikat;
        if(map.containsKey(c)) {
            if(!(map.get(c).equals(b))) {
                return false;
            }
        } else {
            map.put(c, b);
        }
        return true;
    }

    @Override
    public String toString() {
        String s = namePredikat;
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
        for(Expression exp: listTerms) {
            exp.getPeremennes(setNamePeremennes);
        }
    }

    @Override
    public boolean canChenge(Peremennay a, Set<String> setNearKvantor) {
        for(int i = 0; i < listTerms.size(); i++) {
            if(!listTerms.get(i).canChenge(a, setNearKvantor)) {
                return false;
            }
        }
        return true;
    }
}
