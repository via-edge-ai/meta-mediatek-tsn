IFACE=$1

if [ -z $IFACE ]; then
    echo "You must provide the network interface as first argument"
    exit -1
fi

#i=$((`date +%s%N` + 37000000000 + (15 * 1000000000)))

#BASE_TIME=$(($i - ($i % 1000000000)))
i=`date +%s%N`
j=`echo "37000000000 + 20000000000" | bc`
k=`echo "$i + $j" | bc`
m=`echo "$k % 1000000000" | bc`
BASE_TIME=`echo "$k - $m" | bc`
BATCH_FILE=fpe.batch

cat > $BATCH_FILE <<EOF
qdisc replace dev $IFACE parent root handle 100 taprio \\
      num_tc 4 \\
      map 0 1 2 3 0 0 0 0 0 0 0 0 0 0 0 0 \\
      queues 1@0 1@1 1@2 1@3\\
      base-time $BASE_TIME \\
      sched-entry SH 08 300000 \\
      sched-entry SH 04 300000 \\
      sched-entry SH 02 100000 \\
      sched-entry SR 01 300000 \\
      flags 0x2

qdisc replace dev $IFACE parent 100:4 etf \\
      delta 200000 clockid CLOCK_TAI offload

qdisc replace dev $IFACE parent 100:3 etf \\
	  delta 200000 clockid CLOCK_TAI offload deadline_mode
EOF

tc -batch $BATCH_FILE

echo "Base time: $BASE_TIME"
echo "Configuration saved to: $BATCH_FILE"
