\[[0 - Index]]

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
In Java esiste un sistema integrato per gestire errori e situazioni anomale basato sul concetto di **eccezione**. L’obiettivo è separare nettamente il flusso normale (l’*happy path*) dal codice di gestione errori, evitando codice rumoroso pieno di `if` e permettendo una propagazione controllata dei problemi.
### Blocco Try-Catch
Il costrutto `try-catch` delimita un blocco “sorvegliato”:  
- si **tenta** (`try`) di eseguire le operazioni critiche;  
- se qualcosa va storto, viene **lanciata un’eccezione**, l’esecuzione si interrompe immediatamente e il controllo passa al blocco `catch` corrispondente.

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

Se non si verifica alcun errore, il `catch` viene saltato e l’esecuzione prosegue normalmente dopo il blocco.
### Catch multipli e Multi-catch
Per gestire eccezioni di tipo diverso si possono usare più blocchi `catch`, **sempre dalla più specifica alla più generale** (la prima corrispondenza termina la ricerca).

```java
try {
    // operazioni che possono lanciare eccezioni diverse
} catch (FileNotFoundException e) {
    // gestione file mancante (specifica)
} catch (IOException e) {
    // gestione altri errori di I/O (generale)
}
```

quando si vuole eseguire lo stesso trattamento per più tipi di eccezione, si può usare il **multi-catch**:

```java
catch (IOException | SQLException e) {
    // gestione unificata per entrambi i tipi
}
```
### Finally
Il blocco `finally` contiene codice che viene **sempre** eseguito, sia che l’operazione riesca sia che venga lanciata un’eccezione. Viene usato tipicamente per rilasciare risorse (file, connessioni, lock). Con l’introduzione del **try-with-resources**ò, molte operazioni di chiusura vengono gestite automaticamente, riducendo la necessità del `finally`.

```java
FileReader file = null;
try {
    file = new FileReader("testo.txt");
    // operazioni sul file
} catch (IOException e) {
    System.out.println("Errore nella lettura");
} finally {
    if (file != null) {
        try {
            file.close();  // chiusura sicura
        } catch (IOException e) {
            System.out.println("Errore nella chiusura");
        }
    }
}
```
### Throw: lanciare deliberatamente un’eccezione
L’istruzione **`throw`** serve a **lanciare esplicitamente** un’eccezione. Si crea un oggetto eccezione con `new` e lo si “lancia” interrompendo il flusso corrente.

```java
throw new TipoEccezione("Messaggio di errore");
```

**Esempio pratico:** controllo di precondizioni.
```java
public static void verificaEta(int eta) {
    if (eta < 0) {
        throw new IllegalArgumentException("L'età non può essere negativa: " + eta);
    }
    System.out.println("Età valida: " + eta);
}
```

Questo meccanismo è fondamentale per impedire la creazione di oggetti inconsistenti (ad es. un triangolo impossibile o una frazione con denominatore zero) o per segnalare argomenti non validi.
### Eccezioni Checked e Unchecked
In Java le eccezioni si dividono in due grandi categorie:

| Tipo                 | Gerarchia                                        | Obbligo di gestione      | Significato tipico                                                                 |
|----------------------|--------------------------------------------------|---------------------------|-----------------------------------------------------------------------------------|
| **Checked** (a controllo obbligatorio) | `Exception` (ma non `RuntimeException`)          | Sì: `try-catch` o `throws` | Situazioni esterne al controllo del programma (file mancante, rete assente)       |
| **Unchecked** (a controllo facoltativo) | `RuntimeException` e sottoclassi<br>`Error` e sottoclassi | No (facoltativo)           | Bug o violazioni di precondizioni (`NullPointerException`, `IllegalArgumentException`, ecc.)<br>Gli `Error` sono problemi gravi della JVM (es. `OutOfMemoryError`) e non andrebbero catturati. |

Gerarchia semplificata:
```
Throwable
├── Error (unchecked, problemi JVM)
└── Exception
    ├── RuntimeException (unchecked, bug/precondizioni)
    └── (altre) Exception (checked, situazioni esterne)
```

La scelta tra checked e unchecked è parte della progettazione:
- Un’eccezione **checked** costringe il chiamante a pensare alla possibile anomalia. Funziona bene in sistemi a piccola/media scala, ma può diventare onerosa in architetture a molti strati.
- Un’eccezione **unchecked** non impone vincoli di compilazione; si assume che le precondizioni siano rispettate e che eventuali violazioni vadano scoperte durante il testing.
### Throws: dichiarare le eccezioni lanciabili
Quando un metodo può lanciare un’eccezione **checked** senza gestirla internamente, **deve** dichiararlo nella propria firma con la clausola **`throws`**. In questo modo avverte i chiamanti che quel metodo è “pericoloso” e che devono farsene carico (con un `try-catch` o rilanciandola).

```java
public void leggiFile(String nomeFile) throws IOException {
    // ...
}
```

Per le eccezioni **unchecked** la dichiarazione `throws` **è facoltativa**; per chiarezza, la si può aggiungere solo nei casi in cui l’eccezione è particolarmente inattesa o significativa per chi usa il metodo.

**Rilancio di un’eccezione:** un metodo può catturare un’eccezione ma decidere di non saperla gestire, limitandosi a incapsularla in un’eccezione logica o semplicemente a **rilanciarla** (con `throw e;`). In questo caso, se è checked, deve comunque dichiarare `throws` (o il nuovo tipo).
### Eccezioni Personalizzate
Creare nuovi tipi di eccezione aiuta a descrivere con precisione il problema e permette di distinguere in fase di `catch` situazioni diverse (ad es. `ImpossibleTriangleException`, `BadFileFormatException`).

- Per un’eccezione **checked**, estendere direttamente `Exception`.
- Per un’eccezione **unchecked**, estendere `RuntimeException` (o una sua sottoclasse come `IllegalArgumentException`).

Tipicamente si forniscono costruttori che accettano un messaggio e, se si vuole mantenere traccia della causa originale, un `Throwable` (l’eccezione fisica).

```java
public class ImpossibleTriangleException extends IllegalArgumentException {
    public ImpossibleTriangleException() { super(); }
    public ImpossibleTriangleException(String message) { super(message); }
    public ImpossibleTriangleException(String message, Throwable cause) { super(message, cause); }
}
```

**Uso:**
```java
if (a >= b + c || b >= a + c || c >= a + b) {
    throw new ImpossibleTriangleException(
        "Lati impossibili: " + a + ", " + b + ", " + c);
}
```
### Incapsulamento di Eccezioni (fisiche → logiche)
Spesso si intercetta un’eccezione “fisica” (es. `FileNotFoundException`) per convertirla in un’eccezione “logica” che abbia più significato nel contesto dell’applicazione (es. `ImageNotFoundException`). Ciò si realizza catturando l’eccezione originale e costruendo la nuova eccezione passandole la causa con il costruttore apposito. In questo modo lo stack della causa rimane accessibile.

```java
public void caricaImmagine(String percorso) throws ImageNotFoundException {
    try {
        FileReader f = new FileReader(percorso);
        // ...
    } catch (FileNotFoundException e) {
        throw new ImageNotFoundException("Immagine non trovata: " + percorso, e);
    }
}
```
### Verifica delle Precondizioni con java.util.Objects
La classe di utilità **`java.util.Objects`** offre metodi statici per controllare i parametri in modo sintetico e standardizzato:
- `requireNonNull(T obj)` lancia `NullPointerException` se `obj` è `null`.
- `requireNonNull(T obj, String message)` con messaggio personalizzato.
- `checkIndex(...)` e `checkFromToIndex(...)` per validare indici e intervalli, lanciano `IndexOutOfBoundsException`.
Questi metodi vengono impiegati spesso all’inizio di un metodo o costruttore per validare le precondizioni.

```java
public void setNome(String nome) {
    this.nome = Objects.requireNonNull(nome, "Il nome non può essere null");
}
```

Esempio Completo: Lettura di un File con Eccezioni Personalizzate
```java
/**
 * Legge il contenuto di un file di testo.
 *
 * @param percorso percorso del file
 * @return contenuto del file come stringa
 * @throws FileNonTrovatoException se il file non esiste
 * @throws LetturaFileException per altri errori di I/O
 */
public String leggiFile(String percorso) throws FileNonTrovatoException, LetturaFileException {
    StringBuilder contenuto = new StringBuilder();

    // try-with-resources: il reader viene chiuso automaticamente
    try (BufferedReader reader = new BufferedReader(new FileReader(percorso))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            contenuto.append(linea).append("\n");
        }
    } catch (FileNotFoundException e) {
        // incapsulo l'eccezione fisica in una logica specifica
        throw new FileNonTrovatoException("File non trovato: " + percorso, e);
    } catch (IOException e) {
        throw new LetturaFileException("Errore durante la lettura del file", e);
    }
    return contenuto.toString();
}
```

**Definizione delle eccezioni personalizzate:**
```java
public class FileNonTrovatoException extends Exception {
    public FileNonTrovatoException(String msg, Throwable cause) { super(msg, cause); }
}

public class LetturaFileException extends Exception {
    public LetturaFileException(String msg, Throwable cause) { super(msg, cause); }
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
## Il pattern Factory
Il pattern **Factory** è un design pattern creazionale che incapsula la logica di costruzione degli oggetti, nascondendone i dettagli all'utente. Viene utilizzato quando:
- La **costruzione di un oggetto è complessa** e richiede molti parametri.
- Si vogliono **applicare regole o politiche specifiche durante la creazione**.
- Si desidera **restituire oggetti di tipi diversi ma compatibili in base al contesto**.
**Caratteristiche principali**
- La fabbrica espone uno o più **metodi factory statici** (tipicamente chiamati `of()`, `valueOf()`, o in passato `getXYZ()`).
- I costruttori della classe dell'oggetto vengono resi non pubblici, così che solo la fabbrica possa istanziare l'oggetto.
- La fabbrica può essere incorporata nella stessa classe dell'oggetto (parte statica) o essere una classe separata.

es.
```java
public class Prodotto {
    private String nome;
    private double prezzo;

    // Costruttore privato
    private Prodotto(String nome, double prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
    }

    // Metodo factory statico
    public static Prodotto of(String nome, double prezzo) {
        // eventuali controlli o logiche aggiuntive
        if (prezzo < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo");
        }
        return new Prodotto(nome, prezzo);
    }
}

// Utilizzo
Prodotto p = Prodotto.of("Laptop", 999.99);
```

## Cultura locale `Locale`
La classe `java.util.Locale` rappresenta una specifica cultura locale (lingua + paese) e definisce le regole per la formattazione e il parsing di dati come **numeri, valute, date, orari.**

**Componenti di un Locale**
- **Lingua**: codice ISO a due lettere minuscole (es. `it`, `en`, `fr`).
- **Paese**: codice ISO a due lettere maiuscole (es. `IT`, `US`, `CH`).
Esempi: `it_IT` (italiano Italia), `it_CH` (italiano Svizzera), `en_US` (inglese USA), `en_GB` (inglese Regno Unito).

**Ottenere un oggetto Locale**
- **Costanti predefinite** (solo per i casi più comuni):
  ```java
  Locale.ITALY, Locale.US, Locale.CANADA_FRENCH, Locale.UK, ...
  ```
- **Costruttori** (fino a Java 18):
  ```java
  Locale l = new Locale("it", "CH"); // italiano Svizzera
  ```
- **Metodi factory** (da Java 19):
  ```java
  Locale l1 = Locale.of("it", "CH");
  Locale l2 = Locale.forLanguageTag("it-CH");
  ```
  
**Locale predefinito e disponibili**
- **Locale predefinito** del sistema:
  ```java
  Locale defaultLocale = Locale.getDefault();
  ```
- **Tutti i Locale disponibili** nella JVM:
  ```java
  Locale[] available = Locale.getAvailableLocales();
  ```

es.
```java
Locale italia = Locale.ITALY;                 // it_IT
Locale svizzeraItaliana = Locale.of("it", "CH"); // it_CH
Locale usa = Locale.US;                        // en_US

System.out.println(italia.getDisplayName());   // Italian (Italy)
System.out.println(usa.getDisplayName());      // English (United States)
```
### Formattatori numerici: `NumberFormat`
La classe `java.text.NumberFormat` fornisce metodi per formattare e analizzare numeri, percentuali e valute in base a una specifica cultura locale. Essa stessa funge da factory per i propri oggetti.

I metodi factory principali sono:
- `NumberFormat.getNumberInstance(Locale locale)` – per numeri generici.
- `NumberFormat.getPercentInstance(Locale locale)` – per percentuali.
- `NumberFormat.getCurrencyInstance(Locale locale)` – per valute.
*Se non si specifica un Locale, viene usato quello predefinito.*

es.
```java
double x = 43.12345678;
double y = 0.7;
double z = 13456.78;

NumberFormat fN = NumberFormat.getNumberInstance(Locale.ITALY);
NumberFormat fP = NumberFormat.getPercentInstance(Locale.ITALY);
NumberFormat fV = NumberFormat.getCurrencyInstance(Locale.ITALY);

System.out.println(fN.format(x)); // 43,123 (a seconda delle cifre decimali predefinite)
System.out.println(fP.format(y)); // 70%
System.out.println(fV.format(z)); // 13.456,78 € (nota: il simbolo € è in fondo)
```

È possibile controllare il numero minimo e massimo di cifre frazionarie:
```java
NumberFormat fN = NumberFormat.getNumberInstance(Locale.CANADA);
fN.setMaximumFractionDigits(2);
System.out.println(fN.format(43.12345678)); // 43.12
```

**Differenze tra culture**
Lo stesso numero viene formattato in modo diverso a seconda del Locale:
```java
double valore = 12345.678;

NumberFormat it = NumberFormat.getNumberInstance(Locale.ITALY);
NumberFormat us = NumberFormat.getNumberInstance(Locale.US);
NumberFormat frCA = NumberFormat.getNumberInstance(Locale.CANADA_FRENCH);

System.out.println(it.format(valore)); // 12.345,678
System.out.println(us.format(valore)); // 12,345.678
System.out.println(frCA.format(valore)); // 12 345,678  (nota: spazio come separatore migliaia)
```
**Attenzione**: il separatore delle migliaia per `Locale.CANADA_FRENCH` è uno spazio **hard** (non divisibile), non un semplice spazio ASCII.

**Parsing di stringhe numeriche**
I formattatori supportano anche l'operazione inversa: convertire una stringa (formattata secondo le regole di una certa cultura) in un oggetto `Number` tramite il metodo `parse(String source)`.

**Metodo `parse`**
```java
NumberFormat fV = NumberFormat.getCurrencyInstance(Locale.US);
try {
    Number n = fV.parse("$123.56");
    double d = n.doubleValue();
    System.out.println(d); // 123.56
} catch (ParseException e) {
    e.printStackTrace();
}
```
Il metodo restituisce un `Number` (che può essere convertito in `double`, `float`, `int`, ecc.) e può lanciare un'eccezione `ParseException` se la stringa non è conforme.

Esempi con jshell (senza gestione eccezioni)
```java
jshell> NumberFormat fV = NumberFormat.getCurrencyInstance(Locale.US)
jshell> fV.parse("$123.456789987654321")
$3 ==> 123.45678998765432

jshell> double d = n.doubleValue()
d ==> 123.45678998765432
```
**il parsing è rigoroso**
Con culture diverse il comportamento cambia e può essere inaspettato se la stringa non rispetta esattamente il formato previsto.

**Esempio problematico con Locale.ITALY e percentuali:**
```java
NumberFormat fP = NumberFormat.getPercentInstance(Locale.ITALY);
// La stringa "70%" viene interpretata erroneamente (ignora il % e restituisce 70, non 0.7)
Number n = fP.parse("70%"); // ATTENZIONE: risultato 70, non 0.7!
```

**Esempio con Locale.CANADA_FRENCH**:
```java
NumberFormat fP = NumberFormat.getPercentInstance(Locale.CANADA_FRENCH);
fP.setMinimumFractionDigits(2);
String formatted = fP.format(0.7235); // "72,35 %" (con spazio hard prima di %)
// Per fare il parsing corretto, la stringa deve contenere lo spazio hard e la virgola
Number n = fP.parse("72,35 %"); // spazio non divisibile
```
Se si omette lo spazio o si usa uno spazio normale, il parsing fallisce.

il parsing è delicato; in questa fase è meglio limitarsi alla formattazione (output) e rimandare lo studio approfondito del parsing a quando si affronterà la gestione delle eccezioni.
### Formattatori personalizzati con `DecimalFormat`
è possibile creare un formattatore personalizzato usando la classe `java.text.DecimalFormat`.

Il pattern utilizza simboli speciali:
- `¤` (U+00A4) – simbolo di valuta generico (verrà sostituito con il simbolo locale).
- `#` – cifra opzionale (non viene mostrata se zero iniziale).
- `0` – cifra obbligatoria (viene mostrata anche se zero).
- `,` – separatore di gruppo (migliaia).
- `.` – separatore decimale.
**Nota**: nel pattern si usano i simboli anglosassoni (`.` per il decimale, `,` per le migliaia), ma il formattatore li sostituirà automaticamente con i separatori della cultura locale al momento della formattazione.

Es
```java
DecimalFormat f = new DecimalFormat("¤ #,##0.##");
f.setCurrency(Currency.getInstance("EUR"));

System.out.println(f.format(1234.567));   // € 1.234,57
System.out.println(f.format(-1234.567));  // € -1.234,57
System.out.println(f.format(0.567));      // € 0,57
System.out.println(f.format(12345678.91234)); // € 12.345.678,91
```
È possibile specificare due pattern separati da un punto e virgola: il primo per i valori positivi, il secondo per i negativi.
```java
DecimalFormat f = new DecimalFormat("¤ #,##0.##; ¤ -#,##0.##");
f.setCurrency(Currency.getInstance("EUR"));

System.out.println(f.format(1234.567));   // € 1.234,57
System.out.println(f.format(-1234.567));  // € -1.234,57
```

A partire da **Java 9**, il JDK ha adottato il database Unicode **CLDR** (Common Locale Data Repository) come database predefinito per le informazioni sulle culture locali. Questo cambiamento ha introdotto una modifica significativa per la valuta in **Locale.ITALY**:
- **Fino a Java 8**: il simbolo dell'euro veniva posizionato **prima** dell'importo (es. `€ 1.234,56`).
- **Da Java 9 in poi**: il simbolo dell'euro viene posizionato **dopo** l'importo (es. `1.234,56 €`), seguendo le convenzioni CLDR.
## Java.time
La gestione del tempo nelle applicazioni software è complessa per vari motivi:
- **Concetti relativi vs. assoluti**: "ci vediamo alle 10" (relativo al luogo) vs. "l'aereo parte alle 11:30 GMT+1" (assoluto).
- **Convenzioni culturali diverse**: formati di data, nomi dei mesi, calendari differenti (gregoriano, giuliano, ebraico, islamico...).
- **Unità di misura variabili**: un mese può durare 28, 29, 30 o 31 giorni; un anno può essere bisestile.

**Pattern Factory in java.time**
Tutte le classi principali di `java.time` seguono due assunti fondamentali:
1. **Oggetti immutabili**: una volta creati, non possono più essere modificati. Qualsiasi operazione che sembra modificarli restituisce in realtà una **nuova istanza**.
2. **Costruzione indiretta tramite factory**: i costruttori non sono pubblici. Si utilizzano metodi factory statici (principalmente `of()`, ma anche `now()`, `from()`, `parse()`, ecc.) per creare oggetti.
Questo approccio garantisce:
- Controllo sulla creazione (evita duplicati, applica validazioni).
- Chiarezza e leggibilità del codice.

