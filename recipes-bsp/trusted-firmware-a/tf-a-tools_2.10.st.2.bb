require tf-a-stm32mp-common.inc
require tf-a-tools.inc

SUMMARY = "Cert_create & Fiptool for fip generation for Trusted Firmware-A"
LICENSE = "BSD-3-Clause"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/patches:"

SRC_URI:append = " \
    file://0001-FIX-GCC-tools-overwrite.patch \
    file://0002-tools-allow-to-use-a-root-key-password-from-command-.patch \
"

# Configure settings
TFA_PLATFORM = "${TF_A_TOOLS_PLATFORM}"
