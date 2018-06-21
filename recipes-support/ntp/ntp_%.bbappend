FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN} += "libgcc"
RDEPENDS_ntpdate += "lockfile-progs"

inherit relative_symlinks

do_install_append () {
    if ${@ bb.utils.contains('DISTRO_FEATURES','dhcpcd','true','false',d)};then
        rm -vf ${D}${sysconfdir}/ntp.conf
    fi
}
