SUMMARY = "5.10 Linux Kernel for Ka-Ro electronics Computer-On-Modules"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/patches:"

require recipes-kernel/linux/linux-karo.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "rz-5.10-cip36"
SRCREV = "c45e3a8129dd0ee36c51079e95962b2f85472e51"
KERNEL_SRC = "git://github.com/renesas-rz/rz_linux-cip.git"

SRC_URI:append = " \
        file://${KBUILD_DEFCONFIG} \
        ${@ "".join(map(lambda f: " file://cfg/" + f, "${KERNEL_FEATURES}".split()))} \
        file://0001-display-support.patch \
        file://0002-panel-dpi-bus-format.patch \
        file://0003-smsc-lan8741-support.patch \
        file://0010-attiny-regulator-i2c-retries.patch \
        file://0017-spi-nand-dma-map-bugfix.patch \
        file://0019-fdt5x06-dma-bugfix.patch \
        file://0020-ilitek-ts-i2c-driver.patch \
        file://0021-kbuild-dtbo-support.patch \
        file://0022-dtc-dtbo-support.patch \
"

SRC_URI:append:rzg2 = " \
        file://dts/renesas/r9a07g044l2-karo-lcd-panel.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-qsrz.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-txrz.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

KERNEL_LOCALVERSION = "${LINUX_VERSION_EXTENSION}"

KBUILD_DEFCONFIG = "defconfig"

KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"bluetooth"," bluetooth.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"ipv6"," ipv6.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"extmod"," extmod.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"dsi83"," dsi83.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"copro"," copro.cfg","",d)}"

COMPATIBLE_MACHINE:rzg2 = "(txrz-.*|qsrz-.*)"
