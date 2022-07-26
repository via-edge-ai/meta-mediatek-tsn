IFACE=$1

if [ -z $IFACE ]; then
    echo "You must provide the network interface as first argument"
    exit -1
fi

BATCH_FILE=etf.batch

cat > $BATCH_FILE <<EOF
qdisc replace dev $IFACE parent root handle 100 mqprio \\
      num_tc 4 \\
      map 0 1 2 3 0 0 0 0 0 0 0 0 0 0 0 0 \\
      queues 1@0 1@1 1@2 1@3 hw 0

qdisc replace dev $IFACE parent 100:4 etf \\
      delta 200000 clockid CLOCK_TAI offload

EOF

tc -batch $BATCH_FILE


