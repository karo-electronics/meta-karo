LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1fb5dca04b27614d6d04abca6f103d8d"
LICENSE="BSD-3-Clause"
PV = "0.89+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/karo-electronics/rzg2-flash-writer.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "karo-txrz"
SRCREV = "a762698b52729ab7a4680bca2efee857cf7b845d"

inherit deploy

S = "${WORKDIR}/git"
B = "${S}/AArch64_output"

FILES:${PN} += "Flash_Writer_SCIF_${MACHINE}.mot"

do_compile() {
    BOARD="$(echo "${MACHINE}" | tr 'a-z' 'A-Z')"

    cd ${S}
    oe_runmake BOARD=${BOARD} EMMC=ENABLE FILE_NAME="${B}/Flash_Writer_SCIF_${MACHINE}"
}

do_install() {
    install -v "${B}/Flash_Writer_SCIF_${MACHINE}.mot" "${D}/Flash_Writer_SCIF_${MACHINE}.mot"
}

do_deploy() {
    install -v "${D}/Flash_Writer_SCIF_${MACHINE}.mot" "${DEPLOYDIR}/"
}
addtask do_deploy after do_install

PARALLEL_MAKE = "-j 1"

FILESEXTRAPATHS:prepend := "${THISDIR}/patches:"
SRC_URI:append = " file://txrz-g2l2-bugfix.patch"
