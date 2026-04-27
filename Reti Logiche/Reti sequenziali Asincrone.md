A differenza delle reti combinatorie, dove l'uscita dipende solo dagli ingressi attuali, nelle reti sequenziali l'uscita dipende anche dalla **storia passata** degli ingressi.
- **Problema**: la tabella della verità non è sufficiente per descrivere il comportamento di una rete sequenziale. A parità di ingresso, l'uscita può essere diversa.
es. cassaforte che per aprirsi deve ricevere 11 01 11.
**La tabella delle verità è non è più sufficiente**.
![[6_reti_sequenziali_asincrone.pdf#page=3&rect=51,79,691,396|6_reti_sequenziali_asincrone, p.3|500]]
- **Soluzione**: introdurre il concetto di **stato**. Lo stato è un riassunto della storia passata che determina il comportamento futuro della rete.
### Stato Presente e Stato Futuro
> [!Gli stati]
> Gli stati necessari sono tutti i **riassunti «utili»** della storia passata degli ingressi. «Utile» = che **permette di disambiguare ingressi futuri,** che cambia il comportamento futuro della rete
- **Stato Presente ($y$)** : lo stato corrente della rete.
- **Stato Futuro ($Y$)** : il nuovo stato che verrà memorizzato, calcolato in base agli ingressi attuali e allo stato presente.

Quando gli ingressi cambiano, la rete:
1.  Calcola una nuova uscita.
2.  Determina se l'evento appena accaduto è "utile" da ricordare.
3.  Se sì, cambia il proprio stato, sovrascrivendo lo stato presente con lo stato futuro.
### Reti Sequenziali Asincrone (RSA)
> [!RSA]
> In una RSA, la transizione di stato avviene **non appena** lo stato futuro è disponibile. Non c'è un segnale di clock che sincronizza le operazioni. 

La sequenza operativa tipica è:
1.  **Stabilità iniziale**: stato presente = stato futuro, ingressi costanti. Un **Ingresso cambia** 
![[6_reti_sequenziali_asincrone.pdf#page=8&rect=1,17,697,363|6_reti_sequenziali_asincrone, p.8|500]]
2.  **Transizione**: dopo un ritardo ($T_p$) dovuto alla logica combinatoria, vengono calcolati nuovo stato futuro e nuova uscita.
![[6_reti_sequenziali_asincrone.pdf#page=9&rect=8,20,694,361|6_reti_sequenziali_asincrone, p.9|500]]
3.  **Aggiornamento**: lo stato futuro diventa immediatamente il nuovo stato presente (retroazione diretta).
![[6_reti_sequenziali_asincrone.pdf#page=10&rect=11,18,695,357|6_reti_sequenziali_asincrone, p.10|500]]
4.  **Nuova stabilità**: la rete raggiunge una nuova condizione di stabilità, attendendo il prossimo cambiamento.
![[6_reti_sequenziali_asincrone.pdf#page=11&rect=11,21,693,358|6_reti_sequenziali_asincrone, p.11|500]]

**Codifica degli Stati**
- Lo stato è rappresentato internamente da segnali binari.
- Con **K** stati, sono necessari **k = ⌈log₂(K)⌉** bit di stato.
- Esempio di codifica (arbitraria ma non sempre valida):

| $y_1$ | $y_0$ | Stato | Significato |
| :--- | :--- | :--- | :--- |
| 0 | 0 | A | "Nessun simbolo della sequenza visto" |
| 0 | 1 | B | "Visto il primo (1,1) della sequenza" |
| 1 | 1 | C | "Visto (1,1) - (0,1)" |
| 1 | 0 | D | "Sequenza riconosciuta" |
### Struttura di una RSA
![[6_reti_sequenziali_asincrone.pdf#page=15&rect=65,13,670,218|6_reti_sequenziali_asincrone, p.15|450]]
Una RSA è composta da:
- **Ingressi**: $n$ ingressi esterni ($x_0, \dots, x_{n-1}$) e $k$ ingressi di stato presente ($y_0, \dots, y_{k-1}$).
- **Uscite**: $m$ uscite ($z_0, \dots, z_{m-1}$) e $k$ uscite di stato futuro ($Y_0, \dots, Y_{k-1}$).
- **Reti Combinatorie**: $m + k$ reti combinatorie che calcolano uscite e stato futuro.

Le funzioni sono:
- **Uscita**: $Z_i = F_i(x_0, \dots, x_{n-1}, y_0, \dots, y_{k-1})$
- **Stato Futuro**: $Y_i = G_i(x_0, \dots, x_{n-1}, y_0, \dots, y_{k-1})$

> [!important]
> Quindi una RSA **è composta da m + k reti combinatorie ognuna con n + k ingressi**
### Il Ruolo del Ritardo e della Memoria
I segnali di **stato futuro** ($Y$) sono collegati in **retroazione diretta** agli ingressi di **stato presente** ($y$).
![[6_reti_sequenziali_asincrone.pdf#page=17&rect=24,28,375,171|6_reti_sequenziali_asincrone, p.17|200]]
Matematicamente questo non ha senso ma  è possibile grazie al **ritardo di propagazione** ($T_p$) delle reti combinatorie.
![[6_reti_sequenziali_asincrone.pdf#page=18&rect=21,2,708,218|6_reti_sequenziali_asincrone, p.18|500]]
**Il ritardo agisce come una memoria temporanea**: quando gli ingressi cambiano, la rete ricorda il vecchio valore di stato per un breve tempo ($T_p$), permettendo il calcolo corretto del nuovo stato.
![[6_reti_sequenziali_asincrone.pdf#page=19&rect=14,0,710,199|6_reti_sequenziali_asincrone, p.19|500]]
**In una RSA, la "memoria" è quindi realizzata dal ritardo stesso dei circuiti combinatori.**
Ogni rete combinatoria con anelli di retroazione diretta è quindi in realtà una RSA
**La rete ha k bit di stato se ci sono k segnali in retroazione**
Quindi lo stato futuro non è in realtà osservabile. Lo è solo quando diventa stato presente.
### Automa a Stati Finiti (FSM)
Una RSA è un'implementazione fisica di un Automa a Stati Finiti.

Un FSM è definito da:
- **I**: Insieme finito di simboli di ingresso.
- **U**: Insieme finito di simboli di uscita.
- **S**: Insieme finito di stati.
- **F: S × I → U**: Funzione di uscita.
- **G: S × I → S**: Funzione di prossimo stato.
![[6_reti_sequenziali_asincrone.pdf#page=25&rect=86,3,649,395|6_reti_sequenziali_asincrone, p.25|500]]
Esistono due tipi principali di automi:
- **Automa di Mealy**: L'uscita dipende dallo **stato presente e dall'ingresso corrente** ($F: S \times I \rightarrow U$). L'uscita è associata alle transizioni.
- **Automa di Moore**: L'uscita dipende **solo dallo stato presente** ($F: S \rightarrow U$). L'uscita è associata agli stati.
### Rappresentazione del Comportamento
![[6_reti_sequenziali_asincrone.pdf#page=28&rect=14,19,714,444|6_reti_sequenziali_asincrone, p.28|600]]
### Grafo degli Stati
Un grafo orientato che rappresenta il comportamento della macchina.
- **Nodo**: rappresenta uno **stato presente**.
- **Arco (transizione)** : da uno stato presente a uno stato futuro, etichettato con **ingresso/uscita** (Mealy). Per Moore, l'uscita è scritta all'interno del nodo.
- **Regole**:
    - Da ogni nodo devono uscire tanti archi quante sono le possibili configurazioni di ingresso.
    - Un **arco che ritorna sul nodo stesso** indica una condizione di **stabilità**.
    - Il grafo deve essere **strettamente connesso**: deve essere possibile raggiungere qualsiasi nodo da qualsiasi altro nodo, **evitando stati irraggiungibili o assorbenti.**
![[6_reti_sequenziali_asincrone.pdf#page=31&rect=33,40,705,323|6_reti_sequenziali_asincrone, p.31|500]]![[6_reti_sequenziali_asincrone.pdf#page=32&rect=67,40,692,251|6_reti_sequenziali_asincrone, p.32|500]]
Lo stato iniziale in quanto stato deve essere sempre raggiungibile da altri stati (si forma un loop).

**Reset**
Dopo l'**accensione** la macchina produce un **output random** (es. la cassaforte potrebbe aprirsi quando accesa!).
Si utilizza quindi un ingresso di **reset** che porta **per i primi istanti dopo l'accensione la rete a un valore prestabilito.**
Il componente reset assume **valore 1 all'inizio e poi 0**
![[Screenshot from 2026-04-13 17-37-07.png|400]]
é importante stabilire dove viene collegato il reset
![[6_reti_sequenziali_asincrone.pdf#page=38&rect=30,206,181,314|6_reti_sequenziali_asincrone, p.38|200]]

### Tabella di Flusso
Utile per **verificare la correttezza** del grafo fatto
![[6_reti_sequenziali_asincrone.pdf#page=39&rect=7,3,716,420|6_reti_sequenziali_asincrone, p.39|500]]
Una rappresentazione tabellare alternativa e più ordinata, specialmente per la sintesi.
- **Righe**: rappresentano gli **stati presenti**.
- **Colonne**: rappresentano le combinazioni di **ingresso**.
- **Celle (Mealy)** : contengono la coppia `(stato futuro, uscita)` per quella combinazione di stato presente e ingresso.
- **Celle cerchiate**: indicano le condizioni di **stabilità** (stato futuro = stato presente).
- **Celle (Moore)** : l'uscita è costante per ogni riga (stato) e viene riportata in una colonna aggiuntiva.
Mealy
![[6_reti_sequenziali_asincrone.pdf#page=40&rect=14,39,704,288|6_reti_sequenziali_asincrone, p.40|500]]
Moore
![[6_reti_sequenziali_asincrone.pdf#page=41&rect=13,5,709,318|6_reti_sequenziali_asincrone, p.41|500]]
**Controlli formali per la tabella di flusso:**
1.  Ogni riga deve avere **almeno una** cella con condizione di stabilità (cella cerchiata).
2.  Le celle con stato futuro diverso da quello presente (instabilità) devono puntare a uno stato futuro che è stabile in quella colonna di ingresso. Questo garantisce l'assenza di transizioni multiple.
### Procedimento di Sintesi di una RSA
**1 Individuazione del Grafo degli Stati**
- Definire la specifica comportamentale.
- Identificare gli stati necessari ("riassunti utili" della storia).
- Disegnare il grafo degli stati, etichettando le transizioni (Mealy) o gli stati (Moore).
- **Obiettivo**: ottenere una rappresentazione completa del comportamento desiderato.

**2 Definizione della Tabella di Flusso**
- Tradurre il grafo degli stati in una tabella di flusso.
- Verificare i **controlli formali**:
    1.  Almeno una stabilità per riga.
    2.  Nessuna transizione multipla (lo stato futuro di una cella instabile deve essere stabile).

**3 Codifica degli Stati e Tabella delle Transizioni**
- Scegliere una codifica binaria per ogni stato simbolico (es. A=00, B=01, C=11, D=10).
- Sostituire nella tabella di flusso gli stati simbolici con le loro rappresentazioni binarie per ottenere la **tabella delle transizioni**.
- La tabella delle transizioni ha $2^k$ righe, dove $k$ è il numero di bit di stato. Gli stati non utilizzati diventano condizioni di indifferenza (don't care).
- **Nota**: Non tutte le codifiche sono valide; una scelta errata può portare a comportamenti indesiderati (es. corse critiche).

**4 Sintesi delle Reti Combinatorie**
- La tabella delle transizioni è composta da più tabelle di verità: una per ogni bit di uscita ($Z_i$) e una per ogni bit di stato futuro ($Y_i$).
- Per ogni funzione, utilizzare le mappe di Karnaugh (o altri metodi) per ricavare l'espressione booleana minimizzata.
- Le variabili di ingresso per le mappe di Karnaugh sono gli ingressi esterni ($x$) e i bit di stato presente ($y$).

**5 Schema Logico e Ingresso di Reset**
- Disegnare lo schema logico finale, composto dalle reti combinatorie sintetizzate (porte logiche).
- **Inserire l'ingresso di Reset**:
    - All'accensione, lo stato iniziale dei segnali in retroazione è imprevedibile.
    - L'ingresso di reset è un segnale (tipicamente attivo alto) che forza la rete in uno stato iniziale noto (es. stato A) durante l'avvio.
    - Viene implementato modificando le funzioni di stato futuro per forzare la codifica dello stato iniziale quando `reset = 1`.

**Esempio: Sintesi di una Lampada da Tavolo**
**Specifica**: Una lampada si accende/spegne a ogni pressione e rilascio del pulsante $x$. All'accensione, la lampada è spenta ($z=0$).

**Passo 1: Grafo degli Stati**
Si identificano 4 stati:
- **A** (pari): Numero pari di pressioni (lampada spenta).
- **B** (transizione pari->dispari): Pulsante premuto, in transizione.
- **C** (dispari): Numero dispari di pressioni (lampada accesa).
- **D** (transizione dispari->pari): Pulsante premuto, in transizione.
(Il grafo mostra transizioni: da A, con x=1 si va a B; da B, con x=0 si va a C, etc.)
![[6_reti_sequenziali_asincrone.pdf#page=44&rect=7,25,714,351|6_reti_sequenziali_asincrone, p.44|500]]

**Passo 2: Tabella di Flusso**
La tabella viene compilata dal grafo, garantendo le condizioni di stabilità.
![[6_reti_sequenziali_asincrone.pdf#page=45&rect=23,21,632,309|6_reti_sequenziali_asincrone, p.45|500]]

**Passo 3: Tabella delle Transizioni**
Si sceglie una codifica (es. A=00, B=01, C=11, D=10) e si sostituisce nella tabella di flusso.
![[6_reti_sequenziali_asincrone.pdf#page=46&rect=4,-1,713,305|6_reti_sequenziali_asincrone, p.46|500]]

**Passo 4: Sintesi delle Reti Combinatorie**
Si ricavano le espressioni per i bit di stato futuro ($Y_1, Y_0$) e per l'uscita $z$ (che è uguale al bit più significativo dello stato, es. $z = y_1$, per la codifica scelta) utilizzando le mappe di Karnaugh.
![[6_reti_sequenziali_asincrone.pdf#page=47&rect=1,3,708,374|6_reti_sequenziali_asincrone, p.47|500]]

**Passo 5: Schema Logico e Reset**
Si disegna lo schema con le porte logiche e si aggiunge la logica di reset per forzare lo stato A (es. $y_1=0, y_0=0$) quando il segnale di reset è attivo.
![[6_reti_sequenziali_asincrone.pdf#page=48&rect=35,56,707,431|6_reti_sequenziali_asincrone, p.48|500]]
> [!tip]
> per produrre un **uscita in fase di accensione positiva si utilizza un OR con Reset non negato**

Ecco gli appunti completi, precisi e dettagliati basati esclusivamente sul contenuto del file PDF fornito.
### Alee di reti combinatorie
Per comprendere il corretto funzionamento di una Rete Sequenziale Asincrona (RSA), è necessario analizzare il comportamento di una rete combinatoria quando i suoi ingressi cambiano.
- **Comportamento in transitorio:** Fase successiva a un cambiamento degli ingressi in cui l'uscita non ha ancora raggiunto il nuovo valore di regime.
- **Ritardo puro:** Caso più semplice di transitorio. L'uscita mantiene il vecchio valore per un tempo pari al ritardo di propagazione $\tau_{p}$ prima di aggiornarsi. È ineliminabile ma non dannoso.
- **Le alee** sono **malfunzionamenti temporanei che si verificano durante il transitorio di una rete combinatoria**.
![[6_reti_sequenziali_asincrone.pdf#page=50&rect=73,4,695,226|6_reti_sequenziali_asincrone, p.50|500]]

**Alea Dinamica:**
 - L'uscita, dovendo cambiare da un valore logico a un altro (es. 0→1), oscilla più volte prima di assestarsi sul valore finale.
- **Causa:** I diversi ritardi dei percorsi interni che agiscono sul segnale di uscita \( Z \).
- **Prevenzione:** Una rete combinatoria descritta da espressioni **SP (Somma di Prodotti)** o **PS (Prodotto di Somme)** **non** presenta mai alee dinamiche.
![[6_reti_sequenziali_asincrone.pdf#page=52&rect=-2,286,589,456|6_reti_sequenziali_asincrone, p.52|400]]
![[6_reti_sequenziali_asincrone.pdf#page=52&rect=63,2,393,287|6_reti_sequenziali_asincrone, p.52|250]]
**Alea Statica:**
- L'uscita, che **dovrebbe rimanere costante**, subisce una variazione temporanea e indesiderata durante il transitorio.
- **Causa (non) principale:** Nonostante l'ipotesi ideale che due ingressi cambino "contemporaneamente" (es. negli ingressi di una porta OR che dovrebbe restare a 1), in realtà un segnale cambierà sempre prima dell'altro in modo aleatorio (impossibile sincronia perfetta).
- **Condizione Necessaria (ma non sufficiente):** Per evitare alea statica è necessario variare **un solo ingresso alla volta**.
![[6_reti_sequenziali_asincrone.pdf#page=54&rect=35,-1,683,255|6_reti_sequenziali_asincrone, p.54|500]]

**Esempio: Alea Statica nel Multiplexer (MUX) (Causa principale)**
- **Scenario:** MUX a 2 vie con ingressi \( I_0 \) e \( I_1 \) entrambi a `1` e segnale di indirizzo \( A \). L'uscita \( Z \) dovrebbe restare sempre a `1`.
- **Problema:** Quando \( A \) commuta (es. da 1 a 0), il **disallineamento temporale** tra \( A \) e il suo complemento \( A' \) (causato dal ritardo del NOT) provoca un'alea statica (un breve impulso a `0`), anche se si cambia un solo ingresso ( A ) alla volta.
![[6_reti_sequenziali_asincrone.pdf#page=55&rect=11,291,456,456|6_reti_sequenziali_asincrone, p.55|400]]
![[6_reti_sequenziali_asincrone.pdf#page=55&rect=296,0,716,348|6_reti_sequenziali_asincrone, p.55|300]]

**Prevenzione delle Alee Statiche: Copertura Ridondante**
Per evitare alee statiche in fase di sintesi con le Mappe di Karnaugh:
> [!Regola]
> Si deve scegliere un'espressione **ridondante (non minima)** che racchiuda in uno stesso raggruppamento rettangolare **ogni coppia di celle adiacenti** contenenti `1` (per SP) o `0` (per PS).
![[6_reti_sequenziali_asincrone.pdf#page=56&rect=23,19,677,368|6_reti_sequenziali_asincrone, p.56|600]]

*Altri esempi*
![[6_reti_sequenziali_asincrone.pdf#page=59&rect=22,39,695,413|6_reti_sequenziali_asincrone, p.59|500]]
### Vincoli per il Corretto Funzionamento di una RSA
Le RSA funzionano a **inseguimento degli ingressi**: stato e uscita cambiano solo in risposta a variazioni degli ingressi. Dopo un transitorio, raggiungono un nuovo **stato stabile**. Il funzionamento che porta da una stabilità all'altra è detto **funzionamento in modo fondamentale**.
#### Durata degli Ingressi
> [!Regola]
> I segnali di ingresso possono essere modificati **solo dopo che la rete ha raggiunto la nuova stabilità**.
- **Tempo minimo di permanenza di una configurazione d'ingresso:**
    - Caso base: $2T_{pG}$, dove $T_{pG}$ è il massimo ritardo di propagazione della rete combinatoria.
        - Primo $T_{pG}$: per calcolare lo stato futuro a partire dal vecchio stato presente.
        - Secondo $T_{pG}$: per rendere stabile il nuovo stato.
    - Caso con **transizioni multiple** (t) transizioni): $(1 + t)T_{pG}$
#### Cambiamento dei Bit di Ingresso
> [!Regola]
 Le configurazioni consecutive degli ingressi devono essere **adiacenti** (differire al massimo di un bit).
- **Motivazione:** È impossibile variare contemporaneamente più bit di ingresso. Configurazioni non adiacenti (es. `10` → `01`) generano configurazioni intermedie "spurie" che la rete interpreta come comandi validi, portandola potenzialmente fuori strada.
- **Movimento sulla Tabella di Flusso:**
    1. Da una cella stabile, la rete si sposta nella **colonna adiacente** (nuovi ingressi) rimanendo sulla **stessa riga** (stato presente).
    2. Si sposta nella **riga** indicata dalla cella (nuovo stato futuro) rimanendo nella **stessa colonna** (ingressi stabili).
    3. Ripete il punto 2 fino a raggiungere una **cella stabile**.
Sono possibili solo configurazioni adiacenti sulla riga a configurazioni stabili.

#### Eliminazione delle Alee Statiche nella Rete di Retroazione
- **Regola:** Nella sintesi delle reti combinatorie che calcolano lo **stato futuro**, è obbligatorio eliminare le alee statiche usando la tecnica della **copertura ridondante**.
- **Motivazione:** Un'alea statica all'interno dell'anello di retroazione può essere interpretata come una variazione di stato futuro, introducendo **stati spuri** nella sequenza e compromettendo il funzionamento della RSA.
> [!regola]
> Ogni **coppia di celle adiacenti da coprire (nella mappa di Karnaugh per lo stato futuro) deve essere racchiusa in un raggruppamento rettangolare**.
![[6_reti_sequenziali_asincrone.pdf#page=70&rect=28,31,269,154|6_reti_sequenziali_asincrone, p.70|200]]
#### Codifica degli Stati e Corse Critiche
- **Concetto:** I bit di stato presente sono a tutti gli effetti ingressi della rete combinatoria di retroazione. Quando lo stato futuro calcolato differisce dallo stato presente per più di un bit, si verifica una **corsa** (i bit di stato cambiano in momenti leggermente diversi).
- **Corsa Critica:** Se, a seconda dell'ordine di cambiamento dei bit, la rete può raggiungere **stati stabili diversi** (malfunzionamento).
- **Corsa Non Critica (o Transizione Multipla):** La rete passa per stati intermedi, ma raggiunge sempre lo **stesso stato stabile finale**. È accettabile a patto che l'uscita sia corretta e gli ingressi restino stabili.

- **Regola di prevenzione a priori delle corse critiche (Procedura):**
    1. **Colonne con una sola stabilità:** Sostituire le indifferenze (`-`) con il simbolo/codice dello **stato stabile** di quella colonna.
    2. **Grafo delle Adiacenze:** Per **sole le colonne con più stabilità**, tracciare un grafo dove i nodi sono gli stati e i rami orientati rappresentano le transizioni (da stato presente a stato futuro).
    3. **Assegnazione Codice:** Sovrapporre il grafo a una mappa con codici di Gray per assegnare configurazioni binarie adiacenti ad ogni transizione. Se possibile, la codifica è valida.
    4. **Transizioni Multiple (se il punto 3 fallisce):** Se non si riesce a trovare una codifica adiacente per tutte le transizioni, si può modificare la tabella di flusso introducendo **transizioni multiple**. Si fa passare la transizione critica attraverso uno **stato intermedio** che già prevede come stato futuro lo stato di destinazione desiderato. Questo permette di spezzare una transizione in più transizioni adiacenti.
    5. **Aumento variabili di stato (ultima risorsa):** Se i punti 3 e 4 falliscono, si aumenta il numero di bit di stato e si riparte dal punto 3.

**Pdf per esempi**
## Analisi
Il procedimento di analisi di una rete sequenziale asincrona è formato da 5 passi e consente di dedurne il comportamento dallo schema logico
- individuazione delle variabili di stato
- analisi della parte combinatoria, 3: individuazione della tabella delle transizioni, 4: studio delle condizioni di stabilità e individuazione della tabella di flusso, 5: passaggio al grafo degli stati e descrizione testuale del comportamento.
