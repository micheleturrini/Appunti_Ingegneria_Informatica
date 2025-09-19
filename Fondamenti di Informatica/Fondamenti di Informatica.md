[[0 - Index]]
Lunedì 22 settembre 14:30 - lezione base base - Guarda le slide
Il linguaggio C , Deitel

Codelite
eclipse
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
## Il Digitale
Boole Propone un'algebra fatta di vero o falso
Shannon sostiene che l'algebra booleana può essere usata per i circuiti elettronici.
### Architettura
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
## Legge di Moore
(fondatore di Intel)
"Ogni anno **raddoppieremo** il numero di **transistor**"
Legge superata, a causa della fisica quantistica non si possono rimpicciolire i transistor più di qualche nanometro.
## UNIAC
Primo calcolatore senza parti in movimento realizzato con 18000 valvole termoioniche.
## Architettura Von Neumann 
![[01a-ShortintroInf.pdf#page=13&rect=29,189,566,420|01a-ShortintroInf, p.13|600]]
L'Inghilterra vuole la bomba atomica e investe nella realizzazione di calcolatori.
Neumann inventa un architettura per i calcolatori ancora utilizzata.
**componenti**
- RAM (Random Access Memory) Utilizzando i nastri per trovare un informazione era necessario percorrerlo tutto mentre nella RAM si può accedere direttamente per questo Random.
- CPU
- ROM (contenuto immutabile -> contiene il Firmware o il BIOS)

I componenti diversi vengono **collegati tutti allo stesso modo da un BUS** (fili elettrici)
Per evitare confusione sono necessari 3 bus e tutti sono collegati a ogni cella di memoria
- **Dati**
- **Controllo** 
- **Indirizzi** (Necesario per rintracciare le informazioni specifica nella memoria)
	- I bit sono come cassette postali con un indirizzo (address)
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
## L'elaboratore elettronico
è uno strumento in grado di svolgere **operazioni elementari**.
A ogni operazione corrisponde un **circuito fisico** nell'hardware
La quantità migliore di istruzioni che un elaboratore deve saper risolvere è un bilancio fra efficienza e numero di istruzioni.

Turing calcola il numero minimo di istruzioni per risolvere tutte le operazioni computabili.
Immagina un automa esecutore (non sa come realizzarlo fisicamente) che
-  prenda ingiresso un metodo risolutivo
- prenda in ingresso i Dati
- mandi in uscita i risultati
è necessario un linguaggio per spiegare alla macchina come risolvere le Operazioni
Il linguaggio naturale è ambiguo.
La macchina è un interprete di questo linguaggio (a livello basso si usa il linguaggio macchina a livello alto i linguaggi di alto livello)

La macchina è costruita di un numero finito di parti ma deve muoversi nell'ambiente matematico che è infinito -> siamo costretti all'approssimazione

Turing capisce che la macchina deve essere realizzata basandosi sul modello del calcolatore elettronico basato sull'architettura di Von Neumann

Nella sua tesi di laurea Turing dimostra che la sua macchina in teoria è la più potente di sempre (ancora oggi)
Turing stabilisce che se la sua macchina è la più potente ciò che è computabile dipende da quello che la sua macchina è in gradi di fare.
Se esiste un meccanismo per raggiungere la soluzione in un numero finito di passi l'operazione è computabile
Il numero è un concetto rappresentato da un simbolo quindi la macchina (che non può pensare concetti) deve lavorare coi simboli
La macchina è composta da
- una testina capace di scrivere e leggere simboli
- un nastro che scorre sotto la testina
- un dispositivo di controllo che muove a destra o sinistra la testina
	- Il dispositivo è dotato di uno stato interno
	la macchina decide come muoversi in base a cosa scrive cosa legge e allo stato.
![[01b-algoritmiNew.pdf#page=14&rect=82,49,549,181|01b-algoritmiNew, p.14|400]]

Turing sa che deve definire una simbologia in grado di rappresentare dati iniziali e risultati

Il comportamento della macchina è un insieme di quintuple 
> [!PDF|yellow] [[01b-algoritmiNew.pdf#page=16&selection=43,0,44,19&color=yellow|01b-algoritmiNew, p.16]]
> > (stato-corrente, simbolo-letto, nuovo-stato, simboloscritto, direzione)
> 

> [!failure]
> La macchina è in grado di risolvere un solo compito.

L'unica cosa che cambia sono le istruzioni quindi Turing idea un modo per cambiare le istruzioni fornite alla macchina.
Scrive le istruzioni sul nastro, la macchina le legge le impara e inizia a operare.
Tuttora il computer a ogni avvio legge le istruzioni in memoria e avvia il sistema operativo.
> [!success]
> La macchina è **universale e programmabile**
> Tutte le istruzioni sono in memoria
> La macchina di Turing è l'interprete di un linguaggio

-  **fetch** (cerca le istruzioni)
- **decode** (interpreta)
- **execute** (esegue)

Nella macchina di **Turing** non sono previsti dispositivi di interfaccia che vengono ideati da Von Neumann

Problema dell'Halt
è possibile stabilire con un programma se un altro programma finisce solo se il secondo programma finisce.
Il problema è definibile ma non computabile.

La prima che immagina il concetto di programmazione è Ada Byron.
Ha una serie di intuizioni che si avvicinano al linguaggio Assembly