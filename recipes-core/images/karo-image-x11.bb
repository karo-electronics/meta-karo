IMAGE_BASENAME = "karo-image-x11"
SUMMARY = "A very basic X11 image with a terminal"

require karo-image.inc

REQUIRED_DISTRO_FEATURES = "x11"

IMAGE_FEATURES_append = " \
		      splash \
		      ssh-server-openssh \
		      x11-base \
"

IMAGE_INSTALL_append = " \
		     twm \
		     udev \
		     xterm \
"

QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'

python extend_recipe_sysroot_append() {
    if d.getVar('DISTRO') != 'karo-x11':
        raise_sanity_error("cannot build 'karo-image-x11' with DISTRO '%s'" % d.getVar('DISTRO'), d)
}
