DTB_BASENAME_mx6ul ?= "imx6ull-${MACHINE}"

require tx6-base.inc
require conf/machine/include/tune-cortexa7.inc

MACHINEOVERRIDES =. "mx6ul:txul:tx6-emmc:"

MACHINE_FEATURES += "emmc"
