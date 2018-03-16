SUMMARY = "Volatile bind mount setup and configuration for read-only-rootfs"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=5750f3aa4ea2b00c2bf21b2b2a7b714d"

SRC_URI = ""

S = "${WORKDIR}"

inherit allarch distro_features_check

VOLATILE_BINDS = ""
VOLATILE_BINDS[type] = "list"
VOLATILE_BINDS[separator] = "\n"

SYSTEMD_SERVICE_${PN} = ""

FILES_${PN} = ""

do_compile () {
}
do_compile[dirs] = "${WORKDIR}"

do_install () {
    install -d ${D}${sysconfdir}
    install -m 0755 ${D}${sysconfdir}.var
    install -m 0755 ${D}${sysconfdir}.run
}
do_install[dirs] = "${WORKDIR}"
