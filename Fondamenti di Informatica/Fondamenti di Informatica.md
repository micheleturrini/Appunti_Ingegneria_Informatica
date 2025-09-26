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

## Linguaggi
- **Alto livello** (C, Python, Java)
- **Basso livello** (Assembly)

Un linguaggio di programmazione è una notazione formale per descrivere algoritmi
L'**espressività** di un linguaggio è definita in base a
- Quali tipi di dati rappresenta 
- Quali istruzioni mette a disposizione
- Quante operazioni fa rispetto alla macchina di turing
- 


![[02-LinguaggieGrammatiche.pdf#page=16&rect=65,116,671,419|02-LinguaggieGrammatiche, p.16|300]]
**Semantica** significati da attribuire alle frasi (a parola, operazionale, denotazionale, assiomatica)
**Sintassi** regole formali per la scrittura di programmi **Specifica il sottoinsieme V* ***(le regole sintattiche sono definite dal linguaggio **BNF**)
### BNF
Backus-Naur Form
Descrive la sintassi alla base di ogni linguaggio.
![[02-LinguaggieGrammatiche.pdf#page=20&rect=76,74,664,422|02-LinguaggieGrammatiche, p.20|300]]
> [!regole]
> ![[02-LinguaggieGrammatiche.pdf#page=22&rect=140,192,574,343|02-LinguaggieGrammatiche, p.22|250]]
> ** | ** significa oppure
 
 Per sfruttare il linguaggio bisogna muoversi fra le opzioni per costruire la cifra desiderata
![[02-LinguaggieGrammatiche.pdf#page=11&rect=70,131,667,376|02-LinguaggieGrammatiche, p.11|400]]

**Esempio derivazione left most**
![[02-LinguaggieGrammatiche.pdf#page=31&rect=118,155,657,416|02-LinguaggieGrammatiche, p.31|300]]
### Linguaggi imperativi (C)
Si sono imposti come genere di linguaggi preferiti per motivi storici.
### Linguaggi a oggetti (Java, python, C++)
Figli dei linguaggi imperativi con l'aggiunta degli oggetti.
### Il linguaggio assembly
Ogni **circuito corrispondente ad una operazione** ha un nome in 0 e 1
Ogni costrutto linguistico corrisponde a una operazione precisa sul processore.
Per scriverlo è necessario **conoscere nel dettaglio le caratteristiche del processore (Instruction SET)**
### Il linguaggio macchina
I linguaggi di alto livello vengono tradotti da un compiler in linguaggio macchina
Fatto di 0 e 1



Approccio a **compilatore**  (prende un programma e lo traduce direttamente in 0 e 1)
Approccio a **interprete** (prende componente per componente del programma, la traduce in un linguaggio intermedio, lo esegue, lo traduce in linguaggio macchina)

### Linguaggi interpretati
Eseguibili da ogni macchina che abbia l'interprete installato.
Tradotto riga per riga ogni volta
Poco efficiente
![[03-IntroCompilatori+LinguaggioC.pdf#page=7&rect=159,91,532,362|03-IntroCompilatori+LinguaggioC, p.7|250]]
### Linguaggi compilati
Compilato una singola volta per una sola architettura (ex Intel x86 Windows).
Molto efficiente poco versatile.
#### Processo di compilazione
![[03-IntroCompilatori+LinguaggioC.pdf#page=6&rect=130,20,618,396|03-IntroCompilatori+LinguaggioC, p.6|350]]
Editor (Visual studio)
File sorgente (Hello.c)
Compilatore (Trasforma il file sorgente in eseguibile)
**File oggetto** (Eseguibile privo di librerie)
**Librerie** (**Funzioni** di programma **di uso comune**)
**Linker** (Prende il file oggetto e **aggiunge le librerie e le collega rendendo l'eseguibile completo**)

### Linguaggi misti
Python, Java
![[03-IntroCompilatori+LinguaggioC.pdf#page=9&rect=37,318,623,444|03-IntroCompilatori+LinguaggioC, p.9|600]]
Compilando il programma lo **trasporto in un altro linguaggio LMI** (per java è il Bitecode)
Ho un **secondo compilatore** molto efficiente che trasforma il linguaggio in linguaggio macchina

### Il compilatore
- **Analisi**
	- **Lessicale** (correttezza delle singole parole) automatica
	- **Sintattica** (correttezza delle frasi) automatica
	- **Semantica** senso del programma - difficile identificare gli errori
- **Sintesi**
	- **Generatore di codice** (traduce effettivamente il codice)
		- Allocazione della **memoria e dei registri**
	- Ottimizzaore di codice (Migliora il codice, disttivabile)

## Linguaggio C
è estremamente **efficiente** 
- **Sequenziale** l'ordine delle operazioni importa
- **Imperativo**
- **strutturato a blocchi** è costruito di blocchi di codice
- **basato su espressioni**

'![[03-IntroCompilatori+LinguaggioC.pdf#page=20&rect=40,115,652,317|03-IntroCompilatori+LinguaggioC, p.20|500]]
il main () èessenziale (entry point)
Contiene dichiarazioni e definizioni - prima e sequenza di istruzioni -dopo

dichiarazioni-e-definizioni introducono i nomi di costanti, variabili, tipi definiti dall ’utente • sequenza-istruzioni sequenza di frasi del linguaggio ognuna delle quali è un ’istruzione

#### Parole chiave
Espressioni riservate che hanno sempre lo stesso significato e non possono essere utilizzate in altro modo
#### Costanti
Valori costanti
#### Identificatori
Nomi delle variabili tipo???
Iniziano sempre con una lettera che può essere seguita da numeri e cifre senza spazio Maiuscoli e minuscoli fanno la differenza
#### commenti
/* frase su piu righe
// commento su una riga

#### variabile
Una variabile è un'astrazione di una cella di memoria
L-Value (indirizzo)
R-Value (contenuto della cella)
Ogni variabile **deve essere definita**
 - avere un nome
 - avere un contenuto
 - campo d'azione (scope) intervallo di visibilità
 - tipo
 - tempo  di vita


Qualunque istruzione che tocca il ciarpame diventa ciarpame