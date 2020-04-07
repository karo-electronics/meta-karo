require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM_mx8 = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
LIC_FILES_CHKSUM_mx6 = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"

PROVIDES += "u-boot"

SRC_URI_mx8 = "git://source.codeaurora.org/external/imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH_mx8 = "imx_v2018.03_4.14.98_2.3.0"
SRCREV_mx8 = "0e207921e9a0db4d1dff27fee8b9a22a43a8fcc9"

SRCBRANCH_mx6 = "master"
SRC_URI_mx6 = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https;branch=${SRCBRANCH}"
SRCREV_mx6 = "KARO-TX6-2019-07-31"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"

do_deploy_append_tx8 () {
    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8mq-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/tools/mkimage  ${DEPLOYDIR}/${BOOT_TOOLS}/mkimage_uboot
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${type}
                fi
            done
            unset  j
        done
        unset  i
    fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE_tx8 = "(tx8m-.*|qs8m-.*)"
COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"

UBOOT_NAME_mx6 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx8 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
