# Ka-Ro U-Boot LOCALVERSION extension
#
# This allow to easy reuse of code between different U-Boot recipes
#
# The following options are supported:
#
#  SCMVERSION        Puts the Git hash in U-Boot local version
#  LOCALVERSION      Value used in LOCALVERSION (default to '-karo')
#
# Copyright 2026 (C) Ka-Ro electronics GmbH

SCMVERSION ??= "y"
LOCALVERSION ??= "-karo"

UBOOT_LOCALVERSION = "${LOCALVERSION}"

do_compile:prepend() {
    if [ "${SCMVERSION}" = "y" ]; then
        # Add GIT revision to the local version
        head="`cd ${S} ; git rev-parse --verify --short HEAD 2> /dev/null`"
        printf "%s+g%s" "${UBOOT_LOCALVERSION}" "$head" > ${S}/.scmversion
        printf "%s+g%s" "${UBOOT_LOCALVERSION}" "$head" > ${B}/.scmversion
    else
        printf "%s" "${UBOOT_LOCALVERSION}" > ${S}/.scmversion
        printf "%s" "${UBOOT_LOCALVERSION}" > ${B}/.scmversion
    fi
}
