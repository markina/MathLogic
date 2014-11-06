import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by margarita on 2/3/14.
 */
public class Const extends Expression{
    protected String string;

    public Const(String s) {
        this.string = s;
    }


    public boolean equals(Expression b) {
        if(getClass() != b.getClass())
            return false;

        Const q = (Const)b;

        if(!string.equals(q.string))
            return false;

        return true;
    }

    protected boolean equalsToAxiom(Expression b, Map<String, Expression> map) {
        String c = string;
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
        return string;
    }

    @Override
    public void getAllConst(Map<String, Boolean> map) {
        if(!map.containsKey(string)) {
            map.put(string, false);
        }
    }

    @Override
    public boolean checkEntry(Map<String, Boolean> map) {
        Boolean b = map.get(string);
        return b;
    }

    @Override
    public void createdDevelopment(Map<String, Boolean> map, List<String> developmentWithPropositions) {
        if(map.get(string)) {
            developmentWithPropositions.add(string);
        } else {
            developmentWithPropositions.add("!" + string);
        }
    }


}
