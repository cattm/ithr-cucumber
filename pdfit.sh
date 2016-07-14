#!/bin/sh
flist=""
# improvment - path should be an env or param
for f in $( ls reports/site/cucumber-reports/*.html | sort ); do
	bname=`basename $f`
	flist="$flist $bname"
done
#echo "result is : $flist"
date=`date +%Y%m%d_%H%M%S`
cd reports/site/cucumber-reports
st=`wkhtmltopdf $flist cucumer-report-${date}.pdf`
exit 0

