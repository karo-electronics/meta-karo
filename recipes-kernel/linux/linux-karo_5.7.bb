SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

DEPENDS += "lzop-native bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "master"
SRCREV = "v5.7-rc7"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

PROVIDES += "linux"

SRC_URI = "${KERNEL_SRC};protocol=git;branch=${SRCBRANCH}"

SRC_URI_append = " \
	file://0002-display-support.patch \
	file://0003-panel-dpi-bus-format.patch \
	file://0004-drm-mode-video-mode-bus-flags.patch \
	file://0001-usbotg_id-bugfix.patch \
	file://0002-usb-phy-bugfix.patch \
	file://0003-qspi-bus-bugfix.patch \
	file://0004-stmmac-axi-bugfix.patch \
	file://0005-stm32cryp-dependencies.patch \
	file://0006-remove-pinctrl-z.patch \
	file://0007-dwc2-usbotg-bugfix.patch \
	file://0008-ehci-wakeirq.patch \
	file://0009-optional-irqs.patch \
	file://0010-interrupts-extended-bugfix.patch \
	file://0011-rtc-bugfix.patch \
	file://0012-txmp-support.patch \
	file://0013-dsi83-with-tm101-panel-on-qsbase2.patch \
"

SRC_URI_append_stm32mp1 = " \
	file://defconfig \
	file://dts/stm32mp15-karo-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo-mipi-mb.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo-qsbase1.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo-qsbase2.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-qsmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-txmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-qsmp-1530-qsbase1.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-qsmp-1530.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase1.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase2.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570-mipi-mb.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570.dts;subdir=git/arch/arm/boot \
"

COMPATIBLE_MACHINE_stm32mp1 = "(txmp-.*|qsmp-.*)"

# exclude kernel in rootfs
#RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""
