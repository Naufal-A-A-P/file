

import java.io.*;
import java.util.*;
import java.time.Year;

public class Main{  
  public static void main(String args[])throws IOException{
    String terminput;
    boolean apkhljt = true;
    while(apkhljt){
    menupilihan();
    
    Scanner inp= new Scanner(System.in);
    terminput = inp.next();
    
    switch(terminput){
        case "1":
          System.out.println("lihat data");
            readdata();
          break;
        case "2":
          System.out.println("cari data");
            caridata();
          break;
        case "3":
           System.out.println("tambah data");
           adddata();
           readdata();
          break;
        case "4":
          System.out.println("hapus data");
            deletedata();
          break;
        case "5":
          System.out.println("ubah data");
            changedata();
          break;
        default:
          break;
        }
      apkhljt = lanjut("\napakah anda ingin melanjutkan (y/n)");
    }
  }
  
  //membaca data
  public static void readdata()throws IOException{
    FileReader fileinp;
    BufferedReader filebuff;
    
    try{
      fileinp = new FileReader("input.txt");
      filebuff = new BufferedReader(fileinp);
    }catch(Exception e){
      System.err.println("file tidak ditemukan");
      return;
    }
    String tampil = filebuff.readLine();
    int nomor = 0;
    System.out.println("___________________________________________________________________________");
    System.out.println("| No | Thn Lahir |              Nama             | Kelas | Absen | Alamat");
    System.out.println("---------------------------------------------------------------------------");
    while(tampil != null){
      nomor++;
      StringTokenizer Strinc = new StringTokenizer(tampil,",");
      
      System.out.printf("|%2d  ",nomor);
      System.out.printf("| %-10s",Strinc.nextToken());
      System.out.printf("| %-30s",Strinc.nextToken());
      System.out.printf("| %-6s",Strinc.nextToken());
      System.out.printf("| %-6s",Strinc.nextToken());
      System.out.printf("| %-20s\n",Strinc.nextToken());
      
      tampil = filebuff.readLine();
    }
    System.out.println("___________________________________________________________________________");
  }
  
  //tampilan menu
  public static void menupilihan(){
    System.out.println("data nickname\n");
    System.out.println("1.Membaca data");
    System.out.println("2.Mencari data");
    System.out.println("3.Menambah data");
    System.out.println("4.Menghapus data");
    System.out.println("5.Mengubah data");
  }
  
  //method pilihan lanjut
  public static boolean lanjut(String quote){
    Scanner terminput = new Scanner(System.in);
    System.out.println(quote);
    String pilihannya = terminput.next();
    while(!pilihannya.equalsIgnoreCase("Y")&&!pilihannya.equalsIgnoreCase("N")){
      System.err.println("inputanmu salah coba lagi");
      System.out.println(quote);
      pilihannya = terminput.next();
    }
    return pilihannya.equalsIgnoreCase("Y");
  }
  
  //mencari data
  public static void caridata()throws IOException{
    //mengecek data
    try{
      File cekdat = new File("input.txt");
    }
    catch(Exception e){
      System.err.println("database tidak ditemukan\nbuat database baru!");
      adddata();
    }
    
    //menanyakan data dari client
    Scanner cari = new Scanner(System.in);
    System.out.println("masukan kata kunci yang ingin di cari : ");
    String inputcari = cari.nextLine();
    
    String[] pencarian = inputcari.split("\\s");
    
    //mengecek keberadaan data
    cekkeyworddata(pencarian,true);
  }
  
  //mengecek keberadaan keyword
  public static boolean cekkeyworddata(String[] katakunci,boolean tampilkan)throws IOException{
    FileReader file = new FileReader("input.txt");
    BufferedReader buff = new BufferedReader(file);
    boolean exist = false;
    int jml = 0;
    String tampildata = buff.readLine();
    if(tampilkan){
      System.out.println("___________________________________________________________________________");
      System.out.println("| No | Thn Lahir |              Nama             | Kelas | Absen | Alamat");
      System.out.println("---------------------------------------------------------------------------");
      
    }
    
    
    while(tampildata != null){
      
      //cek keyword di baris
      exist = true;
      //System.out.println(tampildata);
      //System.out.println(Arrays.toString(katakunci));
        
      for(String iterasi:katakunci){
        exist = exist && tampildata.toLowerCase().contains(iterasi.toLowerCase());
      }
      //System.out.println(exist);
      //menampilkan hasil pencarian data
      if(exist){
        if(tampilkan){
          jml++;
          StringTokenizer Strinc = new StringTokenizer(tampildata,",");
          
          System.out.printf("|%2d  ",jml);
          System.out.printf("| %-10s",Strinc.nextToken());
          System.out.printf("| %-30s",Strinc.nextToken());
          System.out.printf("| %-6s",Strinc.nextToken());
          System.out.printf("| %-6s",Strinc.nextToken());
          System.out.printf("| %-20s\n",Strinc.nextToken());
          
      }else{
        break;
      }
      
        }
      tampildata = buff.readLine();
    }
    if(tampilkan){
    System.out.println("___________________________________________________________________________");
    }
    return exist;
  }
  
