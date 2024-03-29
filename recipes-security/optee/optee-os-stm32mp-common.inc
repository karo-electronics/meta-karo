FILESEXTRAPATHS:prepend := "${THISDIR}/optee-os:"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/optee-os"
RPROVIDES:${PN} += "virtual/optee-os virtual/systemd-bootconf"

CVE_PRODUCT = "op-tee:op-tee_os"

B = "${WORKDIR}/build"
# Configure build dir for externalsrc class usage through devtool
EXTERNALSRC_BUILD:pn-${PN} = "${WORKDIR}/build"

DEPENDS += "dtc-native"
DEPENDS += "python3-pycryptodomex-native"
DEPENDS += "python3-pyelftools-native"
DEPENDS += "libgcc python3-cryptography-native"
DEPENDS += "python3-pillow-native"

do_compile[vardeps] += "EXTRA_OEMAKE"

inherit deploy python3native

# Default log level
ST_OPTEE_DEBUG_LOG_LEVEL ??= "3"
# ST_OPTEE_DEBUG_TRACE = "1"

# default core debug
ST_OPTEE_CORE_DEBUG ??= "n"

EXTRA_OEMAKE = "PLATFORM=${OPTEEMACHINE}"
EXTRA_OEMAKE += "CROSS_COMPILE_core=${HOST_PREFIX}"
EXTRA_OEMAKE += "CROSS_COMPILE_ta_arm64=${HOST_PREFIX}"
EXTRA_OEMAKE += "${@bb.utils.contains('TUNE_FEATURES', 'aarch64', 'CFG_ARM64_core=y ta-targets=ta_arm64', 'CFG_ARM32_core=y CROSS_COMPILE_ta_arm32=${HOST_PREFIX}', d)}"
#EXTRA_OEMAKE += "NOWERROR=1"
EXTRA_OEMAKE += "LDFLAGS="
EXTRA_OEMAKE += "CFG_STM32_CRYP=n"
EXTRA_OEMAKE += "CFG_WITH_TUI=n"
EXTRA_OEMAKE += "CFG_STPMIC1=n"
EXTRA_OEMAKE += "CFG_DRAM_SIZE=${BOARD_DRAM_SIZE}"

EXTRA_OEMAKE:append:stm32mp13 = " CFG_STM32MP13=y"

# debug and trace
EXTRA_OEMAKE += "${@bb.utils.contains('ST_OPTEE_DEBUG_TRACE', '1', 'CFG_TEE_CORE_LOG_LEVEL=${ST_OPTEE_DEBUG_LOG_LEVEL} CFG_TEE_CORE_DEBUG=${ST_OPTEE_CORE_DEBUG}', '', d)}"

OPTEE_ARCH:armv7a = "arm32"
OPTEE_ARCH:armv7ve = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

do_configure:prepend(){
    chmod 755 ${S}/scripts/bin_to_c.py
}

do_compile() {
    export CFLAGS="${CFLAGS} --sysroot=${STAGING_DIR_HOST}"
    export OPENSSL_MODULES=${STAGING_LIBDIR_NATIVE}/ossl-modules/

    oe_runmake -C ${S} O=${B}/${TF_A_DEVICETREE} CFG_EMBED_DTB_SOURCE_FILE=${TF_A_DEVICETREE}.dts
}

do_install() {
    #install TA devkit
    install -d ${D}${includedir}/optee/export-user_ta/

    for f in  ${B}/${TF_A_DEVICETREE}/export-ta_${OPTEE_ARCH}/* ; do
        cp -aRf  $f ${D}${includedir}/optee/export-user_ta/
    done
}

# Configure optee binaries
OPTEE_HEADER    = "tee-header_v2"
OPTEE_PAGEABLE  = "tee-pageable_v2"
OPTEE_PAGER     = "tee-pager_v2"
OPTEE_SUFFIX    = "bin"
# Output the ELF generated
ELF_DEBUG_ENABLE ?= ""
OPTEE_ELF = "tee"
OPTEE_ELF_SUFFIX = "elf"

do_deploy() {
    install -d ${DEPLOYDIR}/${FIPTOOL_DIR}
    for conf in ${TF_A_DEVICETREE}; do
        install -m 644 ${B}/${conf}/core/${OPTEE_HEADER}.${OPTEE_SUFFIX} ${DEPLOYDIR}/${FIPTOOL_DIR}/${OPTEE_HEADER}.${OPTEE_SUFFIX}
        install -m 644 ${B}/${conf}/core/${OPTEE_PAGER}.${OPTEE_SUFFIX} ${DEPLOYDIR}/${FIPTOOL_DIR}/${OPTEE_PAGER}.${OPTEE_SUFFIX}
        install -m 644 ${B}/${conf}/core/${OPTEE_PAGEABLE}.${OPTEE_SUFFIX} ${DEPLOYDIR}/${FIPTOOL_DIR}/${OPTEE_PAGEABLE}.${OPTEE_SUFFIX}
        if [ -n "${ELF_DEBUG_ENABLE}" ]; then
            install -m 644 ${B}/${conf}/core/${OPTEE_ELF}.${OPTEE_ELF_SUFFIX} ${DEPLOYDIR}/debug/${OPTEE_ELF}-${conf}.${OPTEE_ELF_SUFFIX}
        fi
    done
}
addtask deploy before do_build after do_compile

FILES:${PN} = "${nonarch_base_libdir}/firmware/"
FILES:${PN}-dev = "/usr/include/optee"

INSANE_SKIP:${PN}-dev = "staticdev"

INHIBIT_PACKAGE_STRIP = "1"
