ROOTFS_POSTPROCESS_COMMAND_append = "rootfs_postinst_cleanup; "

rootfs_postinst_cleanup () {
    set -x

    if [ -d ${IMAGE_ROOTFS}$sysconfdir/default/volatiles ];then
        echo "${sysconfdir}/default/volatiles still exists" >&2
    fi
    # It's meaningless to rotate a log file, that is created once
    # upon boot in tmpfs
    rm -vf ${IMAGE_ROOTFS}${sysconfdir}/logrotate-dmesg.conf

    # copy local timezone file from host, if none is installed
    if [ ! -s ${IMAGE_ROOTFS}${sysconfdir}/localtime -a -s /etc/localtime ];then
        cp -vL /etc/localtime ${IMAGE_ROOTFS}${sysconfdir}
    fi

    # remove unused file to prevent confusion
    rm -vf ${IMAGE_ROOTFS}${sysconfdir}/timezone

    rmdir -v ${IMAGE_ROOTFS}/usr/share/man

    date -u > ${IMAGE_ROOTFS}/.timestamp
}
