SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules"

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "linux-4.14.y"
SRCREV = "v4.14.24"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
           file://defconfig \
           file://0001-patch-for-edt-m12.diff \
           file://0002-karo-dtbs.patch \
           file://imx6ull-bugfix.patch \
           file://txul-phy-reset.patch \
"

LOCALVERSION = "-stable"
KERNEL_IMAGETYPE = "uImage"

KERNEL_DEVICETREE_imx6dl-tx6-emmc ?= " \
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
KERNEL_DEVICETREE_imx6dl-tx6-nand ?= " \
                                  imx6dl-tx6s-8034.dtb \
                                  imx6dl-tx6s-8134-mb7.dtb \
                                  imx6dl-tx6s-8034.dtb \
                                  imx6dl-tx6s-8134-mb7.dtb \
                                  imx6dl-tx6u-801x.dtb \
                                  imx6dl-tx6u-811x.dtb \
                                  imx6dl-tx6u-80xx-mb7.dtb \
                                  imx6dl-tx6u-81xx-mb7.dtb \
"
KERNEL_DEVICETREE_imx6q-tx6-emmc ?= " \
                                 imx6q-tx6q-1020.dtb \
                                 imx6q-tx6q-1020-comtft.dtb \
                                 imx6q-tx6q-1036.dtb \
                                 imx6q-tx6q-1036-mb7.dtb \
"
KERNEL_DEVICETREE_imx6q-tx6-nand ?= " \
                                 imx6q-tx6q-1010.dtb \
                                 imx6q-tx6q-1110.dtb \
                                 imx6q-tx6q-1010-comtft.dtb \
                                 imx6q-tx6q-10x0-mb7.dtb \
                                 imx6q-tx6q-11x0-mb7.dtb \
"
KERNEL_DEVICETREE_imx6qp-tx6-emmc ?= " \
                                  imx6qp-tx6qp-8037.dtb \
                                  imx6qp-tx6qp-8137-mb7.dtb \
                                  imx6qp-tx6qp-8037.dtb \
                                  imx6qp-tx6qp-8137-mb7.dtb \
"
KERNEL_DEVICETREE_imx6ul-tx6-emmc ?= " \
                                  imx6ul-tx6ul-0011.dtb \
                                  imx6ul-txul-5011-mainboard.dtb \
                                  imx6ul-txul-5011-mb7.dtb \
"
KERNEL_DEVICETREE_imx6ul-tx6-nand ?= " \
                                  imx6ul-tx6ul-0010.dtb \
                                  imx6ul-txul-5010-mainboard.dtb \
                                  imx6ul-txul-5010-mb7.dtb \
"
KERNEL_DEVICETREE_imx6ull-txul-emmc ?= " \
                                   imx6ull-txul-8013.dtb \
                                   imx6ull-txul-8013-mainboard.dtb \
                                   imx6ull-txul-8013-mb7.dtb \
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
