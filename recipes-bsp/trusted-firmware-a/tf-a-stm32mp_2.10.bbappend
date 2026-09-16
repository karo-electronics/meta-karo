FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/patches:"

SRC_URI:append = " \
	file://fdts/stm32mp13-mx.h;subdir=git \
	file://fdts/stm32mp135c-qsmp-1351.dts;subdir=git \
	file://fdts/stm32mp135c-qsmp-1351-fw-config.dts;subdir=git \
	file://fdts/stm32mp15-karo.dtsi;subdir=git \
	file://fdts/stm32mp15-mx.h;subdir=git \
	file://fdts/stm32mp15-qsmp.dtsi;subdir=git \
	file://fdts/stm32mp15-txmp.dtsi;subdir=git \
	file://fdts/stm32mp151a-qsmp.h;subdir=git \
	file://fdts/stm32mp153a-qsmp-1530.dts;subdir=git \
	file://fdts/stm32mp153a-qsmp-1530-fw-config.dts;subdir=git \
	file://fdts/stm32mp153a-qsmp-1530w.dts;subdir=git \
	file://fdts/stm32mp153a-qsmp-1530w-fw-config.dts;subdir=git \
	file://fdts/stm32mp153a-txmp.h;subdir=git \
	file://fdts/stm32mp153a-txmp-1530.dts;subdir=git \
	file://fdts/stm32mp153a-txmp-1530-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570w.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp-1570w-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-qsmp.h;subdir=git \
	file://fdts/stm32mp157c-txmp-1570.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1570-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1571.dts;subdir=git \
	file://fdts/stm32mp157c-txmp-1571-fw-config.dts;subdir=git \
	file://fdts/stm32mp157c-txmp.h;subdir=git \
	file://fdts/stm32mp257f-txmp-ddr.h;subdir=git \
	file://fdts/stm32mp257f-txmp-2550-rcc.dtsi;subdir=git \
	file://fdts/stm32mp257f-txmp-2550.dts;subdir=git \
	file://fdts/stm32mp257f-txmp-2550-fw-config.dts;subdir=git \
	file://fdts/stm32mp257f-txmp-2570-rcc.dtsi;subdir=git \
	file://fdts/stm32mp257f-txmp-2570.dts;subdir=git \
	file://fdts/stm32mp257f-txmp-2570-fw-config.dts;subdir=git \
	file://fdts/stm32mp255f-qsmp-2550-rcc.dtsi;subdir=git \
	file://fdts/stm32mp255f-qsmp-2550.dts;subdir=git \
	file://fdts/stm32mp255f-qsmp-2550-fw-config.dts;subdir=git \
	file://fdts/stm32mp255f-qsmp-ddr.h;subdir=git \
	file://fdts/stm32mp235c-qsmp-2030-rcc.dtsi;subdir=git \
	file://fdts/stm32mp235c-qsmp-2030.dts;subdir=git \
	file://fdts/stm32mp235c-qsmp-2030-fw-config.dts;subdir=git \
	file://fdts/stm32mp235c-qsmp-2030-ddr.h;subdir=git \
	file://fdts/stm32mp235c-qsmp-2350-rcc.dtsi;subdir=git \
	file://fdts/stm32mp235c-qsmp-2350.dts;subdir=git \
	file://fdts/stm32mp235c-qsmp-2350-fw-config.dts;subdir=git \
	file://fdts/stm32mp235c-qsmp-ddr.h;subdir=git \
"

SRC_URI:append = " \
	file://0001-Modify-reset-reason-trace-level.patch \
	file://0002-fix-stm32mp2-standardize-OTP-node-names-from-undersc.patch \
	file://0003-fix-stm32mp-use-MiB-instead-of-MB-for-memory-sizes.patch \
	file://0004-fix-dt-add-missing-address-size-cells-and-cleanup-sy.patch \
	file://0005-refactor-improve-page_align-and-remove-dead-code.patch \
	file://0006-fix-improve-error-messages-and-code-style.patch \
	file://0007-stm32mp-define-BL2-ro-size.patch \
	file://0008-fix-no-board_id-error.patch \
	file://0009-stm32mp2-add-debug-uart.patch \
	file://0010-stm32mp-pmic-use-microvolt-to-avoid-precision-loss.patch \
	file://0011-drivers-pmic-add-pca9450-driver.patch \
"

