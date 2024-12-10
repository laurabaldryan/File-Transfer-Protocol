import java.io.*;
import java.net.*;

public class FTPServer {
    private static final int PORT = 12345;
    private static final String DIRECTORY = "server_files";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdir(); 
            }

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(clientSocket, DIRECTORY).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private String directory;

    public ClientHandler(Socket socket, String directory) {
        this.socket = socket;
        this.directory = directory;
    }

    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String command;
            while ((command = reader.readLine()) != null) {
                System.out.println("Received command: " + command);
                if (command.equalsIgnoreCase("QUIT")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                String[] tokens = command.split(" ", 2);
                String operation = tokens[0];

                switch (operation.toUpperCase()) {
                    case "LIST":
                        listFiles(writer);
                        break;
                    case "UPLOAD":
                        uploadFile(reader, tokens[1], writer);
                        break;
                    case "REMOVE":
                        removeFile(writer, tokens[1]);
                        break;
                    case "DOWNLOAD":
                        downloadFile(tokens[1], writer);
                        break;
                    default:
                        writer.println("Unknown command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listFiles(PrintWriter writer) {
        File folder = new File(directory);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                writer.println(file.getName());
            }
        }
        writer.println("END");
    }

    private void uploadFile(BufferedReader reader, String fileName, PrintWriter writer) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(directory + "/" + fileName))) {
            String line;
            while (!(line = reader.readLine()).equals("EOF")) {
                fileWriter.write(line + "\n");
            }
            writer.println("File " + fileName + " uploaded successfully.");
        } catch (IOException e) {
            writer.println("Error: Failed to upload the file.");
        }
    }

    private void removeFile(PrintWriter writer, String fileName) {
        File file = new File(directory + "/" + fileName);
        if (file.delete()) {
            writer.println("File " + fileName + " removed successfully.");
        } else {
            writer.println("Error: Failed to delete file " + fileName);
        }
    }

    private void downloadFile(String fileName, PrintWriter writer) {
        File file = new File(directory + "/" + fileName);
        if (!file.exists()) {
            writer.println("Error: File not found.");
            return;
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.println(line);
            }
            writer.println("EOF");
        } catch (IOException e) {
            writer.println("Error: Failed to download the file.");
        }
    }
}
