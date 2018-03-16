FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
do_install_append () {
	rm ${D}${sysconfdir}/network/if-pre-up.d/nfsroot
}
