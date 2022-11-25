require tf-a-stm32mp-common.inc

SUMMARY = "Trusted Firmware-A for STM32MP1"
SECTION = "bootloaders"
LICENSE = "BSD-3-Clause"

PROVIDES += "virtual/trusted-firmware-a"
PROVIDES += "tf-a-karo"

LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

SRC_URI = "git://github.com/ARM-software/arm-trusted-firmware.git;protocol=https;nobranch=1"
#SRCREV corresponds to v2.6
SRCREV = "a1f02f4f3daae7e21ee58b4c93ec3e46b8f28d15"

TF_VERSION = "2.6"
PV = "${TF_VERSION}.r1"

S = "${WORKDIR}/git"
