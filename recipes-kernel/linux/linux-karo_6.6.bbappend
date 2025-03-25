FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

SRC_URI:append = " \
        file://karo-spidev-test.patch \
"

SRC_URI:append:stm32mp13 = " \
        file://patches/stm32mp13-kconfig-bugfixes.patch \
"
