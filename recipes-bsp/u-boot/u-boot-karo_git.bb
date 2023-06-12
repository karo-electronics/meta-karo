require recipes-bsp/u-boot/u-boot.inc

OVERLAY_INC_FILE = "${SOC_PREFIX}-overlays.inc"
OVERLAY_INC_FILE:rzg2 = "rzg2-overlays.inc"
require conf/machine/include/${OVERLAY_INC_FILE}

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

PROVIDES += "u-boot"

DEPENDS:append = " bc-native bison-native xxd-native python3-setuptools-native"

RDEPENDS:${PN}:append:stm32mp1 = " tf-a-stm32mp"
RDEPENDS:${PN}:append:rzg2 = " tf-a-rzg2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/env:${THISDIR}/${PN}/defconfigs:"

SRC_URI = "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH:stm32mp1 = "karo-stm32mp1-v2021.10"
SRCREV:stm32mp1 = "7c7c70f71f34d2cd4c77dd6d1e14e5022178c6c2"

SRCBRANCH:rzg2 = "karo-txrz"
SRCREV:rzg2 = "34dad2fabb84257080b4f7dde92382bd8ab9c132"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

EXTRA_OEMAKE:append = " V=0"

# append git hash to u-boot name
SCMVERSION ??= "y"
LOCALVERSION ??= "+karo"

UBOOT_LOCALVERSION = "${LOCALVERSION}"
UBOOT_INITIAL_ENV = ""

UBOOT_ENV_FILE ?= "${@ "${MACHINE}-${KARO_BASEBOARD}_env.txt" if "${KARO_BASEBOARD}" != "" else "${MACHINE}_env.txt"}"

SRC_URI:append:stm32mp1 = "${@ " file://${UBOOT_ENV_FILE};subdir=git/board/karo/txmp" if "${UBOOT_ENV_FILE}" != "" else ""}"
SRC_URI:append:rzg2 = "${@ " file://${UBOOT_ENV_FILE};subdir=git/board/karo/txrz" if "${UBOOT_ENV_FILE}" != "" else ""}"

SRC_URI:append = "${@ " \
    file://dts/${DTB_BASENAME}-${KARO_BASEBOARD}.dts;subdir=git/arch/arm \
    file://dts/${DTB_BASENAME}-${KARO_BASEBOARD}-u-boot.dtsi;subdir=git/arch/arm" \
    if "${KARO_BASEBOARD}" != "" else "" \
}"

SRC_URI:append = "${@ "".join(map(lambda f: " file://%s.cfg" % f, d.getVar('UBOOT_FEATURES').split()))}"

SRC_URI:append = " file://${MACHINE}_defconfig.template"
SRC_URI:append = "${@ "".join(map(lambda f: " file://u-boot-cfg.%s" % f, d.getVar('UBOOT_CONFIG').split()))}"

do_configure:prepend() {
    if [ -n "${UBOOT_CONFIG}" ];then
        i=0
        for config in ${UBOOT_MACHINE};do
            i=$(expr $i + 1)
            j=0
            for type in ${UBOOT_CONFIG};do
                j=$(expr $j + 1)
                if [ $i -eq $j ];then
                    c="$(echo "$config" | sed s/_config/_defconfig/)"
                    cat "${WORKDIR}/${MACHINE}_defconfig.template" > "${S}/configs/${c}"
                    if [ -s "${WORKDIR}/u-boot-cfg.${type}" ];then
                        bbnote "Appending '$type' specific config to '${S}/configs/${c}'"
                        cat "${WORKDIR}/u-boot-cfg.${type}" >> "${S}/configs/${c}"
                    fi
                fi
            done
            unset j
        done
        unset i
    else
        cat "${MACHINE}_defconfig.template" > "${S}/configs/${MACHINE}_defconfig"
    fi
}

do_configure:append() {
    for f in ${UBOOT_FEATURES};do
        if ! [ -f "${WORKDIR}/${f}.cfg" ];then
            bbfatal "UBOOT_FEATURE: '${WORKDIR}/${f}.cfg' not found"
        fi
    done
    if [ -n "${KARO_BASEBOARD}" ];then
        if [ -n "${UBOOT_CONFIG}" ];then
            for config in ${UBOOT_MACHINE};do
                c="${B}/${config}"
                cat <<EOF >> "${c}/.config"
CONFIG_DEFAULT_DEVICE_TREE="${DTB_BASENAME}-${KARO_BASEBOARD}"
CONFIG_DEFAULT_ENV_FILE="board/\$(VENDOR)/\$(BOARD)/${UBOOT_ENV_FILE}"
EOF
                oe_runmake -C ${c} oldconfig
            done
        else
            cat <<EOF >> "${B}/.config"
CONFIG_DEFAULT_DEVICE_TREE="${DTB_BASENAME}-${KARO_BASEBOARD}"
CONFIG_DEFAULT_ENV_FILE="board/\$(VENDOR)/\$(BOARD)/${UBOOT_ENV_FILE}"
EOF
            oe_runmake -C ${B} oldconfig
        fi
        grep -q "${DTB_BASENAME}-${KARO_BASEBOARD}\.dtb" ${S}/arch/arm/dts/Makefile || \
                sed -i '/^targets /i\
dtb-y += ${DTB_BASENAME}-${KARO_BASEBOARD}.dtb\
' ${S}/arch/arm/dts/Makefile
    fi
}

