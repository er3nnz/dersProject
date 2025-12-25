Bu proje için PostgreSQL içeren Docker Compose konfigürasyonu eklendi.

Docker Compose ile ayağa kaldırma (project kökünde):

```bash
# Arka planda db servisini başlatır
docker-compose up -d

# Tüm servisleri durdurup kaldırmak için
docker-compose down -v
```

Bağlanma örneği (psql veya uygulama):

- Host (uygulama içinde): db
- Host (lokalde psql ile bağlanmak için): localhost
- Port: 5432
- Kullanıcı: demo_user
- Şifre: demo_pass
- DB: demo_db

Notlar:
- `docker-compose up` sonrası DB'nin ayağa kalkması birkaç saniye sürebilir; uygulamayı compose ile birlikte çalıştıracaksanız uygulama servisinin `depends_on` veya healthcheck ile DB hazır olana kadar beklemesi gerekir.
