SUMMARY = "A very basic Wayland image with Weston desktop and terminal"

IMAGE_FEATURES += " \
	       splash \
	       package-management \
	       ssh-server-dropbear \
	       hwcodecs \
"

LICENSE = "MIT"

inherit core-image distro_features_check

REQUIRED_DISTRO_FEATURES = "wayland"

CORE_IMAGE_BASE_INSTALL += " \
			weston \
			weston-init \
			weston-examples \
			gtk+3-demo \
			clutter-1.0-examples \
			glmark2 \
"

IMAGE_INSTALL += " \
		 libdrm \
		 libdrm-tests \
		 libdrm-kms \
		 libdrm-etnaviv \
"

CORE_IMAGE_BASE_INSTALL += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'weston-xwayland matchbox-terminal', '', d)}"

QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'

python extend_recipe_sysroot_append() {
    if d.getVar('DISTRO') != 'karo-wayland':
        raise_sanity_error("cannot build 'karo-image-weston' with DISTRO '%s'" % d.getVar('DISTRO'), d)
}
