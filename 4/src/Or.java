import java.util.Set;

/**
 * Created by margarita on 2/3/14.
 */
public class Or extends BinaryOperation {
    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getString() {
        return "|";
    }


}
