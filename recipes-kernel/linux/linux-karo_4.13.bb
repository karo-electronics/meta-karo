SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "karo-tx-linux"
SRCREV = "f7a9069cf51de58dffe84db692cc9d6680173cf0"
KERNEL_SRC = "git://git@github.com/karo-electronics/karo-linux-devel.git;protocol=ssh"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
           file://defconfig \
"

KERNEL_IMAGETYPE="uImage"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*)"