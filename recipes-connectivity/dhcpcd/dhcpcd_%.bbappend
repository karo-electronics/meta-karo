FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_remove = "udev"
FILES_${PN} += " \
	    /run/dhcpc \
	    ${localstatedir}/lib/dhcpcd \
"

SRC_URI_append = " \
           file://ntp.conf \
           file://resolv.conf \
"
inherit relative_symlinks

do_install_append () {
    sed -i 's/^duid/#duid/;s/^#clientid/clientid/' ${D}${sysconfdir}/dhcpcd.conf
    if ! grep -q '^quiet' ${D}${sysconfdir}/dhcpcd.conf;then
        cat << EOF >> ${D}${sysconfdir}/dhcpcd.conf

# Silence debug messages
quiet
EOF
    fi
    install -v -d -m 0755 ${D}/run/dhcpc

    echo "BPN='${BPN}' PN='${PN}'"
    install -m 0644 ${WORKDIR}/ntp.conf ${D}/run/dhcpc/ntp.conf
    ln -snvf /run/dhcpc/ntp.conf ${D}${sysconfdir}/

    install -m 0644 ${WORKDIR}/resolv.conf ${D}/run/dhcpc/resolv.conf
    ln -snvf /run/dhcpc/resolv.conf ${D}${sysconfdir}/

    install -v -m 0755 -d ${D}${localstatedir}/lib/dhcpcd
    install -v -m 0755 /dev/null ${D}${localstatedir}/lib/dhcpcd/dhcpcd.duid
    ln -snvf ${localstatedir}/lib/dhcpcd/dhcpcd.duid ${D}${sysconfdir}/
}
