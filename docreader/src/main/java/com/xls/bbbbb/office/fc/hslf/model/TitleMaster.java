package com.xls.bbbbb.office.fc.hslf.model;

import com.xls.bbbbb.office.fc.hslf.model.textproperties.TextProp;
import com.xls.bbbbb.office.fc.hslf.record.SlideAtom;


/**
 * Title masters define the design template for slides with a Title Slide layout.
 *
 * @author Yegor Kozlov
 */
public final class TitleMaster extends MasterSheet
{
    private TextRun[] _runs;

    /**
     * Constructs a TitleMaster
     *
     */
    public TitleMaster(com.xls.bbbbb.office.fc.hslf.record.Slide record, int sheetNo)
    {
        super(record, sheetNo);

        _runs = findTextRuns(getPPDrawing());
        for (int i = 0; i < _runs.length; i++)
            _runs[i].setSheet(this);
    }

    /**
     * Returns an array of all the TextRuns found
     */
    public TextRun[] getTextRuns()
    {
        return _runs;
    }

    /**
     * Delegate the call to the underlying slide master.
     */
    public TextProp getStyleAttribute(int txtype, int level, String name, boolean isCharacter)
    {
        MasterSheet master = getMasterSheet();
        return master == null ? null : master.getStyleAttribute(txtype, level, name, isCharacter);
    }

    /**
     * Returns the slide master for this title master.
     */
    public MasterSheet getMasterSheet()
    {
        SlideMaster[] master = getSlideShow().getSlidesMasters();
        SlideAtom sa = ((com.xls.bbbbb.office.fc.hslf.record.Slide)getSheetContainer()).getSlideAtom();
        int masterId = sa.getMasterID();
        for (int i = 0; i < master.length; i++)
        {
            if (masterId == master[i]._getSheetNumber())
                return master[i];
        }
        return null;
    }
    
    /**
     *
     *
     */
    public void dispose()
    {
       super.dispose();
       if (_runs != null)
       {
           for (TextRun tr : _runs)
           {
               tr.dispose();
           }
           _runs = null;
       }
    }
}
