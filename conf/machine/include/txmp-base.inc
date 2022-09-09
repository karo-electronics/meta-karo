MACHINEOVERRIDES =. "stm32mp1:"

DEFAULTTUNE ?= "cortexa7thf-neon-vfpv4"

include karo.inc
include conf/machine/include/soc-family.inc
include conf/machine/include/arm/armv7a/tune-cortexa7.inc

SOC_PREFIX = "stm32mp15"

MACHINE_FEATURES:append = " emmc"

UBOOT_CONFIG ??= "mfg trusted"

UBOOT_ENTRYPOINT = "0xc0008000"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySTM0 115200;ttySTM1 115200;ttySTM2"

# =========================================================================
# boot scheme
# =========================================================================
# List of supported boot schemes
BOOTSCHEME_LABELS ??= "trusted"

# =========================================================================
# tf-a
# =========================================================================
# Finally we must compile tf-a in all cases as we need trusted binary to boot
EXTRA_IMAGEDEPENDS += "tf-a-stm32mp"

# Define default TF-A config
TF_A_CONFIG:append = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'trusted', ' trusted', '', d)}"

# Define SECURE_PAYLOAD config to set for each TF_A_CONFIG
TF_A_CONFIG_serialboot  = "AARCH32_SP=sp_min"
TF_A_CONFIG_trusted     = "AARCH32_SP=sp_min"

# Manage proper update for TF_A_CONFIG_* var
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'serialboot', 'TF_A_CONFIG_serialboot', '', d)}"
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'trusted', 'TF_A_CONFIG_trusted', '', d)}"

DTB_BASENAME ?= "${TF_A_DEVICETREE}"
KERNEL_DEVICETREE:append = "${@ " ${DTB_BASENAME}-${KARO_BASEBOARD}.dtb" if "${KARO_BASEBOARD}" != "" else ""}"
