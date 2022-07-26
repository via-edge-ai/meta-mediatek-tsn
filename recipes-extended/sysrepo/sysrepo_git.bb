SUMMARY = "YANG-based configuration and operational state data store for Unix/Linux applications."
DESCRIPTION = ""
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7df5a8706277b586ca000838046993d1"

SRC_URI = "git://github.com/sysrepo/sysrepo.git;protocol=https;nobranch=1 \
	   file://0001-fix-warning-for-sysrepo-persistent-data.yang.patch \
	   file://sysrepod \
	   file://sysrepo-plugind \
"

#PV = "0.7.8+git${SRCPV}"
SRCREV = "4ddc4b959c189a3c25e406640f1d374358b7e9d7"

S = "${WORKDIR}/git"

DEPENDS = "libyang protobuf protobuf-c protobuf-c-native libredblack libev libnetconf2"

FILES:${PN} += "/usr/share/yang/* /run/sysrepo* /tmp/ /tmp/sysrepo* /usr/lib/*"

inherit cmake pkgconfig python3native python3-dir

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_BUILD_TYPE:String=Release -DBUILD_EXAMPLES:String=False -DENABLE_TESTS:String=False -DREPOSITORY_LOC:PATH=/etc/sysrepo -DCALL_TARGET_BINS_DIRECTLY=False -DGEN_LANGUAGE_BINDINGS:String=False -DIS_DEVELOPER_CONFIGURATION=OFF -DGEN_PYTHON2_TESTS=OFF -DGEN_CPP_BINDINGS=OFF -DGEN_PYTHON_BINDINGS=OFF -DBUILD_CPP_EXAMPLES=OFF -DUSE_SR_MEM_MGMT=OFF "

EXTRA_OECMAKE += " -DDAEMON_PID_FILE:PATH=/etc/sysrepod.pid -DDAEMON_SOCKET:PATH=/etc/sysrepod.sock"
EXTRA_OECMAKE += " -DPLUGIN_DAEMON_PID_FILE:PATH=/etc/sysrepo-plugind.pid -DSUBSCRIPTIONS_SOCKET_DIR:PATH=/etc/sysrepo-subscriptions"

BBCLASSEXTEND = "native nativesdk"

do_install:append () {
    install -d ${D}/etc/sysrepo/data/notifications
    install -d ${D}/etc/sysrepo/yang
    install -d ${D}/etc/sysrepo
    install -m 0775 ${WORKDIR}/sysrepod ${D}/etc/sysrepo/
    install -m 0775 ${WORKDIR}/sysrepo-plugind ${D}/etc/sysrepo/
}
