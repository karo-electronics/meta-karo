SECTION = "bootloaders"
DESCRIPTION = "Application to create binaries in the correct format for rzg2l board flashing"
LICENSE = "CLOSED"

inherit native

S = "${WORKDIR}"

SRC_URI = "file://bootparameter.c"

do_compile () {
         ${CC} bootparameter.c -o bootparameter
}

do_install () {
    install -d ${D}${bindir}
    install ${WORKDIR}/bootparameter ${D}${bindir}
}
