FILESEXTRAPATHS:prepend := "${THISDIR}/tf-a-stm32mp:"

SECTION = "bootloaders"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

def get_tfa_machine(d):
    if d.getVar('SOC_PREFIX') == "stm32mp13":
        return "stm32mp1" 
    if d.getVar('SOC_PREFIX') == "stm32mp15":
        return "stm32mp1"
    if d.getVar('SOC_PREFIX') == "stm32mp23":
        return "stm32mp2"
    if d.getVar('SOC_PREFIX') == "stm32mp25":
        return "stm32mp2"
    return ""

TF_A_MACHINE = "${@ get_tfa_machine(d)}"

#TF_A_SRC_DEFAULT = "git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;protocol=https"
#TF_A_REV_DEFAULT = "7c229848a09ebc3aba8e3941ae1cdfceb222a768"

TF_A_SRC_DEFAULT = "git://github.com/STMicroelectronics/arm-trusted-firmware.git;protocol=https"
TF_A_BRANCH_DEFAULT = "${TF_A_VERSION}-${TF_A_RELEASE}"
TF_A_REV_DEFAULT = "75f8c318eec5e0a710e2edbef550a0b10c8942a9"


TF_A_SRC ?= "${TF_A_SRC_DEFAULT}"
TF_A_BRANCH ?= "${TF_A_BRANCH_DEFAULT}"
TF_A_REV ?= "${TF_A_REV_DEFAULT}"

SRC_URI = "${TF_A_SRC};branch=${TF_A_BRANCH}"
SRCREV = "${TF_A_REV}"

#SRC_URI[fw.sha256sum] = "c87d8a03a8feab1f8a51818a7942deade5d31abb7f4afaa6d6dfa922383e9805"

SRC_URI:append = "${@ " file://tf-a-st-ddr.tar.gz;subdir=git;name=fw" if "${SOC_PREFIX}" == "stm32mp23" else ""}"
SRC_URI:append = "${@ " file://tf-a-st-ddr.tar.gz;subdir=git;name=fw" if "${SOC_PREFIX}" == "stm32mp25" else ""}"
SRC_URI:append:not = " \
    file://0001-v2.10-stm32mp-r1.patch \
"

TF_A_VERSION ?= "v2.10"
TF_A_RELEASE = "stm32mp"

TF_A_SUBVERSION = ".5"

PV = "${TF_A_VERSION}${TF_A_SUBVERSION}-${TF_A_RELEASE}"

S = "${WORKDIR}/git"
