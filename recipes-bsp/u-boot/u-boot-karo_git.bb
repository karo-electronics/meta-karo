require recipes-bsp/u-boot/u-boot.inc

OVERLAY_INC_FILE = "${SOC_PREFIX}-overlays.inc"
OVERLAY_INC_FILE:rzg2 = "rzg2-overlays.inc"
require conf/machine/include/${OVERLAY_INC_FILE}

DESCRIPTION = "U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
LIC_FILES_CHKSUM:stm32mp1 = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

PROVIDES += "u-boot"

DEPENDS:append = " bc-native bison-native xxd-native python3-setuptools-native"

RDEPENDS:${PN}:append:stm32mp1 = " tf-a-stm32mp"
RDEPENDS:${PN}:append:rzg2 = " tf-a-rzg2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/env:${THISDIR}/${PN}/defconfigs:"

SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH}"
SRCBRANCH = "${@ d.getVar('UBOOT_BRANCH') if d.getVar('UBOOT_BRANCH') else \
            d.getVar('UBOOT_BRANCH_DEFAULT')}"
SRCREV = "${@ d.getVar('UBOOT_REV') if d.getVar('UBOOT_REV') else \
            d.getVar('UBOOT_REF_DEFAULT')}"

UBOOT_SRC ?= "git://github.com/karo-electronics/karo-tx-uboot.git;protocol=https;branch=${SRCBRANCH}"

UBOOT_REF_DEFAULT:stm32mp1 = "4227e640b29a74257a91aab502cab1e4ecf1092c"
UBOOT_BRANCH_DEFAULT:stm32mp1 = "karo-stm32mp1-v2022.10"

UBOOT_BRANCH_DEFAULT:rzg2 = "karo-txrz"
UBOOT_REF_DEFAULT:rzg2 = "66fed3c1ba38bf7229e10bfd9923b89bd7e98064"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

EXTRA_OEMAKE:append = " V=0"

# append git hash to u-boot name
SCMVERSION ??= "y"
LOCALVERSION ??= "+karo"

UBOOT_BOARD_DIR:stm32mp1 = "board/karo/stm32mp1"
UBOOT_BOARD_DIR:rzg2 = "board/karo/txrz"

UBOOT_LOCALVERSION = "${LOCALVERSION}"
UBOOT_INITIAL_ENV = ""
UBOOT_DEVICE_TREE ?= "${@ "${DTB_BASENAME}-${KARO_BASEBOARD}" if "${KARO_BASEBOARD}" != "" else "${DTB_BASENAME}"}"

UBOOT_ENV_FILE ?= "${@ "${MACHINE}-${KARO_BASEBOARD}_env.txt" if "${KARO_BASEBOARD}" != "" else "${MACHINE}_env.txt"}"

SRC_URI:append = "${@ " file://${UBOOT_ENV_FILE};subdir=git/${UBOOT_BOARD_DIR}" if "${UBOOT_ENV_FILE}" != "" else ""}"

SRC_URI:append = " \
    file://dts/${UBOOT_DEVICE_TREE}.dts;subdir=git/arch/arm \
    file://dts/${UBOOT_DEVICE_TREE}-u-boot.dtsi;subdir=git/arch/arm \
"

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
                [ $j -lt $i ] && continue
                c="$(echo "$config" | sed s/_config/_defconfig/)"
                cat "${WORKDIR}/${MACHINE}_defconfig.template" > "${S}/configs/${c}"
                if [ -s "${WORKDIR}/u-boot-cfg.${type}" ];then
                    bbnote "Appending '$type' specific config to '${S}/configs/${c}'"
                    cat "${WORKDIR}/u-boot-cfg.${type}" >> "${S}/configs/${c}"
                fi
                break
            done
            unset j
        done
        unset i
    else
        cat "${MACHINE}_defconfig.template" > "${S}/configs/${MACHINE}_defconfig"
    fi
}

