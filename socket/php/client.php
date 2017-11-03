<?php
error_reporting(E_ALL);
set_time_limit(0);
echo "<h2>TCP/IP Connection</h2>\n";

$port = 1935;
$ip = "127.0.0.1";

/*
 +-------------------------------
 *    @socket usage/process step
 +-------------------------------
 *    @socket_create
 *    @socket_connect
 *    @socket_write
 *    @socket_read
 *    @socket_close
 +--------------------------------
 */

$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
if ($socket < 0) {
    echo "socket_create() failed: reason: " . socket_strerror($socket) . "\n";
}else {
    echo "OK.\n";
}

echo "try to connect to '$ip' port '$port'...\n";
$result = socket_connect($socket, $ip, $port);
if ($result < 0) {
    echo "socket_connect() failed.\nReason: ($result) " . socket_strerror($result) . "\n";
}else {
    echo "connected!\n";
}

$in = "Ho\r\n";
$in .= "first blood\r\n";
$out = '';

if(!socket_write($socket, $in, strlen($in))) {
    echo "socket_write() failed: reason: " . socket_strerror($socket) . "\n";
}else {
    echo "send msg to server success!\n";
    echo "sent msg is:<font color='red'>$in</font> <br>";
}

while($out = socket_read($socket, 8192)) {
    echo "receive ACK from server success!\n";
    echo "received msg is:",$out;
}


echo "close socket...\n";
socket_close($socket);
echo "socket closed\n";
?>
