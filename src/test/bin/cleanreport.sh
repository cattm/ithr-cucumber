#!/bin/sh
pathtocheck=$1
st=`ls -l $pathtocheck | wc -l`
echo "result is : $st"
if [ $st -eq 0 ]
then	
	echo "nothing there"
else	
	echo " something to clean"
	st=`rm -R $pathtocheck`
	echo "clean result : $st"
fi