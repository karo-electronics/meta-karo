FILESEXTRAPATHS:prepend := "${THISDIR}/tf-a-karo:${THISDIR}/tf-a-karo/patches:"

SRC_URI:append:stm32mp13 = " \
	file://fdts/stm32mp13-mx.h;subdir=git \
	file://fdts/stm32mp135c-qsmp-1351.dts;subdir=git \
	file://fdts/stm32mp135c-qsmp-1351-fw-config.dts;subdir=git \
"

SRC_URI:append:stm32mp15 = " \
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
	file://fdts/stm32mp153a-txmp-1530.dts;subdir=git \
	file://fdts/stm32mp153a-txmp-1530-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp.h;subdir=git \
	file://fdts/stm32mp157c-txmp-1570.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1570-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1571.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1571-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-txmp.h;subdir=git \
"

SRC_URI:append:stm32mp25 = " \
	file://fdts/stm32mp25-mx.h;subdir=git \
	file://fdts/stm32mp255c-txmp-2550-rcc.dtsi;subdir=git \
	file://fdts/stm32mp255c-txmp-2550.dts;subdir=git \
	file://fdts/stm32mp255c-txmp-2550-fw-config.dts;subdir=git \
"

SRC_URI:append:stm32mp1common = " \
	file://0001-v2.8-stm32mp-r1.patch \
	file://0002-v2.8-stm32mp-r1.1.patch \
"

SRC_URI:append:qsmp-1510 = " \
	file://0003-nand.patch \
	file://0004-fip-offset.patch \
"

SRC_URI:append:stm32mp25 = " \
        file://stm32mp25-bugfix.patch \
        file://stm32mp25-dtsi-fixups.patch \
"

SRC_URI:append = " \
        file://bl2-ro-size.patch \
"

# Extra make settings
EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
EXTRA_OEMAKE += "PLAT=${TF_A_PLATFORM}"
EXTRA_OEMAKE += "ARCH=${TF_A_ARCH}"
EXTRA_OEMAKE += "ARM_ARCH_MAJOR=${TF_A_ARM_MAJOR}"
EXTRA_OEMAKE += "STM32MP_EMMC=1"

EXTRA_OEMAKE:append:stm32mp1 = " AARCH32_SP=optee"
EXTRA_OEMAKE:append:stm32mp1 = " STM32MP_BL2_SIZE=0x0001e000"

EXTRA_OEMAKE:append:stm32mp13 = " STM32MP13=1"

EXTRA_OEMAKE:append:stm32mp25 = " STM32MP_LPDDR4_TYPE=1"
EXTRA_OEMAKE:append:stm32mp25 = " STM32MP_EARLY_CONSOLE=1"
EXTRA_OEMAKE:append:stm32mp25 = " SPD=opteed"

EXTRA_OEMAKE += "${@bb.utils.contains('FLASHLAYOUT_CONFIG_LABELS','spinand','STM32MP_SPI_NAND=1','STM32MP_EMMC=1',d)}"

TF_A_CONFIG_usb += 'DEBUG=1'
TF_A_CONFIG_usb += 'LOG_LEVEL=40'
TF_A_CONFIG_usb += 'STM32MP_USB_PROGRAMMER=1'
TF_A_CONFIG_usb:stm32mp25 += 'STM32MP_BL2_RO_SIZE=0x00022000'
TF_A_CONFIG_usb:stm32mp25 += 'STM32MP_BL2_SIZE=0x0002a000'
TF_A_CONFIG_trusted += 'LOG_LEVEL=30'
TF_A_CONFIG_optee += 'LOG_LEVEL=40'
TF_A_CONFIG_optee:stm32mp25 += 'STM32MP_BL2_RO_SIZE=0x00023000'
TF_A_CONFIG_optee:stm32mp25 += 'STM32MP_BL2_SIZE=0x0002b000'
