require tf-a-stm32mp-common_${PV}.inc
require tf-a-stm32mp-common.inc

SUMMARY = "Trusted Firmware-A for STM32MP1"
LICENSE = "BSD-3-Clause"

PROVIDES += "virtual/trusted-firmware-a"
PROVIDES += "tf-a-karo"

# Enable the wrapper for debug
TF_A_ENABLE_DEBUG_WRAPPER ?= "1"
