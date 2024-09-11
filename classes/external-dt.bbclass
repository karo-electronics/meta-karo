# Enable use of external device tree
EXTERNAL_DT_ENABLED ??= "1"

STAGING_EXTDT_DIR = "${TMPDIR}/work-shared/${MACHINE}/external-dt"

do_compile[depends] += " external-dt:do_configure"

