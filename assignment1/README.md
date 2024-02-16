# Homework1: Stripping Non-ASCII characters

## System Calls Description

> Linux System Call Table
> https://chromium.googlesource.com/chromiumos/docs/+/master/constants/syscalls.md

When running on a Linux system, the Java program `sna` relies on several system calls underneath, especially for file operations and I/O interactions. Key system calls involved include:

- **`open`**: To open files specified by the user for reading (input file) and writing (output file).
- **`read`**: To read the content of the input file into the program.
- **`write`**: To write the processed (filtered) content into the output file.
- **`close`**: To close the file descriptors once the reading and writing operations are completed.

Additionally, the program uses **`brk`** or **`mmap`** for memory allocation, which is managed by the JVM for objects and internal buffers. The **`ioctl`** system call might be used for terminal I/O operations, such as reading input from the console.

Error handling and checking for ASCII values are implemented within the program logic, and the JVM abstracts many of the lower-level details of system interaction, especially related to memory management and execution control.
