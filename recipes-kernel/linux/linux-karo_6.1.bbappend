FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}/patches:"

SRC_URI:append = " \
    file://0001-build-add-DTBO-support.patch \
    file://0002-build-enable-RZV2H-ICU-for-DMA-support.patch \
    file://0003-net-phy-smsc-LAN8741-support.patch \
    file://0004-spi-dma-map-bugfix.patch \
    file://0005-drm-add-support-for-RPi-display-through.patch \
    file://0006-input-ts-add-support-for-RPi-display-touchscreen.patch \
    file://0007-drm-dsi-fix-hsclock-before-panel-setup.patch \
    file://0008-drm-du-add-support-for-pixelclk-polarity.patch \
    file://0009-drm-du-fix-color-swap-on-rgb-displays.patch \
    file://0010-irq-add-support-for-edge-both-interrupts.patch \
"

SRC_URI:append = " \
    file://dts/renesas/r9a07g044l2-karo.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/renesas/r9a07g044l2-qsrz.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
    file://dts/renesas/r9a07g044l2-txrz.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"
