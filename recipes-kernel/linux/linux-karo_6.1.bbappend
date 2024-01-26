FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

SRC_URI:append:stm32mp1 = " \
        file://STM-patches/0001-v6.1-stm32mp-r1-BUS.patch \
        file://STM-patches/0002-v6.1-stm32mp-r1-CLOCK.patch \
        file://STM-patches/0003-v6.1-stm32mp-r1-CPUFREQ.patch \
        file://STM-patches/0004-v6.1-stm32mp-r1-CPUIDLE-POWER.patch \
        file://STM-patches/0005-v6.1-stm32mp-r1-CRYPTO.patch \
        file://STM-patches/0006-v6.1-stm32mp-r1-DMA.patch \
        file://STM-patches/0007-v6.1-stm32mp-r1-DRM.patch \
        file://STM-patches/0008-v6.1-stm32mp-r1-HWSPINLOCK.patch \
        file://STM-patches/0009-v6.1-stm32mp-r1-I2C-IIO-IRQCHIP.patch \
        file://STM-patches/0010-v6.1-stm32mp-r1-REMOTEPROC-RPMSG.patch \
        file://STM-patches/0011-v6.1-stm32mp-r1-MEDIA-SOC-THERMAL.patch \
        file://STM-patches/0012-v6.1-stm32mp-r1-MFD.patch \
        file://STM-patches/0013-v6.1-stm32mp-r1-MMC.patch \
        file://STM-patches/0014-v6.1-stm32mp-r1-NET-TTY.patch \
        file://STM-patches/0015-v6.1-stm32mp-r1-PERF.patch \
        file://STM-patches/0016-v6.1-stm32mp-r1-PHY-USB.patch \
        file://STM-patches/0017-v6.1-stm32mp-r1-PINCTRL-REGULATOR-SPI.patch \
        file://STM-patches/0018-v6.1-stm32mp-r1-RESET-RTC.patch \
        file://STM-patches/0019-v6.1-stm32mp-r1-SCMI.patch \
        file://STM-patches/0020-v6.1-stm32mp-r1-SOUND.patch \
        file://STM-patches/0021-v6.1-stm32mp-r1-MISC.patch \
        file://STM-patches/0022-v6.1-stm32mp-r1-MACHINE.patch \
        file://STM-patches/0023-v6.1-stm32mp-r1-DEVICETREE.patch \
        file://STM-patches/0024-v6.1-stm32mp-r1-CONFIG.patch \
        file://STM-patches/0025-v6.1-stm32mp-r1.1.patch \
        file://STM-patches/0026-v6.1-stm32mp-r1.1-dmaengine-stm32.patch \
        file://patches/0007-stm32cryp-dependencies.patch \
"

SRC_URI:append:stm32mp1 = " \
        file://patches/0001-stm32mp1-compress-ram-size.patch \
"

SRC_URI:append:stm32mp1leaveoutfornow = " \
        file://patches/0001-stm32mp151-bugfix.patch \
        file://patches/0001-display-support.patch \
        file://patches/0002-pinctrl-z.patch \
        file://patches/0002-panel-dpi-bus-format.patch \
        file://patches/0003-smsc-suspend-bugfix.patch \
        file://patches/0003-smsc-lan8741-support.patch \
        file://patches/0004-stmmac-pinctrl-bugfix.patch \
        file://patches/0005-stmmac-gpio-bugfix.patch \
        file://patches/0006-usbotg_id-bugfix.patch \
        file://patches/0007-usb-phy-bugfix.patch \
        file://patches/0009-dwc2-usbotg-bugfix.patch \
        file://patches/0010-attiny-regulator-i2c-retries.patch \
        file://patches/0011-stm-drv-preferred-depth.patch \
        file://patches/0012-ltdc-pixclk-pol-bus-flags.patch \
        file://patches/0015-raspberrypi-7inch-touchscreen-support.patch \
        file://patches/0016-parallel-display-bus-flags-from-display-info.patch \
        file://patches/0017-spi-nand-dma-map-bugfix.patch \
        file://patches/0019-fdt5x06-dma-bugfix.patch \
"
