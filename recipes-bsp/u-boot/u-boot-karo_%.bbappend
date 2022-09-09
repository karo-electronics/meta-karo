FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:${THISDIR}/${PN}/cfg:"

SRC_URI:append = " \
        file://karo.bmp;subdir=git/tools/logos \
"

SRC_URI:append:mx6 = " \
        file://0001-dont-use-soft-float.patch \
"

SRC_URI:append:stm32mp1 = " \
        file://dcache-off.patch \
        file://spi2-clk.patch \
        file://fdt-overlay-support.patch \
"

SRC_URI:append:stm32mp1 = " \
        file://dts/stm32mp15-karo.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp15-qsbase4.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp15-qsmp-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp15-qsmp.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp15-txmp-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp15-txmp.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase2-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase2.dts;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase4-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase4.dts;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510.dts;subdir=git/arch/arm \
        file://dts/stm32mp153-karo.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp153-qsmp.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp153-txmp.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp153a-qsmp-1530-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp153a-qsmp-1530.dts;subdir=git/arch/arm \
        file://dts/stm32mp157-karo.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp157-qsmp.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp157-txmp.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp157c-qsmp-1570-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp157c-qsmp-1570.dts;subdir=git/arch/arm \
        file://dts/stm32mp157c-txmp-1570-mb7-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp157c-txmp-1570-mb7.dts;subdir=git/arch/arm \
        file://dts/stm32mp157c-txmp-1570-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp157c-txmp-1570.dts;subdir=git/arch/arm \
"

SRC_URI:append:qsmp = " \
        file://qsbase-dts.patch \
        file://rgmii-id.patch \
"

SRC_URI:append = "${@ "" if 'mx6' in "${MACHINEOVERRIDES}".split(':') else "\
        file://dts/${DTB_BASENAME}.dts;subdir=git/arch/arm \
        file://dts/${DTB_BASENAME}-u-boot.dtsi;subdir=git/arch/arm \
"}"
