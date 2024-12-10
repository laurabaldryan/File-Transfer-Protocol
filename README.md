# File-Transfer-Protocol
File transfer protocol
This project implements a File Transfer Protocol (FTP) using Java. The protocol allows a client to connect to a server and perform file operations such as listing files, uploading files, downloading files, and removing files. The client remains connected until the QUIT command is issued.
Features
LIST: List all files stored on the server.
UPLOAD <filename>: Upload a file from the client to the server.
DOWNLOAD <filename>: Download a file from the server to the client.
REMOVE <filename>: Remove a file from the server.
QUIT: Disconnect the client from the server.
Project Structure 

FTPProject/
│
├── FTPServer.java        # Server-side implementation
├── FTPClient.java         # Client-side implementation
├── server_files/             # Directory for downloading 
└── test*.txt                     # Files for uploading 

