FILESEXTRAPATHS:prepend:stm32mpcommon := "${THISDIR}/tf-a-stm32mp:"

SECTION = "bootloaders"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

SRC_URI = "git://github.com/karo-electronics/imx-atf;protocol=https;branch=${SRCBRANCH}"
SRCREV = "239fa86224e7bb92cff76680ba559576810e3677"
SRCBRANCH = "karo-txmp"

TF_VERSION = "2.2"
PV = "${TF_VERSION}.r2"

S = "${WORKDIR}/git"
