
# 🐾 Übung: "Pet Passport Service" – Dein digitales Haustier-Reisepass-Register

## Ziel
Erstelle eine **Spring Boot REST API**, mit der Haustierbesitzer ihre Tiere in einem digitalen **Pet Passport Register** eintragen können. Jeder Reisepass enthält Angaben zum Tier, zu Impfungen und zum Besitzer.  
Die Daten sollen in einer **H2-Datenbank** gespeichert werden (optional MariaDB-Anbindung).

---

## Dependencies
- Spring Web (REST Controller)
- Spring Data JPA
- H2 Database (optional MariaDB)
- Validation (`spring-boot-starter-validation`)

---

## Requirements

### 1. Entities / Datenmodell
Du benötigst mindestens **zwei Entities**:

1. **Pet**
    - `id` (Long, auto-generiert)
    - `name` (String, Pflicht, min. 2 Zeichen)
    - `species` (String, nur `"Dog"`, `"Cat"`, `"Parrot"` erlaubt)
    - `birthDate` (LocalDate, Pflicht, muss in der Vergangenheit liegen)
    - Relation: *viele Pets gehören zu einem Owner*

2. **Owner**
    - `id` (Long, auto-generiert)
    - `fullName` (String, Pflicht, min. 3 Zeichen)
    - `email` (String, Pflicht, muss gültige E-Mail sein)
    - `phoneNumber` (String, optional, Regex für internationale Nummern)

---

### 2. REST Endpoints

| HTTP Methode | Pfad | Beschreibung |
|--------------|------|--------------|
| `POST` | `/api/pets` | Neuen Pet-Eintrag inkl. Owner anlegen |
| `GET` | `/api/pets` | Liste aller registrierten Tiere |
| `GET` | `/api/pets/{id}` | Details eines bestimmten Tieres abrufen |
| `PUT` | `/api/pets/{id}` | Pet-Daten (inkl. Owner) aktualisieren |
| `DELETE` | `/api/pets/{id}` | Pet-Eintrag löschen |

💡 **Validation**:
- Bei `POST` und `PUT` muss geprüft werden, ob die Eingaben den Validierungsregeln entsprechen.
- Falls Validierung fehlschlägt, soll eine aussagekräftige Fehlermeldung im JSON-Format zurückgegeben werden.

---

### 3. Persistenz
- Nutze **Spring Data JPA** mit einem Repository pro Entity (`PetRepository`, `OwnerRepository`).
- Standardmäßig **H2-Datenbank** mit automatischer Schemaerstellung.
- Bonus: Konfiguration für **MariaDB** über `application.properties` hinzufügen, sodass ein einfacher Wechsel möglich ist.

---

### 4. Beispiel-Requests

**POST /api/pets**
```json
{
  "name": "Luna",
  "species": "Cat",
  "birthDate": "2019-06-15",
  "owner": {
    "fullName": "Sarah Miller",
    "email": "sarah.miller@example.com",
    "phoneNumber": "+49-172-1234567"
  }
}
```

**Fehlerbeispiel bei Validierung**
```json
{
  "errors": [
    "Pet name must have at least 2 characters",
    "Species must be one of: Dog, Cat, Parrot"
  ]
}
```

---

### 5. Bonus-Aufgaben
- **Such-Endpoint**: `/api/pets/search?species=Cat&owner=Sarah`
- **Pagination & Sorting** für die Liste aller Tiere


