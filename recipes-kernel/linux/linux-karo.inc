# Copyright (C) 2012, 2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "GPL-2.0-only"

inherit kernel
LINUX_VERSION_EXTENSION ?= "-karo"

PROVIDES += "linux"
DEPENDS += "lzop-native bc-native dtc-native"

S = "${WORKDIR}/git"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
KERNEL_EXTRA_ARGS:stm32mp1 += "LOADADDR=${UBOOT_ENTRYPOINT}"

EXTRA_OEMAKE:append = " V=0"

OVERLAY_INC_FILE = "${SOC_PREFIX}-overlays.inc"
require conf/machine/include/${OVERLAY_INC_FILE}

KARO_KERNEL_SRC ?= "${KERNEL_SRC_DEFAULT}"
KARO_KERNEL_BRANCH ?= "${KERNEL_BRANCH_DEFAULT}"
KARO_KERNEL_REV ?= "${KERNEL_REV_DEFAULT}"

KERNEL_SRC = "${KARO_KERNEL_SRC}"
KERNEL_BRANCH = "${KARO_KERNEL_BRANCH}"
KERNEL_REV = "${KARO_KERNEL_REV}"

SRCBRANCH = "${KERNEL_BRANCH}"
SRCREV = "${KERNEL_REV}"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"

# automatically add all .dts files referenced by ${KERNEL_DEVICETREE} to SRC_URI
SRC_URI:append = "${@"".join(map(lambda f: " file://dts/%s;subdir=git/${KERNEL_OUTPUT_DIR}" % f.replace(".dtb", ".dts"), "${KERNEL_DEVICETREE}".split()))}"

SRC_URI:append = "${@ "".join(map(lambda f: " file://dts%s/includes/%s-%s.dtsi;subdir=git/${KERNEL_OUTPUT_DIR}" % \
                  (("/" + d.getVar('dtb_vendordir') if d.getVar('dtb_vendordir') != "" else ""), d.getVar('SOC_PREFIX'), f), \
		  d.getVar('DTB_OVERLAY_INCLUDES').split()))}"

KERNEL_DTC_FLAGS += "-@"

def get_overlays(d):
    ovlist = []
    vendor = d.getVar('dtb_vendordir')
    ext = d.getVar('dtb_ext')
    g = d.getVar('DTB_OVERLAYS_generic') or ""
    o = d.getVar('DTB_OVERLAYS') or ""
    for ov in g.split():
        for fn in ov.split(","):
            fn = " %s%s-%s.%s" % (vendor + "/" if vendor != "" else "", d.getVar('SOC_PREFIX'), fn, ext)
            if fn in ovlist:
                bb.debug(2, "'%s' is already in overlays" % fn)
            else:
                bb.debug(2, "Adding '%s' to overlays" % fn)
                ovlist += [fn]

    for ov in o.split():
        for fn in ov.split(","):
            fn = " %s%s-%s.%s" % (vendor + "/" if vendor != "" else "", d.getVar('SOC_FAMILY'), fn, ext)
            if fn in ovlist:
                bb.debug(2, "'%s' is already in overlays" % fn)
            else:
                bb.debug(2, "Adding '%s' to overlays" % fn)
                ovlist += [fn]

    if len(ovlist) == 0:
        return ""
    bb.debug(2, "ovlist='%s'" % ovlist)
    return " ".join(ovlist)

KERNEL_DEVICETREE:append = "${@ get_overlays(d) }"

