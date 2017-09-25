SUMMARY = "Linux Kernel for Ka-Ro electronics TX Computer-On-Modules"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.1.15_1.0.0_ga"
LOCALVERSION = "-tx6"
SRCREV = "77f61547834c4f127b44b13e43c59133a35880dc"
FILESEXTRAPATHS_prepend = "${THISDIR}/${BP}/txbase/${TXBASE}:"

# Add NXP binary blob driver for the Vivante GPU to the kernel image.
# Otherwise settings in the Kernel defconfig are actively delete or ignored and
# the required external LKM ([RFS]/lib/modules/<kernel-version>/extra) is not
# available at all ( exception being if specific images are being "bitbaked"
# like: fsl-image-multimedia-full )

# Add Vivante GPU driver support
# Handle Vivante kernel driver setting:
#   0 - machine does not have Vivante GPU driver support
#   1 - machine has Vivante GPU driver support
MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT ?= "1"

# Use Vivante kernel driver module:
#   0 - enable the builtin kernel driver module
#   1 - enable the external kernel module
MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE ?= "0"

# Add patches for gcc 6 compiler issue
SRC_URI += "file://gcc6_integrate_fix.patch \
	    file://bcmhd_gcc6_indent_warning_error_fix.patch \
	    file://gpu-viv_gcc6_indent_warning_error_fix.patch \
	   "

# Add Ethernet patches
SRC_URI += "file://reset_ethernet_phy_whenever_the_enet_out_clock_is_being_enabled.patch \
	    file://set-enet_ref_clk-to-50-mhz.patch \
	   "

# Add patch for EDT M12 touchscreen
SRC_URI += "file://add-support-for-edt-m12-touch.patch \
	   "

# Add patch faulty GPU clock defintion for imx6dl
SRC_URI += "file://linux-nxp-v4.1-tx6dl-dts-Faulty-GPU-clock-definition.patch \
	   "

# Add TX6 module kernel default config(s)
SRC_URI += "file://defconfig \
	   "

# Add TX6 module specific DT file(s)
SRC_URI += "file://imx6qdl-tx6.dtsi;subdir=git/arch/arm/boot/dts \
	    file://imx6qdl-tx6-gpio.h;subdir=git/include/dt-bindings/gpio \
	   "

# Add baseboard dtsi file(s)
SRC_URI += "file://txbase-${TXBASE}.dtsi;subdir=git/arch/arm/boot/dts"

# Add TX6 (machine) specific DTS file(s)
SRC_URI += "file://${TXTYPE}-${TXNVM}-${TXBASE}.dts;subdir=git/arch/arm/boot/dts"

KERNEL_DEVICETREE = "${TXTYPE}-${TXNVM}-${TXBASE}.dtb"

DEFAULT_PREFERENCE = "1"

KERNEL_IMAGETYPE = "uImage"

COMPATIBLE_MACHINE  = "(tx6[qus]-.*)"
