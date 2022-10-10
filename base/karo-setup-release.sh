#!/bin/bash
#
# Ka-Ro Yocto Project Build Environment Setup Script
#
# Copyright (C) 2022 Lothar Waßmann <LW@KARO-electronics.de>
#   based on imx-setup-release.sh Copyright (C) 2011-2016 Freescale Semiconductor
#                                 Copyright 2017 NXP
#

CWD=`pwd`
BASENAME="karo-setup-release.sh"
PROGNAME="setup-environment"
SRCDIR=layers

exit_message () {
    echo "To return to this build environment later please run:"
    echo -e "\tsource setup-environment <build_dir>"
}

usage() {
    echo "Usage: source $BASENAME [-b <build-dir>] [-h]"

    echo "Optional parameters:
* [-b <build-dir>]: Build directory, where <build-dir> is a sensible name of a
                    directory to be created.
                    If unspecified script uses 'build' as output directory.
* [-h]: help
"
}

clean_up() {
    unset CWD BUILD_DIR KARO_DISTRO
    unset usage clean_up
    unset ARM_DIR
    exit_message
}

layer_exists() {
    for l in $layers;do
	[ "$1" = "$l" ] && return
    done
    false
}

add_layer() {
    layer_exists && return
    layers="$layers $1 "
    echo "BBLAYERS += \"\${BSPDIR}/${SRCDIR}/$1\"" >> "$BUILD_DIR/conf/bblayers.conf"
}

# get command line options
OLD_OPTIND=$OPTIND
unset KARO_DISTRO

while getopts b:h: opt; do
    case ${opt} in
	b)
	    BUILD_DIR="$OPTARG"
	    echo "Build directory is: $BUILD_DIR"
	    ;;
	h)
	    setup_help=true
	    ;;
	*)
	    setup_error=true
	    ;;
    esac
done
shift $((OPTIND-1))

if [ $# -ne 0 ]; then
    setup_error=true
    echo "Unexpected positional parameters: '$@'" >&2
fi
OPTIND=$OLD_OPTIND
if test $setup_help;then
    usage && clean_up && return 1
elif test $setup_error;then
    clean_up && return 1
fi

if [ -z "$DISTRO" ]; then
    if [ -z "$KARO_DISTRO" ]; then
	KARO_DISTRO='karo-wayland'
    fi
    export DISTRO="$KARO_DISTRO"
else
    KARO_DISTRO="$DISTRO"
fi

if [ -z "$BUILD_DIR" ]; then
    BUILD_DIR='build-karo'
fi

if [ -z "$MACHINE" ]; then
    :
fi

layers=""

# Backup CWD value as it's going to be unset by upcoming external scripts calls
CURRENT_CWD="$CWD"

# Set up the basic yocto environment
DISTRO=${KARO_DISTRO:-DISTRO} MACHINE=$MACHINE . ./$PROGNAME $BUILD_DIR

# Set CWD to a value again as it's being unset by the external scripts calls
[ -z "$CWD" ] && CWD="$CURRENT_CWD"

# Point to the current directory since the last command changed the directory to $BUILD_DIR
BUILD_DIR=.

if [ ! -e "$BUILD_DIR/conf/local.conf" ]; then
    echo -e "\n ERROR - No build directory is set yet. Run the 'setup-environment' script before running this script to create $BUILD_DIR\n"
    echo -e "\n"
    return 1
fi

# On the first script run, backup the local.conf file
# Consecutive runs, it restores the backup and changes are appended on this one.
if [ ! -e "$BUILD_DIR/conf/local.conf.org" ]; then
    cp "$BUILD_DIR/conf/local.conf" "$BUILD_DIR/conf/local.conf.org"
else
    cp "$BUILD_DIR/conf/local.conf.org" "$BUILD_DIR/conf/local.conf"
fi

if [ ! -e "$BUILD_DIR/conf/bblayers.conf.org" ]; then
    cp "$BUILD_DIR/conf/bblayers.conf" "$BUILD_DIR/conf/bblayers.conf.org"
else
    cp "$BUILD_DIR/conf/bblayers.conf.org" "$BUILD_DIR/conf/bblayers.conf"
fi

echo "" >> "$BUILD_DIR/conf/bblayers.conf"
echo "# Ka-Ro Yocto Project Release layers" >> "$BUILD_DIR/conf/bblayers.conf"

echo "" >> "$BUILD_DIR/conf/bblayers.conf"
echo "# Ka-Ro specific layers" >> "$BUILD_DIR/conf/bblayers.conf"
add_layer meta-karo
add_layer meta-karo-distro

case $KARO_DISTRO in
    karo-custom-*)
	if [ -d "${BSPDIR}/${SRCDIR}/meta${KARO_DISTRO#karo-custom}" ];then
	    add_layer "meta${KARO_DISTRO#karo-custom}"
	else
	    echo "No custom layer found for distro: '$KARO_DISTRO'" >&2
	fi
	;;
    *)
	if [ "$KARO_DISTRO" != "karo-minimal" ];then
	    add_layer meta-qt6
	fi
esac

echo "BSPDIR='$(cd "$BSPDIR";pwd)'"
echo "BUILD_DIR='$(cd "$BUILD_DIR";pwd)'"

cd "$BUILD_DIR"
clean_up
