require tf-a-stm32mp-common.inc
require tf-a-tools.inc

SUMMARY = "Cert_create & Fiptool for fip generation for Trusted Firmware-A"
LICENSE = "BSD-3-Clause"

# Configure settings
TFA_PLATFORM = "stm32mp1"
TFA_PLATFORM:class-native  = "stm32mp2"
TFA_PLATFORM:class-nativesdk  = "stm32mp2"
