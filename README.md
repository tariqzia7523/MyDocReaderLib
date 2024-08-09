# MyDocReaderLib

## implementation

Add flowing in
maven { url 'https://jitpack.io' }

then following line in app gradle

[![](https://jitpack.io/v/tariqzia7523/MyDocReaderLib.svg)](https://jitpack.io/#tariqzia7523/MyDocReaderLib)


implementation 'com.github.tariqzia7523:MyDocReaderLib:Tag'

## Usage

Create a FrameLayout in your app, which will actually show the file. and pass that to object of FileOpenerClass

Like this 

        FileOpenerClass(activity, fileUri,frameLayout,mimeType)


### You may use this ass 

This lib can give you list of any required document type you may use following code. Its good to use it in another thread

         try{
                val list : ArrayList<FileModel> = FileListUtils.getExternalFileList(this@MainActivity, MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx")!!)
         }catch (e : Exception){
                e.printStackTrace()
         }


