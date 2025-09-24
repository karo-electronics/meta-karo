FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

SRC_URI:append = " \
        file://karo-spidev-test.patch \
        file://raspi-display.patch \
"

SRC_URI:append:stm32mpcommon = " \
        file://usart-sysrq-bugfix.patch \
"

SRC_URI:append:stm32mpcommon = " \
        file://patches/stm32mp-kconfig-bugfixes.patch \
"
