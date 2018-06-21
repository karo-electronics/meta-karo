POKY_DISTRO ??= "poky"
require conf/distro/${POKY_DISTRO}.conf

SANITY_TESTED_DISTROS_append = " devuan-1"

VIRTUAL-RUNTIME_init_manager = "sysvinit"

# We only want to build systemd free images!
DISTRO_FEATURES_remove = "systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED_append = " systemd"

DISTRO_FEATURES += " \
                alternatives-symlinks-relative \
                dhcpcd \
                ldconfig \
                pam \
                telnet-login \
"

