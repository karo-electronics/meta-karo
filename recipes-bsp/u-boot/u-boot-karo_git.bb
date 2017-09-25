require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "Das U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"

PROVIDES = "u-boot"

PV = "v2015.10-rc2+git${SRCPV}"

SRCREV = "04fae1750f5e236e8abb4470d5d8b5aa3262a99c"
SRCBRANCH = "master"
SRC_URI = "git://git@github.com/karo-electronics/karo-uboot-devel.git;branch=${SRCBRANCH};protocol=ssh \
	file://mbl_fix_compile_issue.patch \
"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*)"
