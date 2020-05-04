MACHINEOVERRIDES =. "stm32mp1:"

include conf/machine/include/tune-cortexa7.inc

KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

UBOOT_CONFIG ??= "mfg generic"

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
PREFERRED_VERSION_linux-karo = "5.7"
KERNEL_IMAGETYPE = "uImage"

UBOOT_ENTRYPOINT = "0xc0008000"

# Set Serial consoles
SERIAL_CONSOLES = "115200;ttySTM0 115200;ttySTM1 115200;ttySTM2"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = " \
                          kernel-modules \
"

# Blacklist evbug kernel module
KERNEL_MODULE_PROBECONF += "evbug"
module_conf_evbug = "blacklist evbug"
