FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/patches:"

SRC_URI:append = " \
    file://0001-plat-stm32mp1-scmi_server-refactor-ETZPC-access-cont.patch \
    file://0002-stm32mp1-initialize-gpioz_nbpin-with-0-when-no-compa.patch \
    file://0003-dt-bindings-nvmem-update-STM32-romem-layout-naming-t.patch \
    file://0004-dts-stm32mp231-add-OPP-and-fix-SCMI-regulator-node-a.patch \
    file://0005-drivers-pmic-add-PCA9450-PMIC-driver-support.patch \
"

SRC_URI:append = " \
    file://dts/${DTB_BASENAME}.dts;subdir=git/core/arch/arm/ \
    file://dts/stm32mp15-karo.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp15-txmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp151-qsmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp157-qsmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp23-karo-resmem.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp235c-qsmp-2350-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp235c-qsmp-2350-rif.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp235c-qsmp-2030-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp235c-qsmp-2030-rif.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp25-karo-resmem.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp255f-qsmp-2550-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp255f-qsmp-2550-rif.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2550-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2550-rif.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2570-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2570-rif.dtsi;subdir=git/core/arch/arm/ \
"
