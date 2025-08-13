# 📝 Erweiterung: Guestbook API mit H2-Datenbank & Kommentaren

## Ziel
Erweitere die bestehende **Guestbook REST API** so, dass:
1. Eine **H2-Datenbank** zur Speicherung der Einträge genutzt wird.
2. Zu jedem **GuestbookEntry** mehrere **Comments** gespeichert werden können (Beziehung: `OneToMany`).

---

## Anforderungen

### 1. Datenbank-Setup
- Füge im `pom.xml` die Abhängigkeit zu **H2 Database** hinzu:
    ```xml
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    ```
- Konfiguriere in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:h2:mem:guestbookdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    spring.h2.console.enabled=true
    spring.jpa.hibernate.ddl-auto=update
    ```

---

### 2. Datenmodell

#### `GuestbookEntry`-Entity
- Felder:
    - `id` (Long, Primary Key, auto-generiert)
    - `name` (String)
    - `message` (String)
    - `date` (LocalDateTime)
- Beziehung:
    - `List<Comment> comments` (OneToMany-Beziehung)

#### `Comment`-Entity
- Felder:
    - `id` (Long, Primary Key, auto-generiert)
    - `author` (String)
    - `text` (String)
    - `date` (LocalDateTime)
- Beziehung:
    - Viele Comments gehören zu einem `GuestbookEntry` (`ManyToOne`).

---

### 3. Repository-Schicht
- Erstelle ein `GuestbookEntryRepository` (erbt von `JpaRepository<GuestbookEntry, Long>`).
- Erstelle ein `CommentRepository` (erbt von `JpaRepository<Comment, Long>`).

---

### 4. Service-Schicht
- `GuestbookService`:
    - Methode `addEntry(String name, String message)` → speichert Eintrag in DB.
    - Methode `getAllEntries()` → liest alle Einträge inkl. Kommentare.
    - Methode `clearEntries()` → löscht alle Einträge.

- `CommentService`:
    - Methode `addComment(Long entryId, String author, String text)` → fügt Kommentar zu Eintrag hinzu.

---

### 5. REST-Controller
- **POST** `/guestbook` → Neuen Eintrag speichern.
- **GET** `/guestbook` → Alle Einträge inkl. Kommentare abrufen.
- **DELETE** `/guestbook` → Alle Einträge löschen.
- **POST** `/guestbook/{id}/comments` → Kommentar zu Eintrag hinzufügen.

---

### 6. Validierung
- Name & Nachricht im Guestbook dürfen nicht leer sein.
- Kommentar-Author darf nicht leer sein.
- Kommentar-Text muss mindestens 3 Zeichen lang sein.

---

### 7. Testen
- Nutze das **H2 Console UI** unter `http://localhost:8080/h2-console` um die Datenbankinhalte zu prüfen.
- Teste mit `curl`, Postman oder IntelliJ HTTP-Client.
