include conf/machine/include/karo.inc

UBOOT_MAKE_TARGET ??= "u-boot.bin"
UBOOT_SUFFIX ??= "bin"

UBOOT_ENTRYPOINT_tx6  ?= "0x10008000"
UBOOT_ENTRYPOINT_txul ?= "0x80008000"
UBOOT_CONFIG ??= "mfg generic"

# Ship imx-sdma & vpu firmware
MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
        linux-firmware-imx-sdma-imx6q \
        ${@bb.utils.contains('DISTRO_FEATURES',"wayland","linux-firmware-imx-vpu","",d)} \
"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttymxc0 115200;ttymxc1 115200;ttymxc2"

# Default baseboard name
DEFAULT_BASEBOARD_tx6 ?= "mb7"
DEFAULT_BASEBOARD_txul ?= "evalkit"
