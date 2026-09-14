LICENSE = "MIT"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS = "u-boot-mkimage-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

BOOTSCRIPT ??= "${THISDIR}/files/uboot.sh"
UBOOT_INSTALL_BOOT_DIR ?= "/boot"

do_compile () {
    mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  -n "boot script" -d ${BOOTSCRIPT} ${B}/boot.scr.uimg
}

do_install () {
    install -d ${D}/${UBOOT_INSTALL_BOOT_DIR}
    install -D -m 755 ${B}/boot.scr.uimg ${D}/${UBOOT_INSTALL_BOOT_DIR}/boot.scr.uimg
    install -D -m 755 ${BOOTSCRIPT} ${D}/${UBOOT_INSTALL_BOOT_DIR}/uboot.sh
}

FILES:${PN} += "${UBOOT_INSTALL_BOOT_DIR}"

COMPATIBLE_MACHINE:stm32mp1 = "(txmp-.*|qsmp-.*)"