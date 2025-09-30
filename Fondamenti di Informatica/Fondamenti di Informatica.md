[[0 - Index]]
Lunedì 22 settembre 14:30 - lezione base base - Guarda le slide
Il linguaggio C , Deitel

Codelite
NetBeans
## Esame
Appelli (I moduli si possono dare separatamente ma i due moduli sono molto connessi)
- 3 (Gennaio Febbraio)
- 2 (Giugno Luglio)
- 1 (Settembre 26)
**Pate scritta** 1 ora, 12 punti, 7 sufficienza
- Teoria
- Piccoli algoritmi
**Parte pratica** 2 ore, 20 punti, 11 sufficienza
- programmare in C
*Si può aumentare il voto facendo l'orale*
## Informatica
Definizione più completa: **scienza della rappresentazione e dell’elaborazione dell’informazione**
## Hardware
### Il Digitale
**Boole (1854)** → algebra booleana (variabili vero/falso con operatori logici).
**Shannon (1940)** → l’algebra booleana può essere applicata ai circuiti elettronici.
### Tecnologia
rappresentare l'Informazione tramite la **presenza  o l'assenza di una differenza di potenziale**
Il vantaggio rispetto all'analogico è che i circuiti sono più piccoli facili e veloci.
Logica **Positiva** **1 - vero** - presenza, 0 - falso - assenza
Logica **Negativa** 0 - vero - presenza, **1 - falso** - assenza
### Bit e Byte
Una singola informazione booleana è un **bit**
Per esprimere informazioni complesse sono necessarie **sequenze** di bit

