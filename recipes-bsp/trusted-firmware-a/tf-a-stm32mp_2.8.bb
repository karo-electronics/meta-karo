require tf-a-stm32mp-common.inc

SUMMARY = "Trusted Firmware-A for STM32MP1"
SECTION = "bootloaders"
LICENSE = "BSD-3-Clause"

PROVIDES += "virtual/trusted-firmware-a"
PROVIDES += "tf-a-karo"

LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

SRC_URI = "git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;protocol=https;branch=lts-${TF_A_VERSION}"

SRCREV = "ff0bd5f9bb2ba2f31fb9cec96df917747af9e92d"

TF_A_VERSION = "v2.8"
PV = "${TF_A_VERSION}-lts"

S = "${WORKDIR}/git"
