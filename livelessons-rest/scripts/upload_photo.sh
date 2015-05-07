#!/bin/sh
set -e

uri=http://127.0.0.1:8080/people/$1/photo 
resp=`curl -F "file=@$2" $uri`
echo $resp

