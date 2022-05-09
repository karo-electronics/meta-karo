SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

DEPENDS += "lzop-native bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "linux-5.10.y"
SRCREV = "v5.10.61"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

PROVIDES += "linux"

SRC_URI = "${KERNEL_SRC};protocol=git;branch=${SRCBRANCH}"

SRCBRANCH_rzg2 = "rz-5.10-cip1"
SRCREV_rzg2 = "6598999af2323a9344d8513f56b509fec114ac6b"
SRC_URI_rzg2 = "git://github.com/renesas-rz/rz_linux-cip.git;protocol=https;branch=${SRCBRANCH}"

# automatically add all .dts files referenced by ${KERNEL_DEVICETREE} to SRC_URI
SRC_URI_append = "${@"".join(map(lambda f: " file://dts/%s;subdir=git/arch/arm/boot" % f.replace(".dtb", ".dts"), d.getVar('KERNEL_DEVICETREE').split()))}"

SRC_URI_append = " \
        file://${KBUILD_DEFCONFIG} \
        ${@ "".join(map(lambda f: " file://cfg/" + f, "${KERNEL_FEATURES}".split()))} \
        file://0001-display-support.patch \
        file://0002-panel-dpi-bus-format.patch \
        file://0007-stm32cryp-dependencies.patch \
        file://0010-attiny-regulator-i2c-retries.patch \
        file://0011-stm-drv-preferred-depth.patch \
        file://0015-raspberrypi-7inch-touchscreen-support.patch \
        file://0016-parallel-display-bus-flags-from-display-info.patch \
        file://0017-spi-nand-dma-map-bugfix.patch \
        file://0019-fdt5x06-dma-bugfix.patch \
        file://0001-lib-iov_iter-initialize-flags-in-new-pipe_buffer.patch \
"

# dirty-pipe vulnerability already applied in renesas linux
SRC_URI_remove_rzg2 = " \
        file://0001-lib-iov_iter-initialize-flags-in-new-pipe_buffer.patch \
"

SRC_URI_append_rzg2 = " \
	    file://dts/renesas/r9a07g044l2-txrz-g2l0.dts;subdir=git/arch/arm64/boot \
	    file://dts/renesas/r9a07g044l2-txrz-g2l0-mb7.dts;subdir=git/arch/arm64/boot \
"

SRC_URI_append_tx6 = " \
        file://dts/imx6qdl-tx6-lcd.dtsi;subdir=git/arch/arm/boot \
        file://dts/imx6qdl-tx6-lvds.dtsi;subdir=git/arch/arm/boot \
        file://dts/imx6qdl-tx6-mb7.dtsi;subdir=git/arch/arm/boot \
        file://dts/imx6qdl-tx6.dtsi;subdir=git/arch/arm/boot \
"

SRC_URI_append_txul = " \
        file://dts/imx6ul-tx6ul.dtsi;subdir=git/arch/arm/boot \
        file://dts/imx6ul-txul-mainboard.dtsi;subdir=git/arch/arm/boot \
        file://dts/imx6ul-txul-mb7.dtsi;subdir=git/arch/arm/boot \
"

SRC_URI_append_stm32mp1 = " \
        file://dts/stm32mp15-karo-dsi-panel.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-karo-lcd-panel.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-karo-mb7.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-karo-qsbase1.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-karo-qsbase2.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-karo.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-qsmp-lcd-panel.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-qsmp.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-txmp-lcd-panel.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp15-txmp.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp153-karo.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp153-qsmp.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp153-txmp.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp157-karo.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp157-qsmp.dtsi;subdir=git/arch/arm/boot \
        file://dts/stm32mp157-txmp.dtsi;subdir=git/arch/arm/boot \
"

KERNEL_LOCALVERSION = "${LINUX_VERSION_EXTENSION}"
KERNEL_IMAGETYPE_mx6 = "uImage"
KERNEL_IMAGETYPE_stm32mp1 = "uImage"

KBUILD_DEFCONFIG = "defconfig"
KBUILD_DEFCONFIG_qsmp-1510 = "qsmp-1510_defconfig"

KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"bluetooth"," bluetooth.cfg","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi.cfg","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd.cfg","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"ipv6"," ipv6.cfg","",d)}"

KERNEL_FEATURES_append = "${@bb.utils.contains('MACHINE_FEATURES',"extmod"," extmod.cfg","",d)}"

KERNEL_FEATURES_append_mx6 = "${@bb.utils.contains('MACHINE_FEATURES',"nand"," nand.cfg","",d)}"
KERNEL_FEATURES_append_tx6 = "${@bb.utils.contains('MACHINE_FEATURES',"lvds"," lvds.cfg"," lcd.cfg",d)}"
KERNEL_FEATURES_append_tx6 = "${@bb.utils.contains('MACHINE_FEATURES',"sata"," sata.cfg","",d)}"

COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"
COMPATIBLE_MACHINE_stm32mp1 = "(txmp-.*|qsmp-.*)"

# returns all the elements from the src uri that are .cfg files
def find_cfgs(d):
    sources=src_patches(d, True)
    sources_list=[]
    for s in sources:
        if s.endswith('.cfg'):
            sources_list.append(s)

    return sources_list

do_configure_prepend() {
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