  //menambah data
  public static void adddata()throws IOException{
    FileWriter tulisdata = new FileWriter("input.txt",true);
    BufferedWriter bufferdata = new BufferedWriter(tulisdata);
    
    String nama,tahun,kelas,absen,alamat; 
    
    Scanner input = new Scanner(System.in);
    System.out.print("masukan nama siswa : ");
    nama = input.nextLine();
    System.out.print("masukan tahun lahir siswa (YYYY) : ");
    tahun = tahun_lahir();
    System.out.print("masukan kelas siswa : ");
    kelas = input.nextLine();
    System.out.print("masukan absen siswa : ");
    absen = input.nextLine();
    System.out.print("masukan alamat siswa : ");
    alamat = input.nextLine();
    //menampilkan hasil cekdata
    String[] keyword = {tahun+","+nama+","+kelas+","+absen+","+alamat};
    
    //
    boolean adakah = cekkeyworddata(keyword,false);
    
    //menulis data ke database
    if(!adakah){
      System.out.printf("\ntahun lahir = %s\nnama siswa = %s\nkelas siswa = %s\nabsen siswa = %s\nalamat siswa = %s\n",tahun,nama,kelas,absen,alamat);
      boolean apakahtambah = lanjut("\napakah anda yakin ingin menambah data tersebut?");
      if(apakahtambah){
        bufferdata.write(tahun+","+nama+","+kelas+","+absen+","+alamat);
        bufferdata.newLine();
        bufferdata.flush();
      }
      
    }else{
      System.out.println("hasil data yang anda masukan sudah ada di database dengan hasil berikut : ");
      cekkeyworddata(keyword,true);
    }
    
    bufferdata.close();
  }
  
  //method untuk memberi input tahun_lahir dalam fungsi menambah data
  public static String tahun_lahir(){
    String tahunlahir;
    boolean validasitahun = false;
    Scanner inputThn = new Scanner(System.in);
    
    tahunlahir = inputThn.nextLine();
    while(!validasitahun){
      try{
        Year.parse(tahunlahir);
        validasitahun = true;
        
      }
      catch(Exception e){
        System.out.println("format tahun salah, masukan dengan format (YYYY)");
        System.out.print("masukan lagi tahun lahir siswa : ");
        tahunlahir = inputThn.nextLine();
        
      }
    }
    return tahunlahir;
    
  }
  
