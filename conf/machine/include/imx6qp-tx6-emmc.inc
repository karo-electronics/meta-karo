DTB_BASENAME_mx6q ?= "imx6qp-${MACHINE}"

require tx6-base.inc
require conf/machine/include/tune-cortexa9.inc

MACHINEOVERRIDES =. "mx6q:tx6:tx6-emmc:"

MACHINE_FEATURES += "emmc"