do_configure:append() {
    tmpfile="`mktemp cfg-XXXXXX.tmp`"
    if [ -z "$tmpfile" ];then
        bbfatal "Failed to create tmpfile"
    fi
    bbnote "UBOOT_DEVICE_TREE='${UBOOT_DEVICE_TREE}'"
    cat <<EOF >> "$tmpfile"
CONFIG_DEFAULT_DEVICE_TREE="${UBOOT_DEVICE_TREE}"
EOF
    grep -q "${UBOOT_DEVICE_TREE}\.dtb" ${S}/arch/arm/dts/Makefile || \
            sed -i '/^targets /i\
dtb-y += ${UBOOT_DEVICE_TREE}.dtb\
' ${S}/arch/arm/dts/Makefile

    bbnote "UBOOT_ENV_FILE='${UBOOT_ENV_FILE}'"
    cat <<EOF >> "$tmpfile"
CONFIG_DEFAULT_ENV_FILE="board/\$(VENDOR)/\$(BOARD)/${UBOOT_ENV_FILE}"
EOF
    if [ -n "${UBOOT_CONFIG}" ];then
        for config in ${UBOOT_MACHINE};do
            c="$(echo "$config" | sed s/_config/_defconfig/)"
            merge_config.sh -m -r -O "${c}" "${c}/.config" "$tmpfile"
            oe_runmake -C ${c} olddefconfig
            if grep '^CONFIG_.*=$' ${c}/.config;then
                bbfatal "defconfig is incomplete"
            fi
        done
    else
        merge_config.sh -m -r -O "${B}" "${B}/.config" "$tmpfile"
        oe_runmake -C "${B}" olddefconfig
        if grep '^CONFIG_.*=$' ${B}/.config;then
            bbfatal "defconfig is incomplete"
        fi
    fi
    rm -vf "$tmpfile"
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
        for cfg in ${TF_A_CONFIG}; do
            i=$(expr $i + 1)
            install -d ${DEPLOYDIR}/${FIPTOOL_DIR}/${cfg}
            j=0
            for config in ${UBOOT_MACHINE}; do
                j=$(expr $j + 1)
                [ $j -lt $i ] && continue
                k=0
                for type in ${UBOOT_CONFIG}; do
                    k=$(expr $k + 1)
                    [ $k -lt $j ] && continue
                    install -m 644 ${B}/${config}/u-boot-${type}.bin ${DEPLOYDIR}
                    install -m 644 ${B}/${config}/u-boot-${type}.bin ${DEPLOYDIR}/${FIPTOOL_DIR}/${cfg}/u-boot.bin
                    install -m 644 ${B}/${config}/u-boot-nodtb.bin ${DEPLOYDIR}/${FIPTOOL_DIR}/${cfg}/u-boot-nodtb.bin
                    install -m 644 ${B}/${config}/u-boot.dtb ${DEPLOYDIR}/${FIPTOOL_DIR}/u-boot.dtb
                    break
                done
                break
            done
            unset j
        done
        unset i
    else
        bbfatal "Wrong u-boot-karo configuration: please make sure to use UBOOT_CONFIG through BOOTSCHEME_LABELS config"
    fi
}

# -----------------------------------------------------
# rzg2
# -----------------------------------------------------
FILES:${PN}:rzg2 = "/boot "
SYSROOT_DIRS:rzg2 += "/boot"

python do_env_overlays () {
    import os
    import shutil

    if d.getVar('KARO_BASEBOARDS') == None:
        bb.warn("KARO_BASEBOARDS is undefined")
        return 1

    overlays = []
    for baseboard in d.getVar('KARO_BASEBOARDS').split():
        ovlist = d.getVarFlag('KARO_DTB_OVERLAYS', baseboard, True)
        if ovlist == None:
            bb.note("No overlays defined for '%s' on baseboard '%s'" % (d.getVar('MACHINE'), baseboard))
            continue
        ov = " ".join(map(lambda f: f, ovlist.split()))
        overlays += ["overlays_%s=%s" % (baseboard, ov)]

    src_file = "%s/%s/%s" % (d.getVar('S'), d.getVar('UBOOT_BOARD_DIR'), d.getVar('UBOOT_ENV_FILE'))
    if d.getVar('UBOOT_CONFIG') != None:
        configs = d.getVar('UBOOT_MACHINE').split()
    else:
        configs = (d.getVar('MACHINE'))

    for config in configs:
        dst_dir = "%s/%s/%s" % (d.getVar('B'), config, d.getVar('UBOOT_BOARD_DIR'))
        bb.utils.mkdirhier(dst_dir)
        env_file = os.path.join(dst_dir, os.path.basename(src_file))
        shutil.copyfile(src_file, env_file)
        f = open(env_file, 'a')
        for ov in overlays:
            bb.note("Adding '%s' to '%s'" % (ov, env_file))
            f.write("%s\n" % ov)
        f.write("soc_prefix=%s\n" % (d.getVar('SOC_PREFIX') or ""))
        f.write("soc_family=%s\n" % (d.getVar('SOC_FAMILY') or ""))
        f.close()
}
addtask do_env_overlays before do_compile after do_configure
do_env_overlays[vardeps] += "KARO_BASEBOARDS KARO_DTB_OVERLAYS"

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
