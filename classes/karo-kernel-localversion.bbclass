# Freescale Kernel LOCALVERSION extension
#
# This allow to easy reuse of code between different kernel recipes
#
# The following options are supported:
#
#  SCMVERSION        Puts the Git hash in kernel local version
#  LOCALVERSION      Value used in LOCALVERSION (default to '-karo')
#
# Copyright 2014, 2015 (C) O.S. Systems Software LTDA.

SCMVERSION ??= "y"
LOCALVERSION ??= "-karo"

# LINUX_VERSION_EXTENSION is used as CONFIG_LOCALVERSION by kernel-yocto class
LINUX_VERSION_EXTENSION ?= "${LOCALVERSION}"

do_kernel_localversion[dirs] += "${S} ${B}"
do_kernel_localversion() {
	if [ "${SCMVERSION}" = "y" ]; then
        # Add GIT revision to the local version
        head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
        if ! [ -s "${S}/.scmversion" ] || ! grep -q "$head" ${S}/.scmversion;then
            echo "+g$head" > "${S}/.scmversion"
        fi
        install -v "${WORKDIR}/${KBUILD_DEFCONFIG}" "${B}/.config"
        sed -i '/CONFIG_LOCALVERSION/d' "${B}/.config"
        echo 'CONFIG_LOCALVERSION="${LOCALVERSION}"' >> "${B}/.config"
    fi
}
addtask kernel_localversion before do_configure after do_patch do_kernel_configme
