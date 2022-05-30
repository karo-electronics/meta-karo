include conf/machine/include/karo.inc

MACHINEOVERRIDES =. "mx6:"

UBOOT_MAKE_TARGET ??= "u-boot.bin"
UBOOT_SUFFIX ??= "bin"

UBOOT_ENTRYPOINT_tx6  ?= "0x10008000"
UBOOT_ENTRYPOINT_txul ?= "0x80008000"
UBOOT_CONFIG ??= "mfg generic"

# Ship imx-sdma & vpu firmware
MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
        linux-firmware-imx-sdma-imx6q \
        linux-firmware-imx-vpu \
"

# Set Serial consoles
SERIAL_CONSOLES ?= "115200;ttymxc0 115200;ttymxc1 115200;ttymxc2"

KARO_BASEBOARD_mx6 = ""

DTB_BASENAME_mx6dl ?= "imx6dl-${MACHINE}"
DTB_BASENAME_mx6q ?= "imx6q-${MACHINE}"
DTB_BASENAME_mx6ul ?= "imx6ul-${MACHINE}"

KERNEL_DEVICETREE_append = "${@ " ${DTB_BASENAME}-${KARO_BASEBOARD}.dtb" if "${KARO_BASEBOARD}" != "" else ""}"