**Esempi di factory methods:**
```java
// Invece di "new LocalDate(2020, 12, 25)" (NON FUNZIONA)
LocalDate xmas = LocalDate.of(2020, 12, 25);

Month m = Month.of(10);               // OCTOBER
DayOfWeek d = DayOfWeek.of(1);         // MONDAY

LocalTime noon = LocalTime.of(12, 0);
LocalTime now = LocalTime.now();

LocalDateTime nowDateTime = LocalDateTime.now();
```
### Concetti relativi (locali)
I concetti relativi rappresentano data e/o orario **senza riferimento a un fuso orario**. Sono utili per esprimere eventi che hanno significato in un contesto locale.

| Classe          | Descrizione                                | Esempio di formato    |
| --------------- | ------------------------------------------ | --------------------- |
| `LocalDate`     | Data (anno, mese, giorno)                  | `2020-12-25`          |
| `LocalTime`     | Orario (ora, minuto, secondo, nanosecondo) | `12:00:00`            |
| `LocalDateTime` | Data e orario combinati                    | `2020-12-25T12:00:00` |

**Creazione:**
```java
LocalDate dataNascita = LocalDate.of(1990, 5, 15);
LocalTime oraLezione = LocalTime.of(9, 30);
LocalDateTime dataOra = LocalDateTime.of(2026, 3, 17, 14, 0);
```

**Metodi accessor:**
```java
LocalDate oggi = LocalDate.now();
int giorno = oggi.getDayOfMonth();
Month mese = oggi.getMonth();           // restituisce un enum Month
int anno = oggi.getYear();
int giornoAnno = oggi.getDayOfYear();   // 1..366
DayOfWeek giornoSettimana = oggi.getDayOfWeek(); // enum DayOfWeek
boolean bisestile = oggi.isLeapYear();
```

**Enumerativi: Month e DayOfWeek**
`Month` e `DayOfWeek` sono enumerazioni che facilitano la gestione di mesi e giorni.
```java
Month m = Month.APRIL;
System.out.println(m.getValue());          // 4
System.out.println(m.getDisplayName(...)); // "aprile" (se si usa un formattatore)

DayOfWeek d = DayOfWeek.MONDAY;
System.out.println(d.getValue());          // 1 (lunedì = 1, domenica = 7)
```
#### Period: durata relativa
`Period` rappresenta un lasso di tempo in **anni, mesi, giorni**. È "volutamente impreciso" perché anni e mesi non hanno durata fissa.

**Creazione:**
```java
Period dueMesiTreGiorni = Period.ofMonths(2).plusDays(3);  // P2M3D
Period unAnno = Period.ofYears(1);                         // P1Y
```

**Differenza tra due date:**
```java
LocalDate inizio = LocalDate.of(2025, 9, 15);
LocalDate fine = LocalDate.of(2026, 6, 10);
Period periodo = Period.between(inizio, fine);
System.out.println(periodo.getYears() + " anni, " + periodo.getMonths() + " mesi, " + periodo.getDays() + " giorni");
```

**Sommare/sottrarre un Period a una data:**
```java
LocalDate nuovaData = (LocalDate) periodo.addTo(inizio);   // richiede cast (vedi spiegazione sotto)
```
**Perché il cast?** I metodi `addTo` e `subtractFrom` lavorano su oggetti `Temporal` (interfaccia generica). Il tipo effettivo dipende dall'input: se passo una `LocalDate`, ottengo una `LocalDate`; se passo una `LocalDateTime`, ottengo una `LocalDateTime`. Il cast è necessario per riottenere il tipo specifico.
### Concetti assoluti
I concetti assoluti rappresentano un **istante preciso sulla linea del tempo**, indipendente dal luogo in cui ci si trova. Richiedono l'indicazione del fuso orario o dell'offset da UTC.

**Instant**: punto sulla linea del tempo
`Instant` è il concetto più "fisico": è il numero di nanosecondi trascorsi dalla mezzanotte del 1° gennaio 1970 UTC (epoca Unix). Non ha nulla a che fare con calendari, mesi o giorni umani.
```java
Instant adesso = Instant.now();   // 2026-03-17T14:30:00.123456789Z (formato ISO)
```

#### Duration: durata in nanosecondi
`Duration` rappresenta un lasso di tempo **preciso** in nanosecondi. Tipicamente si usa per differenze tra `Instant`.

**Creazione:**
```java
Duration unGiorno = Duration.ofDays(1);
Duration dueOre = Duration.ofHours(2);
Duration complessa =  Duration.ofDays(1)
                              .plusHours(3)
                              .minusMinutes(4)
                              .minusSeconds(10); // fluent interface
```

**Differenza tra due Instant:**
```java
Instant inizio = Instant.now();
// ... esecuzione di un'operazione
Instant fine = Instant.now();
Duration durata = Duration.between(inizio, fine);
System.out.println("Durata: " + durata.toMillis() + " ms");
```

**Metodi per estrarre il totale in varie unità:**
```java
long totaleOre = durata.toHours();
long totaleMinuti = durata.toMinutes();
long totaleSecondi = durata.toSeconds();
long totaleMillis = durata.toMillis();
```
#### OffsetDateTime e ZonedDateTime
Queste classi aggiungono a una data/ora locale l'informazione necessaria per renderla assoluta.

| Classe           | Descrizione                                                                 | Esempio                              |
|------------------|-----------------------------------------------------------------------------|--------------------------------------|
| `OffsetDateTime` | Data/ora + offset fisso rispetto a UTC (es. +01:00)                         | `2026-03-17T15:30:00+01:00`          |
| `ZonedDateTime`  | Data/ora + fuso orario (es. Europe/Rome), che include regole per ora legale| `2026-03-17T15:30:00+01:00 Europe/Rome` |
**Creazione:**
```java
LocalDateTime inizioLezioni = LocalDateTime.of(2026, 2, 17, 9, 0);

// OffsetDateTime con offset +1 (ora solare Italia)
OffsetDateTime offsetInizio = OffsetDateTime.of(inizioLezioni, ZoneOffset.ofHours(1));

// ZonedDateTime con fuso CET (Central European Time)
ZonedDateTime zonedInizio = ZonedDateTime.of(inizioLezioni, ZoneId.of("Europe/Rome"));
```
**Ottenere fusi orari:**
- Usare **nomi estesi** come `"Europe/Rome"`, `"America/New_York"`. Le sigle a tre lettere (`CET`, `EST`) sono deprecate perché ambigue.
- `ZoneId.getAvailableZoneIds()` per elencare tutti i fusi supportati.

**Conversione tra classi:**
```java
ZonedDateTime zdt = ZonedDateTime.now();
OffsetDateTime odt = zdt.toOffsetDateTime();
Instant instant = zdt.toInstant();            // da ZonedDateTime a Instant
LocalDate data = zdt.toLocalDate();
LocalTime ora = zdt.toLocalTime();

// Da LocalDateTime a Instant (serve l'offset)
LocalDateTime ldt = LocalDateTime.now();
Instant ist = ldt.toInstant(ZoneOffset.ofHours(1));
```
### Operazioni comuni su date e orari
Tutte le classi di `java.time` offrono metodi per manipolare le date in modo **immutabile**: restituiscono sempre una nuova istanza.

**Aggiungere/sottrarre tempo: `plusXxx()` / `minusXxx()`**
```java
LocalDate oggi = LocalDate.now();
LocalDate domani = oggi.plusDays(1);
LocalDate meseProssimo = oggi.plusMonths(1);
LocalDate annoScorso = oggi.minusYears(1);

LocalTime ora = LocalTime.now();
LocalTime fraUnOra = ora.plusHours(1);
LocalTime dieciMinutiFa = ora.minusMinutes(10);
```

**Modificare componenti specifici: `withXxx()`**
```java
LocalDate data = LocalDate.of(2026, 3, 17);
LocalDate stessoGiornoMeseDiverso = data.withMonth(12).withDayOfMonth(25); // 2026-12-25

LocalTime ora = LocalTime.of(10, 30);
LocalTime stessaOraSecondiZero = ora.withSecond(0);
```

**Confronti: `isBefore()`, `isAfter()`, `isEqual()`**
```java
LocalDate d1 = LocalDate.of(2026, 1, 1);
LocalDate d2 = LocalDate.of(2026, 12, 31);

if (d1.isBefore(d2)) {
    System.out.println("d1 viene prima di d2");
}

ZonedDateTime z1 = ZonedDateTime.now();
ZonedDateTime z2 = z1.plusHours(2);
if (z2.isAfter(z1)) {
    System.out.println("z2 è successivo a z1");
}
```
es.
Quanto manca al prossimo compleanno?
```java
public static Period toNextBirthDay(LocalDate dateOfBirth) {
    LocalDate today = LocalDate.now();
    int currentYear = today.getYear();
    LocalDate nextBirthDay = dateOfBirth.withYear(currentYear);
    if (nextBirthDay.isBefore(today)) {
        nextBirthDay = dateOfBirth.withYear(currentYear + 1);
    }
    return Period.between(today, nextBirthDay);
}

// Utilizzo
LocalDate mioCompleanno = LocalDate.of(1990, 5, 15);
Period attesa = toNextBirthDay(mioCompleanno);
System.out.println("Giorni: " + attesa.getDays() + ", Mesi: " + attesa.getMonths());
```
Effetto dell'ora legale
```java
ZonedDateTime inizio = ZonedDateTime.of(2026, 3, 25, 10, 0, 0, 0, ZoneId.of("Europe/Rome"));
ZonedDateTime fine = inizio.plusDays(10);

Duration differenza = Duration.between(inizio, fine);
System.out.println(differenza.toHours()); // 239 ore (non 240!) perché a fine marzo scatta l'ora legale
```
Durata tra due date espresse come LocalDateTime (ma attenzione!)
```java
LocalDate d1 = LocalDate.of(2024, 3, 12);
LocalDateTime dt1 = LocalDateTime.of(d1, LocalTime.now());

// Calcolo durata tra date con offset fisso
OffsetDateTime od1 = OffsetDateTime.of(dt1, ZoneOffset.ofHours(1));

long giorni1 = Duration.between(od1.plusMonths(3), od1.plusMonths(5)).toDays(); // 61
long giorni2 = Duration.between(od1.plusMonths(4), od1.plusMonths(6)).toDays(); // 62
```
La differenza è dovuta ai diversi numeri di giorni nei mesi (giugno-agosto vs. luglio-settembre).
### Cheatsheet
- **`of` / `ofXxx`** → factory methods per creare oggetti.
- **`now`** → ottiene l'oggetto corrispondente all'istante corrente (o data/ora corrente).
- **`getXxx`** → restituisce una componente (es. `getYear`, `getHour`).
- **`withXxx`** → modifica una componente (es. `withYear`, `withMinute`).
- **`plusXxx` / `minusXxx`** → aggiunge/sottrae una quantità di tempo.
- **`isBefore` / `isAfter` / `isEqual`** → confronti.
- **`toXxx`** → converte in un altro tipo (es. `toLocalDate`, `toInstant`).
**Importante**: tutti i metodi che sembrano modificare l'oggetto restituiscono **una nuova istanza**; l'originale rimane invariato.

## Formattatori per date e orari
La classe principale per la formattazione di date e orari in Java è `DateTimeFormatter`, presente nel package `java.time.format`. A differenza dei formattatori numerici (`NumberFormat`), qui:
- La costruzione avviene tramite **metodi factory** (non `new`).
- È disponibile una **duplice sintassi**:
```java
String s = formatter.format(value);   // stile classico
String s = value.format(formatter);   // stile fluente (preferibile)
```

**Creazione di formattatori localizzati**
Per formattare secondo le convenzioni di una cultura locale, si usano i metodi factory:

|Metodo|Destinazione|
|---|---|
|`ofLocalizedDate(FormatStyle)`|solo data (`LocalDate`)|
|`ofLocalizedTime(FormatStyle)`|solo orario (`LocalTime`)|
|`ofLocalizedDateTime(FormatStyle)`|data+orario (`LocalDateTime`) – stesso stile per entrambi|
|`ofLocalizedDateTime(dateStyle, timeStyle)`|stili distinti per data e orario|
**FormatStyle** è un enum con quattro valori: `SHORT`, `MEDIUM`, `LONG`, `FULL`.
- Per le **date** sono tutti ammessi.
- Per gli **orari** sono ammessi solo `SHORT` e `MEDIUM` (l'uso di `LONG`/`FULL` su `LocalTime` genera eccezione).
**Nota**: il formattatore appena creato usa il **Locale di default** della JVM. Per cambiare cultura si usa il metodo `withLocale(Locale)`:
```java
DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                       .withLocale(Locale.ITALY);
```

Es.
```java
// dichiarazioni
System.out.println(d.format(shortF));   // 04/03/26
System.out.println(d.format(mediumF));  // 4 mar 2026
System.out.println(d.format(longF));    // 4 marzo 2026
System.out.println(d.format(fullF));    // mercoledì 4 marzo 2026
```
Cambiando il Locale:
```java
Locale fr = Locale.FRANCE;
System.out.println(d.format(shortF.withLocale(fr)));   // 04/03/2026
System.out.println(d.format(mediumF.withLocale(fr)));  // 4 mars 2026
System.out.println(d.format(longF.withLocale(fr)));    // 4 mars 2026
System.out.println(d.format(fullF.withLocale(fr)));    // mercredi 4 mars 2026
```

**Formattazione di `LocalTime`**
Solo `SHORT` e `MEDIUM` sono validi:
```java
//dichiarazini
System.out.println(t.format(shortTime));   // 18:37
System.out.println(t.format(mediumTime));  // 18:37:41
```

**Formattazione di `LocalDateTime`**
Con stile unico per data e ora:
```java
LocalDateTime dt = LocalDateTime.of(2026, 3, 4, 18, 37, 41);
DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
System.out.println(dt.format(f));  // 04/03/26, 18:37
```

Con stili distinti:
```java
DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);
System.out.println(dt.format(f));  // 04/03/26, 18:37:41
```
Sono possibili tutte le 8 combinazioni (4 stili data × 2 stili ora).

**Formattazione di date e orari assoluti (`ZonedDateTime`, `OffsetDateTime`)**
Le classi `ZonedDateTime` e `OffsetDateTime` si formattano con gli stessi metodi, ma con una differenza importante: per `ZonedDateTime` gli stili `LONG` e `FULL` dell'ora mostrano anche il **fuso orario** (e l'ora legale se attiva). Per `OffsetDateTime` invece lo stile `LONG`/`FULL` non aggiunge informazioni aggiuntive perché l'offset è già noto.
```java
ZonedDateTime zdt = ZonedDateTime.now();   // es. 2026-03-04T18:37:41+01:00[Europe/Rome]
DateTimeFormatter longTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG);
System.out.println(zdt.format(longTime));  // 18:37:41 CET  (oppure CEST se ora legale)
```

**Attenzione**: la rappresentazione del fuso dipende da come è stato creato lo `ZonedDateTime`. Usare `ZoneId.of("Europe/Rome")` è più preciso di `"CET"` o `"GMT+1"`. La forma `GMT±n` produce un output più povero (es. `18:37:41 GMT+01:00`).

**Formattatori personalizzati con pattern (`ofPattern`)**
Se gli stili localizzati non soddisfano, si può definire un pattern con `DateTimeFormatter.ofPattern(String pattern, Locale)`.

**Esempio**: data in formato `dd/MM/yyyy`:
```java
DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
System.out.println(LocalDate.now().format(f)); // 04/03/2026
```

|Simbolo|Significato|Esempi|
|---|---|---|
|`y`|anno|`yy` → 26, `yyyy` → 2026|
|`M`|mese (numero/testo)|`M` → 3, `MM` → 03, `MMM` → mar, `MMMM` → marzo|
|`d`|giorno del mese|`d` → 4, `dd` → 04|
|`E`|giorno della settimana (testo)|`E` → mer, `EEEE` → mercoledì|
|`h`|ora in formato 12 ore (1-12)|`h` → 6|
|`H`|ora in formato 24 ore (0-23)|`H` → 18|
|`m`|minuti|`m` → 37|
|`s`|secondi|`s` → 41|
|`a`|AM/PM|`a` → PM|
|`z`|nome del fuso orario|`z` → CET, `zzzz` → Central European Time|
|`Z`|offset di zona (stile ISO)|`Z` → +0100|
|`'`|testo letterale|`'alle'` → "alle"|
**Parsing di date e orari**
Il parsing può essere effettuato in due modi:
- Tramite il formattatore (restituisce un `TemporalAccessor`)
```java
DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                         .withLocale(Locale.ITALY);
TemporalAccessor ta = fmt.parse("12/02/26");
LocalDate d = LocalDate.from(ta);       // 2026-02-12
```
Il `TemporalAccessor` è un contenitore generico; lo si converte con i metodi `from()` delle classi specifiche (`LocalDate.from()`, `LocalTime.from()`, `LocalDateTime.from()`).
- Tramite i metodi `parse` delle classi `LocalDate`, `LocalTime`, ecc.
```java
DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
                                         .withLocale(Locale.ITALY);
LocalDateTime dt = LocalDateTime.parse("12/02/26, 12:30", fmt);
LocalDate d = LocalDate.parse("12/02/26, 12:30", fmt);    // solo data
LocalTime t = LocalTime.parse("12/02/26, 12:30", fmt);    // solo ora
```
**Vantaggio**: il tipo di ritorno è direttamente quello desiderato.
**Attenzione**: il parsing può sollevare eccezioni se la stringa non è conforme al formato e alla cultura. In jshell le eccezioni vengono gestite automaticamente, ma in un programma reale vanno gestite con `try-catch`.

Es. parsing del nome del mese
Obiettivo: ottenere il numero del mese a partire dal nome del mese in una data lingua.
```java
public static int getMonth(String monthName, Locale locale) {
    DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM", locale);
    TemporalAccessor ta = f.parse(monthName);
    return ta.get(ChronoField.MONTH_OF_YEAR);
}
// Utilizzo
System.out.println(getMonth("Maggio", Locale.ITALY));   // 5
System.out.println(getMonth("May", Locale.ENGLISH));   // 5
System.out.println(getMonth("mai", Locale.of("nl", "NL"))); // 5 (olandese)
```
**Nota**: il pattern `"MMMM"` richiede il nome completo del mese, in maiuscolo/minuscolo come previsto dalla lingua (es. in italiano "Maggio", in inglese "May", in olandese "mei"). Se necessario, si può normalizzare la stringa prima del parsing.

**Formattatori ISO predefiniti**
`DateTimeFormatter` fornisce costanti per formati ISO comuni, adatti allo scambio machine-to-machine:

|Costante|Esempio|
|---|---|
|`ISO_LOCAL_DATE`|`2026-03-04`|
|`ISO_LOCAL_TIME`|`18:37:41`|
|`ISO_LOCAL_DATE_TIME`|`2026-03-04T18:37:41`|
|`ISO_OFFSET_DATE_TIME`|`2026-03-04T18:37:41+01:00`|
|`ISO_ZONED_DATE_TIME`|`2026-03-04T18:37:41+01:00[Europe/Rome]`|
|`BASIC_ISO_DATE`|`20260304`|
|`RFC_1123_DATE_TIME`|`Thu, 4 Mar 2026 18:37:41 GMT`|
```java
ZonedDateTime zdt = ZonedDateTime.now();
System.out.println(zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
```
### Cheatsheet
- **Costruzione**: `DateTimeFormatter.ofLocalizedDate/Style/DateTime` (con `FormatStyle`) oppure `ofPattern`.
- **Localizzazione**: usare `withLocale(Locale)`.
- **Formattazione**: `formatter.format(value)` o `value.format(formatter)`.
- **Parsing**: `LocalDate.parse(string, formatter)` o `formatter.parse(string)` con conversione tramite `TemporalAccessor`.
- **Pattern personalizzati**: simboli come `yyyy`, `MM`, `dd`, `HH`, `mm`, `ss`, `MMMM`, `EEEE`, `z`, `Z`.
- **Stili per orari**: solo `SHORT` e `MEDIUM` per `LocalTime`; per `ZonedDateTime` sono ammessi anche `LONG` e `FULL` (mostrano il fuso).
- **ISO predefiniti**: utili per scambio dati.
## Sistemi di software
Un sistema a oggetti è **costituito da classi e oggetti che interagiscono tra loro**. La progettazione di un sistema che risolve un problema richiede diverse fasi:
1. **Analisi del problema** – partendo dai requisiti si costruisce un modello del problema (architettura logica).
2. **Pianificazione del collaudo** e del lavoro.
3. **Progettazione della soluzione** – si definisce l’architettura del sistema (modello della soluzione).
4. **Implementazione** – scrittura del codice.
5. **Collaudo** – verifica dell’implementazione.

