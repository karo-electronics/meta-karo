def mach_overlay (d, overlay, mach="", dist=""):
    """
    Select device tree overlay name based on machine and distro constraints.

    Resolves overlay names that may contain machine-specific alternatives.
    An overlay with a colon (:) provides two variants: the part before ':' is
    used if the machine matches MACHINEOVERRIDES, otherwise the part after ':'.

    Args:
        d: BitBake data dictionary
        overlay: Overlay name, optionally with ':' separator for alternatives
        mach: Machine name to check (default: "", matches any)
        dist: Required distro feature (default: "", no check)

    Returns:
        str: Selected overlay name, or "" if constraints are not satisfied
    """
    if dist:
        if not dist in d.getVar('DISTRO_FEATURES'):
            return ""
    if overlay.find(":") >= 0:
        alt1,alt2 = overlay.split(':')
        if mach in d.getVar('MACHINEOVERRIDES'):
            return alt1
        return alt2
    else:
        if mach == "" or mach in d.getVar('MACHINEOVERRIDES'):
            return overlay
    return ""

def get_overlays(d):
    """
    Build space-separated list of device tree overlay file paths.

    Expands DTB_OVERLAYS_generic and DTB_OVERLAYS variables into full paths.
    Generic overlays use the pattern: [DTB_VENDOR_DIR/]SOC_PREFIX-name.DTB_OVERLAY_EXT
    Machine-specific overlays use: [DTB_VENDOR_DIR/]SOC_FAMILY-name.DTB_OVERLAY_EXT
    Handles comma-separated overlay names and removes duplicates.

    Args:
        d: BitBake data dictionary containing DTB-related configuration variables

    Returns:
        str: Space-separated list of overlay file paths, or "" if no overlays are defined
    """
    ovlist = []
    vendordir = d.getVar('DTB_VENDOR_DIR')
    ovext = d.getVar('DTB_OVERLAY_EXT')
    g = d.getVar('DTB_OVERLAYS_generic') or ""
    o = d.getVar('DTB_OVERLAYS') or ""
    for ov in g.split():
        for fn in ov.split(","):
            fn = " %s%s-%s.%s" % (vendordir + "/" if vendordir != "" else "", d.getVar('SOC_PREFIX'), fn, ovext)
            if fn in ovlist:
                bb.debug(2, "'%s' is already in overlays" % fn)
            else:
                bb.debug(2, "Adding '%s' to overlays" % fn)
                ovlist += [fn]

    for ov in o.split():
        for fn in ov.split(","):
            fn = " %s%s-%s.%s" % (vendordir + "/" if vendordir != "" else "", d.getVar('SOC_FAMILY'), fn, ovext)
            if fn in ovlist:
                bb.debug(2, "'%s' is already in overlays" % fn)
            else:
                bb.debug(2, "Adding '%s' to overlays" % fn)
                ovlist += [fn]

    if len(ovlist) == 0:
        return ""
    bb.debug(2, "ovlist='%s'" % ovlist)
    return " ".join(ovlist)
