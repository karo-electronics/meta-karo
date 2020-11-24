SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

DEPENDS += "lzop-native bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "linux-5.7.y"
SRCREV = "v5.7.1"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

PROVIDES += "linux"

SRC_URI = "${KERNEL_SRC};protocol=git;branch=${SRCBRANCH}"

SRC_URI_append = " \
	file://${KBUILD_DEFCONFIG};subdir=git/arch/arm/configs \
	${@bb.utils.contains('DISTRO_FEATURES',"systemd","file://cfg/systemd.cfg","",d)} \
	${@bb.utils.contains('DISTRO_FEATURES',"wifi","file://cfg/wifi.cfg","",d)} \
	file://0001-display-support.patch \
	file://0002-panel-dpi-bus-format.patch \
	file://0003-ltdc-missing-bus-flags.patch \
	file://0004-usbotg_id-bugfix.patch \
	file://0005-usb-phy-bugfix.patch \
	file://0006-qspi-bus-bugfix.patch \
	file://0007-stmmac-axi-bugfix.patch \
	file://0008-stm32cryp-dependencies.patch \
	file://0009-remove-pinctrl-z.patch \
	file://0010-dwc2-usbotg-bugfix.patch \
	file://0011-ehci-wakeirq.patch \
	file://0012-optional-irqs.patch \
	file://0013-interrupts-extended-bugfix.patch \
	file://0014-rtc-bugfix.patch \
	file://0015-txmp-support.patch \
	file://0016-dsi83-with-tm101-panel-on-qsbase2.patch \
	file://0017-raspberrypi-7inch-touchscreen-support.patch \
	file://0018-can-m_can-make-m_can-driver-work-again-with-sleep-st.patch \
	file://0019-parrallel-display-bus-flags-from-display-info.patch \
	file://0020-spi-nand-dma-map-bugfix.patch \
"

SRC_URI_append_mx6 = " \
	${@bb.utils.contains('MACHINE_FEATURES',"nand","file://cfg/nand.cfg","",d)} \
"

SRC_URI_append_tx6 = " \
	file://dts/imx6dl-tx6u-8033.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8133.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8134.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8135.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8034-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8035-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8134-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6s-8135-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8133-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-80xx-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6dl-tx6u-8033-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-10x0-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-1020-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6q-tx6q-1036-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-lcd.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-lvds.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qdl-tx6-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8037-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8037.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8137-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6qp-tx6q-8137.dts;subdir=git/arch/arm/boot \
	${@bb.utils.contains('MACHINE_FEATURES',"lvds","file://cfg/lvds.cfg","file://cfg/lcd.cfg",d)} \
	${@bb.utils.contains('MACHINE_FEATURES',"sata","file://cfg/sata.cfg","",d)} \
"

SRC_URI_append_txul = " \
	file://dts/imx6ul-tx6ul-0010.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-tx6ul-0011.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-tx6ul.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5010-mainboard.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5010-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5011-mainboard.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-5011-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-mainboard.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6ul-txul-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/imx6ull-txul-8013-mainboard.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ull-txul-8013-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/imx6ull-txul-8013.dts;subdir=git/arch/arm/boot \
"

SRC_URI_append_stm32mp1 = " \
	file://dts/stm32mp15-karo-mb7.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo-mipi-mb.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo-qsbase1.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo-qsbase2.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-karo.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp153-karo.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp157-karo.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-qsmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp15-txmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp153-qsmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp153-txmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp157-qsmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp157-txmp.dtsi;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-qsmp-1530-qsbase1.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-qsmp-1530.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp153a-txmp-1530.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase1.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase2.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase2-dsi83.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570-qsbase2-raspi.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-qsmp-1570.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570-mb7.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570-mipi-mb.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp157c-txmp-1570.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp151a-qsmp-1510-qsbase1.dts;subdir=git/arch/arm/boot \
	file://dts/stm32mp151a-qsmp-1510.dts;subdir=git/arch/arm/boot \
"

LOCALVERSION = "-stable"
KERNEL_IMAGETYPE_mx6 = "uImage"
KERNEL_IMAGETYPE_stm32mp1 = "uImage"

KBUILD_DEFCONFIG = "defconfig"
KBUILD_DEFCONFIG_qsmp-1510 = "qsmp-1510_defconfig"

KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," cfg/wifi.cfg","",d)}"
KERNEL_FEATURES_append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," cfg/systemd.cfg","",d)}"

COMPATIBLE_MACHINE_tx6 = "(tx6[qsu]-.*)"
COMPATIBLE_MACHINE_txul = "(txul-.*)"
COMPATIBLE_MACHINE_stm32mp1 = "(txmp-.*|qsmp-.*)"

do_configure_prepend() {
    install -v "${WORKDIR}/defconfig" "${B}/.config"
}
