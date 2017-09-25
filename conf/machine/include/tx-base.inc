include conf/machine/include/soc-family.inc

# Set specific make target and binary suffix
PREFERRED_PROVIDER_u-boot ??= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-karo"

UBOOT_MAKE_TARGET ?= "u-boot.bin"
UBOOT_SUFFIX ?= "bin"

UBOOT_ENTRYPOINT_mx6  = "0x10008000"
UBOOT_ENTRYPOINT_mx6ul = "0x80008000"

DEFAULTTUNE_mx6 ?= "cortexa9hf-neon"
DEFAULTTUNE_mx6ul ?= "cortexa7hf-neon"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX_mx6q = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX_mx6dl = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX_mx6ul = "-mx6ul"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
XSERVER_DRIVER = "xf86-video-fbdev"
XSERVER_DRIVER_mx6 = "xf86-video-imxfb-vivante"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           ${XSERVER_DRIVER}"

PREFERRED_PROVIDER_virtual/egl ?= "mesa"

PREFERRED_PROVIDER_virtual/libgles1 ?= "mesa"

PREFERRED_PROVIDER_virtual/libgles2 ?= "mesa"

PREFERRED_PROVIDER_virtual/libgl ?= "mesa"

PREFERRED_PROVIDER_virtual/libg2d_mx6 ?= "mesa"
PREFERRED_PROVIDER_virtual/libg2d_mx6ul ?= ""

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
KERNEL_IMAGETYPE = "uImage"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

SERIAL_CONSOLE = "115200 ttymxc0"
SERIAL_CONSOLE_mxs = "115200 ttyAMA0"

MACHINE_FEATURES = "usbgadget usbhost vfat alsa touchscreen"

# Add the ability to specify _imx machines
MACHINEOVERRIDES =. "imx:"
