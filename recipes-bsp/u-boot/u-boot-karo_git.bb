require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "Das U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"

PROVIDES = "u-boot"
DEPENDS += "bc-native"

PV = "v2015.10-rc2+git${SRCPV}"

SRCREV = "KARO-TX6-2018-01-08"
SRCBRANCH = "master"
SRC_URI = "git://github.com/karo-electronics/karo-tx-uboot.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*|imx6.*-tx.*)"

python check_sanity_everybuild_append () {
    if d.getVar('UBOOT_MACHINE') != None and d.getVar('IMAGE_BASENAME') != 'u-boot-karo':
        status.addresult("Error: cannot build %s in build dir that has been configured for 'u-boot' build only" % d.getVar('IMAGE_BASENAME'), d)

    elif d.getVar('IMAGE_BASENAME') == 'karo-image-x11' and d.getVar('DISTRO') != 'karo-x11':
        status.addresult("Error: cannot build '%s' with DISTRO '%s'" % \
           (d.getVar('IMAGE_BASENAME'), d.getVar('DISTRO')))
    else
        bb.error("Ka-Ro sanity check passed")
}
