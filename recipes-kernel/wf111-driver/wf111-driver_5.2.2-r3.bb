DESCRIPTION = "WF111 driver and bin utils"
SECTION = "wireless driver"
LICENSE = "CLOSED"

inherit module

INSANE_SKIP_${PN} = "already-stripped"

WF111_VERSION = "5.2.2-r3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRCREV = "silabs-drv-r3"
SRC_URI = " \
	git://github.com/karo-electronics/wf111-driver;branch=${SRCREV};destsuffix=wf111-driver-${WF111_VERSION} \
	file://patches/${PREFERRED_VERSION_linux-karo}/bugfixes.patch \
"
RPROVIDES_${PN} += "wf111-driver"
RPROVIDES_${PN} += "wifi"
RPROVIDES_${PN} += "kernel-module-unifi-sdio${KERNEL_MODULE_PACKAGE_SUFFIX}"

RDEPENDS_${PN} += "wireless-tools wpa-supplicant"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_configure_prepend() {
    export KDIR=${STAGING_KERNEL_BUILDDIR}
}

do_compile () {
    make unifi_sdio KDIR=${STAGING_KERNEL_BUILDDIR}
}

do_install () {
    make install_static KDIR=${STAGING_KERNEL_BUILDDIR} OUTPUT=${D}
} 

FILES_${PN} = " \
	    /usr/sbin/unififw \
	    /usr/sbin/unifi_config \
	    /usr/sbin/unifi_helper \
	    /lib/* \
"
