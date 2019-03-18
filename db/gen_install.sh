#!/bin/bash

# Generates locale-specific install-script and populates
# locale-specific upgrade-list
#
TIMESTAMP=$(date -I)

for lang in "de"; do
	echo generating $lang
	FILENAME=install_${lang}.sql
	cat install.header > $FILENAME 
	echo "-- generated $TIMESTAMP" >> $FILENAME
	cat install.template | sed 's/$lang/'$lang'/' >> $FILENAME
	
	cat install.template | grep -E '[0-9]{3}+.*\.sql$' | awk '{print "./"$2}' | sed 's/$lang/'$lang'/' > upgrade_$lang.lst
done

