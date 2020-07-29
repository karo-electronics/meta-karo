FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
"

SRC_URI_append_mx6 = " \
	file://0001-dont-use-soft-float.patch \
	file://ums-watchdog-reset.patch \
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
	file://udc-remove-dev.patch \
	file://txmp-support.patch \
	file://reloc-bugfix.patch \
	file://reloc-checks.patch \
	file://ums-watchdog-reset.patch \
"
