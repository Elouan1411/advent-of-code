import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {
    public static void main(String[] args) {
        String path = "unusual_data.txt";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(path))) {
            String line;
            int cpt = 0;

            while ((line = lecteur.readLine()) != null) {
                // Séparer la ligne en fonction des espaces
                String[] nb = line.split(" ");
                cpt += result(nb);
            }
            System.out.println(String.valueOf(cpt));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int result(String[] line) {
        if (line.length == 1) {
            return 1;
        }

        int diff = Integer.parseInt(line[0]) - Integer.parseInt(line[1]);

        if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
            return 0;
        }

        boolean isIncreasing = diff < 0;

        for (int i = 1; i < line.length - 1; i++) {
            diff = Integer.parseInt(line[i]) - Integer.parseInt(line[i + 1]);

            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return 0;
            }
            if ((isIncreasing && diff >= 0) || (!isIncreasing && diff <= 0)) {
                return 0;
            }
        }

        return 1;
    }
}
