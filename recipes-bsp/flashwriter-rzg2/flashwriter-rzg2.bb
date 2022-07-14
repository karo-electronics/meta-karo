LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1fb5dca04b27614d6d04abca6f103d8d"
LICENSE="BSD-3-Clause"
PV = "0.89+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/karo-electronics/rzg2-flash-writer.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "karo-txrz"
SRCREV = "4803e81f6c82b7b9fbf8fbc5ba2c1ebdc296098b"

inherit deploy

S = "${WORKDIR}/git"

do_compile() {
        if [ "${MACHINE}" = "txrz-g2l0" ]; then
                BOARD="TXRZ";
        fi
        if [ "${MACHINE}" = "qsrz-g2l0" ]; then
                BOARD="QSRZ";
        fi
        cd ${S}

	oe_runmake BOARD=${BOARD} EMMC=ENABLE
}

do_install[noexec] = "1"

do_deploy() {
        install -d ${DEPLOYDIR}
        install -m 644 ${S}/AArch64_output/*.mot ${DEPLOYDIR}/Flash_Writer_SCIF_${MACHINE}.mot
}
PARALLEL_MAKE = "-j 1"
addtask deploy after do_compile