do_savedefconfig() {
    if [ -n "${UBOOT_CONFIG}" ];then
        for config in ${UBOOT_MACHINE}; do
            bbplain "Saving ${config} defconfig to:\n${B}/${config}/defconfig"
            oe_runmake -C ${B}/${config} savedefconfig
        done
    else
        bbplain "Saving ${config} defconfig to:\n${B}/defconfig"
        oe_runmake -C ${B} savedefconfig
    fi
}
do_savedefconfig[nostamp] = "1"
addtask savedefconfig after do_configure

do_compile:prepend() {
    if [ "${SCMVERSION}" = "y" ]; then
        # Add GIT revision to the local version
        head=`cd ${S} ; git rev-parse --verify --short HEAD 2> /dev/null`
        printf "%s%s%s" "${UBOOT_LOCALVERSION}" +g $head > ${S}/.scmversion
        printf "%s%s%s" "${UBOOT_LOCALVERSION}" +g $head > ${B}/.scmversion
    else
        printf "%s" "${UBOOT_LOCALVERSION}" > ${S}/.scmversion
        printf "%s" "${UBOOT_LOCALVERSION}" > ${B}/.scmversion
    fi
}

# -----------------------------------------------------------------------------
# Append deploy to handle specific device tree binary deployement
#
do_deploy:append:stm32mp1 () {
    if [ -n "${UBOOT_CONFIG}" ]; then
        i=0
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1)
            j=0
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1)
                if [ $j -eq $i ]; then
                    install -m 644 ${B}/${config}/u-boot-${type}.bin ${DEPLOYDIR}
                fi
            done
            unset j
        done
        unset i
    else
        bbfatal "Wrong u-boot-karo configuration: please make sure to use UBOOT_CONFIG through BOOTSCHEME_LABELS config"
    fi
}

BIN_DIR:stm32mp1 = "/binaries"
FILES:${PN}:stm32mp1 = "${BIN_DIR}"
SYSROOT_DIRS:stm32mp1 += "${BIN_DIR}"

do_install:stm32mp1 () {
    if [ -n "${UBOOT_CONFIG}" ]; then
        i=0
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1)
            j=0
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1)
                if [ $j -eq $i ]; then
                    install -d ${D}/${BIN_DIR}
                    install -m 644 ${B}/${config}/u-boot-nodtb.bin ${D}/${BIN_DIR}/u-boot-nodtb-${type}.bin
                    install -m 644 ${B}/${config}/u-boot.dtb ${D}/${BIN_DIR}/u-boot-${type}.dtb
                fi
            done
            unset j
        done
        unset i
    fi
}

# -----------------------------------------------------
# rzg2
# -----------------------------------------------------
FILES:${PN}:rzg2 = "/boot "
SYSROOT_DIRS:rzg2 += "/boot"

UBOOT_SREC_SUFFIX:rzg2 = "srec"
UBOOT_SREC:rzg2 ?= "u-boot-elf.${UBOOT_SREC_SUFFIX}"
UBOOT_SREC_IMAGE:rzg2 ?= "u-boot-elf-${MACHINE}-${PV}-${PR}.${UBOOT_SREC_SUFFIX}"
UBOOT_SREC_SYMLINK:rzg2 ?= "u-boot-elf-${MACHINE}.${UBOOT_SREC_SUFFIX}"

do_deploy:append:rzg2 () {
    if [ -n "${UBOOT_CONFIG}" ]; then
        i=0
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1)
            j=0
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1)
                if [ $j -eq $i ]; then
                    install -m 644 ${B}/${config}/${UBOOT_SREC} ${DEPLOYDIR}/u-boot-elf-${type}-${PV}-${PR}.${UBOOT_SREC_SUFFIX}

                    ln -sf u-boot-elf-${type}-${PV}-${PR}.${UBOOT_SREC_SUFFIX} ${DEPLOYDIR}/u-boot-elf-${type}.${UBOOT_SREC_SUFFIX}
                fi
            done
            unset j
        done
        unset i
    else
        install -m 644 ${B}/${UBOOT_SREC} ${DEPLOYDIR}/${UBOOT_SREC_IMAGE}
        ln -sf ${UBOOT_SREC_IMAGE} ${DEPLOYDIR}/${UBOOT_SREC_SYMLINK}
        ln -sf ${UBOOT_SREC_IMAGE} ${DEPLOYDIR}/${UBOOT_SREC}
    fi
}

# ---------------------------------------------------------------------
# Avoid QA Issue: No GNU_HASH in the elf binary
INSANE_SKIP:${PN} += "ldflags"
# ---------------------------------------------------------------------
# Avoid QA Issue: ELF binary has relocations in .text
# (uboot no need -fPIC option : remove check)
INSANE_SKIP:${PN} += "textrel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE:rzg2 = "(txrz-.*|qsrz-.*)"
COMPATIBLE_MACHINE:stm32mp1 = "(txmp-.*|qsmp-.*)"

UBOOT_NAME = "u-boot-${MACHINE}.bin"
