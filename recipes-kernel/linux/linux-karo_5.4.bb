SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "master"
SRCREV = "219d54332a09e8d8741c1e1982f5eae56099de85"
KERNEL_SRC = "git://github.com/torvalds/linux.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"

SRC_URI_append = " \
	file://0001-display-support.patch \
	file://0002-fec-enet-reset.patch \
	file://0003-panel-dpi.patch \
	${@bb.utils.contains('KERNEL_FEATURES',"systemd","file://cfg/systemd.cfg","",d)} \
	${@bb.utils.contains('KERNEL_FEATURES',"wifi","file://cfg/wifi.cfg","",d)} \
"

SRC_URI_append_mx8mm = " \
	file://defconfig \
	file://dts/freescale/imx8mm-qs8m-mq00-qsbase2.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-qs8m-mq00.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-16xx.dtsi;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-1610-mipi-mb.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-1610-tx4etml0500.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-1610.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-1620-lvds-mb.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-1620.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-lvds-mb.dtsi;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mm-tx8m-mipi-mb.dtsi;subdir=git/arch/arm64/boot \
"

SRC_URI_append_mx8mn = " \
	file://defconfig \
	file://dts/freescale/imx8mn-tx8m-mipi-mb.dtsi;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mn-tx8m-nd00-mipi-mb.dts;subdir=git/arch/arm64/boot \
	file://dts/freescale/imx8mn-tx8m-nd00.dts;subdir=git/arch/arm64/boot \
"

SRC_URI_append_tx6 = " \
	file://defconfig \
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
"

SRC_URI_append_txul = " \
	file://defconfig \
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

LOCALVERSION = "-stable"
KERNEL_IMAGETYPE_mx8 = "Image"
KERNEL_IMAGETYPE_mx6 = "uImage"

KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd","",d)}"

COMPATIBLE_MACHINE_mx8 = "(tx8m-.*|qs8m-.*)"
COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"

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
