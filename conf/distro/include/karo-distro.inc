POKY_DISTRO ??= "poky"
require conf/distro/${POKY_DISTRO}.conf

# DISTRO_FEATURES += " \
#                 alternatives-symlinks-relative \
#                 dhcpcd \
#                 ldconfig \
#                 pam \
#                 telnet-login \
# "

# VIRTUAL-RUNTIME_base-utils-hwclock = "util-linux-hwclock"

WARN_QA_remove = "host-user-contaminated"