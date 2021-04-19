FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
	file://vpu/vpu_fw_imx6d.bin;subdir=linux-firmware-${PV} \
	file://vpu/vpu_fw_imx6q.bin;subdir=linux-firmware-${PV} \
	file://LICENSE.vpu_firmware;subdir=linux-firmware-${PV} \
	file://0001-whence-add-vpu-firmware.patch \
"

PACKAGES =+ "${PN}-imx-vpu-license ${PN}-imx-vpu"

LICENSE =+ "Firmware-imx-vpu_firmware &"
LIC_FILES_CHKSUM_remove = "file://WHENCE;md5=ef0565762eac313c409567b59dff00b2"
LIC_FILES_CHKSUM =+ "file://LICENSE.vpu_firmware;md5=8cf95184c220e247b9917e7244124c5a \
                     file://WHENCE;md5=10173ab7e74b59f45f29e1aabefb7fea \
"

NO_GENERIC_LICENSE[Firmware-imx-vpu_firmware] = "LICENSE.vpu_firmware"

LICENSE_${PN}-imx-vpu = "Firmware-imx-vpu_firmware"
LICENSE_${PN}-imx-vpu-license = "Firmware-imx-vpu_firmware"

FILES_${PN}-imx-vpu = " \
		    ${nonarch_base_libdir}/firmware/vpu/vpu_fw_imx6d.bin \
		    ${nonarch_base_libdir}/firmware/vpu/vpu_fw_imx6q.bin \
"

RPROVIDES_${PN}-imx-vpu = "firmware-imx-vpu"
RREPLACES_${PN}-imx-vpu = "firmware-imx-vpu"
RCONFLICTS_${PN}-imx-vpu = "firmware-imx-vpu"

FILES_${PN}-imx-vpu-license = "${nonarch_base_libdir}/firmware/LICENSE.vpu_firmware"

RDEPENDS_${PN}-imx-vpu += "${PN}-imx-vpu-license"

LICENSE_${PN} =+ "Firmware-imx-vpu_firmware &"

do_install_prepend () {
    set -x
}
