DESCRIPTION = "U-Boot for Ka-Ro electronics Computer-On-Modules (STM32)."
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

PN = "u-boot-karo"

UBOOT_SRC_DEFAULT = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https"
UBOOT_BRANCH_DEFAULT = "karo-stm32mp2-v2022.10"
UBOOT_REV_DEFAULT = "6601c677f17bf7ff86a87b3784b7ac7a087962f6"

CVE_PRODUCT = "denx:u-boot"
CVE_VERSION = "2022.10"

DEPENDS:append = " ${@ "tf-a-tools-native" if "stm32mp2" in d.getVar('MACHINEOVERRIDES').split(':') else "fiptool-native"}"

UBOOT_BOARD_DIR:stm32mp1 = "board/karo/stm32mp1"
UBOOT_BOARD_DIR:stm32mp2 = "board/karo/stm32mp2"

require u-boot-karo.inc

do_deploy:stm32mp1() {
    dt=${TF_A_DEVICETREE}
    # Create fip images
    i=0
    for config in ${UBOOT_MACHINE}; do
        i=$(expr $i + 1)
        j=0
        for type in ${UBOOT_CONFIG}; do
            j=$(expr $j + 1)
            [ $j -lt $i ] && continue
            k=0
            for cfg in ${TF_A_CONFIGS}; do
                k=$(expr $k + 1)
                [ $k -lt $j ] && continue
                if [ "${SIGN_ENABLE}" = 1 ];then
                    cert_create \
                        -n --tfw-nvctr 0 --ntfw-nvctr 0 \
                        --key-alg ecdsa --hash-alg sha256 \
                        --rot-key ${SB_KEYS_DIR}/${SIGN_KEY} \
                        --rot-key-pwd ${SIGN_KEY_PASS} \
                        --tb-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/bl2.bin \
                        --tb-fw-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tb_fw.crt \
                        --tos-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-header_v2-${dt}.bin \
                        --tos-fw-extra1 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pager_v2-${dt}.bin \
                        --tos-fw-extra2 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pageable_v2-${dt}.bin \
                        --fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/${dt}-fw-config.dtb \
                        --hw-config ${B}/${config}/u-boot.dtb \
                        --nt-fw ${B}/${config}/u-boot-nodtb.bin \
                        --trusted-key-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/trusted_key.crt \
                        --tos-fw-key-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tos_fw_key.crt \
                        --tos-fw-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tos_fw_content.crt \
                        --nt-fw-key-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/nt_fw_key.crt \
                        --nt-fw-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/nt_fw_content.crt \
                        --stm32mp-cfg-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/stm32mp_cfg_cert.crt

                    fiptool create \
                        --tb-fw-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tb_fw.crt \
                        --fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/${dt}-fw-config.dtb \
                        --hw-config ${B}/${config}/u-boot.dtb \
                        --nt-fw ${B}/${config}/u-boot-nodtb.bin \
                        --trusted-key-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/trusted_key.crt \
                        --tos-fw-key-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tos_fw_key.crt \
                        --tos-fw-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tos_fw_content.crt \
                        --nt-fw-key-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/nt_fw_key.crt \
                        --nt-fw-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/nt_fw_content.crt \
                        --stm32mp-cfg-cert ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/stm32mp_cfg_cert.crt \
                        --tos-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-header_v2-${dt}.bin \
                        --tos-fw-extra1 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pager_v2-${dt}.bin \
                        --tos-fw-extra2 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pageable_v2-${dt}.bin \
                        ${DEPLOYDIR}/fip-${dt}-${type}_Signed.bin
                else
                    fiptool create \
                        --fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/${dt}-fw-config.dtb \
                        --hw-config ${B}/${config}/u-boot.dtb \
                        --nt-fw ${B}/${config}/u-boot-nodtb.bin \
                        --tos-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-header_v2-${dt}.bin \
                        --tos-fw-extra1 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pager_v2-${dt}.bin \
                        --tos-fw-extra2 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pageable_v2-${dt}.bin \
                        ${DEPLOYDIR}/fip-${dt}-${type}.bin
                fi
                break
            done
            break
        done
    done
}

do_deploy:stm32mp2() {
set -x
    dt=${TF_A_DEVICETREE}
    # Create fip images
    i=0
    for config in ${UBOOT_MACHINE}; do
        i=$(expr $i + 1)
        j=0
        for type in ${UBOOT_CONFIG}; do
            j=$(expr $j + 1)
            [ $j -lt $i ] && continue
            k=0
            for cfg in ${TF_A_CONFIGS}; do
                k=$(expr $k + 1)
                [ $k -lt $j ] && continue
                fiptool create \
                    --nt-fw ${B}/${config}/u-boot-nodtb.bin \
                    --hw-config ${B}/${config}/u-boot.dtb \
		    --ddr-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/ddr_pmu-${OPTEE_CONF}.bin \
                    --soc-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/bl31-${MACHINE}.bin \
                    --soc-fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/${dt}-bl31.dtb \
                    --fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${cfg}/${dt}-fw-config.dtb \
                    --tos-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-header_v2-${OPTEE_CONF}.bin \
                    --tos-fw-extra1 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pager_v2-${OPTEE_CONF}.bin \
                    --tos-fw-extra2 ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/tee-pageable_v2-${OPTEE_CONF}.bin \
                    ${DEPLOYDIR}/fip-${dt}-${type}.bin
                break
            done
            break
        done
    done
}

do_deploy:append() {
    # create bash script to start 'fastboot' on the target via dfu-util
    cat <<EOF > ${DEPLOYDIR}/fastboot.cmd
fastboot 0
EOF
    for config in ${UBOOT_MACHINE};do
        ${B}/${config}/tools/mkimage -C none -A arm -T script -d ${DEPLOYDIR}/fastboot.cmd ${DEPLOYDIR}/fastboot.img
        break
    done
}

COMPATIBLE_MACHINE = "(txmp-.*|qsmp-.*)"