do_check_config() {
    ifs="$IFS"
    IFS="
"
    applied=$(fgrep -f "${WORKDIR}/${KBUILD_DEFCONFIG}" "${B}/.config" | sort) || \
        bbfatal "Failed to read ${B}/.config or ${WORKDIR}/${KBUILD_DEFCONFIG}"
    configured=$(sort "${WORKDIR}/${KBUILD_DEFCONFIG}") || \
        bbfatal "Failed to read ${WORKDIR}/${KBUILD_DEFCONFIG}"
    if [ "$applied" != "$configured" ];then
        bad=$(fgrep -f "${WORKDIR}/${KBUILD_DEFCONFIG}" ${B}/.config | \
                fgrep -vf - "${WORKDIR}/${KBUILD_DEFCONFIG}" | sed 's/^# //;s/[= ].*$//')
        for pat in $bad;do
            override=false
            for f in ${KERNEL_FEATURES};do
                if fgrep -qw "$pat" "${WORKDIR}/cfg/${f}";then
                    bbdebug 1 "'$(fgrep -w "$pat" "${WORKDIR}/${KBUILD_DEFCONFIG}")' from '${KBUILD_DEFCONFIG}' has been overridden by '${f}'"
                    override=true
                    break
                fi
            done
            if ! $override;then
                bbwarn "'$(fgrep -w "$pat" "${WORKDIR}/${KBUILD_DEFCONFIG}")' from '${KBUILD_DEFCONFIG}' has not been accepted by Kconfig"
            fi
        done
    fi
    rc=0
    obsolete="$(fgrep -f "${WORKDIR}/${KBUILD_DEFCONFIG}" "${B}/defconfig" | \
            fgrep -vf - "${WORKDIR}/${KBUILD_DEFCONFIG}")" || rc=$?
    if [ $rc -gt 1 ];then
        bbfatal "Failed to read defconfig or ${KBUILD_DEFCONFIG}"
    fi
    for pat in $obsolete;do
        if fgrep -qw "$pat" "${B}/.config";then
            bbwarn "'$(fgrep -w "$pat" "${WORKDIR}/${KBUILD_DEFCONFIG}")' is obsolete in '${KBUILD_DEFCONFIG}'"
        fi
    done

    for f in ${KERNEL_FEATURES};do
        bbdebug 1 "Checking '$f'"
        applied=$(fgrep -f "${WORKDIR}/cfg/${f}" ${B}/.config | sort)
        configured=$(sort "${WORKDIR}/cfg/${f}")
        if [ "$applied" != "$configured" ];then
            bad=$(fgrep -f "${WORKDIR}/cfg/${f}" ${B}/.config | \
                    fgrep -vf - "${WORKDIR}/cfg/${f}" | sed 's/^# //;s/[= ].*$//')
            for pat in $bad;do
                bbwarn "'$(fgrep -w "$pat" "${WORKDIR}/cfg/${f}")' from '${f}' has not been accepted by Kconfig"
            done
        fi
        obsolete="$(fgrep -f "${WORKDIR}/cfg/${f}" "${B}/defconfig" | \
                fgrep -vf - "${WORKDIR}/cfg/${f}" | \
                sed 's/^# //;s/[= ].*$//')"
        for pat in $obsolete;do
            cfg=$(fgrep -qw "$pat" "${B}/.config" | sort)
            def=$(fgrep -qw "$pat" "${WORKDIR}/cfg/${f}" | sort)
            if fgrep -qw "$pat" "${B}/.config";then
                if fgrep -qw "$pat" "${WORKDIR}/defconfig";then
                    bbdebug 1 "$(fgrep -w "$pat" "${WORKDIR}/cfg/${f}")' from '${f}' overrides '$(fgrep -w "$pat" "${WORKDIR}/defconfig")' from '${KBUILD_DEFCONFIG}'"
                else
                    bbwarn "$(fgrep -w "$pat" "${WORKDIR}/cfg/${f}")' is obsolete in '${f}'"
                fi
            fi
        done
    done
    rc=0
    missing_vals="$(grep '^CONFIG_.*=$' ${B}/.config)" || rc=$?
    if [ $rc -gt 1 ];then
        bbfatal "Failed to read ${B}/.config"
    fi
    if [ -n "$missing_vals" ];then
        bberror "The following config variables have missing values:"
        for var in $missing_vals;do
            bberror "$var"
        done
        bbfatal ".config is incomplete"
    fi
    IFS="$ifs"
}
addtask do_check_config after do_savedefconfig
do_check_config[nostamp] = "1"

