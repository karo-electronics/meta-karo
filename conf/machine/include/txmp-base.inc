MACHINEOVERRIDES =. "stm32mp1:"

include conf/machine/include/st-machine-common-stm32mp.inc
include conf/machine/include/tune-cortexa7.inc

KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

UBOOT_CONFIG ??= "mfg generic"

EXTRA_IMAGEDEPENDS += "u-boot-karo"
PREFERRED_PROVIDER_u-boot ??= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-karo"

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
PREFERRED_VERSION_linux-karo = "5.10"
KERNEL_IMAGETYPE = "uImage"

UBOOT_ENTRYPOINT = "0xc0008000"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySTM0 115200;ttySTM1 115200;ttySTM2"

MACHINE_FEATURES = " \
                 alsa \
                 rtc \
                 serial \
                 usbgadget \
                 usbhost \
                 vfat \
"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = " \
                          kernel-modules \
                          kernel-devicetree \
                          u-boot-karo \
"

# Blacklist evbug kernel module
KERNEL_MODULE_PROBECONF += "evbug"
module_conf_evbug = "blacklist evbug"

# =========================================================================
# flashlayout
# =========================================================================
# Define the config labels to use to generate flashlayout file
FLASHLAYOUT_CONFIG_LABELS = "emmc"

# =========================================================================
# Trusted firmware debug
# =========================================================================
TF_DEBUG ?= "1"

# =========================================================================
# Flashlayouts
# =========================================================================
ENABLE_FLASHLAYOUT_CONFIG = "1"
FLASHLAYOUT_BOOTSCHEME_LABELS = "basic trusted"
BIN2BOOT_REPLACE_PATTERNS_fsbl1-boot = "basic;trusted"
BIN2BOOT_REPLACE_PATTERNS_ssbl-boot = "basic;mfg trusted;mfg"
FLASHLAYOUT_TYPE_LABELS_emmc = "${TF_A_DEVICETREE}"
FLASHLAYOUT_TYPE_LABELS_spinand = "${TF_A_DEVICETREE}"
