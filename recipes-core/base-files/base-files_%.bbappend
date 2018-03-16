FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://adjtime"

dirs1777 = "/tmp ${localstatedir}/tmp"
dirs755_remove = " \
	       ${localstatedir}/volatile \
	       ${localstatedir}/volatile/log \
"
dirs755_append = " \
	       ${localstatedir}/log \
	       ${localstatedir}/lib/hwclock \
	       ${@ 'run/dbus' if d.getVar('IMAGE_TYPE') != 'core-image-minimal' else ''} \
	       /run/lock \
	       /run/network \
"

volatiles = ""

inherit relative_symlinks

do_install_append () {
    if [ "${TXNVM}" = "emmc" ];then
        sed -i '/root/s/0$/1/' ${D}${sysconfdir}/fstab
    fi
    if ${@ bb.utils.contains('IMAGE_FEATURES','read-only-rootfs','true','false',d)};then
        install -v -m 0744 ${WORKDIR}/adjtime ${D}${localstatedir}/lib/hwclock/adjtime
        ln -snvf ${localstatedir}/lib/hwclock/adjtime ${D}${sysconfdir}/adjtime
    else
        install -v -m 0744 ${WORKDIR}/adjtime ${D}${sysconfdir}/adjtime
    fi
}
