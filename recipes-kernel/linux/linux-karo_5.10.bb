SUMMARY = "5.10 Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

PROVIDES += "linux"
DEPENDS += "lzop-native bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "rz-5.10-cip17"
SRCREV = "13dea4598e61893e75eae1c1887fa51ea6b22a07"
KERNEL_SRC = "git://github.com/renesas-rz/rz_linux-cip.git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

SRC_URI = "${KERNEL_SRC};protocol=https;branch=${SRCBRANCH}"

# automatically add all .dts files referenced by ${KERNEL_DEVICETREE} to SRC_URI
SRC_URI:append = "${@"".join(map(lambda f: " file://dts/%s;subdir=git/${KERNEL_OUTPUT_DIR}" % f.replace(".dtb", ".dts"), "${KERNEL_DEVICETREE}".split()))}"

SRC_URI:append = " \
        file://${KBUILD_DEFCONFIG} \
        ${@ "".join(map(lambda f: " file://cfg/" + f, "${KERNEL_FEATURES}".split()))} \
        file://0001-display-support.patch \
        file://0002-panel-dpi-bus-format.patch \
        file://0003-smsc-lan8741-support.patch \
        file://0007-stm32cryp-dependencies.patch \
        file://0010-attiny-regulator-i2c-retries.patch \
        file://0011-stm-drv-preferred-depth.patch \
        file://0017-spi-nand-dma-map-bugfix.patch \
        file://0019-fdt5x06-dma-bugfix.patch \
        file://0020-ilitek-ts-i2c-driver.patch \
        file://0021-kbuild-dtbo-support.patch \
        file://0022-dtc-dtbo-support.patch \
"

SRC_URI:append:rzg2 = " \
		file://0001-clock-support-for-CM33.patch \
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

do_configure:prepend() {
    # Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    if ! [ -s "${S}/.scmversion" ] || ! grep -q "$head" ${S}/.scmversion;then
        echo "+g$head" > "${S}/.scmversion"
    fi
    install -v "${WORKDIR}/${KBUILD_DEFCONFIG}" "${B}/.config"
    sed -i '/CONFIG_LOCALVERSION/d' "${B}/.config"
    echo 'CONFIG_LOCALVERSION="${KERNEL_LOCALVERSION}"' >> "${B}/.config"

    for f in ${KERNEL_FEATURES};do
        cat ${WORKDIR}/cfg/$f >> ${B}/.config
    done
}
