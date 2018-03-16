SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules"

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "linux-4.14.y"
SRCREV = "v4.14.24"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
           file://defconfig \
           file://imx6ull-bugfix.patch \
           file://txul-phy-reset.patch \
"

LOCALVERSION = "-stable"
KERNEL_IMAGETYPE = "uImage"

KERNEL_DEVICETREE_imx6dl-tx6-emmc ?= " \
                                  imx6dl-tx6s-8035.dtb \
                                  imx6dl-tx6u-8{0,1}1x.dtb \
                                  imx6dl-tx6u-8033.dtb \
"
KERNEL_DEVICETREE_imx6dl-tx6-nand ?= " \
                                  imx6dl-tx6dl-comtft.dtb \
                                  imx6dl-tx6s-8034.dtb \
                                  imx6dl-tx6u-81xx-mb7.dtb \
"
KERNEL_DEVICETREE_imx6q-tx6-emmc ?= " \
                                 imx6q-tx6q-1020{,-comtft}.dtb \
                                 imx6q-tx6q-1036.dtb \
"
KERNEL_DEVICETREE_imx6qp-tx6-emmc ?= " \
"
KERNEL_DEVICETREE_imx6q-tx6-nand ?= " \
                                 imx6q-tx6q-1{0,1}10.dtb \
                                 imx6q-tx6q-1010-comtft.dtb \
                                 imx6q-tx6q-11x0-mb7.dtb \
"
KERNEL_DEVICETREE_imx6ul-tx6-emmc ?= " \
                                  imx6ul-tx6ul-0011.dtb \
                                  imx6ul-tx6ul-mainboard.dtb \
"
KERNEL_DEVICETREE_imx6ul-tx6-nand ?= " \
                                  imx6ul-tx6ul-0010.dtb \
"
KERNEL_DEVICETREE_imx6ull-tx6-emmc ?= " \
"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*|imx6.*-tx.*)"

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
