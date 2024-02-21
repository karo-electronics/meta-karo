MACHINEOVERRIDES =. "r9a07g044l:rzg2:rzg2l:"

include conf/machine/include/karo.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

DEFAULTTUNE = "cortexa55"

SOC_PREFIX = "r9a07g044l2"
SOC_FAMILY ??= "${SOC_PREFIX}"

PREFERRED_VERSION_linux-karo = "5.10"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "flashwriter-rzg2"

KERNEL_IMAGETYPE ?= "Image"

MACHINE_FEATURES:append = " emmc"
MACHINE_FEATURES:append:qsrz = " dsi83"

UBOOT_CONFIG ??= "generic noenv"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySC0 115200;ttySC1 ${@ bb.utils.contains('DISTRO_FEATURES', 'copro', "", "115200;ttySC2", d)}"
SERIAL_CONSOLES:txrz ?= "115200;ttySC0 115200;ttySC1"

DTB_BASENAME ?= "r9a07g044l2-${MACHINE}"
