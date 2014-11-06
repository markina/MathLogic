/**
 * Created by margarita on 2/3/14.
 ⟨выражение⟩ ::= ⟨дизъюнкция⟩ | ⟨дизъюнкция⟩ ‘->’ ⟨выражение⟩

*/

public class Parser {
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
// ⟨дизъюнкция⟩ ::= ⟨конъюнкция⟩ | ⟨дизъюнкция⟩ ‘|’ ⟨конъюнкция⟩
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
 //⟨конъюнкция⟩ ::= ⟨отрицание⟩ | ⟨конъюнкция⟩ ‘&’ ⟨отрицание⟩
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
                    exp = new And(parseAnd(leftString), parseConst(rightString));
                    return exp;
                }
            }
        }
        exp = parseConst(s);
        return exp;

    }
/*
    ⟨отрицание⟩ ::= (‘A’ . . . ‘Z’) {‘0’ . . . ‘9’} ∗ | ‘!’ ⟨отрицание⟩ | ‘(’ ⟨выражение⟩ ‘)’
            */
    public static Expression parseConst(String s) {
        Expression exp;
        String smallString = new String();
        if(s.charAt(0) == '!') {
            smallString = s.substring(1, s.length());
            exp = new Negative(parseConst(smallString));
            return exp;
        }
        if(s.charAt(0) == '(') {
            smallString = s.substring(1, s.length() -1);
            exp = parseExp(smallString);
            return exp;
        }
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' )) {
                throw new IllegalStateException(" не соответвтвует: ⟨отрицание⟩ ::= (‘A’ . . . ‘Z’) {‘0’ . . . ‘9’} ∗ ");
            }
        }
        exp = new Const(s);
        return exp;
    }

}
