[[0 - Index]]
Simulatore Digital

## Macchine Digitali
I microchip sono **schede elettroniche miniaturizzate** composte da **transistor**.
La M. Digitale è un sistema artificiale:
- progettato per **immagazzinare**, **elaborare** e **comunicare** **informazioni**
- impiegando **segnali digitali**, ovvero **grandezze fisiche** contraddistinte da un insieme discreto (finito, non continuo) di valori significativi

Le **grandezze fisiche** che noi **percepiamo** sono **analogiche**.

> [!attention]
> Perché in informatica si usa il **digitale**?

Es. segnali di fumo.
**Analogico** (la dimensione della nuvola definisce il colore delle tende)
In un segnale analogico l’informazione è rappresentata da ogni possibile valore.
![[1_segnali_codifica.pdf#page=13&rect=21,60,701,471|1_segnali_codifica, p.13|500]]
In teoria posso **facilmente** rappresentare **infinite informazioni** ma in pratica **ogni piccola perturbazione (rumore)** trasforma il mio segnale in uno valido ma di **significato differente**.
Trasmettere e ricevere è **difficile e costoso.**
![[1_segnali_codifica.pdf#page=14&rect=21,268,354,437|1_segnali_codifica, p.14|200]]

**Digitale**
 In un segnale digitale, invece, **è l’intervallo in cui si trova la grandezza** a rappresentare l’informazione. (se la nuvola è alta tra 0 e 1 metri = viola).
![[1_segnali_codifica.pdf#page=15&rect=21,65,704,467|1_segnali_codifica, p.15|500]]
Accetto di rappresentare **meno informazioni** con una stessa grandezza fisica per ottenere **maggior robustezza** nel trasferire l’informazione e **minor complessità** (e costo) dei dispositivi necessari.
![[1_segnali_codifica.pdf#page=17&rect=25,23,314,169|1_segnali_codifica, p.17|200]]

> [!success]
> Il segnale binario è più **robusto e economico.** (passa meno info)
> Al di sotto di un segnale binario c'è un segnale analogico interpretato in modo binario.

**Variabili Binarie** (Bit)
possono assumere i due soli valori «0» e «1»
**non sono numeri,** ma solo valori logici (**simboli**) che indicano se il **segnale è sopra o sotto soglia.
![[1_segnali_codifica.pdf#page=19&rect=36,16,670,178|1_segnali_codifica, p.19|500]]

Un singolo bit trasmette pochissima informazione quindi **utilizzo più bit**. (più fuochi)
Essendo due gli stati di un bit il numero di stringhe diverse è uguale alle **potenze di 2.**
![[1_segnali_codifica.pdf#page=22&rect=38,85,689,248|1_segnali_codifica, p.22|400]]

I **Transistor**
Un transistor è un interruttore ad azionamento elettronico
- estremamente precisi ed economici.
- **ingresso ed uscita della stessa natura**, entrambe elettriche: è possibile usare l’**uscita di un transistor per pilotarne altri**.

**Livelli di astrazione**
![[1_segnali_codifica.pdf#page=35&rect=12,5,703,456|1_segnali_codifica, p.35|500]]

## La rete logica
Una rete logica è quindi un’**astrazione per una combinazione di interruttori** che elaborano segnali binari.
I componenti elementari sono i **Gate**
![[1_segnali_codifica.pdf#page=38&rect=24,28,688,212|1_segnali_codifica, p.38|500]]

Il numero di funzioni diverse con n ingressi e un uscita binaria sono 
$$2^(2^(n))$$
### Gate con 1 ingresso
Slide OSUCRa
![[1_segnali_codifica.pdf#page=39&rect=261,147,456,232|1_segnali_codifica, p.39|300]]
L'unica funzione interessante è il **gate not**
![[1_segnali_codifica.pdf#page=40&rect=66,16,601,140|1_segnali_codifica, p.40|400]]
Restituisce sempre l'opposto

### Gate con 2 ingressi
![[1_segnali_codifica.pdf#page=42&rect=76,193,645,341|1_segnali_codifica, p.42]]
Le uniche funzioni commutative e non banali sono quelle cerchiate in rosso.

**Il gate AND**
è l'astrazione di due interruttori in **serie**
![[1_segnali_codifica.pdf#page=43&rect=67,10,608,195|1_segnali_codifica, p.43]]

![[1_segnali_codifica.pdf#page=43&rect=395,217,712,413|1_segnali_codifica, p.43|200]]

**Il gate OR**
![[1_segnali_codifica.pdf#page=44&rect=373,199,710,458|1_segnali_codifica, p.44|200]]
![[1_segnali_codifica.pdf#page=44&rect=65,9,611,198|1_segnali_codifica, p.44]]

And e or possono avere piu ingressi ma il loro senso non cambia
Il numero di ingressi è il fan-in

il Gate Xor
![[1_segnali_codifica.pdf#page=46&rect=373,199,715,453|1_segnali_codifica, p.46]]
![[1_segnali_codifica.pdf#page=46&rect=68,8,612,194|1_segnali_codifica, p.46]]

Somma in complemento a due
Se il numero di 1 è dispari restituisce 1

Le restanti funzioni sono il not delle precedenti
![[1_segnali_codifica.pdf#page=48&rect=68,10,609,458|1_segnali_codifica, p.48]]

Questi gate possono essere ottenuti combinandone altri ma per comodità e ottimizzazione viene realizzato un operatore dedicato.

## Diagrammi a occhio
![[1_segnali_codifica.pdf#page=50&rect=335,0,657,238|1_segnali_codifica, p.50|300]]


## bus di segnali
le linee quando si intrecciano dicono che il bus cambia valore ma non rappresentano il valore dei bit
![[1_segnali_codifica.pdf#page=51&rect=10,4,659,198|1_segnali_codifica, p.51|400]]

## Ritardi di propagazione
![[1_segnali_codifica.pdf#page=52&rect=46,2,686,197|1_segnali_codifica, p.52|400]]
## Possibili configurazioni
- In **serie** ovvero l’uscita del gate a monte è uno degli ingressi del gate a valle
![[1_segnali_codifica.pdf#page=53&rect=430,324,659,379|1_segnali_codifica, p.53|200]]
- In **parallelo**, ovvero **alcuni degli ingressi sono in comune** (e **non le uscite**)
![[1_segnali_codifica.pdf#page=53&rect=405,161,547,293|1_segnali_codifica, p.53|100]]
- In **retroazione**, ovvero l’**uscita di un gate è uno degli ingressi** dell’altro e viceversa
![[1_segnali_codifica.pdf#page=53&rect=104,26,334,113|1_segnali_codifica, p.53| 200]]
![[1_segnali_codifica.pdf#page=53&rect=457,5,622,145|1_segnali_codifica, p.53|150]]
> [!danger]
> **NON** si possono **collegare tra loro** **uscite** di gate

## Il codice binario
Funzione dall’insieme delle 2^n configurazioni di n bit ad un insieme di M informazioni (simboli alfanumerici, colori, numeri, ecc.).
