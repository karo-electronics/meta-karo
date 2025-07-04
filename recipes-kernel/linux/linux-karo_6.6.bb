SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/patches:"

require recipes-kernel/linux/linux-karo.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KERNEL_SRC_DEFAULT = "git://github.com/karo-electronics/karo-tx-linux.git;protocol=https"
KERNEL_BRANCH_DEFAULT = "linux-stm32mp-v6.6-karo"
KERNEL_REV_DEFAULT = "ac39a3dddcd1da881e485ef3004a96d52fc88349"

SRC_URI:append = " \
        file://${KBUILD_DEFCONFIG} \
        ${@ "".join(map(lambda f: " file://cfg/" + f, "${KERNEL_FEATURES}".split()))} \
"

SRC_URI:append:stm32mp1 = " \
        file://dts/st/stm32mp15-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp15-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp15-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp15-karo-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp15-qsmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp153-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp153-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp153-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp153a-karo-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp153a-qsmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp157-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp157-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp157-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp157c-karo-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/st/stm32mp157c-qsmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

SRC_URI:append:stm32mp23 = " \
        file://dts/st/stm32mp23-karo-resmem.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

SRC_URI:append:stm32mp25 = " \
        file://dts/st/stm32mp25-karo-resmem.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

KERNEL_LOCALVERSION = "${LINUX_VERSION_EXTENSION}"

KBUILD_DEFCONFIG = "defconfig"
KBUILD_DEFCONFIG:qsmp-1510 = "qsmp-1510_defconfig"

KERNEL_FEATURES:append:stm32mp13 = " stm32mp13.cfg"
KERNEL_FEATURES:append:stm32mp15 = " stm32mp15.cfg"
KERNEL_FEATURES:append:stm32mp23 = " stm32mp23.cfg"
KERNEL_FEATURES:append:stm32mp25 = " stm32mp25.cfg"

KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"bluetooth"," bluetooth.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"bcm4373"," bcm4373.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"laird-wifi"," laird-wifi.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"ipv6"," ipv6.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"pcie"," pci.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"rauc"," rauc.cfg","",d)}"

KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"extmod"," extmod.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"nxp-wifi"," extmod.cfg pci.cfg ipv6.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"lvds"," lvds.cfg","",d)}"

KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"nvme"," nvme.cfg","",d)}"
KERNEL_FEATURES:append:stm32mp2:qsmp = "${@bb.utils.contains('DISTRO_FEATURES',"csi-camera"," imx219.cfg csi.cfg","",d)}"

KERNEL_FEATURES:append:stm32mp2 = "${@bb.utils.contains('DISTRO_FEATURES',"flexcan"," fdcan.cfg","",d)}"

KERNEL_FEATURES:remove = "${@bb.utils.contains('DISTRO_FEATURES','bcm4373','wifi.cfg','',d)}"


COMPATIBLE_MACHINE:stm32mp1 = "(txmp-.*|qsmp-.*)"
