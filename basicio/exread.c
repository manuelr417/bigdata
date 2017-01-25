#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <strings.h>

#define BUF_SIZE 	128
#define TRUE 1
#define FALSE 0

void err_exit(char *msg){
	printf("ERROR: %s\n", msg);
	exit(1); 
}

void debug_msg(char *msg){
	printf("DEBUG: %s\n", msg);
}

int main(int argc, char *argv[]){
	char str[BUF_SIZE]; // string for messages
	char buffer[BUF_SIZE]; // buffer to hold data read
	int n; // number of bytes read

	if (argc != 2){
		err_exit("Too few parameters: exread <file name>.");
	}

	// initialiaze to 0
	bzero(str, BUF_SIZE);
	sprintf(str, "Reading file: %s.", argv[1]);
	debug_msg(str);


	//printf("Argv[1]: %s\n", argv[1]);
	// open the file 
	int fd = open(argv[1], O_RDONLY);
	if (fd < 0){
		err_exit("File not found.");
	}

	// loop reading the file 
	int done = FALSE; 
	while (!done ){
		bzero(buffer, BUF_SIZE);
		n = read(fd, &buffer, BUF_SIZE);
		if (n == 0){
			done = TRUE; // end of file
		}
		else if (n < 0){
			err_exit("File Read Error.");
		}
		else {
			printf("%s", buffer);
		}
	}
	// close file
	debug_msg("File read!");
	exit(0);
}