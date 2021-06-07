FILESEXTRAPATHS_prepend := "${THISDIR}/tf-a-karo:"

# Extra make settings
EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX}'
EXTRA_OEMAKE += 'PLAT=txmp'
EXTRA_OEMAKE += 'VENDOR=karo'
EXTRA_OEMAKE += 'ARCH=aarch32'
EXTRA_OEMAKE += 'ARM_ARCH_MAJOR=7'

# Configure default mode (All supported device types)
EXTRA_OEMAKE += 'STM32MP_SDMMC=1'
EXTRA_OEMAKE += "${@bb.utils.contains('MACHINE','qsmp-1510','STM32MP_SPI_NAND=1','STM32MP_EMMC=1',d)}"

TF_A_CONFIG_serialboot += 'DEBUG=1'
TF_A_CONFIG_serialboot += 'LOG_LEVEL=40'
TF_A_CONFIG_serialboot += 'STM32MP_USB_PROGRAMMER=1'
TF_A_CONFIG_trusted += 'LOG_LEVEL=30'

# Set default TF-A config

PREFERRED_VERSION_tf-a-stm32mp = "2.2"
