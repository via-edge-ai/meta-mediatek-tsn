# Copyright 2020-2021 NXP

SUMMARY = "OPC UA implementation"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "gitsm://github.com/open62541/open62541.git;protocol=https;nobranch=1 \
           file://0001-feat-examples-Add-pubsub-TSN-sample-applications.patch \
           file://pktgen/functions.sh \
           file://pktgen/parameters.sh \
           file://pktgen/pktgen_sample01_simple.sh \
           file://pktgen/pktgen_twoqueue.sh \
"

# Modify these as desired
PV = "v1.2.2"
SRCREV = "ecf5a703785877a8719a0cda863a98455f7d5d12"

S = "${WORKDIR}/git"

DEPENDS = "openssl"
RDEPENDS:${PN} += "bash"
require libopen62541.inc

do_install:append () {
	if [ -d ${B}/bin/examples ] && [ -z $(find ${B}/bin/examples -maxdepth 0 -empty) ]
	then
		install -d ${D}${ROOT_HOME}/open62541_example/
		install -m 0755 ${B}/bin/examples/* ${D}${ROOT_HOME}/open62541_example/
		install -d ${D}${ROOT_HOME}/pktgen/
		cp ${WORKDIR}/pktgen/* ${D}${ROOT_HOME}/pktgen/
	fi
}

FILES:${PN} += "${bindir_native}/* ${datadir_native}/open62541/* ${libdir_native}/* ${ROOT_HOME}/*"

inherit cmake python3native

