import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Part2 {

    public static void main(String[] args) {
        boolean easy = false;

        String filePath;
        int optimization_scale;
        if (!easy) {
            filePath = "input.txt";
            optimization_scale = 65; // it's the minimum for get the good result
        } else {
            filePath = "input-easy.txt";
            optimization_scale = 3;
        }

        List<Point3D> points = readFile(filePath);
        List<Edge> edges = filter(points, optimization_scale);
        edges.sort(Comparator.comparingDouble(Edge::getDist));
        Edge lastEdge = doGroup(edges, points);
        if (lastEdge == null) {
            System.out.println("increase optimization_scale for have result...");
            return;
        }
        long res = computeRes(lastEdge);

        System.out.println("Answer : " + res);
    }

    public static Edge doGroup(List<Edge> edges, List<Point3D> points) {
        UnionFind uf = new UnionFind(points.size());

        for (Edge edge : edges) {
            uf.union(edge.getA(), edge.getB());
            if (uf.isAllConnected()) {
                return edge;
            }

        }
        return null;
    }

    public static long computeRes(Edge lastEdge) {
        return lastEdge.multiplyX();
    }

    /**
     * Read file and return list of Point3D
     */
    public static List<Point3D> readFile(String filePath) {
        List<Point3D> points = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            int idCounter = 0;

            for (String line : lines) {
                if (line.trim().isEmpty())
                    continue; // if empty line

                String[] parts = line.split(",");

                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                int z = Integer.parseInt(parts[2].trim());

                points.add(new Point3D(idCounter, x, y, z));

                idCounter++;
            }

        } catch (IOException e) {
            System.err.println("Erreur lecture fichier : " + e.getMessage());
        }

        return points;
    }

    public static List<Edge> filter(List<Point3D> points, int optimization_scale) {
        Set<Edge> candidatesSet = new HashSet<>();

        // With x
        points.sort(Comparator.comparingInt(p -> p.getX()));
        for (int i = 0; i < points.size(); i++) {
            int fin = Math.min(points.size() - 1, optimization_scale + i);
            Point3D p1 = points.get(i);
            for (int j = i + 1; j <= fin; j++) {
                Point3D p2 = points.get(j);
                candidatesSet.add(new Edge(p1, p2, p1.distanceTo(p2)));
            }
        }

        // With y
        points.sort(Comparator.comparingInt(p -> p.getY()));
        for (int i = 0; i < points.size(); i++) {
            int fin = Math.min(points.size() - 1, optimization_scale + i);
            Point3D p1 = points.get(i);
            for (int j = i + 1; j <= fin; j++) {
                Point3D p2 = points.get(j);
                candidatesSet.add(new Edge(p1, p2, p1.distanceTo(p2)));
            }
        }

        // With z
        points.sort(Comparator.comparingInt(p -> p.getZ()));
        for (int i = 0; i < points.size(); i++) {
            int fin = Math.min(points.size() - 1, optimization_scale + i);
            Point3D p1 = points.get(i);
            for (int j = i + 1; j <= fin; j++) {
                Point3D p2 = points.get(j);
                candidatesSet.add(new Edge(p1, p2, p1.distanceTo(p2)));
            }
        }

        return new ArrayList<>(candidatesSet);
    }

}