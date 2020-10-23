require u-boot-karo-common_git.inc
require u-boot-karo_git.inc

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
PROVIDES += "u-boot"

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


UBOOT_NAME = "u-boot-${MACHINE}.bin"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
"
