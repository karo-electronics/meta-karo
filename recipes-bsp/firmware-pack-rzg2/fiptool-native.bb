LIC_FILES_CHKSUM = "file://docs/license.rst;md5=713afe122abbe07f067f939ca3c480c5"

require fiptool-native.inc

SRC_URI = "git://github.com/karo-electronics/imx-atf.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "karo-txrz"
SRCREV = "1ac68a9f6e21142d9a633a778c8eab875deff853"

PV = "2.5-rzg"
PR = "r1"
