LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1fb5dca04b27614d6d04abca6f103d8d"
LICENSE="BSD-3-Clause"
PV = "0.89+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/karo-electronics/rzg2-flash-writer.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "karo-txrz"
SRCREV = "61eeb2478d256da956fe1d9d383614b330978613"

inherit deploy

S = "${WORKDIR}/git"

do_compile() {
        if [ "${MACHINE}" = "txrz-g2l0" ]; then
                BOARD="TXRZ";
        fi
        cd ${S}

	oe_runmake BOARD=${BOARD} EMMC=ENABLE
}

do_install[noexec] = "1"

do_deploy() {
        install -d ${DEPLOYDIR}
        install -m 644 ${S}/AArch64_output/*.mot ${DEPLOYDIR}
}
PARALLEL_MAKE = "-j 1"
addtask deploy after do_compile
