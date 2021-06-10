FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:"

SRC_URI_append = " \
	file://karo.bmp;subdir=git/tools/logos \
"

SRC_URI_append_mx6 = " \
	file://0001-dont-use-soft-float.patch \
"

SRC_URI_append_stm32mp1 = " \
	file://dcache-off.patch \
"
