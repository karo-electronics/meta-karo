KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "u-boot-karo"
PREFERRED_PROVIDER_u-boot ??= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-karo"

UBOOT_MAKE_TARGET ??= "u-boot.bin"
UBOOT_SUFFIX ??= "bin"

UBOOT_ENTRYPOINT_tx6  ?= "0x10008000"
UBOOT_ENTRYPOINT_txul ?= "0x80008000"
UBOOT_CONFIG ??= "mfg generic"

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
PREFERRED_VERSION_linux-karo = "5.10"
KERNEL_IMAGETYPE ??= "uImage"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = " \
                          kernel-modules \
                          u-boot-karo \
"

# Ship imx-sdma & vpu firmware
MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
        linux-firmware-imx-sdma-imx6q \
        ${@bb.utils.contains('DISTRO_FEATURES',"wayland","linux-firmware-imx-vpu","",d)} \
"

# Blacklist evbug kernel module
KERNEL_MODULE_PROBECONF += "evbug"
module_conf_evbug = "blacklist evbug"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttymxc0 115200;ttymxc1 115200;ttymxc2"

MACHINE_FEATURES = " \
                 alsa \
                 rtc \
                 serial \
                 usbgadget \
                 usbhost \
                 vfat \
"

# Default baseboard name
DEFAULT_BASEBOARD_tx6 ?= "mb7"
DEFAULT_BASEBOARD_txul ?= "evalkit"
