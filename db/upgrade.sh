#!/bin/bash

DATABASE=$1
UPGRADE_TO=16

if [ $# -eq 0 -o "$1" = '-h' -o "$1" = '--help' ]; then
	echo ""
	echo "Upgrading How-You-Grow-Smart-DB"
	echo ""
	echo "Queries version and locale from a given DB file and checks whether"
	echo "and which SQL-scripts need to be added, to move to newest version."
	echo ""
	echo "    upgrade.sh <db-file>"
	echo ""
	exit 0
fi

VERSION=$(sqlite3 $DATABASE 'SELECT max(cap_version) from app_capability;')

if [ $VERSION -eq $UPGRADE_TO ]; then
	echo already at version $VERSION, nothing to upgrade
	exit 0
fi

let UPGRADE_FROM=VERSION+1

DB_BACKUP_FILE=$DATABASE.$VERSION
if [ -f $DB_BACKUP_FILE ]; then
	echo "DB Backup $DB_BACKUP_FILE exists, move or delete first"
	exit 3
fi

echo "creating backup $DB_BACKUP_FILE"
cp $DATABASE $DB_BACKUP_FILE

echo "Current Version $VERSION, UPGRADING $UPGRADE_FROM to $UPGRADE_TO"

dbLang=$(sqlite3 $DATABASE 'SELECT prop_val_text FROM app_properties WHERE prop_name="language";')
#dbLang=de

echo "Collecting DDL/DML, language $dbLang"

for ((i=UPGRADE_FROM;i<=UPGRADE_TO;i++)); do
	FILE_PREFIX=$(printf "%03d" $i)
	#echo "searching $FILE_PREFIX"
	CANDIDATE=$(find ./ -name $FILE_PREFIX'_*.sql')
	#echo "candidate $CANDIDATE"
	
	if grep -q $CANDIDATE upgrade_$dbLang.lst; then
		echo ".read $CANDIDATE"
	else
		echo "skipping $CANDIDATE"
		continue
	fi

	sqlite3 $DATABASE ".read $CANDIDATE"
	errCode=$?
	if [ $errCode -ne 0 ]; then
		echo sqlite3 failed with error code $errCode
		exit 7
	fi
done
