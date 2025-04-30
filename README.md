📝 Proje Tanıtımı
NoteApp, modern bir not alma deneyimi sunan, Jetpack Compose ve Material 3 teknolojileri ile geliştirilmiş Android uygulamasıdır. Kullanıcıların günlük yaşamlarında fikirlerini, görevlerini veya anılarını kolayca kaydedebilmelerini sağlar.

Bu uygulama, MVVM (Model-View-ViewModel) mimarisi, Room veritabanı, Hilt bağımlılık enjeksiyonu ve Kotlin Coroutines gibi modern Android geliştirme pratiklerini kullanmaktadır.

✨ Özellikler  
Modern UI/UX: Jetpack Compose ile geliştirilen akıcı ve modern kullanıcı arayüzü  
Not Yönetimi: Not ekleme, düzenleme, silme ve listeleme işlemleri  
Kategorilendirme: Notları farklı kategorilere ayırabilme  
Öncelik Belirleme: Notlara farklı öncelik seviyeleri atayabilme (düşük, orta, yüksek)  
Arama Fonksiyonu: Notlar içinde hızlı arama yapabilme  
Sabitleme: Önemli notları üst kısımda tutabilme  
Görsel Ekleme: Notlara resim ekleyebilme  
Sıralama Seçenekleri: Tarih, başlık ve önceliğe göre notları sıralama  
Tema Desteği: Karanlık ve aydınlık tema arasında geçiş yapabilme  
Otomatik Tarih: Otomatik tarih bilgisi ekleme  

🛠️ Kullanılan Teknolojiler  
Jetpack Compose: Modern UI geliştirme toolkit'i  
Kotlin Coroutines & Flow: Asenkron işlemler için  
Room Database: Yerel veri saklama  
Hilt: Bağımlılık enjeksiyonu  
ViewModel: UI ve veri katmanı arasındaki iletişim  
Material 3: Modern tasarım dili  
Coil: Görsel yükleme kütüphanesi  
Accompanist: Compose için yardımcı kütüphaneler  

📱 Ekran Görüntüleri  
![Ekran görüntüsü 2025-04-30 135746](https://github.com/user-attachments/assets/4f5f36fa-c3c5-43ac-baed-4ff4f5b5e244)
![Ekran görüntüsü 2025-04-30 140037](https://github.com/user-attachments/assets/629b580f-b949-41a6-b325-3ebc71509bdd)
![Ekran görüntüsü 2025-04-30 140018](https://github.com/user-attachments/assets/de01eb5d-1049-4920-8a77-e0554a16ee38)
![Ekran görüntüsü 2025-04-30 135817](https://github.com/user-attachments/assets/7022042b-ad72-496c-b9d1-8d236d394e27)  

🏗️ Mimari Yapı  
Bu proje, temiz kod ve sürdürülebilirlik prensipleri gözetilerek MVVM mimarisi ile tasarlanmıştır:  

Model: Room veritabanı ve Entity sınıfları (NoteEntity)  
View: Compose UI bileşenleri (NoteListScreen, NoteAddScreen, NoteEditScreen)  
ViewModel: UI mantığı ve veri işlemleri (NoteViewModel)  
Repository: Veri erişim katmanı (NoteRepository)  
