#@DESCRIPTION: STM32MP machine flashlayout configuration

INHERIT += "flashlayout-stm32mp"

# Add specific dependencies to get all binaries generated before flashlayout files
FLASHLAYOUT_DEPEND_TASKS += "${@bb.utils.contains('EXTRA_IMAGEDEPENDS', 'tf-a-stm32mp', 'tf-a-stm32mp:do_deploy', '', d)}"
FLASHLAYOUT_DEPEND_TASKS += "${@bb.utils.contains('EXTRA_IMAGEDEPENDS', 'u-boot-karo', 'u-boot-karo:do_deploy', '', d)}"

# -----------------------------------------------------------------------------
# Define flashlayout devices
# -----------------------------------------------------------------------------
DEVICE_EMMC    = "mmc0"
DEVICE_SPINAND = "spi-nand0"

# -----------------------------------------------------------------------------
# EMMC
# Extra space is required to store 'Protective MBR' and 'Primary GPT Header'
# Currently the required size is 17kBytes (i.e. 0x4400)
# We need to align this size to get the first offset to use
DEVICE_START_OFFSET_mmc0    = "0x00100000"
DEVICE_ALIGNMENT_SIZE_mmc0  = "0x00080000"

# -----------------------------------------------------------------------------
# SPINAND
#
DEVICE_START_OFFSET_spi-nand0 = "0x00000000"
DEVICE_ALIGNMENT_SIZE_spi-nand0 = "0x00020000"

# -----------------------------------------------------------------------------
# Define bootscheme labels
# -----------------------------------------------------------------------------
FLASHLAYOUT_BOOTSCHEME_LABELS ??= "basic trusted"

# -----------------------------------------------------------------------------
# Define config labels
# -----------------------------------------------------------------------------
# NOTE: define can be done with following priority assignment:
#           1) list_<BOOTSCHEME>
#           2) list
#           3) Default 'list' to 'none' when not defined
# -----------------------------------------------------------------------------
FLASHLAYOUT_CONFIG_LABELS ??= "emmc"

# Set by default this variable to 0, and set to 1 only when we are using st-example-image-*
ST_EXAMPLE_IMAGE ??= "0"

# -----------------------------------------------------------------------------
# Define label types
# -----------------------------------------------------------------------------
# NOTE: define can be done with following priority assignment:
#           1) list_<BOOTSCHEME>_<CONFIG>
#           2) list_<BOOTSCHEME>
#           3) list_<CONFIG>
#           4) list
#           5) Default 'list' to 'none' when not defined
# -----------------------------------------------------------------------------
# EMMC
# Set flashlayout file generation to eval board (mother and daughter) only
FLASHLAYOUT_TYPE_LABELS_emmc = "${MACHINE}"
FLASHLAYOUT_TYPE_LABELS_spinand = "${MACHINE}"

# -----------------------------------------------------------------------------
# Define partitions to use
# -----------------------------------------------------------------------------
# There are few restrictions to follow:
#   - The partition for the first boot loader should follow the naming rule:
#       fsbl*
#   - The partition for the secondary boot loader should follow the naming rule:
#       ssbl
# -----------------------------------------------------------------------------
# NOTE: define can be done with following priority assignment:
#           1) list_<BOOTSCHEME>_<CONFIG>
#           2) list_<BOOTSCHEME>
#           3) list_<CONFIG>
#           4) list
#           5) Default 'list' to 'none' when not defined
# -----------------------------------------------------------------------------

FLASHLAYOUT_PARTITION_LABELS_basic_emmc           = "fsbl1-boot ssbl-boot fsbl1 ssbl bootfs rootfs userfs"
FLASHLAYOUT_PARTITION_LABELS_basic_spinand        = "fsbl1-boot ssbl-boot fsbl ssbl UBI"

# -----------------------------------------------------------------------------
# Partition configuration for each partition label
# NOTE: each item can be defined with following priority assignment:
#           1) item_<BOOTSCHEME>_<CONFIG>_<PARTITION>
#           2) item_<BOOTSCHEME>_<CONFIG>
#           3) item_<BOOTSCHEME>_<PARTITION>
#           4) item_<BOOTSCHEME>
#           5) item_<CONFIG>_<PARTITION>
#           6) item_<CONFIG>
#           7) item_<PARTITION>
#           8) item
#           9) Default 'item' to 'none' when not defined
# -----------------------------------------------------------------------------
FLASHLAYOUT_PARTITION_ENABLE                = "P"
FLASHLAYOUT_PARTITION_ENABLE_fsbl1-boot     = "-"
FLASHLAYOUT_PARTITION_ENABLE_ssbl-boot      = "-"
FLASHLAYOUT_PARTITION_ENABLE_empty          = "PE"
FLASHLAYOUT_PARTITION_ENABLE_emptyfsbl1     = "PE"
FLASHLAYOUT_PARTITION_ENABLE_emptyssbl      = "PE"
FLASHLAYOUT_PARTITION_ENABLE_emptyssbl2     = "PE"
FLASHLAYOUT_PARTITION_ENABLE_env            = "PED"

