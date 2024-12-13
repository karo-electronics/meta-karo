FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

SRC_URI:append = " \
        file://karo-spidev-test.patch \
"

SRC_URI:append:stm32mpcommon = " \
        file://patches/0001-display-support.patch \
        file://patches/0002-stmmac-gpio-bugfix.patch \
        file://patches/0003-smsc-suspend-bugfix.patch \
        file://patches/0004-smsc-lan8741-support.patch \
        file://patches/0006-usbotg_id-bugfix.patch \
        file://patches/0007-usb-phy-bugfix.patch \
        file://patches/0009-dwc2-usbotg-bugfix.patch \
        file://patches/0011-stm-drv-preferred-depth.patch \
        file://patches/0017-spi-nand-dma-map-bugfix.patch \
        file://patches/0019-fdt5x06-dma-bugfix.patch \
"

SRC_URI:append:stm32mp1 = " \
        file://patches/0001-stm32mp1-compress-ram-size.patch \
"

SRC_URI:append:stm32mp15 = " \
        file://patches/0010-attiny-regulator-i2c-retries.patch \
        file://patches/0015-raspberrypi-7inch-touchscreen-support.patch \
"
SRC_URI:append:stm32mp25 = " \
        file://patches/stm32mp25-bugfix.patch \
        file://patches/qsmp-2550-support.patch \
"
