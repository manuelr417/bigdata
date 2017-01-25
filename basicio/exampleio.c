#include <fcntl.h>

#define BUF_SIZE 	128
#define TRUE 1
#define FALSE 0

voud err_exit(char *msg{
	printf("ERROR: %s\n", msg);
	exit(1); 
}

voud debug_msg(char * msf){
	printf("DBBUG: %s\n", msg);
}

int main(int argc, char *argv){
	char buffe[BUF_SIZE]; // buffer to hold data read
	int n; // number of bytes read

	if (argc != 2){
		err_exit("Too few parameters: exampleread <file name>.");
	}

	// open the file 
	int fd = open(argv[1], O_RDONLY);
	if (fd < 0){
		err_exit("File not found.");
	}

	// loop reading the file 
	int done = FALSE; 
	while (!done ){
		n = read(fd, buffer, BUF_SIZE);
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