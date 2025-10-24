# patches
SRC_URI:append:stm32mp1:not = " \
    file://patches/0001-${OPTEE_VERSION}-${OPTEE_SUBVERSION}-r1.patch \
    file://patches/0002-${OPTEE_VERSION}-${OPTEE_SUBVERSION}-${OPTEE_RELEASE}.patch \
"

SRC_URI:append:stm32mp1:not = " \
    file://patches/dump-general-regs.patch \
"
SRC_URI:append:stm32mp1 = " \
    file://patches/clk-name-bugfix.patch \
"
SRC_URI:append:stm32mp23 = " \
    file://patches/stm32mp2-bugfix.patch \
    file://patches/mp23-optee.patch \
"
SRC_URI:append:stm32mp25 = " \
    file://patches/stm32mp2-bugfix.patch \
"
SRC_URI:append:stm32mp2:not = " \
    file://patches/i2c-types.patch \
"
SRC_URI:append:stm32mp2:not = " \
    file://patches/whitespace-cleanup.patch \
"
SRC_URI:append:stm32mp15:txmp = " \
    file://patches/optee-gpioz.patch \
"

#SRC_URI:append:stm32mp2 = "${@ " file://patches/regulators-microvolts.patch" if d.getVar('REGULATORS_MICROVOLTS') == '1' else ""}"

SRC_URI:append:stm32mp23:qsmp = " ${@ " file://patches/pca9450%s-support.patch" % ("" if d.getVar('REGULATORS_MICROVOLTS') == '1' else "-mv")}"
SRC_URI:append:stm32mp25:qsmp = " ${@ " file://patches/pca9450%s-support.patch" % ("" if d.getVar('REGULATORS_MICROVOLTS') == '1' else "-mv")}"

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

SRC_URI:append:stm32mp23 = " \
    file://dts/stm32mp23-karo-resmem.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp235c-qsmp-2350-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp235c-qsmp-2350-rif.dtsi;subdir=git/core/arch/arm/ \
"

SRC_URI:append:stm32mp25 = " \
    file://dts/stm32mp25-karo-resmem.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp255f-qsmp-2550-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp255f-qsmp-2550-rif.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2550-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2550-rif.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2570-rcc.dtsi;subdir=git/core/arch/arm/ \
    file://dts/stm32mp257f-txmp-2570-rif.dtsi;subdir=git/core/arch/arm/ \
"