# -----------------------------------------------------------------------------
# Partition ID
# -----------------------------------------------------------------------------
# The STM32CubeProgrammer supported ID range is:
#   0x00 to 0xFF
# Some IDs are reserved for internal usage on STM32CubeProgrammer and special
# management is implemented for binary with STM32 header. This means that for
# flashlayout files, available ID range is only:
#   0x01 to 0x0F for Boot partitions with STM32 header
#   0x10 to 0xF0 for User partitions programmed without header
# Note also that for FSBL and SSBL binaries loaded in RAM to program the devices
# there are two reserved IDs
#   0x01 for FSBL
#   0x03 for SSBL
FLASHLAYOUT_PARTITION_ID_fsbl1-boot     = "0x01"
FLASHLAYOUT_PARTITION_ID_ssbl-boot      = "0x03"
FLASHLAYOUT_PARTITION_ID_fsbl1          = "0x04"
FLASHLAYOUT_PARTITION_ID_fsbl2          = "0x05"
FLASHLAYOUT_PARTITION_ID_ssbl           = "0x06"
FLASHLAYOUT_PARTITION_ID_ssbl2          = "0x07"
FLASHLAYOUT_PARTITION_ID_teeh           = "0x0A"
FLASHLAYOUT_PARTITION_ID_teed           = "0x0B"
FLASHLAYOUT_PARTITION_ID_teex           = "0x0C"
FLASHLAYOUT_PARTITION_ID_empty          = "0x10"
FLASHLAYOUT_PARTITION_ID_emptyfsbl1     = "0x11"
FLASHLAYOUT_PARTITION_ID_emptyssbl      = "0x12"
FLASHLAYOUT_PARTITION_ID_emptyssbl2     = "0x13"
FLASHLAYOUT_PARTITION_ID_env            = "0x20"
FLASHLAYOUT_PARTITION_ID_bootfs         = "0x21"
FLASHLAYOUT_PARTITION_ID_vendorfs       = "0x22"
FLASHLAYOUT_PARTITION_ID_rootfs         = "0x23"
FLASHLAYOUT_PARTITION_ID_userfs         = "0x24"
FLASHLAYOUT_PARTITION_ID_fsbl           = "0x04"
FLASHLAYOUT_PARTITION_ID_ubi            = "0x21"

FLASHLAYOUT_PARTITION_TYPE                  = "Binary"
FLASHLAYOUT_PARTITION_TYPE_bootfs           = "System"
FLASHLAYOUT_PARTITION_TYPE_vendorfs         = "FileSystem"
FLASHLAYOUT_PARTITION_TYPE_rootfs           = "FileSystem"
FLASHLAYOUT_PARTITION_TYPE_userfs           = "FileSystem"
FLASHLAYOUT_PARTITION_TYPE_ubi              = "Binary"

FLASHLAYOUT_PARTITION_DEVICE_emmc = "${DEVICE_EMMC}"
FLASHLAYOUT_PARTITION_DEVICE_spinand = "${DEVICE_SPINAND}"
# Specific for fsbl1-boot ssbl-boot partitions
FLASHLAYOUT_PARTITION_DEVICE_basic_fsbl1-boot = "none"
FLASHLAYOUT_PARTITION_DEVICE_basic_ssbl-boot  = "none"
FLASHLAYOUT_PARTITION_DEVICE_trusted_fsbl1-boot = "none"
FLASHLAYOUT_PARTITION_DEVICE_trusted_ssbl-boot  = "none"

FLASHLAYOUT_PARTITION_OFFSET_fsbl1-boot = "0x0"
FLASHLAYOUT_PARTITION_OFFSET_ssbl-boot  = "0x0"
FLASHLAYOUT_PARTITION_OFFSET_emmc_fsbl1 = "boot1"
FLASHLAYOUT_PARTITION_OFFSET_emmc_fsbl2 = "boot2"
FLASHLAYOUT_PARTITION_OFFSET_emmc_ssbl  = "${DEVICE_START_OFFSET_mmc0}"
FLASHLAYOUT_PARTITION_OFFSET_spinand_fsbl = "0x0"
FLASHLAYOUT_PARTITION_OFFSET_spinand_ssbl = "0x00080000"

