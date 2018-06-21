FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

SRC_URI_remove = " \
           file://login-utilities.cfg \
           ${@ bb.utils.contains('VIRTUAL-RUNTIME_dev_manager','busybox-mdev','','file://mdev.cfg',d)} \
           file://sha1sum.cfg \
"
SRC_URI_append = " \
           file://defconfig \
           file://mdev \
           file://rtcsymlink.sh \
           ${@ bb.utils.contains('DISTRO_FEATURES','pam','file://pam.cfg','',d)} \
"
FILES_${PN} += "${sysconfdir}/network/run"

DEPENDS += "${@ bb.utils.contains('DISTRO_FEATURES','pam','libpam','',d)}"

# overrule prio 200 of sysvinit and shadow
ALTERNATIVE_PRIORITY = "201"
#BUSYBOX_SPLIT_SUID = "0"
RDEPENDS_busybox-mdev += "bash"

inherit useradd

USERADD_PACKAGES += "${PN}"
USERADD_PARAM_${PN} = ""
GROUPADD_PARAM_${PN} = "--system utmp"

# need /etc/group in the staging directory
DEPENDS_${PN} = " base-passwd"

FILES_${PN} += "/run/utmp /var/log/utmp"

inherit relative_symlinks

do_install_append () {
    if ${@ bb.utils.contains('VIRTUAL-RUNTIME_dev_manager','busybox-mdev','true','false',d)};then
        echo "Using busybox-mdev as device manager"
    elif ${@ bb.utils.contains('VIRTUAL-RUNTIME_dev_manager','udev','true','false',d)};then
        echo "Using ${@ d.getVar('VIRTUAL-RUNTIME_dev_manager')} as device manager"
    else
        echo "ERROR: VIRTUAL-RUNTIME_dev_manager is not set!" >&2
        exit 1
    fi
    if grep "CONFIG_FEATURE_MDEV_CONF=y" ${B}/.config; then
        install -v -m 0755 ${WORKDIR}/rtcsymlink.sh ${D}${sysconfdir}/mdev
    fi

    install -d -m 0755 ${D}${sysconfdir}/network
    ln -snvf /run/network ${D}${sysconfdir}/network/run

    install -v -d -m 0755 ${D}/run
    install -v -m 0664 -g utmp /dev/null ${D}/run/utmp

    install -v -d -m 0755 ${D}/var/log
    install -v -m 0664 -g utmp /dev/null ${D}/var/log/wtmp
}
