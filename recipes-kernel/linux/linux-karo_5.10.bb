SUMMARY = "5.10 Linux Kernel for Ka-Ro electronics Computer-On-Modules"

require recipes-kernel/linux/linux-karo.inc

DEPENDS += "lzop-native bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "linux-5.10.y"
SRCREV = "452ea6a15ed2ac74789b7b3513777cc94ea3b751"
KERNEL_SRC = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git"
KERNEL_SRC:rzg2 = "git://github.com/renesas-rz/rz_linux-cip.git"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/patches:${THISDIR}/${BP}:"

PROVIDES += "linux"

SRC_URI = "${KERNEL_SRC};protocol=https;branch=${SRCBRANCH}"

SRCBRANCH:rzg2 = "rz-5.10-cip3"
SRCREV:rzg2 = "82f95c617eff879a0296e4b8d0160f38785b87ca"

# automatically add all .dts files referenced by ${KERNEL_DEVICETREE} to SRC_URI
SRC_URI:append = "${@"".join(map(lambda f: " file://dts/%s;subdir=git/${KERNEL_OUTPUT_DIR}" % f.replace(".dtb", ".dts"), "${KERNEL_DEVICETREE}".split()))}"

SRC_URI:append = " \
        file://${KBUILD_DEFCONFIG} \
        ${@ "".join(map(lambda f: " file://cfg/" + f, "${KERNEL_FEATURES}".split()))} \
        file://0001-display-support.patch \
        file://0002-panel-dpi-bus-format.patch \
        file://0003-smsc-lan8741-support.patch \
        file://0007-stm32cryp-dependencies.patch \
        file://0010-attiny-regulator-i2c-retries.patch \
        file://0011-stm-drv-preferred-depth.patch \
        file://0015-raspberrypi-7inch-touchscreen-support.patch \
        file://0016-parallel-display-bus-flags-from-display-info.patch \
        file://0017-spi-nand-dma-map-bugfix.patch \
        file://0019-fdt5x06-dma-bugfix.patch \
        file://0020-ilitek-ts-i2c-driver.patch \
        file://0001-lib-iov_iter-initialize-flags-in-new-pipe_buffer.patch \
"

# dirty-pipe vulnerability already applied in renesas linux
SRC_URI:remove:rzg2 = " \
        file://0001-lib-iov_iter-initialize-flags-in-new-pipe_buffer.patch \
"

SRC_URI:append:rzg2 = " \
        file://dts/renesas/r9a07g044l2-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-qsrz-qsbase4.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-qsrz.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-txrz-mb7.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
        file://dts/renesas/r9a07g044l2-txrz.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

KERNEL_LOCALVERSION = "${LINUX_VERSION_EXTENSION}"

KBUILD_DEFCONFIG = "defconfig"

KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"bluetooth"," bluetooth.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"systemd"," systemd.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('DISTRO_FEATURES',"ipv6"," ipv6.cfg","",d)}"
KERNEL_FEATURES:append = "${@bb.utils.contains('MACHINE_FEATURES',"extmod"," extmod.cfg","",d)}"

KERNEL_FEATURES:append:qsrz = "${@bb.utils.contains('MACHINE_FEATURES',"dsi83"," dsi83.cfg"," dsi83.cfg",d)}"

# returns all the elements from the src uri that are .cfg files
def find_cfgs(d):
    sources=src_patches(d, True)
    sources_list=[]
    for s in sources:
        if s.endswith('.cfg'):
            sources_list.append(s)

    return sources_list

do_configure:prepend() {
    # Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    if ! [ -s "${S}/.scmversion" ] || ! grep -q "$head" ${S}/.scmversion;then
        echo "+g$head" > "${S}/.scmversion"
    fi
    install -v "${WORKDIR}/${KBUILD_DEFCONFIG}" "${B}/.config"
    sed -i '/CONFIG_LOCALVERSION/d' "${B}/.config"
    echo 'CONFIG_LOCALVERSION="${KERNEL_LOCALVERSION}"' >> "${B}/.config"

    for f in ${KERNEL_FEATURES};do
        cat ${WORKDIR}/cfg/$f >> ${B}/.config
    done
}
