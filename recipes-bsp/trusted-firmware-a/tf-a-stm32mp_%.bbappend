FILESEXTRAPATHS_prepend := "${THISDIR}/tf-a-karo:"
SRC_URI_append = " \
    file://0001-stm-dts-bugfixes.patch \
    file://0002-stm-bugfixes.patch \
    file://0003-tf-a-karo.patch \
    file://0004-pll2-q-gpu-clk.patch \
"

# Extra make settings
EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX}'
EXTRA_OEMAKE += 'PLAT=txmp'
EXTRA_OEMAKE += 'VENDOR=karo'
EXTRA_OEMAKE += 'ARCH=aarch32'
EXTRA_OEMAKE += 'ARM_ARCH_MAJOR=7'

# Debug support
EXTRA_OEMAKE += "${@oe.utils.ifelse(d.getVar('TF_DEBUG') == '1', 'DEBUG=1','')}"
EXTRA_OEMAKE += "${@oe.utils.ifelse(d.getVar('TF_DEBUG') == '1', 'LOG_LEVEL=40','LOG_LEVEL=10')}"

# Set default TF-A config
TF_A_CONFIG ?= ""
