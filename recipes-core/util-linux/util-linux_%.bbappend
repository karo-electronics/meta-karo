PACKAGES =+ "util-linux-setterm"

ALTERNATIVE_util-linux-setterm += "setterm"
ALTERNATIVE_LINK_NAME[setterm] = "${bindir}/setterm"
FILES_util-linux-setterm = "${bindir}/setterm*"
