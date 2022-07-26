do_install:append () {
    install -d ${D}/etc/ptp4l_cfg
    install ${S}/configs/* ${D}/etc/ptp4l_cfg
}
