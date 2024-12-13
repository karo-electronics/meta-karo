FILESEXTRAPATHS:prepend := "${THISDIR}/tf-a-stm32mp:"

SECTION = "bootloaders"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

def get_tfa_machine(d):
    if d.getVar('SOC_PREFIX') == "stm32mp13":
        return "stm32mp1" 
    if d.getVar('SOC_PREFIX') == "stm32mp15":
        return "stm32mp1"
    if d.getVar('SOC_PREFIX') == "stm32mp25":
        return "stm32mp2"
    return ""

TF_A_MACHINE = "${@ get_tfa_machine(d)}"

TF_A_SRC_DEFAULT = "${@ "git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;protocol=https" if get_tfa_machine(d) == 'stm32mp1' else \
                        "git://github.com/STMicroelectronics/arm-trusted-firmware.git;protocol=https"}"
TF_A_BRANCH_DEFAULT = "${@ "lts-v2.8" if get_tfa_machine(d) == 'stm32mp1' else "v2.8-stm32mp"}"
TF_A_REV_DEFAULT = "${@ "ff0bd5f9bb2ba2f31fb9cec96df917747af9e92d" if get_tfa_machine(d) == 'stm32mp1' else "c536182ad385c0a817f712d0084f02b71514a181"}"


TF_A_SRC ?= "${TF_A_SRC_DEFAULT}"
TF_A_BRANCH ?= "${TF_A_BRANCH_DEFAULT}"
TF_A_REV ?= "${TF_A_REV_DEFAULT}"

SRC_URI = "${TF_A_SRC};branch=${TF_A_BRANCH}"
SRCREV = "${TF_A_REV}"

SRC_URI[fw.sha256sum] = "c87d8a03a8feab1f8a51818a7942deade5d31abb7f4afaa6d6dfa922383e9805"

SRC_URI += "${@ "file://tf-a-st-ddr.tar.gz;subdir=git;name=fw" if "${SOC_PREFIX}" == "stm32mp25" else ""}"

TF_A_VERSION ?= "${@ 'v2.8' if get_tfa_machine(d) == 'stm32mp1' else 'v2.8.15'}"
TF_A_RELEASE = "${@ 'lts' if get_tfa_machine(d) == 'stm32mp1' else 'r2'}"

#TF_A_VERSION:stm32mp1 ?= "v2.8"
#TF_A_RELEASE:stm32mp1 = "lts"

TF_A_SUBVERSION = "stm32mp"

PV = "${TF_A_VERSION}-${TF_A_SUBVERSION}-${TF_A_RELEASE}"

S = "${WORKDIR}/git"