  public static void deletedata()throws IOException{
    //mengambil database awal
    File dataawal = new File("input.txt");
    FileReader datainput = new FileReader(dataawal);
    BufferedReader buffinput = new BufferedReader(datainput);
    //menyalin database awal ke database sementara
    File filesementara = new File("datasementara.txt");
    FileWriter outsem = new FileWriter(filesementara);
    BufferedWriter buffsem = new BufferedWriter(outsem);
    //menampilkan database awal
    readdata();
    //mengambil user input untuk menghapus data
    Scanner input = new Scanner(System.in);
    System.out.print("Silahkan masukan nomor yang akan di hapus : ");
    int nomor = input.nextInt();

    //looping untuk membaca data per baris dan skip data yang di delete oleh user input
    boolean hapus = false;
    int penghitung = 0;
    String data = buffinput.readLine();
    while(data != null){
      penghitung++;
      hapus = false;
      StringTokenizer komponen = new StringTokenizer(data,",");
      if(penghitung == nomor){
        System.out.println("\nberikut adalah data yang akan anda hapus");
        System.out.println("Tahun Lahir      = "+komponen.nextToken());
        System.out.println("Nama             = "+komponen.nextToken());
        System.out.println("Kelas            = "+komponen.nextToken());
        System.out.println("No Absen         = "+komponen.nextToken());
        System.out.println("Alamat           = "+komponen.nextToken());
        
        hapus = lanjut("\napakah anda yakin akan menghapus data tersebut? (y/n)");
      }
      if(hapus){
        System.out.println("data nomor "+nomor+" berhasil dihapus!");
      }else{
        buffsem.write(data);
        buffsem.newLine();
      }
      data = buffinput.readLine();
    }
    //menulis data ke data sementara
    buffsem.flush();
    //menghapus data awal
    dataawal.delete();
    //mengubah database sementara ke data inti dengan mengganti nama
    filesementara.renameTo(new File("input.txt"));
  }
  public static void changedata()throws IOException{
    //mengambil database awal
    File dataawal = new File("input.txt");
    FileReader datainput = new FileReader(dataawal);
    BufferedReader buffinput = new BufferedReader(datainput);
    //menyalin database awal ke database sementara
    File filesementara = new File("datasementara.txt");
    FileWriter outsem = new FileWriter(filesementara);
    BufferedWriter buffsem = new BufferedWriter(outsem);
    //menampilkan database awal
    readdata();
    //mengambil user input untuk mengubah data
    Scanner input = new Scanner(System.in);
    System.out.print("Silahkan masukan nomor yang akan di ubah : ");
    int nomor = input.nextInt();

    //looping untuk membaca data per baris dan update data oleh user input
    int penghitung = 0;
    String data = buffinput.readLine();
    while(data != null){
      penghitung++;
      StringTokenizer komponen = new StringTokenizer(data,",");
      //menampilkan komponen data yang dipilih
      if(penghitung == nomor){
        System.out.println("\nberikut adalah data yang akan anda update/ubah");
        
        String Tahun = komponen.nextToken();
        System.out.println("1.Tahun Lahir      = "+Tahun);

        String Nama = komponen.nextToken();
        System.out.println("2.Nama             = "+Nama);

        String Kelas = komponen.nextToken();
        System.out.println("3.Kelas            = "+Kelas);

        String Absen =komponen.nextToken();
        System.out.println("4.No Absen         = "+Absen);

        String Alamat = komponen.nextToken();
        System.out.println("5.Alamat           = "+Alamat);
        
        //mengambil input dari user
        int choose;
        boolean next = true;
        while(next){
        System.out.print("silahkan pilih nomor dari komponen di atas yang akan diubah : ");
        choose = input.nextInt();
          switch(choose){
            case 1:
              System.out.print("masukan tahun yang akan diubah : ");
              Tahun = tahun_lahir();
              break;
            case 2:
              System.out.print("masukan nama yang akan diubah : ");
              Nama = input.next();
              break;
            case 3:
              System.out.print("masukan kelas yang akan diubah : ");
              Kelas = input.next();
              break;
            case 4:
              System.out.print("masukan absen yang akan diubah : ");
              Absen = input.next();
              break;
            case 5:
              Alamat = input.next();
              System.out.print("masukan alamat yang akan diubah : ");
              break;
            default:
              break;
          }
          next = lanjut("apakah anda ingin mengubah komponen data tersebut lagi? (y/n)");
        }
        //mengambil komponen yang diubah dan menampilkan data yang diubah
        String[] updata = {Tahun,Nama,Kelas,Absen,Alamat};
        komponen = new StringTokenizer(data,",");
        System.out.printf("tahun lahir siswa = %-30s -> %-5s\n",komponen.nextToken(),updata[0]);
        System.out.printf("Nama siswa        = %-30s -> %-5s\n",komponen.nextToken(),updata[1]);
        System.out.printf("Kelas siswa       = %-30s -> %-5s\n",komponen.nextToken(),updata[2]);
        System.out.printf("No absen          = %-30s -> %-5s\n",komponen.nextToken(),updata[3]);
        System.out.printf("Alamat siswa      = %-30s -> %-5s\n",komponen.nextToken(),updata[4]);
        //input user untuk melanjutkan perubahan atau tidak
        boolean lanjut = lanjut("apakah anda yakin ingin mengubahnya? (y/n)");
        
        if(lanjut){
          boolean ada = cekkeyworddata(updata,true);
          if(ada){
            System.err.println("data yang anda masukan sudah ada");
          }else{
            //menulis new data ke database
            buffsem.write(Tahun+","+Nama+","+Kelas+","+Absen+","+Alamat);
          }
          
        }else{
        buffsem.write(data);
        }

      }else{
        buffsem.write(data);
      }
      buffsem.newLine();
      data = buffinput.readLine();
    }
    //menulis data ke file database baru
    buffsem.flush();
    //menghapus file database lama
    dataawal.delete();
    //mengganti nama database baru dengan database lama agar program berjalan
    filesementara.renameTo(new File("input.txt"));
  }
}
