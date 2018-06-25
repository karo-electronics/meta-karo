TCMODE = "${@ 'default' if d.getVar('UBOOT_MACHINE') == None else 'uboot'}"

KBUILD_BUILD_USER := "support"
KBUILD_BUILD_HOST := "karo-electronics.de"

# Set specific make target and binary suffix
PREFERRED_PROVIDER_u-boot ??= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-karo"

UBOOT_MAKE_TARGET ??= "u-boot.bin"
UBOOT_SUFFIX ??= "bin"

UBOOT_ENTRYPOINT_mx6  = "0x10008000"
UBOOT_ENTRYPOINT_mx6ul = "0x80008000"

# 'cortexa9-neon' 'cortexa9hf-neon'
DEFAULTTUNE_mx6 ?= "cortexa9hf-neon"
DEFAULTTUNE_mx6ul ?= "cortexa7hf-neon"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
XSERVER_DRIVER = "xf86-video-fbdev"
XSERVER_DRIVER_mx6 = "xf86-video-modesetting"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           ${XSERVER_DRIVER}"

PACKAGE_EXCLUDE_COMPLEMENTARY = ".*"

PREFERRED_PROVIDER_virtual/egl ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles1 ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles2 ?= "mesa"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/libg2d_mx6 ?= "mesa"
PREFERRED_PROVIDER_virtual/libg2d_mx6ul ?= ""

PREFERRED_PROVIDER_virtual/kernel ??= "linux-karo"
PREFERRED_VERSION_linux-karo ?= "4.14.24"
KERNEL_IMAGETYPE = "uImage"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
                 linux-firmware-imx-sdma-imx6q \
"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS_append_karo-x11 = " \
                 linux-firmware-imx-vpu \
"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = " \
                          kernel-modules \
"

SERIAL_CONSOLES = " \
                115200;ttymxc0 \
                115200;ttymxc1 \
                115200;ttymxc2 \
"
SERIAL_CONSOLES_mxs = " \
                115200;ttyAMA0 \
                115200;ttyAPP1 \
                115200;ttyAPP3 \
"

MACHINE_FEATURES = " \
                 alsa \
                 rtc \
                 serial \
                 touchscreen \
                 usbgadget \
                 usbhost \
                 vfat \
"

# Add the ability to specify _imx machines
MACHINEOVERRIDES =. "imx:"

# Default baseboard name
TXBASE_DEFAULT_mx6 ?= "mb7"
TXBASE_DEFAULT_mx6ul ?= "evalkit"

TXBASE ?= "${TXBASE_DEFAULT}"
