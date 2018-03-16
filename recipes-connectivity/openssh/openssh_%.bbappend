do_install_append () {
	rm -rvf ${D}/${sysconfdir}/default/volatiles
}