**Modelli e viste**
I modelli sono rappresentazioni (spesso grafiche) del sistema. Si distinguono:
- **Architettura logica** – descrive il problema, indipendentemente dalla soluzione. Si articola in viste:
    - **Struttura**: macro-blocchi derivanti dai requisiti (diagramma delle classi generale).
    - **Interazione**: relazioni dinamiche tra blocchi (diagrammi di sequenza).
    - **Comportamento** osservabile dei singoli blocchi (diagrammi degli stati).
- **Architettura del sistema** – descrive la soluzione specifica. Viste analoghe ma di dettaglio.
    - Struttura: classi di progetto (anche 10-100 volte più classi).
    - Interazione: dettaglio di come avviene.
    - Comportamento di dettaglio.
### **UML** (Unified Modeling Language) 
è il linguaggio grafico standard per esprimere questi modelli. Supporta tutte le fasi e permette forward/reverse engineering.
#### Classi
![[113 Sistemi di Software a Oggetti.pdf#page=9&rect=73,165,641,355|113 Sistemi di Software a Oggetti, p.9|400]]
#### Dipendenza
Una classe **dipende** da un’altra se la usa (ad esempio come parametro, tipo di ritorno, o creazione locale). In UML si rappresenta con una **freccia tratteggiata** etichettata `<<use>>`.
- CASO 1: un’operazione della classe A richiede come argomento una istanza della classe B
```java
void fun1(B b) { … usa b … }
```
- CASO 2: un’operazione della classe A restituisce un oggetto di tipo B
```java
B fun2(…) { B b; … return b;}
```
- CASO 3: un’operazione della classe A utilizza una specifica istanza della classe B, senza che però esista un’associazione tra A e B
```java
void fun3(…) { B b = new B(…); … usa b … }
```
![[113 Sistemi di Software a Oggetti.pdf#page=10&rect=104,213,586,320|113 Sistemi di Software a Oggetti, p.10|400]]
#### Associazioni
Relazione strutturale tra classi. Può essere unidirezionale o bidirezionale, con molteplicità (es. 1, 0.._, 1.._).
fondamentale distinguere i diversi significati del verbo “avere” nelle descrizioni. Esempi:
- _“Una flotta ha un ammiraglio”_ → **relazione generica** (non è parte della flotta).
- _“Una flotta ha delle navi”_ → **aggregazione** (le navi esistono indipendentemente).
- _“Un esagono ha sei vertici”_ → **composizione** (i vertici esistono solo con l’esagono).
- _“Un libro ha delle pagine”_ → **composizione** (le pagine non hanno senso fuori dal libro).

**Associazione generica**
![[113 Sistemi di Software a Oggetti.pdf#page=16&rect=211,258,506,327|113 Sistemi di Software a Oggetti, p.16|200]]
Ogni classe contiene un riferimento all’altra. Se la molteplicità è maggiore di 1, si usa una collezione (array, List, ecc.).
**Esempio**: Ammiraglio e Flotta (un ammiraglio può essere associato a più flotte).
```java
public class Ammiraglio {
    private List<Flotta> flotte; // o array
    // ...
}

public class Flotta {
    private Ammiraglio ammiraglio;
    // ...
}
```

**Aggregazione**
![[113 Sistemi di Software a Oggetti.pdf#page=19&rect=236,206,505,267|113 Sistemi di Software a Oggetti, p.19|200]]
La classe contenitore ha un riferimento a oggetti che esistono indipendentemente. Non è necessario fare copie difensive nei costruttori perché gli oggetti possono essere condivisi.
**Esempio**: Flotta e Nave (una nave può appartenere a più flotte).
```java
public class Flotta {
    private Nave[] navi; // o List<Nave>
    
    public Flotta(Nave[] navi) {
        // NON si fa copia difensiva perché le navi sono indipendenti
        this.navi = navi;
    }
}
```

**Composizione**
![[113 Sistemi di Software a Oggetti.pdf#page=22&rect=248,183,508,247|113 Sistemi di Software a Oggetti, p.22|200]]
**La composizione implica una dipendenza**, ma non viceversa.
Le parti hanno ciclo di vita legato al contenitore. Per garantire l’esclusività, nei costruttori si usa la **copia difensiva** (se gli oggetti sono mutabili) e si impedisce la condivisione.
**Esempio**: Triangolo e Vertici (un triangolo è composto esattamente da tre vertici, che non possono essere condivisi).
```java
public class Triangolo {
    private Vertice[] vertici;
    
    public Triangolo(Vertice[] vertici) {
        // copia difensiva per evitare modifiche esterne
        this.vertici = Arrays.copyOf(vertici, vertici.length);
    }
    
    public Vertice[] getVertici() {
        // restituisce una copia per mantenere l'incapsulamento
        return Arrays.copyOf(vertici, vertici.length);
    }
}
```
se gli oggetti parte sono a loro volta immutabili, la copia difensiva potrebbe non essere necessaria, ma è buona norma per evitare sorprese.

Es. orologio slide pag 35
Es. traghetti slide pag 63
Es. Punti e Poligoni slide pag 75
Finestra pag 91
Esercizi Elezioni e 7 segmenti
## Ereditarietà
Spesso, nello sviluppo software, ci si trova a dover creare un nuovo componente che è molto simile a uno già esistente, ma con qualche funzionalità aggiuntiva o modificata.

**Approcci iniziali (prima di ereditarietà):**
1.  **Copia e Incolla**: Copiare il codice della classe esistente e modificarlo. È una pessima pratica perché duplica codice, rendendo difficile la manutenzione e l'evoluzione.
2.  **Composizione con Adapter**: Creare una nuova classe che contiene (compone) un'istanza della classe esistente.
    *   **Vantaggio**: Riusa la classe esistente senza modificarne il codice sorgente.
    *   **Come funziona**:
        1.  La nuova classe incapsula un oggetto della classe esistente.
        2.  I metodi già presenti vengono delegati all'oggetto incapsulato.
        3.  I nuovi metodi vengono implementati sfruttando l'oggetto incapsulato.
    *   **Pattern Adapter**: Questo approccio è una forma del pattern Adapter, che adatta un'interfaccia esistente a una nuova.

**Esempio: Counter (solo avanti) a CounterDec (avanti/indietro) con Adapter**
```java
// Classe esistente: Counter (solo incremento)
public class Counter {
    private int val;
    public Counter() { val = 1; }
    public Counter(int v) { val = v; }
    public void reset() { val = 0; }
    public void inc() { val++; }
    public int getValue() { return val; }
    public String toString() { return "Counter di valore " + getValue(); }
}

// Nuova classe CounterDec che usa Adapter
public class CounterDec {
    private final Counter c; // Incapsula un Counter

    public CounterDec() { this.c = new Counter(); }
    public CounterDec(int value) { this.c = new Counter(value); }

    // Delega dei metodi esistenti
    public void reset() { c.reset(); }
    public void inc() { c.inc(); }
    public int getValue() { return c.getValue(); }
    public String toString() { return c.toString(); }

    // Nuovo metodo: decremento
    public void dec() {
        // Strategia 1: reset e reincremento (inefficiente)
        int v = c.getValue();
        c.reset();
        for (int i = 1; i <= v - 1; i++) {
            c.inc();
        }

        // Strategia 2: creazione di un nuovo Counter (più efficiente)
        // Nota: richiederebbe che c fosse modificabile (non final)
        // c = new Counter(c.getValue() - 1);
    }
}
```

**Limiti dell'Adapter:**
*   I campi privati della classe incapsulata non sono accessibili.
*   Bisogna scrivere codice di "delega" anche per i metodi che rimangono identici (es. `reset`, `inc`, `getValue`).
*   Non è sempre possibile implementare la nuova funzionalità sfruttando solo i metodi pubblici dell'oggetto incapsulato.
### Inheritance
permette di definire una nuova classe (**sottoclasse, classe derivata**) a partire da una classe esistente (superclasse, classe base), specificando solo le *differenze*.
**Sintassi:** `public class NuovaClasse extends ClasseEsistente { ... }`
![[114-Ereditarieta.pdf#page=5&rect=386,46,709,292|114-Ereditarieta, p.5|300]]

**Cosa può fare la classe derivata:**
*   Aggiungere nuovi campi dati.
*   Aggiungere nuovi metodi.
*   Ridefinire (override) i metodi ereditati, modificarne il comportamento.
**Cosa non può fare:**
*   Eliminare o nascondere campi o metodi ereditati.

Esempio: Counter2 (con decremento) che estende Counter
![[114-Ereditarieta.pdf#page=6&rect=143,217,536,359|114-Ereditarieta, p.6|400]]
```java
// Classe base Counter (da modificare per l'ereditarietà)
public class Counter {
    // Il campo val non può essere private, altrimenti Counter2 non lo vedrà
    protected int val; // <--- Livello di protezione protetto

    public Counter() { val = 1; }
    public Counter(int v) { val = v; }
    public void reset() { val = 0; }
    public void inc() { val++; }
    public int getValue() { return val; }
}

// Classe derivata Counter2
public class Counter2 extends Counter {
    // Aggiunge un nuovo metodo
    public void dec() {
        val--; // Ora val è accessibile perché è protected
    }
    // Non ha bisogno di ridefinire inc, reset, getValue
}
```

 **Il Modificatore `protected`**
Il problema emerso nell'esempio precedente è l'accesso al campo `val` di `Counter` da parte di `Counter2`. Se `val` è `private`, la classe derivata non può accedervi. Se è `public`, si viola l'incapsulamento.

**Soluzione:** `protected` (# in UML)
*   Un membro `protected` è accessibile:
    *   All'interno della stessa classe.
    *   A tutte le classi nello stesso package.
    *   A **tutte le sottoclassi** (anche in package diversi).
**Attenzione:** L'uso di `protected` va fatto con giudizio. Rende i dati accessibili a *tutte* le sottoclassi presenti e future. Un'alternativa più sicura è mantenere i campi `private` e fornire metodi accessor `protected` per le sottoclassi.
```java
public class Counter {
    private int val; // campo privato

    protected int getVal() { return val; } // metodo protetto per leggere
    protected void setVal(int v) { val = v; } // metodo protetto per scrivere

    // ... costruttori e metodi ...
}

public class Counter2 extends Counter {
    public void dec() {
        // setVal(getVal() - 1); // uso dei metodi protetti
        int current = getVal();
        setVal(current - 1);
    }
}
```

**Ereditarietà e Costruttori**
I costruttori **non vengono ereditati**.
Ogni classe è responsabile di inizializzare i propri campi. La classe derivata può avere campi aggiuntivi, e i costruttori della classe base non sanno come inizializzarli.

Meccanismo di costruzione:
1.  Quando si crea un oggetto di una classe derivata, viene chiamato il suo costruttore.
2.  La prima operazione di ogni costruttore della classe derivata è chiamare un costruttore della classe base.
3.  Se non si specifica esplicitamente, il compilatore tenta di chiamare il costruttore di default (senza argomenti) della classe base.
4.  Se la classe base non ha un costruttore di default, si verifica un errore di compilazione. Bisogna quindi chiamare esplicitamente un costruttore della classe base usando `super(...)`.

**La parola chiave `super` (in Java):**
*   `super(...);`: Chiama il costruttore della classe base (deve essere la prima istruzione nel costruttore).
*   `super.metodo()`: Invoca un metodo della classe base (utile se è stato ridefinito).
*   `super.campo`: Accede a un campo della classe base.

**Esempio: Counter2 con costruttori espliciti**

```java
public class Counter2 extends Counter {
    public Counter2() {
        super(); // Chiama Counter() (opzionale, sarebbe implicito)
    }

    public Counter2(int v) {
        super(v); // Chiama Counter(int v) -> OBBLIGATORIO perché non esiste un costruttore di default
    }

    public void dec() {
        val--;
    }
}
```

**Esempio: ColoredCounter (aggiunge un campo colore)**
```java
import java.awt.Color;

public class ColoredCounter extends Counter {
    private Color color; // nuovo campo

    public ColoredCounter(int v) {
        super(v); // Inizializza la parte ereditata (val)
        this.color = Color.BLACK; // Inizializza il nuovo campo
    }

    public ColoredCounter(int v, Color color) {
        super(v);
        this.color = color;
    }

    public Color getColor() { return color; }

    // Ridefinizione di toString
    @Override
    public String toString() {
        // super.toString() chiama il toString di Counter
        return super.toString() + " e colore " + color;
    }

    // Ridefinizione di equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ColoredCounter)) return false;
        ColoredCounter that = (ColoredCounter) obj;
        // super.equals(that) confronta la parte ereditata
        return super.equals(that) && this.color.equals(that.color);
    }
}
```
**Principio:** "Ognuno costruisce ciò che gli compete". Il costruttore di `Counter` inizializza `val`, il costruttore di `ColoredCounter` inizializza `color` e delega a `super` il resto.

**Ridefinizione dei Metodi (Override) e Comportamento**
Una classe derivata può **ridefinire un metodo ereditato per modificarne il comportamento.**

Regole per un override corretto (estensioni conservative):
*   Il metodo ridefinito dovrebbe mantenere la semantica e il contratto del metodo originale.
*   Può estendere il comportamento (es. fare qualcosa in più), ma non sovvertirlo.
es. Non è corretto ridefinire `inc()` in modo che divida il valore per 10 o che faccia un'altra operazione non correlata all'"incremento".

Esempio di estensione conservativa: `Finestra3`
*   La classe base `Finestra` ha un metodo `print(String txt)`.
*   La classe `Finestra3` estende `Finestra2` e ridefinisce `print` per stampare in maiuscolo, ma *chiama comunque il metodo della superclasse* per svolgere il compito principale.

```java
public class Finestra3 extends Finestra2 {
    public Finestra3() { super(); }
    public Finestra3(String titolo) { super(titolo); }

    @Override
    public void print(String txt) {
        super.print(txt.toUpperCase()); // Estensione conservativa
    }
}
```

**Classi e Metodi `final`**
Per impedire che un metodo venga ridefinito o che una classe venga estesa, si usa la parola chiave `final`.
*   `public final void mioMetodo() { ... }`: Questo metodo non può essere sovrascritto in alcuna sottoclasse.
*   `public final class MiaClasse { ... }`: Questa classe non può essere estesa.


Modelliamo il concetto di "Studente" come specializzazione di "Persona".
```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Persona {
    private final String nome;
    private final LocalDate dataNascita;

    public Persona(String nome, LocalDate dataNascita) {
        this.nome = nome;
        this.dataNascita = dataNascita;
    }

    public String getNome() { return nome; }
    public LocalDate getDataNascita() { return dataNascita; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        return "Mi chiamo " + nome + " e sono nato/a il " + formatter.format(dataNascita);
    }
}

public class Studente extends Persona {
    private final int matricola;

    public Studente(String nome, LocalDate dataNascita, int matricola) {
        super(nome, dataNascita); // Inizializza la parte di Persona
        this.matricola = matricola;
    }

    public int getMatricola() { return matricola; }

    @Override
    public String toString() {
        // Estensione conservativa: usa super.toString() e aggiunge la matricola
        return super.toString() + ", matricola " + matricola;
    }
}
```

**Punti chiave:**
*   `Studente` eredita da `Persona` i campi `nome` e `dataNascita` e il metodo `getNome()`.
*   Il costruttore di `Studente` chiama `super(nome, dataNascita)` per inizializzare la parte ereditata.
*   `toString()` è ridefinito per aggiungere la matricola, ma si basa su `super.toString()` per non duplicare la logica di stampa della persona.

Ecco degli appunti dettagliati in italiano basati esclusivamente sui concetti relativi a Java e ai principi generali di programmazione orientata agli oggetti presenti nel PDF, ignorando deliberatamente i riferimenti a Scala, Kotlin e C#.

**Il Principio di Sostituibilità (e la Relazione IS-A)**
- **Concetto Base:** L'ereditarietà definisce una relazione **IS-A** (È-UN).
- **Definizione:** Una classe derivata (es. `Counter2`) estende la classe base (`Counter`) **aggiungendo** funzionalità, **mai togliendone**.
- **Conseguenza:** Dove il codice si aspetta un oggetto della classe base, posso sempre fornire un oggetto della classe derivata.
    - *Analogia:* Non ci si può lamentare se, allo stesso prezzo, si riceve un oggetto "più ricco" di funzionalità.
- **Direzionalità:** La sostituibilità vale solo in una direzione.
    - Posso dare un `Counter2` a chi vuole un `Counter`.
    - **Non posso** dare un semplice `Counter` a chi si aspetta un `Counter2` (perché il `Counter` non ha i metodi aggiuntivi del `Counter2`).

**Subclassing = Subtyping**
Il tipo di una sottoclasse è un **sottotipo** del tipo della sua superclasse. Questa compatibilità di tipo si manifesta in tre modi principali:
1.  **Assegnamenti:** Un riferimento di un tipo base può puntare a un oggetto di un tipo derivato.
    ```java
    Counter c = new Counter2(); // OK
    ```
2.  **Passaggio di Parametri:** Come visto sopra, un metodo che accetta un tipo base può ricevere un'istanza di un tipo derivato.
3.  **Valori di Ritorno:** Un metodo che dichiara di restituire un tipo base può restituire un'istanza di un tipo derivato.
    ```java
    public static Counter creaCounter(int v) {
        // Restituisce un Counter2, che è comunque un Counter
        return new Counter2(v);
    }
    ```

È invece **sbagliato fare il contrario**: assegnare un oggetto base a un riferimento derivato non è permesso senza un cast esplicito (che potrebbe fallire a runtime).
```java
Counter c = new Counter();
Counter2 c2 = c; // ERRORE DI COMPILAZIONE
```

Non tutte le relazioni IS-A che sembrano valide nel mondo reale lo sono nel mondo della programmazione a oggetti, specialmente se gli oggetti sono **mutabili**.

**Es quadrato e rettangolo.**
Implementazione (Problematica):
```java
public class Rettangolo {
    private double base, altezza;

    public Rettangolo(double base, double altezza) {
        setBase(base);
        setAltezza(altezza);
    }

    public void setBase(double base) { this.base = base; }
    public void setAltezza(double altezza) { this.altezza = altezza; }
    // ... altri metodi
}

public class Quadrato extends Rettangolo {
    public Quadrato(double lato) {
        super(lato, lato);
    }

    // Tentativo di "riparare" la mutabilità:
    // Se cambio la base, cambio anche l'altezza per mantenere l'invariante del quadrato.
    @Override
    public void setBase(double base) {
        super.setBase(base);
        super.setAltezza(base); // Effetto collaterale imprevisto!
    }

    @Override
    public void setAltezza(double altezza) {
        super.setBase(altezza); // Effetto collaterale imprevisto!
        super.setAltezza(altezza);
    }
}
```
Un client che usa un `Quadrato` come se fosse un `Rettangolo` avrà un comportamento inaspettato:
```java
public void modificaRettangolo(Rettangolo r) {
    r.setBase(5);
    r.setAltezza(10);
    // Il programmatore si aspetta che l'area ora sia 50.
}

Quadrato q = new Quadrato(4);
modificaRettangolo(q);
// Cosa è successo?
// 1. r.setBase(5) -> in Quadrato ha impostato base=5 E altezza=5
// 2. r.setAltezza(10) -> in Quadrato ha impostato base=10 E altezza=10
// Il quadrato ora ha lato 10 e area 100, non 50 come si aspetterebbe il client.
// Il client ha violato senza saperlo l'invariante del quadrato.
```
- **Se gli oggetti sono mutabili**, `Quadrato` **NON** è un sottotipo valido di `Rettangolo` perché aggiunge un vincolo comportamentale ("lati sempre uguali") che la superclasse non ha e che viene violato dai suoi stessi metodi pubblici.
- **Soluzione alternativa:** Rendere `Quadrato` e `Rettangolo` classi separate (fratelli) che derivano entrambe da una superclasse astratta comune, ad esempio `FormaGeometrica`. In questo modo si evita la relazione di sottotipizzazione forzata e incorretta.

**Es Persona Studente**
Al contrario di Quadrato/Rettangolo, la relazione `Persona` e `Studente` è un perfetto esempio di ereditarietà ben applicata.
- Uno `Studente` è sempre una `Persona`.
- Lo `Studente` non aggiunge vincoli che invalidano i comportamenti ereditati da `Persona`. Una `Persona` può cambiare nome, uno `Studente` anche, senza che si rompa alcun contratto.
### Polimorfismo e Late Binding
La situazione più interessante si verifica quando si usa un riferimento di tipo base per puntare a un oggetto di tipo derivato:
```java
Persona p = new Studente("Mario", 12345);
```
Ora `p` è formalmente un riferimento a `Persona`, ma **concretamente** punta a un oggetto `Studente`. 
Quando invochiamo `p.toString()` viene chiamato il metodo della classe **effettiva** dell'oggetto a runtime (`Studente.toString()`). Questo meccanismo si chiama **Polimorfismo**.

**Il Meccanismo: Late Binding (o Dynamic Binding)**
La decisione su *quale* metodo chiamare non viene presa dal compilatore (binding statico/early), ma viene **rimandata a runtime** (binding dinamico/late).
- In Java, il late binding è **l'impostazione predefinita** per tutti i metodi di istanza non `private` e non `static`. Si può usare l'annotazione `@Override` per rendere esplicita l'intenzione di ridefinire un metodo della superclasse.

**Esempio di Esecuzione:**
```java
Persona p = new Persona("John");
Studente s = new Studente("Tom", 98765);

System.out.println(p.toString()); // Output: Mi chiamo John
System.out.println(s.toString()); // Output: Mi chiamo Tom, matricola 98765

p = s; // p ora punta all'oggetto Studente di nome Tom
System.out.println(p.toString()); // Output: Mi chiamo Tom, matricola 98765
// Anche se p è di tipo Persona, viene eseguito il metodo di Studente!
```

Late Binding: Funzionamento Interno (VMT)
Come fa la JVM a sapere a runtime quale metodo chiamare? Usa la **Virtual Method Table (VMT)** .
Ogni classe ha una sua VMT, che è una tabella che associa ogni metodo (della classe e ereditato) all'indirizzo di memoria del codice da eseguire.
Quando una classe (`Studente`) estende un'altra classe (`Persona`), la VMT di `Studente` è costruita a partire da quella di `Persona`.
    - I metodi ereditati ma **non** ridefiniti mantengono lo stesso indice e puntano al codice della superclasse.
    - I metodi **ridefiniti** (con `@Override`) mantengono lo **stesso indice** nella tabella, ma l'indirizzo associato viene cambiato per puntare al nuovo codice della sottoclasse.
    - I nuovi metodi della sottoclasse vengono aggiunti in fondo alla tabella.
A runtime, quando si esegue `p.toString()`, la JVM:
    - Guarda l'oggetto puntato da `p` (che è di tipo `Studente`).
    - Accede alla VMT della classe `Studente`.
    - Prende l'indirizzo del codice associato al metodo `toString()` (che è sempre lo stesso indice della tabella).
    - Esegue quel codice.

Il polimorfismo ha un limite fondamentale: funziona solo per i metodi che **esistono nella classe base**.
- **Metodi Ridefiniti:** Disponibili e polimorfici (es. `toString()`).
- **Metodi Aggiunti:** **NON** sono accessibili tramite un riferimento della classe base, perché il compilatore non può garantire che l'oggetto a runtime li possieda.
```java
class Counter2 extends Counter {
    public void dec() { /* decrementa */ }
}

Counter c = new Counter2();

c.dec(); // ERRORE DI COMPILAZIONE!
// 'c' è di tipo Counter e la classe Counter non ha un metodo 'dec()'.

// Per chiamarlo, è necessario un cast esplicito:
((Counter2) c).dec(); // OK, ma rischioso se c non fosse veramente un Counter2
```
### La Classe `Object`
In Java, tutte le classi formano una gerarchia la cui radice è la classe **`java.lang.Object`**.
- Se si scrive `class MiaClasse { ... }`, il compilatore la interpreta come `class MiaClasse extends Object { ... }`.
- Questo implica che **ogni** oggetto in Java eredita un insieme di metodi fondamentali da `Object`.

**Metodi "Predefiniti" Principali di `Object` (e quindi disponibili per tutti gli oggetti):**

| Metodo | Descrizione | Comportamento Predefinito in `Object` |
| :--- | :--- | :--- |
| `String toString()` | Restituisce una rappresentazione in stringa dell'oggetto. | Restituisce una stringa come `NomeClasse@hashcode` (es. `Counter@712c1a3c`). |
| `boolean equals(Object obj)` | Confronta l'oggetto corrente con un altro per verificarne l'uguaglianza "logica". | Confronta i riferimenti in memoria, come l'operatore `==`. |
| `int hashCode()` | Restituisce un valore numerico (hash) che rappresenta l'oggetto. Deve essere coerente con `equals` (oggetti uguali devono avere lo stesso hash). | Tipicamente converte l'indirizzo di memoria in un intero. |
| `protected Object clone()` | Crea e restituisce una copia dell'oggetto. | Crea una copia "superficiale" (shallow copy). |
**Esempio di Utilizzo di `toString()` e `println()`:**
Il metodo `System.out.println()` è progettato per sfruttare il polimorfismo di `toString()`. La sua implementazione (semplificata) è:

```java
public void println(Object obj) {
    // Chiama toString() sull'oggetto, che sarà la versione appropriata
    // in base al tipo effettivo dell'oggetto a runtime.
    String s = obj.toString();
    // ... stampa la stringa s
}
```
Ecco perché fare `System.out.println(unaPersona)` o `System.out.println(unoStudente)` funziona sempre e produce un output sensato, se il metodo `toString()` è stato ridefinito correttamente.

Es Numeri Reali e Numeri Complessi
**Analisi Errata (Relazione Invertita):**
- Pensiero: "Un numero complesso ha una parte reale, come i reali, ma anche una parte immaginaria. Quindi `Complex extends Real`."
- **Problema:** Un numero reale è un sottoinsieme (caso particolare) dei numeri complessi (R ⊂ C). Matematicamente, l'insieme dei reali è più "piccolo". La relazione di ereditarietà "estende" indica un sottoinsieme, non un ampliamento.

**Analisi Corretta:**
- **Relazione:** R ⊂ C  ->  `Real` è un sottotipo di `Complex`.
- **Progetto:** La classe base è `Complex`. La classe `Real` estende `Complex`.

```java
// Classe base: Numero Complesso
public class Complex {
    protected float re, im;

    public Complex(float r, float i) {
        this.re = r;
        this.im = i;
    }

    public Complex sum(Complex that) {
        return new Complex(this.re + that.re, this.im + that.im);
    }

    public Complex sub(Complex that) {
        return new Complex(this.re - that.re, this.im - that.im);
    }

    // Moltiplicazione: (a+ib)(c+id) = (ac-bd) + i(bc+ad)
    public Complex mul(Complex that) {
        return new Complex(
            this.re * that.re - this.im * that.im,
            this.im * that.re + this.re * that.im
        );
    }

    // ... altri metodi come div, coniugato, modulo quadrato ...

    @Override
    public String toString() {
        return re + " + i" + im;
    }
}

// Classe derivata: Numero Reale (caso particolare di Complesso con parte immaginaria = 0)
public class Real extends Complex {

    public Real(float r) {
        super(r, 0.0f); // La parte immaginaria è sempre 0
    }

    // Metodi specializzati per operazioni fra reali (restituiscono un Real)
    // per efficienza e per mantenere il tipo più specifico
    public Real sum(Real that) {
        // Si potrebbe usare this.re + that.re, ma re è protected in Complex
        return new Real(this.re + that.re);
    }

    // Nota: i metodi ereditati come sum(Complex) funzionano ancora,
    // ma restituiscono un Complex. Questo è corretto (polimorfismo).

    @Override
    public String toString() {
        // Nascondo la parte immaginaria per una rappresentazione più pulita
        return Float.toString(re);
    }
}
```
1.  **Correttezza Matematica:** Rispetta la relazione insiemistica R ⊂ C.
2.  **Sostituibilità Corretta:** Posso passare un `Real` ovunque sia richiesto un `Complex` (es. sommare un reale a un complesso), il che è matematicamente corretto. Il viceversa non è permesso (non posso passare un `Complex` qualsiasi dove è richiesto un `Real`), che è ancora una volta corretto.
3.  **Polimorfismo Naturale:** Un `Real` è trattato come un `Complex` con `im=0`, e i metodi di `Complex` funzionano perfettamente. Possono poi essere specializzati in `Real` per ottimizzazione.
### Equals e hash code
#### `equals`
Nelle nostre prime implementazioni di classi come `Counter` e `Frazione`, avevamo definito un metodo `equals` con un argomento del tipo specifico della classe:
```java
// Classe Counter
public boolean equals(Counter that) {
    return this.val == that.val;
}

// Classe Frazione
public boolean equals(Frazione that) {
    return this.num * that.den == this.den * that.num;
}
```
La classe `Object` (superclasse di tutte le classi in Java) dichiara:
```java
public boolean equals(Object obj) {
    return this == obj;
}
```
- `equals(Counter)` in `Counter`
- `equals(Frazione)` in `Frazione`
- `equals(Object)` ereditata da `Object`

Queste sono **tre funzioni diverse** (overloading, non overriding).  
Di conseguenza, ogni classe contiene **due metodi `equals`**:
- quella specifica (con argomento del proprio tipo)
- quella ereditata (con argomento `Object`)

Caso 1: riferimenti di tipo `Counter`
```java
Counter c1 = new Counter(13);
Counter c2 = new Counter(13);
System.out.println(c1.equals(c2)); // true (usa equals(Counter))
```
Caso 2: cambia il tipo nominale del riferimento
```java
Object obj = c1;
System.out.println(obj.equals(c2)); // false (usa equals(Object) di Object)
System.out.println(c2.equals(obj)); // false (overloading: sceglie equals(Object))
```
- `obj.equals(c2)` – il target è `Object`, quindi si cerca in `Object` (l’unica `equals` disponibile è quella con `Object`). Il polimorfismo non trova una versione più specifica in `Counter` perché la signature `equals(Counter)` non è una ridefinizione di `equals(Object)`.
- `c2.equals(obj)` – il target è `Counter`, che ha due `equals`. La risoluzione dell’overloading guarda il tipo nominale dell’argomento (`Object`) e sceglie `equals(Object)` (quella ereditata), non la nostra.
**avere due `equals` è pericoloso – quella “sbagliata” può emergere in contesti polimorfi.**

**overriding della `equals` di `Object`**
Per un comportamento polimorfo corretto, dobbiamo **sostituire** (override) il metodo `equals` ereditato, **non aggiungerne uno nuovo**. La signature deve essere identica:
```java
@Override
public boolean equals(Object obj) { ... }
```
`Object` generico, quindi non possiamo accedere direttamente a campi come `val` (di `Counter`) o `num`, `den` (di `Frazione`).  
Serve un **cast** (downcast) esplicito.

Prima versione (fragile)
```java
@Override
public boolean equals(Object obj) {
    return this.val == ((Counter) obj).val; // può lanciare ClassCastException
}
```

Aggiungere un controllo di tipo con `instanceof`
Per evitare eccezioni, prima di eseguire il cast controlliamo se l’oggetto è del tipo atteso:

```java
@Override
public boolean equals(Object obj) {
    if (obj instanceof Counter) {
        Counter that = (Counter) obj;
        return this.val == that.val;
    }
    return false;
}
```
Ora il metodo è sicuro: se `obj` non è un `Counter`, restituisce `false`.

`instanceof` esteso (Java 16+)
A partire da Java 16, possiamo usare una forma più concisa che dichiara e casta automaticamente la variabile:
```java
@Override
public boolean equals(Object obj) {
    if (obj instanceof Counter that) {
        return this.val == that.val;
    }
    return false;
}
```
All’interno del ramo `true`, `that` è già di tipo `Counter`.

**Alternativa moderna: `switch` con pattern matching**
```java
@Override
public boolean equals(Object obj) {
    return switch (obj) {
        case Counter c -> this.val == c.val;
        default -> false;
    };
}
```
#### `hashCode`
- `Object` dichiara anche `public int hashCode()`.
- **Regola aurea:** **se due oggetti sono uguali secondo `equals`, devono avere lo stesso `hashCode`**
- `hashCode` è usato internamente da strutture dati come `HashMap`, `HashSet` per ricerche efficienti.
Se non ridefiniamo `hashCode` in `Counter`:

```java
Counter c1 = new Counter(13);
Counter c2 = new Counter(13);
System.out.println(c1.hashCode()); // 1523554304 (valore casuale)
System.out.println(c2.hashCode()); // 1175962212 (diverso!)
```
Nonostante `c1.equals(c2)` sia `true`, gli hash code sono diversi → violazione della regola.
> [!important]
> **Eclipse può generare equals e hashCode automaticamente che funzionano SEMPRE**
> da rigenerare se modifichi i campi
>Opzione consigliata in Eclipse: usa `Objects.hash` per un codice più compatto.

es.
```java
public class Counter {
    private int val;

    public Counter(int val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Counter that) && this.val == that.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }
}
```
###  `instanceof`, switch esteso e overloading
Talvolta è necessario **distinguere le istanze di una classe da quelle delle sue sottoclassi** per trattarle in modo diverso.  
Se la funzione è un metodo (non statico), il polimorfismo risolve il problema automaticamente.  
Se invece la funzione è **statica** (o comunque non legata al polimorfismo dinamico), occorre un approccio diverso.

**Due strade possibili:**
1. **Controllo dinamico del tipo** – usando `instanceof` o `switch` con pattern matching.
2. **Overloading** – definendo più versioni della stessa funzione con parametri di tipo diverso.

es. funzione `decrementa`
Scrivere una **funzione statica** `decrementa(Counter c)` che:
  - Se `c` è effettivamente un `Counter2`, deve chiamare `c.dec()`.
  - Altrimenti (è un `Counter` semplice), deve simulare il decremento usando `reset()`, `getValue()` e un ciclo di `inc()`.

> Attenzione: l’argomento è passato **per valore** (riferimento), quindi non possiamo sostituire l’oggetto, dobbiamo modificare quello esistente.

**uso di `instanceof`**
```java
public static void decrementa(Counter c) {
    if (c instanceof Counter2) {
        Counter2 c2 = (Counter2) c;
  //if (c instanceof Counter2 c2) {   // dichiara e casta automaticamente
        c2.dec();
    } else {
        int valore = c.getValue();
        c.reset();
        while (--valore > 0) {
            c.inc();
        }
    }
}
```

**uso di `switch` esteso con pattern matching**
Il costrutto `switch` (dalla forma estesa) permette di distinguere per tipo.
```java
public static void decrementa(Counter c) {
    switch (c) {
        case Counter2 c2 -> c2.dec();          // se è un Counter2
        case Counter _   -> {                  // se è un Counter semplice
            int valore = c.getValue();
            c.reset();
            while (--valore > 0) {
                c.inc();
            }
        }
    }
}
```
- Il secondo caso usa `Counter _` (variabile anonima) perché non ci serve un riferimento castato.  
- La sintassi con `->` evita il `break` esplicito.  

**overloading**
Invece di distinguere *dentro* la funzione, possiamo definire **due funzioni diverse** con lo stesso nome e parametri di tipo differente.
```java
// Versione per Counter2 (decremento diretto)
public static void decrementa(Counter2 c2) {
    c2.dec();
}

// Versione per Counter generico (decremento simulato)
public static void decrementa(Counter c) {
    int valore = c.getValue();
    c.reset();
    while (--valore > 0) {
        c.inc();
    }
}
```
Il compilatore decide **staticamente** (a compile time) quale versione chiamare in base al **tipo dichiarato** dell’argomento, non al tipo effettivo a runtime.
```java
Counter c1 = new Counter(10);
Counter2 c2 = new Counter2(20);

decrementa(c1);   // chiama decrementa(Counter)
decrementa(c2);   // chiama decrementa(Counter2) perché c2 è dichiarato Counter2
```
Se avessimo:
```java
Counter c = new Counter2(20);
decrementa(c);    // chiama decrementa(Counter) !!! (perché il tipo dichiarato è Counter)
```
In questo caso la versione sbagliata verrebbe eseguita (quella con il ciclo, invece del `dec()`).  
**L’overloading non è polimorfo** – è una scelta statica.

**Perché l’overloading non funziona per `equals`**
- L’**overloading** è una scelta **statica** (compile time) basata sul tipo dichiarato.  
- Il metodo `equals` deve essere **polimorfo** (dynamic dispatch), quindi serve **override** (stessa identica firma) e un controllo dinamico del tipo con `instanceof` o pattern matching.
## Wrapper per tipi primitivi in Java
In Java, i **tipi primitivi** (`int`, `double`, `char`, `boolean`, ecc.) **non sono classi**.  
Questo crea una disuniformità rispetto ai tipi riferimento:

| Caratteristica          | Tipi primitivi                  | Tipi riferimento (classi)               |
| ----------------------- | ------------------------------- | --------------------------------------- |
| Hanno metodi            | No                              | Sì (es. `toString()`)                   |
| Uguaglianza             | `==` (valore)                   | `equals()` (sovrascrivibile)            |
| Passaggio parametri     | per valore (copia)              | per riferimento (copia del riferimento) |
| Array separati          | `int[]`, `double[]`             | `Object[]`, `Counter[]`                 |
| Supportano ereditarietà | No                              | Sì                                      |
| Usabili in `Collection` | No (es. `List<int>` non valido) | Sì (`List<Integer>`)                    |

**Conseguenze**
Un valore primitivo **non può essere usato dove è richiesto un `Object`**:
```java
Object[] array = new Object[10];
array[0] = "ciao";              // OK - String è Object
array[1] = new Counter(2);      // OK - Counter è Object
array[2] = 7;                   // errore di compilazione (prima del boxing automatico)
```

> [!success]
> Java fornisce per ogni tipo primitivo una **classe wrapper** che **incapsula un valore primitivo all’interno di un oggetto.**

![[117-Wrapper per tipi primitivi.pdf#page=7&rect=12,179,701,325|117-Wrapper per tipi primitivi, p.7]]
Le classi wrapper (e altre come `Optional`, classi `java.time`) sono **value-based**:
- Sono `final` e **immutabili**.
- L’uguaglianza va testata con `equals()`, **non con `==`** (perché istanze diverse possono essere considerate intercambiabili).
- Non espongono costruttori pubblici; si usano metodi factory (`valueOf`).
- `equals()`, `hashCode()`, `toString()` dipendono solo dallo stato, non dall’identità.

**Boxing e unboxing**
```java
Integer i = Integer.valueOf(22);
Double d = Double.valueOf(3.14);
```
`valueOf()` può ottimizzare con **caching** (es. per interi tra -128 e 127 riutilizza le stesse istanze).

```java
int x = i.intValue();        // 22
double z = d.doubleValue();  // 3.14
```

Esempi di conversione tra tipi
```java
Integer i = Integer.valueOf(22);
System.out.println(i.intValue());    // 22
System.out.println(i.doubleValue()); // 22.0

Double d = Double.valueOf(3.14);
System.out.println(d.intValue());    // 3 (troncato)

Float f = Float.valueOf(1.73F);
System.out.println(f.longValue());   // 1
```

**Boxing e unboxing automatici**
Java converte automaticamente tra primitivi e wrapper quando necessario:
```java
// Boxing automatico
Integer k = 42;   // equivale a Integer.valueOf(42)

// Unboxing automatico
int val = k;      // equivale a k.intValue()

// Operazioni miste
Integer a = 10;
Integer b = 20;
Integer c = a + b;   // unboxing, somma, boxing
```

**Uso con array di `Object`**
```java
Object[] array = new Object[10];
array[0] = "ciao";
array[1] = new Counter(2);
array[2] = 7;           // boxing automatico: int → Integer
array[3] = 7.2;         // boxing automatico: double → Double
// Funziona!
```

**Uso con collection**
```java
List<Integer> list = new ArrayList<>();
list.add(5);            // boxing automatico: int → Integer
int primo = list.get(0); // unboxing automatico: Integer → int
```
**Nota:** non si può usare `List<int>` – i tipi primitivi non sono ammessi come parametri di tipo.

**Parsing**
Da String a numero primitivo – `parseXXX()`
```java
String s = "23";
int i = Integer.parseInt(s);    // int
long l = Long.parseLong(s);     // long
double d = Double.parseDouble(s); // double
```

Da String a wrapper – `valueOf()`
```java
Integer k = Integer.valueOf(s);   // Integer
Long L = Long.valueOf(s);         // Long
```

Se la stringa non è un numero valido, si ha `NumberFormatException` a runtime:
```java
Integer.parseInt("23a");   // NumberFormatException
```

**Da numero a String**
```java
int i = 12;
String i = String.valueOf(i); // "12"

Integer k = 24;
String k = k.toString();        // "24"
```

**Conversioni in basi diverse (radix)**
```java
// String → int in base binaria (radix 2)
int bin = Integer.parseInt("1010", 2);   // 10

// int → String in base esadecimale
String hex = Integer.toString(255, 16);  // "ff"

// Metodi helper predefiniti
String binStr = Integer.toBinaryString(10);   // "1010"
String octStr = Integer.toOctalString(10);    // "12"
String hexStr = Integer.toHexString(255);    // "ff"
```

`instanceof` e `switch` con pattern matching lavorano su **oggetti**, non su primitivi.  
Per testare un valore primitivo avvolto in un wrapper:
```java
Object v = 17;   // boxing automatico

if (v instanceof Integer && (Integer)v < 18) {
    System.out.println("Voto insufficiente: " + v);
}

switch (v) {
    case Integer i when i < 18 -> System.out.println("Bocciato: " + i);
    case Integer i -> System.out.println("Promosso: " + i);
}
```
Da Java 25 (in preview) è possibile usare pattern direttamente sui tipi primitivi:
```java
// Con instanceof
if (v instanceof int && v < 18) {   // v deve essere int
    System.out.println("Voto insufficiente: " + v);
}

// Con switch esteso
switch (v) {
    case int i when i < 18 -> System.out.println("Bocciato: " + i);
    case int i -> System.out.println("Promosso: " + i);
}
```
Per abilitare le preview feature:
```bash
javac --enable-preview --source 25 NomeFile.java
java --enable-preview NomeFile
```
## Record e classi di dati
Nel modellare un sistema, capita spesso di avere classi che sono semplici **aggregati di dati immutabili**:
- campi dati (tipicamente `final`)
- costruttore (che inizializza i campi)
- accessor (getter)
- metodi `equals`, `hashCode`, `toString` standard

La struttura di queste classi è meccanica e ripetitiva.  
Gli IDE (Eclipse, IntelliJ) possono generarli automaticamente, ma nei linguaggi moderni** (Java da versione 15+) esistono i **record**.

Un **record** è una forma leggera di classe, pensata per essere un semplice contenitore di dati immutabili.
```java
public record Persona(String nome, int anni) {}
```
Con questa sola riga, il compilatore genera automaticamente:
- campi `private final` per `nome` e `anni`;
- un costruttore canonico (`public Persona(String nome, int anni)`);
- metodi accessor **con lo stesso nome del campo** (`nome()` e `anni()`);
- `equals` (confronto campo per campo);
- `hashCode` (basato sui campi);
- `toString` (restituisce una stringa con tutti i campi).

```java
public static void main(String[] args) {
    var p1 = new Persona("John", 25);
    var p2 = new Persona("Mary", 22);
    var p3 = new Persona("John", 25);

    System.out.println(p1);                 // Persona[nome=John, anni=25]
    System.out.println(p1.equals(p3));      // true
    System.out.println(p1 == p3);           // false (identità oggetti)

    System.out.println(p1.nome());          // John
    System.out.println(p1.anni());          // 25
}
```
**Nota bene:** l’operatore `==` confronta l’identità degli oggetti, non il contenuto. Per il contenuto si usa `equals` (che nei record è già implementato correttamente).

| Proprietà             | Descrizione                                                                                                                         |
| --------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| Immutabilità          | Tutti i campi dichiarati nell’intestazione sono `final`                                                                             |
| Non Estensibilità     | Il record è `final`: non può essere esteso da altre classi, né estendere altre classi (deriva implicitamente da `java.lang.Record`) |
| Metodi aggiuntivi     | È possibile aggiungere metodi di istanza, metodi statici, campi statici                                                             |
| Interfacce            | Un record può implementare una o più interfacce                                                                                     |
| Costruttori ausiliari | Si possono definire costruttori aggiuntivi, ma devono richiamare un altro costruttore con `this(...)`                               |

I record possono essere usati nel **pattern matching** all’interno di `instanceof` e `switch`.  
Ciò permette la **destrutturazione** (estrazione dei componenti) in modo conciso.

**Destrutturazione con `instanceof`**
```java
static void print(Object obj) {
    if (obj instanceof Persona(String n, int a)) {
        System.out.println("Persona: nome=" + n + ", anni=" + a);
    } else {
        System.out.println(obj);
    }
}
```
Con type inference (var):
```java
if (obj instanceof Persona(var n, var a)) { ... }
```

**Unnamed patterns**
Quando un componente del record non serve, si può usare il carattere `_` per ignorarlo.
```java
if (obj instanceof Persona(_, int a)) {
    System.out.println("Età: " + a);   // il nome viene ignorato
}
```

**Pattern innestati**
Se un record contiene un altro record, si possono destrutturare entrambi in un unico pattern.
```java
record Point(int x, int y) {}
record Rectangle(Point a, Point b) {}

static void print(Rectangle r) {
    if (r instanceof Rectangle(Point(var x1, var y1), Point(var x2, var y2))) {
        System.out.println("Vertici: (" + x1 + "," + y1 + "), (" + x2 + "," + y2 + ")");
    }
}
```

**Guardie semantiche (clausola `when`)**
nei pattern di `switch` si può aggiungere una condizione booleana con `when`.
```java
public String show() {
    return switch(this.anni) {
        case int i when i < 18 -> nome + " è minorenne";
        case int i when i > 70 -> nome + " è pensionato";
        default -> nome + " è un lavoratore";
    };
}
```

| Aspetto | Classe tradizionale | Record |
|---------|---------------------|--------|
| Dichiarazione | Molto verbosa (campi, costruttore, getter, equals, hashCode, toString) | Una sola riga |
| Mutabilità | I campi possono essere `final` o meno | Tutti i campi dell’intestazione sono `final` (immutabili) |
| Ereditarietà | Può estendere altre classi | Non può essere esteso (è `final`) |
| Metodi accessor | Di solito `getNome()` | `nome()` (stesso nome del campo) |
| Pattern matching | Non supportato (o richiede `instanceof` con cast) | Supporto nativo alla destrutturazione |

Quando usare un record
- Quando la classe è un semplice **contenitore di dati** (DTO, value object).
- Quando l’**immutabilità** è desiderata.
- Quando non serve ereditarietà.
- Quando si vuole sfruttare il **pattern matching** per destrutturare gli oggetti.
In tutti gli altri casi (gerarchie, oggetti mutevoli, comportamenti complessi) si usano le normali classi.
## Classi astratte
Nel modellare il mondo reale, molte entità sono **categorie concettuali** e non esistono come oggetti concreti.

**Esempi:**
- Non esiste il “generico animale” – esistono cani, gatti, lepri, …
- Non esiste la “generica roccia” – esistono granito, quarzo, basalto, …
- Non esiste la “generica forma geometrica” – esistono triangoli, quadrati, cerchi, …

Le **classi astratte** servono proprio a rappresentare queste categorie concettuali, di cui **non possono esistere istanze**. Le istanze effettive appartengono a sottoclassi concrete.

In Java si usa la parola chiave `abstract`:
- per dichiarare una classe astratta
- per dichiarare uno o più **metodi astratti** (senza implementazione)
```java
public abstract class Animale {
    // campo privato (esiste in tutte le sottoclassi)
    private String mioNome;
    
    // campo protected (accessibile alle sottoclassi)
    protected String verso;
    
    // costruttore
    public Animale(String nome) {
        this.mioNome = nome;
    }
    
    // metodo astratto (senza corpo, solo dichiarazione)
    public abstract String siMuove(); 
    
    public abstract String vive(); // verrà definito da una sottoclasse
    
    public abstract String nome();
    
    // metodo concreto (implementato qui)
    public void mostra() {
        System.out.println(mioNome + ", " + nome() + ", " + verso +
                           ", si muove " + siMuove() + " e vive " + vive());
    }
}
```

**Regole fondamentali:**
- Una classe che contiene almeno un metodo astratto **deve** essere dichiarata `abstract`.
- Una classe astratta **non può essere istanziata** (non si può fare `new Animale(...)`).
- Una classe astratta può contenere anche metodi concreti (con implementazione) e campi.

### Sottoclassi astratte e concrete
Una sottoclasse può implementare solo **una parte** dei metodi astratti ereditati. Se ne rimane almeno uno non implementato, anche la sottoclasse deve essere dichiarata `abstract`.
![[119-Classi astratte.pdf#page=8&rect=590,181,688,415|119-Classi astratte, p.8|100]]

**sottoclasse astratta**
```java
public abstract class AnimaleTerrestre extends Animale {
    public AnimaleTerrestre(String s) {
        super(s);
    }
    
    // implementa vive() e nome(), ma non siMuove()
    @Override
    public String vive() {
        return "sulla terraferma";
    }
    
    @Override
    public String nome() {
        return "un animale terrestre";
    }
    
    // siMuove() rimane astratto – quindi la classe è astratta
}
```

**Sottoclasse concreta**
Una classe è **concreta** quando **tutti** i metodi astratti ereditati sono stati implementati (direttamente o ereditati da una superclasse).
```java
public class Gatto extends AnimaleTerrestre {
    public Gatto(String nome) {
        super(nome);
        this.verso = "miagola";
    }
    
    @Override
    public String siMuove() {
        return "saltando";
    }
    
    @Override
    public String nome() {
        return "un gatto";
    }
}
```
#### Esempio animali
```java
public abstract class Animale {
    private String mioNome;
    protected String verso;
    
    public Animale(String nome) {
        this.mioNome = nome;
    }
    
    public abstract String siMuove();
    public abstract String vive();
    public abstract String nome();
    
    public void mostra() {
        System.out.println(mioNome + ", " + nome() + ", " + verso +
                           ", si muove " + siMuove() + " e vive " + vive());
    }
}
```

`AnimaleTerrestre`
```java
public abstract class AnimaleTerrestre extends Animale {
    public AnimaleTerrestre(String s) {
        super(s);
    }
    
    @Override
    public String vive() {
        return "sulla terraferma";
    }
    
    @Override
    public String nome() {
        return "un animale terrestre";
    }
}
```

 `AnimaleAcquatico`
```java
public abstract class AnimaleAcquatico extends Animale {
    public AnimaleAcquatico(String s) {
        super(s);
    }
    
    @Override
    public String vive() {
        return "nell'acqua";
    }
    
    @Override
    public String nome() {
        return "un animale acquatico";
    }
}
```

`AnimaleMarino` (specializzazione di `AnimaleAcquatico`)
```java
public abstract class AnimaleMarino extends AnimaleAcquatico {
    public AnimaleMarino(String s) {
        super(s);
    }
    
    @Override
    public String vive() {
        return "in mare";
    }
    
    @Override
    public String nome() {
        return "un animale marino";
    }
}
```

Classi concrete
```java
public class PesceDiMare extends AnimaleMarino {
    public PesceDiMare(String nome) {
        super(nome);
        this.verso = "non fa versi";
    }
    
    @Override
    public String nome() {
        return "un pesce (di mare)";
    }
    
    @Override
    public String siMuove() {
        return "nuotando";
    }
}

public class Uccello extends AnimaleTerrestre {
    public Uccello(String nome) {
        super(nome);
        this.verso = "cinguetta";
    }
    
    @Override
    public String vive() {
        return "in un nido su un albero";
    }
    
    @Override
    public String nome() {
        return "un uccello";
    }
    
    @Override
    public String siMuove() {
        return "volando";
    }
}

public class Tonno extends PesceDiMare {
    public Tonno(String nome) {
        super(nome);
    }
    
    @Override
    public String nome() {
        return "un tonno";
    }
}
```
![[119-Classi astratte.pdf#page=20&rect=96,61,570,428|119-Classi astratte, p.20|500]]
#### Esempio forme geometriche
```java
public abstract class Forma {
    public abstract double area();
    public abstract double perimetro();
    public abstract String nome();
    
    @Override
    public String toString() {
        return nome() + " di area " + area() + " e perimetro " + perimetro();
    }
}
```

`Triangolo`
```java
public class Triangolo extends Forma {
    private double lato1, lato2, lato3;
    
    public Triangolo(double l1, double l2, double l3) {
        this.lato1 = l1;
        this.lato2 = l2;
        this.lato3 = l3;
    }
    
    @Override
    public double perimetro() {
        return lato1 + lato2 + lato3;
    }
    
    @Override
    public double area() {
        // Formula di Erone
        double p = perimetro() / 2;
        return Math.sqrt(p * (p - lato1) * (p - lato2) * (p - lato3));
    }
    
    @Override
    public String nome() {
        return "Triangolo qualsiasi";
    }
}
```

`TriangoloIsoscele` (estende `Triangolo`)
```java
public class TriangoloIsoscele extends Triangolo {
    public TriangoloIsoscele(double base, double lato) {
        super(base, lato, lato);
    }
    
    @Override
    public String nome() {
        return "Triangolo isoscele";
    }
}
```

`TriangoloEquilatero`
```java
public class TriangoloEquilatero extends Triangolo {
    public TriangoloEquilatero(double lato) {
        super(lato, lato, lato);
    }
    
    @Override
    public String nome() {
        return "Triangolo equilatero";
    }
}
```

**Il caso del quadrato e del rettangolo**
![[119-Classi astratte.pdf#page=37&rect=241,51,675,303|119-Classi astratte, p.37|300]]
Nella realtà un **quadrato** è un caso particolare di **rettangolo** (ha tutti gli angoli retti e i lati uguali).  
Tuttavia, con l’ereditarietà singola si può scegliere solo un criterio di classificazione alla volta.
![[119-Classi astratte.pdf#page=46&rect=25,39,703,437|119-Classi astratte, p.46|500]]

Se definiamo:
```java
public class Rettangolo extends Forma { ... }
public class Quadrato extends Rettangolo { ... }
```
funziona, ma se volessimo anche classificare il quadrato come “rombo” o “trapezio rettangolo” non possiamo, perché in Java una classe può estendere una sola altra classe.
```java
Rettangolo r = new Quadrato(5);   // vorremmo che fosse valido, ma se Quadrato non estende Rettangolo dà errore
```

In un modello mal progettato (ad esempio se `Quadrato` e `Rettangolo` sono fratelli sotto `Forma`), l’assegnazione non è permessa, violando l’intuizione geometrica.
#### Intersezioni di insiemi
> [!missing]
> L’ereditarietà singola modella bene le relazioni di **inclusione insiemistica** (un sottoinsieme è un caso particolare). Ma la realtà spesso presenta **intersezioni**:
- Un triangolo rettangolo è sia “triangolo” sia “rettangolo” (angolo retto).
- Un quadrato è sia “rettangolo” sia “rombo”.
Queste situazioni richiederebbero **ereditarietà multipla**, che Java non supporta tra classi per evitare problemi complessi (diamond problem, duplicazione di dati, conflitti di metodi).

**Ereditarietà Multipla e interfacce** 
Per risolvere il problema delle intersezioni senza i problemi dell’ereditarietà multipla tra classi, Java introduce il concetto di **interfaccia**. Un’interfaccia permette di dichiarare comportamenti (metodi astratti) che una classe può implementare **molteplice** (implementa più interfacce). Le interfacce non contengono stato (campi di istanza) – solo costanti statiche e metodi astratti (o default/statici da Java 8).
## Interfaccia
**Definizione di Classe:** Una classe definisce un Tipo di Dato Astratto (ADT) specificando contemporaneamente due aspetti:
    1.  **Front-End (Pubblico):** Cosa fa l'oggetto (firme dei metodi pubblici).
    2.  **Back-End (Privato):** Come lo fa (implementazione dei metodi e dati interni).
Questa contemporaneità è un limite progettuale per tre motivi:
- **Fasi Preliminari:** Non sempre si conoscono i dettagli implementativi all'inizio del progetto.
- **Flessibilità:** Non si vogliono vincolare le scelte implementative a priori (es. per cambiare idea dopo o scegliere a runtime).
-  **Ereditarietà Multipla Assente:** Java non supporta l'ereditarietà multipla tra classi, limitando la modellazione di concetti come `StudenteLavoratore`.
**Classi Astratte: Una Soluzione Parziale**
- Permettono di dichiarare metodi senza implementarli (`abstract`).
- **Limite:** Vincolano l'implementazione dei metodi astratti a una **sottoclasse**, legando il tutto a una gerarchia singola e rigida. Non risolvono il problema dell'ereditarietà multipla né del disaccoppiamento totale.

> [!success]
> **Separare Interfaccia e Implementazione** Utilizzare due costrutti distinti invece di uno solo.
 **Interfaccia (`interface`):** Definisce solo il **front-end** (cosa deve fare un oggetto). Contiene solo dichiarazioni di metodi (e costanti `static final`).
 **Classe Concreta (`class`):** Fornisce il **back-end** (implementazione reale) di una o più interfacce.
### L'Interfaccia in Java
![[120-Interfacce.pdf#page=9&rect=11,37,694,438|120-Interfacce, p.9|600]]
- **Natura:** Una classe astratta portata all'estremo. **Non contiene alcuna definizione ma solo dichiarazioni.** Non contiene stato (variabili di istanza), né costruttori, né implementazioni di metodi (fino a Java 8, poi sono stati introdotti i metodi `default` e `static`, ma il concetto core rimane la pura specifica).
- **Scopo:** Introdurre un **Tipo** puro, usabile per riferimenti e argomenti di metodi.
- **Relazione di Realizzazione (`implements`):** Una classe che si impegna a fornire il codice per tutti i metodi dichiarati in un'interfaccia "realizza" (o "implementa") quell'interfaccia.compatibili
- **Il tipo classe e il tipo interfaccia sono** 

| Caratteristica        | Classe Astratta (`abstract class`)        | Interfaccia (`interface`)                                                 |
| :-------------------- | :---------------------------------------- | :------------------------------------------------------------------------ |
| **Ereditarietà**      | Singola (`extends`)                       | Multipla (`implements`)                                                   |
| **Stato (Campi)**     | Può definire variabili di istanza         | **No** (solo costanti `public static final`)                              |
| **Costruttori**       | Sì                                        | **No**                                                                    |
| **Metodi**            | Astratti e concreti                       | Astratti (e `default`/`static` da Java 8+)                                |
| **Visibilità membri** | `public`, `protected`, `private`          | Solo `public` (implicito)                                                 |
| **Relazione**         | Ereditarietà (**sempre una sottoclasse**) | Realizzazione (**qualunque classe che implementa**) Ereditarietà multipla |
![[120-Interfacce.pdf#page=10&rect=31,88,688,376|120-Interfacce, p.10|550]]
```java
public interface Rettangolo {
    // Metodi astratti impliciti: public abstract
    double base();
    double altezza();
}

class ImplRettangolo implements Rettangolo {
	private double lato1, lato2;
	public ImplRettangolo(double altezza, double base) { lato1 = base; lato2 = altezza;}
	public double altezza() { return lato2; }
	public double base() { return lato1; }
	// eventuali metodi aggiuntivi
}
```
![[120-Interfacce.pdf#page=16&rect=40,97,709,310|120-Interfacce, p.16|500]]
### Ereditarietà Multipla con le Interfacce
> [!success]
> Poiché le interfacce non contengono implementazioni (nessun corpo metodo, nessun dato, 0 codice), **non c'è il rischio di collisione** tipico dell'ereditarietà multipla fra classi.
*Se ci dovessero essere metodi omonimi comunque non ci sarebbero collisioni*

**Ereditarietà tra Interfacce:** Un'interfaccia può estendere (`extends`) più altre interfacce. Questo serve a comporre tipi e stabilire relazioni concettuali tra astrazioni pure.
```java
    public interface TrapezioRettangolo extends Trapezio { ... }
    public interface Parallelogrammo extends Quadrilatero { ... }

    // Ereditarietà multipla tra interfacce
    public interface Rettangolo extends TrapezioRettangolo, Parallelogrammo {
        // Rettangolo eredita i metodi di entrambe le super-interfacce
    }
```

**Progettare con le Interfacce ("Design for Change")**
1.  **Prima le Interfacce:** Si modella la tassonomia dei concetti (astrazioni) usando solo interfacce. Si stabilisce **cosa** il sistema deve fare, in modo pulito e senza vincoli implementativi.
2.  **Poi le Classi:** Si creano le classi concrete che implementano tali interfacce, ottimizzando per efficienza, riuso del codice, ecc. Queste classi diventano "dettagli implementativi" nascosti al resto del sistema.
- **Vantaggi:** Disaccoppiamento architetturale. Il codice che usa un'interfaccia non dipende da una classe specifica, ma solo dal contratto. Questo rende il sistema più facile da mantenere, estendere e testare.

**Compatibilità di Tipo e Riferimenti a Interfaccia**
- Un'interfaccia definisce un tipo. Non si possono creare istanze dirette (`new Rettangolo()` è illegale).
- Una classe che implementa un'interfaccia `I` è compatibile per assegnamento con il tipo `I`.
- Un riferimento di tipo interfaccia può puntare a un'istanza di **qualsiasi** classe che la implementa.

**Esempio: Vista "Orizzontale" vs. "Verticale"**
```java
public interface Rettangolare {
    double larghezza();
    double lunghezza();
}

public class Tavolo implements Rettangolare {
	private double largh, lungh, h;
	public Tavolo(double largh, double lungh, double h){
	this.largh=largh; this.lungh=lungh; this.h=h;
	}
	@Override 
	public double larghezza() { return largh;}
	@Override 
	public double lunghezza() { return lungh; }
	public double altezza() { return h; }
	
	public double peso() {…} // in base al peso del legno… … // altre cose specifiche del tavolo: colore… 
}
public class Libro implements Rettangolare { ... }
public class Appezzamento implements Rettangolare { ... }

public class MyMath {
    // Questo metodo funziona con QUALSIASI cosa sia Rettangolare,
    // indipendentemente dalla sua classe concreta.
    public static double area(Rettangolare r) {
        return r.larghezza() * r.lunghezza();
    }
}

public class Main {
    public static void main(String[] args) {
        Rettangolare libro = new Libro(22, 16, 3);
        Rettangolare tavolo = new Tavolo(120, 70, 74);

        System.out.println(MyMath.area(libro)); // Funziona!
        System.out.println(MyMath.area(tavolo)); // Funziona!
    }
}
```
![[120-Interfacce.pdf#page=35&rect=48,31,708,402|120-Interfacce, p.35|600]]
### Interfacce e Pattern Factory
1.  Il client richiede un oggetto che rispetti una certa interfaccia (es. `Rettangolo`).
2.  La **Factory**, in base a logiche interne (es. parametri passati, configurazione), decide quale classe concreta istanziare (es. `ImplRettangolo`, `Quadrato`, ecc.).
3.  La Factory restituisce l'istanza come tipo **interfaccia**, nascondendo completamente al client il tipo concreto dell'oggetto.
*Es. java.time è costruito cosi*

la **Factory Internalizzata:** Si può inserire un metodo `static` di factory direttamente nell'interfaccia.
```java
    public interface Rettangolo {
        double base();
        double altezza();

        // Factory internalizzata
        public static Rettangolo of(double base, double altezza) {
            // Qui la logica per decidere cosa creare.
            // Se base == altezza, potremmo restituire un Quadrato
            if (base == altezza) {
                return new ImplQuadrato(base);
            } else {
                return new ImplRettangolo(base, altezza);
            }
        }
    }

    // Uso lato client
    Rettangolo r = Rettangolo.of(10, 5); // Non so se è un ImplRettangolo o un Quadrato
```
### Forme Geometriche
Con le sole classi, l'ereditarietà singola impedisce di modellare in modo pulito criteri di classificazione ortogonali (es. "avere i lati paralleli" vs. "avere gli angoli retti"). Un `Quadrato` è sia un `Rettangolo` che un `Rombo`, ma non può ereditare da entrambi.
- **Soluzione con Interfacce:**
    1.  Si crea una tassonomia di **interfacce** con ereditarietà multipla.![[120-Interfacce.pdf#page=54&rect=9,54,702,436|120-Interfacce, p.54|400]]
    ```java
	public interface Forma { double area(); double perimetro(); }
        public interface Quadrilatero extends Forma { ... }
        public interface Parallelogrammo extends Quadrilatero { ... }
        public interface Rettangolo extends Parallelogrammo, TrapezioRettangolo { ... }
        public interface Rombo extends Parallelogrammo { ... }
        // Ereditarietà multipla: Quadrato è sia Rettangolo che Rombo
        public interface Quadrato extends Rettangolo, Rombo { }
    ```
    2.  Si creano **classi concrete** per l'implementazione. Poiché non c'è ereditarietà multipla fra classi, si dovrà scegliere una gerarchia di classi che massimizzi il riuso del codice. Ad esempio, `ImplQuadrato` può estendere `ImplRettangolo` per riutilizzare il codice, implementando poi l'interfaccia `Quadrato`.
        ```java
        class ImplRettangolo implements Rettangolo { ... }

        class ImplQuadrato extends ImplRettangolo implements Quadrato {
            public ImplQuadrato(double lato) {
                super(lato, lato); // Chiama il costruttore di ImplRettangolo
            }
            // ...
        }
        ```
L'utente vede solo la tassonomia pulita delle interfacce e usa le factory per ottenere le forme.

### Numeri Complessi e Reali
Un numero reale è un numero complesso con parte immaginaria nulla. Modellare questa relazione con le classi è inefficiente (ogni reale deve comunque mantenere un campo `im = 0`).
1.  **Interfaccia `Complex`:** Definisce le operazioni generali sui complessi (`getReal()`, `getIm()`, `sum(Complex)`, `mul(Complex)`, ecc.).
2.  **Interfaccia `Real extends Complex`:** Specializza `Complex` aggiungendo metodi che restituiscono `Real` (covarianza del tipo di ritorno).
3.  **Classi Separate:** Le classi `ComplexNum` e `RealNum` implementano le rispettive interfacce. `RealNum` non ha bisogno di memorizzare `im` perché il suo metodo `getIm()` restituisce sempre `0`.
    ```java
    // Interfaccia con factory internalizzata
    public interface Complex {
        // ... metodi ...
        public static Complex of(double x, double y) {
            return new ComplexNum(x, y);
        }
        public static Complex of(double x) {
            return new RealNum(x); // Restituisce un Real!
        }
    }

    public interface Real extends Complex {
        // ... metodi ...
        public static Real of(double x) {
            return new RealNum(x);
        }
    }

    // Lato client
    Complex c = Complex.of(3, 2);   // Ottiene un ComplexNum
    Real r = Real.of(3.14);        // Ottiene un RealNum
    Complex c2 = Complex.of(5);     // Ottiene un RealNum, ma visto come Complex!
    
    public class RealNum implements Real {...}
    public class ComplexNum implements Complex {...}
    ```
![[120-Interfacce.pdf#page=88&rect=31,84,698,396|120-Interfacce, p.88|600]]
questa struttura definisce una factory con più funzioni ma è possibile utilizzare una factory più intelligente.
```java
public class NumFactory {
	public static Real of() { return new RealNum(); }
	public static Real of(double x){ return new RealNum(x); }
	public static Complex of(double x, double y) {
		return new ComplexNum(x,y);
	}
}

Real r1 = NumFactory.of(18.5);
```
posso creare due factory una per i numeri complessi e una per i reali.
```java
public interface Complex {
	//...
	public static Complex of(){ return new ComplexNum(); }
}

public interface Real {
	//...
	public static Real of(){ return new RealNum(); }
}

Real r1 = Real.of(18.5);
```
### Diamond Inheritance in Java
`Studente` e `Lavoratore` estendono `Persona`. `StudenteLavoratore` è sia `Studente` che `Lavoratore`.
- **Soluzione:**
    1.  **Front-End (Interfacce):** `Persona`, `Studente`, `Lavoratore` e `StudenteLavoratore` sono **interfacce**. `StudenteLavoratore` può estendere (`extends`) entrambe `Studente` e `Lavoratore` senza problemi.
    2.  **Back-End (Classi):** Si creano classi concrete come `LaPersona`, `LoStudente` e `IlLavoratore`. La classe `LoStudenteLavoratore` **può estendere solo una classe** (es. `LoStudente`), ma **può implementare l'interfaccia** `StudenteLavoratore`. 
        ```java
interface StudenteLavoratore extends Studente, Lavoratore { 
	// interfaccia vuota! Definisce il tipo-intersezione 
}
        ```
La tassonomia di **interfacce** è concettualmente perfetta e supporta l'ereditarietà multipla. La tassonomia di **classi** è un dettaglio implementativo vincolato dall'ereditarietà singola di Java, che viene gestito internamente senza influenzare l'utente dell'API.
## Genericità e Polimorfismo Orizzontale
Necessità di creare componenti software (strutture dati, metodi) che **funzionino indipendentemente dal tipo specifico** di dato trattato (es. una lista di `String` o una lista di `Frazione`).

**Approccio Obsoleto: Polimorfismo Verticale con `Object`**
Questo metodo sfrutta la radice della gerarchia Java, `Object`, per contenere qualsiasi cosa.
```java
package edutils;

public class List {
    private Object elem;
    private List next;

    public List() { elem = null; next = null; }
    public List(Object e) { elem = e; next = null; }
    public List(Object e, List l) { elem = e; next = l; }

    public Object head() { return elem; }
    public List tail() { return next; }
    public boolean isEmpty() { return elem == null; }
}
```

Metodo Generico (find) con `Object`
```java
class Main {
    public static boolean find(Object[] array, Object elem) {
        for (Object obj : array)
            if (obj.equals(elem))
                return true;
        return false;
    }
}
```

> [!danger]
> **Perdita del Type Safety:** Il compilatore non può verificare la coerenza dei tipi.
**Mix Illegali:** È perfettamente legale (per il compilatore) creare una lista mista, anche se non voluta.
```java
// Codice che compila ma è logicamente scorretto
List l = new List(new Frazione(2,3), new List("Pippo", new List(new Counter2(18))));ù
```
**ClassCastException a Runtime:** Passare tipi sbagliati a metodi come `find` compila, ma causa errori in esecuzione.
```java
String[] arr1 = { "Pippo", "Alberto" };
// Compila, ma esplode a runtime perché cerca un Counter in un array di Stringhe
boolean res = Main.find(arr1, new Counter(18));
```

**Approccio Moderno: Polimorfismo Orizzontale con Tipi Generici (`<T>`)**
La soluzione è rendere il tipo un **parametro**. Il vincolo di tipo viene spostato dalla mente del programmatore al codice stesso, permettendo al compilatore di fare controlli.
- Definizione classe: `class NomeClasse<T> { ... }`
- Definizione metodo: `public <T> TipoRitorno nomeMetodo(T param) { ... }`
L'utilizzo di del tipo generico T permette di non utilizzare Object

Struttura Dati Generica (`List<T>`)
```java
package edutils2;

public class List<T> {
    private T elem;
    private List<T> next;

    public List() { elem = null; next = null; }
    public List(T e) { elem = e; next = null; }
    public List(T e, List<T> l) { elem = e; next = l; }

    public T head() { return elem; }
    public List<T> tail() { return next; }
    public boolean isEmpty() { return elem == null; }

    public String toString() {
        if (isEmpty()) return "";
        String tailStr = (next == null || next.isEmpty()) ? "" : ", " + next.toString();
        return elem.toString() + tailStr;
    }
}
```
**Uso Corretto:**
```java
List<String> l1 = new List<>("Pippo", new List<>("Alberto"));
List<Frazione> l2 = new List<>(new Frazione(2,3));

// ERRORE DI COMPILAZIONE (Mix non consentito):
l1 = l2; // Il compilatore blocca questa assegnazione
```

Metodo Generico (`find`)
La posizione del parametro `<T>` va dichiarata prima del tipo di ritorno.
```java
public static <T> boolean idem(T[] a, T[] b) //stuttura

//esempio
public static <T> boolean find(T[] array, T elem) {
    for (T obj : array)
        if (obj.equals(elem))
            return true;
    return false;
}
```
Chiamata (Se il tipo viene omesso si usa Object di default):
```java
String[] arr1 = { "Pippo", "Alberto", "Enrico" };
Frazione[] arr2 = { new Frazione(2,3), new Frazione(5,7) };

nomeclasse.<Counter>idem(array1, array2)//stuttura

// esempi
boolean res1 = Main.<String>find(arr1, "Giusy");
boolean res2 = Main.<Frazione>find(arr2, new Frazione(4,6));

// ERRORE DI COMPILAZIONE:
boolean err = Main.<String>find(arr1, new Frazione(2,3));
```

#### Composizione con Generics
È possibile comporre strutture dati generiche per crearne di più complesse.
```java
class Stack<T> {
    private List<T> memory;

    public Stack() { 
        memory = new List<>(); 
    }

    // Restituisce Stack<T> per permettere la "Fluent Interface"
    public Stack<T> push(T e) {
        memory = new List<>(e, memory);
        return this;
    }

    public T pop() {
        T result = memory.head();
        memory = memory.tail();
        return result;
    }

    public boolean isEmpty() { 
        return memory.isEmpty(); 
    }

    public String toString() { 
        return memory.toString(); 
    }
}
```
Esempio di Fluent Interface:
```java
Stack<String> stack = new Stack<>();
stack.push("pippo").push("pluto").push("paperino");
```
#### Ereditarietà con Generics (Esempio: `ReversibleList`)
Le classi generiche possono essere estese.
```java
class ReversibleList<T> extends List<T> {
    
    // Costruttori da ridefinire esplicitamente (non ereditati)
    public ReversibleList() { super(); }
    public ReversibleList(T e) { super(e); }
    public ReversibleList(T e, List<T> l) { super(e, l); }

    public ReversibleList<T> reverse() {
        // Caso base: lista vuota o con un solo elemento
        if (this.isEmpty() || (!this.isEmpty() && this.tail().isEmpty()))
            return this;
        else
            return reverse(this.tail(), new ReversibleList<>(this.head()));
    }

    // Metodo ricorsivo ausiliario
    private ReversibleList<T> reverse(List<T> source, ReversibleList<T> accumulator) {
        if (source.isEmpty()) 
            return accumulator;
        else 
            return reverse(source.tail(), new ReversibleList<>(source.head(), accumulator));
    }
}
```
## Interfacce Standard: `Comparable`, `Comparator` e Marker Interface
Le librerie Java forniscono interfacce standard per esprimere abilità o proprietà comuni degli oggetti.
- **Interfacce di abilità:** Definiscono un comportamento (es. `Comparable` per confrontare, `Serializable` per serializzare). Tipicamente contengono uno o più metodi.
- **Interfacce Marker:** Non contengono metodi. Fungono da "etichetta" (`tag`) per segnalare al compilatore o al runtime che una classe possiede una certa proprietà (es. `Serializable`, `Cloneable`).
### La Confrontabilità: Ordinamento Naturale con `Comparable<T>`
Ordinare una lista o un array di oggetti richiede un criterio di confronto. L'approccio generico delega questa responsabilità all'oggetto stesso se esso "sa confrontarsi".

**Interfaccia `Comparable<T>`**
- Definisce l'**ordinamento naturale** dell'oggetto (es. alfabetico per le stringhe, numerico per i numeri).
- **Metodo:** `int compareTo(T o)`
- **Semantica del valore di ritorno:**
    - `-1` (o valore negativo) se `this < o`
    - `0` se `this` equivale a `o` (deve essere coerente con `equals()`)
    - `+1` (o valore positivo) se `this > o`

Esempio: Classe `Counter` che implementa `Comparable`
```java
public class Counter implements Comparable<Counter> {
    private int val;

    // Costruttore, getter, ecc...

    @Override
    public int compareTo(Counter that) {
        // Criterio naturale: confronto basato sul valore numerico
        if (this.val < that.val) return -1;
        if (this.val > that.val) return 1;
        return 0;
    }
}
```

**Utilizzo con `Arrays.sort()`**
Il metodo `Arrays.sort()` sfrutta internamente `compareTo()` per ordinare senza bisogno di scrivere algoritmi manuali.
```java
public class Test {
    public static void main(String[] args) {
        Counter[] myCounterArray = {
            new Counter(110),
            new Counter(100),
            new Counter(30),
            new Counter(50)
        };

        System.out.println("Pre-ordinamento:");
        for (Counter c : myCounterArray) System.out.println(c);

        // Ordina usando l'ordinamento naturale definito in compareTo
        Arrays.sort(myCounterArray);

        System.out.println("\nPost-ordinamento:");
        for (Counter c : myCounterArray) System.out.println(c);
    }
}
```
*Nota:* Se una classe non implementa `Comparable`, chiamare `Arrays.sort()` su un suo array causerà una `ClassCastException` a runtime.
#### Confrontabilità Personalizzata con `Comparator<T>`
**Limitazione di `Comparable`:**
- Non sempre esiste un criterio "naturale" (es. ordinare una `Persona`: per nome? cognome? età?).
- Potrebbe servire un ordinamento diverso da quello predefinito (es. ordinare stringhe per lunghezza invece che alfabeticamente).

**L'oggetto Comparatore**
- Un comparatore è un oggetto **esterno** alla classe da confrontare.
- Incapsula una specifica strategia di confronto.
- Interfaccia: `Comparator<T>`
- **Metodo:** `int compare(T o1, T o2)`
- **Semantica:** Analoga a `compareTo`, ma confronta due oggetti passati come argomento.

Esempio: Comparatore per `Counter` basato sul modulo 24
Invece di modificare la classe `Counter`, creiamo un comparatore ad-hoc.
```java
import java.util.Comparator;

public class MyComp implements Comparator<Counter> {
    @Override
    public int compare(Counter x, Counter y) {
        // Ordina in base al valore % 24
        int modX = x.getVal() % 24;
        int modY = y.getVal() % 24;
        
        if (modX < modY) return -1;
        if (modX > modY) return 1;
        return 0;
    }
}
```

Utilizzo di `Comparator` con `Arrays.sort()`
Il metodo `sort` ha un overload che accetta un `Comparator` come secondo argomento.
```java
Counter[] myCounterArray = { /* ... */ };

// Uso l'ordinamento personalizzato passando un'istanza del comparatore
Arrays.sort(myCounterArray, new MyComp());
```

Esempio: Comparatori per la classe `Persona`
Se la classe `Persona` non ha un ordinamento naturale logico, creiamo tre comparatori esterni distinti.
```java
class CognomeComparator implements Comparator<Persona> {
    public int compare(Persona p1, Persona p2) {
        // Sfrutta il compareTo delle Stringhe
        return p1.getCognome().compareTo(p2.getCognome());
    }
}

class NomeComparator implements Comparator<Persona> {
    public int compare(Persona p1, Persona p2) {
        return p1.getNome().compareTo(p2.getNome());
    }
}

class EtaComparator implements Comparator<Persona> {
    public int compare(Persona p1, Persona p2) {
        // Sfrutta il metodo statico di confronto per interi
        return Integer.compare(p1.getEta(), p2.getEta());
    }
}

// Utilizzo
Arrays.sort(persone, new CognomeComparator());
Arrays.sort(persone, new NomeComparator());
Arrays.sort(persone, new EtaComparator());
```

**Sintassi Compatta: Classi Anonime**
Se un comparatore viene usato una volta sola, definire una classe esterna risulta verboso. Si può usare una **classe anonima** per definire e istanziare il comparatore inline.

```java
Arrays.sort(persone, new Comparator<Persona>() {
    @Override
    public int compare(Persona p1, Persona p2) {
        return p1.getCognome().compareTo(p2.getCognome());
    }
});
```
- `new Comparator<Persona>() { ... }` non sta istanziando l'interfaccia (cosa impossibile).
- Il compilatore Java genera "dietro le quinte" una nuova classe anonima (es. `ClasseEsterna$1.class`) che implementa l'interfaccia e ne crea un'istanza.
### Interfacce Marker (Esempio `Dentable`)
Un'**interfaccia vuota usata per "marcare" una classe** come appartenente a una categoria logica, abilitando controlli a tempo di compilazione.

**Problema:** Vogliamo che solo alcune classi possano essere passate a un metodo `show()`, anche se tutte le classi hanno un metodo `toString()`.

Soluzione:
1. Definire un'interfaccia marker vuota.
   ```java
   public interface Dentable { } // Nessun metodo
   ```
2. Fare implementare l'interfaccia solo alle classi "abilitate".
   ```java
   public class Good implements Dentable { ... }
   public class Bad { ... } // Non implementa Dentable
   ```
3. Definire il metodo `show` con un vincolo di tipo.
   ```java
   public static void show(Dentable d) {
       System.out.println(d.toString());
   }
   ```

Risultato:
```java
Good g = new Good();
show(g); // OK: Good è un sottotipo di Dentable

Bad b = new Bad();
show(b); // ERRORE DI COMPILAZIONE: Bad non è compatibile con Dentable
```

## Null Safety e il tipo Optional
Spesso serve indicare che un oggetto o un valore **non esiste**:
  - Risultato di una ricerca senza esito
  - Input non valido (es. matrice non quadrata per il determinante)
  - Argomenti opzionali di un metodo
L’approccio classico è restituire o passare `null`, ma è **fragile**:
  - Obbliga il chiamante a **controllare** sempre `if (x != null)` prima di usare l’oggetto
  - Una dimenticanza causa `NullPointerException` (NPE) in punti imprevisti, difficile debug
  - Non funziona con i tipi primitivi (`int`, `double`, …) che non possono essere `null`

**Rappresentare l’assenza nei reali: `NaN`**
Per i tipi `float` e `double`, le classi `Float` e `Double` forniscono le costanti
- `NaN` (Not a Number).
- `NEGATIVE_INFINITY` (-infinito).
- `POSITIVE_INFINITY` (+infinito).
`NaN` è usato per rappresentare un risultato mancante (es. forme indeterminate come ∞−∞).
- Esempio: determinante di una matrice non quadrata → si restituisce `Double.NaN`.
**Controllo: metodo statico** `Double.isNaN(valore)` o `Float.isNaN(valore)`.
**Limite**: soluzione solo per reali, non per oggetti o altri primitivi.

**L’approccio generale: il tipo Optional**
Invece di passare `null`, si **incapsula** il valore in un oggetto wrapper `Optional<T>` che:
  - non è mai `null`
  - può contenere un valore di tipo `T` oppure essere vuoto (nessun valore)
Si **dichiara** esplicitamente nella **firma** del metodo che un risultato potrebbe mancare, migliorando la leggibilità e la sicurezza.
Il cliente sa che l’oggetto `Optional` esiste sempre, ma deve verificare la presenza del contenuto prima di usarlo.

**Classe `java.util.Optional<T>`**
**Creazione**:
  - `Optional.of(valore)` – incapsula un valore **non nullo** (lancia `NullPointerException` se `valore` è `null`)
  - `Optional.empty()` – crea un optional vuoto
  - `Optional.ofNullable(T value)` -optional da valore potenzialmente null
**Verifica presenza**:
  - `boolean isPresent()` – `true` se contiene un valore
  - `boolean isEmpty()` (Java 11+) – `true` se vuoto
**Estrazione**:
  - `T get()` – restituisce il valore se presente, altrimenti lancia `NoSuchElementException`
  - `T orElse(T altro)` – restituisce il valore se presente, altrimenti `altro`
  - `T getOrElse(Supplier<? extends T> supplier)` – restituisce il valore se presente, altrimenti invoca il supplier
Altro
`filter`, `map`, `flatMap` per trasformazioni condizionali

**Optional per tipi primitivi**
- `OptionalInt` → metodo `getAsInt()`
- `OptionalLong` → `getAsLong()`
- `OptionalDouble` → `getAsDouble()`
Queste classi hanno metodi analoghi (`of`, `empty`, `isPresent`, `orElse`, ecc.) ma lavorano con il tipo primitivo.

Es. Uso di base con `OptionalDouble`
```java
OptionalDouble aliquota = OptionalDouble.of(8.6);
if (aliquota.isPresent()) {
    System.out.println(aliquota.getAsDouble());
} else {
    System.out.println("aliquota indefinita");
}

OptionalDouble detrazione = OptionalDouble.empty();
if (detrazione.isEmpty()) {
    System.out.println("nessuna detrazione");
} else {
    System.out.println(detrazione.getAsDouble());
}
// Output:
// 8.6
// nessuna detrazione
```

Metodo `orElse`
```java
OptionalDouble aliquota = OptionalDouble.of(8.6);
System.out.println(aliquota.orElse(10.6));   // 8.6

OptionalDouble aliquota2 = OptionalDouble.empty();
System.out.println(aliquota2.orElse(10.6));  // 10.6
```

Gestione di riferimenti potenzialmente nulli
```java
String nome = null;

// Costruzione condizionale
Optional<String> opt1 = (nome == null) ? Optional.empty() : Optional.of(nome);

// Equivalente con ofNullable
Optional<String> opt2 = Optional.ofNullable(nome);

System.out.println(opt1);  // Optional.empty
System.out.println(opt2);  // Optional.empty
```

Combinazione con `orElse`
```java
String s1 = null;
String s2 = "ciao";

Optional<String> opt1 = Optional.ofNullable(s1);
Optional<String> opt2 = Optional.ofNullable(s2);

String value1 = opt1.orElse("boh");
String value2 = opt2.orElse("buh");

System.out.println(value1);  // boh
System.out.println(value2);  // ciao
```

### 5.5 Esempio reale: compito BikeRent (12/9/2018)

La classe `Rate` ha attributi opzionali come durata massima e orario limite di rientro. Il costruttore e i getter usano `Optional<Duration>` e `Optional<LocalTime>`:

```java
public class Rate {
    private final String city;
    private final double costFirstPeriod;
    private final Duration firstPeriodDuration;
    // ...
    private final Optional<Duration> maxDuration;
    private final Optional<LocalTime> returnTimeLimit;

    public Rate(String city, double costFirstPeriod, Duration firstPeriodDuration,
                Optional<Duration> maxDuration, Optional<LocalTime> returnTimeLimit) {
        this.city = city;
        // ...
        this.maxDuration = maxDuration;
        this.returnTimeLimit = returnTimeLimit;
    }

    public Optional<Duration> getMaxDuration() {
        return maxDuration;
    }

    public Optional<LocalTime> getReturnTimeLimit() {
        return returnTimeLimit;
    }
}
```

Il chiamante, sapendo che i campi sono opzionali, userà `isPresent()` o `orElse()` per gestirli in modo sicuro.

Esempio reale: compito Cambia Valute
La classe `CambiaValute` ha metodi `acquisto` e `vendita` che restituiscono `OptionalDouble`: se la valuta richiesta non è trattata dall’agenzia, si restituisce un optional vuoto.

```java
public class CambiaValute {
    // ...
    public OptionalDouble acquisto(double importo, String valuta) {
        Double tasso = tassi.get(valuta);
        if (tasso == null) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(importo / tasso);
    }

    public OptionalDouble vendita(double importo, String valuta) {
        Double tasso = tassi.get(valuta);
        if (tasso == null) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(importo * tasso);
    }
}
```

Il cliente può usare `orElse` per fornire un valore di default o un messaggio:

```java
CambiaValute agenzia = new CambiaValute();
double risultato = agenzia.acquisto(100, "USD").orElse(-1);
if (risultato == -1) {
    System.out.println("Valuta non disponibile");
}
```

**Quando usare (e NON usare) Optional**
**Casi d’uso appropriati**:
- Incapsulare un valore di ritorno che potrebbe essere assente (es. ricerca in una collezione).
- Passare argomenti **realmente** opzionali a un metodo.
**Casi da evitare**:
- Non usare `Optional` per mascherare violazioni di precondizioni: se un argomento è obbligatorio e non viene fornito, è meglio lanciare un’eccezione.
- Non usarlo per evitare la validazione dell’input: un dato mancante perché l’utente ha sbagliato non è un caso “opzionale” ma un errore.
- Non usare `Optional` come campo di un oggetto serializzabile senza attenzione (la serializzazione di `Optional` era problematica in passato; meglio campi normali con getter che restituiscono `Optional`).
## Java Collection Framework (JCF)
è un'architettura unificata per le strutture dati.
Le strutture sono **generiche** in Java: usano il parametro di tipo `<T>` (es. `List<String>`).
I tipi primitivi (`int`, `double`, …) **non possono essere utilizzati direttamente** come tipo parametrico: si usano i wrapper (`Integer`, `Double`).

### Interfacce Fondamentali
![[124-Collection Framework.pdf#page=6&rect=5,36,655,442|124-Collection Framework, p.6]]
#### `Iterable<T>`
È il punto di partenza: se una classe implementa `Iterable`, può essere scandita con `for (T item : collection)`.
- `Collection<T>` estende `Iterable<T>`.
#### `Collection<T>`
Rappresenta un **gruppo di elementi** senza fare ipotesi su ordine, duplicati, posizione.
**Operazioni base** (nessun `get`!):
  - `boolean add(T element)` – aggiunge un elemento (opzionale)
  - `boolean remove(Object o)` – rimuove un elemento se presente
  - `boolean contains(Object o)` – verifica presenza
  - `int size()` – numero di elementi
  - `boolean isEmpty()`
  - `Object[] toArray()` – esporta in array
  - `boolean equals(Object o)` – confronto di collezioni
Non esiste un metodo `get` perché non esiste una nozione univoca di “posizione”.
#### `Set<T>` (estende `Collection<T>`)
**Insieme matematico**: non ammette duplicati e non esiste un ordine.
- Il metodo `add` **non inserisce se l'elemento è già presente** (restituisce `false`).
- `equals` definisce uguaglianza insiemistica: due set sono uguali se contengono gli stessi elementi.
Non introduce nuovi metodi rispetto a `Collection`, ma **ridefinisce il contratto** per garantire l'assenza di duplicati.
####  `SortedSet<T>` (estende `Set<T>`)
Aggiunge l'**ordinamento totale** sugli elementi.
Gli elementi devono implementare `Comparable<T>` o va fornito un `Comparator<T>` al momento della creazione.
Nuovi metodi:
  - `T first()` – elemento minimo
  - `T last()` – elemento massimo
  - `SortedSet<T> headSet(T toElement)` – elementi strettamente minori di `toElement`
  - `SortedSet<T> tailSet(T fromElement)` – elementi maggiori o uguali a `fromElement`
  - `SortedSet<T> subSet(T from, T to)` – intervallo $[from, to)$
#### `List<T>` (estende `Collection<T>`)
**Sequenza ordinata** di elementi. Ammette duplicati.
A differenza di SortedSet gli elementi sono **ordinati per ordine di inserimento.**
Introduce la **nozione di posizione (indice)**. Ecco perché compare il `get`.
Metodi principali:
  - `T get(int index)` – elemento alla posizione `index`
  - `void add(T element)` – inserisce in coda
  - `T set(int index, T element)` – sostituisce
  - `T remove(int index)` – rimuove per posizione
#### `Queue<T>` (estende `Collection<T>`)
Modella una **coda** (tipicamente FIFO, ma può essere anche LIFO).
Metodi di accesso alla testa della coda (due versioni: una lancia eccezione, l'altra restituisce un valore speciale come `null` o `false`):
Metodi
- `add(e)` inserimento
- `remove()` restituisce e rimuove
- `element()` ispeziona
Non c'è accesso posizionale.
#### `Deque<T>` (estende `Queue<T>`)
- **Coda doppia** (Double Ended Queue): inserimento e rimozione da entrambi gli estremi.
- Aggiunge versioni con suffisso `First`/`Last` (es. `addFirst`, `pollLast`).
#### `Map<K, V>` (non estende `Collection`)
Struttura dati **bidimensionale** (tabella a 2 colonne): associa una **chiave** univoca (tipo `K`) a un **valore** (tipo `V`). Non è una `Collection` perché lavora su coppie chiave-valore.
Metodi essenziali:
  - `V put(K key, V value)` – inserisce/aggiorna
  - `V get(Object key)` – recupera il valore associato a `key` (null se assente)
  - `boolean containsKey(Object key)`
  - `boolean containsValue(Object value)`
  - `V remove(Object key)`
Equals e hashcode vanno ridefinite assieme.
**Collection views** per estrarre i dati in forma di collection:
  - `Set<K> keySet()` – l’insieme delle chiavi (non può avere duplicati)
  - `Collection<V> values()` – la collezione dei valori
  - `Set<Map.Entry<K,V>> entrySet()` – l’insieme delle coppie (righe)
####  `SortedMap<K, V>` (estende `Map<K, V>`)
Mappa con **chiavi ordinate**.
Metodi aggiuntivi:
  - `K firstKey()` prima chiave
  - `K lastKey()` ultima chiave
  - `SortedMap<K,V> headMap(K toKey)`
  - `SortedMap<K,V> tailMap(K fromKey)`
  - `SortedMap<K,V> subMap(K fromKey, K toKey)`
#### Aggiunte per Sorted
- L’ordinamento è basato su `Comparable` (naturale) o su `Comparator` fornito in costruzione.
- Metodi di intervallo (`headSet`, `subSet`, `tailSet`) restituiscono **viste** live, non copie.
- L’iteratore sulle `SortedSet` e sulle `SortedMap` (attraverso `keySet`/`entrySet`) segue l’ordine delle chiavi.
### `java.util.Collections`
Contiene **metodi statici di utilità** per operare sulle collezioni.
  - `sort(List<T> list)` – merge sort, **solo su liste** perché richiede riposizionamento.
  - `binarySearch(List<T> list, T key)` – lista deve essere ordinata.
  - `min(Collection<T> coll)`, `max(...)` – restituisce minimo/massimo.
  - `reverse(List<T> list)`, `shuffle(List<T> list)`, `fill(List<T> list, T obj)`
  - `emptyList()`, `emptySet()`, `emptyMap()` – restituiscono collezioni immutabili vuote.
### Implementazioni Concrete (Classi)
**Implementazioni general‑purpose modificabili**

| Interfaccia     | Hash Table | Array ridimensionabile | Albero bilanciato | Lista concatenata | Hash + lista linkata |
| --------------- | ---------- | ---------------------- | ----------------- | ----------------- | -------------------- |
| **Set**         | `HashSet`  |                        | `TreeSet`         |                   | `LinkedHashSet`      |
| **List**        |            | `ArrayList`            |                   | `LinkedList`      |                      |
| **Queue/Deque** |            | `ArrayDeque`           |                   | `LinkedList`      |                      |
| **Map**         | `HashMap`  |                        | `TreeMap`         |                   | `LinkedHashMap`      |
**Set e Map**
- Se non serve l'ordinamento`HashSet` / `HashMap`: basati su tabella hash.
- Se serve l'ordinamento`TreeSet` / `TreeMap`: implementano `SortedSet`/`SortedMap`, basati su albero bilanciato.
-  `LinkedHashSet` / `LinkedHashMap`: oltre alla tabella hash mantengono una lista doppiamente linkata per tenere traccia dell’**ordine di inserimento** durante l’iterazione.
- Se gli elementi sono Enum: `EnumSet` e `EnumMap` molto efficienti.
**List**
- `ArrayList`: realizzata con array ridimensionabile, accesso posizionale O(1).
- `LinkedList`: doppia lista concatenata, inserimenti/rimozioni in testa e mezzo efficienti.

| Interfaccia | Implementazione | Caratteristiche principali |
|-------------|-----------------|----------------------------|
| `Set` | `HashSet` | O(1) medio, nessun ordine |
|  | `TreeSet` | O(log n), ordinamento totale |
| `List` | `ArrayList` | accesso O(1), buona per la maggior parte dei casi |
|  | `LinkedList` | O(1) per inserimenti/rimozioni in testa; implementa anche `Queue`/`Deque` |
| `Map` | `HashMap` | O(1) medio, nessun ordine |
|  | `TreeMap` | O(log n), chiavi ordinate (interfaccia `SortedMap`) |

**Implementazioni immodificabili**
Non esistono classi pubbliche **per le versioni immodificabili**.
Si ottengono tramite **factory method statici**:
  - `List.of(...)`
  - `Set.of(...)`
  - `Map.of(...)`, `Map.ofEntries(...)`
Questi restituiscono istanze di classi interne (non visibili) che non permettono modifiche.
### Costruzione delle Collezioni in Java
- **Costruttore vuoto**: `new ArrayList<T>()`, `new HashSet<T>()`, `new HashMap<T>()` ecc. → collezione modificabile inizialmente vuota.
```java
List<String> l1 = new LinkedList<String>();
l1.add("Bologna"); l1.add("Modena");

List<String> l2 = new ArrayList<String>();
l2.add("Ferrara"); l2.add("Ravenna");
//costruzioni compatte
var l1 = new ArrayList<String>(); // type inference
List<String> l1 = new ArrayList<>(); // diamond operator (sottointende lo stesso tipo)

Set<String> s1 = new HashSet<String>();
Set<String> s2 = new TreeSet<String>();

Map<String,Integer> m = new HashMap<String,Integer>(); m.put("Bologna", 395416); m.put("Modena", 189013);
Map<String,Integer> m2 = new TreeMap<String,Integer>();
```
- **Costruttore di copia**: accetta un’altra `Collection` (o `Map`) per inizializzare la nuova collezione con gli stessi elementi.
  ```java
  List<String> list1 = new ArrayList<>(existingSet);
  ```
- **Factory per collezioni pre‑popolate immodificabili**:
Il tipo restituito è una classe ad hoc (non una semplice Map)
  ```java
  List<Integer> immutableList = List.of(1, 2, 3);
  Set<String> immutableSet = Set.of("a", "b");
  Map<String, Integer> immutableMap = Map.of("key1", 10, "key2", 20);
  // Per mappe con più di 10 entry: Map.ofEntries(...)
  ```
### Iteratori
**`Iterable<T>`** è l’interfaccia che permette a un oggetto di essere scandito con il costrutto **for‑each** (`for (T x : collezione)`).
Idea base: ogni entità iterabile «sa» come navigare al proprio interno e, su richiesta, è in grado di produrre un **iteratore** adatto alla propria struttura.
Il metodo chiave è `Iterator<T> iterator()`, una sorta di **factory** che restituisce un oggetto iteratore per quella specifica collezione.

> [!summary]
> L’**iteratore** è un **oggetto capace di navigare in una cosa «iterabile» del «suo» tipo**
- Dichiara tre metodi:
  - `T next()` – restituisce il prossimo elemento e fa avanzare l’iteratore.
  - `boolean hasNext()` – restituisce `true` se ci sono ancora elementi da visitare.
  - `void remove()` – **operazione opzionale**; rimuove l’ultimo elemento restituito da `next()`.
Le collezioni della JCF lo implementano; le collezioni **immutabili** (es. quelle ottenute con `List.of`) lanciano `UnsupportedOperationException` se si invoca `remove()`.

**Uso dell’iteratore nel for‑each**
- Il costrutto `for (T x : coll)` si basa proprio sull’iteratore. Il codice:
```java
for (T x : coll) {
    // corpo del ciclo
}
```
equivale a:
```java
for (Iterator<T> i = coll.iterator(); i.hasNext(); ) {
    T x = i.next();
    // corpo del ciclo
}
```
Grazie a ciò, è possibile iterare su qualsiasi `Collection` (e anche sugli array) con una sintassi uniforme.

Esempio: iteratore implicito ed esplicito
```java
import java.util.*;

public class IteratorExample {
    public static void main(String[] args) {
        List<String> listOfStrings = List.of("Pippo", "Pluto", "Paperino", "Zio Paperone");
        iterateOn("lista disney", listOfStrings);

        Set<Number> setOfNums = Set.of(18, 22.2, 37.4F);
        iterateOn("numeri vari", setOfNums);
    }

    public static <T> void iterateOn(String msg, Collection<T> coll) {
        System.out.println("-----------------------------");
        System.out.println(msg);

        // Uso implicito (for-each)
        System.out.println("uso di iteratore implicito");
        for (T s : coll) {
            System.out.println(s);
        }

        // Uso esplicito
        System.out.println("uso di iteratore esplicito");
        Iterator<T> it = coll.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
```
La funzione `iterateOn` è **generica** e funziona su qualunque `Collection<T>`, indipendentemente dal tipo concreto degli elementi.

**Regole nell'utilizzo degli iteratori**
**Cosa si può fare:**
  - Iterare per **leggere** gli elementi.
  - Su collezioni **modificabili**, modificare gli elementi stessi (es. `list.set(i, newValue)`).
**Cosa NON si può fare:**
  - Modificare collection immodificabili (es. `List.of`): `UnsupportedOperationException`
  - Modificare la **struttura della collezione** mentre l’iteratore è attivo (**aggiungere o rimuovere elementi** direttamente sulla collezione). Farlo causa una **`ConcurrentModificationException`**
```java
public static <T> void iterateAndChange(String msg, Collection<T> coll) {
    for (T element : coll) {
        System.out.println(element);
        coll.add(element); // ERRORE: modifica la collezione sotto iterazione
    }
}
```

#### Iteratori e Mappe
`Map<K,V>` non estende `Collection`, quindi **non si può iterare direttamente su una mappa**. Tuttavia, si può iterare sulle sue **viste**:
  - `keySet()` → `Set<K>` (insieme delle chiavi)
  - `values()` → `Collection<V>` (insieme dei valori)
  - `entrySet()` → `Set<Map.Entry<K,V>>` (insieme delle righe)
- `Map.Entry<K,V>` è una **interfaccia interna** a `Map` che rappresenta una coppia chiave-valore, con metodi `getKey()` e `getValue()`.
Es
```java
import java.util.*;

public class MapIteratorExample {
    public static void main(String[] args) {
        Map<String, Integer> peopleMap = Map.of(
            "Anna", 21,
            "Piero", 25,
            "Silvia", 43,
            "Guido", 56
        );

        // 1) Iterazione sulle chiavi
        for (String name : peopleMap.keySet()) {
            System.out.println(name + " ha " + peopleMap.get(name) + " anni");
        }

     // 2) Iterazione sui valori (es. calcolo età media)
        float sum = 0;
        for (int value : peopleMap.values()) {
            sum += value;
        }
        System.out.println("L'età media è di " + sum / peopleMap.size() + " anni");

        // 3) Iterazione sulle entry (righe)
        for (Map.Entry<String, Integer> entry : peopleMap.entrySet()) {
            System.out.println(entry);  // stampa nella forma "chiave=valore"
        }
    }
}
```

Output:
```
Silvia ha 43 anni
Piero ha 25 anni
Anna ha 21 anni
Guido ha 56 anni

L'età media è di 36.25 anni

Silvia=43
Piero=25
Anna=21
Guido=56
```
### Esempi
#### Esercizio 1 – Insieme di parole distinte (`Set`)
**Obiettivo:** analizzare un elenco di parole (es. argomenti da riga di comando) e:
- stampare le parole duplicate
- contare e stampare le parole distinte
- mostrare l’elenco delle parole senza duplicati

**Struttura dati:** `Set<String>` perché non ammette duplicati per definizione; il metodo `add` restituisce `false` se l’elemento è già presente.
```java
import java.util.*;

public class FindDups {
    public static void main(String[] args) {
        Set<String> s = new HashSet<>();  // o TreeSet per ordinamento

        for (String a : args) {
            if (!s.add(a)) {
                System.out.println("Parola duplicata: " + a);
            }
        }

        System.out.println(s.size() + " parole distinte: " + s);

        // Stampa personalizzata
        for (String st : s) {
            System.out.print(st + " ");
        }
    }
}
```

Esecuzione:
```
> java FindDups Io sono Io esisto Io parlo
Parola duplicata: Io
Parola duplicata: Io
4 parole distinte: [Io, parlo, esisto, sono]
Io parlo esisto sono
```

**Scelta dell’implementazione:**
- `HashSet`: nessun ordine garantito, tempo medio O(1) per `add`/`contains`.
- `TreeSet`: elementi ordinati (alfabeticamente), tempo O(log n). Si usa se serve un elenco ordinato.
#### Esercizio 2 – Scambio di elementi in una lista (`List`)
**Obiettivo:** scambiare due elementi in una sequenza (es. argomenti da riga di comando).
**Struttura dati:** `List<T>` perché fornisce accesso posizionale (indicizzato) e duplicati ammessi.

Funzione di swap generica:
```java
static <T> void swap(List<T> list, int i, int j) {
    T temp = list.get(i);
    list.set(i, list.get(j));
    list.set(j, temp);
}
```
Programma principale:
```java
import java.util.*;

public class EsList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(); // o LinkedList
        for (String arg : args) {
            list.add(arg);
        }
        System.out.println(list);
        swap(list, 2, 3);
        System.out.println(list);
    }
}
```
Esecuzione:
```
> java EsList cane gatto pappagallo canarino cane canarino pescerosso
[cane, gatto, pappagallo, canarino, cane, canarino, pescerosso]
[cane, gatto, canarino, pappagallo, cane, canarino, pescerosso]
```

**Scelta dell’implementazione:**
- `ArrayList`: accesso posizionale O(1), ottimo per la maggior parte degli usi.
- `LinkedList`: doppia lista concatenata, efficiente se si fanno molti inserimenti/rimozioni all’inizio o nel mezzo. Implementa anche `Queue` e `Deque`.
#### Esercizio 3 – Iterazione a ritroso con `ListIterator`
- `ListIterator<T>` estende `Iterator<T>` ed è specifico per le liste. Permette:
  - di muoversi all’indietro (`hasPrevious()`, `previous()`)
  - di conoscere l’indice corrente e di aggiungere/sostituire elementi durante l’iterazione.

```java
import java.util.*;

public class EsListIt {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (String arg : args) {
            list.add(arg);
        }

        // Partiamo dalla fine
        for (ListIterator<String> it = list.listIterator(list.size()); it.hasPrevious(); ) {
            System.out.print(it.previous() + " ");
        }
    }
}
```
Esecuzione:
```
> java EsListIt cane gatto cane canarino
canarino cane gatto cane
```
Per iniziare dalla fine si usa `list.listIterator(list.size())`; così il cursore è posizionato dopo l’ultimo elemento.
#### Esercizio 4 – Conteggio delle occorrenze (`Map`)
**Obiettivo:** contare quante volte ogni parola compare in un insieme di argomenti.
**Struttura dati:** `Map<String, Integer>` che associa a ogni parola (chiave) il numero di occorrenze (valore).
**Logica:**
- Se la parola non è ancora nella mappa (`get` restituisce `null`), la si inserisce con conteggio 1.
- Altrimenti, si incrementa il conteggio esistente.

```java
import java.util.*;

public class ContaFrequenza {
    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<>();  // o TreeMap per ordinamento

        for (String parola : args) {
            Integer freq = m.get(parola);
            m.put(parola, (freq == null) ? 1 : freq + 1);
        }

        System.out.println(m.size() + " parole distinte:");
        System.out.println(m); // toString() produce {parola=occorrenze, ...}
    }
}
```
Esecuzione:
```
> java ContaFrequenza cane gatto cane pesce gatto gatto cane
3 parole distinte:
{cane=3, pesce=1, gatto=3}
```

**Iterare sulla mappa risultante**:
1. Via `keySet`:
```java
public static void myPrint(Map<String, Integer> m) {
    for (String key : m.keySet()) {
        System.out.println(key + "\t" + m.get(key));
    }
}
```
2. Via `entrySet` (evita di chiamare `get`):
```java
public static void myPrint2(Map<String, Integer> m) {
    for (Map.Entry<String, Integer> entry : m.entrySet()) {
        System.out.println(entry); // stampa "chiave=valore"
    }
}
```
#### Esercizio 5 – Conteggio con ordinamento (`SortedMap`)
**Obiettivo:** come sopra, ma con output **ordinato per chiave**.
**Struttura dati:** `SortedMap<String, Integer>` con implementazione `TreeMap`.

```java
import java.util.*;

public class ContaFrequenzaOrd {
    public static void main(String[] args) {
        SortedMap<String, Integer> m = new TreeMap<>();

        for (String parola : args) {
            Integer freq = m.get(parola);
            m.put(parola, (freq == null) ? 1 : freq + 1);
        }

        System.out.println(m.size() + " parole distinte:");
        System.out.println(m);
    }
}
```
Esecuzione:
```
> java ContaFrequenzaOrd cane gatto cane pesce gatto gatto cane
3 parole distinte:
{cane=3, gatto=3, pesce=1}
```
L’ordine delle chiavi è garantito dal `TreeMap` (ordine naturale delle stringhe o secondo `Comparator` fornito). L’iteratore sulle viste (`keySet`, `entrySet`) seguirà lo stesso ordinamento.
### Conversioni fra strutture
**Costruttori per copia** – tutte le classi del Collection Framework accettano una `Collection` qualsiasi come argomento e ne copiano (shallow) gli elementi.
**Metodi di conversione** specifici per array.
#### Da List a Set (e viceversa)
```java
List<String> l1 = List.of("Pippo", "Pluto", "QuiQuoQua", "Paperino", "Zio Paperone");
System.out.println(l1); // [Pippo, Pluto, QuiQuoQua, Paperino, Zio Paperone]

// Costruttore per copia: da List a HashSet (non ordinato)
Set<String> s1 = new HashSet<>(l1);
System.out.println(s1); // [Paperino, QuiQuoQua, Pippo, Pluto, Zio Paperone]

// Costruttore per copia: da List a TreeSet (ordinato)
Set<String> s2 = new TreeSet<>(l1);
System.out.println(s2); // [Paperino, Pippo, Pluto, QuiQuoQua, Zio Paperone]
```
#### Da lista immodificabile a lista modificabile
```java
List<String> l1 = List.of("Pippo", "Pluto", "QuiQuoQua", "Paperino", "Zio Paperone");
List<String> l2 = new ArrayList<>(l1); // ora l2 è modificabile
l2.add("Archimede");
l2.add("Paperino");
System.out.println(l2); // [Pippo, Pluto, QuiQuoQua, Paperino, Zio Paperone, Archimede, Paperino]
```
#### Da lista ad array
```java
List<String> l1 = List.of("Pippo", "Pluto", "QuiQuoQua", "Paperino", "Zio Paperone");
String[] a1 = l1.toArray(new String[0]); // l’argomento serve solo per specificare il tipo
// ora a1 è un array: {"Pippo", "Pluto", "QuiQuoQua", "Paperino", "Zio Paperone"}
```
#### Da array a lista
```java
String[] arr = { "One", "Three", "Ten" };
List<String> list = Arrays.asList(arr); // metodo statico della libreria Arrays
System.out.println(list); // [One, Three, Ten]
```
**Attenzione:** la lista ottenuta con `Arrays.asList` è **modificabile** (si possono fare `set`) ma ha **dimensione fissa** (non si possono aggiungere/rimuovere elementi). Per una lista completamente modificabile si può usare:

```java
List<String> listMod = new ArrayList<>(Arrays.asList(arr));
```
### Esempi di ordinamento e ricerca
```java
class Persona implements Comparable<Persona> {
    private String nome, cognome;
	//...
    public int compareTo(Persona that) {
        int confrontoCognomi = cognome.compareTo(that.cognome);
        if (confrontoCognomi != 0) return confrontoCognomi;
        return nome.compareTo(that.nome);
    }
}
```
#### Ordinare una lista di persone
La lista deve essere **modificabile** perché `Collections.sort` modifica l’ordine degli elementi.

**Opzione 1** – da array a lista tramite `Arrays.asList`:
```java
List<Persona> l = Arrays.asList(
    new Persona("Eugenio", "Bennato"),
    new Persona("Roberto", "Benigni"),
    new Persona("Edoardo", "Bennato"),
    new Persona("Bruno", "Vespa")
);
Collections.sort(l);
System.out.println(l);
// [Roberto Benigni, Edoardo Bennato, Eugenio Bennato, Bruno Vespa]
```

**Opzione 2** – da lista immodificabile a lista modificabile con costruttore di copia:
```java
List<Persona> l = new ArrayList<>(List.of(
    new Persona("Eugenio", "Bennato"),
    new Persona("Roberto", "Benigni"),
    new Persona("Edoardo", "Bennato"),
    new Persona("Bruno", "Vespa")
));
Collections.sort(l);
System.out.println(l);
```
**Nota:** `List.of` produce una lista immodificabile, per cui `Collections.sort(l)` lancierebbe `UnsupportedOperationException` se si tentasse di ordinare direttamente quella.
#### Ordinare un array di persone
```java
Persona[] persone = {
    new Persona("Eugenio", "Bennato"),
    new Persona("Roberto", "Benigni"),
    new Persona("Edoardo", "Bennato"),
    new Persona("Bruno", "Vespa")
};
Arrays.sort(persone);
System.out.println(Arrays.toString(persone));
```
Oppure partendo da una lista e convertendo in array:
```java
List<Persona> l = List.of( /* elementi */ );
Persona[] persone = l.toArray(new Persona[0]);
Arrays.sort(persone);
```
#### Ricerca binaria su lista
La lista deve essere **già ordinata** (altrimenti il risultato è imprevedibile). Può essere immodificabile, purché ordinata.

```java
List<Persona> l = List.of(
    new Persona("Edoardo", "Bennato"),
    new Persona("Eugenio", "Bennato"),
    new Persona("Roberto", "Benigni"),
    new Persona("Bruno", "Vespa")
); // già in ordine naturale

System.out.println(Collections.binarySearch(l, new Persona("Bruno", "Vespa"))); // 3 (posizione)
System.out.println(Collections.binarySearch(l, new Persona("Bruno", "Ape")));   // -1 (non trovato)
```
Se la lista fosse inizialmente non ordinata, si può ordinare prima (richiede modificabilità):
```java
List<Persona> l = new ArrayList<>(List.of( /* elementi */ ));
Collections.sort(l);
System.out.println(Collections.binarySearch(l, new Persona("Bruno", "Vespa")));
```
#### Ricerca in una mappa di persone (con chiave codice fiscale)
Si usa una `Map<String, PersonaCF>` dove la chiave è il codice fiscale.
```java
class PersonaCF extends Persona {
    private String cf;
    public PersonaCF(String nome, String cognome, String cf) {
        super(nome, cognome);
        this.cf = cf;
    }
    // eventualmente equals & hashCode basati su cf
}
```

```java
Map<String, PersonaCF> m = new TreeMap<>(Map.of(
    "BNNGNEyymddxxxxu", new PersonaCF("Eugenio", "Bennato", "BNNGNEyymddxxxxu"),
    "BNGRRTyymddxxxxu", new PersonaCF("Roberto", "Benigni", "BNGRRTyymddxxxxu"),
    "BNNDRDyymddxxxxu", new PersonaCF("Edoardo", "Bennato", "BNNDRDyymddxxxxu"),
    "VSPBRNyymddxxxxu", new PersonaCF("Bruno", "Vespa", "VSPBRNyymddxxxxu")
));

System.out.println(m);
// {BNGRRTyymddxxxxu=Roberto Benigni, ...}
System.out.println(m.get("BNGRRTyymddxxxxu"));
// Roberto Benigni
```
Con `TreeMap` la mappa è ordinata per chiave (codice fiscale).

### Approfondimento facoltativo: `EnumSet` e `EnumMap` in Java

Quando gli elementi di un set o le chiavi di una mappa sono **costanti enumerative**, esistono implementazioni specializzate estremamente efficienti.

#### `EnumSet`

- `EnumSet` è una **classe astratta**; non ha costruttori pubblici.
- Le istanze si ottengono tramite **metodi factory statici**:
  - `EnumSet.allOf(Class<E> elementType)` – tutte le costanti dell’enumerazione
  - `EnumSet.noneOf(Class<E> elementType)` – set vuoto
  - `EnumSet.of(E e1)`, `of(E e1, E e2)`, …, fino a 5 argomenti
  - `EnumSet.range(E from, E to)` – intervallo di costanti (secondo l’ordine dichiarato)
  - `EnumSet.copyOf(Collection<E> c)` – copia da una collection esistente (utile anche per set ordinari)
  - `EnumSet.complementOf(EnumSet<E> s)` – complemento rispetto all’insieme totale delle costanti
- **Non accetta elementi nulli** – lancia `NullPointerException`.

**Esempio d’uso:**

```java
import java.time.DayOfWeek;
import java.util.EnumSet;

// Set di un solo giorno
Set<DayOfWeek> s = EnumSet.of(DayOfWeek.MONDAY);
s.add(DayOfWeek.FRIDAY);
System.out.println(s); // [MONDAY, FRIDAY]

// Range di giorni
EnumSet<DayOfWeek> workingDays = EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
System.out.println(workingDays); // [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]

// Set vuoto
EnumSet<DayOfWeek> empty = EnumSet.noneOf(DayOfWeek.class);
```

#### `EnumMap`

- `EnumMap<K extends Enum<K>, V>` è una mappa ottimizzata per chiavi enumerative.
- Ha costruttori pubblici:
  - `EnumMap(Class<K> keyType)` – mappa vuota
  - `EnumMap(EnumMap<K, ? extends V> m)` – copia da un’altra EnumMap
  - `EnumMap(Map<K, ? extends V> m)` – copia da una mappa qualsiasi (se le chiavi sono tutte dello stesso enum)
- Fornisce i classici metodi `put`, `get`, `keySet`, ecc.
- **Non accetta chiavi nulle** (`NullPointerException`).

**Esempio d’uso:**

```java
import java.time.DayOfWeek;
import java.util.EnumMap;

EnumMap<DayOfWeek, Double> costoSosta = new EnumMap<>(DayOfWeek.class);
costoSosta.put(DayOfWeek.MONDAY, 1.50);
costoSosta.put(DayOfWeek.SATURDAY, 2.00);
costoSosta.put(DayOfWeek.SUNDAY, 0.0);

System.out.println(costoSosta.get(DayOfWeek.MONDAY)); // 1.5
System.out.println(costoSosta); // {MONDAY=1.5, SATURDAY=2.0, SUNDAY=0.0}
```
