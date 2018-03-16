FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN} += "libgcc"
RDEPENDS_ntpdate += "lockfile-progs"

inherit relative_symlinks

do_install_append () {
    echo "DISTRO_FEATURES='${@ d.getVar('DISTRO_FEATURES')}'"
    if ${@ bb.utils.contains('DISTRO_FEATURES','dhcpcd','true','false',d)};then
        rm -vf ${D}${sysconfdir}/ntp.conf
    fi
}
