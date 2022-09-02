LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1fb5dca04b27614d6d04abca6f103d8d"
LICENSE="BSD-3-Clause"
PV = "0.89+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/karo-electronics/rzg2-flash-writer.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "karo-txrz"
SRCREV = "a762698b52729ab7a4680bca2efee857cf7b845d"

inherit deploy

S = "${WORKDIR}/git"

do_compile() {
        # uppercasing $MACHINE would be enough, but how?
        if [ "${MACHINE}" = "txrz-g2l0" ]; then
                BOARD="TXRZ-G2L0";
        fi
        if [ "${MACHINE}" = "txrz-g2l1" ]; then
                BOARD="TXRZ-G2L1";
        fi
        if [ "${MACHINE}" = "qsrz-g2l0" ]; then
                BOARD="QSRZ-G2L0";
        fi
        if [ "${MACHINE}" = "qsrz-g2l1" ]; then
                BOARD="QSRZ-G2L1";
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
