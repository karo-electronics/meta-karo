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
        qsmp-dsi-panel \
        qsmp-eth1-ksz9031 \
        ${@ mach_overlay(d, "qsmp-2030-eth1-ksz9131:qsmp-eth1-ksz9131", mach="qsmp20")} \
        qsmp-eth1-lan8710 \
        qsmp-eth2-lan8710 \
        qsmp-fdcan1 \
        qsmp-lcd-panel \
        qsmp-lvds-panel \
        qsmp-qsbase5 \
        qsmp-raspi-display \
        qsmp-sdcard \
        qsmp-sdcard-cd \
        qsmp-spidev1 \
"

KARO_BASEBOARDS:qsmp ?= "\
        qsbase1 \
        qsbase4 \
        qsbase5 \
"

KARO_DTB_OVERLAYS[qsbase1] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        qsmp-lcd-panel \
        qsmp-eth1-ksz9031 \
        qsmp-fdcan1 \
        qsmp-sdcard \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", dist="wayland")} \
        ${@ mach_overlay(d, "qsmp-dsi-panel", mach="qsmp20")} \
        ${@ mach_overlay(d, "qsmp-2030-eth1-ksz9131:qsmp-eth1-ksz9131", mach="qsmp20")} \
        qsmp-fdcan1 \
        ${@ mach_overlay(d, "qsmp-raspi-display", mach="qsmp20")} \
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
