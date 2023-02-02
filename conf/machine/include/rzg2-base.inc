MACHINEOVERRIDES =. "r9a07g044l:rzg2:rzg2l:"

include conf/machine/include/karo.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

DEFAULTTUNE = "cortexa55"

PREFERRED_VERSION_linux-karo = "5.10"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "firmware-pack flashwriter-rzg2"

KERNEL_IMAGETYPE ?= "Image"

MACHINE_FEATURES:append = " emmc"
MACHINE_FEATURES:append:qsrz = " dsi83"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySC0 115200;ttySC1 115200;ttySC2"

DTB_BASENAME ?= "r9a07g044l2-${MACHINE}"
