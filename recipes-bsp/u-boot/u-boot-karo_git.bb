require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM_mx6 = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"
LIC_FILES_CHKSUM_stm32mp1 = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

PROVIDES += "u-boot"

DEPENDS_append = " bc-native"
DEPENDS_append_stm32mp1 = " bison-native"

SRCBRANCH_mx6 = "master"
SRC_URI_mx6 = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https;branch=${SRCBRANCH}"
SRCREV_mx6 = "6d7e3f066d2b977b02f6a471e749030d62ae5bde"

SRCBRANCH_stm32mp1 = "master"
SRC_URI_stm32mp1 = "git://git.denx.de/u-boot.git;protocol=git;branch=${SRCBRANCH}"
SRCREV_stm32mp1 = "e0718b3ab754860bd47677e6b4fc5b70da42c4ab"

S = "${WORKDIR}/git"

# append git hash to u-boot name
SCMVERSION ??= "y"
LOCALVERSION ??= "+karo"

UBOOT_LOCALVERSION = "${LOCALVERSION}"

do_compile_prepend() {
	if [ "${SCMVERSION}" = "y" ]; then
		# Add GIT revision to the local version
		head=`cd ${S} ; git rev-parse --verify --short HEAD 2> /dev/null`
		printf "%s%s%s" "${UBOOT_LOCALVERSION}" +g $head > ${S}/.scmversion
		printf "%s%s%s" "${UBOOT_LOCALVERSION}" +g $head > ${B}/.scmversion
    else
		printf "%s" "${UBOOT_LOCALVERSION}" > ${S}/.scmversion
		printf "%s" "${UBOOT_LOCALVERSION}" > ${B}/.scmversion
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

COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"
COMPATIBLE_MACHINE_stm32mp1 = "(txmp-.*|qsmp-.*)"

UBOOT_NAME = "u-boot-${MACHINE}.bin"
