KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "u-boot-karo"
PREFERRED_PROVIDER_u-boot ??= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-karo"

BUILD_REPRODUCIBLE_BINARIES ?= "0"
INHERIT:remove = "${@'reproducible_build' if d.getVar('BUILD_REPRODUCIBLE_BINARIES') == '0' else ''}"

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
PREFERRED_VERSION_linux-karo = "6.1"

MACHINEOVERRIDES =. "use-mainline-bsp:"

IMAGE_CLASSES += "st-partitions-image"

FIPTOOL_DIR = "fiptool"

GPU_IMAGE_INSTALL ??= ""
GPU_LIST ??= ""

GPU_USERLAND_LIBRARIES_INSTALL ??= "libdrm-etnaviv"

GPU_LIST:stm32mp2 = "\
    gcnano-driver-stm32mp \
    ${GPU_USERLAND_LIBRARIES_INSTALL} \
"

GPU_IMAGE_INSTALL:stm32mp2 = "${@bb.utils.contains('MACHINE_FEATURES', 'gpu', '${GPU_LIST}', '', d)} "

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = " \
    kernel-modules \
    kernel-devicetree \
    u-boot-karo \
    ${GPU_IMAGE_INSTALL} \
"

MACHINE_FEATURES = " \
                 alsa \
                 rtc \
                 serial \
                 usbgadget \
                 usbhost \
                 vfat \
"

UBOOT_FEATURES ??= ""
KARO_BASEBOARD ??= ""

# Blacklist evbug kernel module
KERNEL_MODULE_PROBECONF += "evbug"
module_conf_evbug = "blacklist evbug"
