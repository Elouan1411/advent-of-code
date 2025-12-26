import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Part1 {

    public static void main(String[] args) {
        boolean easy = false;

        String filePath;
        int nbPairs;
        int optimization_scale;
        if (!easy) {
            filePath = "input.txt";
            nbPairs = 1000;
            optimization_scale = 40; // it's the minimum for get the good result
        } else {
            filePath = "input-easy.txt";
            nbPairs = 10;
            optimization_scale = 2;
        }

        List<Point3D> points = readFile(filePath);
        List<Edge> edges = filter(points, optimization_scale);
        List<Edge> selected_edges = selectEdges(edges, nbPairs);
        int[] groups = doGroup(selected_edges, points);
        long res = computeRes(groups);

        System.out.println("Answer : " + res);
    }

    public static List<Edge> selectEdges(List<Edge> edges, int nbPairs) {
        edges.sort(Comparator.comparingDouble(Edge::getDist));
        return edges.subList(0, nbPairs);

    }

    public static int[] doGroup(List<Edge> edges, List<Point3D> points) {
        UnionFind uf = new UnionFind(points.size());

        for (Edge edge : edges) {
            uf.union(edge.getA(), edge.getB());
        }

        return uf.getAllSize();
    }

    public static long computeRes(int[] allGroups) {
        Arrays.sort(allGroups);

        long res = 1;
        int count = 0;

        for (int i = allGroups.length - 1; i >= 0; i--) {
            int size = allGroups[i];
            res *= size;
            count++;

            if (count == 3) {
                break;
            }
        }

        return res;
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
                candidatesSet.add(new Edge(p1.getId(), p2.getId(), p1.distanceTo(p2)));
            }
        }

        // With y
        points.sort(Comparator.comparingInt(p -> p.getY()));
        for (int i = 0; i < points.size(); i++) {
            int fin = Math.min(points.size() - 1, optimization_scale + i);
            Point3D p1 = points.get(i);
            for (int j = i + 1; j <= fin; j++) {
                Point3D p2 = points.get(j);
                candidatesSet.add(new Edge(p1.getId(), p2.getId(), p1.distanceTo(p2)));
            }
        }

        // With z
        points.sort(Comparator.comparingInt(p -> p.getZ()));
        for (int i = 0; i < points.size(); i++) {
            int fin = Math.min(points.size() - 1, optimization_scale + i);
            Point3D p1 = points.get(i);
            for (int j = i + 1; j <= fin; j++) {
                Point3D p2 = points.get(j);
                candidatesSet.add(new Edge(p1.getId(), p2.getId(), p1.distanceTo(p2)));
            }
        }

        return new ArrayList<>(candidatesSet);
    }

}