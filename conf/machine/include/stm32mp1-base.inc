MACHINEOVERRIDES =. "stm32mp1:"

DEFAULTTUNE ?= "cortexa7thf-neon-vfpv4"

include karo.inc
include conf/machine/include/soc-family.inc
include conf/machine/include/arm/armv7a/tune-cortexa7.inc

MACHINE_FEATURES:append = " emmc"
MACHINE_FEATURES:append = " optee"

UBOOT_CONFIG ??= "${@ "optee" if 'optee' in d.getVar('MACHINE_FEATURES').split() else "trusted"} mfg noenv"
UBOOT_FEATURES ?= "${@ "emmc fastboot" if 'emmc' in d.getVar('MACHINE_FEATURES').split() else ""}"

UBOOT_ENTRYPOINT = "0xc0008000"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySTM0 115200;ttySTM1 115200;ttySTM2"

# =========================================================================
# boot scheme
# =========================================================================
# List of supported boot schemes
BOOTSCHEME_LABELS ??= "optee"

# =========================================================================
# tf-a
# =========================================================================
# Finally we must compile tf-a in all cases as we need trusted binary to boot
EXTRA_IMAGEDEPENDS += "tf-a-karo"
EXTRA_IMAGEDEPENDS += "optee-os"

# Define default TF-A config
# Set default TF-A config
TF_A_CONFIG = "usb"
TF_A_CONFIG:prepend = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'trusted', 'trusted ', '', d)}"
TF_A_CONFIG:prepend = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', 'optee ', '', d)}"

# link TF_A_CONFIG to UBOOT_CONFIG
TF_A_CONFIG[mfg] = "usb"
TF_A_CONFIG[noenv] = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', "optee", "trusted", d)}"

# Manage proper update for TF_A_CONFIG_* var
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'usb', 'TF_A_CONFIG_usb', '', d)}"
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'trusted', 'TF_A_CONFIG_trusted', '', d)}"
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'optee', 'TF_A_CONFIG_optee', '', d)}"

DTB_BASENAME ?= "${TF_A_DEVICETREE}"

# coprocessor stuff
M4_BOARDS ?= "STM32MP157C-DK2"

# op-tee
BOARD_DRAM_SIZE ??= "0x20000000"
