FILES_${PN} += "/run/dbus"

do_install_append() {
    rm -rvf ${D}${sysconfdir}/default/volatiles
    install -v -d -m 0755 ${D}/run/dbus
}
