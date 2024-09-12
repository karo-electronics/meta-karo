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

DTB_OVERLAYS_generic:append:stm32mp2 = " \
        karo-gpu \
        karo-rtc \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        txmp-keypad \
        txmp-mb7 \
"

DTB_OVERLAYS_generic:append:txmp = " \
        txmp-fdcan1 \
        txmp-fdcan2 \
        txmp-ft5x06 \
        txmp-lcd-panel \
        txmp-sdcard1-cd \
        txmp-sdcard2-cd \
        txmp-sound \
        txmp-spidev \
"

KARO_BASEBOARDS:txmp ?= "\
        mb7 \
"

# baseboard DTB specific overlays
KARO_DTB_OVERLAYS[mb7] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        txmp-mb7 \
        txmp-fdcan1 \
        txmp-ft5x06 \
        txmp-keypad \
        txmp-lcd-panel \
        txmp-sound \
        txmp-spidev \
"
