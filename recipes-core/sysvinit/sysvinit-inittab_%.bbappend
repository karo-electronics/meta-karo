FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_remove = "\
	file://start_getty \
"

do_install () {
    set -x
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/inittab ${D}${sysconfdir}/inittab

    i=0
    cons="${SERIAL_CONSOLES}"
    for c in ${cons};do
	if [ $i = 0 ];then
	    cat <<EOF >> ${D}${sysconfdir}/inittab
#
# Serial consoles on the standard serial ports
EOF
	fi
	j=`echo ${c} | sed 's/;/ /g'`
	l=`echo ${c} | sed 's/tty//;s/^.*;//;s/;.*//'`
	echo "s$i:123:respawn:${base_sbindir}/getty -L ${j} linux" >> ${D}${sysconfdir}/inittab
	i=`expr $i + 1`
    done

    if [ "${USE_VT}" = "1" ]; then
        cat <<EOF >> ${D}${sysconfdir}/inittab
#
# ${base_sbindir}/getty invocations for the runlevels.
#
# The "id" field MUST be the same as the last
# characters of the device (after "tty").
#
# Format:
#  <id>:<runlevels>:<action>:<process>
EOF
        for n in ${SYSVINIT_ENABLED_GETTYS};do
            echo "$n:345:respawn:${base_sbindir}/getty 38400 tty$n" >> ${D}${sysconfdir}/inittab
        done
    fi

    if ${@ bb.utils.contains('DISTRO_FEATURES','telnet-login','true','false',d)};then
        cat << EOF >> ${D}${sysconfdir}/inittab

# Telnet daemon
T0:2345:respawn:/usr/sbin/telnetd -F
EOF
    fi
}
