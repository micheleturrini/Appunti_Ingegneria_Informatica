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
Per evitare confusione sono necessari 3 bus
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