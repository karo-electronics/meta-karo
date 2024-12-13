# patches
SRC_URI:append:stm32mp1 = " \
    file://patches/0001-${OPTEE_VERSION}-${OPTEE_SUBVERSION}-r1.patch \
    file://patches/0002-${OPTEE_VERSION}-${OPTEE_SUBVERSION}-${OPTEE_RELEASE}.patch \
"

SRC_URI:append:stm32mp1 = " \
    file://patches/dump-general-regs.patch \
    file://patches/clk-name-bugfix.patch \
"

SRC_URI:append:stm32mp2 = " \
    file://patches/stm32mp2-bugfix.patch \
    file://patches/whitespace-cleanup.patch \
"
SRC_URI:append:stm32mp2 = "${@ " file://patches/regulator-microvolts.patch" if d.getVar('REGULATORS_MICROVOLTS') == '1' else ""}"

# dts files
SRC_URI:append = " \
    file://dts/${DTB_BASENAME}.dts;subdir=git/core/arch/arm/ \
"

SRC_URI:append:stm32mp15 = " \
    file://dts/stm32mp15-karo.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp15-txmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp151-qsmp.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp157-qsmp.dtsi;subdir=git/core/arch/arm/ \
"

SRC_URI:append:stm32mp25 = " \
    file://dts/stm32mp255c-txmp-2550-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp255c-txmp-2550-resmem.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp255c-txmp-2550-rif.dtsi;subdir=git/core/arch/arm/ \
"
