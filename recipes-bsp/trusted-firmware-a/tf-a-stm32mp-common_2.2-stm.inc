FILESEXTRAPATHS_prepend_stm32mpcommon := "${THISDIR}/tf-a-stm32mp:"

SECTION = "bootloaders"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

SRC_URI = "git://github.com/STMicroelectronics/arm-trusted-firmware.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "26c42c65680820c0b28c175e75a9d4c6a635597f"

TF_VERSION = "2.2"
PV = "${TF_VERSION}.r2"

S = "${WORKDIR}/git"
