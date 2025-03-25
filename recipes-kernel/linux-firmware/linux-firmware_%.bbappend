FILESEXTRAPATHS:prepend:stm32mpcommon := "${THISDIR}/${PN}:"

# Add calibration file
SRC_URI:append:stm32mpcommon = " git://github.com/murata-wireless/cyw-fmac-nvram.git;protocol=https;nobranch=1;name=nvram;destsuffix=nvram-murata "
SRCREV_nvram = "61b41349b5aa95227b4d2562e0d0a06ca97a6959"
SRC_URI:append:stm32mpcommon = " git://github.com/murata-wireless/cyw-fmac-fw.git;protocol=https;nobranch=1;name=murata;destsuffix=murata "
SRCREV_murata = "a80cb77798a8d57f15b7c3fd2be65553d9bd5125"
SRCREV_FORMAT = "linux-firmware-murata"
SRC_URI:append:stm32mpcommon = "git://github.com/murata-wireless/cyw-bt-patch;protocol=https;nobranch=1;name=bt;destsuffix=bt "
SRCREV_bt = "bbc63f8b15394023c4a2fd9f74565fbd0d76ae71"

LICENSE =+ "Firmware-cypress-bcm43 &"
NO_GENERIC_LICENSE[Firmware-cypress-bcm43] = "LICENCE.cypress"

do_install:append:stm32mpcommon() {
   # ---- 4373 ----
   # Install calibration file
   install -m 0644 ${WORKDIR}/nvram-murata/cyfmac4373-sdio.2AE.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.txt
   # disable Wakeup on WLAN
   sed -i "s/muxenab=\(.*\)$/#muxenab=\1/g" ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.txt
   # Install calibration file (stm32mp25)
   install -m 0644 ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.st,stm32mp215f-dk.txt

   # Take newest murata firmware
   install -m 0644 ${WORKDIR}/murata/cyfmac4373-sdio.2AE.bin ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.bin
   install -m 0644 ${WORKDIR}/murata/cyfmac4373-sdio.2AE.clm_blob ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.clm_blob

   # Add symlinks for newest kernel compatibility
   cd ${D}${nonarch_base_libdir}/firmware/brcm/
   ln -sf brcmfmac4373-sdio.bin brcmfmac4373-sdio.karo,${SOC_FAMILY}-${MACHINE}.bin
   ln -sf brcmfmac4373-sdio.clm_blob brcmfmac4373-sdio.karo,${SOC_FAMILY}-${MACHINE}.clm_blob
   ln -sf brcmfmac4373-sdio.txt brcmfmac4373-sdio.karo,${SOC_FAMILY}-${MACHINE}.txt

}

do_install:append() {
    install -d ${D}${nonarch_base_libdir}/firmware/brcm/

    # 4373
    install -m 644 ${WORKDIR}/bt/LICENCE.cypress ${D}${nonarch_base_libdir}/firmware/LICENCE.cypress_bcm4373
    install -m 644 ${WORKDIR}/bt/BCM4373A0_001.001.025.0103.0155.FCC.CE.2AE.hcd ${D}${nonarch_base_libdir}/firmware/brcm/BCM4373A0.hcd
    cd ${D}${nonarch_base_libdir}/firmware/brcm/
    ln -sf BCM4373A0.hcd BCM.karo,${SOC_FAMILY}-${MACHINE}.hcd
}

FILES:${PN}-bcm4373:append:stm32mpcommon = " \
  ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.txt \
  ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.bin \
  ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.clm_blob \
  ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.karo,${SOC_FAMILY}-${MACHINE}* \
  ${nonarch_base_libdir}/firmware/brcm/BCM4373A0.hcd \
  ${nonarch_base_libdir}/firmware/brcm/BCM.karo,${SOC_FAMILY}-${MACHINE}* \
"

LICENSE:${PN} = "Firmware-cypress-bcm43"
LICENSE:${PN}-cypress-license = "Firmware-cypress-bcm43"

FILES:${PN}-cypress-license = "${nonarch_base_libdir}/firmware/LICENCE.cypress*"
FILES:${PN} = "${nonarch_base_libdir}/firmware/"

RDEPENDS:${PN} += "${PN}-cypress-license"

