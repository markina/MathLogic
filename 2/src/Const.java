import java.util.Collection;
import java.util.HashMap;
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

}
