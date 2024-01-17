// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

/**
 * A generic pair class.
 * @param <A> The type of the first element.
 * @param <B> The type of the second element.
 */
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

}