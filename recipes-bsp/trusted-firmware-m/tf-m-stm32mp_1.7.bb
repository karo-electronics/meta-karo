SUMMARY = "Trusted Firmware for Cortex-M"
DESCRIPTION = "Trusted Firmware-M"
HOMEPAGE = "https://git.trustedfirmware.org/trusted-firmware-m.git"
PROVIDES = "virtual/trusted-firmware-m"

LICENSE = "BSD-3-Clause & Apache-2.0"

LIC_FILES_CHKSUM = "file://license.rst;md5=07f368487da347f3c7bd0fc3085f3afa"

SRC_URI = "git://git.trustedfirmware.org/TF-M/trusted-firmware-m.git;protocol=http;nobranch=1;name=tfm;destsuffix=git/tfm \
            git://git.trustedfirmware.org/TF-M/tf-m-tests.git;protocol=http;nobranch=1;name=tfm-tests;destsuffix=git/tf-m-tests \
            git://github.com/ARMmbed/mbedtls.git;protocol=https;branch=master;name=mbedtls;destsuffix=git/mbedtls \
            git://github.com/mcu-tools/mcuboot.git;protocol=https;branch=main;name=mcuboot;destsuffix=git/mcuboot \
            git://github.com/laurencelundblade/QCBOR.git;protocol=https;branch=master;name=qcbor;destsuffix=git/qcbor \
"

# The required dependencies are documented in tf-m/config/config_default.cmake
# TF-Mv1.7.0
SRCREV_tfm = "b725a1346cdb9ec75b1adcdc4c84705881e8fd4e"
# mbedtls-3.2.1
SRCREV_mbedtls = "869298bffeea13b205343361b7a7daf2b210e33d"
# TF-Mv1.7.0++
SRCREV_tfm-tests = "4c4b58041c6c01670266690538a780b4a23d08b8"
# v1.9.0
SRCREV_mcuboot = "c657cbea75f2bb1faf1fceacf972a0537a8d26dd"

# v1.1++
SRCREV_qcbor = "b0e7033268e88c9f27146fa9a1415ef4c19ebaff"

SRCREV_FORMAT = "tfm"

UPSTREAM_CHECK_GITTAGREGEX = "^TF-Mv(?P<pver>\d+(\.\d+)+)$"

# ST Patches
SRC_URI += " \
    file://0001-v1.7.0-stm32mp25-r6.patch \
    \
    file://0001-Build-Add-stub-functions-of-system-calls.patch;patchdir=../tf-m-tests \
"

TF_M_VERSION = "v1.7.0"
TF_M_SUBVERSION = "stm32mp25"
TF_M_RELEASE = "r6"
PV = "${TF_M_VERSION}-${TF_M_SUBVERSION}-${TF_M_RELEASE}"

include tf-m-stm32mp-common.inc

