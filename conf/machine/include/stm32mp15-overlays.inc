require karo-dtb-overlays.inc

# DTB overlays
DTB_OVERLAYS ??= ""

DTB_OVERLAY_INCLUDES ?= "\
        karo-ft5x06 \
        karo-lcd-panel \
        karo-sound \
"

# STM32
DTB_OVERLAYS_generic:append:stm32mp1 = " \
        ${@ mach_overlay(d, "karo-gpu", "stm32mp157c")} \
        karo-rtc \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
"
DTB_OVERLAYS_generic:append:txmp = " \
        txmp-ft5x06 \
        txmp-sdcard \
        txmp-sdcard-cd \
        txmp-lcd-panel \
        txmp-sound \
"
DTB_OVERLAYS_generic:append:qsmp = " \
        ${@ mach_overlay(d, "qsmp-dsi-panel", "stm32mp157c")} \
        qsmp-ft5x06 \
        qsmp-ksz9031 \
        qsmp-ksz9131 \
        qsmp-lcd-panel \
        qsmp-qsbase1 \
        qsmp-qsbase2 \
        qsmp-qsbase4 \
        qsmp-qsglyn1 \
        ${@ mach_overlay(d, "qsmp-raspi-display", "stm32mp157c")} \
        qsmp-sdcard \
        qsmp-sdcard-cd \
        qsmp-smsc-phy \
        qsmp-sound \
"

KARO_BASEBOARDS:txmp ?= "\
        mb7 \
"

KARO_BASEBOARDS:qsmp ?= "\
        qsbase1 \
        qsbase2 \
        qsbase4 \
"

KARO_BASEBOARDS:append:qsmp-1570 ?= "\
        qsglyn1 \
"

KARO_BASEBOARDS:qsmp-1510 ?= "\
        qsbase1 \
        qsbase4 \
"

# baseboard DTB specific overlays
KARO_DTB_OVERLAYS[mb7] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", "stm32mp157c", "wayland")} \
        txmp-sdcard \
        txmp-sdcard-cd \
        txmp-lcd-panel \
        txmp-ft5x06 \
        txmp-sound \
        karo-rtc \
"

KARO_DTB_OVERLAYS[qsbase1] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", "stm32mp157c", "wayland")} \
        qsmp-qsbase1 \
        qsmp-ksz9031 \
        qsmp-lcd-panel \
        qsmp-sdcard \
"

KARO_DTB_OVERLAYS[qsbase2] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        qsmp-qsbase2 \
        qsmp-ksz9031 \
        ${@ mach_overlay(d, "qsmp-dsi-panel", "stm32mp157c")} \
        ${@ mach_overlay(d, "qsmp-raspi-display", "stm32mp157c")} \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", "stm32mp157c", "wayland")} \
        qsmp-qsbase4 \
        qsmp-ksz9131 \
        ${@ mach_overlay(d, "qsmp-dsi-panel", "stm32mp157c")} \
        ${@ mach_overlay(d, "qsmp-raspi-display", "stm32mp157c")} \
"

KARO_DTB_OVERLAYS[qsglyn1] = " \
        ${@ mach_overlay(d, "karo-copro", dist="copro")} \
        ${@ mach_overlay(d, "karo-gpu", "stm32mp157c", "wayland")} \
        qsmp-qsglyn1 \
        qsmp-ksz9031 \
        qsmp-lcd-panel \
"
