# Maze Compression and Search Problem Solver

This project implements a client-server application for generating mazes, compressing and decompressing them, and solving search problems within mazes. It includes functionalities for testing these operations.

## Tech Stack

- Java
- Custom compression/decompression streams (`MyCompressorOutputStream`, `MyDecompressorInputStream`)
- Custom maze generation algorithms (`MyMazeGenerator`)
- Custom search algorithms (`AState`, `Solution`)

## Features

- **Maze Generation**: Generates mazes of specified dimensions.
- **Maze Compression/Decompression**: Compresses generated mazes and decompresses them back to their original form, with a focus on compression efficiency.
- **Client-Server Communication**: Establishes communication between clients and servers for:
    - Requesting maze generation from a server.
    - Sending mazes to a server for solving search problems and receiving solutions.
- **Automated Testing**: Includes test cases for:
    - Verifying the integrity of compressed and decompressed mazes.
    - Communicating with maze generation and search problem-solving servers.

## Future Work/Improvements (optional)

- Develop a graphical user interface (GUI) for better interaction and visualization of mazes and solutions.
- Add more comprehensive unit and integration tests.
