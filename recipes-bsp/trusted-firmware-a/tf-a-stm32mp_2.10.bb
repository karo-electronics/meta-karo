require tf-a-stm32mp-common.inc

inherit deploy

COMPATIBLE_MACHINE = "(stm32mpcommon)"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SUMMARY = "Trusted Firmware-A for STM32MP"

PROVIDES += "virtual/trusted-firmware-a"
PROVIDES += "tf-a-karo"

# ------------------------------------
# Set MBEDTLS support
TFA_MBEDTLS_DIR ?= "mbedtls"
# MBEDTLS v3.6.0
SRC_URI_MBEDTLS = "git://github.com/ARMmbed/mbedtls.git;protocol=https;destsuffix=git/${TFA_MBEDTLS_DIR};nobranch=1;name=mbedtls"
SRCREV_mbedtls = "2ca6c285a0dd3f33982dd57299012dacab1ff206"
LIC_FILES_CHKSUM_MBEDTLS = "file://mbedtls/LICENSE;md5=379d5819937a6c2f1ef1630d341e026d"
LICENSE_MBEDTLS = "Apache-2.0"
# Add MBEDTLS to our sources
SRC_URI:append = " ${SRC_URI_MBEDTLS}"
# Update license variables
LICENSE:append = " & ${LICENSE_MBEDTLS}"
LIC_FILES_CHKSUM:append = " ${LIC_FILES_CHKSUM_MBEDTLS}"
# Add mbed TLS to version
SRCREV_FORMAT:append = "_mbedtls"
# ------------------------------------

B = "${WORKDIR}/build"
# Configure build dir for externalsrc class usage through devtool
EXTERNALSRC_BUILD:pn-${PN} = "${WORKDIR}/build"

DEPENDS += "dtc-native"
DEPENDS:stm32mp2common += "tf-a-tools-native"

SIGN_KEY ?= ""
SIGN_KEY_PASS ?= ""
SIGN_PUB_KEY ?= ""
SIGN_ENABLE ?= "0"
SIGN_TOOL ?= ""

TF_A_CONFIG_optee:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' MBEDTLS_DIR=${S}/${TFA_MBEDTLS_DIR}', '', d)}"
TF_A_CONFIG_optee:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' ROT_KEY=${SB_KEYS_DIR}/${SIGN_KEY}', '', d)}"
TF_A_CONFIG_optee:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' TRUSTED_BOARD_BOOT=1', '', d)}"
TF_A_CONFIG_optee:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' GENERATE_COT=1', '', d)}"
TF_A_CONFIG_optee:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' ROT_KEY_PWD=${SIGN_KEY_PASS}', '', d)}"
TF_A_CONFIG_optee:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' KEY_ALG=ecdsa', '', d)}"

TF_A_CONFIG_usb:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' MBEDTLS_DIR=${S}/${TFA_MBEDTLS_DIR}', '', d)}"
TF_A_CONFIG_usb:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' ROT_KEY=${SB_KEYS_DIR}/${SIGN_KEY}', '', d)}"
TF_A_CONFIG_usb:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' TRUSTED_BOARD_BOOT=1', '', d)}"
TF_A_CONFIG_usb:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' GENERATE_COT=1', '', d)}"
TF_A_CONFIG_usb:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' ROT_KEY_PWD=${SIGN_KEY_PASS}', '', d)}"
TF_A_CONFIG_usb:append = "${@bb.utils.contains('SIGN_ENABLE', '1', ' KEY_ALG=ecdsa', '', d)}"

# Define default TF-A namings
TF_A_BASENAME ?= "tf-a"
TF_A_SUFFIX ?= "stm32"

TF_A_PLATFORM = "stm32mp1"
TF_A_ARCH = "aarch32"
TF_A_ARM_MAJOR = "7"

TF_A_PLATFORM:stm32mp2 = "stm32mp2"
TF_A_ARCH:stm32mp2 = "aarch64"
TF_A_ARM_MAJOR:stm32mp2 = "8"

# Output the ELF generated
ELF_DEBUG_ENABLE ?= ""
TF_A_ELF_SUFFIX = "elf"

