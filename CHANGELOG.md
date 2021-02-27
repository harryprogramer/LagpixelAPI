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
