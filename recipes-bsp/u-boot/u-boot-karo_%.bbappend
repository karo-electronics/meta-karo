FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
"

SRC_URI_append_mx6 = " \
	file://0001-compiler-.h-sync-include-linux-compiler-.h-with-Linu.patch \
	file://0002-net-use-packed-structs.patch \
	file://0003-dont-use-soft-float.patch \
	file://0004-fix-duplicate-const-errors.patch \
	file://0005-fix-tester_pads-setup.patch \
	file://0006-use-bootp-random-id.patch \
	file://0007-increase-bootm-len.patch \
	file://0013-net-bootp-convert-messages-about-unhandled-DHCP-opti.patch \
	file://0025-pinctrl-print-error-message-with-dev_err-rather-than.patch \
"

SRC_URI_append_mx8m = " \
	file://0001-Fix-alignment-of-reserved-memory-section.patch \
	file://0002-serial-Add-missing-dependencies-for-IMX8-to-MXC_UART.patch \
	file://0003-net-Add-missing-dependencies-for-IMX8-to-FEC_MXC.patch \
	file://0004-net-fec_mxc-add-missing-FEC_QUIRK_ENET_MAC-definitio.patch \
	file://0005-imx8m-add-missing-prototype-for-imx_get_mac_from_fus.patch \
	file://0006-net-define-missing-Kconfig-symbol-MII-which-is-selec.patch \
	file://0007-imx-spl-remove-weak-attribute-from-jump_to_image_no_.patch \
	file://0008-pmic-bd71837-silence-noisy-output-and-prevent-compil.patch \
	file://0009-usb-gadget-sdp-silence-noisy-messages.patch \
	file://0010-autoboot-don-t-change-bootcmd-to-fastboot-when-CMD_F.patch \
	file://0011-imx8mm-distinguish-between-watchdog-and-softreset.patch \
	file://0012-imx8mm-don-t-clear-the-reset-status-upon-reading-it.patch \
	file://0014-common-autoboot-make-Normal-Boot-a-debug-message.patch \
	file://0015-fs-fs.c-correctly-interpret-the-max-len-parameter-to.patch \
	file://0017-common-add-call-to-show_activity-in-main-cmd-loop.patch \
	file://0016-drivers-ddr-imx8m-silence-noisy-messages.patch \
	file://0018-imx8mn-soc-put-env_get_location-in-board-specific-co.patch \
	file://0019-imx-make-use-of-WDOG_B-output-selectable-via-Kconfig.patch \
	file://0020-net-phy-micrel-cleanup-skew-config-code.patch \
	file://0021-net-phy-micrel-fix-the-max-value-that-is-used-to-cap.patch \
	file://0022-net-phy-micrel-support-storing-the-skew-parameters-i.patch \
	file://0023-env-fix-handling-of-.callbacks-variable.patch \
	file://0024-clk-print-error-messages-with-dev_err-pr_err-rathern.patch \
	file://0026-dm-print-device-name-in-dev_printk.patch \
	file://0027-net-fec-various-cleanups.patch \
	file://0028-net-fec-make-the-use-of-CONFIG_FEC_MXC_PHYADDR-actua.patch \
	file://0029-net-fec-don-t-blindly-assign-xcv_type-from-CONFIG_FE.patch \
	file://0030-net-fec-add-support-for-PHY-reset-via-GPIO.patch \
	file://0031-imx8mm-clock-fix-setting-for-PHY_REF_CLK-at-25MHz.patch \
	file://0032-imx8mm-clock-add-a-config-option-to-select-a-125MHz-.patch \
	file://0033-Ka-Ro-TX8M-QS8M-Support.patch \
	file://0034-karo-qs8m-change-U-Boot-prompt-to-QS8M.patch \
	file://0035-net-phy-ksz9031-add-workaround-for-1000baseT-chip-er.patch \
	file://0036-qs8m-nd00-support.patch \
"

SRC_URI_append_stm32mp1 = " \
	file://show-activity.patch \
	file://wdog-bugfix.patch \
	file://tftp-rand-port.patch \
	file://env-callback-bugfix.patch \
	file://env-callback-returnvalue.patch \
	file://dot_callbacks-bugfix.patch \
	file://backlight-errmsgs.patch \
	file://ddr-interactive-wdog.patch \
	file://md-wdog.patch \
	file://stm32-ltdc-bugfix.patch \
	file://err-value-bugfix.patch \
	file://phy-skew-bugfix.patch \
	file://stm32-sdmmc-bugfix.patch \
	file://gpio-uclass-bugfix.patch \
	file://dev_printk-devname.patch \
	file://stm32-pkg-names.patch \
	file://adc-uclass-errmsg.patch \
	file://signed-compare-bugfix.patch \
	file://show_activity-bugfix.patch \
	file://hclk6-bugfix.patch \
	file://stm32mp-clk-cleanup.patch \
	file://gpt-help-formatting.patch \
	file://led-blink-bugfix.patch \
	file://bootp-random-id.patch \
	file://seed_mac-bugfix.patch \
	file://native-mode.patch \
	file://missing-newline.patch \
	file://mmc-pr_err.patch \
	file://pr_err.patch \
	file://dwc-eth-bugfix.patch \
	file://dwc-eth-phy-reset.patch \
	file://of-live-bugfixes.patch \
	file://cell_count-bugfixes.patch \
	file://panel-timing.patch \
	file://dev_read_addr_size-bugfix.patch \
	file://np-parent-bugfix.patch \
	file://stm-bugfixes.patch \
	file://fdtsize-variable.patch \
	file://dwc2-udc-bugfix.patch \
	file://i2c6-clk-bugfix.patch \
	file://spi-nor-bugfix.patch \
	file://spi-nand-bugfix.patch \
	file://netsol-spi-nand.patch \
	file://_ofnode_to_np-bugfix.patch \
	file://phy-stm32-bugfix.patch \
	file://stm32prog.patch \
	file://buggy-dev-workaround.patch \
	file://udc-remove-dev.patch \
	file://txmp-support.patch \
"
