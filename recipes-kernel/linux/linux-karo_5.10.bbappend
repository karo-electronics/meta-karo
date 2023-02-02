SRC_URI:append:rzg2 = " \
    file://patches/0001-rcar-du-pixclk-polarity-from-drm-connector-bus-flags.patch \
    file://patches/0002-renesas-du-change-fixed-pixformat.patch \
    file://patches/0003-RZ-G2L-SSIF-slave-mode-trial.patch \
    file://patches/0004-drm-sn65dsi83-bridge-support.patch \
    file://patches/0005-raspberrypi-7inch-touchscreen-support.patch \
    file://patches/0006-rzg2l-dsi-fix-hsclock-before-panel-setup.patch \
"

SRC_URI:remove:qsrz = " \
    file://patches/0002-renesas-du-change-fixed-pixformat.patch \
"

# DTB overlays depending on module
DTB_OVERLAYS ??= ""

DTB_OVERLAYS:append:rzg2 = " \
    karo-otg-host \
    karo-led \
    karo-sdcard \
    karo-sdcard-cd \
"

DTB_OVERLAYS:append:txrz = " \
    txrz-rtc \
    txrz-lcd-panel \
    txrz-ft5x06 \
    txrz-sound \
"

DTB_OVERLAYS:append:txrz-g2l2 = " \
    txrz-lvds-panel \
"

DTB_OVERLAYS:remove:txrz-g2l2 = " \
    txrz-lcd-panel \
"

DTB_OVERLAYS:append:qsrz = " \
    qsrz-ksz9131 \
    qsrz-lcd-panel \
    qsrz-ft5x06 \
    qsrz-ili2130 \
    qsrz-dsi83 \
    qsrz-raspi-display \
"

KERNEL_DEVICETREE:append:rzg2 = "${@ "".join(map(lambda f: " renesas/r9a07g044l2-%s.dtbo" % f, "${DTB_OVERLAYS}".split()))}"

KERNEL_DTC_FLAGS += "-@"

KARO_BASEBOARDS:txrz ?= "\
    mb7 \
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
    karo-led \
    karo-sdcard-cd \
    txrz-rtc \
    txrz-lcd-panel \
    txrz-ft5x06 \
    txrz-sound \
"

KARO_DTB_OVERLAYS[lvds-mb] = " \
    karo-led \
    karo-sdcard \
    txrz-rtc \
    txrz-lvds-panel \
    txrz-sound \
"

KARO_DTB_OVERLAYS[qsbase1] = " \
    karo-led \
    karo-sdcard \
    qsrz-lcd-panel \
    qsrz-ft5x06 \
    qsrz-ksz9131 \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
    karo-led \
    karo-sdcard \
    qsrz-dsi83 \
    qsrz-raspi-display \
    qsrz-ksz9131 \
"

KARO_DTB_OVERLAYS[qsglyn1] = " \
    karo-led \
    karo-sdcard \
    qsrz-lcd-panel \
    qsrz-ili2130 \
    qsrz-dsi83 \
    qsrz-raspi-display \
    qsrz-ksz9131 \
"

DEPENDS += "dtc-native"

python do_check_dtbs () {
    import os

    def apply_overlays(infile, outfile, overlays):
        pfx = d.getVar('SOC_PREFIX')
        ovlist = map(lambda f: "%s-%s.dtb" % (pfx ,f), overlays.split())
        ovfiles = "".join(map(lambda f: " '%s'" % f, ovlist))
        cmd = ("fdtoverlay -i '%s.dtb' -o '%s.dtb' %s" % (infile, outfile, ovfiles))
        bb.debug(2, "%s" % cmd)
        if os.system("%s" % cmd):
            bb.fatal("Failed to apply overlays '%s' for '%s' to '%s'" %
                (ovfiles, baseboard, infile))
        bb.note("FDT overlays %s for '%s' successfully applied to '%s.dtb'" %
            (ovfiles, baseboard, infile))
        d.appendVar('IMAGE_INSTALL', " " + outfile + ".dtb")

    here = os.getcwd()
    os.chdir(d.getVar('DEPLOYDIR'))
    baseboards = d.getVar('KARO_BASEBOARDS').split()
    for baseboard in baseboards:
        basename = d.getVar('DTB_BASENAME')
        bb.debug(2, "creating %s-%s.dtb from %s.dtb" % (basename, baseboard, basename))
        outfile = "%s-%s" % (basename, baseboard)
        overlays = d.getVarFlags('KARO_DTB_OVERLAYS', baseboard)
        bb.note("overlays='%s'" % overlays)
        bb.debug(2, "overlays for %s-%s = '%s'" % (basename, baseboard,
            "','".join(overlays[baseboard].split())))
        if overlays == None or len(overlays[baseboard].split()) == 0:
            bb.warn("%s: No overlays specified for %s" % (d.getVar('MACHINE'), baseboard))
            continue
        apply_overlays(basename, outfile, overlays[baseboard])

    os.chdir(here)
}

addtask do_check_dtbs after do_deploy