do_compile_dtbs() {
    bbnote "Compiling ${KERNEL_DEVICETREE}"
    oe_runmake -C ${B} DTC_FLAGS="${KERNEL_DTC_FLAGS}" ${KERNEL_DEVICETREE}
}
addtask do_compile_dtbs after do_configure before do_check_dtbs

do_configure:prepend() {
    # Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    if ! [ -s "${S}/.scmversion" ] || ! grep -q "$head" ${S}/.scmversion;then
        echo "+g$head" > "${S}/.scmversion"
    fi
    install -v "${WORKDIR}/${KBUILD_DEFCONFIG}" "${B}/.config"

    sed -i '/CONFIG_LOCALVERSION/d' "${B}/.config"
    echo 'CONFIG_LOCALVERSION="${KERNEL_LOCALVERSION}"' >> "${B}/.config"

    for f in ${KERNEL_FEATURES};do
        cat ${WORKDIR}/cfg/${f} >> ${B}/.config
    done
}
addtask do_configure before do_devshell

dtb_ext = "dtb"
dtb_ext:rzg2 = "dtbo"

dtb_vendordir = ""
dtb_vendordir:rzg2 = "renesas"
dtb_vendordir:stm32mpcommon = "st"

python do_check_dtbs () {
    import os
    pfx = d.getVar('SOC_PREFIX')
    fam = d.getVar('SOC_FAMILY')
    dtb_ext = d.getVar('dtb_ext')
    dtb_vendordir = d.getVar('dtb_vendordir')

    def get_ovname(name):
        fn = []
        for n in name.split(","):
            if os.path.exists("%s-%s.%s" % (fam, n, dtb_ext)):
                fn.append("%s-%s.%s" % (fam, n, dtb_ext))
            elif os.path.exists("%s-%s.%s" % (pfx, n, dtb_ext)):
                fn.append("%s-%s.%s" % (pfx, n, dtb_ext))
            else:
                bb.fatal("Overlay file '[%s,%s]-%s.%s' not found" % (fam, pfx, n, dtb_ext))
        return fn

    def apply_overlays(infile, outfile, overlays):
        ovlist = []
        for f in overlays.split():
            ovlist += get_ovname(f)
        if len(ovlist) == 0:
            bb.fatal("No files found for overlays %s" % overlays)
            return
        bb.debug(2, "ovlist=%s" % " ".join(ovlist))
        ovfiles = " ".join(map(lambda f: "'%s'" % f, ovlist))
        bb.debug(2, "ovfiles=%s" % ovfiles)
        cmd = ("fdtoverlay -i '%s.dtb' -o '%s.dtb' %s" % (infile, outfile, ovfiles))
        bb.debug(2, "%s" % cmd)
        if os.system("%s" % cmd):
            bb.fatal("Failed to apply overlays %s for baseboard '%s' to '%s.dtb'" %
                     (",".join(ovfiles.split()), baseboard, infile))
            return
        bb.note("FDT overlays %s for '%s' successfully applied to '%s.dtb'" %
            (ovfiles, baseboard, infile))

    here = os.getcwd()
    baseboards = d.getVar('KARO_BASEBOARDS')
    if baseboards == None:
        bb.warn("KARO_BASEBOARDS is not set; cannot process FDT overlays")
        return
    basename = d.getVar('DTB_BASENAME')
    if basename == None:
        bb.warn("DTB_BASENAME is not set; cannot process FDT overlays")
        return
    if dtb_vendordir == "":
        os.chdir(os.path.join(d.getVar('B'), d.getVar('KERNEL_OUTPUT_DIR'), "dts"))
    else:
        os.chdir(os.path.join(d.getVar('B'), d.getVar('KERNEL_OUTPUT_DIR'), "dts", dtb_vendordir))
    for baseboard in baseboards.split():
        bb.debug(2, "creating %s-%s.dtb from %s.dtb" % (basename, baseboard, basename))
        outfile = "%s-%s" % (basename, baseboard)
        karo_dtb_overlays = d.getVarFlag('KARO_DTB_OVERLAYS', baseboard, True)
        if karo_dtb_overlays == None:
            continue
        overlays = " ".join(map(lambda f: f, karo_dtb_overlays.split()))
        bb.note("overlays_%s=%s" % (baseboard, overlays))
        if overlays == None or len(overlays.split()) == 0:
            bb.fatal("%s: No overlays specified for %s" % (d.getVar('MACHINE'), baseboard))
        bb.debug(2, "overlays for %s-%s='%s'" %
                 (basename, baseboard, "','".join(overlays.split())))
        apply_overlays(basename, outfile, overlays)
    os.chdir(here)
}
do_check_dtbs[vardeps] += "DTB_OVERLAYS KARO_DTB_OVERLAYS"
addtask do_check_dtbs after do_compile_dtbs before do_compile

