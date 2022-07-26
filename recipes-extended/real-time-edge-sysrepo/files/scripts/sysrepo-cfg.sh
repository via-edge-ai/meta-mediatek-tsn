#!/bin/sh

echo "sysrepo-cfg is running for config related components"
CMD=$1

if [ $CMD == "start" ]; then
    /etc/sysrepo-tsn/sysrepo-init ${CMD}
    /etc/sysrepo/sysrepod ${CMD}
    /etc/sysrepo/sysrepo-plugind ${CMD}
    /etc/sysrepo-tsn/sysrepo-tsnd ${CMD}
    /etc/netopeer2/netopeer2-server ${CMD}
else
    /etc/netopeer2/netopeer2-server ${CMD}
    /etc/sysrepo-tsn/sysrepo-tsnd ${CMD}
    /etc/sysrepo/sysrepo-plugind ${CMD}
    /etc/sysrepo/sysrepod ${CMD}
fi

exit 0
