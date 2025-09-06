# Kütüphane Yönetim Sistemi (Java OOP Projesi)

## Proje Hakkında

Bu proje, Java programlama dili kullanılarak geliştirilmiş basit ve işlevsel bir **Kütüphane Yönetim Sistemi** uygulamasıdır. Konsol yerine Swing tabanlı sade ve kullanıcı dostu bir GUI ile tüm işlemler gerçekleştirilmektedir.

## Özellikler

- **Üye Kayıt ve Giriş:**
  - E-posta ve şifre ile kayıt olma.
  - Formatta yanlış veya eksik bilgi girildiğinde kullanıcı bilgilendirilir.
  - Yeni üyelikler admin onayına tabidir.
  - Sadece admin onayladıktan sonra üyeler giriş yapabilir.

- **Admin Paneli:**
  - Admin "admin@kutuphane.com" e-posta ve "admin1234" şifre ile giriş yapar.
  - Onay bekleyen üyeleri görüntüleme, onaylama ve reddetme.
  - Onaylanan üyeler sisteme giriş yapabilir.

- **Kitap Yönetimi:**
  - Sistemde 10 farklı kitap ve stokları mevcuttur.
  - Üyeler kitap ödünç alabilir ve iade edebilir.
  - Kitap stokları ödünç alma ve iade işlemlerine göre otomatik güncellenir.
  - Ödünç alınmayan kitap iade edilemez.
  - Tüm işlemler kullanıcıya bildirimle sunulur.

## Kullanılan Teknolojiler

- Java SE 8+
- Swing GUI bileşenleri
- Nesne Yönelimli Programlama (OOP) prensipleri: soyutlama, miras alma, metot ezme

## Proje Dosyaları

- **Book.java** — Kitap sınıfı, kitap bilgileri ve stok yönetimi.
- **User.java** — Soyut kullanıcı sınıfı.
- **Member.java** — Üye sınıfı, kullanıcı onay durumu ve ödünç aldığı kitaplar.
- **Admin.java** — Admin kullanıcı sınıfı.
- **Library.java** — Kitap koleksiyonu ve kitap arama işlemleri.
- **LibraryPanel.java** — Üye kullanıcıları için kitap ödünç alma/iade GUI paneli.
- **MainFrame.java** — Uygulamanın ana çerçevesi, giriş ve admin panelleri.

## Nasıl Çalıştırılır?

1. Java 8 veya üstü yüklü olmalıdır.
2. Tüm `.java` dosyalarını aynı klasöre kaydedin.
3. Terminal veya komut satırından klasöre gidin.
4. Derleyin:
   ```bash
   javac *.java
