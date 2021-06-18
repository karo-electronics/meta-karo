require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"
LIC_FILES_CHKSUM_stm32mp1 = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

PROVIDES += "u-boot"

DEPENDS_append = " bc-native"
DEPENDS_append_stm32mp1 = " bison-native xxd-native"

RDEPENDS_${PN}_append_stm32mp1 = " tf-a-stm32mp"

SRC_URI = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH_mx6 = "master"
SRCREV_mx6 = "c0b7b18e33d4fc17af2544de50816d539412d6e0"

SRCBRANCH_stm32mp1 = "karo-txmp"
SRCREV_stm32mp1 = "99c107845d260cdc019232d4f07e8dfa13c7ff90"

S = "${WORKDIR}/git"

# append git hash to u-boot name
SCMVERSION ??= "y"
LOCALVERSION ??= "+karo"

UBOOT_LOCALVERSION = "${LOCALVERSION}"
UBOOT_INITIAL_ENV = ""

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
        bbfatal "Wrong u-boot-karo configuration: please make sure to use UBOOT_CONFIG through BOOTSCHEME_LABELS config"
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
