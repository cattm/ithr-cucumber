#!/bin/sh

hostname=`hostname`
if [ "$hostname" == "SSS_Mac01" ]
then
        ptowk="/usr/local/bin/wkhtmltopdf"
else
        ptowk="/bin/wkhtmltopdf"
fi
flist=""
# improvment - path should be an env or param
for f in $( ls $1/*.html | sort ); do
	bname=`basename $f`
	flist="$flist $bname"
done
echo "result is : $flist"
flist2="anoverview.html failures-overview.html"
date=`date +%Y%m%d_%H%M%S`
cd $1 
#st=`/usr/local/bin/wkhtmltopdf --javascript-delay 5000 $flist cucumer-report-${date}.pdf`
st=`$ptowk --javascript-delay 5000 $flist2 cucumer-report-${date}.pdf`
exit 0

