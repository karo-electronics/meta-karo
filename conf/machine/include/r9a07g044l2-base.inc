MACHINEOVERRIDES =. "r9a07g044l:rzg2:rzg2l:"

DEFAULTTUNE ?= "cortexa55"

require karo-base.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

SOC_PREFIX = "r9a07g044l2"
SOC_FAMILY ??= "${SOC_PREFIX}"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "flashwriter-rzg2"

KERNEL_IMAGETYPE ?= "Image"

MACHINE_FEATURES:append = " emmc"
MACHINE_FEATURES:append:qsrz = " dsi83"
MACHINE_FEATURES:append:qsrz = " lvds"

UBOOT_CONFIG ??= "generic noenv"

DTB_VENDOR_DIR = "renesas"

PREFERRED_VERSION_u-boot-karo = "2020.10"
PREFERRED_VERSION_linux-karo = "6.1"

# Kernel
KERNEL_DEVICETREE ?= "renesas/${DTB_BASENAME}.dtb"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySC0 115200;ttySC1 ${@ bb.utils.contains('DISTRO_FEATURES', 'copro', "", "115200;ttySC2", d)}"
SERIAL_CONSOLES:txrz ?= "115200;ttySC0 115200;ttySC1"
