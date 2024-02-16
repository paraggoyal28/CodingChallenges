import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * A class implementing UNIX "wc" command line tool
 * It supports both filepath and standard inout options
 * Supported options:
 * "-l": returns number of lines in a file or input
 * "-c": returns number of bytes in a file or input
 * "-w": returns number of words in a file or input
 * "-m": returns number of characters in a file or input
 * if no option is provided, returns "line count" "word count" "byte count" in same order
 */
public class ccwc {
    /**
     * A utility function which prints output for a specific given option
     *
     * @param input
     * @param flag
     */
    public static void executeForStandardInput(String input, String flag) {
        switch (flag) {
            case "-l" -> System.out.println(input.split("\n").length);
            case "-c" -> System.out.println(input.getBytes().length);
            case "-w" -> System.out.println(input.split("\\s").length);
            case "-m" -> System.out.println(input.length());
            default ->
                    System.out.println(input.split("\n").length + " " + input.split("\\s+").length + " " + input.getBytes().length);
        }
    }

    /**
     * A utility function which prints output for given option
     *
     * @param filePath
     * @param flag
     * @throws IOException
     */
    public static void executeForInputFile(Path filePath, String flag) throws IOException {
        switch (flag) {
            case "-l" -> System.out.println(Files.readAllLines(filePath).size() + " " + filePath);
            case "-c" -> System.out.println(Files.readAllBytes(filePath).length + " " + filePath);
            case "-w" -> System.out.println(Files.readString(filePath).split("\\s+").length + " " + filePath);
            case "-m" -> System.out.println(Files.readString(filePath).length() + " " + filePath);
            default ->
                    System.out.println(Files.readAllLines(filePath).size() + " " + Files.readString(filePath).split("\\s+").length + " " + Files.readAllBytes(filePath).length + " " + filePath);
        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length == 0) { //when only std input is present
            Scanner scanner = new Scanner(System.in);
            String input = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            executeForStandardInput(input, "");
        } else if (args.length == 1) {
            String argValue = args[0];
            Path filePath = Paths.get(argValue); //checking if the args is a file path or an input string

            if (!Files.exists(filePath)) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                executeForStandardInput(input, argValue);
            } else {
                executeForInputFile(filePath, "");
            }
        } else if (args.length == 2) {
            Path filePath = Paths.get(args[1]);
            executeForInputFile(filePath, args[0]);
        }
    }
}