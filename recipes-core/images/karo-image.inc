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
