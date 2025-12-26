import java.util.Objects;

public class Edge {
    private int a, b;
    private double dist;

    public Edge(int a, int b, double dist) {
        if (a < b) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
        this.dist = dist;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public double getDist() {
        return dist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Edge edge = (Edge) o;
        return a == edge.a && b == edge.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}