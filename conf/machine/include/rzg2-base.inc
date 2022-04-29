MACHINEOVERRIDES =. "r9a07g044l:rzg2:rzg2l:"

include conf/machine/include/karo.inc
include conf/machine/include/tune-cortexa55.inc

DEFAULTTUNE = "cortexa55"

# build u-boot with image
EXTRA_IMAGEDEPENDS += "firmware-pack-rzg2 flashwriter-rzg2"

KERNEL_IMAGETYPE ?= "Image"

MACHINE_FEATURES_append = " emmc"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttySC0"
