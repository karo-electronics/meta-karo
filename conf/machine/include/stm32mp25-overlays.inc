require karo-dtb-overlays.inc

# DTB overlays
DTB_OVERLAYS ??= ""
DTB_OVERLAY_INCLUDES ??= ""
DTB_OVERLAYS_generic ??= ""
KARO_DTB_OVERLAYS ??= ""

DTB_OVERLAY_INCLUDES ?= "\
        karo-ft5x06 \
        karo-lcd-panel \
        karo-sound \
"

DTB_OVERLAYS_generic:append = " \
        karo-gpu \
        karo-rtc \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
"

DTB_OVERLAYS_generic:append:txmp = " \
        txmp-fdcan1 \
        txmp-fdcan2 \
        txmp-ft5x06 \
        txmp-keypad \
        txmp-lcd-panel \
        txmp-mb7 \
        txmp-mb7-wakeup-btn \
        txmp-sdcard1-cd \
        txmp-sdcard2-cd \
        txmp-sound \
        txmp-spidev \
"

DTB_OVERLAYS_generic:append:qsmp = " \
        karo-panel-tm101jvhg32 \
        qsmp-csi \
        qsmp-eth1-ksz9131 \
        qsmp-eth1-lan8710 \
        qsmp-eth2-lan8710 \
        qsmp-fdcan1 \
        qsmp-fdcan2 \
        qsmp-lvds-panel \
        qsmp-pcie \
        qsmp-qsbase5 \
        qsmp-spidev1 \
        qsmp-spidev2 \
"

KARO_BASEBOARDS:txmp ?= "\
        mb7 \
"

KARO_BASEBOARDS:qsmp ?= "\
        qsbase4 \
        qsbase5 \
"

# baseboard DTB specific overlays
KARO_DTB_OVERLAYS[mb7] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        txmp-mb7 \
        txmp-fdcan1 \
        txmp-ft5x06 \
        txmp-keypad,txmp-mb7-wakeup-btn \
        txmp-lcd-panel \
        txmp-sound \
        txmp-spidev \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        qsmp-eth1-ksz9131 \
        qsmp-fdcan1 \
        qsmp-fdcan2 \
        qsmp-spidev2 \
"

KARO_DTB_OVERLAYS[qsbase5] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        qsmp-csi \
        qsmp-eth1-lan8710 \
        qsmp-eth2-lan8710 \
        qsmp-fdcan1 \
        qsmp-fdcan2 \
        qsmp-qsbase5 \
        qsmp-lvds-panel \
        qsmp-pcie \
        qsmp-spidev1 \
        qsmp-spidev2 \
        karo-panel-tm101jvhg32 \
"