BL1_NAME ?= "bl1/bl1"
BL1_ELF = "${BL1_NAME}.${TF_A_ELF_SUFFIX}"
BL1_BASENAME = "${@os.path.basename(d.getVar("BL1_NAME"))}"

BL2_NAME ?= "bl2/bl2"
BL2_ELF = "${BL2_NAME}.${TF_A_ELF_SUFFIX}"
BL2_BASENAME = "${@os.path.basename(d.getVar("BL2_NAME"))}"

BL31_NAME ?= "bl31/bl31"
BL31_ELF = "${BL31_NAME}.${TF_A_ELF_SUFFIX}"
BL31_BASENAME = "${@os.path.basename(d.getVar("BL31_NAME"))}"

BL32_NAME ?= "bl32/bl32"
BL32_ELF = "${BL32_NAME}.${TF_A_ELF_SUFFIX}"
BL32_BASENAME = "${@os.path.basename(d.getVar("BL32_NAME"))}"

TF_A_FWDDR ?= "0"
TF_A_FWDDR:stm32mp2common = "1"

FWDDR_NAME = "ddr_pmu"
FWDDR_SUFFIX = "bin"

FIP_FW_DDR ?= ""
FIP_FW_DDR:stm32mp2 = "ddr_pmu"

# -----------------------------------------------
# Enable use of work-shared folder
STAGING_TFA_DIR = "${TMPDIR}/work-shared/${MACHINE}/tfa-source"
# Make sure to move ${S} to STAGING_TFA_DIR. We can't just
# create the symlink in advance as the git fetcher can't cope with
# the symlink.
do_unpack[cleandirs] += " ${S} ${STAGING_TFA_DIR}"
do_clean[cleandirs] += " ${S} ${STAGING_TFA_DIR}"

do_unpack:append () {
    s = d.getVar("STAGING_TFA_DIR")
    b = d.getVar("B")
    os.symlink(os.path.relpath(b, os.path.realpath(s)), os.path.join(s, 'build'))
}

base_do_unpack:append () {
    # Specific part to update devtool-source class
    if bb.data.inherits_class('devtool-source', d):
        # We don't want to move the source to STAGING_TFA_DIR here
        if d.getVar('STAGING_TFA_DIR', d):
            d.setVar('STAGING_TFA_DIR', '${S}')

    # Copy/Paste from kernel class with adaptation to TFA var
    s = d.getVar("S")
    while len(s) > 1 and s[-1] == '/':
        # drop trailing slashes, so that os.symlink(tfasrc, s) doesn't use s as directory name and fail
        s = s[:-1]
    tfasrc = d.getVar("STAGING_TFA_DIR")
    if s != tfasrc:
        bb.utils.mkdirhier(tfasrc)
        bb.utils.remove(tfasrc, recurse=True)
        if d.getVar("EXTERNALSRC"):
            # With EXTERNALSRC S will not be wiped so we can symlink to it
            os.symlink(s, tfasrc)
        else:
            import shutil
            shutil.move(s, tfasrc)
            os.symlink(tfasrc, s)
}


# Manage to export any specific setting for defined configs
python tfaconfig_env () {
    import filecmp
    import shutil
    if d.getVar('TF_A_CONFIG'):
        tfaconfig_env = os.path.join(d.getVar('T'), "tfaconfig_env")
        try:
            tmpfile = "tfaconfig_env" + ".tmp"
            f = open(tmpfile, 'w')
            for config in d.getVar('TF_A_CONFIG').split():
                f.write( "export TF_A_CONFIG_%s=\"%s\"\n" % (config, d.getVar(('TF_A_CONFIG_' + config))) )
            f.close()
            if os.path.exists(tfaconfig_env) and filecmp.cmp(tfaconfig_env, tmpfile):
                bb.note("config unchanged; removing '%s'" % tmpfile)
                os.remove(tmpfile)
            else:
                if os.path.exists(tfaconfig_env):
                    bb.note("Removing '%s' due to changed config" % d.getVar('B'))
                    shutil.rmtree(d.getVar('B'))
                shutil.move(tmpfile, tfaconfig_env)
        except:
            pass
}
do_compile[prefuncs] += "tfaconfig_env"

