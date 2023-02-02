SRC_URI:append:rzg2 = " \
        file://patches/0001-renesas-du-change-fixed-clock-polarity.patch \
        file://patches/0002-renesas-du-change-fixed-pixformat.patch \
        file://patches/0003-RZ-G2L-SSIF-slave-mode-trial.patch \
        file://patches/0004-renesas-gpiolib-irqchip-bugfix.patch \
        file://patches/0005-renesas-arch-rcar-gen3-bugfix.patch \
        file://patches/0006-drm-rzg2l_mipi_dsi-add-host-transfer-function-for-RZ.patch \
        file://patches/0007-Fixed-an-issue-that-caused-flicker-when-outputting-t.patch \
        file://patches/0008-drm-rzg2l_mipi_dsi-initialize-panel-after-high-speed.patch \
        file://patches/0009-drm-rcar-du-rzg2l_mipi_dsi-move-parsing-number-of-da.patch \
        file://patches/0010-drm-rcar-du-Modify-clk-div-to-support-2-lane-MIPI-DS.patch \
        file://patches/0011-drm-rzg2l-mipi-dsi-comment-out-lane-check-that-prevents-from-working.patch \
        file://patches/0012-drm-sn65dsi83-bridge-support.patch \
"

SRC_URI:append:stm32mp1 = " \
	file://dts/overlays/stm32mp15-karo-ft5x06.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
	file://dts/overlays/stm32mp15-karo-lcd-panel.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
	file://dts/overlays/stm32mp15-karo-sound.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

EXTRA_OEMAKE:append = " V=0"

# DTB overlays
DTB_OVERLAYS ??= ""

KERNEL_DTC_FLAGS += "-@"

inherit deploy
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
