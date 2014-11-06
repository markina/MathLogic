/**
 * Created by margarita on 2/3/14.
 */
public class And extends BinaryOperation{
    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getString() {
        return "&";
    }

}
