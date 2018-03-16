SUMMARY = "A console-only image for Ka-Ro electronics TX6 modules with Debian Package Management"

require karo-image.inc

IMAGE_LINGUAS_append = " de-de"

python extend_recipe_sysroot_append() {
    if d.getVar('DISTRO') != 'karo-base':
        raise_sanity_error("cannot build 'karo-image-base' with DISTRO '%s'" % d.getVar('DISTRO'), d)
}
