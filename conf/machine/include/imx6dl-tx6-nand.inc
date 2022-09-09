require tx6-base.inc
require conf/machine/include/arm/armv7a/tune-cortexa9.inc

MACHINEOVERRIDES =. "mx6dl:tx6:tx6-nand:"

MACHINE_FEATURES += "nand"
