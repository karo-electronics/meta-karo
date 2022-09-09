FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://vpu/vpu_fw_imx6d.bin;subdir=linux-firmware-${PV} \
	file://vpu/vpu_fw_imx6q.bin;subdir=linux-firmware-${PV} \
	file://LICENSE.vpu_firmware;subdir=linux-firmware-${PV} \
	file://0001-whence-add-vpu-firmware.patch \
"

PACKAGES =+ "${PN}-imx-vpu-license ${PN}-imx-vpu"

LICENSE =+ "Firmware-imx-vpu_firmware &"
LIC_FILES_CHKSUM:remove = "file://WHENCE;md5=4cf67d71a21887c682c3989a4318745e"
LIC_FILES_CHKSUM =+ "file://LICENSE.vpu_firmware;md5=8cf95184c220e247b9917e7244124c5a \
                     file://WHENCE;md5=f1af704e6d1e0c78058a4add8e21f625 \
"

NO_GENERIC_LICENSE[Firmware-imx-vpu_firmware] = "LICENSE.vpu_firmware"

LICENSE:${PN}-imx-vpu = "Firmware-imx-vpu_firmware"
LICENSE:${PN}-imx-vpu-license = "Firmware-imx-vpu_firmware"

FILES:${PN}-imx-vpu = " \
		    ${nonarch_base_libdir}/firmware/vpu/vpu_fw_imx6d.bin \
		    ${nonarch_base_libdir}/firmware/vpu/vpu_fw_imx6q.bin \
"

RPROVIDES:${PN}-imx-vpu = "firmware-imx-vpu"
RREPLACES:${PN}-imx-vpu = "firmware-imx-vpu"
RCONFLICTS:${PN}-imx-vpu = "firmware-imx-vpu"

FILES:${PN}-imx-vpu-license = "${nonarch_base_libdir}/firmware/LICENSE.vpu_firmware"

RDEPENDS:${PN}-imx-vpu += "${PN}-imx-vpu-license"

LICENSE:${PN} =+ "Firmware-imx-vpu_firmware &"
