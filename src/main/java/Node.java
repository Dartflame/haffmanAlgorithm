import java.util.Objects;

class Node  implements Comparable{

    protected char letter;
    protected int repeat;
    protected String position;

    protected Node left;
    protected Node right;
    protected Node parent;

    public Node() {
    }

    public Node(char letter) {
        this.letter = letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        if (letter == 0 && node.letter == 0)
            return false;
        return letter == node.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter);
    }

    @Override
    public String toString() {
        return "Node{" +
                "letter=" + letter +
                ", repeat=" + repeat +
                ", position='" + position + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node) o;
        return repeat - node.repeat;
    }
}