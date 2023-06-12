SRC_URI:append:rzg2 = " \
    file://patches/0001-rcar-du-pixclk-polarity-from-drm-connector-bus-flags.patch \
    file://patches/0002-renesas-du-change-fixed-pixformat.patch \
    file://patches/0003-RZ-G2L-SSIF-slave-mode-trial.patch \
    file://patches/0004-drm-sn65dsi83-bridge-support.patch \
    file://patches/0005-raspberrypi-7inch-touchscreen-support.patch \
    file://patches/0006-rzg2l-dsi-fix-hsclock-before-panel-setup.patch \
    file://patches/0007-dsi83-10inch-clock.patch \
    file://patches/0008-10inch-parallel-clock.patch \
    file://patches/0009-gpio-int-trigger-bothedge.patch \
"

SRC_URI:remove:qsrz = " \
    file://patches/0002-renesas-du-change-fixed-pixformat.patch \
"
