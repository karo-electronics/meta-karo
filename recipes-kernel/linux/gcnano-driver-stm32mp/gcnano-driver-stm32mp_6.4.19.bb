SUMMARY = "GCNano kernel drivers"
LICENSE = "GPL-1.0-only & MIT"
# Note get md5sum with: $ head -n 19 Makefile | md5sum
LIC_FILES_CHKSUM = "file://Makefile;endline=19;md5=9b60522ab24b25fa2833058e76c41216"

SRC_URI = "git://github.com/STMicroelectronics/gcnano-binaries.git;protocol=https;branch=gcnano-${GCNANO_VERSION}-binaries"
SRCREV = "359d5007ef19575f6b8ca3071cf90d3848778ae7"

GCNANO_VERSION = "6.4.19"
GCNANO_SUBVERSION:stm32mp1common = "stm32mp1"
GCNANO_SUBVERSION:stm32mp2common = "stm32mp2"
GCNANO_RELEASE = "r1"

PV = "${GCNANO_VERSION}-${GCNANO_SUBVERSION}-${GCNANO_RELEASE}"

S = "${WORKDIR}/git/${BPN}"

include gcnano-driver-stm32mp.inc

# ---------------------------------
# Configure archiver use
# ---------------------------------
include ${@oe.utils.ifelse(d.getVar('ST_ARCHIVER_ENABLE') == '1', 'gcnano-driver-stm32mp-archiver.inc','')}
