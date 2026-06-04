[[0 - Index]]
In tutta la gestione moderna di date, orari e formattazioni in Java, **non si usa (quasi) mai la parola chiave `new`**. Gli oggetti vengono costruiti indirettamente tramite metodi _Factory_ statici.
- Nomi tipici dei metodi: `of(...)`, `now()`, `getInstance()`, `from(...)`.
### Cultura Locale (`java.util.Locale`)
La cultura locale definisce le regole di formattazione (lingua e paese).
- **Costanti predefinite:** `Locale.ITALY`, `Locale.US`, `Locale.UK`
### Formattatori Numerici (`java.text.NumberFormat`)
Si usano per numeri, percentuali e valute.

**Creazione (Factory Methods):**
- Numeri: `NumberFormat.getNumberInstance(locale)`
- Percentuali: `NumberFormat.getPercentInstance(locale)`
- Valute: `NumberFormat.getCurrencyInstance(locale)`

**Utilizzo Pratico:**
- **Formattare (Output):** `formatter.format(valore)`.
- **Parsing (Input):** `formatter.parse(stringa)` restituisce un oggetto `Number`. Lo estrai con `.doubleValue()`, `.intValue()`, ecc..

**Pattern Personalizzati (`DecimalFormat`):**
Se il default non va bene, puoi usare (qui sì!) il costruttore:
`DecimalFormat f = new DecimalFormat("¤ #,##0.##; -¤ #,##0.##");` f è gia un formattatore
- `¤`: Simbolo valuta
- `#`: Cifra generica (può mancare)
- `0`: Cifra obbligatoria (mette gli zeri davanti)
- Il punto e la virgola nel pattern anglosassone verranno poi sostituiti in automatico dai separatori della cultura locale (es. per l'Italia la `,` diventerà `.` e viceversa).
```java
import java.text.DecimalFormat;

// Il pattern definisce: Positivi ; Negativi
// ¤ = Simbolo valuta, # = cifra opzionale, 0 = cifra obbligatoria
Locale.setDefault(Locale.ITALY); // non dovrebbe servire
String pattern = "¤ #,##0.00; -¤ #,##0.00";
DecimalFormat valutaCustom = new DecimalFormat(pattern);

double prezzo = 1234.5;
double debito = -50.75;

System.out.println(valutaCustom.format(prezzo)); // Output: € 1.234,50
System.out.println(valutaCustom.format(debito)); // Output: -€ 50,75

prezzo = Formatters.valutaCustom.parse("€ 1.234,50").doubleValue();
```

> [!attention]
> I formattatori generano eccezioni 
Per sapere quali basta fermarsi col mouse sul metodo e premere F2 o fn+F2
### 4. Date e Orari (`java.time`)
Tutti gli oggetti temporali sono **immutabili**. Qualsiasi modifica crea un _nuovo_ oggetto, non altera quello originale!

|**Classe**|**Cosa modella**|**Esempio Costruzione**|
|---|---|---|
|**LocalDate**|Data locale (giorno, mese, anno)|`LocalDate.of(2026, 3, 4)` o `LocalDate.now()`|
|**LocalTime**|Orario locale (ore, min, sec)|`LocalTime.of(12, 0)` o `LocalTime.now()`|
|**LocalDateTime**|Data + Orario|`LocalDateTime.of(data, orario)`|
|**Instant**|Punto fisico assoluto nel tempo|`Instant.now()`|
|**ZonedDateTime**|Data + Orario + Fuso orario|`ZonedDateTime.of(ldt, ZoneId.of("CET"))`|
**Aritmetica e Manipolazione (Fluent Interface):**
- **Aggiungere:** `.plusDays()`, `.plusMonths()`, `.plusHours()`
- **Sottrarre:** `.minusDays()`, `.minusYears()`
- **Sostituire/Impostare:** `.withYear()`, `.withMinute()`
- **Confrontare:** `.isBefore()`, `.isAfter()`, `.isEqual()`
**Durate e Periodi:**
- **Period:** Intervallo concettuale in anni/mesi/giorni. Creazione: `Period.between(data1, data2)` o `Period.ofMonths(2)`.
- **Duration:** Intervallo esatto in secondi/nanosecondi. Creazione: `Duration.between(tempo1, tempo2)`.
```java
import java.time.*;
import java.time.format.*;
import java.util.Locale;

// 1. Costruisci i pezzi base
LocalDate dataVolo = LocalDate.of(2026, Month.MARCH, 25);
LocalTime oraVolo = LocalTime.of(14, 30);

// 2. Unisci tutto con il fuso orario di partenza (es. Roma)
ZonedDateTime partenza = ZonedDateTime.of(dataVolo, oraVolo, ZoneId.of("Europe/Rome"));

// 3. Formattatore FULL per vedere tutti i dettagli
DateTimeFormatter fmtVolo = DateTimeFormatter
    .ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.LONG)
    .withLocale(Locale.ITALY);

System.out.println(partenza.format(fmtVolo)); 
// Output: mercoledì 25 marzo 2026, 14:30:00 CET
```
### Formattazione Date e Orari `java.time.format.DateTimeFormatter`
La classe principale è `DateTimeFormatter`. Anche qui si usano i Factory Methods.
**Stili predefiniti (`FormatStyle`):** Ci sono 4 stili: `SHORT`, `MEDIUM`, `LONG`, `FULL`.
- **Date:** Accettano tutti e 4 gli stili.
- **Orari:** Accettano SOLO `SHORT` e `MEDIUM`.

**Creare e Configurare il Formatter:**
```java
// Formattatore misto: Data estesa, orario breve
var fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
                           .withLocale(Locale.ITALY); // Specifica la lingua
```
_(Nota: `.withLocale()` si applica_ dopo aver creato il formatter ).

**Pattern Personalizzati:**
Se ti serve ad esempio l'anno su 4 cifre nel formato corto (cosa non supportata di default), crea un pattern:
`DateTimeFormatter.ofPattern("dd/MM/yyyy")`.
- `y` (anno), `M` (mese come numero/testo), `d` (giorno del mese), `H` (ora 0-23), `m` (minuto), `s` (secondo).

**Parsing Sicuro di Date/Orari:** Esistono due approcci, ma usa sempre il **Secondo Approccio (più comodo e sicuro)** raccomandato dalle slide:
```java
// MODO OTTIMALE: Chiedere alla classe di destinazione di fare il parsing 
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
LocalDate data = LocalDate.parse("12/02/2026", fmt);
```
(Evita il "primo approccio" `fmt.parse("...")` perché restituisce un generico `TemporalAccessor` che va poi faticosamente convertito tramite i metodi `from(...)` ).

**Creare e Formattare Date e Orari Assoluti (Fusi Orari)**
```java
import java.time.*;
import java.time.format.*;
import java.util.Locale;

// 1. Costruisci i pezzi base
LocalDate dataVolo = LocalDate.of(2026, Month.MARCH, 25);
LocalTime oraVolo = LocalTime.of(14, 30);

// 2. Unisci tutto con il fuso orario di partenza (es. Roma)
ZonedDateTime partenza = ZonedDateTime.of(dataVolo, oraVolo, ZoneId.of("Europe/Rome"));

// 3. Formattatore FULL per vedere tutti i dettagli
DateTimeFormatter fmtVolo = DateTimeFormatter
    .ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.LONG)
    .withLocale(Locale.ITALY);

System.out.println(partenza.format(fmtVolo)); 
// Output: mercoledì 25 marzo 2026, 14:30:00 CET
```

**Giorno Bisestile**
```java
import java.time.*;

// La notte del cambio dell'ora legale in Europa (le 02:00 diventano le 03:00)
ZonedDateTime primaDelCambio = ZonedDateTime.of(
    2026, 3, 28, 12, 0, 0, 0, ZoneId.of("Europe/Rome")
);

// Aggiungiamo 1 giorno logico (Period) e 24 ore fisiche (Duration)
ZonedDateTime domaniLogico = primaDelCambio.plus(Period.ofDays(1));
ZonedDateTime domaniFisico = primaDelCambio.plus(Duration.ofHours(24));

System.out.println("Domani alla stessa ora (Period): " + domaniLogico); 
// Output: 2026-03-29T12:00+02:00[Europe/Rome] -> Sono sempre le 12:00!

System.out.println("24 ore fisiche esatte dopo (Duration): " + domaniFisico); 
// Output: 2026-03-29T13:00+02:00[Europe/Rome] -> Sono diventate le 13:00 per via del salto!
```

**Parsing "Comodo e Sicuro" di Stringhe in Date**
```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// Stringa in input fornita dall'utente o da un file
String inputData = "15-ottobre-2026";

// Creiamo un pattern esatto:
// dd = giorno a due cifre, MMMM = mese testuale per esteso, yyyy = anno a 4 cifre
DateTimeFormatter parserCustom = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ITALY);

// Approccio SICURO: chiediamo a LocalDate di fare il lavoro
LocalDate dataConvertita = LocalDate.parse(inputData, parserCustom);

System.out.println("Data parsata con successo: " + dataConvertita);
// Output: 2026-10-15
```

**Duration e period**
```java
import java.time.LocalDate;
import java.time.Period;

LocalDate oggi = LocalDate.now();
LocalDate compleanno = LocalDate.of(2000, 11, 20); // 20 Novembre

// Spostiamo l'anno del compleanno all'anno corrente
LocalDate prossimoCompleanno = compleanno.withYear(oggi.getYear());

// Se quest'anno è già passato, aggiungiamo 1 anno
if (prossimoCompleanno.isBefore(oggi) || prossimoCompleanno.isEqual(oggi)) {
    prossimoCompleanno = prossimoCompleanno.plusYears(1);
}

Period quantoManca = Period.between(oggi, prossimoCompleanno);

System.out.println("Mancano: " + quantoManca.getMonths() + " mesi e " + quantoManca.getDays() + " giorni.");
```