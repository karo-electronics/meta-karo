FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
inherit relative_symlinks

do_install_append () {
    set -x
    rm -rvf ${D}${sysconfdir}/default/volatiles
}
