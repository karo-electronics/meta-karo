require karo-dtb-overlays.inc

# DTB overlays
DTB_OVERLAYS ??= ""
DTB_OVERLAY_INCLUDES ??= ""
DTB_OVERLAYS_generic ??= ""
KARO_DTB_OVERLAYS ??= ""

DTB_OVERLAY_INCLUDES ?= "\
        karo-lcd-panel \
        karo-sound \
"

DTB_OVERLAYS_generic:append = " \
        karo-gpu \
        karo-rtc \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
"

DTB_OVERLAYS_generic:append:qsmp = " \
        karo-panel-tm101jvhg32 \
        qsmp-csi \
        qsmp-eth1-ksz9131 \
        qsmp-eth1-lan8710 \
        qsmp-eth2-lan8710 \
        qsmp-fdcan1 \
        qsmp-lvds-panel \
        qsmp-qsbase5 \
        qsmp-spidev1 \
"

KARO_BASEBOARDS:qsmp ?= "\
        qsbase4 \
        qsbase5 \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        qsmp-eth1-ksz9131 \
        qsmp-fdcan1 \
"

KARO_DTB_OVERLAYS[qsbase5] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        qsmp-csi \
        qsmp-eth1-lan8710 \
        qsmp-eth2-lan8710 \
        qsmp-fdcan1 \
        qsmp-qsbase5 \
        qsmp-lvds-panel \
        qsmp-spidev1 \
        karo-panel-tm101jvhg32 \
"
