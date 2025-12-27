import java.util.Objects;

public class Edge {
    private Point3D a, b;
    private double dist;

    public Edge(Point3D a, Point3D b, double dist) {
        if (a.getId() < b.getId()) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
        this.dist = dist;
    }

    public int getA() {
        return a.getId();
    }

    public int getB() {
        return b.getId();
    }

    public double getDist() {
        return dist;
    }

    public long multiplyX() {
        return (long) a.getX() * b.getX();
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