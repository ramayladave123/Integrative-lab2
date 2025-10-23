import java.util.Scanner;

public class Integrative2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter media source type (local, hls, remote): ");
        String sourceType = scanner.nextLine().trim().toLowerCase();

        // Placeholder for media source creation
        System.out.println("Source type selected: " + sourceType);
    }
}