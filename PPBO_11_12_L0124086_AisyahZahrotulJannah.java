import java.util.*;

interface Penilaian {
    double getBobotNilai();
    String getGrade();
}

abstract class MataKuliah implements Penilaian {
    protected String namaMataKuliah;
    protected int sks;
    protected double nilaiAkhir;

    public MataKuliah(String namaMataKuliah, int sks, double nilaiAkhir) {
        this.namaMataKuliah = namaMataKuliah;
        this.sks = sks;
        this.nilaiAkhir = nilaiAkhir;
    }

    public abstract double hitungNilaiAkhir();

    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }

    public int getSks() {
        return sks;
    }

    public double getNilaiAkhir() {
        return nilaiAkhir;
    }

    @Override
    public String getGrade() {
        double nilaiAkhir = hitungNilaiAkhir();
        if (nilaiAkhir >= 85) return "A";
        else if (nilaiAkhir >= 70) return "B";
        else if (nilaiAkhir >= 60) return "C";
        else if (nilaiAkhir >= 50) return "D";
        else return "E";
    }
}

class PemrogramanBerorientasiObjek extends MataKuliah {
    public PemrogramanBerorientasiObjek(double nilai) {
        super("Pemrograman Berorientasi Objek", 3, nilai);
    }

    @Override
    public double hitungNilaiAkhir() {
        return nilaiAkhir * 0.4 + nilaiAkhir * 0.6;
    }

    @Override
    public double getBobotNilai() {
        return hitungNilaiAkhir() * sks;
    }
}

class BasisData extends MataKuliah {
    public BasisData(double nilai) {
        super("Basis Data", 4, nilai);
    }

    @Override
    public double hitungNilaiAkhir() {
        return nilaiAkhir * 0.3 + nilaiAkhir * 0.3 + nilaiAkhir * 0.4;
    }

    @Override
    public double getBobotNilai() {
        return hitungNilaiAkhir() * sks;
    }
}

class SistemOperasi extends MataKuliah {
    public SistemOperasi(double nilai) {
        super("Sistem Operasi", 3, nilai);
    }

    @Override
    public double hitungNilaiAkhir() {
        return nilaiAkhir * 0.2 + nilaiAkhir * 0.3 + nilaiAkhir * 0.3;
    }

    @Override
    public double getBobotNilai() {
        return hitungNilaiAkhir() * sks;
    }
}

class MetodeNumerik extends MataKuliah {
    public MetodeNumerik(double nilai) {
        super("Metode Numerik", 3, nilai);
    }

    @Override
    public double hitungNilaiAkhir() {
        return nilaiAkhir * 0.25 + nilaiAkhir * 0.3 + nilaiAkhir * 0.45;
    }

    @Override
    public double getBobotNilai() {
        return hitungNilaiAkhir() * sks;
    }
}

class DesainAnalisisAlgoritma extends MataKuliah {
    public DesainAnalisisAlgoritma(double nilai) {
        super("Desain dan Analisis Algoritma", 3, nilai);
    }

    @Override
    public double hitungNilaiAkhir() {
        return nilaiAkhir * 0.35 + nilaiAkhir * 0.25 + nilaiAkhir * 0.4;
    }

    @Override
    public double getBobotNilai() {
        return hitungNilaiAkhir() * sks;
    }
}

class Mahasiswa {
    private String nama;
    private List<MataKuliah> mataKuliahList;
    private double nilaiAkhir;
    private int totalSks;
    
    public Mahasiswa(String nama) {
        this.nama = nama;
        this.mataKuliahList = new ArrayList<>();
    }
    
    public void tambahMataKuliah(MataKuliah mataKuliah) {
        mataKuliahList.add(mataKuliah);
    }
    
    public void hitungNilaiAkhir() {
        double totalNilai = 0;
        totalSks = 0;
        
        for (MataKuliah mk : mataKuliahList) {
            totalNilai += mk.getBobotNilai();
            totalSks += mk.getSks();
        }
        
        nilaiAkhir = totalSks > 0 ? totalNilai / totalSks : 0;
    }
    
    public String getNama() {
        return nama;
    }
    
    public double getNilaiAkhir() {
        return nilaiAkhir;
    }
    
    public int getTotalSks() {
        return totalSks;
    }
    
    public String getGrade() {
        if (nilaiAkhir >= 85) return "A";
        else if (nilaiAkhir >= 70) return "B";
        else if (nilaiAkhir >= 60) return "C";
        else if (nilaiAkhir >= 50) return "D";
        else return "E";
    }
    
    public List<MataKuliah> getMataKuliahList() {
        return mataKuliahList;
    }
    
