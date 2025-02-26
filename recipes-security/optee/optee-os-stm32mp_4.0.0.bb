SUMMARY = "OPTEE TA development kit for stm32mp"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

#OPTEE_SRC_DEFAULT = "git://github.com/OP-TEE/optee_os.git;protocol=https"
#OPTEE_BRANCH_DEFAULT = "master"
#OPTEE_REV_DEFAULT = "2a5b1d1232f582056184367fb58a425ac7478ec6"

OPTEE_SRC_DEFAULT = "git://github.com/STMicroelectronics/optee_os.git;protocol=https"
OPTEE_BRANCH_DEFAULT = "${OPTEE_VERSION}-${OPTEE_SUBVERSION}"
OPTEE_REV_DEFAULT = "d9c4df97fc9cf1e5319d8511adbf1eb6c95da38e"

OPTEE_SRC ??= "${OPTEE_SRC_DEFAULT}"
OPTEE_BRANCH ??= "${OPTEE_BRANCH_DEFAULT}"
OPTEE_REV ??= "${OPTEE_REV_DEFAULT}"

SRCBRANCH = "${OPTEE_BRANCH}"
SRC_URI = "${OPTEE_SRC};branch=${SRCBRANCH}"
SRCREV = "${OPTEE_REV}"

SRC_URI += " \
    file://fonts.tar.gz;subdir=git;name=fonts \
"

SRC_URI[fonts.sha256sum] = "4941e8bb6d8ac377838e27b214bf43008c496a24a8f897e0b06433988cbd53b2"

PROVIDES += "optee-os"

OPTEE_VERSION = "4.0.0"
OPTEE_SUBVERSION = "stm32mp"
OPTEE_RELEASE = "r1"

PV = "${OPTEE_VERSION}-${OPTEE_SUBVERSION}-${OPTEE_RELEASE}"

ARCHIVER_ST_REVISION = "${PV}"
ARCHIVER_COMMUNITY_BRANCH = "master"
ARCHIVER_COMMUNITY_REVISION = "${OPTEE_VERSION}"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(stm32mpcommon)"

OPTEEMACHINE:stm32mp1common = "stm32mp1"
OPTEEMACHINE:stm32mp2common = "stm32mp2"
OPTEEOUTPUTMACHINE ?= "${OPTEEMACHINE}"

# The package is empty but must be generated to avoid apt-get installation issue
ALLOW_EMPTY:${PN} = "1"

require optee-os-stm32mp-common.inc