python do_check_overlays() {
    import itertools
    import os
    import re
    import subprocess

    kb = d.getVar('KARO_BASEBOARDS').split()
    kbb = d.getVarFlags('KARO_DTB_OVERLAYS')
    bb.debug(1, "kbb='%s'" % kbb)
    all = []
    for b in kbb:
        if b not in kb:
            bb.note("baseboard '%s' is not listed in KARO_BASEBOARDS: [%s]" % (b, " ".join(kb)))
        ovl = d.getVarFlag('KARO_DTB_OVERLAYS', b, True)
        if ovl is None:
            continue

        for ov in re.split(r'[ ,]+', ovl.strip()):
                bb.debug(1, "ov='%s'" % ov)
                if ov not in all:
                    all += [ov]
                    bb.note("all=[%s]" % " ".join(all))

    ovlist = d.getVar('DTB_OVERLAYS').split()
    ovlist += (d.getVar('DTB_OVERLAYS_generic') or "").split()
    for baseboard in kb:
        bb.debug(2, "Checking baseboard '%s'" % baseboard);
        ovb = d.getVarFlag('KARO_DTB_OVERLAYS', baseboard, True)
        if ovb is None:
            bb.warn("No overlays defined for '%s' on baseboard '%s'" % (d.getVar('MACHINE'), baseboard))
            continue
        bb.note("KARO_DTB_OVERLAYS[%s]=%s" % (baseboard, ",".join(ovb.split())))
        for ov in re.split(r'[ ,]+', ovb.strip()):
            bb.debug(2, "ov='%s'" % ov)
            if ov not in ovlist:
                bb.warn("'%s' used for '%s' is not listed in DTB_OVERLAYS" % (ov, baseboard))
            if ov not in all:
                bb.warn("Overlay %s is not used by any baseboard" % ov)
                all += [ov]

    dtbdir = os.path.join(d.getVar('B'), d.getVar('KERNEL_OUTPUT_DIR'), "dts")

    def ov_file(ov):
        return os.path.join(dtbdir, ov)

    def get_syms(file, node):
        cmd = ['fdtget', '-p', '-d', "", file, node]
        syms = subprocess.check_output(cmd, text=True)
        if syms != None:
            return syms.split()
        return ()

    syms = dict()
    refs = dict()
    for f in d.getVar('KERNEL_DEVICETREE').split():
        syms[f] = get_syms(ov_file(f), "/__symbols__")
        refs[f] = get_syms(ov_file(f), "/__fixups__")

    ok = True
    for f in d.getVar('KERNEL_DEVICETREE').split():
        bb.note("Checking DTB '%s'" % f)
        for s in refs[f]:
            bb.debug(5, "Looking up %s from %s" % (s, f))
            if not s in list(itertools.chain.from_iterable(syms.values())):
                bb.error("'%s' referenced in '%s' is not defined in any DTB file" % (s, f))
                ok = False
            else:
                for (sf,ss) in syms.items():
                    if s in ss:
                        bb.debug(4, "%s referenced in %s is defined in %s" % (s, f, sf))
                        break

    if not ok:
        bb.fatal("check_overlays failed")
}
addtask do_check_overlays after do_compile_dtbs before do_compile
