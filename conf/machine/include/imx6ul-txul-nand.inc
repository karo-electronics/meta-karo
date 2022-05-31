require tx6-base.inc
require conf/machine/include/tune-cortexa7.inc

MACHINEOVERRIDES =. "mx6ul:txul:tx6-nand:"

MACHINE_FEATURES += "nand"
