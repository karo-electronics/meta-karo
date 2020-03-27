SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules"

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
	${@bb.utils.contains('KERNEL_FEATURES',"systemd","file://cfg/systemd.cfg","",d)} \
	${@bb.utils.contains('KERNEL_FEATURES',"wifi","file://cfg/wifi.cfg","",d)} \
"

#file://patch-for-edt-m12.diff
#file://imx6ull-bugfix.patch
#file://txul-phy-reset.patch
#file://txul-enet-sleep.patch
#file://pixclk-polarity-override.patch

SRC_URI_append_tx6 = " \
	file://defconfig \
	file://dts/imx6dl-tx6s-8034-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8035-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8134-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8134.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8135-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8135.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-80xx-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8033-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8133-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8133.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-10x0-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-1020-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-1036-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-lcd.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-lvds.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6qp-8037-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6qp-8037.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6qp-8137-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6qp-8137.dts;subdir=git/arch/arm/boot \
"

SRC_URI_append_txul = " \
	file://defconfig \
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
KERNEL_IMAGETYPE = "uImage"

# TODO: put them all into machine specific files.
DEFAULT_DEVICETREE_imx6dl-tx6-emmc ?= " \
                                imx6dl-tx6dl-comtft.dtb \
                                imx6dl-tx6s-8035.dtb \
                                imx6dl-tx6s-8035-mb7.dtb \
                                imx6dl-tx6s-8135.dtb \
                                imx6dl-tx6s-8135-mb7.dtb \
                                imx6dl-tx6u-8033.dtb \
                                imx6dl-tx6u-8033-mb7.dtb \
                                imx6dl-tx6u-8133.dtb \
                                imx6dl-tx6u-8133-mb7.dtb \
"
DEFAULT_DEVICETREE_imx6dl-tx6-nand ?= " \
                                imx6dl-tx6s-8034.dtb \
                                imx6dl-tx6s-8134-mb7.dtb \
                                imx6dl-tx6s-8034.dtb \
                                imx6dl-tx6s-8134-mb7.dtb \
                                imx6dl-tx6u-801x.dtb \
                                imx6dl-tx6u-811x.dtb \
                                imx6dl-tx6u-80xx-mb7.dtb \
                                imx6dl-tx6u-81xx-mb7.dtb \
"
DEFAULT_DEVICETREE_imx6q-tx6-emmc ?= " \
                                imx6q-tx6q-1020.dtb \
                                imx6q-tx6q-1020-comtft.dtb \
                                imx6q-tx6q-1036.dtb \
                                imx6q-tx6q-1036-mb7.dtb \
"
DEFAULT_DEVICETREE_imx6q-tx6-nand ?= " \
                                imx6q-tx6q-1010.dtb \
                                imx6q-tx6q-1110.dtb \
                                imx6q-tx6q-1010-comtft.dtb \
                                imx6q-tx6q-10x0-mb7.dtb \
                                imx6q-tx6q-11x0-mb7.dtb \
"
DEFAULT_DEVICETREE_imx6qp-tx6-emmc ?= " \
                                imx6qp-tx6qp-8037.dtb \
                                imx6qp-tx6qp-8137-mb7.dtb \
                                imx6qp-tx6qp-8037.dtb \
                                imx6qp-tx6qp-8137-mb7.dtb \
"
DEFAULT_DEVICETREE_imx6ul-tx6-emmc ?= " \
                                imx6ul-tx6ul-0011.dtb \
                                imx6ul-txul-5011-mb7.dtb \
				imx6ul-txul-5011-mainboard.dtb \
"
DEFAULT_DEVICETREE_imx6ul-tx6-nand ?= " \
                                imx6ul-tx6ul-0010.dtb \
                                imx6ul-txul-5010-mainboard.dtb \
                                imx6ul-txul-5010-mb7.dtb \
"
DEFAULT_DEVICETREE_imx6ull-txul-emmc ?= " \
                                imx6ull-txul-8013.dtb \
                                imx6ull-txul-8013-mainboard.dtb \
                                imx6ull-txul-8013-mb7.dtb \
"

KERNEL_DEVICETREE ?= "${DEFAULT_DEVICETREE}"

KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd","",d)}"

COMPATIBLE_MACHINE_tx8 = "(tx8m-.*|qs8m-.*)"
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

do_configure_prepend () {
    for f in ${@" ".join(find_cfgs(d))};do
        cat $f >> ${B}/.config
    done
}

do_install_append () {
    set -x
    install -v -d -m 0755 ${D}${FW_PATH}
    for f in ${FW_FILES};do
        src="${f//file:\//${WORKDIR}}"
        subdir="$(dirname "${src##*/firmware/}")"
        [ "${subdir##/*}" = "$subdir" ] || exit 1
        if [ "$subdir" != "." ];then
                install -v -d -m 0755 "${D}${FW_PATH}/$subdir"
        fi
        install -v -m 0644 "${src}" "${D}${FW_PATH}/$subdir"
    done
}
