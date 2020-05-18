FILESEXTRAPATHS_prepend := "${THISDIR}/tf-a-karo:"
SRC_URI_append = " \
  file://tf-a-karo-r1.1-3.patch \
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
