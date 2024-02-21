SUMMARY = "Trusted Firmware-A for RZ/G2L"
LICENSE = "BSD-3-Clause"

inherit deploy

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

COMPATIBLE_MACHINE = "(txrz-.*|qsrz-.*)"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PLATFORM = "g2l"
EXTRA_FLAGS = "BOARD=${MACHINE}"
EXTRA_FLAGS += "LDFLAGS=--no-warn-rwx-segments"

# Requires CROSS_COMPILE set by hand as there is no configure script
export CROSS_COMPILE="${TARGET_PREFIX}"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

FILES:${PN} += "\
    bl31-${MACHINE}.bin \
    bl2-${MACHINE}.bin \
    bl2_bp-${MACHINE}.bin \
"

do_configure() {
    bbnote "Doing 'make clean' due to compiler variables change"
    oe_runmake -C ${S} BUILD_BASE=${B} PLAT=${PLATFORM} ${EXTRA_FLAGS} clean
}
do_configure[vardeps] += "EXTRA_FLAGS"

do_compile () {
    # Build TF-A
    oe_runmake -C ${S} BUILD_BASE=${B} PLAT=${PLATFORM} ${EXTRA_FLAGS} bl2 bl31

    bootparameter ${B}/${PLATFORM}/release/bl2.bin ${B}/${PLATFORM}/release/bl2_bp.bin
    cat ${B}/${PLATFORM}/release/bl2.bin >> ${B}/${PLATFORM}/release/bl2_bp.bin
}

do_install () {
    install -v ${B}/${PLATFORM}/release/bl2.bin ${D}/bl2-${MACHINE}.bin
    install -v ${B}/${PLATFORM}/release/bl2_bp.bin ${D}/bl2_bp-${MACHINE}.bin
    install -v ${B}/${PLATFORM}/release/bl31.bin ${D}/bl31-${MACHINE}.bin
}

do_deploy () {
    # Create bl2_bp.bin
    install -d ${DEPLOYDIR}/${FIPTOOL_DIR}

    install -v "${D}/bl2_bp-${MACHINE}.bin" "${DEPLOYDIR}/${FIPTOOL_DIR}/bl2_bp-${MACHINE}.bin"
    install -v "${D}/bl31-${MACHINE}.bin" ${DEPLOYDIR}/${FIPTOOL_DIR}/bl31-${MACHINE}.bin
}

addtask deploy before do_build after do_install
