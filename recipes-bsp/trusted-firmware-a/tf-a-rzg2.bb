LIC_FILES_CHKSUM = "file://docs/license.rst;md5=b2c740efedc159745b9b31f88ff03dde"

require tf-a-rzg2.inc

TF_A_SRC ?= "git://github.com/karo-electronics/imx-atf.git;protocol=https"
TF_A_BRANCH ?= "karo-txrz"
TF_A_REV ?= "ef156f33ce580564ff1e863ee12dfe834f097d30"

SRC_URI = "${TF_A_SRC};branch=${TF_A_BRANCH}"
SRCREV = "${TF_A_REV}"

PROVIDES += "virtual/trusted-firmware-a"
PROVIDES += "tf-a-karo"

DEPENDS:append = " bootparameter-native"

PV = "2.7-rz"
PR = "r1"
