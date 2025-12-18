import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part2 {
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

    public static boolean isValid(String[] sequence) {
        int diff = Integer.parseInt(sequence[0]) - Integer.parseInt(sequence[1]);
        boolean isIncreasing = diff < 0;

        if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
            return false;
        }

        for (int i = 1; i < sequence.length - 1; i++) {
            diff = Integer.parseInt(sequence[i]) - Integer.parseInt(sequence[i + 1]);
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3 || (isIncreasing && diff >= 0)
                    || (!isIncreasing && diff <= 0)) {
                return false;
            }
        }
        return true;
    }

    public static int result(String[] line) {
        if (isValid(line)) {
            return 1;
        }
        for (int i = 0; i < line.length; i++) {
            String[] newLine = new String[line.length - 1];
            int index = 0;
            for (int j = 0; j < line.length; j++) {
                if (j != i) {
                    newLine[index++] = line[j];
                }
            }
            if (isValid(newLine)) {
                return 1;
            }
        }

        return 0;
    }
}