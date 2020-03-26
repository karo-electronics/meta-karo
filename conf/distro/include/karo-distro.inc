POKY_DISTRO ??= "poky"
require conf/distro/${POKY_DISTRO}.conf

SANITY_TESTED_DISTROS_append = " devuan-1"

VIRTUAL-RUNTIME_init_manager = "sysvinit"

# We only want to build systemd free images!
DISTRO_FEATURES_remove = "systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED_append = " systemd"
DISTRO_FEATURES_DEFAULT_remove = "wifi"

DISTRO_FEATURES += " \
                alternatives-symlinks-relative \
                dhcpcd \
                ldconfig \
                pam \
                telnet-login \
                ${@bb.utils.contains("TXBASE","evalkit", \
                   bb.utils.contains("TXNVM","emmc","wifi","",d),"",d)} \
"

ROOT_HOME ?= "/root"

VIRTUAL-RUNTIME_base-utils-hwclock = "util-linux-hwclock"

inherit rootfs-postprocess
ROOTFS_POSTPROCESS_COMMAND_remove = "rootfs_update_timestamp; "
