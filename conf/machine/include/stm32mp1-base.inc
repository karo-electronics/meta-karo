MACHINEOVERRIDES =. "stm32mpcommon:stm32mp1common:stm32mp1:"

DEFAULTTUNE ?= "cortexa7thf-neon-vfpv4"

require karo-base.inc
require conf/machine/include/soc-family.inc
require conf/machine/include/arm/armv7a/tune-cortexa7.inc

MACHINE_FEATURES:append = " emmc"
MACHINE_FEATURES:append = " optee"

UBOOT_CONFIG ??= "${@ "optee" if 'optee' in d.getVar('MACHINE_FEATURES').split() else "trusted"} mfg noenv"

UBOOT_ENTRYPOINT = "0xc0008000"

DTB_OVERLAY_EXT = "dtb"
DTB_VENDOR_DIR = "st"

KERNEL_IMAGETYPE ?= "uImage"

PREFERRED_VERSION_u-boot-karo = "2022.10"
PREFERRED_VERSION_linux-karo = "6.6"

# Kernel
KERNEL_DEVICETREE ?= "st/${DTB_BASENAME}.dtb"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySTM0 115200;ttySTM1 115200;ttySTM2"

# =========================================================================
# boot scheme
# =========================================================================
# List of supported boot schemes
BOOTSCHEME_LABELS ??= "optee"
BOOTSCHEME_DEFAULT ??= "optee"

INITIAL_BOOTSCHEME[tf-a] = "usb"
INITIAL_BOOTSCHEME[fip] = "mfg"

BOOTLOADER_BINARIES = "tf-a.stm32 fip.bin"

# =========================================================================
# tf-a
# =========================================================================
# Finally we must compile tf-a in all cases as we need trusted binary to boot
EXTRA_IMAGEDEPENDS += "tf-a-karo"
EXTRA_IMAGEDEPENDS += "optee-os"

# Set default TF-A config
TF_A_CONFIG = "usb"
TF_A_CONFIG:prepend = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'trusted', 'trusted ', '', d)}"
TF_A_CONFIG:prepend = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', 'optee ', '', d)}"

# link TF_A_CONFIG to UBOOT_CONFIG
TF_A_CONFIG[mfg] = "usb"
TF_A_CONFIG[noenv] = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', "optee", "trusted", d)}"

TF_A_TOOLS_PLATFORM ?= "stm32mp1"
TF_A_DEVICETREE ?= "${SOC_FAMILY}-${MACHINE}"

# coprocessor stuff
M4_BOARDS ?= "STM32MP157C-DK2"

# op-tee
OPTEE_CONF ?= "${DTB_BASENAME}"
STM32MP_SOC_NAME ?= "${SOC_PREFIX}"

BOARD_DRAM_SIZE ??= "0x20000000"
