# PROTOCOL 0.9 SENSORS UPDATE

**Zmieniono składnie jsona:**
- od teraz 'code' został zamieniony na 'id'

**Dodano nowe funkcje:**
- sprawdzanie aktualnej temperatury
- sprawdzanie temperatury z calego dnia, umozliwienie robienie wykresow

**Wątki:**
- dodano dwa nowe niezalezne wątki obsługujące obliczanie temperataury procesora

**API:**
- Dodano nowy interfejs obsługujący dane z czujników w komputerze SystemInfo, implementacja SystemInfoImpl, factory, SystemInfoAPI (BETA)

**Naprawione Bugi:**
- od teraz Logger.createDumpCore() drukuje poprawne dane

**Inne:**
- zmieniono nazwe z LagpixelAPI na LWLAPI_08
- ta aktualizacja przygotuje api do wersji 1.0

---

# PROTOCOL 0.9.2 patch 

**Naprawiono:**
- nie czyszczący sie bufor podczas zbierania danych telemetrycznych z calego dnia
- naprawiono petle loop zbierająca dane telemtryczne nie zawierająca zwiekszania zmiennej i++;
- naprawiono nie aktualizujące się dane o procesorze  