SUMMARY = "6.1 Linux Kernel for Ka-Ro electronics Computer-On-Modules"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/cfg:${THISDIR}/${BP}/defconfigs:"

require recipes-kernel/linux/linux-karo.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KERNEL_BRANCH_DEFAULT = "rz-6.1-cip43"
KERNEL_REV_DEFAULT = "6717c06c72df7430323d0d48258ae4090f2d76aa"
KERNEL_SRC_DEFAULT = "git://github.com/renesas-rz/rz_linux-cip.git;protocol=https"

CVE_PRODUCT = "linux:linux_kernel"
CVE_VERSION = "6.1.141"

SPDX_INCLUDE_KERNEL_CONFIG = "1"

SRC_URI:append = " file://${KBUILD_DEFCONFIG}"

SRC_URI:append = " \
    ${@ "".join(map(lambda f: " file://" + f, "${KERNEL_FEATURES}".split()))} \
"

KBUILD_DEFCONFIG ?= "defconfig"

KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"extmod"," extmod.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"dsi83"," dsi83.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"lvds"," lvds.cfg","",d)}"

KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"bluetooth"," bluetooth.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"ipv6"," ipv6.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"copro"," copro.cfg","",d)}"

COMPATIBLE_MACHINE:rzg2 = "(txrz-.*|qsrz-.*)"
