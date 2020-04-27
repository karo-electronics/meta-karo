FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
	file://0013-net-bootp-convert-messages-about-unhandled-DHCP-opti.patch \
	file://0025-pinctrl-print-error-message-with-dev_err-rather-than.patch \
"

SRC_URI_append_mx6 = " \
	file://0001-compiler-.h-sync-include-linux-compiler-.h-with-Linu.patch \
	file://0002-net-use-packed-structs.patch \
	file://0003-dont-use-soft-float.patch \
	file://0004-fix-duplicate-const-errors.patch \
	file://0005-fix-tester_pads-setup.patch \
	file://0006-use-bootp-random-id.patch \
	file://0007-increase-bootm-len.patch \
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
