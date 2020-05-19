require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM_mx8m = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
LIC_FILES_CHKSUM_mx6 = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"
LIC_FILES_CHKSUM_stm32mp1 = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

PROVIDES += "u-boot"

DEPENDS_append = " bc-native"
DEPENDS_append_mx8m = " dtc-native"
DEPENDS_append_stm32mp1 = " bison-native"

SRC_URI_mx8m = "git://source.codeaurora.org/external/imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH_mx8m = "imx_v2018.03_4.14.98_2.3.0"
SRCREV_mx8m = "0e207921e9a0db4d1dff27fee8b9a22a43a8fcc9"

SRCBRANCH_mx6 = "master"
SRC_URI_mx6 = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https;branch=${SRCBRANCH}"
SRCREV_mx6 = "KARO-TX6-2019-07-31"

SRCBRANCH_stm32mp1 = "master"
SRC_URI_stm32mp1 = "git://git.denx.de/u-boot.git;protocol=git;branch=${SRCBRANCH}"
SRCREV_stm32mp1 = "e0718b3ab754860bd47677e6b4fc5b70da42c4ab"

S = "${WORKDIR}/git"

BOOT_TOOLS_mx8m = "imx-boot-tools"

do_deploy_append_mx8m () {
    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8mq-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ]; then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]; then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME} ${DEPLOYDIR}/${BOOT_TOOLS}
                    install ${B}/${config}/tools/mkimage ${DEPLOYDIR}/${BOOT_TOOLS}/mkimage_uboot
                    install ${B}/${config}/u-boot-nodtb.bin ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${type}
                fi
            done
            unset  j
        done
        unset  i
    fi
}

# -----------------------------------------------------------------------------
# Append deploy to handle specific device tree binary deployement
#
do_deploy_append_stm32mp1 () {
    if [ -n "${UBOOT_CONFIG}" ]; then
        unset i j k
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]; then
                    install -m 644 ${B}/${config}/u-boot-${type}.bin ${DEPLOYDIR}

                    # As soon as SPL binary exists, install it
                    # This allow to mix u-boot configuration, with and without SPL
                    if [ -f ${B}/${config}/u-boot-spl.stm32 ]; then
                        install -m 644 ${B}/${config}/u-boot-spl.stm32 ${DEPLOYDIR}
                    fi
                fi
            done
            unset j
        done
        unset i
    else
        bbfatal "Wrong u-boot-stm32mp configuration: please make sure to use UBOOT_CONFIG through BOOTSCHEME_LABELS config"
    fi
}

# ---------------------------------------------------------------------
# Avoid QA Issue: No GNU_HASH in the elf binary
INSANE_SKIP_${PN} += "ldflags"
# ---------------------------------------------------------------------
# Avoid QA Issue: ELF binary has relocations in .text
# (uboot no need -fPIC option : remove check)
INSANE_SKIP_${PN} += "textrel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE_mx8 = "(tx8m-.*|qs8m-.*)"
COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"
COMPATIBLE_MACHINE_stm32mp1 = "(txmp-.*|qsmp-.*)"

UBOOT_NAME = "u-boot-${MACHINE}.bin"
