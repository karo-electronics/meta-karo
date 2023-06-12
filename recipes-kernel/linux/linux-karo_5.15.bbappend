FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

SRC_URI:append:stm32mp1 = " \
        file://STM-patches/0001-ARM-5.15.24-stm32mp1-r1-MACHINE.patch \
        file://STM-patches/0002-ARM-5.15.24-stm32mp1-r1-CLOCK.patch \
        file://STM-patches/0003-ARM-5.15.24-stm32mp1-r1-CPUFREQ.patch \
        file://STM-patches/0004-ARM-5.15.24-stm32mp1-r1-CRYPTO.patch \
        file://STM-patches/0005-ARM-5.15.24-stm32mp1-r1-DMA.patch \
        file://STM-patches/0006-ARM-5.15.24-stm32mp1-r1-DRM.patch \
        file://STM-patches/0007-ARM-5.15.24-stm32mp1-r1-HWSPINLOCK.patch \
        file://STM-patches/0008-ARM-5.15.24-stm32mp1-r1-I2C-IIO-IRQCHIP.patch \
        file://STM-patches/0009-ARM-5.15.24-stm32mp1-r1-REMOTEPROC-RPMSG.patch \
        file://STM-patches/0010-ARM-5.15.24-stm32mp1-r1-MISC-MEDIA-SOC-THERMAL.patch \
        file://STM-patches/0011-ARM-5.15.24-stm32mp1-r1-MFD.patch \
        file://STM-patches/0012-ARM-5.15.24-stm32mp1-r1-MMC.patch \
        file://STM-patches/0013-ARM-5.15.24-stm32mp1-r1-NET-TTY.patch \
        file://STM-patches/0014-ARM-5.15.24-stm32mp1-r1-PERF.patch \
        file://STM-patches/0015-ARM-5.15.24-stm32mp1-r1-PHY-USB.patch \
        file://STM-patches/0016-ARM-5.15.24-stm32mp1-r1-PINCTRL-REGULATOR-SPI.patch \
        file://STM-patches/0017-ARM-5.15.24-stm32mp1-r1-RESET-RTC.patch \
        file://STM-patches/0018-ARM-5.15.24-stm32mp1-r1-SCMI.patch \
        file://STM-patches/0019-ARM-5.15.24-stm32mp1-r1-SOUND.patch \
        file://STM-patches/0020-ARM-5.15.24-stm32mp1-r1-CPUIDLE-POWER.patch \
        file://STM-patches/0021-ARM-5.15.24-stm32mp1-r1-DEVICETREE.patch \
        file://STM-patches/0022-ARM-5.15.24-stm32mp1-r1-CONFIG.patch \
"

SRC_URI:append:stm32mp1 = " \
        file://patches/0001-stm32mp151-bugfix.patch \
        file://patches/0002-pinctrl-z.patch \
        file://patches/0003-smsc-suspend-bugfix.patch \
        file://patches/0004-stmmac-pinctrl-bugfix.patch \
        file://patches/0005-stmmac-gpio-bugfix.patch \
        file://patches/0006-usbotg_id-bugfix.patch \
        file://patches/0007-usb-phy-bugfix.patch \
        file://patches/0009-dwc2-usbotg-bugfix.patch \
        file://patches/0012-ltdc-pixclk-pol-bus-flags.patch \
"

SRC_URI:append:stm32mp1 = " \
	file://dts/overlays/stm32mp15-karo-ft5x06.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
	file://dts/overlays/stm32mp15-karo-lcd-panel.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
	file://dts/overlays/stm32mp15-karo-sound.dtsi;subdir=git/${KERNEL_OUTPUT_DIR} \
"

EXTRA_OEMAKE:append = " V=0"

# DTB overlays
DTB_OVERLAYS ??= ""

# STM32
DTB_OVERLAYS:append:stm32mp1 = " \
        karo-gpu \
        karo-rtc \
        karo-copro \
"
DTB_OVERLAYS:append:txmp = " \
        txmp-ft5x06 \
        txmp-sdcard \
        txmp-lcd-panel \
        txmp-sound \
"
DTB_OVERLAYS:append:qsmp = " \
        qsmp-dsi-panel \
        qsmp-ft5x06 \
        qsmp-ksz9031 \
        qsmp-ksz9131 \
        qsmp-lcd-panel \
        qsmp-qsbase1 \
        qsmp-qsbase2 \
        qsmp-qsbase4 \
        qsmp-qsglyn1 \
        qsmp-raspi-display \
        qsmp-smsc-phy \
        qsmp-sound \
"

KERNEL_DEVICETREE:append:stm32mp1 = "${@ "".join(map(lambda f: " stm32mp15-%s.dtb" % f, "${DTB_OVERLAYS}".split()))}"

KERNEL_DTC_FLAGS += "-@"

KARO_BASEBOARDS:txmp ?= "\
	mb7 \
"

KARO_BASEBOARDS:qsmp ?= "\
	qsbase1 \
	qsbase2 \
	qsbase4 \
        qsglyn1 \
"

# baseboard DTB specific overlays
KARO_DTB_OVERLAYS[mb7] = " \
        txmp-sdcard \
        txmp-lcd-panel \
        txmp-ft5x06 \
        txmp-sound \
        karo-rtc \
"

KARO_DTB_OVERLAYS[qsbase1] = " \
        karo-copro \
        qsmp-qsbase1 \
        qsmp-ksz9031 \
        qsmp-lcd-panel \
"

KARO_DTB_OVERLAYS[qsbase2] = " \
        karo-copro \
        qsmp-qsbase2 \
        qsmp-ksz9031 \
        ${@ "qsmp-dsi-panel" if d.getVar('SOC_FAMILY') == "stm32mp157c" else ""} \
        ${@ "qsmp-raspi-display" if "raspi-display" in d.getVar('DISTRO_FEATURES') else ""} \
"

KARO_DTB_OVERLAYS[qsbase4] = " \
        karo-copro \
        qsmp-qsbase4 \
        qsmp-ksz9131 \
        ${@ "qsmp-dsi-panel" if d.getVar('SOC_FAMILY') == "stm32mp157c" else ""} \
        ${@ "qsmp-raspi-display" if "raspi-display" in d.getVar('DISTRO_FEATURES') else ""} \
"

KARO_DTB_OVERLAYS[qsglyn1] = " \
        karo-copro \
        qsmp-qsglyn1 \
        qsmp-ksz9031 \
        qsmp-lcd-panel \
"

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
