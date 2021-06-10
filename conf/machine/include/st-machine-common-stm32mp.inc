#@DESCRIPTION: Common Machine configuration for STM32 systems

require conf/machine/include/st-machine-flashlayout-stm32mp.inc

# =========================================================================
# boot scheme
# =========================================================================
# List of supported boot schemes
BOOTSCHEME_LABELS ??= "basic trusted"

# Don't include kernels in standard images
#RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""

MACHINE_FEATURES_append = " emmc"
#IMAGE_FSTYPES ?= "tar.bz2"
IMAGE_FSTYPES_append = "${@bb.utils.contains('MACHINE_FEATURES',"emmc"," ext4","",d)}"

# =========================================================================
# Image
# =========================================================================
# Partitions configuration
IMAGE_CLASSES += "st-partitions-image"

# Define image to use for extra partitions
STM32MP_BOOTFS_IMAGE = "karo-image-bootfs"
STM32MP_BOOTFS_LABEL = "bootfs"
STM32MP_BOOTFS_MOUNTPOINT_IMAGE = "/boot"
STM32MP_USERFS_IMAGE = "karo-image-userfs"
STM32MP_USERFS_LABEL = "userfs"
STM32MP_USERFS_MOUNTPOINT_IMAGE = "/usr/local"

# Define extra partition to build
PARTITIONS_IMAGE  = "${STM32MP_BOOTFS_IMAGE}"
PARTITIONS_IMAGE += "${STM32MP_USERFS_IMAGE}"
PARTITIONS_MOUNTPOINT_IMAGE  = "${STM32MP_BOOTFS_MOUNTPOINT_IMAGE}"
PARTITIONS_MOUNTPOINT_IMAGE += "${STM32MP_USERFS_MOUNTPOINT_IMAGE}"

# Provide list of partition to mount
MOUNT_PARTITIONS_LIST  = "${STM32MP_BOOTFS_LABEL},${STM32MP_BOOTFS_MOUNTPOINT_IMAGE}"
MOUNT_PARTITIONS_LIST += "${STM32MP_USERFS_LABEL},${STM32MP_USERFS_MOUNTPOINT_IMAGE}"

# Define image partition size (supposed to be set as max size in image recipe)
# Proposed value for bootfs is 64MB
BOOTFS_PARTITION_SIZE = "65536"
# Proposed value for rootfs should fit our highest constraint: NAND size (128MiB)
# For optee bootscheme we have the maximum partitions:
#   FSBL1 + SSBL + SSBL2 + TEEH + TEED + TEEX + Multivolume UBI = NAND size
#   Multivolume UBI = 128MiB - (2MiB + 2MiB + 2MiB + 512KiB + 512KiB + 512KiB) = 1016.5MiB
# With multivolume UBI split:
#   Multivolume UBI > uboot_config + uboot_config_r + bootfs + rootfs + userfs + UBI Overhead
# From http://www.linux-mtd.infradead.org/doc/ubi.html#L_overhead, we compute
# the UBI overhead for our NAND:
#   (20*4096/1024 + 4) * 256KiB + (256KiB - 248KiB) * (1016.5MiB/256KiB - 20*4096/1024 - 4) = 53360KiB
# In addition, for each UBIFS, our NAND consummed 9 extra eraseblocks
# So:
#   rootfs < Multivolume UBI - (rootfs + env + dtb + kernel + userfs + UBI Overhead + 4 * 9*eraseblocks)
#   rootfs < 120.5MiB - (256KiB + 64MiB + 16MiB + 128MiB + 53360KiB + 4 * 9 * 256KiB)
# Proposed value for rootfs is 2GiB
ROOTFS_PARTITION_SIZE ??= "2097152"
ROOTFS_PARTITION_SIZE_qsmp-1510 ??= "32*1024"
# Proposed value for userfs is 128MB (4*32MB)
USERFS_PARTITION_SIZE ??= "131072"

# Define the max size for ROOTFS image being built
# On other image partition such settings is directly done in image recipe
IMAGE_ROOTFS_MAXSIZE ?= ""

# Define specific U-Boot config partition settings
STM32MP_UBOOTCONFIG_NAME   = "uboot_config"
UBOOTCONFIG_PARTITION_SIZE ??= "256"

# Define specific EXT4 command line:
#   - Create minimal inode number (as it is done by default in image_types.bbclass)
#   - Add label name (maximum length of the volume label is 16 bytes)
#     So use IMAGE_NAME_SUFFIX name by removing the '.' and truncating to 16 caracters
#   - Deactivate metadata_csum and dir_index (hashed b-trees): update not supported
#     by U-Boot
EXTRA_IMAGECMD_ext4 = "-i 4096 -L ${@d.getVar('IMAGE_NAME_SUFFIX').replace('.', '', 1)[:16]} -O ^metadata_csum,^dir_index"

# =========================================================================
# tf-a
# =========================================================================
# Finally we must compile tf-a in all cases as we need trusted binary to boot
EXTRA_IMAGEDEPENDS += "tf-a-stm32mp"

# Define default TF-A config
TF_A_CONFIG_append = "${@bb.utils.contains('BOOTSCHEME_LABELS', 'trusted', ' trusted', '', d)}"

# Define SECURE_PAYLOAD config to set for each TF_A_CONFIG
TF_A_CONFIG_serialboot  = "AARCH32_SP=sp_min"
TF_A_CONFIG_trusted     = "AARCH32_SP=sp_min"

# Manage proper update for TF_A_CONFIG_* var
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'serialboot', 'TF_A_CONFIG_serialboot', '', d)}"
tfaconfig_env[vardeps] += "${@bb.utils.contains('TF_A_CONFIG', 'trusted', 'TF_A_CONFIG_trusted', '', d)}"

# List of TF-A device tree to use
TF_A_DEVICETREE = "${STM32MP_DEVICETREE}"

# =========================================================================
# flashlayout
# =========================================================================
# Define bootscheme label to allow specific expansion for partition vars
FLASHLAYOUT_BOOTSCHEME_LABELS += "${BOOTSCHEME_LABELS}"

# Default config labels supported
FLASHLAYOUT_CONFIG_LABELS ??= ""
