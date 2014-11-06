/**
 * Created by margarita on 2/3/14.
 */
public class Consecution extends BinaryOperation{
    public Consecution(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getString() {
        return "->";
    }
}
