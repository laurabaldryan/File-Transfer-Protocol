import java.io.*;
import java.net.*;

public class FTPClient {
    private static final String SERVER = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER, PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ) {
            printInstructions();

            System.out.println("Connected to the server. Type 'QUIT' to disconnect.");

            String command;
            while (true) {
                System.out.print("Enter command: ");
                command = console.readLine();
                writer.println(command);

                if (command.equalsIgnoreCase("QUIT")) {
                    System.out.println("Disconnected from server.");
                    break;
                }

                String[] tokens = command.split(" ", 2);
                String operation = tokens[0];

                switch (operation.toUpperCase()) {
                    case "LIST":
                        String response;
                        while (!(response = reader.readLine()).equals("END")) {
                            System.out.println(response);
                        }
                        break;

                    case "UPLOAD":
                        if (tokens.length < 2) {
                            System.out.println("Usage: UPLOAD <filename>");
                            break;
                        }
                        uploadFile(writer, tokens[1]);
                        System.out.println(reader.readLine());
                        break;

                    case "REMOVE":
                        if (tokens.length < 2) {
                            System.out.println("Usage: REMOVE <filename>");
                            break;
                        }
                        System.out.println(reader.readLine());
                        break;

                    case "DOWNLOAD":
                        if (tokens.length < 2) {
                            System.out.println("Usage: DOWNLOAD <filename>");
                            break;
                        }
                        downloadFile(reader, tokens[1]);
                        break;

                    default:
                        System.out.println("Invalid command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printInstructions() {
        System.out.println("=====================================================");
        System.out.println("FTP PROTOCOL INSTRUCTIONS:");
        System.out.println("Use the following commands to interact with the server:");
        System.out.println("1. LIST           - Lists all hosted files on the server.");
        System.out.println("2. UPLOAD <file>  - Uploads the specified file to the server.");
        System.out.println("3. DOWNLOAD <file> - Downloads the specified file from the server.");
        System.out.println("4. REMOVE <file>  - Removes the specified file from the server.");
        System.out.println("5. QUIT           - Disconnects from the server.");
        System.out.println("=====================================================");
    }

    private static void uploadFile(PrintWriter writer, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.println(line);
            }
            writer.println("EOF");
        } catch (IOException e) {
            System.out.println("Error: File not found.");
        }
    }

    private static void downloadFile(BufferedReader reader, String fileName) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {
            String line;
            while (!(line = reader.readLine()).equals("EOF")) {
                fileWriter.write(line);
                fileWriter.newLine();
            }
            System.out.println("File downloaded successfully.");
        } catch (IOException e) {
            System.out.println("Error: Failed to download file.");
        }
    }
}
