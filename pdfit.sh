#!/bin/sh
flist=""
for f in $( ls reports/site/cucumber-reports/*.html | sort ); do
	echo "$file"
	bname=`basename $f`
	echo "$bname"
	flist="$flist $bname"
done
echo "result is : $flist"
cd reports/site/cucumber-reports
st=`wkhtmltopdf $flist cucumer-report.pdf`
exit 0