# Extra make settings
EXTRA_OEMAKE = 'CROSS_COMPILE=${STAGING_DATADIR_NATIVE}/${ST_CROSS_COMPILE_BIN}/${ST_CROSS_COMPILE}'
EXTRA_OEMAKE:append = " PLAT=${TF_A_PLATFORM}"
EXTRA_OEMAKE:append = " ARCH=${TF_A_ARCH}"
EXTRA_OEMAKE:append = " ARM_ARCH_MAJOR=${TF_A_ARM_MAJOR}"
EXTRA_OEMAKE:append = " STM32MP_EMMC=1"

EXTRA_OEMAKE:append:stm32mp1 = " AARCH32_SP=optee"
EXTRA_OEMAKE:append:stm32mp1 = " STM32MP_BL2_SIZE=0x0001e000"

EXTRA_OEMAKE:append:stm32mp13 = " STM32MP13=1"

EXTRA_OEMAKE:append:stm32mp2 = " ${@ " STM32MP_LPDDR4_TYPE=1" if d.getVar('TF_A_DDR_TARGET') == 'lpddr4' else "STM32MP_DDR3_TYPE=1"}"
EXTRA_OEMAKE:append:stm32mp2:qsmp20 = " STM32MP_DDR3_TYPE=1"
EXTRA_OEMAKE:append:stm32mp2 = " STM32MP_EARLY_CONSOLE=1"
EXTRA_OEMAKE:append:stm32mp2 = " SPD=opteed"
EXTRA_OEMAKE:append:stm32mp2:txmp = " STM32MP_DEBUG_UART=2"
EXTRA_OEMAKE:append:stm32mp2:qsmp = " STM32MP_DEBUG_UART=4"
EXTRA_OEMAKE:append:stm32mp2 = " STM32MP_BL31_SIZE=0x1e000"
EXTRA_OEMAKE:append:stm32mp2common = " ${@ "STM32MP_%s=1" % "${KARO_BOARD_PMIC}".upper()}"

EXTRA_OEMAKE:append = " STM32MP_EMMC=1"

TF_A_CONFIG_usb:append = ' STM32MP_USB_PROGRAMMER=1'
TF_A_CONFIG_usb:append = ' DEBUG=1'
TF_A_CONFIG_usb:append = ' LOG_LEVEL=30'
TF_A_CONFIG_usb:append:stm32mp2 = ' STM32MP_BL2_RO_SIZE=0x00021000'
TF_A_CONFIG_usb:append:stm32mp2:qsmp = ' STM32MP_BL2_SIZE=0x00029000'

TF_A_CONFIG_optee:append = ' LOG_LEVEL=30'
TF_A_CONFIG_optee:append:stm32mp2 = ' STM32MP_BL2_RO_SIZE=0x00023000'
TF_A_CONFIG_optee:append:stm32mp2 = ' STM32MP_BL2_SIZE=0x0002b000'

TF_A_CONFIG_usb:append:stm32mp25:txmp = ' STM32MP_BL2_SIZE=0x00029000'
TF_A_CONFIG_usb:append:stm32mp25:txmp = ' STM32MP_BL2_RO_SIZE=0x00022000'
TF_A_CONFIG_usb:append:stm32mp25:txmp = ' STM32MP_BL31_SIZE=0x1e000'

TF_A_CONFIG_optee:append:stm32mp25:txmp = ' STM32MP_BL2_SIZE=0x00029000'
TF_A_CONFIG_optee:append:stm32mp25:txmp = ' STM32MP_BL2_RO_SIZE=0x0001c000'
TF_A_CONFIG_optee:append:stm32mp25:txmp = ' STM32MP_BL31_SIZE=0x1e000'

TF_A_CONFIG_trusted:append = ' LOG_LEVEL=30'
