SUMMARY = "real-time-edge-sysrepo"
DESCRIPTION = "A tool to configure TSN funtionalities in user space"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/real-time-edge-sw/real-time-edge-sysrepo.git;protocol=https;nobranch=1 \
           file://0001-adapt-real-time-edge-sysrepo-to-MTK-platform.patch \
           file://0002-add-support-for-taprio-preemption.patch \
           file://0003-add-support-for-genio-700-platform.patch \
           file://0004-add-support-for-MTK-platform.patch \
           file://sysrepo-tsnd \
           file://sysrepo-init \
           file://sysrepo-tsn.service \
           file://sysrepo-cfg.service \
           file://scripts/model-install.sh \
           file://scripts/sysrepo-cfg.sh \
           file://Instances/qbu-eth0.xml \
           file://Instances/qbv-eth0.xml \
           file://Instances/ietf-ip-cfg.xml \
"

SRCREV = "1ad055e8d24564dcb50c63b8b10fa362c55c3e9d"

S = "${WORKDIR}/git"

DEPENDS = "libyang libnetconf2 sysrepo netopeer2-keystored netopeer2-server netopeer2-cli cjson libnl"
RDEPENDS:${PN} += "bash curl libyang libnetconf2 sysrepo netopeer2-keystored netopeer2-server netopeer2-cli cjson libnl"

FILES:${PN} += "/etc/sysrepo-tsn /lib/systemd/system/* /etc/systemd/system/multi-user.target.wants/* /lib/*"

inherit cmake pkgconfig
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX=/usr -DCMAKE_BUILD_TYPE:String=Release \
		  -DSYSREPOCTL_EXECUTABLE=/usr/bin/sysrepoctl -DSYSREPOCFG_EXECUTABLE=/usr/bin/sysrepocfg \
		  -DCONF_SYSREPO_TSN_TC=ON "

do_install:append () {
    install -d ${D}/etc/sysrepo-tsn/
    install -d ${D}/etc/sysrepo-tsn/scripts
    install -d ${D}/etc/sysrepo-tsn/Instances
    install -m 0775 ${WORKDIR}/scripts/*.sh ${D}/etc/sysrepo-tsn/scripts
    install -m 0775 ${WORKDIR}/Instances/*.xml ${D}/etc/sysrepo-tsn/Instances

    install -d ${D}/etc/sysrepo-tsn/modules
    install -m 0775 ${S}/modules/*.yang ${D}/etc/sysrepo-tsn/modules

    install -d ${D}/etc/sysrepo-tsn
    install -m 0755 ${WORKDIR}/sysrepo-tsnd ${D}/etc/sysrepo-tsn/
    install -m 0755 ${WORKDIR}/sysrepo-init ${D}/etc/sysrepo-tsn/

    install -d ${D}/lib/systemd/system/
    install -m 0664 ${WORKDIR}/sysrepo-cfg.service ${D}/lib/systemd/system/

    install -d ${D}/etc/systemd/system/multi-user.target.wants/
    ln -sfr ${D}/lib/systemd/system/sysrepo-cfg.service ${D}/etc/systemd/system/multi-user.target.wants/sysrepo-cfg.service
}
