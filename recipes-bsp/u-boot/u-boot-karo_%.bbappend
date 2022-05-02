FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:${THISDIR}/${PN}/cfg:"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
"

SRC_URI_append_mx6 = " \
	file://0001-dont-use-soft-float.patch \
"

SRC_URI_append_stm32mp1 = " \
        file://dcache-off.patch \
        file://dts/stm32mp15-qsmp.dtsi;subdir=git/arch/arm \
"

SRC_URI_append_qsmp = " \
        file://qsbase-dts.patch \
        file://rgmii-id.patch \
        file://dts/stm32mp151a-qsmp-1510-qsbase2.dts;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase2-u-boot.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp15-qsbase4.dtsi;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase4.dts;subdir=git/arch/arm \
        file://dts/stm32mp151a-qsmp-1510-qsbase4-u-boot.dtsi;subdir=git/arch/arm \
        file://qsmp-1510-qsbase2_env.txt;subdir=git/board/karo/txmp \
        file://qsmp-1510-qsbase4_env.txt;subdir=git/board/karo/txmp \
"
