#command untuk mengcompile program java

ecj namafile.java
dx --dex --output=namadex.dex namaclass.class
dalvikvm -cp namadex.dex namaclass
