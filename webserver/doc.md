**Socket**
# WebServer

A socket is an abstraction of a communication endpoint and it allows your program to communicate with another program using file descriptors. In this article I’ll be talking specifically about TCP/IP sockets on Linux/Mac OS X. An important notion to understand is the TCP socket pair.

The socket pair for a TCP connection is a 4-tuple that identifies two endpoints of the TCP connection: the local IP address, local port, foreign IP address, and foreign port. A socket pair uniquely identifies every TCP connection on a network. The two values that identify each endpoint, an IP address and a port number, are often called a socket.1

1. The server creates a TCP/IP socket. This is done with the following statement in Python:

*listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)*

2. The server might set some socket options (this is optional, but you can see that the server code above does just that to be able to re-use the same address over and over again if you decide to kill and re-start the server right away).

*listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)*

3. Then, the server binds the address. The bind function assigns a local protocol address to the socket. With TCP, calling bind lets you specify a port number, an IP address, both, or neither.1

*listen_socket.bind(SERVER_ADDRESS)*

4. Then, the server makes the socket a listening socket

*listen_socket.listen(REQUEST_QUEUE_SIZE)*

The TCP/IP stack within the kernel automatically assigns the local IP address and the local port when the client calls connect. The local port is called an ephemeral port, i.e. a short-lived port.

A port on a server that identifies a well-known service that a client connects to is called a well-known port (for example, 80 for HTTP and 22 for SSH)

**Process**

What is a process? A process is just an instance of an executing program. When the server code is executed, for example, it’s loaded into memory and an instance of that executing program is called a process. The kernel records a bunch of information about the process - its process ID would be one example - to keep track of it. When you run your iterative server webserver3a.py or webserver3b.py you run just one process.