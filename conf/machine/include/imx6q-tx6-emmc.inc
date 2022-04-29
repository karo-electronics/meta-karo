require tx6-base.inc
require conf/machine/include/tune-cortexa9.inc

MACHINEOVERRIDES =. "mx6q:tx6:"

MACHINE_FEATURES += "emmc"