    public String getMataKuliahString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mataKuliahList.size(); i++) {
            sb.append(mataKuliahList.get(i).getNamaMataKuliah());
            if (i < mataKuliahList.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}

class HitungNilaiThread implements Runnable {
    private Mahasiswa mahasiswa;
    
    public HitungNilaiThread(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    
    @Override
    public void run() {
        mahasiswa.hitungNilaiAkhir();
        System.out.printf("[%s] %s = Total SKS: %d, Nilai Akhir: %.2f, Grade: %s%n",
            Thread.currentThread().getName(),
            mahasiswa.getNama(),
            mahasiswa.getTotalSks(),
            mahasiswa.getNilaiAkhir(),
            mahasiswa.getGrade());
    }
}

public class PPBO_11_12_L0124086_AisyahZahrotulJannah {
    public static void main(String[] args) {
        List<Mahasiswa> daftarMahasiswa = buatDataMahasiswa();

        System.out.println("=".repeat(80));
        System.out.println("SISTEM PENILAIAN MAHASISWA");
        System.out.println("=".repeat(80));
        System.out.println();

        long waktuSingleThread = prosesSingleThread(daftarMahasiswa);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println();

        daftarMahasiswa = buatDataMahasiswa();

        long waktuMultithread = prosesMultithread(daftarMahasiswa);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("PERBANDINGAN WAKTU EKSEKUSI");
        System.out.println("=".repeat(80));
        System.out.printf("Waktu Eksekusi Single-thread  : %d ms%n", waktuSingleThread);
        System.out.printf("Waktu Eksekusi Multithreading : %d ms%n", waktuMultithread);
        System.out.println("-".repeat(80));
        
        if (waktuSingleThread > waktuMultithread) {
            double selisih = waktuSingleThread - waktuMultithread;
            double persentase = (selisih / waktuSingleThread) * 100;
            System.out.printf("Multithreading lebih cepat %.0f ms (%.2f%% lebih efisien)%n", 
                selisih, persentase);
        } else if (waktuMultithread > waktuSingleThread) {
            double selisih = waktuMultithread - waktuSingleThread;
            System.out.printf("Single-thread lebih cepat %.0f ms%n", selisih);
        } else {
            System.out.println("Kedua metode memiliki waktu eksekusi yang sama");
        }
        
        System.out.println();
        System.out.println("KESIMPULAN:");
        System.out.println("Multithreading memungkinkan perhitungan nilai mahasiswa dilakukan secara");
        System.out.println("paralel, sehingga dapat meningkatkan efisiensi terutama saat memproses");
        System.out.println("data dalam jumlah besar. Setiap mahasiswa diproses oleh thread terpisah,");
        System.out.println("memaksimalkan penggunaan CPU multi-core.");
        System.out.println("=".repeat(80));
    }

    private static List<Mahasiswa> buatDataMahasiswa() {
        List<Mahasiswa> daftarMahasiswa = new ArrayList<>();

        Mahasiswa mhs1 = new Mahasiswa("Faatikah Hidayat");
        mhs1.tambahMataKuliah(new PemrogramanBerorientasiObjek(85));
        mhs1.tambahMataKuliah(new BasisData(78));
        mhs1.tambahMataKuliah(new SistemOperasi(90));
        daftarMahasiswa.add(mhs1);

        Mahasiswa mhs2 = new Mahasiswa("Armania Sailendra");
        mhs2.tambahMataKuliah(new DesainAnalisisAlgoritma(75));
        mhs2.tambahMataKuliah(new SistemOperasi(82));
        mhs2.tambahMataKuliah(new PemrogramanBerorientasiObjek(88));
        mhs2.tambahMataKuliah(new BasisData(80));
        daftarMahasiswa.add(mhs2);
        
        Mahasiswa mhs3 = new Mahasiswa("Reno Samira");
        mhs3.tambahMataKuliah(new DesainAnalisisAlgoritma(92));
        mhs3.tambahMataKuliah(new MetodeNumerik(87));
        mhs3.tambahMataKuliah(new SistemOperasi(85));
        daftarMahasiswa.add(mhs3);
        
        Mahasiswa mhs4 = new Mahasiswa("Afandi Banyumurni");
        mhs4.tambahMataKuliah(new BasisData(70));
        mhs4.tambahMataKuliah(new PemrogramanBerorientasiObjek(72));
        mhs4.tambahMataKuliah(new MetodeNumerik(68));
        mhs4.tambahMataKuliah(new DesainAnalisisAlgoritma(75));
        daftarMahasiswa.add(mhs4);
        
        Mahasiswa mhs5 = new Mahasiswa("Fasyinda Angel");
        mhs5.tambahMataKuliah(new SistemOperasi(88));
        mhs5.tambahMataKuliah(new DesainAnalisisAlgoritma(91));
        mhs5.tambahMataKuliah(new PemrogramanBerorientasiObjek(86));
        daftarMahasiswa.add(mhs5);
        
        return daftarMahasiswa;
    }    

    private static long prosesSingleThread(List<Mahasiswa> daftarMahasiswa) {
        System.out.println("MODE SINGLE-THREAD");
        System.out.println("=".repeat(80));
        
        long startTime = System.currentTimeMillis();
        
        for (Mahasiswa mhs : daftarMahasiswa) {
            mhs.hitungNilaiAkhir();
            
            System.out.println("[Single Thread]");
            System.out.println("Nama          : " + mhs.getNama());
            System.out.println("Mata Kuliah   : " + mhs.getMataKuliahString());
            System.out.println("Total SKS     : " + mhs.getTotalSks());
            System.out.printf("Nilai Akhir   : %.2f%n", mhs.getNilaiAkhir());
            System.out.println("Grade         : " + mhs.getGrade());
            System.out.println("-".repeat(80));
        }
        
        long endTime = System.currentTimeMillis();
        long waktuEksekusi = endTime - startTime;
        
        System.out.printf("Waktu Eksekusi Single-thread: %d ms%n", waktuEksekusi);
        
        return waktuEksekusi;
    }
    
    private static long prosesMultithread(List<Mahasiswa> daftarMahasiswa) {
        System.out.println("MODE MULTITHREADING");
        System.out.println("=".repeat(80));
        
        long startTime = System.currentTimeMillis();
        
        List<Thread> threads = new ArrayList<>();
        
        for (Mahasiswa mhs : daftarMahasiswa) {
            Thread thread = new Thread(new HitungNilaiThread(mhs));
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long endTime = System.currentTimeMillis();
        long waktuEksekusi = endTime - startTime;
        
        System.out.println("-".repeat(80));
        System.out.printf("Waktu Eksekusi Multithreading: %d ms%n", waktuEksekusi);
        
        return waktuEksekusi;
    }
}