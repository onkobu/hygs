#!/bin/bash

DATABASE=$1
UPGRADE_TO=3

VERSION=$(sqlite3 $DATABASE 'SELECT max(cap_version) from app_capability;')

if [ $VERSION -eq $UPGRADE_TO ]; then
	echo already at version $VERSION, nothing to upgrade
	exit 0
fi

let UPGRADE_FROM=VERSION+1

DB_BACKUP_FILE=$DATABASE.$UPGRADE_FROM
if [ -f $DB_BACKUP_FILE ]; then
	echo "DB Backup $DB_BACKUP_FILE exists, move or delete first"
	exit 3
fi

echo "creating backup $DB_BACKUP_FILE"
cp $DATABASE $DB_BACKUP_FILE

echo "Current Version $VERSION, UPGRADING $UPGRADE_FROM to $UPGRADE_TO"

echo "Collecting DDL/DML"

for ((i=UPGRADE_FROM;i<=UPGRADE_TO;i++)); do
	FILE_PREFIX=$(printf "%03d" $i)
	#echo "searching $FILE_PREFIX"
	CANDIDATE=$(find ./ -name $FILE_PREFIX'_*.sql')
	#echo "candidate $CANDIDATE"
	echo ".read $CANDIDATE"
	sqlite3 $DATABASE ".read $CANDIDATE"
	errCode=$?
	if [ $errCode -ne 0 ]; then
		echo sqlite3 failed with error code $errCode
		exit 7
	fi
done
