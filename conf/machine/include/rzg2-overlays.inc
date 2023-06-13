require karo-dtb-overlays.inc

# DTB overlays depending on module
DTB_OVERLAYS ??= ""

DTB_OVERLAYS:append:rzg2 = " \
    karo-copro \
    karo-otg-host \
    karo-led \
    karo-sdcard \
    karo-sdcard-cd \
"

DTB_OVERLAY_INCLUDES ??= " \
    karo-lcd-panel \
"

DTB_OVERLAYS:append:txrz = " \
    txrz-rtc \
    ${@ mach_overlay(d, "txrz-lvds-panel:txrz-lcd-panel", "txrz-g2l2")} \
    txrz-ft5x06 \
    txrz-sound \
"

DTB_OVERLAYS:append:qsrz = " \
    qsrz-ksz9131 \
    qsrz-lcd-panel \
    qsrz-ft5x06 \
    qsrz-ili2130 \
    qsrz-dsi83 \
    qsrz-raspi-display \
"

KARO_BASEBOARDS:txrz ?= "\
    mb7 \
"

KARO_BASEBOARDS:txrz-g2l2 ?= "\
    lvds-mb \
"

KARO_BASEBOARDS:qsrz ?= "\
    qsbase1 \
    qsbase4 \
    qsglyn1 \
"

# baseboard DTB specific overlays
KARO_DTB_OVERLAYS[mb7] = " \
    ${@ mach_overlay(d, "karo-copro", dist="copro")} \
    karo-led \
    karo-sdcard-cd \
    txrz-rtc \
    txrz-lcd-panel \
    txrz-ft5x06 \
    txrz-sound \
"

KARO_DTB_OVERLAYS[lvds-mb] = " \
    ${@ mach_overlay(d, "karo-copro", dist="copro")} \
    karo-led \
    karo-sdcard \
    txrz-rtc \
    txrz-lvds-panel \
    txrz-sound \
"

KARO_DTB_OVERLAYS[qsbase1] = " \
    ${@ mach_overlay(d, "karo-copro", dist="copro")} \
    karo-led \
    karo-sdcard \
    qsrz-lcd-panel \
    qsrz-ft5x06 \
    qsrz-ksz9131 \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
    ${@ mach_overlay(d, "karo-copro", dist="copro")} \
    karo-led \
    karo-sdcard \
    qsrz-dsi83 \
    qsrz-raspi-display \
    qsrz-ksz9131 \
"

KARO_DTB_OVERLAYS[qsglyn1] = " \
    ${@ mach_overlay(d, "karo-copro", dist="copro")} \
    karo-led \
    karo-sdcard \
    qsrz-lcd-panel \
    qsrz-ili2130 \
    qsrz-dsi83 \
    qsrz-raspi-display \
    qsrz-ksz9131 \
"
