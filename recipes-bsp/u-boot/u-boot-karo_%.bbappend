FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-karo:"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
"

SRC_URI_append_mx6 = " \
	file://0001-compiler-.h-sync-include-linux-compiler-.h-with-Linu.patch \
	file://0002-fix-duplicate-const-errors.patch \
	file://0003-dont-use-soft-float.patch \
"
