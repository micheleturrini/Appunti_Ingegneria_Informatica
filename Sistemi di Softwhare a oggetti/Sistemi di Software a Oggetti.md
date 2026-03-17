[[0 - Index]]

Mail: sistemidisoftwhareaoggetti@live.unibo.it

Un linguaggio a oggetti è necessario per gestire problemi di ampiezza superiore e sistemi software complessi. 
## Nuovi Concetti
I linguaggi moderni nascono per risolvere i limiti dell'approccio procedurale (programmazione *in-the-small*) e introducono nuovi concetti per la progettazione *in-the-large*:
* **Novità rispetto al C:** L'obiettivo è sostituire costrutti linguistici obsoleti e poco chiari, e intercettare a compile-time quanti più errori possibile per garantire maggiore sicurezza.
* **Tutto è un oggetto:** Abolizione (o **mascheramento**) dei **tipi primitivi** in favore di un approccio in cui ogni entità è un oggetto ("**everything is an object**").
* **Immutabilità:** Distinzione netta tra variabili modificabili e valori non modificabili, con una preferenza per le **strutture dati immodificabili** ("compute by synthesis") per garantire maggiore sicurezza.


## L'Infrastruttura di Java (JVM, JRE, JDK)
L'obiettivo principale dell'infrastruttura Java è massimizzare la portabilità del software, realizzando il sogno del "write once, run everywhere" (scrivi una volta, esegui ovunque).
### Il Processo di Compilazione
* **Il compilatore (`javac`)** non genera codice macchina, ma traduce il file sorgente `.java` in un formato intermedio universale chiamato **bytecode** (file `.class`).
* **Linking Dinamico:** Durante la compilazione le librerie vengono caricate e collegate dinamicamente (a run-time) solo quando servono.
### I Livelli dell'Infrastruttura Java
![[101 - Intro.pdf#page=99&rect=144,46,578,225|101 - Intro, p.99|300]]
Per far funzionare il bytecode su un computer fisico, serve uno "strato-ponte" che interpreti il formato portabile e lo adatti allo specifico sistema. L'infrastruttura si divide in **tre livelli concentrici:**
#### JVM (Java Virtual Machine)
* Si occupa di astrarre l'hardware e il sistema operativo sottostante.
* È **l'unico strato dipendente dalla piattaforma**: esiste una JVM specifica per Windows, una per Mac, una per Linux, ecc., ma tutte eseguono lo stesso identico bytecode.
#### JRE (Java Runtime Environment)
* È l'ambiente necessario per **eseguire** le applicazioni Java.
* È composto da: **JVM + Librerie e Componenti standard** di Java.
#### JDK (Java Development Kit)
* È il pacchetto completo necessario per **sviluppare** applicazioni Java.
* Contiene l'intero **JRE** (per poter testare il codice) più una serie di **Strumenti di Sviluppo (Tools)**.
* Tra gli strumenti inclusi ci sono:
  * `javac` (il compilatore)
  * `jar` (per impacchettare le applicazioni)
  * `javadoc` (per generare la documentazione automatica)



**Infrastruttura e Portabilità (La JVM):**
Java è pensato per essere estremamente versatile e portabile.
* Perché il codice possa funzionare su diversi processori, la compilazione non produce un eseguibile di sistema (come in C), ma un formato intermedio (**bytecode**). 
* Questo bytecode viene eseguito dalla **JVM (Java Virtual Machine)**, che si occupa di tradurlo per lo specifico processore.
* I collegamenti alle librerie (**linker**) vengono risolti **a runtime** (dinamicamente): questo rende l'**avvio leggermente più lento rispetto al C**, ma rende il programma estremamente flessibile e capace di gestire **aggiornamenti** automatici delle librerie.

## Compilazione ed esecuzione da terminale
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

- **Var** (tipo generico) permette l'inferenza del tipo deducendolo dal contesto --> snellisce la sintassi ma va usato solo quando è facile capire il tipo

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
## Componenti Software in C
### Tipi di Componenti in C
* **Librerie (senza stato):** Solo funzioni matematiche o di utilità (header + implementazione).
* **Moduli Singleton (con stato):** Variabile globale protetta con `static`. Utile ma unico per intero progetto.
* **ADT (Tipi di Dato Astratti):** Creati con `typedef`, permettono la creazione di istanze multiple (es. Liste, Frazioni).
### Dichiarazioni Vuote
* **Problema:** Scrivere `int getValue();` in C indica "argomenti sconosciuti", disabilitando i controlli di tipo del compilatore.
* **Soluzione:** Usare sempre `void` per vietare i parametri: `int getValue(void);`.
### Limiti Pratici del C
* **Singleton -> Non Scalabile:** Ottimo incapsulamento (stato nascosto), ma non è possibile istanziarlo più di una volta senza fare brutali "copia-incolla" del codice.
* **ADT -> Incapsulamento Violato:** Permette istanze multiple, ma per funzionare richiede che la `typedef` sia visibile a tutti nel file `.h`. Chiunque può leggere/modificare i dati interni bypassando le funzioni.
* **I Puntatori** sono intrinsecamente pericolosi perchè danno accesso alla memoria senza limiti.
### Costruzione e Collaudo
* **Costruzione Fragile:** In C l'allocazione (`malloc`) e l'inizializzazione (`init`) sono fasi staccate. Dimenticarne una causa malfunzionamenti.
* **Pattern "Make":** Si aggira il problema unendo i due step in un'unica funzione `make()` (che prefigura i "costruttori" dei linguaggi a oggetti).
* **Collaudo (Testing):** Abbandono dei `printf` manuali a favore delle **asserzioni** (`<assert.h>`), per verificare in automatico i risultati attesi.  (es. `assert(getValue() == 1);`)
## Classi e Oggetti
seguendo l'esempio di un contatore:
#### Singleton
In **C**
```cpp
void reset(void);
void inc(void);
int getValue(void);

static int stato;
void reset(){stato=0;}
void inc(){stato++;}
int getValue(){return stato;}
```
in **JAVA**
```java
public class Contatore {
	private static int stato;
	public static void reset(){stato=0;}
	public static void inc(){stato++;}
	public static int getValue(){return stato;}
}

//main
public class MyMain {
	public static void main(String[] args) {
		Contatore.reset();
		Contatore.inc();
		assert Contatore.getValue()==1;
		System.out.println(Contatore.getValue()==1);
	}
}
```
Contatore
- Il file **header** viene **eliminato** (le **parti pubbliche** di ogni classe le sostituiscono)
- il livello di protezione (**privato/pubblico**) è espresso **esplicitamente**
	- le **funzioni** sono **pubbliche** perchè tutti le usino
	- lo **stato** è **privato** per **proteggere** il valore
- è tutto **statico** perché la memoria **non** è allocata **dinamicamente**
Main
- non occorre creare esplicitamente il contatore perché esso coincide con la **classe**, che è un’entità **statica e preesistente** (**esiste solo un contatore**)
- non occorre alcuna include: le **classi vengono caricate al momento del bisogno**, cercandole nel classpath
Collaudo
- il collaudo è nativo tramite la funzione assert (il programma restituisce errore se la condizione non si verifica)

![[105-Classi e oggetti.pdf#page=34&rect=16,48,703,435|105-Classi e oggetti, p.34|500]]
#### L'ADT
In **C**
```cpp
typedef struct {int value;} contatore;
void reset(contatore*);
void inc(contatore*);
int getValue(contatore);

void reset(contatore* pc){ pc -> value =0; }
void inc(contatore* pc) { (pc ->value)++; }
int getValue(contatore c){ return c.value; }

```
in **JAVA**
```java
public class Counter {
	private int value;
	public void reset() { value = 0; }
	public void inc() { value++; }
	public int getValue(){ return value; }
}

//main
public class MyMain {
	public static void main(String[] args) {
	int v1, v2; Counter c1, c2;
	c1 = new Counter(); c2 = new Counter();
	c1.reset(); c2.reset();
	c1.inc(); c1.inc(); c2.inc();
	v1 = c1.getValue(); v2 = c2.getValue();
	System.out.println(v1); System.out.println(v2);
	}
}
```

> [!important]
> Contents- **La classe come "Progetto":** La classe definisce un modello che specifica la struttura interna e il comportamento condiviso.
- **L'oggetto come "Istanza":** Ogni volta che si usa il timbro, si crea un nuovo oggetto distinto (un'istanza) con la propria identità e il proprio stato indipendente.
- **Passaggio dei parametri implicito:** Poiché dati e funzioni sono uniti, le funzioni interne alla classe accedono direttamente ai dati dell'oggetto senza bisogno di passare puntatori come argomenti.
> [!Gli OGGETTI]
> Gli oggetti sono **istanze di una classe**
   Se la classe è il progetto di una macchina l'oggetto è la macchina stessa
   la creazione di un nuovo oggetto (**allocazione dinamica**) è affidata all'operatore **new**
#### Il costruttore
seguendo l'esempio di una frazione:
```java
public class Frazione {
    private int num, den;
    // Costruttore primario a due argomenti
    public Frazione(int n, int d) {
        num = n;
        den = d;
    }
    // Costruttore ausiliario a un argomento (per numeri interi)
    public Frazione(int n) {
        num = n;
        den = 1;
    }
    public int getNum() { return num; }
    public int getDen() { return den; }   
    // Metodi omessi per brevità: equals() e minTerm()
}


// Esempio d'uso
public class MyMain {
    public static void main(String[] args) {
        Frazione f1 = new Frazione(3,4); // costruttore primario
        Frazione f2 = new Frazione(2);   // costruttore ausiliario (den=1)
        System.out.println(f1.getNum() + "/" + f1.getDen()); // L'operatore + concatena e converte i numeri in stringhe
    }
}
```
Inizializzare significa assegnare un valore iniziale a un oggetto che esiste già in memoria.
Creare significa esclusivamente allocare la memoria necessaria per un nuovo oggetto.
Java permette di **creare un oggetto senza inizializzarlo** --> alta probabilità di introdurre bug.
**Costruire** rappresenta l'unione logica delle due azioni, ovvero **creare l'oggetto e contemporaneamente inizializzarlo**.

- Il metodo **costruttore** ha sempre **nome uguale alla classe**
- viene **invocato automaticamente** ogni volta che si istanzia un **nuovo oggetto**
- non può **mai essere richiamato** esplicitamente **dall'utente**
- si possono creare costruttori diversi (stesso nome) con parametri diversi
- il costruttore di default (no argomenti) inizializza le **variabili numeriche a zero** e i **riferimenti a** `null`
## Riferimenti
- **Puntatori (Stile C):** Contengono l'i**ndirizzo di memoria di una variabile** e permettono di **manipolarlo liberamente** tramite l'**aritmetica** dei puntatori. Sono potenti ma **pericolosi**, poiché richiedono il **dereferencing esplicito** (tramite l'operatore `*`) ed espongono al rischio di **invadere aree di memoria** non pertinenti.
- **Riferimenti (Java):** Rappresentano un'astrazione di più alto livello. **Contengono l'indirizzo di un oggetto** ma **non consentono di vederlo né di manipolarlo**.
    - **Non esiste l'aritmetica** dei puntatori, garantendo così l'inviolabilità della barriera di astrazione.
    - Il **dereferencing avviene in automatico** tramite la notazione puntata (es. `c.inc()`), azzerando i rischi del dereferencing manuale.
- **Tipi di Dati (Java)** Il linguaggio impone il **passaggio per valore per i tipi primitivi e il passaggio per riferimento per gli oggetti**.

**Operazioni sui Riferimenti e Aliasing**
- **Dichiarazione:** Definirli senza inizializzarli (es. `Counter c;`).
- **Assegnazione a null:** Assegnare la costante `null` per indicare che il riferimento non punta a nulla. PERICOLOSO
- **Creazione:** Usarli per allocare nuovi oggetti tramite la keyword `new` (es. `c = new Counter();`).
- **Confronto:** Verificare se due riferimenti puntano al medesimo indirizzo.
- **Aliasing:** Assegnare un riferimento a un altro (es. `Counter c2 = c1;`).
Se si assegna `c1` a `c2`, entrambi i riferimenti punteranno allo stesso identico oggetto in memoria. Di conseguenza, un'operazione che muta lo stato dell'oggetto tramite `c2` (es. `c2.inc();`) si rifletterà istantaneamente anche su `c1`.
```java
Counter c1 = new Counter();
c1.reset(); 
c1.inc(); // c1 ora vale 1
Counter c2 = c1; // Creazione dell'alias
c2.inc(); // Incrementa c2
System.out.println(c1.getValue()); // Stamperà 2, perché c1 e c2 sono lo stesso oggetto
```

**Confronto: Identità (**`==`**) vs. Uguaglianza (**`equals`**)**
- **Identità (`==`):** In Java, l'operatore `==` **confronta esclusivamente l'identità di due oggetti**. L'espressione `c1 == c2` è vera se e solo se `c1` e `c2` sono alias, **ovvero puntano allo stesso identico spazio di memoria**. Se due oggetti hanno lo **stesso contenuto** ma sono **istanze distinte** create con due `new` separati, il confronto con `==` darà `false`.
- **Uguaglianza Semantica (`equals`):** Quando serve confrontare il "valore" o il significato logico di due oggetti, il progettista deve specificare un **criterio personalizzato** implementando il metodo `equals`.
es. counter
```java
public boolean equals(Counter x) {return value == x.value;}
```
 *due Counter sono uguali se il valore dell’oggetto corrente («questo») è uguale a quello dell’oggetto ricevuto come argomento" («l’altro»)*

Per meglio evidenziare la simmetria fra i due oggetti del confronto si usa
- `this` serve a denotare esplicitamente **l'oggetto corrente**
- `that` **non è una parola chiave** del linguaggio. È una convenzione, ovvero un nome di variabile scelto liberamente dal programmatore per indicare l'**oggetto ricevuto come argomento**.
```java
public boolean equals(Counter that) {this.value == that.value;}
```
*Essendo all'interno della classe posso usare that.value*

la keyword this si utilizza anche nei costruttori
```java
public Counter() {this.value = 1; }
```
Eclipse può generare i costruttori automaticamente

La keyword `this` può essere utilizzata per **richiamare un costruttore dall'interno di un altro costruttore della stessa classe**.
```java
public class Point {
    double x, y, z;
    // Costruttore a 3 argomenti (il caso più generale)
    public Point(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }
    // Costruttore a 2 argomenti: richiama quello a 3 argomenti
    public Point(double x, double y) {
        this(x, y, 0); 
    }
    // Costruttore a 1 argomento: richiama quello a 2 argomenti
    public Point(double x) {
        this(x, 0); 
    }
}
```
- economia di codice
- collaudabilità
- single entry

**Pre-inizializzazioni**
Quando ci sono **inizializzazioni** che sono identiche per tutti i costruttori è possibile specificarle **direttamente nella dichiarazione del campo**.
```java
public class Point {
    double x, y, z = 18; // z è pre-inizializzato
    // ...
}
```

**Incapsulamento e Accesso ai Campi Privati**
Java **permette** di **violare l'incapsulamento** nel definire i metodi di una classe
```java
public boolean equals(Frazione that){
	return this.num * that.den == this.den * that.num;
}
```
Una good practice è utilizzare comunque i getter
```java
public class Frazione {
    private int num, den;
    
    public boolean equals(Frazione that) {
        // Incapsulamento rispettato sia per "this" che per "that"
        return this.getNum() * that.getDen() == this.getDen() * that.getNum();
    }
}
```

**Overloading di Funzioni**
- in Java anche le funzioni ordinarie possono essere **omonime** all'interno della stessa classe, a condizione che siano **distinguibili dalla lista degli argomenti** (firme diverse).
- **Il tipo di ritorno**, da solo, **non è sufficiente** per distinguere due metodi omonimi.
- **Obiettivo:** Evitare la proliferazione di **nomi diversi** (es. `inc1()`, `inck(int k)`) per operazioni che sono  varianti della stessa azione.
```java
public class Counter {
    // ...
    // Metodo senza argomenti
    public void inc() { this.value++; } 
    // Metodo sovraccaricato con un argomento intero
    public void inc(int k) { this.value += k; } 
}
```
## Stringhe
le stringhe sono **oggetti** della classe `String`. Sono **immutabili**. Ogni stringa rappresenta una specifica sequenza di caratteri, se si vuole una stringa diversa, si crea un nuovo oggetto.
la sintassi per la creazione di una stringa è semplificata
```java
String s = "ciao";
```

Le stringhe **non sono array di caratteri** (non si può usare l'operatore `[]`)
```java
String s = "Nel mezzo del cammin";
char ch = s.charAt(4);       // OK, lettura
// s.charAt(4) = 'Q';        // ERRORE: non si può modificare
```

| Metodo                                                | Descrizione                                                                        | Esempio                                   |
| ----------------------------------------------------- | ---------------------------------------------------------------------------------- | ----------------------------------------- |
| **`charAt(int index)`**                               | Restituisce il carattere alla posizione specificata (da 0 a length-1).             | `"ciao".charAt(1) → 'i'`                  |
| **`length()`**                                        | Restituisce il numero di caratteri della stringa.                                  | `"ciao".length() → 4`                     |
| **`equals(Object obj)`**                              | Confronta il contenuto di due stringhe (case sensitive).                           | `"ciao".equals("CIAO") → false`           |
| **`equalsIgnoreCase(String s)`**                      | Confronta il contenuto ignorando maiuscole/minuscole.                              | `"ciao".equalsIgnoreCase("CIAO") → true`  |
| **`compareTo(String s)`**                             | Confronta lessicograficamente due stringhe (restituisce negativo, zero, positivo). | `"a".compareTo("b") → negativo`           |
| **`indexOf(String s)`**                               | Cerca la prima occorrenza di una sottostringa (o carattere).                       | `"banana".indexOf("na") → 2`              |
| **`lastIndexOf(String s)`**                           | Cerca l'ultima occorrenza di una sottostringa.                                     | `"banana".lastIndexOf("na") → 4`          |
| **`startsWith(String prefix)`**                       | Verifica se la stringa inizia con un dato prefisso.                                | `"Java".startsWith("Ja") → true`          |
| **`endsWith(String suffix)`**                         | Verifica se la stringa termina con un dato suffisso.                               | `"file.txt".endsWith(".txt") → true`      |
| **`substring(int begin)`**                            | Estrae la sottostringa da begin fino alla fine.                                    | `"programma".substring(4) → "gramma"`     |
| **`substring(int begin, int end)`**                   | Estrae la sottostringa da begin (incluso) a end (escluso).                         | `"programma".substring(4,7) → "gra"`      |
| **`replace(char old, char new)`**                     | Sostituisce tutte le occorrenze di un carattere.                                   | `"casa".replace('a','o') → "coso"`        |
| **`toLowerCase()`**                                   | Converte tutti i caratteri in minuscolo.                                           | `"Java".toLowerCase() → "java"`           |
| **`toUpperCase()`**                                   | Converte tutti i caratteri in maiuscolo.                                           | `"Java".toUpperCase() → "JAVA"`           |
| **`trim()`**                                          | Rimuove spazi bianchi iniziali e finali.                                           | `" ciao ".trim() → "ciao"`                |
| **`split(String regex)`**                             | Divide la stringa in array usando un delimitatore (regex).                         | `"a,b,c".split(",") → ["a","b","c"]`      |
| **`concat(String s)`**                                | Concatena la stringa con un'altra (equivalente a `+`).                             | `"ciao".concat(" mondo") → "ciao mondo"`  |
| **`join(CharSequence delim, CharSequence... elems)`** | Metodo **statico** che unisce più stringhe con un delimitatore.                    | `String.join("-", "a","b","c") → "a-b-c"` |
| **`valueOf(tipo primitivo)`**                         | Metodo **statico** che converte un valore primitivo in stringa.                    | `String.valueOf(123) → "123"`             |
| **`format(String format, Object... args)`**           | Metodo **statico** che restituisce una stringa formattata.                         | `String.format("%d più %d fa %d", 2,3,5)` |

**Concatenazione**
L'operatore `+` crea un **nuovo oggetto** `String` che è la concatenazione degli operandi.
```java
String s1 = "ciao";
String s2 = " mondo";
String s3 = s1 + s2;  // nuovo oggetto "ciao mondo"
```
Se si riassegna `s1 = s1 + s2;`, il riferimento `s1` **punta al nuovo oggetto**, ma l'oggetto originale `"ciao"` **rimane in memoria** (eventualmente raccolto dal garbage collector se non più referenziato).

**Carattere di nuova riga**
Java usa `\n` ma i sistemi operativi hanno convenzioni diverse quindi per scrivere codice portabile si usa `System.lineSeparator()` (molto verboso).

**Text Blocks**
Consentono di scrivere multilinea con **sintassi alleggerita**
Sintassi:
```java
String s = """
            E' la prima volta che mi capita
            Prima mi chiudevo in una scatola
            Sempre un po' distante...
            """;
```

**Uguaglianza di stringhe**
In Java, **`==`** confronta i **riferimenti** (se due variabili puntano allo stesso oggetto).
Per confrontare il contenuto si utilizza `equals` o `equalsIgnoreCase`
```java
String a = "ciao";
String b = "ciao";
String c = new String("ciao");
System.out.println(a == b);       // true (stesso oggetto literal internato)
System.out.println(a == c);       // false (oggetti diversi)
System.out.println(a.equals(c));  // true (stesso contenuto)
```

**Metodo `toString`**
Ogni classe in Java eredita il metodo `toString()` da `Object`
La versione di default restituisce una stringa con il nome della classe e un codice hash (es. `Counter@15db9742`).
```java
Counter c = new Counter(5);
System.out.println(c.toString());  // output: Counter@15db9742
System.out.println(c);              // stessa cosa (println chiama toString automaticamente)
```
Le informazioni stampate di default sono poco utili quindi ha senso sovrascriverle (@Override)
```java
public class Counter {
    private int value;
    
    // costruttori e altri metodi...
    
    @Override
    public String toString() {
        return "Counter di valore " + value;
    }
}
```

**Conversione dei metodi primitivi**
I tipi primitivi (`int`, `double`, `boolean`, ecc.) **non sono oggetti**, quindi non hanno metodi come `toString()`.
Java fornisce una serie di metodi **statici** nella classe `String` chiamati `valueOf`
```java
int a = 35;
double d = 3.14;
String s1 = String.valueOf(a);   // "35"
String s2 = String.valueOf(d);   // "3.14"
String s3 = String.valueOf(true); // "true"
```
L'utilizzo di questo metodo è sottointeso nell'utilizzo dell'operatore +

**Formattazione stringhe --C**
Metodo statico
```java
String s = String.format("%-20s canta la canzone %-35s", "Francesca Michielin", "Nessun grado di separazione");
```
Metodo di istanza
```java
String s = "%-20s canta la canzone %-35s".formatted("Francesca Michielin", "Nessun grado di separazione");
```

**StringBuilder**
La concatenazione con `+` in un ciclo è inefficiente perché crea **nuovi oggetti** a ogni iterazione, copiando i caratteri e abbandonando i precedenti al garbage collector.
```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("La nebbia agli irti colli");
}
String risultato = sb.toString();  // converte in String immutabile
```
- `StringBuilder` ha metodi come `append`, `insert`, `delete`, ecc.
- È molto più veloce

**StringJoiner** e **String.Join**
**`StringJoiner`** è un contenitore progettato per concatenare più stringhe con un separatore, gestendo automaticamente l'assenza del separatore dopo l'ultimo elemento.
```java
StringJoiner sj = new StringJoiner(", ");
for (String arg : args) {
    sj.add(arg);
}
System.out.println(sj);  // "alfa, beta, gamma, delta" (senza virgola finale)
```


Es. codice fiscale
(per criteri codice vedi slide)
Si utilizza il **pattern di programmazione FACADE**
- i **metodi pubblici costituiscono la "facciata"** del componente
- lavorano coordinando il lavoro di altri metodi (non visibili)

Per distinguere vocali e consonanti verifico la loro appartenenza a una stringa di sole vocali o consonanti.

## JUnit
è uno strumento di testing più evoluto rispetto ad assert.
- Notifica dei problemi in formato amichevole e analizzabile.
- Esecuzione di tutti i test anche in caso di fallimenti.
- Report chiaro e compatto sui risultati.
JUnit sostituisce l'istruzione `assert` con una serie di **metodi statici** della classe `Assertions` (package `org.junit.jupiter.api.Assertions`).

**Import Necessari**
```java
import org.junit.jupiter.api.*;           // per le annotazioni
import static org.junit.jupiter.api.Assertions.*;  // per i metodi assert
```

|Metodo|Descrizione|
|---|---|
|`assertEquals(expected, actual)`|Verifica che due valori/oggetti siano uguali (usa `equals` per oggetti)|
|`assertEquals(expected, actual, delta)`|Per valori floating-point: verifica uguaglianza entro un delta (tolleranza)|
|`assertArrayEquals(expected, actual)`|Verifica che due array siano uguali (elemento per elemento)|
|`assertTrue(condition)`|Verifica che la condizione sia vera|
|`assertFalse(condition)`|Verifica che la condizione sia falsa|
|`assertNull(value)`|Verifica che il valore sia nullo|
|`assertNotNull(value)`|Verifica che il valore non sia nullo|
|`assertSame(expected, actual)`|Verifica che i riferimenti puntino allo stesso oggetto (`==`)|
|`assertNotSame(expected, actual)`|Verifica che i riferimenti puntino a oggetti diversi|
|`fail()`|Forza il fallimento del test (utile per percorsi eccezionali)|
esempio
```java
package counter;

public class Counter {
    private int val;

    public Counter() { val = 1; }
    public Counter(int v) { val = v; }
    public void reset() { val = 0; }
    public void inc() { val++; }
    public void inc(int k) { val += k; }
    public int getValue() { return val; }
    public boolean equals(Counter x) { return val == x.val; }
    public String toString() { return "Counter di valore " + val; }
}
```
```java
package counter.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import counter.Counter;

class CounterTest {

    // Test costruttori
    @Test
    void testConstructor0() {
        Counter c = new Counter();
        assertEquals(1, c.getValue());
    }

    @Test
    void testConstructor1() {
        Counter c = new Counter(10);
        assertEquals(10, c.getValue());
    }

    @Test
    void testConstructor1b() {
        Counter c = new Counter(27);
        assertEquals(27, c.getValue());
    }

    // Test reset
    @Test
    void testReset() {
        Counter c = new Counter(5);
        c.reset();
        assertEquals(0, c.getValue());
    }

    // Test inc senza parametri
    @Test
    void testInc() {
        Counter c = new Counter(5);
        c.inc();
        c.inc();
        assertEquals(7, c.getValue());
    }

    // Test inc con parametro
    @Test
    void testIncWithK() {
        Counter c = new Counter(5);
        c.inc(3);
        assertEquals(8, c.getValue());
    }

    @Test
    void testIncWithKMultiple() {
        Counter c = new Counter(5);
        c.inc(2);
        c.inc(3);
        assertEquals(10, c.getValue());
    }

    // Test equals
    @Test
    void testEquals() {
        Counter c1 = new Counter(7);
        Counter c2 = new Counter(7);
        Counter c3 = new Counter(10);
        
        assertTrue(c1.equals(c2));    // simmetria
        assertFalse(c1.equals(c3));
        assertTrue(c1.equals(c1));    // riflessività
    }
}
```

**Gestione in Eclipse**
- Tasto destro sulla classe da testare → **New → JUnit Test Case**.
- Wizard: scegliere i metodi da collaudare (opzionale).
- Selezionare la source folder di destinazione (es. `tests`).
- Eclipse crea automaticamente lo scheletro della classe di test con i metodi vuoti.
- Se JUnit non è già nel build path, Eclipse lo aggiunge automaticamente (chiede conferma).
Esecuzione
- Tasto destro sulla classe di test → **Run As → JUnit Test**.
- La vista JUnit mostra:
    - **Barra verde**: tutti i test passano.
    - **Barra rossa**: almeno un test fallisce.
    - Dettaglio degli errori: blu per assertion fallite, rosso per eccezioni (stack trace).

**Strutturazione dei test**
Quando più test condividono operazioni di setup o cleanup, è utile fattorizzarle in metodi dedicati, annotati opportunamente.

|Annotazione|Descrizione|
|---|---|
|`@BeforeAll`|Metodo **statico** eseguito **una sola volta** prima di tutti i test della classe.|
|`@AfterAll`|Metodo **statico** eseguito **una sola volta** dopo tutti i test della classe.|
|`@BeforeEach`|Metodo **non statico** eseguito **prima di ogni** metodo `@Test`.|
|`@AfterEach`|Metodo **non statico** eseguito **dopo ogni** metodo `@Test`.|
## Gestione delle Eccezioni
In Java c'è un sistema integrato di gestione di errori e eccezioni.

**Blocco Try Catch**
```java
try {
    // codice che può generare eccezioni
    System.out.println("Accedo all'elemento 5: " + numeri[5]);
} catch (TipoEccezione e) {
    // gestione dell'eccezione
    System.out.println("Errore: indice dell'array non valido!");
    System.out.println("Messaggio: " + e.getMessage());
}
System.out.println("Il programma continua normalmente...");
```
- Se avviene un'eccezione, l'esecuzione del `try` si interrompe e passa al `catch`
- Dopo il `catch` il programma continua

**Catch multiple**
Per gestire eccezioni diverse si possono utilizzare più catch ordinate dalla più specifica alla più generale.
```java
// CORRETTO: dal più specifico al più generale
catch (FileNotFoundException e) { ... }
catch (IOException e) { ... }

// ERRATO: IOException è più generale di FileNotFoundException
catch (IOException e) { ... }
catch (FileNotFoundException e) { ... } // Non raggiungibile!
```

**Finally**
Codice che viene eseguito sempre dopo
Tipicamente usato per operazioni di pulizia:
- Chiudere file
- Rilasciare connessioni al database
- Liberare risorse
```java
FileReader file = null;
try {
    file = new FileReader("testo.txt");
    // ... operazioni sul file
} catch (IOException e) {
    System.out.println("Errore nella lettura");
} finally {
    if (file != null) {
        try {
            file.close();  // Chiusura sicura
        } catch (IOException e) {
            System.out.println("Errore nella chiusura");
        }
    }
}
```

**Throw**
`throw` serve per **lanciare esplicitamente** un'eccezione.
```java
throw new TipoEccezione("Messaggio di errore");
// oppure
throw variabileEccezione;
```
Esempio
```java
    public static void verificaEta(int eta) {
        if (eta < 0) {
            throw new IllegalArgumentException("L'età non può essere negativa: " + eta);
        }
        System.out.println("Età valida: " + eta);
    }
```

Se l'eccezione è unchecked deve essere dichiarata assieme al metodo
```java
    // Questo metodo dichiara di poter lanciare IOException
    public static void leggiFile(String nomeFile) throws IOException {
        //....
    }
```

Esempio completo
```java
/**
 * Legge un file e restituisce il contenuto come stringa.
 * 
 * @param percorso percorso del file
 * @return contenuto del file
 * @throws FileNotFoundException se il file non esiste
 * @throws IOException per altri errori di I/O
 */
public String leggiFile(String percorso) throws IOException {
    StringBuilder contenuto = new StringBuilder();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(percorso))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            contenuto.append(linea).append("\n");
        }
    } catch (FileNotFoundException e) {
        System.err.println("File non trovato: " + percorso);
        throw e;  // rilancio per gestione a livello superiore
    } catch (IOException e) {
        System.err.println("Errore durante la lettura: " + e.getMessage());
        throw e;
    }
    
    return contenuto.toString();
}
```

## Array
In Java gli array sono **oggetti** veri e propri, istanze di una classe speciale.

**Dichiarazione e creazione**
```java
int[] v;          // dichiarazione di un riferimento
v = new int[3];   // creazione dinamica dell'oggetto array
```
- `new int[3]` crea un array di **3 elementi,** ciascuno **inizializzato al valore di default** per il tipo (0 per interi, `false` per boolean, `\u0000` per char, `null` per riferimenti).

**Differenza tra array di tipi primitivi e array di oggetti
- **Primitivi**: ogni cella contiene direttamente il valore (es. `int`).
- **Oggetti**: ogni cella contiene un **riferimento** a un oggetto (inizialmente `null`). Dopo la creazione dell'array, occorre creare singolarmente gli oggetti da inserire:
```java
Counter[] w = new Counter[6];   // tutti i riferimenti sono null
w[0] = new Counter(11);         // ora w[0] punta a un oggetto
```
È possibile inserire riferimenti a oggetti già esistenti:
```java
Counter c1 = new Counter(3);
w[2] = c1;
```

**Proprietà `length`**
Ogni array in Java ha una proprietà pubblica `length` (finale) che indica il numero di elementi.
```java
int[] v = new int[3];
System.out.println(v.length);   // stampa 3
```

**Inizializzazione degli array**
- **Inizializzazione automatica**: al momento della creazione con `new`, tutti gli elementi sono posti al valore di default del tipo.
- **Inizializzazione contestuale (solo al momento della dichiarazione)**:
```java
int[] v1 = {2, 3, 4};               // array di 3 elementi
Counter[] w1 = { new Counter(2), new Counter(3) };
```
*Questa sintassi è valida solo nella dichiarazione, non per riassegnazioni successive.*
- Inizializzazione al volo come argomento di una funzione
```java
void f(int[] v) { ... }
f(new int[]{2, 3, 4});   // crea e passa un array anonimo
```

**Il problema del metodo `toString()`**
La classe "array" non ha un metodo `toString` personalizzabile; eredita quello di `Object`, che produce una stringa del tipo `[I@6659c656` (dove `[I` indica array di int).

Soluzioni per **stampare il contenuto**:
-  Scrivere un ciclo esplicito.
-  Usare i metodi statici della classe `java.util.Arrays`:
    - **`Arrays.toString(array)` per array monodimensionali.**
    - **`Arrays.deepToString(array)` per array multidimensionali.**
-  Convertire l'array in una lista (ad es. `Arrays.asList()`), ma attenzione: funziona solo per array di oggetti, non per primitivi.
 es.
```java
public class EsempioMain {
    public static void main(String[] args) {
        if (args.length == 0)
            System.out.println("Nessun argomento");
        else
            for (int i = 0; i < args.length; i++)
                System.out.println("argomento " + i + ": " + args[i]);
    }
}
```

**Array come valore di ritorno di una funzione**
in Java una funzione può restituire un array:
```java
public static int[] creaTabella(int n) {
    int[] tab = new int[n];
    for (int i = 0; i < n; i++)
        tab[i] = i * i;
    return tab;
}
```

> [!Metodo o static?]
> - un **metodo** verrebbe **invocato su uno specifico oggetto** (ad es. c1 in c1.inc(), f2 in f2.getNum(), etc.). che dovrebbe quindi essere **preventivamente creato**
> - una **funzione statica** («di libreria») viene invece **invocata semplicemente col suo nome** assoluto (Math.sin()): **non occorre creare nulla**, perché ci si rivolge a una classe

Nel caso in questione non c’è uno specifico oggetto «creatore di tabelle» → meglio una funzione statica

**Ciclo "for each" (enhanced for)**
Sintassi che permette di iterare su tutti gli elementi senza usare un indice esplicito.
```java
for (int x : tab) {
    System.out.println(x);
}

//main dell'es prec
public static void main(String[] args){
	int[] tab = creaTabella(4);
	for (int x : tab) System.out.println(x);
}
```
- A ogni iterazione, la variabile (`x`) contiene una **copia** del **valore dell'elemento** (per i primitivi) o una **copia del riferimento** (per oggetti).
- **Non si può usare per modificare gli elementi dell'array** (nel caso di primitivi perché si modifica la copia; nel caso di oggetti si può modificare l'oggetto tramite il riferimento, ma non si può cambiare il riferimento stesso).
- Utile solo per lettura o per operazioni che non richiedono l'indice.

**Esempio di confronto di array (uguaglianza)**
- per tipi primari
```java
public static boolean idem(int[] a, int[] b) {
    if (a.length != b.length) return false;
    for (int i = 0; i < a.length; i++)
        if (a[i] != b[i]) return false;
    return true;
}
```
- Per array di oggetti, si usa il metodo `equals` della classe degli oggetti (che deve essere opportunamente sovrascritto)
```java
if (!a[i].equals(b[i])) return false;
```

**Funzioni di utilità della classe `java.util.Arrays`**
La classe `Arrays` fornisce numerosi metodi statici per operare sugli array:

| Metodo                       | Descrizione                                                   |
| ---------------------------- | ------------------------------------------------------------- |
| `sort(array)`                | Ordina l'array (per tipi primitivi e oggetti)                 |
| `binarySearch(array, key)`   | Ricerca binaria (array deve essere ordinato)                  |
| `copyOf(array, newLength)`   | Restituisce una copia dell'array con la lunghezza specificata |
| `equals(array1, array2)`     | Confronto superficiale (shallow)                              |
| `deepEquals(array1, array2)` | Confronto profondo (deep) per array annidati                  |
| `fill(array, value)`         | Riempie l'array con un valore                                 |
| `toString(array)`            | Rappresentazione in stringa (monodimensionale)                |
| `deepToString(array)`        | Rappresentazione in stringa per array multidimensionali       |
Es. Stampare il contenuto
Si utilizza `toString(array)` da NON confondere con il metodo della classe [] 
![[108-Array.pdf#page=58&rect=91,174,619,305|108-Array, p.58|500]]
vedi altri es. nel pdf

**Costanti (final)**
```java
final int DIM = 8;
```

**Array multidimensionali (matrici)**
In Java non esistono array multidimensionali "veri", ma array di array.
- Dichiarazione
```java
double[][] m;   // array di array di double
```
- Creazione in due fasi (per **matrici irregolari**):
```java
m = new double[3][];      // array esterno di 3 righe (riferimenti ad array)
m[0] = new double[5];     // prima riga di 5 elementi
m[1] = new double[5];     // seconda riga di 5 elementi
m[2] = new double[5];     // terza riga di 5 elementi
```
- **Creazione concisa** per matrici regolari:
```java
double[][] m = new double[3][5];   // matrice 3x5
```
-  **Accesso** `m[i][j]` dove `i` è l'indice di riga, `j` di colonna.
- Lunghezze
	- `m.length` è il numero di righe.
	- `m[i].length` è il numero di colonne della riga i-esima.
- **Stampa** devo utilizzare la  `Arrays.deepToString(m)`
- **Equals** `Arrays.equals(array1, array2)`confronta i riferimenti agli array interni, non i loro contenuti. Per confrontare in profondità si usa `Arrays.deepEquals(m1, m2)` che **confronta ricorsivamente il contenuto.**
es.  somma di matrici
```java
public static double[][] sommaMatrici(double[][] a, double[][] b) {
    // Supponiamo che a e b abbiano le stesse dimensioni
    double[][] c = new double[a.length][a[0].length];
    for (int i = 0; i < a.length; i++)
        for (int j = 0; j < a[0].length; j++)
            c[i][j] = a[i][j] + b[i][j];
    return c;
}
```

FAI PRODOTTO DI MATRICI

## Package
Applicazioni complesse sono composte da **molte classi e librerie.** Senza una struttura, si incorre in due problemi principali:
- **Conflitti di Nome (Name Clash):** Due classi con lo stesso nome (es. `Book`) potrebbero essere sviluppate da team diversi o per scopi diversi, rendendo impossibile utilizzarle insieme nello stesso progetto.
- **Ingestibilità:** Un insieme "piatto" di centinaia di classi è caotico quanto una singola cartella piena di file senza sottocartelle.
I **package** (in Java) introducono uno **spazio di nomi strutturato**. Permettono di raggruppare logicamente classi correlate in un "pacchetto software".
- è un contenitore che raggruppa classi logicamente correlate.
- Definisce un nuovo livello di visibilità intermedio tra `public` e `private`: la **visibilità nel package** (è di default, non va specificata)
- Crea uno spazio di nomi: il nome completo di una classe è composto dal nome del package + il nome semplice della classe (es. `edenti.Book`).

|Livello di Visibilità|Qualificatore Java|Descrizione|
|---|---|---|
|**Pubblico**|`public`|Visibile a qualsiasi classe di qualsiasi package.|
|**Package**|_(nessuna parola chiave, è il default)_|Visibile solo alle classi che si trovano all'interno dello **stesso package**. È il livello di default in Java.|
|**Privato**|`private`|Visibile solo all'interno della stessa classe.|

```java
// Nel file edenti/Book.java
package edenti;

class Book { // <-- Visibilità di package (default)
    String title; // <-- Visibilità di package (default)

    void printTitle() { // <-- Visibilità di package (default)
        System.out.println(title);
    }
}
```
```java
// Nello stesso package edenti/Library.java
package edenti;

public class Library {
    public void addBook(String title) {
        Book b = new Book(); // OK: Library è nello stesso package di Book
        b.title = title;     // OK: title ha visibilità di package
        b.printTitle();      // OK: printTitle ha visibilità di package
    }
}
```
```java
// In un altro package, ad esempio un cliente
import edenti.Book; // ERRORE: Book non è public, quindi non è importabile/visibile qui!
public class Client {
    public void test() {
        Book b = new Book(); // NON COMPILA: Book non è visibile
    }
}
```

**Convenzioni di Nomenclatura**
- **Nomi:** I nomi dei package sono convenzionalmente scritti in **minuscolo**.
- **Nome Assoluto:** È il nome univoco di una classe, ottenuto concatenando il nome del package e il nome della classe (es. `it.unibo.utilities.Point`) r**everse internet naming**.
- **Corrispondenza File System:** Java impone una rigida corrispondenza tra il nome del package e la struttura delle directory nel file system.
    - Il **package** `edenti` deve risiedere in una cartella chiamata `edenti`.
    - Il package **multilivello** `it.unibo.utilities` deve risiedere in una sequenza di cartelle innestate `it/unibo/utilities/`.
    - La **classe** `it.unibo.utilities.Point` deve trovarsi fisicamente nel file `it/unibo/utilities/Point.java`.
- **Default Package:** Se non si specifica un package, la classe finisce nel "default package".
    - **È da evitare assolutamente in progetti reali.**
    - Le classi nel default package non hanno un nome assoluto e non possono essere importate da classi che si trovano in un package con nome.

**Utilizzo dei Package: `import`**
Per usare una classe da un altro package, si deve usare il suo **nome assoluto**.
```java
public class MyClient {
    public static void main(String[] args) {
        // Uso del nome assoluto
        edenti.Book myBook = new edenti.Book("Il Nome della Rosa");
    }
}
```

Per evitare di scrivere ogni volta il nome assoluto (che può essere molto lungo), si usa la direttiva **`import`** che permette di riferirsi a una classe di un altro package usando il suo **nome relativo (semplice)**. Non include il codice ma permette al compilatore di risolvere il nome.
```java
import edenti.Book; // Importa la singola classe Book

public class MyClient {
    public static void main(String[] args) {
        // Uso del nome relativo grazie all'import
        Book myBook = new Book("Il Nome della Rosa");
    }
}
```
Si può importare tutte le classi di un package con `import edenti.*;`.
Se ci sono omonimie la soluzione è importare la classe più usata e usare il nome assoluto per l'altra.

**Compilazione e esecuzione**
I comandi `javac` e `java` devono essere eseguiti dalla **directory che sta al di sopra della radice dei package**.
Nella stessa directory
```bash
javac edenti/Book.java edenti/Library.java

java edenti.Library
```
In un'altra directory
```bash
# -cp .;..  (Su Windows) indica di cercare le classi nella cartella corrente (.) e in quella superiore (..)
javac -cp .;.. MyClient.java

java -cp .;.. MyClient
```
I percorsi sono separati da `;` (Windows) o `:` (Linux/macOS).

**Importazione statica**
Introdotta per permettere l'uso di membri `static` (campi e metodi) di una classe senza dover prefissare il nome della classe.
```java
// Senza import static
public class TestMath {
    public double areaCerchio(double raggio) {
        return Math.PI * raggio * raggio; // Devo sempre scrivere "Math."
    }
}

// Con import static
import static java.lang.Math.PI;
import static java.lang.Math.pow; // O anche import static java.lang.Math.*;

public class TestMath {
    public double areaCerchio(double raggio) {
        return PI * pow(raggio, 2); // Non serve più "Math."
    }
}
```
*Da usare con parsimonia*

**Il Package `java.lang`**
È il package più importante di Java, importato **automaticamente** in ogni programma.
Contiene le classi fondamentali del linguaggio:
- `Object` (la radice di tutte le classi)
- `String`, `StringBuilder`
- Classi wrapper: `Integer`, `Double`, `Boolean`, etc.
- `System` (per `System.out.println`)
- `Math`
- `Class` (per la reflection)
- Eccezioni e errori di base (`Throwable`, `Exception`, `RuntimeException`)
## I moduli
Con applicazioni molto grandi (come l'intera piattaforma Java stessa), il solo meccanismo dei package si è rivelato insufficiente.
Un modulo è un raggruppamento di package che aggiunge un ulteriore livello di incapsulamento. Si specifica quali package **esporta** (rende visibili all'esterno) e da quali altri moduli **dipende**.
Le specifiche del modulo sono in un file speciale: `module-info.java`, posto nella directory radice dei package del modulo.
```java
module it.unibo.mylib {
    // Esporta il package 'it.unibo.mylib.utils' a tutti
    exports it.unibo.mylib.utils;

    // Esporta il package 'it.unibo.mylib.api' solo al modulo 'it.unibo.myapp'
    exports it.unibo.mylib.api to it.unibo.myapp;

    // Dichiara che questo modulo dipende dal modulo 'java.sql'
    requires java.sql;

    // Dichiara che questo modulo usa un servizio (per ServiceLoader)
    uses it.unibo.mylib.spi.MyService;

    // Dichiara che questo modulo fornisce un'implementazione di un servizio
    provides it.unibo.mylib.spi.MyService with it.unibo.mylib.internal.MyServiceImpl;
}
```
- I package non esportati sono accessibili solo all'interno del modulo. Non è più necessario usare `public` per farli vedere a package "amici".
- **Immagine di Runtime Ridotta:** Strumenti come `jlink` possono creare un'immagine di runtime di Java contenente solo i moduli strettamente necessari all'applicazione, riducendo le dimensioni.
- **Retrocompatibilità**  Le classi tradizionali (senza modulo) finiscono in un "modulo senza nome" (unnamed module) e possono accedere a tutti i moduli della piattaforma.
## Enumerativi
Un `enum` in Java è una classe a tutti gli effetti, con alcune caratteristiche speciali:
- Ha un **costruttore privato**, quindi l'utente non può creare istanze arbitrariamente.
- In quanto classe, può avere **stato (campi)** e **comportamento (metodi)**.
- I valori elencati (es. `NORTH`, `SOUTH`) sono le **uniche istanze pubbliche e statiche** della classe, create dal compilatore.
```java
public enum Direction {
    NORTH, SOUTH, EAST, WEST;
}

Direction dir = Direction.NORTH;
```

**Utilizzo negli `switch`**
```java
Direction dir = Direction.NORTH;

// switch tradizionale (statement)
switch (dir) {
    case NORTH: System.out.println("North"); break;
    case SOUTH: System.out.println("South"); break;
    case EAST:  System.out.println("East");  break;
    case WEST:  System.out.println("West");  break;
}
```
oppure
```java
var name = switch (dir) {
    case NORTH -> "North";
    case SOUTH -> "South";
    case EAST  -> "East";
    case WEST  -> "West";
}; // <-- Punto e virgola obbligatorio perché è un'assegnazione
```
- **È un'espressione**, quindi restituisce un valore che può essere assegnato a una variabile.
- I rami sono mutuamente esclusivi, quindi **non serve il `break`**
- Deve essere esaustiva: coprire tutti i casi possibili o includere un ramo `default`

**L'Enum come Classe: Metodi Automatici**
- **`ordinal()`:** Restituisce l'indice (0-based) della costante nell'ordine in cui è stata dichiarata. Da usare con cautela, perché l'ordine è parte della definizione dell'enum e non dovrebbe essere usato per logiche di business.
- **`values()`:** Metodo statico che restituisce un array contenente tutte le costanti dell'enum nell'ordine di dichiarazione. Utile per iterare.
- **`valueOf(String name)`:** Metodo statico che restituisce la costante dell'enum il cui nome corrisponde esattamente alla stringa passata.
```java
for (Direction d : Direction.values()) {
    System.out.println(d.ordinal()); // Stampa 0, 1, 2, 3
}

Direction d = Direction.valueOf("EAST"); // d sarà Direction.EAST
System.out.println(d); // Stampa "EAST"
```

**Estendere l'Enum: Aggiungere Comportamento**
Essendo una classe, possiamo arricchire un enum con metodi personalizzati.
```java
public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public int getDegrees() {
        return switch (this) {
            case NORTH -> 0;
            case SOUTH -> 180;
            case EAST  -> 90;
            case WEST  -> 270;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "Nord";
            case SOUTH -> "Sud";
            case EAST  -> "Est";
            case WEST  -> "Ovest";
        } + " a " + getDegrees() + "°";
    }
}
```

**Soluzione all switch**
si **associano i dati direttamente a ogni costante**, spostando la conoscenza dalle funzioni (metodi) ai dati stessi.
si definisce un **costruttore privato che accetta i dati e li salva in campi dell'istanza.**
```java
public enum Direction {
    // Le costanti ora invocano il costruttore con i loro dati specifici
    NORTH("Nord", 0),
    SOUTH("Sud", 180),
    EAST("Est", 90),
    WEST("Ovest", 270);

    // Campi per lo stato
    private String italianName;
    private int degrees;

    // Costruttore privato
    private Direction(String italianName, int degrees) {
        this.italianName = italianName;
        this.degrees = degrees;
    }

    // I metodi ora restituiscono semplicemente lo stato dell'istanza
    public int getDegrees() {
        return degrees;
    }

    @Override
    public String toString() {
        return italianName + " a " + degrees + "°";
    }

    // Il metodo getOpposite, purtroppo, non può essere implementato con dati,
    // perché l'opposto è una forward reference.
    public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST  -> WEST;
            case WEST  -> EAST;
        };
    }
}
```

**Limitazioni e Workaround per Riferimenti Circolari**
Non è possibile, durante la dichiarazione di una costante, riferirsi a un'altra costante che non è stata ancora definita (_forward reference_). Questo impedisce, ad esempio, di passare l'oggetto `SOUTH` al costruttore di `NORTH` per il metodo `getOpposite`.
```java
// NON COMPILA! 'SOUTH' è ancora sconosciuto quando si definisce NORTH.
NORTH("Nord", 0, SOUTH),
```
**Workaround:** Si può passare il **nome** della costante opposta come stringa e usare il metodo `valueOf()` per ottenere l'istanza corretta al momento dell'esecuzione. (Metodo comunque fragile)

Es. Banconote e Portafogli
```java
public enum Taglio {
    CINQUECENTO(500), DUECENTO(200), /* ... */ UNO(1);

    private final int valore;

    private Taglio(int valore) {
        this.valore = valore;
    }

    public int getValore() {
        return valore;
    }
}
```
il **Portafoglio** stesso è un'entità del mondo reale e merita una sua classe. Questo incapsula i dati (l'array di tagli) e le operazioni (metodi `valore()` e `toString()`).
```java
import java.util.Arrays;
import java.util.StringJoiner;

public class Portafoglio {
    private Taglio[] contenuto;
    private int logicalSize; // Tiene traccia degli elementi effettivamente inseriti

    // Costruttore per un portafoglio vuoto con una capacità iniziale
    public Portafoglio(int capacita) {
        contenuto = new Taglio[capacita];
        logicalSize = 0;
    }

    // Costruttore da un array esistente
    public Portafoglio(Taglio[] tagliIniziali) {
        this.contenuto = Arrays.copyOf(tagliIniziali, tagliIniziali.length);
        this.logicalSize = tagliIniziali.length;
    }

    public void add(Taglio t) {
        if (logicalSize < contenuto.length) {
            contenuto[logicalSize++] = t;
        } else {
            // Gestire l'overflow (es. ridimensionare l'array)
            System.out.println("Portafoglio pieno!");
        }
    }

    public int valore() {
        int sum = 0;
        for (int i = 0; i < logicalSize; i++) {
            sum += contenuto[i].getValore(); // Chiede il valore al taglio!
        }
        return sum;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        for (int i = 0; i < logicalSize; i++) {
            sj.add(contenuto[i].toString());
        }
        return sj.toString();
    }
}
```
il main è pulito
```java
Portafoglio pf = new Portafoglio(10);
pf.add(Taglio.CINQUANTA);
pf.add(Taglio.VENTI);
// ...

System.out.println("Contenuto: " + pf); // Stampa: CINQUANTA, VENTI, ...
System.out.println("Valore: " + pf.valore()); // Stampa: 70
```
- **Enum Evoluto (con stato):** Incapsula la conoscenza, eliminando gli switch per le proprietà intrinseche. Il codice diventa più robusto e auto-documentante.
- **Nascita del Portafoglio (classe contenitore):** Incapsula la collezione e le operazioni correlate, dando una "casa" a metodi che altrimenti sarebbero rimasti in librerie statiche senza una chiara appartenenza. Il modello finale rispecchia la realtà, è robusto, estendibile e manutenibile.
### Il Pattern Factory

In molte situazioni pratiche, permettere all'utente di costruire direttamente oggetti complessi (tramite la parola chiave `new`) è inopportuno. Questo perché gli oggetti complessi spesso hanno costruttori con molti argomenti o richiedono competenze specifiche che l'utente potrebbe non avere, con il rischio di non rispettare i vincoli.
Il **Design Pattern Factory** ("fabbrica") interviene per risolvere questo problema:
- **Incapsulamento:** Nasconde i dettagli costruttivi all'utente, incapsulando la conoscenza necessaria per creare l'oggetto.
- **Decisioni Strategiche:** Una fabbrica può decidere come costruire un oggetto in base ai parametri, alla disponibilità di risorse o restituire oggetti compatibili senza che il cliente lo sappia.
- **Struttura in Java:** I costruttori della classe non sono pubblici. La costruzione avviene tramite funzioni statiche (chiamate _factory methods_) incluse nella classe stessa.
- **Nomenclatura:** I metodi factory tipicamente si chiamano `of`, `valueOf` o, più in passato, `get***`.
### Cultura Locale 

Le convenzioni per formattare numeri, percentuali, valute, date e orari cambiano in base al paese e alla cultura (es. separatori delle migliaia, posizione del simbolo della valuta). A partire da Java 9, Java adotta il database internazionale **Unicode CLDR** (Common Locale Data Repository) per raccogliere queste convenzioni (fino a Java 8 usava un database interno JRE).

In Java, la cultura locale è rappresentata dalla classe `java.util.Locale`.

- Una cultura locale è composta da una **lingua** (sigla minuscola, es. `it`) e un **paese** (sigla MAIUSCOLA, es. `IT`). Questo perché la stessa lingua in paesi diversi adotta convenzioni diverse (es. l'italiano in Italia è `it_IT`, in Svizzera è `it_CH`).
    

### Ottenere un oggetto Locale in Java

Ci sono diversi modi per ottenere o costruire una cultura locale:

1. **Costanti predefinite:** Per i casi più comuni.
    
2. **Factory Methods (da Java 19):** I costruttori classici tramite `new` sono stati deprecati in Java 19 a favore dei factory methods.
    

Java

```
// 1. Uso di costanti (Factory implicita)
Locale italy = Locale.ITALY; // [cite: 167]

// 2. Factory methods (Da Java 19 in poi)
Locale swissItalian = Locale.forLanguageTag("it-CH"); // [cite: 176]
Locale customLocale = Locale.of("en", "GB"); // [cite: 146]

// 3. Ottenere il locale di default del sistema
Locale defaultLocale = Locale.getDefault(); // [cite: 154]
```

---

## 3. Formattatori Numerici (`NumberFormat`)

La classe `java.text.NumberFormat` è una factory che genera formattatori per numeri, percentuali e valute in base alla cultura locale. Si utilizzano i metodi `get***Instance()` e poi si applica il metodo `format(valore)` per ottenere la stringa formattata.

### A. Formattazione di Numeri Base

Java

```
double x = 43.12345678; // [cite: 248]
// Crea il formattatore numerico per il Canada
NumberFormat fN = NumberFormat.getNumberInstance(Locale.CANADA); // [cite: 248, 249]
fN.setMaximumFractionDigits(2); // [cite: 250]

System.out.println(fN.format(x)); // Output: 43.12 [cite: 251, 257]
```

_Nota:_ Il separatore delle migliaia e dei decimali cambia in base alla cultura. In Italia si usa il punto `.` per le migliaia e la virgola `,` per i decimali, mentre in Nord America è l'opposto. Nel Canada francofono si usa lo spazio (hard space) per le migliaia.

### B. Formattazione di Percentuali

Java

```
double q = 0.7; // [cite: 293]
NumberFormat fP = NumberFormat.getPercentInstance(Locale.ITALY); // [cite: 293]
System.out.println(fP.format(q)); // Output: 70% [cite: 299, 308]
```

### C. Formattazione di Valute e il problema dell'Euro

Non serve specificare il numero di cifre decimali per le valute, in quanto fanno già parte della convenzione del database CLDR, con i relativi arrotondamenti.

**Attenzione al cambio da Java 8 a Java 9+:** Con l'adozione del database CLDR in Java 9, la convenzione italiana per l'Euro è cambiata. Fino a Java 8 il simbolo precedeva l'importo (es. `€ 1.243,57`), mentre da Java 9+ il simbolo si posiziona dopo l'importo (es. `1.243,57 €`).

Java

```
double price = 1243.5678; // [cite: 385]
NumberFormat fV = NumberFormat.getCurrencyInstance(Locale.ITALY); // [cite: 386]
System.out.println(fV.format(price)); // Output in Java 9+: 1.243,57 € [cite: 386, 389]
```

---

## 4. Parsing: Dalle Stringhe ai Numeri

I formattatori permettono anche l'operazione inversa (parsing), ovvero convertire una stringa formattata in un numero (oggetto della classe `Number`) tramite il metodo `parse`.

Java

```
NumberFormat fV = NumberFormat.getCurrencyInstance(Locale.US); // [cite: 420]
// Il metodo parse restituisce un oggetto Number
Number n = fV.parse("$123.56"); // [cite: 423, 424]
// Estrazione in tipi primitivi
double d = n.doubleValue(); // [cite: 426]
```

**Pericoli del Parsing:** Il parsing è molto sensibile e richiede la gestione delle eccezioni, poiché un formato scorretto fa "arrabbiare" il formattatore. Inoltre, se si analizza una stringa con una cultura locale errata (es. leggere `72.35%` con `Locale.ITALY`), Java potrebbe interpretare la stringa solo parzialmente o dare risultati assurdi senza lanciare errore. In culture come il Canada francese, il parsing richiede spazi speciali non interrompibili (non-breakable space).

---

## 5. Formattatori Personalizzati (`DecimalFormat`)

Se i formattatori standard non soddisfano le esigenze (ad esempio, si rivuole il simbolo dell'Euro davanti all'importo in Java 9+), si può creare un formattatore su misura utilizzando la classe `java.text.DecimalFormat`. In questo caso non si usa una factory, ma il costruttore `new`, passando una "stringa di formato" (pattern).

Simbologia del Pattern:

- `¤` (carattere jolly per il simbolo di valuta)
    
- `#` (cifra generica che può mancare)
    
- `0` (cifra generica obbligatoria, aggiunge zero iniziale/finale se necessario)
    
- `,` (separatore delle migliaia) - _Verrà sostituito col simbolo della cultura corrente_
    
- `.` (separatore decimale) - _Verrà sostituito col simbolo della cultura corrente_
    

### Esempio: Ripristinare l'Euro davanti e formattare i negativi

Per gestire elegantemente i numeri negativi, è possibile fornire una doppia specifica separata da `;` (la prima per i positivi, la seconda per i negativi).

Java

```
// Il pattern definisce un formato per positivi e uno per negativi
DecimalFormat customFmt = new DecimalFormat("¤ #,##0.## ; ¤ -#,##0.##"); // [cite: 548]

System.out.println(customFmt.format(1234.567));  // Output: € 1.234,57 [cite: 550, 554]
System.out.println(customFmt.format(-1234.567)); // Output: € -1.234,57 [cite: 551, 555]
```

---

Spero che questi appunti chiariscano i concetti! Vuoi che approfondiamo l'utilizzo dei blocchi `try-catch` necessari per gestire correttamente gli errori del metodo `parse()` di cui si accenna nel documento?