FILESEXTRAPATHS:prepend := "${THISDIR}/tf-a-karo:"

SRC_URI:append = " \
	file://fdts/stm32mp15-karo.dtsi;subdir=git \
	file://fdts/stm32mp15-mx.h;subdir=git \
	file://fdts/stm32mp15-qsmp.dtsi;subdir=git \
	file://fdts/stm32mp15-txmp.dtsi;subdir=git \
	file://fdts/stm32mp151a-qsmp-1510.dts;subdir=git \
	file://fdts/stm32mp151a-qsmp-1510-fw-config.dts;subdir=git \
	file://fdts/stm32mp151a-qsmp.h;subdir=git \
	file://fdts/stm32mp153a-qsmp-1530.dts;subdir=git \
	file://fdts/stm32mp153a-qsmp-1530-fw-config.dts;subdir=git \
	file://fdts/stm32mp153a-txmp.h;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp.h;subdir=git \
	file://fdts/stm32mp157c-txmp-1570.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1570-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1571.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1571-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-txmp.h;subdir=git \
"

SRC_URI:append = " \
	file://0001-v2.6-stm32mp-r1.patch \
"

SRC_URI:append:qsmp-1510 = " \
	file://0002-extend-bl2-size.patch \
	file://0003-nand.patch \
	file://0004-fip-offset.patch \
"

# Extra make settings
EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX}'
EXTRA_OEMAKE += 'PLAT=stm32mp1'
EXTRA_OEMAKE += 'ARCH=aarch32'
EXTRA_OEMAKE += 'ARM_ARCH_MAJOR=7'
EXTRA_OEMAKE += 'AARCH32_SP=sp_min'

# Configure default mode (All supported device types)
EXTRA_OEMAKE += 'STM32MP_SDMMC=1'
EXTRA_OEMAKE += "${@bb.utils.contains('FLASHLAYOUT_CONFIG_LABELS','spinand','STM32MP_SPI_NAND=1','STM32MP_EMMC=1',d)}"

TF_A_CONFIG_serialboot += 'DEBUG=1'
TF_A_CONFIG_serialboot += 'LOG_LEVEL=40'
TF_A_CONFIG_serialboot += 'STM32MP_USB_PROGRAMMER=1'
TF_A_CONFIG_trusted += 'LOG_LEVEL=30'

EXTRA_IMAGEDEPENDS += "virtual/trusted-firmware-a-serialboot"

PREFERRED_VERSION:tf-a-stm32mp = "2.6"
