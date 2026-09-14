FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/patches:"

SRC_URI:append = " \
    file://karo-spidev-test.patch \
    file://raspi-display.patch \
    file://usart-sysrq-bugfix.patch \
    file://stm32mp-kconfig-bugfixes.patch \
"

SRC_URI:append = " \
    file://dts/st/stm32mp15-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp15-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp15-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp15-karo-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp15-qsmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp153-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp153-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp153-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp153a-karo-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp153a-qsmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp157-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp157-qsmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp157-txmp.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp157c-karo-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp157c-qsmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp157c-txmp-scmi.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp23-karo-resmem.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/st/stm32mp25-karo-resmem.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"