# Manage proper update for TF_A_CONFIG_* var
do_compile[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'usb', 'TF_A_CONFIG_usb', '', d)}"
do_compile[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'trusted', 'TF_A_CONFIG_trusted', '', d)}"
do_compile[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'optee', 'TF_A_CONFIG_optee', '', d)}"


do_compile() {
    . ${T}/tfaconfig_env

    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    for config in ${TF_A_CONFIG}; do
        # Get any specific EXTRA_OEMAKE for current config
        eval local add_extraoemake=\"\$TF_A_CONFIG_${config}\"
        if [ -n "${TF_A_DEVICETREE}" ]; then
            for dt in ${TF_A_DEVICETREE}; do
                oe_runmake -C ${S} DTB_FILE_NAME=${dt}.dtb BUILD_PLAT=${B}/${config} ${add_extraoemake}
                cp ${B}/${config}/${TF_A_BASENAME}-${dt}.${TF_A_SUFFIX} ${B}/${config}/${TF_A_BASENAME}-${dt}-${config}.${TF_A_SUFFIX}
            done
        else
            oe_runmake -C ${S} BUILD_PLAT=${B}/${config} ${add_extraoemake}
        fi

        if [ "${SIGN_ENABLE}" = 1 ];then
            # Sign tf-a binary
            bbnote "${SIGN_TOOL} \
                -bin "${B}/${config}/${TF_A_BASENAME}-${dt}-${config}.${TF_A_SUFFIX}" \
                -o "${B}/${config}/${TF_A_BASENAME}-${dt}-${config}_Signed.${TF_A_SUFFIX}" \
                --password "${SIGN_KEY_PASS}" \
                --public-key "${SB_KEYS_DIR}/${SIGN_PUB_KEY}" \
                --private-key "${SB_KEYS_DIR}/${SIGN_KEY}" \
                --type fsbl \
                --silent "

            ${SIGN_TOOL} \
                -bin "${B}/${config}/${TF_A_BASENAME}-${dt}-${config}.${TF_A_SUFFIX}" \
                -o "${B}/${config}/${TF_A_BASENAME}-${dt}-${config}_Signed.${TF_A_SUFFIX}" \
                --password ${SIGN_KEY_PASS} \
                --public-key ${SB_KEYS_DIR}/${SIGN_PUB_KEY} \
                --private-key ${SB_KEYS_DIR}/${SIGN_KEY} \
                --type fsbl \
                --silent
        fi
    done

    if [ "${TF_A_FWDDR}" = 1 ];then
        ddr_target=lpddr4
        if [ -s "${S}/drivers/st/ddr/phy/firmware/bin/${ddr_target}_pmu_train.bin" ]; then
            cp "${S}/drivers/st/ddr/phy/firmware/bin/${ddr_target}_pmu_train.bin" "${B}/${FWDDR_NAME}-${dt}.${FWDDR_SUFFIX}"
        else
            bbfatal "Missing ddr firmware file ${ddr_target}_pmu_train.bin for ${dt}"
        fi
    fi
}

