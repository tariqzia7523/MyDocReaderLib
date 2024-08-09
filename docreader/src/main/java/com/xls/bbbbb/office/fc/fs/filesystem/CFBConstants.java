
package com.xls.bbbbb.office.fc.fs.filesystem;


public interface CFBConstants
{
    public static final int SMALLER_BIG_BLOCK_SIZE = 0x0200;
    public static final BlockSize SMALLER_BIG_BLOCK_SIZE_DETAILS = new BlockSize(SMALLER_BIG_BLOCK_SIZE, (short)9);
    public static final int LARGER_BIG_BLOCK_SIZE = 0x1000;
    public static final BlockSize LARGER_BIG_BLOCK_SIZE_DETAILS = new BlockSize(LARGER_BIG_BLOCK_SIZE, (short)12);

    public static final int SMALL_BLOCK_SIZE = 0x0040;

    /** How big a single property is */
    public static final int PROPERTY_SIZE = 0x0080;

    public static final int BIG_BLOCK_MINIMUM_DOCUMENT_SIZE = 0x1000;

    /** The highest sector number you're allowed, 0xFFFFFFFA */
    public static final int LARGEST_REGULAR_SECTOR_NUMBER = -5;

    /** Indicates the sector holds a DIFAT block (0xFFFFFFFC) */
    public static final int DIFAT_SECTOR_BLOCK = -4;
    /** Indicates the sector holds a FAT block (0xFFFFFFFD) */
    public static final int FAT_SECTOR_BLOCK = -3;
    /** Indicates the sector is the end of a chain (0xFFFFFFFE) */
    public static final int END_OF_CHAIN = -2;
    /** Indicates the sector is not used (0xFFFFFFFF) */
    public static final int UNUSED_BLOCK = -1;
} 
