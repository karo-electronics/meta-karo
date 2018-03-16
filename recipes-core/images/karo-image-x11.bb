IMAGE_BASENAME = "karo-image-x11"

require karo-image.inc

IMAGE_LINGUAS_append = " de-de"

#python extend_recipe_sysroot_append() {
#    if d.getVar('UBOOT_MACHINE') != None:
#        raise_sanity_error("build dir has been configured for u-boot; cannot build 'karo-image-x11'", d)
#    if d.getVar('DISTRO') != 'karo-image-x11':
#        raise_sanity_error("cannot build 'karo-image-x11' with DISTRO '%s'" % d.getVar('DISTRO'), d)
#}
