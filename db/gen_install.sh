#!/bin/bash

TIMESTAMP=$(date -I)

for i in "de"; do
	FILENAME=install_${i}.sql
	cat install.header > $FILENAME 
	echo "-- generated $TIMESTAMP" >> $FILENAME
	cat install.template | sed 's/$lang/'$lang'/' >> $FILENAME
done