# Size defined in Kbytes
FLASHLAYOUT_PARTITION_SIZE_fsbl1    = "256"
FLASHLAYOUT_PARTITION_SIZE_fsbl2    = "256"
FLASHLAYOUT_PARTITION_SIZE_ssbl     = "2048"
FLASHLAYOUT_PARTITION_SIZE_ssbl2    = "2048"
FLASHLAYOUT_PARTITION_SIZE_env      = "256"
FLASHLAYOUT_PARTITION_SIZE_spinand_fsbl = "512"

# Specific override for partition size as the configuration should follow
# the U-Boot source code where these partition sizes are hard coded
FLASHLAYOUT_PARTITION_SIZE_empty    = "0"
FLASHLAYOUT_PARTITION_SIZE_emptyfsbl1   = "${FLASHLAYOUT_PARTITION_SIZE_fsbl1}"
FLASHLAYOUT_PARTITION_SIZE_emptyssbl    = "${FLASHLAYOUT_PARTITION_SIZE_ssbl}"
FLASHLAYOUT_PARTITION_SIZE_emptyssbl2   = "${FLASHLAYOUT_PARTITION_SIZE_ssbl2}"
FLASHLAYOUT_PARTITION_SIZE_bootfs       = "${BOOTFS_PARTITION_SIZE}"
FLASHLAYOUT_PARTITION_SIZE_emmc_rootfs  = "${ROOTFS_PARTITION_SIZE}"
FLASHLAYOUT_PARTITION_SIZE_spinand_rootfs = "${SPINAND_ROOTFS_PARTITION_SIZE}"
FLASHLAYOUT_PARTITION_SIZE_userfs       = "${USERFS_PARTITION_SIZE}"
FLASHLAYOUT_PARTITION_SIZE_ubi          = "${UBI_PARTITION_SIZE}"

# Set binaries to use for each partition
FLASHLAYOUT_PARTITION_BIN2LOAD_fsbl1-boot   = "tf-a.stm32"
FLASHLAYOUT_PARTITION_BIN2LOAD_ssbl-boot    = "u-boot-mfg.bin"
FLASHLAYOUT_PARTITION_BIN2LOAD_fsbl1        = "tf-a.stm32"
FLASHLAYOUT_PARTITION_BIN2LOAD_basic_fsbl1  = "u-boot-spl.stm32"
FLASHLAYOUT_PARTITION_BIN2LOAD_fsbl         = "tf-a.stm32"
FLASHLAYOUT_PARTITION_BIN2LOAD_basic_fsbl   = "u-boot-spl.stm32"
FLASHLAYOUT_PARTITION_BIN2LOAD_ssbl         = "u-boot.bin"
FLASHLAYOUT_PARTITION_BIN2LOAD_basic_ssbl   = "u-boot.bin"
FLASHLAYOUT_PARTITION_BIN2LOAD_bootfs       = "${STM32MP_BOOTFS_IMAGE}-${MACHINE}.ext4"
FLASHLAYOUT_PARTITION_BIN2LOAD_rootfs       = "${IMAGE_LINK_NAME}.ext4"
FLASHLAYOUT_PARTITION_BIN2LOAD_userfs       = "${STM32MP_USERFS_IMAGE}-${MACHINE}.ext4"
FLASHLAYOUT_PARTITION_BIN2LOAD_ubi          = "${IMAGE_LINK_NAME}.ubi"

# -----------------------------------------------------------------------------
# Use the 'BIN2BOOT_REPLACE_PATTERNS' var to allow dynamic binary renaming for
# the bootloader binaries. This is only required for fsbl1-boot and ssbl-boot
# partitions that provides the binary to flash the device.
# The format to follow is:
#   '<PATTERN2REPLACE_1>;<PATTERN2SET_1> <PATTERN2REPLACE_2>;<PATTERN2SET_2>'
# And the pattern to replace in binary name is only searched as:
#   '-<PATTERN>$'
#   or
#   '-<PATTERN>-'
# -----------------------------------------------------------------------------
# The 'basic' bootscheme does not support Programmer mode, so use 'trusted' one
# (valid for both fsbl1-boot and ssbl-boot)
BIN2BOOT_REPLACE_PATTERNS_DEFAULT = "basic;trusted"

# Apply for fsbl1-boot and ssbl-boot
BIN2BOOT_REPLACE_PATTERNS_fsbl1-boot = "${BIN2BOOT_REPLACE_PATTERNS_DEFAULT}"
BIN2BOOT_REPLACE_PATTERNS_ssbl-boot  = "${BIN2BOOT_REPLACE_PATTERNS_DEFAULT}"
