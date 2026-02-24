[[0 - Index]]

Mail: sistemidisoftwhareaoggetti@live.unibo.it

Un linguaggio a oggetti è necessario per gestire problemi di ampiezza superiore e sistemi software complessi. 
## Teoria e Nuovi Concetti
I linguaggi moderni nascono per risolvere i limiti dell'approccio procedurale (programmazione *in-the-small*) e introducono nuovi concetti per la progettazione *in-the-large*:
* **Novità rispetto al C:** L'obiettivo è sostituire costrutti linguistici obsoleti e poco chiari, e intercettare a compile-time quanti più errori possibile per garantire maggiore sicurezza.
* **Tutto è un oggetto:** Abolizione (o **mascheramento**) dei **tipi primitivi** in favore di un approccio in cui ogni entità è un oggetto ("**everything is an object**").
* **Immutabilità:** Distinzione netta tra variabili modificabili e valori non modificabili, con una preferenza per le **strutture dati immodificabili** ("compute by synthesis") per garantire maggiore sicurezza.

**Infrastruttura e Portabilità (La JVM):**
Java è pensato per essere estremamente versatile e portabile.
* Perché il codice possa funzionare su diversi processori, la compilazione non produce un eseguibile di sistema (come in C), ma un formato intermedio (**bytecode**). 
* Questo bytecode viene eseguito dalla **JVM (Java Virtual Machine)**, che si occupa di tradurlo per lo specifico processore.
* I collegamenti alle librerie (**linker**) vengono risolti **a runtime** (dinamicamente): questo rende l'**avvio leggermente più lento rispetto al C**, ma rende il programma estremamente flessibile e capace di gestire **aggiornamenti** automatici delle librerie.
## Struttura del Codice e Gestione Memoria
### Le operazioni
Si inverte il punto di vista rispetto al C, passando dal focus sull'operazione al focus sull'entità.
```c
// Approccio procedurale C
operation(comp, argomenti);
fprintf(fout, "Hello!");
```
diventa:
```java
// Approccio a oggetti Java
comp.operation(argomenti);
```
* **comp** = a CHI mi rivolgo
* **operation** = COSA gli chiedo di fare
### Elementi di base e Memoria
* **Entità Statiche:** Esistono prima dell'inizio del programma e permangono per tutta la sua durata (es. moduli, libreria matematica) -> **CLASSI**.
* **Entità Dinamiche:** Vengono create durante l'esecuzione solo al momento del bisogno -> **OGGETTI**.
* **Gestione della memoria:**
    * Sostituzione dei puntatori del C con i **riferimenti**.
    * Dereferenziamento automatico.
    * **Allocazione e deallocazione automatica** della memoria tramite il **Garbage Collector**.
    * **Type safety** = type system stringente + controlli a run-time per prevenire crash.
### Convenzioni di Naming 
* **Classi e File:** Il nome della classe deve iniziare con la maiuscola (*CamelCase*). Il file **deve** chiamarsi esattamente come la classe pubblica che contiene con estensione `.java`.
* **Metodi e Variabili:** Iniziano con la minuscola (*camelCase*). Le costanti sono tutte in MAIUSCOLO.
## Il Main e l'IO
Il punto di partenza dell'applicazione è il `main`. In Java classico è contenuto in una classe pubblica dedicata.
Il tipo di **ritorno** è sempre `void` e ha **sempre** come argomento un array di stringhe (`String[] args`).
`static` significa che il metodo esiste dall'inizio, indipendentemente dalla creazione di oggetti.

```java
public class MyProg {
    public static void main(String[] args){
        // In Java, args[0] è il primo argomento passato dall'utente, non il nome del programma
        if (args.length > 0) {
            System.out.println("Primo argomento: " + args[0]); 
        }
    }
}
```
### Il Main semplificato
Per alleggerire la sintassi, Java 25 permette di omettere la classe contenitore, `public`, `static` e persino l'argomento `String[] args`. Introduce anche il modulo `IO` per semplificare stampe e letture.
```java
// Il file si chiama App.java (la classe è generata in automatico)
import static java.lang.IO.*;

void main() {
    println("Hello!"); // Sostituisce System.out.println
    String nome = readln("Come ti chiami? "); // Sostituisce l'uso complesso di System.in
}
```
## Tipi di Base, Conversioni e Classi Wrapper
I tipi base definiscono la dimensione e i limiti dei dati:
* **Boolean:** separato dagli interi, ammette solo `false` e `true` (non 0 e 1).
- **Int** (le diverse lunghezze vengono standardizzate e la sintassi semplificata)
	- **byte**  (1 byte) -128 ... +127
	- **short**  (2 byte) -32768 ... +32767
	- **int** (4 byte) -2 10^9  …... +2 10^9
	- **long** (8 byte) -9 10^18 ……+9 10^18 
	*Nota errori aritmetici intera:* L'overflow intero non genera errori bloccanti ma "sborda" nei numeri negativi (wrap-around). La **divisione per zero** tra interi genera un'eccezione bloccante `ArithmeticException` (esplode a runtime).
