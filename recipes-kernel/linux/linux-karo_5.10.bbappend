SRC_URI:append:rzg2 = " \
    file://patches/0001-clock-support-for-CM33.patch \
    file://patches/0002-renesas-du-change-fixed-pixformat.patch \
    file://patches/0004-drm-sn65dsi83-bridge-support.patch \
    file://patches/0005-raspberrypi-7inch-touchscreen-support.patch \
    file://patches/0006-rzg2l-dsi-fix-hsclock-before-panel-setup.patch \
    file://patches/0009-gpio-int-trigger-bothedge.patch \
"

SRC_URI:remove:qsrz = " \
    file://patches/0002-renesas-du-change-fixed-pixformat.patch \
"
