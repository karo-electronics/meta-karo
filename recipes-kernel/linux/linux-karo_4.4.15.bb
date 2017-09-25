SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "karo-tx6-mainline"
LOCALVERSION = "-v4.4.15"
SRCREV = "017b90c50098a261a5145ccc2a9ef868e3a82f30"
KERNEL_SRC = "git://github.com/karo-electronics/karo-tx-linux;protocol=git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
           file://defconfig \
"

# Add missing dts files
SRC_URI += "file://imx6dl-tx6s-8134.dts;subdir=git/arch/arm/boot/dts \
	    file://imx6dl-tx6s-8135.dts;subdir=git/arch/arm/boot/dts \
	    file://imx6dl-tx6u-8133.dts;subdir=git/arch/arm/boot/dts \
	   "

KERNEL_IMAGETYPE="uImage"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*)"