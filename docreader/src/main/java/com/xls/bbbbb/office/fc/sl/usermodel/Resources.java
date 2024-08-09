package com.xls.bbbbb.office.fc.sl.usermodel;

/**
 * Common SlideShow resources, such as fonts, pictures
 *  and multimedia data
 */
public interface Resources {
	public FontCollection getFontCollection();

	public PictureData[] getPictureData();
	public int addPictureData(PictureData pict);
}
