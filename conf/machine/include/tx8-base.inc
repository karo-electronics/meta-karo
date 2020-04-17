MACHINEOVERRIDES =. "mx8:mx8m:"

KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

require conf/machine/include/arm/arch-arm64.inc

UBOOT_CONFIG ??= "mfg generic"

SPL_BINARY = "spl/u-boot-spl.bin"

# Set DDR FIRMWARE
DDR_FIRMWARE_NAME = "ddr3_imem_1d.bin ddr3_dmem_1d.bin"

# Set imx-mkimage boot target
IMXBOOT_TARGETS = "flash_tx8m"

# Set Serial consoles
SERIAL_CONSOLES = "115200;ttymxc0 115200;ttymxc1 115200;ttymxc2"

IMAGE_BOOTLOADER = "imx-boot"

BOOT_SPACE = "65536"

PREFERRED_PROVIDER_u-boot ?= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-karo"

LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = ""

# Kernel Configs
PREFERRED_PROVIDER_virtual/kernel ?= "linux-karo"
PREFERRED_VERSION_linux-karo ?= "5.4"
