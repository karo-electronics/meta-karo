rm_base_bindir_progs := "${base_bindir_progs}"

# The only thing we actually want from coreutils
rm_base_bindir_progs_remove = " \
                            date \
"

ALTERNATIVE_PRIORITY = "0"
ALTERNATIVE_PRIORITY[date] = "300"
ALTERNATIVE_${PN} = "date"
ALTERNATIVE_${PN}-doc = ""

do_install_append () {
    echo "base_bindir = ${base_bindir}"
    echo "bindir      = ${bindir}"
    echo "sbindir     = ${sbindir}"
    echo "datarootdir = ${datarootdir}"
    echo "sharedstatedir = ${sharedstatedir}"

    echo "removing files from ${bindir}"
    for i in ${bindir_progs}; do rm -v ${D}${bindir}/$i; done

    echo "removing files from ${bindir}"
    for i in df mktemp base32 base64 lbracket; do rm -v ${D}${bindir}/$i.${BPN}; done

    echo "removing files from ${sbindir}"
    for i in ${sbindir_progs};do rm -v ${D}${sbindir}/$i.${BPN}; done

    echo "removing files from ${base_bindir}"
    for i in ${rm_base_bindir_progs};do rm -v ${D}${base_bindir}/$i.${BPN}; done

    # remove now empty directories
    echo "removing empty directories"
    find ${D}${base_bindir} ${D}${bindir} ${D}${sbindir} -depth -type d -empty -exec rmdir -v \{\} \;
}
