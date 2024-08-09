package com.xls.bbbbb.office.fc.hslf.model;

import com.xls.bbbbb.office.fc.ShapeKit;
import com.xls.bbbbb.office.fc.ddf.EscherClientDataRecord;
import com.xls.bbbbb.office.fc.ddf.EscherComplexProperty;
import com.xls.bbbbb.office.fc.ddf.EscherContainerRecord;
import com.xls.bbbbb.office.fc.ddf.EscherOptRecord;
import com.xls.bbbbb.office.fc.ddf.EscherProperties;
import com.xls.bbbbb.office.fc.ddf.EscherRecord;
import com.xls.bbbbb.office.fc.hslf.exceptions.HSLFException;
import com.xls.bbbbb.office.fc.hslf.record.Document;
import com.xls.bbbbb.office.fc.hslf.record.ExControl;
import com.xls.bbbbb.office.fc.hslf.record.ExObjList;
import com.xls.bbbbb.office.fc.hslf.record.OEShapeAtom;
import com.xls.bbbbb.office.fc.hslf.record.Record;
import com.xls.bbbbb.office.fc.hslf.record.RecordTypes;
import com.xls.bbbbb.office.fc.util.LittleEndian;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;


/**
 * Represents an ActiveX control in a PowerPoint document.
 *
 * TODO: finish
 * @author Yegor Kozlov
 */
public final class ActiveXShape extends Picture
{
    public static final int DEFAULT_ACTIVEX_THUMBNAIL = -1;

    /**
     * Create a new <code>Picture</code>
     *
    * @param pictureIdx the index of the picture
     */
    public ActiveXShape(int movieIdx, int pictureIdx)
    {
        super(pictureIdx, null);
        setActiveXIndex(movieIdx);
    }

    /**
      * Create a <code>Picture</code> object
      *
      * @param escherRecord the <code>EscherSpContainer</code> record which holds information about
      *        this picture in the <code>Slide</code>
      * @param parent the parent shape of this picture
      */
    protected ActiveXShape(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }

    /**
     * Create a new Placeholder and initialize internal structures
     *
     * @return the created <code>EscherContainerRecord</code> which holds shape data
     */
    protected EscherContainerRecord createSpContainer(int idx, boolean isChild)
    {
        _escherContainer = super.createSpContainer(idx, isChild);

        /*EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        spRecord.setFlags(EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE
            | EscherSpRecord.FLAG_OLESHAPE);

        setShapeType(ShapeTypes.HostControl);
        setEscherProperty(EscherProperties.BLIP__PICTUREID, idx);
        setEscherProperty(EscherProperties.LINESTYLE__COLOR, 0x8000001);
        setEscherProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x80008);
        setEscherProperty(EscherProperties.SHADOWSTYLE__COLOR, 0x8000002);
        setEscherProperty(EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, -1);

        EscherClientDataRecord cldata = new EscherClientDataRecord();
        cldata.setOptions((short)0xF);
        _escherContainer.addChildRecord(cldata); // TODO unit test to prove getChildRecords().add is wrong

        OEShapeAtom oe = new OEShapeAtom();

        //convert hslf into ddf
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            //oe.writeOut(out);
        }
        catch(Exception e)
        {
            throw new HSLFException(e);
        }
        cldata.setRemainingData(out.toByteArray());
*/
        return _escherContainer;
    }

    /**
     * Assign a control to this shape
     *
     * @see com.xls.bbbbb.office.fc.hslf.usermodel.SlideShow#addMovie(String, int)
     * @param idx  the index of the movie
     */
    public void setActiveXIndex(int idx)
    {
        EscherContainerRecord spContainer = getSpContainer();
        for (Iterator<EscherRecord> it = spContainer.getChildIterator(); it.hasNext();)
        {
            EscherRecord obj = it.next();
            if (obj.getRecordId() == EscherClientDataRecord.RECORD_ID)
            {
                EscherClientDataRecord clientRecord = (EscherClientDataRecord)obj;
                byte[] recdata = clientRecord.getRemainingData();
                LittleEndian.putInt(recdata, 8, idx);
            }
        }
    }

    public int getControlIndex()
    {
        int idx = -1;
        OEShapeAtom oe = (OEShapeAtom)getClientDataRecord(RecordTypes.OEShapeAtom.typeID);
        if (oe != null)
            idx = oe.getOptions();
        return idx;
    }

    /**
     * Set a property of this ActiveX control
     * @param key
     * @param value
     */
    public void setProperty(String key, String value)
    {

    }

    /**
     * Document-level container that specifies information about an ActiveX control
     *
     * @return container that specifies information about an ActiveX control
     */
    public ExControl getExControl()
    {
        int idx = getControlIndex();
        ExControl ctrl = null;
        Document doc = getSheet().getSlideShow().getDocumentRecord();
        ExObjList lst = (ExObjList)doc.findFirstOfType(RecordTypes.ExObjList.typeID);
        if (lst != null)
        {
            Record[] ch = lst.getChildRecords();
            for (int i = 0; i < ch.length; i++)
            {
                if (ch[i] instanceof ExControl)
                {
                    ExControl c = (ExControl)ch[i];
                    if (c.getExOleObjAtom().getObjID() == idx)
                    {
                        ctrl = c;
                        break;
                    }
                }
            }
        }
        return ctrl;
    }

    protected void afterInsert(Sheet sheet)
    {
        ExControl ctrl = getExControl();
        ctrl.getExControlAtom().setSlideId(sheet._getSheetNumber());

        try
        {
            String name = ctrl.getProgId() + "-" + getControlIndex();
            byte[] data = (name + '\u0000').getBytes("UTF-16LE");
            EscherComplexProperty prop = new EscherComplexProperty(
                EscherProperties.GROUPSHAPE__SHAPENAME, false, data);
            EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);
            opt.addEscherProperty(prop);
        }
        catch(UnsupportedEncodingException e)
        {
            throw new HSLFException(e);
        }
    }
}
