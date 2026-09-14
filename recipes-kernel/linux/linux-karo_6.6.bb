SUMMARY = "Linux Kernel for Ka-Ro electronics Computer-On-Modules"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/cfg:${THISDIR}/${BP}/defconfigs:"

require recipes-kernel/linux/linux-karo.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KERNEL_SRC_DEFAULT = "git://github.com/karo-electronics/karo-tx-linux.git;protocol=https"
KERNEL_BRANCH_DEFAULT = "linux-stm32mp-v6.6-karo"
KERNEL_REV_DEFAULT = "5d05fd35e52b4007bc904b8039a5900dce9ca098"

CVE_PRODUCT = "linux:linux_kernel"
CVE_VERSION = "6.6.116"

SPDX_INCLUDE_KERNEL_CONFIG = "1"

SRC_URI:append = " file://${KBUILD_DEFCONFIG}"

SRC_URI:append = " \
    ${@ "".join(map(lambda f: " file://" + f, "${KERNEL_FEATURES}".split()))} \
"

KBUILD_DEFCONFIG ?= "defconfig"

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
KERNEL_FEATURES:append:stm32mp2 = "${@bb.utils.contains('MACHINE_FEATURES',"stm-tsn-swch"," stm-tsn-swch.cfg","",d)}"

KERNEL_FEATURES:remove = "${@bb.utils.contains('DISTRO_FEATURES','bcm4373','wifi.cfg','',d)}"

COMPATIBLE_MACHINE:stm32mp1 = "(txmp-.*|qsmp-.*)"
