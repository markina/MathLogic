import java.util.ArrayList;
import java.util.List;

/**
 * Created by margarita on 2/3/14.
*/

public class Parser {
    // ⟨выражение⟩ ::= ⟨дизъюнкция⟩ | ⟨дизъюнкция⟩ ‘->’ ⟨выражение⟩
    public static Expression parseExp(String s) {
        Expression exp;
        int bal = 0;
        String leftString = new String();
        String rightString = new String();
        for(int i = 0;i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                bal++;
                continue;
            }
            if(s.charAt(i) == ')') {
                bal--;
                continue;
            }
            if(bal == 0) {
                if(s.charAt(i) == '-') {
                    leftString = s.substring(0, i);
                    rightString = s.substring(i+2, s.length());
                    exp = new Consecution(parseOr(leftString), parseExp(rightString));
                    return exp;
                }
            }
        }
        exp = parseOr(s);
        return exp;
    }

    //⟨дизъюнкция⟩ ::= ⟨конъюнкция⟩ | ⟨дизъюнкция⟩ ‘|’ ⟨конъюнкция⟩
    public static Expression parseOr(String s) {
        Expression exp;
        int bal = 0;
        String leftString = new String();
        String rightString = new String();
        for(int i = s.length() - 1 ;i >= 0; i--) {
            if(s.charAt(i) == ')') {
                bal++;
                continue;
            }
            if(s.charAt(i) == '(') {
                bal--;
                continue;
            }
            if(bal == 0) {
                if(s.charAt(i) == '|') {
                    leftString = s.substring(0, i);
                    rightString = s.substring(i+1, s.length());
                    exp = new Or(parseOr(leftString), parseAnd(rightString));
                    return exp;
                }
            }
        }
        exp = parseAnd(s);
        return exp;
    }

    //⟨конъюнкция⟩ ::= ⟨унарное⟩ | ⟨конъюнкция⟩ ‘&’ ⟨унарное⟩
    public static Expression parseAnd(String s) {
        Expression exp;
        int bal = 0;
        String leftString = new String();
        String rightString = new String();
        for(int i = s.length() - 1 ;i >= 0; i--) {
            if(s.charAt(i) == ')') {
                bal++;
                continue;
            }
            if(s.charAt(i) == '(') {
                bal--;
                continue;
            }
            if(bal == 0) {
                if(s.charAt(i) == '&') {
                    leftString = s.substring(0, i);
                    rightString = s.substring(i+1, s.length());
                    exp = new And(parseAnd(leftString), parseUnary(rightString));
                    return exp;
                }
            }
        }
        exp = parseUnary(s);
        return exp;
    }

    //⟨унарное⟩ ::= ⟨предикат⟩ | ‘!’ ⟨унарное⟩ | ‘(’ ⟨выражение⟩ ‘)’ | (‘@’ | ‘?’) ⟨переменная⟩ ⟨унарное⟩
    //⟨переменная⟩ ::= (‘a’...‘z’) {‘0’...‘9’} ∗  [‘(’⟨терм⟩ {‘,’ ⟨терм⟩} ∗ ‘)’]
    //⟨предикат⟩ ::= (‘A’ . . . ‘Z’) {‘0’ . . . ‘9’} ∗ [‘(’⟨терм⟩ {‘,’ ⟨терм⟩} ∗ ‘)’]
    public static Expression parseUnary(String s) {
        Expression exp;
        String smallString = new String();
        if(s.charAt(0) == '!') {
            smallString = s.substring(1, s.length());
            exp = new Negative(parseUnary(smallString));
            return exp;
        }
        if(s.charAt(0) == '@') {
            smallString = s.substring(2, s.length());
            exp = new Any(new Peremennay(s.substring(1, 2), new ArrayList<Expression>()), parseUnary(smallString));
            return exp;
        }
        if(s.charAt(0) == '?') {
            smallString = s.substring(2, s.length());
            exp = new Exist(new Peremennay(s.substring(1, 2), new ArrayList<Expression>()), parseUnary(smallString));
            return exp;
        }

        if(s.charAt(0) == '(') {
            smallString = s.substring(1, s.length() -1);
            exp = parseExp(smallString);
            return exp;
        }
        List<Expression> listExp = new ArrayList<Expression>();
        String namePredikat = new String();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != '(') {
                namePredikat += s.charAt(i);
            } else {
                listExp = getlistExp (s.substring(i+1, s.length()-1));
                break;
            }
        }
        exp = new Predikat(namePredikat, listExp);

        return exp;
    }

    private static List<Expression> getlistExp(String s) {
        List<Expression> listExp = new ArrayList<Expression>();
        String smallString = new String();
        int bal = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                bal++;
                smallString += s.charAt(i);
                continue;
            }
            if(s.charAt(i) == ')') {
                bal--;
                smallString += s.charAt(i);
                continue;
            }
            if(bal == 0) {
                if(s.charAt(i) == ',') {
                    listExp.add(parseTerm(smallString));
                    smallString = new String();
                    continue;
                }
            }
            smallString += s.charAt(i);

        }
        listExp.add(parseTerm(smallString));
        return listExp;
    }

    // ⟨терм⟩ ::= ⟨переменная⟩ | ‘(’ ⟨терм⟩ ‘)’
    private static Expression parseTerm(String s) {
        if(s.charAt(0) == '(') {
            return parseTerm(s.substring(1, s.length()-1));
        }
        List<Expression> listExp = new ArrayList<Expression>();
        String namePeremennay = new String();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != '(') {
                namePeremennay += s.charAt(i);
            } else {
                listExp = getlistExp (s.substring(i+1, s.length()-1));
                break;
            }
        }
        return new Peremennay(namePeremennay, listExp);
    }
}
