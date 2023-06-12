SUMMARY = "5.15 Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

PROVIDES += "linux"
DEPENDS += "lzop-native bc-native dtc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "linux-5.15.y"
SRCREV = "a0ebea480bb319a3ad408c99db91262dbc696b76"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git"
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
        file://0015-raspberrypi-7inch-touchscreen-support.patch \
        file://0016-parallel-display-bus-flags-from-display-info.patch \
        file://0017-spi-nand-dma-map-bugfix.patch \
        file://0019-fdt5x06-dma-bugfix.patch \
"

SRC_URI:append:stm32mp1 = " \
        file://dts/stm32mp15-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp15-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp15-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp153-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp153-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp153-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp157-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp157-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/stm32mp157-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

KERNEL_LOCALVERSION = "${LINUX_VERSION_EXTENSION}"
KERNEL_IMAGETYPE:stm32mp1 = "uImage"

KBUILD_DEFCONFIG = "defconfig"
KBUILD_DEFCONFIG:qsmp-1510 = "qsmp-1510_defconfig"

KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"bluetooth"," bluetooth.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"ipv6"," ipv6.cfg","",d)}"

KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"extmod"," extmod.cfg","",d)}"

COMPATIBLE_MACHINE:stm32mp1 = "(txmp-.*|qsmp-.*)"

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
addtask do_configure before do_devshell
