KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "u-boot-karo"
PREFERRED_PROVIDER_u-boot ??= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-karo"

BUILD_REPRODUCIBLE_BINARIES ?= "0"
INHERIT_remove = "${@'reproducible_build' if d.getVar('BUILD_REPRODUCIBLE_BINARIES') == '0' else ''}"

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
PREFERRED_VERSION_linux-karo = "5.10"
KERNEL_IMAGETYPE ??= "uImage"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = " \
                          kernel-modules \
                          kernel-devicetree \
                          u-boot-karo \
"

MACHINE_FEATURES = " \
                 alsa \
                 rtc \
                 serial \
                 usbgadget \
                 usbhost \
                 vfat \
"

# Blacklist evbug kernel module
KERNEL_MODULE_PROBECONF += "evbug"
module_conf_evbug = "blacklist evbug"
