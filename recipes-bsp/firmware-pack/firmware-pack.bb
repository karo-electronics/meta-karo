SECTION = "bootloaders"
SUMMARY = "Firmware Packaging"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit deploy staging

DEPENDS = "u-boot-karo fiptool-native tf-a-karo"
DEPENDS:append:rzg2 = " bootparameter-native"

S = "${WORKDIR}"

do_configure[noexec] = "1"

do_compile:rzg2() {
    # Create bl2_bp.bin
    bootparameter ${RECIPE_SYSROOT}/boot/bl2-${MACHINE}.bin bl2_bp.bin
    cat ${RECIPE_SYSROOT}/boot/bl2-${MACHINE}.bin >> bl2_bp.bin

    # Create fip.bin
    fiptool create --align 16 --soc-fw ${WORKDIR}/recipe-sysroot/boot/bl31-${MACHINE}.bin \
        --nt-fw ${RECIPE_SYSROOT}/boot/u-boot.bin fip.bin
}

# idk why it has to be defined before it can be overwritten
do_deploy() {

}

do_deploy:rzg2() {
    # Create deploy folder
    install -d ${DEPLOYDIR}

    # Copy fip images
    install -m 0644 -D ${S}/bl2_bp.bin ${DEPLOYDIR}/bl2_bp-${MACHINE}.bin
    install -m 0644 ${S}/fip.bin ${DEPLOYDIR}/fip-${MACHINE}.bin
}

do_deploy:stm32mp1() {
    # Copy fip images
    for config in ${TF_A_CONFIG}; do
        for dt in ${TF_A_DEVICETREE}; do
            fiptool create \
                --fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${config}/${dt}-fw-config.dtb \
                --hw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/u-boot.dtb \
                --nt-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${config}/u-boot-nodtb.bin \
                --tos-fw ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${config}/bl32.bin \
                --tos-fw-config ${DEPLOY_DIR_IMAGE}/${FIPTOOL_DIR}/${config}/${dt}-bl32.dtb \
                fip-${dt}-${config}.bin
            install -m 0644 -D fip-${dt}-${config}.bin ${DEPLOYDIR}/fip-${dt}-${config}.bin
        done
    done
}

addtask deploy after do_compile
