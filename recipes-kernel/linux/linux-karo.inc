# Copyright (C) 2012, 2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "GPLv2"

LINUX_VERSION_EXTENSION ?= "-karo"

require recipes-kernel/linux/linux-yocto.inc

S = "${WORKDIR}/git"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"
