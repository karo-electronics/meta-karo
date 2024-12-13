MACHINEOVERRIDES =. "stcommon:stm32mpcommon:stm32mp2common:stm32mp25common:stm32mp2:stm32mp25:"

DEFAULTTUNE ?= "cortexa35"

require karo.inc
require conf/machine/include/soc-family.inc
require conf/machine/include/arm/armv8a/tune-cortexa35.inc

MACHINE_FEATURES:append = " emmc"
MACHINE_FEATURES:append = " optee"
MACHINE_FEATURES:append = " gpu"

UBOOT_CONFIG ??= "${@ "optee" if 'optee' in d.getVar('MACHINE_FEATURES').split() else "trusted"} mfg noenv"
UBOOT_FEATURES ?= "${@ "emmc fastboot" if 'emmc' in d.getVar('MACHINE_FEATURES').split() else ""}"

UBOOT_ENTRYPOINT = "0xc4000000"

KERNEL_IMAGETYPE ?= "Image"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySTM0 ${@ " 115200;ttySTM1" if d.getVar('KARO_BASEBOARD') != "qsbase5" else ""} 115200;ttySTM2"

# =========================================================================
# boot scheme
# =========================================================================
# List of supported boot schemes
BOOTSCHEME_LABELS ??= "optee"
BOOTDEVICE_LABELS ?= "emmc sdcard"

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

TF_A_TOOLS_PLATFORM ?= "stm32mp2"

# link TF_A_CONFIG to UBOOT_CONFIG
TF_A_CONFIG[mfg] = "usb"
TF_A_CONFIG[noenv] = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', "optee", "trusted", d)}"

# Manage proper update for TF_A_CONFIG_* var
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'usb', 'TF_A_CONFIG_usb', '', d)}"
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'trusted', 'TF_A_CONFIG_trusted', '', d)}"
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'optee', 'TF_A_CONFIG_optee', '', d)}"

DTB_BASENAME ?= "${SOC_FAMILY}-${MACHINE}"
KARO_BOARD_PMIC ??= "stpmic2"

# op-tee
OPTEE_CONF ?= "${DTB_BASENAME}"
STM32MP_SOC_NAME ?= "${SOC_PREFIX}"

BOARD_DRAM_SIZE ??= "0x20000000"

FIP_CONFIG += "${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', 'optee', '', d)}"

# Define config for each FIP_CONFIG
FIP_CONFIG[optee]   ?= "optee,${FIP_DEVICETREE_OPTEE}"

# List of device tree to use for fip binary creation
FIP_DEVICETREE_OPTEE   ?= "${STM32MP_DEVICETREE}"

# Define fiptool wrapper name
FIPTOOL_WRAPPER ?= "fiptool-stm32mp"

# Manage specific config to sign FIP
FIP_SIGN_SUFFIX ?= "${@bb.utils.contains('SIGN_ENABLE', '1', '${SIGN_SUFFIX}', '', d)}"
FIP_ENCRYPT_SUFFIX ?= "${@bb.utils.contains('ENCRYPT_ENABLE', '1', '${ENCRYPT_SUFFIX}', '', d)}"

# Configure use of BL31
FIP_BL31_ENABLE = "0"
FIP_BL31_ENABLE:stm32mp25common = "1"

REGULATORS_MICROVOLTS = "1"
