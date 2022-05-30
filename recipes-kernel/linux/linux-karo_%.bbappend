FILESEXTRAPATHS_prepend_stm32mp1 := "${THISDIR}/${BP}:"

SRC_URI_append_stm32mp1 = " \
        file://STM-patches/0001-ARM-5.10.61-stm32mp1-r2-MACHINE.patch \
        file://STM-patches/0002-ARM-5.10.61-stm32mp1-r2-CLOCK.patch \
        file://STM-patches/0003-ARM-5.10.61-stm32mp1-r2-CPUFREQ.patch \
        file://STM-patches/0004-ARM-5.10.61-stm32mp1-r2-CRYPTO.patch \
        file://STM-patches/0005-ARM-5.10.61-stm32mp1-r2-DMA.patch \
        file://STM-patches/0007-ARM-5.10.61-stm32mp1-r2-HWSPINLOCK.patch \
        file://STM-patches/0008-ARM-5.10.61-stm32mp1-r2-I2C-IIO-IRQCHIP.patch \
        file://STM-patches/0009-ARM-5.10.61-stm32mp1-r2-MAILBOX-REMOTEPROC-RPMSG.patch \
        file://STM-patches/0010-ARM-5.10.61-stm32mp1-r2-MEDIA-SOC-THERMAL.patch \
        file://STM-patches/0011-ARM-5.10.61-stm32mp1-r2-MFD.patch \
        file://STM-patches/0012-ARM-5.10.61-stm32mp1-r2-MMC.patch \
        file://STM-patches/0013-ARM-5.10.61-stm32mp1-r2-NET-TTY.patch \
        file://STM-patches/0014-ARM-5.10.61-stm32mp1-r2-PERF.patch \
        file://STM-patches/0015-ARM-5.10.61-stm32mp1-r2-PHY-USB.patch \
        file://STM-patches/0016-ARM-5.10.61-stm32mp1-r2-PINCTRL-REGULATOR-SPI.patch \
        file://STM-patches/0017-ARM-5.10.61-stm32mp1-r2-RESET-RTC-WATCHDOG.patch \
        file://STM-patches/0018-ARM-5.10.61-stm32mp1-r2-SCMI.patch \
        file://STM-patches/0019-ARM-5.10.61-stm32mp1-r2-SOUND.patch \
        file://STM-patches/0020-ARM-5.10.61-stm32mp1-r2-MISC.patch \
        file://STM-patches/0021-ARM-5.10.61-stm32mp1-r2-CPUIDLE-POWER.patch \
        file://STM-patches/0022-ARM-5.10.61-stm32mp1-r2-DEVICETREE.patch \
        file://STM-patches/0023-ARM-5.10.61-stm32mp1-r2-CONFIG.patch \
        file://STM-patches/0024-5.10.61-stm32mp1-r2-TestSupportWakeupFromTouchscreen.patch \
"

SRC_URI_append_stm32mp1 = " \
        file://patches/0001-stm32mp151-bugfix.patch \
        file://patches/0002-pinctrl-z.patch \
        file://patches/0003-smsc-suspend-bugfix.patch \
        file://patches/0004-stmmac-pinctrl-bugfix.patch \
        file://patches/0005-stmmac-gpio-bugfix.patch \
        file://patches/0006-usbotg_id-bugfix.patch \
        file://patches/0007-usb-phy-bugfix.patch \
        file://patches/0009-dwc2-usbotg-bugfix.patch \
        file://patches/0010-stm32-cpufreq-bugfix.patch \
        file://patches/0011-usb-ohci-wakeup-source-suspend.patch \
        file://patches/0012-ltdc-pixclk-pol-bus-flags.patch \
"

SRC_URI_append_rzg2 = " \
        file://patches/0001-renesas-du-change-fixed-clock-polarity.patch \
        file://patches/0002-renesas-du-change-fixed-pixformat.patch \
        file://patches/0003-RZ-G2L-SSIF-slave-mode-trial.patch \
        file://patches/0004-renesas-gpiolib-irqchip-bugfix.patch \
"
