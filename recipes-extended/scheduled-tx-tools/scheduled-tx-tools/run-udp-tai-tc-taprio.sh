#!/bin/sh
#
# Copyright (c) 2018, Intel Corporation
#
# SPDX-License-Identifier: BSD-3-Clause
#

IFACE=$1

if [ -z $IFACE ]; then
    echo "You must provide the network interface as first argument"
    exit -1
fi

# Now plus 1 minute
#PLUS_1MIN=$((`date +%s%N` + 37000000000 + (60 * 1000000000)))
i=`date +%s%N`
j=`echo "37000000000 + 60000000000" | bc`
PLUS_1MIN=`echo "$i + $j" | bc`
l=`echo "$PLUS_1MIN % 1000000000" | bc`


# Base will the next "round" timestamp ~1 min from now, plus 50us
#TC2_BASE=$(($PLUS_1MIN - ( $PLUS_1MIN % 1000000000 ) + 50000))
m=`echo "$l - 50000" | bc`
TC2_BASE=`echo "$PLUS_1MIN - $m" | bc`

# Base will the next "round" timestamp ~1 min from now, plus 100us
#TC1_BASE=$(($PLUS_1MIN - ( $PLUS_1MIN % 1000000000 ) + 450000))
n=`echo "$l - 450000" | bc`
TC1_BASE=`echo "$PLUS_1MIN - $n" | bc`

# TC2 txtime
udp_tai -i $IFACE -b $TC2_BASE -P 1000000 -t 3 -p 90 -d 600000 -u 7788&

# TC1 deadline
udp_tai -i $IFACE -b $TC1_BASE -P 1000000 -t 2 -p 90 -D -d 600000 -u 7789&

#./ftrace.sh -n trace
