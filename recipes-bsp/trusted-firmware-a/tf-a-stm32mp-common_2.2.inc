FILESEXTRAPATHS_prepend_stm32mpcommon := "${THISDIR}/tf-a-stm32mp:"

SECTION = "bootloaders"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

SRC_URI = "git://github.com/karo-electronics/imx-atf;protocol=https;branch=${SRCBRANCH}"
SRCREV = "22b5069b5d5019da27e873aa90ceca2589401bee"
SRCBRANCH = "karo-txmp"

TF_VERSION = "2.2"
PV = "${TF_VERSION}.r2"

S = "${WORKDIR}/git"
