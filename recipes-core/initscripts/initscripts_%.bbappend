FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
               file://bootmisc.sh \
               file://initvar.sh \
               file://umountroot \
               file://vars.sh \
"

FILES_${PN} += "/lib/init/vars.sh"
CONFFILES_${PN} += "${sysconfdir}/init.d/umountroot"

rmfiles = " \
        init.d/populate-volatile.sh \
        init.d/read-only-rootfs-hook.sh \
        init.d/save-rtc.sh \
        rc*.d/*populate-volatile.sh \
        rc*.d/*read-only-rootfs-hook.sh \
        rc*.d/*save-rtc.sh \
        default/volatiles \
"

do_install_append () {
    install -m 0755 ${WORKDIR}/initvar.sh ${D}${sysconfdir}/init.d
    update-rc.d -r ${D} initvar.sh start 03 S .

    install -d -m 0755 ${D}${sysconfdir}/.run
    install -d -m 0755 ${D}${sysconfdir}/.var

    install -d -m 0755 ${D}/lib/init
    install -m 0755 ${WORKDIR}/vars.sh ${D}/lib/init/vars.sh
    for f in ${rmfiles};do
        rm -rv ${D}${sysconfdir}/${f}
    done

    install -m 0755 ${WORKDIR}/umountroot       ${D}${sysconfdir}/init.d/umountroot
    update-rc.d -r ${D} umountroot start 41 0 6 .
}