do_deploy() {
    install -v -d ${DEPLOYDIR}/${FIPTOOL_DIR}

    if [ -n "${TF_A_DEVICETREE}" ]; then
        for dt in ${TF_A_DEVICETREE}; do
            if [ -n "${FIP_FW_DDR}" ]; then
                # Install DDR firmware binary
                install -m 644 "${B}/${FWDDR_NAME}-${dt}.${FWDDR_SUFFIX}" \
                        "${DEPLOYDIR}/${FIPTOOL_DIR}/${FWDDR_NAME}-${dt}.${FWDDR_SUFFIX}"
                bbnote "fiptool create \
                        --ddr-fw ${DEPLOYDIR}/${FIPTOOL_DIR}/${FIP_FW_DDR}-${dt}.${FWDDR_SUFFIX} \
                        ${DEPLOYDIR}/${FIPTOOL_DIR}/fip-ddr-${dt}.${FWDDR_SUFFIX}"
                which fiptool
                fiptool create \
                        --ddr-fw ${DEPLOYDIR}/${FIPTOOL_DIR}/${FIP_FW_DDR}-${dt}.${FWDDR_SUFFIX} \
                        ${DEPLOYDIR}/${FIPTOOL_DIR}/fip-ddr-${dt}.${FWDDR_SUFFIX}
            fi

            for config in ${TF_A_CONFIG}; do
                install -v -d ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}

                if [ -n "${FIP_FW_DDR}" ]; then
                    ln -vs ${FIPTOOL_DIR}/fip-ddr-${dt}.${FWDDR_SUFFIX} ${DEPLOYDIR}/fip-ddr-${dt}-${config}.${FWDDR_SUFFIX}
                fi

                if [ -f ${B}/${config}/bl32.bin ]; then
                    install -v ${B}/${config}/bl32.bin ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}
                fi

                install -v ${B}/${config}/${TF_A_BASENAME}-${dt}-${config}.${TF_A_SUFFIX} ${DEPLOYDIR}
                if [ "${SIGN_ENABLE}" = 1 ];then
                    install -v ${B}/${config}/${TF_A_BASENAME}-${dt}-${config}_Signed.${TF_A_SUFFIX} ${DEPLOYDIR}
                fi
                install -v ${B}/${config}/bl2.bin ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}
                if [ -f ${B}/${config}/bl31.bin ]; then
                    install -v ${B}/${config}/bl31.bin ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}/bl31-${MACHINE}.bin
                fi

                if [ -f ${B}/${config}/fdts/${dt}-bl31.dtb ]; then
                    install -v ${B}/${config}/fdts/${dt}-bl31.dtb ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}
                fi

                if [ -f ${B}/${config}/fdts/${dt}-bl32.dtb ]; then
                    install -v ${B}/${config}/fdts/${dt}-bl32.dtb ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}
                fi

                install -v ${B}/${config}/fdts/${dt}-fw-config.dtb ${DEPLOYDIR}/${FIPTOOL_DIR}/${config}
            done
        done
    else
        # Get tf-a binary basename to copy
        tf_a_binary_basename=$(find ${B}/${config} -name "${TF_A_BASENAME}-*.${TF_A_SUFFIX}" -exec basename {} \; | sed 's|\.'"${TF_A_SUFFIX}"'||g')
        install -v ${B}/${config}/${tf_a_binary_basename}.${TF_A_SUFFIX} ${DEPLOYDIR}/${FIPTOOL_DIR}/${tf_a_binary_basename}-${config}.${TF_A_SUFFIX}
    fi

    if [ -n "${ELF_DEBUG_ENABLE}" ]; then
        for config in ${TF_A_CONFIG}; do
            if [ -f ${B}/${config}/${BL1_ELF} ]; then
                install -v ${B}/${config}/${BL1_ELF} ${DEPLOYDIR}/${FIPTOOL_DIR}/${TF_A_BASENAME}-${BL1_BASENAME}-${config}.${TF_A_ELF_SUFFIX}
            fi
            if [ -f ${B}/${config}/${BL2_ELF} ]; then
                install -v ${B}/${config}/${BL2_ELF} ${DEPLOYDIR}/${FIPTOOL_DIR}/${TF_A_BASENAME}-${BL2_BASENAME}-${config}.${TF_A_ELF_SUFFIX}
                if [ "${TF_A_FWDDR}" = "1" ]; then
                    install -d "${DEPLOYDIR}/arm-trusted-firmware/ddr"
                fi

            fi
            if [ -f ${B}/${config}/${BL32_ELF} ]; then
                install -v ${B}/${config}/${BL32_ELF} ${DEPLOYDIR}/${FIPTOOL_DIR}/${TF_A_BASENAME}-${BL32_BASENAME}-${config}.${TF_A_ELF_SUFFIX}
            fi
        done
    fi
}
addtask deploy before do_build after do_compile

S = "${WORKDIR}/git"
