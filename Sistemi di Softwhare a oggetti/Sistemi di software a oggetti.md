
## il Deployment del softwhare
L'eseguibile non è un file unico ma un **archivio zippato .jar** (non da estrarre) che contiene
- Per **librerie**, bastano le classi (file .class)
- Per **applicazioni**, occorre anche specificare la posizione del main (in un file di testo)
![[103-Deployment.pdf#page=4&rect=540,208,703,431|103-Deployment, p.4|150]]
Per creare un file JAR come archivio di classi (zip) `jar cf nomearchivio.jar classi`
- un elenco di classi (.class) da includerem DAFINIRE

Per creare un jar eseguibile
`jar cmf info.txt nomeapp.jar classi`dove info.txt contiene "Main-Class: NomeclasseMain"
oppure
`jar cef NomeclasseMain nomeapp.jar classi`

es.
`jar cmf infoApp MyApp.jar Esempio.class CodFisc.class`
`jar cmf infoApp MyApp.jar Esempio.class *.class` (prende tutte e sole le classi contenute nella cartella)

Per eseguire un jar aprendo il terminale `java -jar nomefile.jar` 

Per compilare includendo delle librerie.
![[103-Deployment.pdf#page=14&rect=10,56,325,429|103-Deployment, p.14||200]]
Vedi esempio comoleto








## Tipi di Base
**Boolean** (separato dagli interi): ammette solo `false` e `true` che **non** sono convertibili o equivalenti a 0 e 1.

**Interi**
- **byte** (1 byte): -128 ... +127
- **short** (2 byte): -32768 ... +32767
- **int** (4 byte): -2·10^9 … +2·10^9
- **long** (8 byte): -9·10^18 … +9·10^18 (le costanti necessitano del suffisso `L`)

*Nota errori aritmetici intera:* L'overflow intero non genera errori bloccanti ma "sborda" nei numeri negativi (wrap-around). La **divisione per zero** tra interi genera un'eccezione bloccante `ArithmeticException` (esplode a runtime).

**Float / Numeri Reali** (Seguono lo standard IEEE-754)
- **float** (4 byte): -10^45 ... +10^38 (le costanti necessitano del suffisso `F`)
- **double** (8 byte): -10^324 ... +10^308

*Nota errori aritmetici reali:* La divisione per `0.0` non causa crash ma restituisce `Infinity` o `-Infinity`. Le forme indeterminate (es. `0.0/0.0`) restituiscono `NaN` (Not a Number).

**Regole di Assegnamento e Cast:**
Sono ammessi implicitamente solo gli assegnamenti che non generano una perdita di informazioni.
```java
double x = 3.54F; // Permesso (nessuna perdita)
float f = 3.54;   // ERRORE in compilazione (double in float)
float f2 = (float) 3.54; // Corretto: forzatura tramite CAST esplicito
```

**Caratteri (Unicode/UTF-16)**
Il tipo `char` usa 2 byte (UTF-16) per rappresentare i caratteri (es. `'A'`).
C'è ancora la corrispondenza diretta fra caratteri e numeri come in C:
```java
char ch = 'A';
int x = ch; // x vale 65
ch = 72;
char cStran = '\u2122'; // Notazione esadecimale Unicode
```

### Conversioni Stringa -> Numero (Fondamentale per input CLI)
Spesso gli argomenti `args` (che sono stringhe) vanno convertiti per poter eseguire calcoli matematici.
```java
int a = Integer.parseInt(args[0]);
double b = Double.parseDouble(args[1]);
```
*Attenzione:* Se la stringa non è un numero valido (es. `"3a"`), il programma "esplode" lanciando l'eccezione `NumberFormatException`. Occorre sempre assicurarsi che l'input sia formalmente corretto.

## Il Deployment del software (JAR)


**Comandi Utili:**
- Creare un JAR libreria (solo classi):
  `jar cf nomearchivio.jar classi`
- Creare un JAR eseguibile (specificando il main via file di testo `info.txt` contenente `Main-Class: NomeclasseMain`):
  `jar cmf info.txt nomeapp.jar classi`
- Creare un JAR eseguibile (specificando la entry point direttamente nel comando):
  `jar cfe nomeapp.jar NomeclasseMain *.class`
- Eseguire il JAR:
  `java -jar nomeapp.jar`