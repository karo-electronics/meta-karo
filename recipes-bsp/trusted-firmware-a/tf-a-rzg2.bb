LIC_FILES_CHKSUM = "file://docs/license.rst;md5=b2c740efedc159745b9b31f88ff03dde"

require tf-a-rzg2.inc

SRC_URI = "git://github.com/karo-electronics/imx-atf.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "karo-txrz"
SRCREV = "ef156f33ce580564ff1e863ee12dfe834f097d30"

PROVIDES += "virtual/trusted-firmware-a"
PROVIDES += "tf-a-karo"

DEPENDS:append = " bootparameter-native"

PV = "2.7-rz"
PR = "r1"
