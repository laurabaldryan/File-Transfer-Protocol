# File-Transfer-Protocol
File transfer protocol
This project implements a File Transfer Protocol (FTP) using Java. The protocol allows a client to connect to a server and perform file operations such as listing files, uploading files, downloading files, and removing files. The client remains connected until the QUIT command is issued.

## Features

LIST: List all files stored on the server.

UPLOAD <filename>: Upload a file from the client to the server.

DOWNLOAD <filename>: Download a file from the server to the client.

REMOVE <filename>: Remove a file from the server.

QUIT: Disconnect the client from the server.

## Project Structure 

FTPProject/

│

├── FTPServer.java        # Server-side implementation

├── FTPClient.java         # Client-side implementation

├── server_files/             # Directory for downloading 

└── test*.txt                     # Files for uploading 


## Steps to Run:
1. Compile the Code
javac FTPServer.java FTPClient.java
2. Start the Server
Run the server first:
java FTPServer
3.Run the client to connect to the server:
java FTPClient

## Example:
Enter command: UPLOAD example.txt

Enter command: LIST

Enter command: DOWNLOAD example.txt

Enter command: REMOVE example.txt

Enter command: QUIT

## Example Usage
### Server Console:
Server is listening on port 12345

New client connected

Received command: LIST

Received command: UPLOAD test1.txt

Received command: QUIT

Client disconnected.

### Client Console:
=====================================================
FTP PROTOCOL INSTRUCTIONS:

Use the following commands to interact with the server:
1. LIST           - Lists all hosted files on the server.
2. UPLOAD <file>  - Uploads the specified file to the server.
3. DOWNLOAD <file> - Downloads the specified file from the server.
4. REMOVE <file>  - Removes the specified file from the server.
5. QUIT           - Disconnects from the server.

=====================================================

Connected to the server. Type 'QUIT' to disconnect.

Enter command: LIST

example.txt

Enter command: UPLOAD test1.txt

File test1.txt uploaded successfully.

Enter command: QUIT

Disconnected from server.










