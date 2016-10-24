#!/bin/sh
ls -l $1 | cut -d' ' -f8- | awk '{print $1}'