Una sequenza di **8** bit forma un  **byte**  
$2^8$  possibili combinazioni
### Interpretazione
Non basta avere la grandezza fisica ma bisogna saperla **interpretare**
è necessaria una convenzione internazionale
### Legge di Moore
(fondatore di Intel)
"Ogni anno **raddoppieremo** il numero di **transistor**"
Legge superata, a causa della fisica quantistica non si possono rimpicciolire i transistor più di qualche nanometro.
### Primi calcolatori
**ENIAC** Primo calcolatore senza parti in movimento realizzato con 18000 valvole termoioniche.
### Architettura Von Neumann 
L'Inghilterra vuole la bomba atomica e investe nella realizzazione di calcolatori.
Neumann inventa un architettura per i calcolatori ancora utilizzata.
![[01a-ShortintroInf.pdf#page=13&rect=29,189,566,420|01a-ShortintroInf, p.13|600]]
**Componenti**
- **RAM** (Random Access Memory) Utilizzando i nastri per trovare un informazione era necessario percorrerlo tutto mentre nella RAM si può accedere direttamente per questo Random.
- **CPU** (transistor) Il **processore** è uno strumento in grado di svolgere **operazioni elementari**.
	A ogni operazione corrisponde un **circuito fisico** nell'hardware
	La quantità migliore di istruzioni che un processore deve saper risolvere è un bilancio fra efficienza e numero di istruzioni.
- **ROM** (contenuto immutabile -> contiene il Firmware o il BIOS)
- **BUS** (fili che collegano tutti i componenti elettrici)
	- **Dati**
	- **Controllo** 
	- **Indirizzi** (Necessario per rintracciare le informazioni specifica nella memoria) I bit sono come cassette postali con un indirizzo (address)
	- **I/O**
## Firmware 
(Bios)
è il **confine** fra hardware e software
Piccoli programmi
## Software
- Software di Base (Sistema operativo)
- Software Applicativo (Chrome ,Macchine Virtuali)
![[01a-ShortintroInf.pdf#page=21&rect=363,125,671,384&color=yellow|01a-ShortintroInf, p.21|300]]
### Sistema operativo
- gestione risorse
- gestione memoria centrale e di massa
- comandi elementari
- sistema multi utente
- GUI

## Lo sviluppo della macchina di Turing
Turing calcola il numero minimo di istruzioni per risolvere tutte le operazioni computabili.
Immagina un automa esecutore (non sa come realizzarlo fisicamente) che
- prenda ingresso un metodo risolutivo
- prenda in ingresso i Dati
- mandi in uscita i risultati

> [!attention]
> La macchina è costruita di un numero finito di parti ma deve muoversi nell'ambiente matematico che è infinito -> siamo costretti all'approssimazione

Turing capisce che la macchina deve essere realizzata basandosi sul modello del calcolatore elettronico basato sull'architettura di Von Neumann

Nella sua tesi di laurea Turing dimostra che la sua macchina in teoria è la più potente di sempre (ancora oggi)
Turing stabilisce che se la sua macchina è la più potente ciò che è computabile dipende da quello che la sua macchina è in grado di fare.
Se esiste un meccanismo per raggiungere la soluzione in un numero finito di passi l'operazione è computabile

> [!attention]
> E' necessario un linguaggio per spiegare alla macchina come risolvere le Operazioni

**Il linguaggio naturale è ambiguo.**
La macchina è un interprete di questo linguaggio (a livello basso oggi si usa il linguaggio macchina a livello alto i linguaggi di alto livello)
Il numero è un concetto rappresentato da un simbolo quindi la macchina (che non può pensare concetti) deve lavorare coi simboli.

La macchina è composta da
- una testina capace di scrivere e leggere simboli
- un nastro che scorre sotto la testina
- un dispositivo di controllo che muove a destra o sinistra la testina
	- Il dispositivo è dotato di uno stato interno
	la macchina decide come muoversi in base a cosa scrive cosa legge e allo stato.
![[01b-algoritmiNew.pdf#page=14&rect=82,49,549,181|01b-algoritmiNew, p.14|400]]

Turing sa che deve **definire una simbologia in grado di rappresentare dati iniziali e risultati**
> [!success]
> Il comportamento della macchina è definito da un insieme di quintuple (Primo linguaggio di programmazione)
> [!PDF|yellow] [[01b-algoritmiNew.pdf#page=16&selection=43,0,44,19&color=yellow|01b-algoritmiNew, p.16]]
> > (stato-corrente, simbolo-letto, nuovo-stato, simboloscritto, direzione)
> 

> [!failure]
> La macchina è in grado di risolvere un solo compito.

L'unica cosa che cambia sono le istruzioni quindi Turing idea un modo per cambiare le istruzioni fornite alla macchina.
**Scrive le istruzioni sul nastro, la macchina le legge le impara e inizia a operare.**
Tuttora il computer a ogni avvio legge le istruzioni in memoria e avvia il sistema operativo.
> [!success]
> La macchina è **universale e programmabile**
> Tutte le istruzioni sono in memoria
> La macchina di Turing è l'interprete di un linguaggio

-  **fetch** (cerca le istruzioni)
- **decode** (interpreta)
- **execute** (esegue)

Nella macchina di **Turing** non sono previsti dispositivi di interfaccia che vengono ideati da Von Neumann.

> [!attention]
> Problema dell'Halt
è possibile stabilire con un programma se un altro programma finisce solo se il secondo programma finisce.
**Il problema è definibile ma non computabile.**

La prima che immagina il concetto di programmazione è Ada Byron.
Ha una serie di intuizioni che si avvicinano al linguaggio Assembly.

## Algoritmo
> [!PDF|yellow] [[01b-algoritmiNew.pdf#page=50&selection=4,0,21,11&color=yellow|01b-algoritmiNew, p.50]]
> > Un algoritmo è una sequenza finita di mosse che risolve in un tempo finito una classe di problemi
> 

**Proprietà degli algoritmi**:
- **Eseguibilità** Ogni operazione deve essere eseguibile in un tempo finito
- **Non-ambiguità** Le istruzioni non possono essere fraintendibili
- **Finitezza** Deve risolversi in un temp finito
- **applicabile** a **qualsiasi insieme di dati di ingresso appartenenti al dominio** di definizione dell’algoritmo
Algoritmi **equivalenti**:
- stesso dominio di ingresso
- stesso dominio di uscita
- stessi valori in uscita per gli stessi valori in entrata

Funzione **ricorsiva** (definisco una funzione con se stessa)

## Classificazione dei linguaggi
- **Alto livello** (C, Python, Java)
- **Basso livello** (Assembly)

Un linguaggio di programmazione è una **notazione formale per descrivere algoritmi**
L'**espressività** di un linguaggio è definita in base a:
- quali tipi di dati rappresenta
- quali istruzioni mette a disposizione
- quante operazioni fa rispetto alla macchina di Turing

## Sintassi e Semantica
- **Sintassi** → regole formali per la scrittura di programmi (definisce il sottoinsieme $V^*$).
    - Le regole sintattiche sono spesso definite tramite **BNF**. 
- **Semantica** → significati da attribuire alle frasi.
    - Può essere: **a parola, operazionale, denotazionale, assiomatica**.

![[02-LinguaggieGrammatiche.pdf#page=16&rect=65,116,671,419|02-LinguaggieGrammatiche, p.16|300]]
### BNF (Backus–Naur Form)
La **BNF** descrive la sintassi alla base di ogni linguaggio di programmazione.
![[02-LinguaggieGrammatiche.pdf#page=20&rect=76,74,664,422|02-LinguaggieGrammatiche, p.20|300]]
> [!regole]
> ![[02-LinguaggieGrammatiche.pdf#page=22&rect=140,192,574,343|02-LinguaggieGrammatiche, p.22|250]]
> Il simbolo `|` significa **oppure**.
> <>
> {opzione facoltativa} 
 
 Per sfruttare il linguaggio bisogna muoversi fra le opzioni per costruire la cifra desiderata
![[02-LinguaggieGrammatiche.pdf#page=11&rect=70,131,667,376|02-LinguaggieGrammatiche, p.11|400]]

**Esempio derivazione left-most**
![[02-LinguaggieGrammatiche.pdf#page=31&rect=118,155,657,416|02-LinguaggieGrammatiche, p.31|300]]
## Tipi di Linguaggi
### Linguaggi imperativi (C)
Si sono imposti come genere di linguaggi preferiti per motivi storici.
### Linguaggi a oggetti (Java, python, C++)
Figli dei linguaggi imperativi con l'aggiunta degli oggetti.
### Il linguaggio assembly
![[03-IntroCompilatori+LinguaggioC.pdf#page=2&rect=56,180,175,261|03-IntroCompilatori+LinguaggioC, p.2|100]]
Le istruzioni corrispondono univocamente a quelle macchina, ma vengono espresse tramite nomi simbolici (parole chiave )
Per scriverlo è necessario **conoscere nel dettaglio le caratteristiche del processore (Instruction SET)**
L'assembler lo traduce in linguaggio macchina
### Linguaggio Macchina
![[03-IntroCompilatori+LinguaggioC.pdf#page=2&rect=60,303,270,398|03-IntroCompilatori+LinguaggioC, p.2|200]]
- Fatto di **0 e 1**.
- È il linguaggio direttamente eseguibile dal processore.
- I linguaggi di alto livello vengono tradotti in linguaggio macchina tramite un **compilatore**.
## Compilazione e Interpretazione
Approccio a **compilatore**  (il codice viene direttamente tradotto in linguaggio macchina una sola volta per una specifica architettura)
Approccio a **interprete** (Il codice viene tradotto e eseguito riga per riga ogni volta)
### Linguaggi interpretati
- Eseguibili su ogni macchina che abbia l’interprete installato.
- Vengono tradotti riga per riga **ogni volta che il programma gira**.
- Poco efficienti.
![[03-IntroCompilatori+LinguaggioC.pdf#page=7&rect=159,91,532,362|03-IntroCompilatori+LinguaggioC, p.7|250]]
### Linguaggi compilati
- Compilati una volta sola per una sola architettura (es. Intel x86, Windows).
- Molto efficienti ma poco versatili.
#### Processo di compilazione
![[03-IntroCompilatori+LinguaggioC.pdf#page=6&rect=130,20,618,396|03-IntroCompilatori+LinguaggioC, p.6|350]]
1. **Editor** (es. Visual Studio)
2. **File sorgente** (es. Hello.c)
3. **Compilatore** → trasforma il file sorgente in eseguibile
    - produce un **file oggetto** (eseguibile privo di librerie)    
4. **Librerie** → funzioni di uso comune
5. **Linker** → prende il file oggetto, aggiunge e collega le librerie → eseguibile completo
#### Linguaggi misti
Python, Java
![[03-IntroCompilatori+LinguaggioC.pdf#page=9&rect=37,318,623,444|03-IntroCompilatori+LinguaggioC, p.9|600]]
- La compilazione trasporta il programma in un **linguaggio LMI (linguaggio macchina intermedio)**.
    - In Java questo linguaggio è il **Bytecode**.
- Un secondo compilatore **efficiente** (o macchina virtuale) lo trasforma poi in linguaggio macchina.
#### Il compilatore
- **Analisi**
	- **Lessicale** (correttezza delle singole parole) automatica
	- **Sintattica** (correttezza delle frasi) automatica
	- **Semantica** senso del programma - difficile identificare gli errori
- **Sintesi**
	- **Generatore di codice** → traduce effettivamente il codice, occupandosi di:
	    - allocazione della memoria
	    - gestione dei registri
	- **Ottimizzatore di codice** → migliora l’efficienza del codice (può essere disattivato).
## Linguaggio C
### Caratteristiche principali
Il linguaggio **C** (creato da Dennis Ritchie, 1972) è:
- **Efficientissimo** → tradotto in linguaggio macchina quasi diretto.
- **Sequenziale** → l’ordine delle operazioni conta.
- **Imperativo** → i programmi sono composti da istruzioni che modificano lo stato del calcolatore.
- **Strutturato a blocchi** → il codice è suddiviso in funzioni e blocchi racchiusi tra `{ }`.
- **Basato su espressioni** → ogni istruzione è spesso un’espressione che produce un valore.
![[03-IntroCompilatori+LinguaggioC.pdf#page=20&rect=40,115,652,317|03-IntroCompilatori+LinguaggioC, p.20|500]]
### Struttura
- **Direttive al preprocessore** (`#include`, `#define`) → vengono eseguite prima della compilazione.
- Il **punto di ingresso** di ogni programma è la funzione `main()`
- **Dichiarazioni e definizioni** → introducono nomi di costanti, variabili, tipi.
- **Sequenza di istruzioni** → frasi del linguaggio eseguite in ordine.
- `return 0;` → segnala che il programma è terminato correttamente.
### Elementi del linguaggio
#### Parole chiave
Espressioni **riservate**, con significato predefinito (non possono essere usate come identificatori).  
Esempi:  
`int`, `return`, `if`, `else`, `while`, `for`, `switch`, `break`, `continue`, `void`, `struct`, `typedef`, `const`, `static` …
#### Costanti
Valori fissi
const float PI = 3.14159;
#### Identificatori
- Sono i **nomi di variabili, funzioni, tipi**
- Regole:
    - Devono iniziare con una lettera o `_`.    
    - Possono contenere lettere, numeri, `_`.
    - Case sensitive (`pippo` ≠ `Pippo`).
`int contatore; 
float valore_medio;`
#### Commenti
```cpp
/* commento su più righe */
// commento su una riga
```
#### Variabili
Una variabile è un’**astrazione di una cella di memoria**.
- **L-Value** → l’indirizzo della variabile (dove sta in memoria).
- **R-Value** → il contenuto della variabile.
- **Identificatore** -> deve avere un nome 
##### Definizione di variabili
Ogni variabile deve avere:
- un **nome** (identificatore)
- un **tipo** (classe di valori che può assumere)
- opzionalmente un valore iniziale (se non sovrascrivo il valore della variabile il pc usa quello che trova)
- un **scope** (campo di visibilità) vive all'interno del blocco in cui è iniazlizzata
- un **tempo di vita**

```cpp
float altezza = 1.75; // variabile reale 
char iniziale = 'A';  // variabile carattere`
int eta = 21;       // variabile intera 
```
> [!Chesani 26/09/25]
> Qualunque istruzione che tocca il ciarpame diventa ciarpame
> **Inizializzare sempre le variabili**
#### Espressioni
Un’espressione è **una formula che produce un valore**.
Possono avere **Effetti collaterali** se assegnano un valore a una variabile, la **incrementano** o la **diminuiscono**
L'assegnamento è distruttivo

```cpp
int i = 4+3; // espressione semplice

// espressioni con effetti collaterali
int i = i-3;

int j = 0;
int i = j-3; // assegnamento

int i += 1 // incrementa di 1 (+,-,*.%,...)
```

### Tipi di Dato
- numeri **naturali**
- numeri **reali**
- **caratteri**
- **stringhe**
- booleano (rappresentato con una variable **int** (**0-Falso, altri numeri-Vero**))
**Specificatore di tipo**
- **char** caratteri
- **int** numeri **interi**
- **float** numero con **virgola**
- **double** numero con virgola ma **precisione doppia**
**Modificatori**
- **short** memorizza **meno valori**
- **long** memorizza **più** **valori**
- **signed** dato **con segno** (positivi e negativi)
- **unsigned** dato **senza segno** (solo positivi)

![[04-TipiDato.pdf#page=5&rect=84,83,614,204|04-TipiDato, p.5|300]]
Con numeri negativi
![[04-TipiDato.pdf#page=7&rect=53,97,614,405|04-TipiDato, p.7|400]]

Interi
- **short** int: almeno **16 bit** (2 byte)
- int: a discrezione del compilatore, $sizeof(int) >= sizeof (short int)$
- **long** int: almeno 32 bit (4 byte)  $sizeof(long int) >= sizeof(int)$
