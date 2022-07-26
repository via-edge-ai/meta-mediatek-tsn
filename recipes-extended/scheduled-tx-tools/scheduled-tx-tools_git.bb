SUMMARY = "Examples and Helpers for testing SO_TXTIME, and the etf and taprio qdiscs"
DESCRIPTION = "Scheduled Tx Tools provides examples and helpers for testing tsn qdiscs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://README;md5=d7cd189770434b98d5589149aa49e267"

SRC_URI = "\
	git://github.com/huangwonder/Scheduled-Tx-Tools.git;protocol=https;branch=master \
	file://0001-Remove-the-duplicated-definition-of-SO_TXTIME.patch \
	file://0002-modify-pcap-library.patch \
	file://0003-add-more-files-to-Makefile.patch \
	file://0004-add-adjust_clock_tai_offset.patch \
	file://0005-redefine-NSEC_TO_SEC-to-avoid-inaccurate-transform-i.patch \
	file://0006-modify-gatemask-to-match-tc-order-on-MediaTek-platfo.patch \
	file://0007-add-TSN-Talker-and-Listener-examples.patch \
	file://config-etf-offload.sh \
	file://config-taprio-offload.sh \
	file://config-fpe.sh \
	file://run-udp-tai-tc-etf.sh \
	file://run-udp-tai-tc-taprio.sh \
	file://filter \
"

SRCREV = "72fbb1568db086dcc8b27629b789c1848c7e902a"

S = "${WORKDIR}/git"

DEPENDS = "libpcap"

do_compile() {
    make
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/udp_tai ${D}${bindir}/
	install -m 0755 ${S}/adjust_clock_tai_offset ${D}${bindir}/
	install -m 0755 ${S}/check_clocks ${D}${bindir}/
	install -m 0755 ${S}/dump-classifier ${D}${bindir}/

	install -d ${D}/etc/tsn-scripts/
	cp ${WORKDIR}/config-etf-offload.sh ${D}/etc/tsn-scripts/
	cp ${WORKDIR}/config-taprio-offload.sh ${D}/etc/tsn-scripts/
	cp ${WORKDIR}/config-fpe.sh ${D}/etc/tsn-scripts/
	cp ${WORKDIR}/run-udp-tai-tc-etf.sh ${D}/etc/tsn-scripts/
	cp ${WORKDIR}/run-udp-tai-tc-taprio.sh ${D}/etc/tsn-scripts/
	cp ${WORKDIR}/filter ${D}/etc/tsn-scripts/
	cp ${S}/txtime_offset_stats.py ${D}/etc/tsn-scripts/
}

TARGET_CC_ARCH += "${LDFLAGS}"
