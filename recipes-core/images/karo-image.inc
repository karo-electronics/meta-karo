LICENSE = "MIT"

inherit core-image

#DISTROOVERRIDES =. "karo-minimal karo-base karo-x11"

IMAGE_FEATURES += "tools-debug"
IMAGE_FEATURES_append_karo-base = " splash ssh-server-openssh"
IMAGE_FEATURES_append_karo-x11 = " splash ssh-server-openssh x11-base"

IMAGE_INSTALL = "\
              packagegroup-core-boot \
              ${CORE_IMAGE_EXTRA_INSTALL} \
              alsa-utils-aplay \
              alsa-utils-amixer \
              alsa-utils-alsamixer \
              bash \
              canutils \
              coreutils \
              dhcpcd \
              dosfstools \
              e2fsprogs \
              file \
              gdbserver \
              kmod \
              i2c-tools \
              ldd \
              libstdc++ \
              lsof \
              mmc-utils \
              ntp \
              ntpdate \
              parted \
              rsync \
              strace \
              tzdata \
              util-linux-fsck \
              util-linux-mkfs \
              util-linux-setterm \
"
IMAGE_INSTALL_append_karo-base = " udev"
IMAGE_INSTALL_append_karo-x11 = " twm udev xterm"

RDEPENDS_append_karo-x11 = " libdrm-etnaviv"

PREFERRED_PROVIDER_fdisk = "util-linux"

IMAGE_ROOTFS_SIZE ?= "32768"

#python extend_recipe_sysroot_append() {
#    if d.getVar('UBOOT_MACHINE') != None:
#        raise_sanity_error("build directory has been configured for u-boot build", d)
#}

python karo_check_distro () {
    if d.getVar('UBOOT_MACHINE') != None and d.getVar('IMAGE_BASENAME') != 'u-boot-karo':
        raise_sanity_error("cannot build %s in build dir that has been configured for 'u-boot' build only" % d.getVar('IMAGE_BASENAME'), d)

    elif d.getVar('IMAGE_BASENAME') == 'karo-image-x11' and d.getVar('DISTRO') != 'karo-x11':
        raise_sanity_error("cannot build '%s' with DISTRO '%s'" % \
           (d.getVar('IMAGE_BASENAME'), d.getVar('DISTRO')), d)
}
