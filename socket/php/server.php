<?php
//never timeout when connecting
set_time_limit(0);

$ip = '127.0.0.1';
$port = 1935;

/*
 +-------------------------------
 *    @socket usage/process step
 +-------------------------------
 *    @socket_create
 *    @socket_bind
 *    @socket_listen
 *    @socket_accept
 *    @socket_read
 *    @socket_write
 *    @socket_close
 +--------------------------------
 */

/*----------------    Steps from manual   -------------------*/
if(($sock = socket_create(AF_INET,SOCK_STREAM,SOL_TCP)) < 0) {
    echo "socket_create() fail reason:".socket_strerror($sock)."\n";
}

if(($ret = socket_bind($sock,$ip,$port)) < 0) {
    echo "socket_bind() fail reason:".socket_strerror($ret)."\n";
}

if(($ret = socket_listen($sock,4)) < 0) {
    echo "socket_listen() fail reason:".socket_strerror($ret)."\n";
}

$count = 0;

do {
    if (($msgsock = socket_accept($sock)) < 0) {
        echo "socket_accept() failed: reason: " . socket_strerror($msgsock) . "\n";
        break;
    } else {

        //send to client
        $msg ="test msg\n";
        socket_write($msgsock, $msg, strlen($msg));

        echo "test success!\n";
        $buf = socket_read($msgsock,8192);

        $talkback = "receive msg:$buf\n";
        echo $talkback;

        if(++$count >= 5){
            break;
        };

    }
    //echo $buf;
    socket_close($msgsock);

} while (true);

socket_close($sock);
?>
