#!/bin/bash

TIMESTAMP=$(date -I)

for lang in "de"; do
	echo generating $lang
	FILENAME=install_${lang}.sql
	cat install.header > $FILENAME 
	echo "-- generated $TIMESTAMP" >> $FILENAME
	cat install.template | sed 's/$lang/'$lang'/' >> $FILENAME
done
