FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
        file://bash_aliases \
        file://bash_login \
"

FILES_${PN} += "${ROOT_HOME}/.bash_aliases \
            ${ROOT_HOME}/.bash_login \
"

do_install_append () {
    set -x
    install -v -d -m 0700 ${D}${ROOT_HOME}
    install -m 0644 ${WORKDIR}/bash_aliases ${D}${ROOT_HOME}/.bash_aliases
    install -m 0644 ${WORKDIR}/bash_login ${D}${ROOT_HOME}/.bash_login

    if ! grep -q "bash" ${D}${sysconfdir}/shells;then
        echo "ERROR: bash is not listed in ${sysconfdir}/shells" >&2
    fi
    #grep -q "bash" ${D}${sysconfdir}/shells || echo /bin/bash >> ${D}${sysconfdir}/shells
}
