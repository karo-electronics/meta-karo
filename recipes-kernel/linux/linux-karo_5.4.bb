SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

DEPENDS += "lzop-native bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCBRANCH = "linux-5.4.y"
SRCREV = "v5.4.32"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

PROVIDES += "linux"

SRC_URI = "${KERNEL_SRC};protocol=git;branch=${SRCBRANCH}"

SRC_URI_append = " \
	file://defconfig \
	file://0001-display-support.patch \
	file://0002-fec-enet-reset.patch \
	file://0003-panel-dpi.patch \
	${@bb.utils.contains('KERNEL_FEATURES',"systemd","file://cfg/systemd.cfg","",d)} \
	${@bb.utils.contains('KERNEL_FEATURES',"wifi","file://cfg/wifi.cfg","",d)} \
"

SRC_URI_append_mx8mm = " \
	file://dts/imx8mm-qs8m-mq00-qsbase2.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-qs8m-mq00.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-16xx.dtsi;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-1610-mipi-mb.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-1610-tx4etml0500.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-1610.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-1620-lvds-mb.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-1620.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-lvds-mb.dtsi;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mm-tx8m-mipi-mb.dtsi;subdir=git/arch/arm64/boot/freescale \
"

SRC_URI_append_mx8mn = " \
	file://dts/imx8mn-tx8m-mipi-mb.dtsi;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mn-tx8m-nd00-mipi-mb.dts;subdir=git/arch/arm64/boot/freescale \
	file://dts/imx8mn-tx8m-nd00.dts;subdir=git/arch/arm64/boot/freescale \
"

SRC_URI_append_mx6 = " \
	${@bb.utils.contains('MACHINE_FEATURES',"nand","file://cfg/nand.cfg","",d)} \
"

SRC_URI_append_tx6 = " \
	file://dts/imx6dl-tx6u-8033.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8133.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8134.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8135.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8034-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8035-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8134-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8135-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8133-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-80xx-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8033-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-10x0-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-1020-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-1036-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-lcd.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-lvds.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8037-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8037.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8137-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8137.dts;subdir=git/arch/arm/boot \
	${@bb.utils.contains('MACHINE_FEATURES',"lvds","file://cfg/lvds.cfg","file://cfg/lcd.cfg",d)} \
	${@bb.utils.contains('MACHINE_FEATURES',"sata","file://cfg/sata.cfg","",d)} \
"

SRC_URI_append_txul = " \
	file://dts/imx6ul-tx6ul-0010.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-tx6ul-0011.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-tx6ul.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5010-mainboard.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5010-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5011-mainboard.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5011-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-mainboard.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6ull-txul-8013-mainboard.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ull-txul-8013-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ull-txul-8013.dts;subdir=git/arch/arm/boot \
"

SRC_URI_append_stm32mp1 = " \
	file://dts/stm32mp15-ddr.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-mx.h;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-txmp-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-txmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530-mipi-mb.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase1.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570-mipi-mb.dts;subdir=git/arch/arm/boot \
"

LOCALVERSION = "-stable"
KERNEL_IMAGETYPE_mx8m = "Image"
KERNEL_IMAGETYPE_mx6 = "uImage"

KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd","",d)}"

COMPATIBLE_MACHINE_tx8 = "(tx8m-.*|qs8m-.*)"
COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"

# exclude kernel in rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""

# returns all the elements from the src uri that are .cfg files
def find_cfgs(d):
    sources=src_patches(d, True)
    sources_list=[]
    for s in sources:
        if s.endswith('.cfg'):
            sources_list.append(s)

    return sources_list

do_configure_append () {
    for f in ${@" ".join(find_cfgs(d))};do
        cat $f >> ${B}/.config
    done
}
