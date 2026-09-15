DESCRIPTION = "U-Boot for Ka-Ro electronics Computer-On-Modules (Renesas)."
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

PN = "u-boot-karo"

UBOOT_SRC_DEFAULT = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https"
UBOOT_BRANCH_DEFAULT = "karo-txrz"
UBOOT_REV_DEFAULT = "23d3b5822290db7f0413074c7d920fb81db1739f"

CVE_PRODUCT = "denx:u-boot"
CVE_VERSION = "2020.10"

DEPENDS:append = " fiptool-native"

UBOOT_BOARD_DIR = "board/karo/txrz"

require u-boot-karo.inc

do_deploy() {
    # Create fip.bin
    install -v -d "${DEPLOYDIR}/${FIPTOOL_DIR}"
    if [ -n "${UBOOT_CONFIG}" ];then
        i=0
        for config in ${UBOOT_MACHINE};do
            i=$(expr $i + 1)
            j=0
            for type in ${UBOOT_CONFIG};do
                j=$(expr $j + 1)
                [ $j -lt $i ] && continue
                fiptool create --align 16 \
                    --soc-fw "${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/bl31-${MACHINE}.bin" \
                    --nt-fw "${B}/${config}/u-boot-${type}.bin" "${DEPLOYDIR}/${FIPTOOL_DIR}/fip-${MACHINE}-${type}.bin"
                if [ $i = 1 ];then
                    ln -s ${FIPTOOL_DIR}/fip-${MACHINE}-${type}.bin "${DEPLOYDIR}/fip-${MACHINE}.bin"
                fi
                break
            done
        done
    else
        fiptool create --align 16 --soc-fw "${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/bl31-${MACHINE}.bin" \
            --nt-fw "${B}/u-boot.bin" "${DEPLOYDIR}/${FIPTOOL_DIR}/fip-${MACHINE}.bin"
    fi
}

COMPATIBLE_MACHINE = "(txrz-.*|qsrz-.*)"
