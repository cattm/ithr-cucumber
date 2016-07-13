#!/bin/sh
for file in $( ls reports/site/cucumber-reports/*.html ) do
	echo $file
done

