SECTION = "bootloaders"
SUMMARY = "Firmware Packaging"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit deploy staging

DEPENDS = "u-boot-karo fiptool-native"
DEPENDS:append:rzg2 = " bootparameter-native tf-a-rzg2"
DEPENDS:append:stm32mp1 = " tf-a-stm32mp"

S = "${WORKDIR}"

do_configure[noexec] = "1"

do_compile:rzg2() {

	# Create bl2_bp.bin
	bootparameter ${WORKDIR}/recipe-sysroot/boot/bl2-${MACHINE}.bin bl2_bp.bin
	cat ${WORKDIR}/recipe-sysroot/boot/bl2-${MACHINE}.bin >> bl2_bp.bin

	# Create fip.bin
	fiptool create --align 16 --soc-fw ${WORKDIR}/recipe-sysroot/boot/bl31-${MACHINE}.bin --nt-fw ${WORKDIR}/recipe-sysroot/boot/u-boot.bin fip.bin

	# Convert to srec
	objcopy -O srec --adjust-vma=0x00011E00 --srec-forceS3 -I binary bl2_bp.bin bl2_bp.srec
	objcopy -I binary -O srec --adjust-vma=0x0000 --srec-forceS3 fip.bin fip.srec
}

do_compile:stm32mp1() {
    for config in ${TF_A_CONFIG}; do
        case ${config} in
        serialboot)
            for dt in ${TF_A_DEVICETREE}; do
                fiptool create \
                --fw-config ${WORKDIR}/recipe-sysroot/binaries/${config}/${dt}-fw-config.dtb \
                --hw-config ${WORKDIR}/recipe-sysroot/binaries/u-boot-mfg.dtb \
                --nt-fw ${WORKDIR}/recipe-sysroot/binaries/u-boot-nodtb-mfg.bin \
                --tos-fw ${WORKDIR}/recipe-sysroot/binaries/${config}/bl32.bin \
                --tos-fw-config ${WORKDIR}/recipe-sysroot/binaries/${config}/${dt}-bl32.dtb \
                fip-${dt}-${config}.bin
            done
            ;;

        trusted)
            for dt in ${TF_A_DEVICETREE}; do
                fiptool create \
                --fw-config ${WORKDIR}/recipe-sysroot/binaries/${config}/${dt}-fw-config.dtb \
                --hw-config ${WORKDIR}/recipe-sysroot/binaries/u-boot-trusted.dtb \
                --nt-fw ${WORKDIR}/recipe-sysroot/binaries/u-boot-nodtb-trusted.bin \
                --tos-fw ${WORKDIR}/recipe-sysroot/binaries/${config}/bl32.bin \
                --tos-fw-config ${WORKDIR}/recipe-sysroot/binaries/${config}/${dt}-bl32.dtb \
                fip-${dt}-${config}.bin
            done
            ;;
        esac
    done
}

# idk why it has to be defined before it can be overwritten
do_deploy() {

}

do_deploy:rzg2() {
	# Create deploy folder
	install -d ${DEPLOYDIR}

	# Copy fip images
	install -m 0644 ${S}/bl2_bp.bin ${DEPLOYDIR}/bl2_bp-${MACHINE}.bin
	install -m 0644 ${S}/bl2_bp.srec ${DEPLOYDIR}/bl2_bp-${MACHINE}.srec
	install -m 0644 ${S}/fip.bin ${DEPLOYDIR}/fip-${MACHINE}.bin
	install -m 0644 ${S}/fip.srec ${DEPLOYDIR}/fip-${MACHINE}.srec
}

do_deploy:stm32mp1() {
	# Create deploy folder
	install -d ${DEPLOYDIR}

	# Copy fip images
	install -m 0644 ${S}/fip-*.bin ${DEPLOYDIR}
}

addtask deploy after do_compile
