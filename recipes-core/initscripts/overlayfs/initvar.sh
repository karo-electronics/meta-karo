#!/bin/sh
volatile_dirs="var run"

error() {
    echo "FAILED"
    cat /tmp/initvar.log
    umount -n /tmp
}

rootfs_ro() {
    echo " $(cat /proc/cmdline) " | grep -q ' ro ' && return
    while read DEV MNT TYPE OPTS REST;do
	if [ "$MNT" = "/" -o "$DEV" = "/dev/root" ];then
	    echo ",${OPTS}," | grep -q ",ro,"
	    return
	fi
    done < /etc/fstab
}

init_overlayfs() {
    grep -q '^overlay' /etc/fstab || return 0
    volatile_dirs=run
    mount -nt tmpfs tmpfs /tmp
    while read fs mnt type opts dump pass junk;do
	case "$fs" in
	    ""|\#*)
		continue;
		;;
	    overlay)
		upperdir="${opts#*upperdir=}"
		upperdir="${upperdir%%,*}"
		workdir="${opts#*workdir=}"
		workdir="${workdir%%,*}"
		[ "$VERBOSE" != no ] && echo "Creating $upperdir $workdir for overlayfs"
		mkdir -p "$upperdir" "$workdir"
	esac
    done < /etc/fstab
}

case $1 in
    start)
	rootfs_ro || volatile_dirs=run
	init_overlayfs

	set -e
	trap error 0

	echo -n "Initializing /var... "
	for d in $volatile_dirs;do
	    mount -nt tmpfs tmpfs -o mode=755,nodev,nosuid,strictatime /tmp
	    if ( tar -C /$d -cf - .| tar -C /tmp -xf - ) 2> /tmp/init$d.log;then
		mount -n --bind /$d /etc/.$d
		mount -n --move /tmp /$d
	    fi
	    done
	trap - 0
	:> /run/.clean
	:> /run/lock/.clean
	echo "Done."
	;;
    stop)
	;;
esac
