/* File Name: server.c */
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<errno.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#define DEFAULT_PORT 8000
#define MAXLINE      4096


int main(int argc, char** argv)
{
    int    socket_fd, connect_fd;
    struct sockaddr_in     servaddr;
    char   buff[MAXLINE];
    int    n;

    //create Socket
    if( (socket_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1 ){
		printf("create socket error: %s(errno: %d)\n",strerror(errno),errno);
		exit(0);
    }

    //init
    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
	/* init IP addr with INADDR_ANY for system auto get local host IP addr */
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	/* set port to DEFAULT_PORT */
    servaddr.sin_port = htons(DEFAULT_PORT);

	/* bind local addr to new created socket */
    if( bind(socket_fd, (struct sockaddr*)&servaddr, sizeof(servaddr)) == -1){
		printf("bind socket error: %s(errno: %d)\n",strerror(errno),errno);
		exit(0);
    }

	/* start listen clent request event */
    if( listen(socket_fd, 10) == -1){
		printf("listen socket error: %s(errno: %d)\n",strerror(errno),errno);
		exit(0);
    }
    printf("======waiting for client's request======\n");
    while(1){
			/* block untill clent connected */
			if( (connect_fd = accept(socket_fd, (struct sockaddr*)NULL, NULL)) == -1){
				printf("accept socket error: %s(errno: %d)",strerror(errno),errno);
				continue;
			}
		//receive data from clent
		n = recv(connect_fd, buff, MAXLINE, 0);
		//send ack to clent
		if(!fork()){
			if(send(connect_fd, "Hello,you are connected!\n", 26,0) == -1)
				perror("send error");

			close(connect_fd);
			exit(0);
		}

		buff[n] = '\0';
		printf("recv msg from client: %s\n", buff);
		close(connect_fd);
    }
    close(socket_fd);
}
