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

## Gli elementi di una rete
Una rete logica è quindi un’**astrazione per una combinazione di interruttori** che elaborano segnali binari.
I componenti elementari sono i **Gate** (transistor astratti a entità teoriche)
![[1_segnali_codifica.pdf#page=38&rect=24,28,688,212|1_segnali_codifica, p.38|500]]

Il numero di funzioni diverse con n ingressi e un uscita binaria sono  $2^{2^n}$
### Gate con 1 ingresso
con un solo ingresso ci sono 4 opzioni
![[1_segnali_codifica.pdf#page=39&rect=261,147,456,232|1_segnali_codifica, p.39|200]]
Tolte le funzioni prive di significato (identità e costanti) l'unica funzione interessante è f2.
**Il gate NOT**
![[1_segnali_codifica.pdf#page=40&rect=66,16,601,140|1_segnali_codifica, p.40|400]]
Restituisce sempre l'opposto
### Gate con 2 ingressi
Ci sono 16 combinazioni.
Tolte l'identità, le costanti, i NOT e le funzioni non commutative le uniche funzioni commutative e non banali sono quelle cerchiate in rosso.
![[1_segnali_codifica.pdf#page=42&rect=76,193,645,341|1_segnali_codifica, p.42|500]]

**Il gate AND**
è l'astrazione di due interruttori in **serie**
![[1_segnali_codifica.pdf#page=43&rect=67,10,608,195|1_segnali_codifica, p.43|400]]
![[1_segnali_codifica.pdf#page=43&rect=395,217,712,413|1_segnali_codifica, p.43|200]]
L’uscita assume valore logico «1» se **entrambi gli ingressi hanno valore «1».**

**Il gate OR**
è l’astrazione di due interruttori in **parallelo**
![[1_segnali_codifica.pdf#page=44&rect=65,9,611,198|1_segnali_codifica, p.44|400]]
![[1_segnali_codifica.pdf#page=44&rect=373,199,710,458|1_segnali_codifica, p.44|200]]
L’uscita assume valore logico «1» se almeno un ingresso ha valore «1».

> [!important]
> And e or possono avere **più ingressi** ma il loro **senso non cambia**
Il numero di ingressi è il **fan-in**

**Il Gate XOR**
è l’astrazione di due **deviatori**, ovvero interruttori che chiudono il circuito verso uno di due possibili percorsi.
![[1_segnali_codifica.pdf#page=46&rect=373,199,715,453|1_segnali_codifica, p.46|200]]
![[1_segnali_codifica.pdf#page=46&rect=68,8,612,194|1_segnali_codifica, p.46|400]]
Somma in complemento a due
Se il numero di 1 è dispari restituisce 1

**Le restanti funzioni sono il NOT delle precedenti**
![[1_segnali_codifica.pdf#page=48&rect=68,10,609,458|1_segnali_codifica, p.48|400]]
Questi gate possono essere ottenuti combinandone altri ma per comodità e ottimizzazione viene realizzato un operatore dedicato.
## Diagrammi a occhio
Il diagramma riposta anche la fase di transito del transistor.
![[1_segnali_codifica.pdf#page=50&rect=87,5,653,237|1_segnali_codifica, p.50|400]]
## Bus di segnali
L’**evoluzione di un gruppo di bit** può anche essere descritta scrivendone la **configurazione binaria nel diagramma.**
Un gruppo di segnali viene anche detto un **bus**.
Quando il valore **cambia** le linee si **intrecciano** (**non rappresentano il valore dei bit**)
es. Bus Colore $COLORE[2..0]$
![[1_segnali_codifica.pdf#page=51&rect=10,4,659,198|1_segnali_codifica, p.51|500]]
## Ritardi di propagazione
I transistor hanno un **limite fisico di velocità**

![[1_segnali_codifica.pdf#page=52&rect=46,2,686,197|1_segnali_codifica, p.52|500]]
I ritardi sono:
- **INERZIALI** (un impulso di **durata inferiore** a $\tau_p$ su uno degli ingressi **non appare in uscita**.)
- **SEMPLICI** (quando cambia un ingresso **l'uscita non cambia istantaneamente**.)
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

## La rappresentazione Binaria
Funzione dall’insieme delle 2^n configurazioni di n bit ad un insieme di M informazioni (simboli alfanumerici, colori, numeri, ecc.).


**es**. codifica della **posizione**
Vogliamo conoscere in ogni instante la posizione di un carrello lungo una rotaia.
![[1_segnali_codifica.pdf#page=74&rect=97,0,563,304|1_segnali_codifica, p.74|400]]
Potrei codificare le posizioni come **numeri binari** ma basterebbe un lievissimo **disallineamento** dei riquadri per farmi leggere un numero **sbagliato**
> [!Codice di Gray]
> La soluzione è avere una codifica in cui **cambia un solo bit alla volta**

![[1_segnali_codifica.pdf#page=75&rect=89,-1,685,368|1_segnali_codifica, p.75|400]]
### La conversione di codice
![[1_segnali_codifica.pdf#page=77&rect=57,225,614,451|1_segnali_codifica, p.77|400]]
Il **codice interno** è di norma non ridondante per minimizzare il numero di bit da elaborare e da memorizzare.
Il **codice esterno** è di norma 
- **ridondante**, per semplificare la generazione e l’interpretazione delle informazioni
- **standard**, per rendere possibile la connessione di macchine realizzate da costruttori diversi.

**Codice proprietario** - Realizzato da un'azienda per ottimizzare e proteggere il suo mercato
**Codice standard** - Codice scelto da una norma di legge o da una marcata superiorità (PDF)
### Il codice ASCII
Primo standard per i caratteri basato su una codifica a 7 bit.
Il numero di caratteri è **largamente insufficiente**.
Viene poi esteso aggiungendo altri caratteri ma ormai è datato.
### I codici UNICODE
Le architetture sono ottimizzate per operare su gruppi di byte (= 8 bit) che seguono le potenze di 2.
Sono stati quindi definiti 3 standard per mappare un carattere Unicode da 21 bit:
- **UTF-32**, ovvero usare **32 bit** (4 byte) per codificare i 21 bit necessari per ogni carattere, aggiungendo 11 volte «0» a sinistra.
- **UTF-16**, usare 2 byte (= **16 bit**) per i 63,000 caratteri Unicode più comuni, e **4 byte** per i restanti caratteri
- **UTF-8**, ovvero usare **1 byte** per i 128 caratteri ASCII (**retrocompatibile con ASCII**), **2 byte** per altri 1920 caratteri relativamente comuni (lingue europee e arabe), **3 e 4 byte** per gli altri caratteri più rari.
![[1_segnali_codifica.pdf#page=83&rect=20,18,711,462|1_segnali_codifica, p.83|600]]
### Bitmap
Rappresentazione binaria dei **simboli grafici dei caratteri**
Esistono diverse colorazioni che variano le dimensioni del carattere in memoria.
Un **font** è un gruppo di caratteri bitmap.

# Le reti logiche
Modello astratto composto di **Gate** che realizza comportamenti complessi.
![[2_reti_combinatorie.pdf#page=2&rect=165,120,549,289|2_reti_combinatorie, p.2|300]]
> [!Convenzioni]
> **ingressi** **a sinistra, uscite a destra, nomi dei segnali di ingresso/uscita della rete indicati all’interno della rete**
> > [!danger]
> è FONDAMENTALE scrivere sempre i **nomi degli ingressi** di una macchina
## Macchina combinatoria
L'**output dipende solo dagli ingressi**

Es Il **DISPLAY A 7 SEGMENTI**
Il bit più significativo va collegato a D
![[2_reti_combinatorie.pdf#page=3&rect=136,246,579,467|2_reti_combinatorie, p.3|300]]
## Macchina sequenziale
L'output dipende anche **dall'ordine di input**.

Es. La **CASSAFORTE**
per aprirla (z = 1) bisogna premere in sequenza i tasti collegati a x e y
![[2_reti_combinatorie.pdf#page=6&rect=444,11,678,130|2_reti_combinatorie, p.6|200]]

> [!riassunto]
> Se in presenza di una **stessa configurazione** di ingressi la rete deve fornire **2 o più uscite diverse**, allora è una rete **sequenziale**, altrimenti è una rete **combinatoria**
## Composizione di macchine
La **disposizione in serie e/o in parallelo** di reti logiche combinatorie è ancora una rete logica combinatoria.
Per progettare una rete con M uscite si possono quindi progettare **M reti con una sola uscita**, operanti in **parallelo**.
![[2_reti_combinatorie.pdf#page=9&rect=78,11,721,295|2_reti_combinatorie, p.9|400]]
## Funzioni complete e incomplete
Funzione **COMPLETA** - Per **ognuno dei possibili ingressi ha un'uscita** definita
Funzione **INCOMPLETA** - vi è almeno una configurazione degli ingressi per cui **non è specificato il valore dell’uscita**

La **tabella delle verità** descrive univocamente il comportamento di una rete combinatoria.
Se una funzione è incompleta righe non definite possono essere omesse oppure avere output "-".
Il "-" del **don't care** è esclusivamente per il progettista (il bit resta solo 1  o 0)
![[2_reti_combinatorie.pdf#page=12&rect=14,8,621,199|2_reti_combinatorie, p.12|400]]

Es. il convertitore BCD/**7 Segmenti**
Sono in realtà 7 funzioni in parallelo di 4 ingressi
(non tutte le combinazioni di segmenti hanno senso)
## L'algebra di commutazione
è un'algebra binaria basata solo su OR, AND e NOT.