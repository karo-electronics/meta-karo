require karo-dtb-overlays.inc

# DTB overlays
DTB_OVERLAYS ??= ""

DTB_OVERLAY_INCLUDES ?= "\
"

DTB_OVERLAYS_generic:append:qsmp = " \
        qsmp-ft5x06 \
        qsmp-lcd-panel \
        qsmp-ksz9031 \
        qsmp-ksz9131 \
        qsmp-qsbase1 \
        qsmp-qsbase4 \
"

KARO_BASEBOARDS:qsmp ?= "\
        qsbase1 \
        qsbase4 \
"

KARO_DTB_OVERLAYS[qsbase1] = " \
        qsmp-qsbase1 \
        qsmp-ksz9031 \
        qsmp-lcd-panel \
        qsmp-ft5x06 \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
        qsmp-qsbase4 \
        qsmp-ksz9131 \
"
