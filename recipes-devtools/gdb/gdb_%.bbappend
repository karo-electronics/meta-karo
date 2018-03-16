do_install_append () {
    # weed out files not needed on the target
    rm -rvf ${D}/usr/share/gdb
    rm -vf ${D}${bindir}/gdb
}
