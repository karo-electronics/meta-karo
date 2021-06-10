MACHINEOVERRIDES =. "stm32mp1:"

DEFAULTTUNE ?= "cortexa7thf-neon-vfpv4"

include conf/machine/include/karo.inc
include conf/machine/include/st-machine-common-stm32mp.inc
include conf/machine/include/tune-cortexa7.inc

UBOOT_CONFIG ??= "mfg trusted generic"

UBOOT_ENTRYPOINT = "0xc0008000"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySTM0 115200;ttySTM1 115200;ttySTM2"

# =========================================================================
# flashlayout
# =========================================================================
# Define the config labels to use to generate flashlayout file
FLASHLAYOUT_CONFIG_LABELS = "emmc"

# =========================================================================
# Flashlayouts
# =========================================================================
ENABLE_FLASHLAYOUT_CONFIG = "1"
FLASHLAYOUT_BOOTSCHEME_LABELS = "trusted"
FLASHLAYOUT_BOOTSCHEME_LABELS += "${@bb.utils.contains('UBOOT_CONFIG','generic','basic','',d)}"

BIN2BOOT_REPLACE_PATTERNS_fsbl1-boot = "basic;serialboot trusted;serialboot"
BIN2BOOT_REPLACE_PATTERNS_ssbl-boot = "basic;mfg trusted;mfg"
FLASHLAYOUT_TYPE_LABELS_emmc = "${TF_A_DEVICETREE}"
FLASHLAYOUT_TYPE_LABELS_spinand = "${TF_A_DEVICETREE}"
