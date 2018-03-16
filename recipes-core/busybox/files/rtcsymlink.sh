#!/bin/sh

if [ "$ACTION" = "remove" ];then
    if [ "$(readlink /dev/rtc)" = "${MDEV}" ];then
	rm -f /dev/rtc
	shopt -s nullglob
	for rtc in /sys/class/rtc/rtc*;do
	    ln -s $(basename "$rtc") /dev/rtc
	    break
	done
    fi
else
    if ! grep -q 'i2c:' "/sys/class/rtc/${MDEV}/device/modalias" || [ ! -e /dev/rtc ];then
	rm -f /dev/rtc
	ln -s "${MDEV}" /dev/rtc
    fi
fi
