#@TYPE: Machine
#@NAME: Ka-Ro electronics TX6UL NAND
#@SOC: i.MX6UL
#@DESCRIPTION: Machine configuration for Ka-Ro electronics TX6UL (i.MX6UL) Computer-On-Module with NAND
#@MAINTAINER: Oliver Wendt <ow@karo-electronics.com>

require include/tx-base.inc
require conf/machine/include/tune-cortexa7.inc

TXTYPE = "imx6ul-tx6"
TXNVM = "nand"

MACHINEOVERRIDES =. "mx6ul:txul:${TXTYPE}-${TXNVM}:"
