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
IMAGE_INSTALL_append = "${@bb.utils.contains('DISTRO_FEATURES',"wifi"," wifi","",d)}"

RDEPENDS_append_karo-x11 = " libdrm-etnaviv"

PREFERRED_PROVIDER_fdisk = "util-linux"

IMAGE_ROOTFS_SIZE ?= "${@bb.utils.contains('TXNVM',"nand",32*1024,128*1024,d)}"
IMAGE_ROOTFS_ALIGNMENT ?= "${@bb.utils.contains('TXNVM',"nand",128*1024,1024,d)}"
IMAGE_ROOTFS_EXTRA_SPACE ?= "${@bb.utils.contains('TXNVM',"nand",2*128*1024,0,d)}"
IMAGE_FSTYPES += "${@bb.utils.contains('TXNVM',"nand","ubi","",d)}"

MKUBIFS_ARGS ?= "-F -x zlib -m 2048 -e 126976 -c 256"
UBINIZE_ARGS ?= "-m 2048 -s 2048 -p 128KiB"
UBI_VOLNAME_mx6 ?= "rootfs"
UBI_VOLNAME_mx6ul ?= "rootfs"
