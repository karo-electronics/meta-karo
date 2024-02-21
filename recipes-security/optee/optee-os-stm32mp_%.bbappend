SRC_URI:append = " \
    file://dts/${TF_A_DEVICETREE}.dts;subdir=git/core/arch/arm/ \
"

SRC_URI:append:stm32mp15 = " \
    file://dts/stm32mp15-karo.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp15-txmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp151-qsmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp157-qsmp.dtsi;subdir=git/core/arch/arm/ \
"

SRC_URI:append = " \
    file://dump-general-regs.patch \
    file://clk-name-bugfix.patch \
"
