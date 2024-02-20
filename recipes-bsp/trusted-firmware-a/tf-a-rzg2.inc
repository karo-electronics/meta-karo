SUMMARY = "Trusted Firmware-A for RZ/G2L"
LICENSE = "BSD-3-Clause"

inherit deploy

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(txrz-.*|qsrz-.*)"

PLATFORM = "g2l"
EXTRA_FLAGS = "BOARD=${MACHINE}"
EXTRA_FLAGS += "LDFLAGS=--no-warn-rwx-segments"
FLASH_ADDRESS_BL2_BP = "00000"
FLASH_ADDRESS_FIP = "1D200"

# Requires CROSS_COMPILE set by hand as there is no configure script
export CROSS_COMPILE="${TARGET_PREFIX}"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

# No configure
do_configure[noexec] = "1"

FILES:${PN} = "/boot "
SYSROOT_DIRS += "/boot"

do_compile () {
	# Build TF-A
	oe_runmake PLAT=${PLATFORM} ${EXTRA_FLAGS} bl2 bl31
}

do_install () { 
	install -d ${D}/boot 
	install -m 644 ${S}/build/${PLATFORM}/release/bl2.bin ${D}/boot/bl2-${MACHINE}.bin
	install -m 644 ${S}/build/${PLATFORM}/release/bl31.bin ${D}/boot/bl31-${MACHINE}.bin
}

do_deploy () {
	# Create deploy folder
	install -d ${DEPLOYDIR}

	# Copy IPL
	install -m 0644 ${S}/build/${PLATFORM}/release/bl2/bl2.elf ${DEPLOYDIR}/bl2-${MACHINE}.elf
	install -m 0644 ${S}/build/${PLATFORM}/release/bl2.bin ${DEPLOYDIR}/bl2-${MACHINE}.bin
	install -m 0644 ${S}/build/${PLATFORM}/release/bl31/bl31.elf ${DEPLOYDIR}/bl31-${MACHINE}.elf
	install -m 0644 ${S}/build/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31-${MACHINE}.bin
}

addtask deploy before do_build after do_compile