- **Float**
	- **float** (4 byte) - 10^45 ... +10^38
	- **double** (8 byte) -10^324 ... +10^308
	* **Caratteri:** `char` (2 byte, Unicode/UTF-16). Permette di usare caratteri speciali tramite codifica esadecimale (es. `\u00B1` per $\pm$).
	*Nota errori aritmetici reali:* La divisione per `0.0` non causa crash ma restituisce `Infinity` o `-Infinity`. Le forme indeterminate (es. `0.0/0.0`) restituiscono `NaN` (Not a Number).
- **Caratteri** 127 caratteri non bastano quindi si assegnano più byte a ogni carattere 1-4 (UTF-8)(1.114.112 caratteri)
![[102-Linguaggi e piattaforme.pdf#page=81&rect=229,48,682,241|102-Linguaggi e piattaforme, p.81|300]]
C'è ancora la corrispondenza diretta fra caratteri e numeri come in C.
```java
char ch = 'A';
ch = 72;
char cStran = '\u2122'
```

**Assegnamenti e Cast:**
Sono ammessi implicitamente solo gli assegnamenti che non generano una perdita di informazioni.
```java
double x = 3.54F; // Permesso (nessuna perdita)
// float f = 3.54;   // DÀ ERRORE (double in float perde precisione)
float f = (float) 3.54; // Corretto: forzatura tramite CAST esplicito
```

**Classi, Librerie e Wrapper**
Le classi **partizionano** lo spazio dei **nomi** (possono esistere nomi uguali in classi diverse).
I tipi primitivi in Java non sono oggetti e non hanno metodi diretti. Per operazioni avanzate si usano le Classi di utilità (Wrapper e Librerie):
- **Math:** Libreria statica per calcoli matematici. Es. `Math.sin(Math.PI/3)`, `Math.sqrt(x)`. Funzione utile: `Math.hypot(x,y)` calcola $\sqrt{x^2+y^2}$ prevenendo overflow/underflow intermedi.
- **Character:** Es. `Character.toUpperCase(ch)`, `Character.isWhitespace(ch)`, `Character.digit(ch, base)` (per ottenere il valore numerico del carattere).
- **Integer:** Es. `Integer.signum(n)`, `Integer.rotateLeft()`.
## Documentazione (Javadoc)
La documentazione è generata automaticamente a patto di usare una sintassi specifica.
```java
/** * Javadoc Classico
* @author Mario Rossi
* @version 1.0
*/
```
In Java 23+ si può usare il **Markdown** anteponendo `///` a ogni riga.
Generazione automatica manuale HTML: `javadoc -d docs NomeFile.java`.



## Guida Pratica da Terminale: Da `.java` a `.jar`
L'eseguibile in Java non è un file monolitico (`.exe`) ma un **archivio zippato `.jar`** (Java ARchive) (non va estratto).
- Per **librerie**, basta impacchettare le classi (file `.class`).
- Per **applicazioni eseguibili**, occorre specificare la classe contenente il main attraverso un file descrittore `MANIFEST.MF` (creabile passando un file di testo come `info.txt`).

**Compilazione ed Esecuzione Locale**
1. **Compilazione:** Trasforma il file sorgente in bytecode (`.class`).
   ```bash
   javac MyProg.java
   
   javac -cp Lib.jar MyProg.java //se contiene librerie
   ```
   *Risultato:* Viene creato il file `MyProg.class`. Se ci sono altre classi nel progetto, puoi compilarle tutte insieme con `javac *.java`.
2. **Esecuzione semplice:** Avvia la Java Virtual Machine.
   ```bash
   java MyProg.class
   ```

**Creare una Libreria JAR (Non eseguibile)**
Se hai sviluppato delle classi di utilità e vuoi distribuirle come libreria.
```bash
jar cf MiaLibreria.jar *.class
```
* `c` sta per *create* (crea un nuovo archivio).
* `f` sta per *file* (specifica il nome del file jar da creare).

**Creare un'Applicazione JAR Eseguibile**
Per rendere un `.jar` eseguibile con un doppio clic o da terminale, la JVM deve sapere qual è la classe che contiene il metodo `main`. Questo si fa usando un file manifesto (`MANIFEST.MF`).

- **Usando un file di testo**
1. Crea un normale file di testo chiamato `info.txt`.
2. Scrivici dentro questa singola riga (assicurati di andare a capo alla fine):
   `Main-Class: MyProg`
3. Lancia il comando per creare il jar iniettando il manifesto:
   ```bash
   jar cmf info.txt AppEseguibile.jar *.class //o lista delle classi
   ```
   *(`m` sta per manifest)*.

- **Comando diretto**
Puoi dire direttamente a Java quale classe è l'entry-point (la `e` sta per *entry point*):
```bash
jar cef AppEseguibile.jar MyProg *.class //o lista delle classi
 //verboso
jar --create --main-class NomeclasseMain --file nomeapp.jar classi
```

**Eseguire il file JAR**
```bash
java -jar AppEseguibile.jar

java -cp _/Lib.jar;. MyMain //se contiene librerie
```
Se c'è una **libreria** nel file `MANIFEST.MF` bisogna **aggiungere `Class-Path: .../Lib.jar`**
*(Se il programma prevede argomenti da terminale, aggiungili alla fine, es: `java -jar AppEseguibile.jar argomentiVari`)*.

