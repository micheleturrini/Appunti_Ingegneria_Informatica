[[0 - Index]]
Esempio: una cassaforte con due ingressi $(x_0, x_1)$ la cui uscita $z=1$ (porta aperta) si attiva **se e solo se** la sequenza di ingressi è stata nell'ordine:
$$(1,1) \;\to\; (0,1) \;\to\; (1,1)$$
Una rete combinatoria non è sufficiente: serve uno **stato interno** che ricordi la storia degli ingressi.

Un primo approccio è la **retroazione diretta** (RSA): lo stato futuro è ricalcolato da una rete combinatoria e riportato direttamente all'ingresso di stato presente. La "memoria" è il **ritardo** della rete combinatoria.

**Vantaggio RSA**: risposta immediata al variare degli ingressi.

**Limiti RSA**:
- Gli ingressi devono rimanere stabili abbastanza a lungo da permettere alla rete di raggiungere una nuova stabilità.
- Gli ingressi possono variare **solo un bit alla volta** (vincolo di adiacenza delle configurazioni d'ingresso consecutive).
- Lo stato futuro deve variare un bit alla volta $\Rightarrow$ necessità di rimuovere le **corse critiche** nella codifica degli stati.
- Rischio di **instabilità** a causa di **alee** nelle reti combinatorie di aggiornamento dello stato.
- Progettazione molto complessa al crescere del numero di ingressi e stati.
- A causa del vincolo di variazione di un bit alla volta, l'RSA **non può riconoscere combinazioni come `00 → 11 → 01`** (gli ingressi passerebbero da 00 a 11 cambiando due bit).
- L'RSA non può **discriminare la durata degli ingressi**: sequenze con simboli d'ingresso ripetuti (es. `00 → 01 → 01 → 11`) non sono gestibili perché la rete "insegue" gli ingressi e non ha un meccanismo per scandire il tempo.
### Soluzione: il ritardo controllato e il campionamento periodico
Per superare i limiti delle RSA si introduce un **ritardo controllato** mediante **campionamento periodico** dello stato futuro. L'idea:
- Si calcola lo stato futuro $S^*$ tramite una rete combinatoria $G$.
- Invece di riportarlo immediatamente allo stato presente, lo si **campiona** (memorizza) ad intervalli regolari $T_0$ usando dei **flip-flop D**.
- Lo stato presente $S$ si aggiorna solo nei fronti di salita del clock con il valore campionato alla fine del periodo precedente.
![[8_reti_sequenziali_sincrone.pdf#page=6&rect=31,29,715,469|8_reti_sequenziali_sincrone, p.6|600]]
![[8_reti_sequenziali_sincrone.pdf#page=7&rect=71,174,625,471|8_reti_sequenziali_sincrone, p.7|600]]
### Struttura generale
![[8_reti_sequenziali_sincrone.pdf#page=10&rect=12,1,702,326|8_reti_sequenziali_sincrone, p.10|600]]
- Una o più **reti combinatorie** $F$ (uscita) e $G$ (stato futuro).
- **Flip-flop D** in retroazione: il loro ingresso $D$ è lo stato futuro $Y$, l'uscita $Q$ è lo stato presente $y$.
- Un **segnale di clock** comune a tutti i FF-D, con periodo $T_0$, generato da un oscillatore al quarzo.

**CICLO DI CLOCK**
1.  Al **fronte di salita** del clock, i FF-D campionano lo stato futuro $Y$ (che è a regime).
	![[8_reti_sequenziali_sincrone.pdf#page=11&rect=8,9,709,364|8_reti_sequenziali_sincrone, p.11|600]]
2.  Dopo il tempo di risposta $t_r$ dei FF, lo stato campionato diventa lo **stato presente** $y$.
	![[8_reti_sequenziali_sincrone.pdf#page=12&rect=4,15,709,372|8_reti_sequenziali_sincrone, p.12|600]]
3.  Il cambiamento di $y$ e/o degli ingressi $x$ provoca il ricalcolo della rete $G$, che produce un nuovo stato futuro $Y^*$. Durante questa fase possono verificarsi stati spuri, ma vengono ignorati perché non campionati.
	![[8_reti_sequenziali_sincrone.pdf#page=13&rect=7,-1,707,352|8_reti_sequenziali_sincrone, p.13|600]]
4.  Il nuovo $Y$ si stabilizza entro un tempo massimo e rimane stabile per il **tempo di setup** $t_{su}$ prima del prossimo fronte di salita.
	![[8_reti_sequenziali_sincrone.pdf#page=14&rect=13,18,709,352|8_reti_sequenziali_sincrone, p.14|600]]
La rete **non funziona ad inseguimento**: ogni stato presente è stabile per almeno un intero periodo di clock. L'uscita e lo stato futuro possono cambiare anche senza variazione degli ingressi, perché cambia lo stato presente (ingresso della $G$).
> [!important]
> Il **solo evento** che fa evolvere lo stato e l'uscita è il **fronte di salita del clock**.

![[8_reti_sequenziali_sincrone.pdf#page=15&rect=20,6,708,319|8_reti_sequenziali_sincrone, p.15|700]]
### Vincoli temporali
Il periodo di clock $T_0$ ha un **valore minimo** determinato da:
- $t_r$: tempo di risposta dei FF-D dopo il fronte di clock (dopo $t_r$, i $y$ sono stabili).
- $t_i$: ritardo con cui gli ingressi $x$ diventano stabili (si assume che gli ingressi siano sincroni, cioè varino subito dopo il clock).
- $t_{pd,G}$: ritardo di propagazione della rete combinatoria $G$ (che genera $Y$ a partire da $x$ e $y$).
- $t_{su}$: tempo di setup dei FF-D (i dati $Y$ devono essere stabili prima del fronte di salita successivo).
- $t_h$: tempo di hold (i dati devono rimanere stabili per un breve tempo dopo il fronte).

L’unico vincolo in una RSS è che **il periodo di clock $T_{0}$ ha un valore minimo**: i nuovi ingressi e lo stato presente devono avere **il tempo di propagarsi attraverso la funzione G**.
In aggiunta al ritardo della rete G, **anche i FF-D aggiungono vincoli temporali.** 
- Tempo di **set-up** $t_{su}$ : tempo minimo in cui D deve essere costante prima del fronte
- Tempo di **hold** $t_{h}$ : tempo minimo in cui D deve essere costante dopo il fronte 
- Tempo di **risposta** $t_{r}$ : tempo massimo di durata del transitorio sulle uscite (Q e Q’) dopo il fronte
Quindi:
- i bit di **stato futuro** $Y$ devono **rimanere costanti per $t_{su}+t_{h}$**
- i bit di **stato presente** $y$ saranno **ingressi stabili per G dopo $t_{r}$ dal fronte del clock**
![[8_reti_sequenziali_sincrone.pdf#page=16&rect=5,6,675,279|8_reti_sequenziali_sincrone, p.16|500]]
Per garantire il corretto funzionamento della rete il periodo del segnale di clock $T_{0}$ deve essere quindi **maggiore della somma del tempo necessario ad avere ingressi a regime, del ritardo della rete combinatoria G e del tempo di set-up dei FF-D.**
![[8_reti_sequenziali_sincrone.pdf#page=17&rect=441,279,681,486|8_reti_sequenziali_sincrone, p.17|200]]
![[8_reti_sequenziali_sincrone.pdf#page=17&rect=2,-2,705,260|8_reti_sequenziali_sincrone, p.17|700]]
### Il modello matematico: Finite State Machine (FSM)
Una RSS realizza un **automa a stati finiti** (FSM), definito da:
$$FSM = {I, U, S, F, G}$$
- **Insiemi**:
  - $I$: alfabeto di ingresso $\{i_1, i_2, \dots, i_N\}$
  - $U$: alfabeto di uscita $\{u_1, u_2, \dots, u_M\}$
  - $S$: insieme degli stati $\{s_1, s_2, \dots, s_K\}$
- **Funzioni**:
  - Funzione di uscita: $F : S \times I \to U$
  - Funzione di aggiornamento dello stato: $G : S \times I \to S$
Nelle RSS la **memoria** che mantiene lo stato presente $s$ fino all'aggiornamento con $s^*$ è realizzata dai flip-flop D.
### Modelli di Mealy e Moore
![[8_reti_sequenziali_sincrone.pdf#page=19&rect=74,2,650,405|8_reti_sequenziali_sincrone, p.19|500]]
#### Modello di Moore
L'uscita $F$ dipende **solo dallo stato presente** $y$: $z = F(y)$. Non c'è percorso combinatorio diretto tra ingressi e uscite.
**Implicazioni**:
- L'uscita varia **in sincrono col clock**, sempre con un periodo di clock di **ritardo** rispetto alla variazione degli ingressi.
- Gli ingressi possono variare anche in modo asincrono (purché rispettino setup/hold): l'uscita rimane stabile durante il periodo.
Principale limite: **latenza** maggiore **(risposta ritardata di un colpo di clock).**
![[8_reti_sequenziali_sincrone.pdf#page=21&rect=3,6,716,437|8_reti_sequenziali_sincrone, p.21|700]]
### Modello di Mealy
L'uscita $F$ dipende sia dallo stato presente $y$ **che dagli ingressi** $x$: $z = F(y, x)$. Esiste sempre un percorso combinatorio diretto tra $x$ e $z$.

**Implicazioni**:
- L'uscita **può cambiare immediatamente** al variare degli ingressi, anche nello stesso periodo di clock.
- La risposta è **anticipata** rispetto a Moore: l'uscita può riconoscere la sequenza nello stesso periodo in cui arriva l'ultimo simbolo.
![[8_reti_sequenziali_sincrone.pdf#page=23&rect=3,-1,682,447|8_reti_sequenziali_sincrone, p.23|700]]
> [!danger]
> Se gli ingressi non sono sincroni, **si possono avere più variazioni dell'uscita in un ciclo**; la rete mostra un comportamento asincrono sull'uscita.
> *Per questo motivo spesso si usa la sintesi di Moore*
### Sincronizzazione di ingressi asincroni
Il modello RSS assume ingressi **sincroni col clock** (cambiano subito dopo il fronte e una volta per ciclo). In pratica molti segnali sono **asincroni** (es. pulsanti, sensori).
> [!Condizione di sincronizzabilità]
la **frequenza del segnale asincrono deve essere minore della frequenza di clock.** Se il segnale varia più velocemente non è possibile sincronizzarlo; bisogna aumentare il clock.

![[8_reti_sequenziali_sincrone.pdf#page=25&rect=57,9,711,201|8_reti_sequenziali_sincrone, p.25|600]]
Il sincronizzatore più semplice **è un singolo FF-D** ma i suoi $t_{su}$ e $t_{h}$ sono una debolezza-
Per evitare il rischio di **metastabilità** si utilizza il **sincronizzatore a due flip-flop D**:
Il segnale asincrono $x$ viene campionato da un primo FF-D, la cui uscita $x_1$ viene poi campionata da un secondo FF-D producendo $x_{\text{sync}}$.
![[8_reti_sequenziali_sincrone.pdf#page=26&rect=16,13,683,283|8_reti_sequenziali_sincrone, p.26|600]]
- Se il campionamento del primo FF avviene in violazione dei tempi di setup/hold, il FF1 può entrare in **metastabilità**.
- Tuttavia, è molto probabile che la metastabilità si risolva entro un periodo di clock. Il secondo FF campiona $x_1$ quando è ormai stabile, garantendo che $x_{\text{sync}}$ non sia mai metastabile.
- L'incertezza sulla transizione di $x$ si traduce in un'incertezza sul **ritardo** con cui $x_{\text{sync}}$ commuta (1 o 2 cicli di clock dopo il fronte reale di $x$).
Si possono usare più di due FF in cascata per ridurre ulteriormente la probabilità di metastabilità alla rete a valle.
## Sintesi formale di una RSS
Procedura in 5 passi:
1.  **Comprensione delle specifiche e grafo degli stati**.
2.  **Tabella di flusso**.
3.  **Codifica degli stati** e tabella delle transizioni.
4.  **Sintesi della parte combinatoria** (funzioni $D_i$ per i FF e uscita $z$).
5.  **Schema logico**.

### 1 Grafo degli stati
![[8_reti_sequenziali_sincrone.pdf#page=31&rect=36,7,691,199|8_reti_sequenziali_sincrone, p.31|600]]
Simile a quello delle RSA, ma con differenze fondamentali:
- Si assume che gli ingressi siano **sincroni**: all'inizio del clock si ricevono i nuovi ingressi, che rimangono stabili per tutto il periodo.
- La permanenza su ogni arco (transizione) dura **un intero periodo di clock**.
- **Non esistono più stati stabili per configurazione d'ingresso**; **uno stato viene raggiunto e immediatamente al clock successivo può evolvere anche se gli ingressi non cambiano** (se la $G$ prevede un diverso stato futuro).
- Occorre considerare **tutte le configurazioni d'ingresso** per ogni stato (a meno che non siano impossibili per le specifiche).
- Non ci sono automaticamente indifferenze sulle uscite quando cambiano (a differenza delle RSA).

**Esempio**: cassaforte con sequenza da riconoscere `00-11-11-01` (per sfruttare la possibilità di ingressi ripetuti).

- **Moore**:![[8_reti_sequenziali_sincrone.pdf#page=32&rect=4,8,702,440|8_reti_sequenziali_sincrone, p.32]]
- **Mealy**:![[8_reti_sequenziali_sincrone.pdf#page=33&rect=2,13,718,442|8_reti_sequenziali_sincrone, p.33]]
### 2 Tabella di flusso
Riporta, per ogni stato presente e per ogni configurazione d'ingresso, lo **stato futuro** e l'**uscita** (eventualmente nella notazione di Mealy: $S^*,\,z$). Non devo fare **nessun controllo**.

Esempio per Mealy :
$$\begin{array}{c|cccc}
\text{Stato} & 00 & 01 & 11 & 10 \\
\hline
A & B,0 & A,0 & A,0 & A,0 \\
B & B,0 & A,0 & C,0 & A,0 \\
C & A,0 & A,0 & D,0 & A,0 \\
D & A,0 & A,1 & D,0 & A,0 \\
\end{array}$$

### 3 Codifica degli stati
La codifica è **arbitraria** (es. A=00, B=01, C=10, D=11). Non esistono corse critiche perché il campionamento filtra le transizioni multiple. Bit di stato spesso indicati con $Q_1 Q_0$ (uscite dei FF). Una codifica opportuna può minimizzare la complessità combinatoria.
### 4 Sintesi combinatoria
Dalla tabella delle transizioni codificata si ricavano le funzioni booleane per:
- gli **ingressi $D_i$ dei flip-flop** (stato futuro): $D_1^n = \text{funzione}(Q_1^n, Q_0^n, x_1^n, x_0^n)$, $D_0^n = \dots$
- l'**uscita** $z^n$ (per Mealy, anche funzione degli ingressi).
Non occorre preoccuparsi di alee, quindi si può eseguire la sintesi **a costo minimo** (mappe di Karnaugh, NAND, ecc.).

Esempio per la cassaforte Mealy:
$$
\begin{aligned}
D_1^n &= (Q_0 \, x_1 \, x_0)^n \\
D_0^n &= (x_1' x_0' + x_1 x_0 \, Q_1' Q_0')^n \\
z^n &= (Q_1 \, Q_0' \, x_1' \, x_0)^n
\end{aligned}
$$
### 5 Schema logico
![[8_reti_sequenziali_sincrone.pdf#page=37&rect=10,10,693,449|8_reti_sequenziali_sincrone, p.37|700]]

**Limiti della cassaforte sincrona**
Dallo schema logico finale **non è immediato capire quale sequenza sia riconosciuta**
Eventuali modifiche alla sequenza target richiedono un nuovo progetto. 
Per sequenze di ingressi asincroni con più bit, la semplice sincronizzazione con un FF per bit non garantisce la correttezza (problema della coerenza dei segnali multi-bit).
## Sintesi diretta di RSS
Si utilizzano **blocchi notevoli** già pronti – registri, shift register, contatori – e li si interconnette con opportuna logica combinatoria per ottenere il comportamento desiderato.

I componenti:
### Registro a k bit
Un **registro a k bit** è una rete sincrona che memorizza un dato di k bit. Possiede:
![[8_reti_sequenziali_sincrone.pdf#page=41&rect=233,13,488,138|8_reti_sequenziali_sincrone, p.41|200]]
- **Ingressi**: $IN[k-1..0]$ (dato da scrivere), $WE$ (write enable, sincrono).
- **Uscite**: $OUT[k-1..0]$ (dato memorizzato).
- **Reset asincrono**: $A\_RESET$ (o $A\_RESET'$, attivo basso), che azzera tutti i bit indipendentemente dal clock.  (A_SegnaleAsincrono != SegnaleSincrono)
Il comportamento **al fronte di salita del clock**:
- Se $WE = 1$, $OUT \leftarrow IN$.
- Se $WE = 0$, $OUT$ mantiene il valore precedente.
![[8_reti_sequenziali_sincrone.pdf#page=45&rect=20,2,709,219|8_reti_sequenziali_sincrone, p.45|600]]

**Comandi sincroni e priorità**
Spesso i registri offrono più comandi sincroni, con diversi livelli di priorità. Regola generale: **i comandi asincroni sono sempre prioritari su quelli sincroni**.
> [!important]
> Tra comandi **sincroni** bisogna specificare l'**ordine**.

Esempi con un registro dotato di $WE$ e di un segnale di reset sincrono $RESET$:
- **RESET prioritario su WE**: se al fronte di clock $RESET = 1$, l'uscita diventa $0$ indipendentemente da $WE$ e $IN$.
- **WE prioritario su RESET**: l'azzeramento sincrono avviene solo se $RESET = 1$ e contemporaneamente $WE = 1$, altrimenti il comando di reset viene ignorato.
![[8_reti_sequenziali_sincrone.pdf#page=47&rect=8,4,658,452|8_reti_sequenziali_sincrone, p.47|400]]

**Esempio 1: flip‑flop T (toggle)**
Specifiche: rete di Moore con un ingresso sincrono $T$. L'uscita $Q$ commuta (toggle) ad ogni ciclo in cui $T = 1$; con $T = 0$ mantiene il valore. All'accensione ($A\_RESET = 1$) la rete memorizza $1$.
![[8_reti_sequenziali_sincrone.pdf#page=49&rect=166,11,522,224|8_reti_sequenziali_sincrone, p.49|300]]
Questo circuito è noto come **flip‑flop T**. 

**Esempio 2**
Specifiche: ingresso sincrono $N[3..0]$ (livello come numero senza segno). L'uscita $Z$ deve andare a $1$ quando $N \ge 12$ e rimanere a $1$ finché $N \ge 8$ (isteresi). All'accensione $Z=0$.
Analisi: nell'intervallo $8 \le N < 12$ l'uscita dipende dalla storia passata: serve un bit di memoria. Si usa un registro a 1 bit.
Si definiscono due segnali combinatori:
- $NGTE12 = 1$ se $N \ge 12$, cioè $N_3 N_2$.
- $NLT8 = 1$ se $N < 8$, cioè $N_3'$ (poiché 8 è la potenza di due successiva, basta il bit più significativo).
Il registro deve essere scritto solo quando si esce dall'intervallo di isteresi o vi si entra: $WE = NGTE12 + NLT8$. Il dato da scrivere è $NGTE12$ (perché quando si attiva $NGTE12$ va scritto $1$, quando si attiva $NLT8$ va scritto $0$). L'uscita $Z$ coincide con lo stato del registro.
La logica combinatoria aggiuntiva calcola $NGTE12$ e $NLT8$, poi il tutto pilota il registro.
![[8_reti_sequenziali_sincrone.pdf#page=53&rect=98,137,577,362|8_reti_sequenziali_sincrone, p.53|400]]
### Shift register (registro a scorrimento)
 L'ingresso seriale $IN$ viene campionato e tutti i bit memorizzati scalano di una posizione ad ogni fronte di clock.
![[8_reti_sequenziali_sincrone.pdf#page=54&rect=176,16,570,118|8_reti_sequenziali_sincrone, p.54|300]]
- **Uscite**: $OUT[k-1..0]$ **(il bit $OUT[0]$ è il bit più significativo ritardato di un solo clock).**
- **Reset asincrono**: $A\_RESET$ azzera tutti i registri.

Le forme d'onda mostrano che l'andamento di $IN$ si ritrova sulle uscite con ritardi crescenti di un ciclo di clock ciascuna.

Uno **shift register a k bit** è una catena di $k$ flip‑flop D in serie.

![[8_reti_sequenziali_sincrone.pdf#page=57&rect=155,15,413,161|8_reti_sequenziali_sincrone, p.57|200]]
**Comandi sincroni**:
- **Enable (EN)**: lo scorri!mento avviene solo se $EN = 1$ altrimenti vengono conservati gli ultimi bit letti.
- **Load (LD)**: se $LD = 1$, il registro carica in parallelo un bus $I[k-1..0]$ indipendentemente da $IN$ e $EN$. Normalmente $LD$ è prioritario su $EN$.
- **Direzione (R/L')**: stabilisce se lo scorrimento è verso destra (verso il bit meno significativo) o verso sinistra.

**Universal shift register**: dotato di tutti i comandi, codificati su 2 bit ($S_1 S_0$):

| $S_1 S_0$ | Comando       | Funzione                              |
|-----------|---------------|---------------------------------------|
| 00        | Hold          | Mantiene il valore                    |
| 01        | Shift right   | $OUT[i] \leftarrow OUT[i-1]$ (i>0), $OUT[0] \leftarrow IN\_R$ |
| 10        | Shift left    | $OUT[i] \leftarrow OUT[i+1]$ (i<k-1), $OUT[k-1] \leftarrow IN\_L$ |
| 11        | Load          | $OUT[i] \leftarrow I[i]$              |

---

## 7. Comunicazione seriale e parallela

Un'applicazione classica degli shift register è la conversione tra formato seriale e parallelo.

- **Seriale → Parallelo (S/P)**:
  - **Moore**: l'ultimo bit di un dato di $k$ bit viene caricato nello shift register al clock $k$, e il dato completo è disponibile sulle uscite al clock $k+1$. (Es.: 3 bit, il dato appare un ciclo dopo la ricezione dell'ultimo bit).
  - **Mealy**: si può risparmiare un flip‑flop se l'ultimo bit viene mandato direttamente sull'uscita senza passare per il registro. Il dato è disponibile già nello stesso ciclo in cui arriva l'ultimo bit, ma l'uscita è parzialmente combinatoria.

- **Parallelo → Seriale (P/S)**: si usa uno shift register con comando di load. Al primo ciclo si carica il dato parallelo ($LD=1$), poi con $LD=0$ e $EN=1$ (o semplicemente facendo shift) i bit escono uno al ciclo dall'uscita seriale ($OUT[k-1]$ o $OUT[0]$ a seconda della connessione). Conoscendo la posizione del bit più significativo si può trasmettere correttamente.

---

## 8. Shift come moltiplicazione/divisione per 2

Uno shift register con $IN=0$ e controllo di direzione realizza moltiplicazioni/divisioni per $2$ di numeri senza segno.

- **Shift a sinistra** (verso il bit più significativo) $\Rightarrow$ moltiplicazione per $2$. Es.: $010100_2 = 20$ shift left $\rightarrow 101000_2 = 40$.
- **Shift a destra** $\Rightarrow$ divisione per $2$ (intera). Es.: $010100_2 \rightarrow 001010_2 = 10$.

Attenzione: bisogna stabilire quale bit è il più significativo all'interno del registro. L'overflow è segnalato dal bit che esce: se un $1$ esce dal registro durante uno shift a sinistra, si è persa una cifra significativa.

**Shift aritmetico** per numeri con segno in complemento a due:

- **Shift a destra (divisione)**: per preservare il segno, il bit che entra deve essere uguale al bit più significativo (estensione del segno). Es.: $111010_2 = -6$ shift right $ \rightarrow 111101_2 = -3$.
- **Shift a sinistra (moltiplicazione)**: come lo shift logico, entra $0$. L'overflow in complemento a 2 è segnalato da $Q_0 \neq Q_1$ (il bit di segno cambierebbe).

---

## 9. Riconoscitore di sequenze a 3 byte (soluzioni con shift register)

Specifiche: rete di Moore che riconosce la sequenza di byte (mentre $EN=1$): `FF – 27 – 30`. Dopo il riconoscimento, l'uscita $OUT$ va a $1$ e vi rimane fino a un $A\_RESET$. Dopo reset, la rete ricomincia da capo.

**Soluzione 1 (inefficiente)**:
Si usa uno shift register a 3 stadi da 8 bit ciascuno (totale 24 FF). Ad ogni clock si carica $IN$ se $EN=1$ e non è già stato riconosciuto ($OUT=0$). L'uscita si attiva quando i tre byte memorizzati corrispondono esattamente a FF, 27, 30. La condizione di mantenimento di $OUT$ si ottiene disabilitando lo scorrimento ($EN\_SHIFT = EN \cdot OUT'$) una volta riconosciuta la sequenza, cosicché $OUT$ rimane $1$ fino al reset.

**Soluzione 2 (più efficiente)**:
Invece di memorizzare gli interi byte, si ricorda solo se in ciascuno degli ultimi 3 colpi di clock validi è stato visto il simbolo corretto atteso in quell'istante. Si utilizzano:
- Uno shift register a 3 bit per l'attesa di FF: se 3 cicli fa c'era FF, questo bit è 1.
- Uno shift register a 2 bit per l'attesa di 27: se 2 cicli fa c'era 27 e il precedente era FF…
- Un flip‑flop per l'attesa di 30 al colpo più recente.
I bit vengono aggiornati solo quando $EN=1$ e $OUT=0$. Alla fine si fa l'AND di tutti e tre i bit. Solo 6 FF in totale, indipendentemente dal parallelismo del dato.

Le funzioni combinatorie $DECFF$, $DEC27$, $DEC30$ riconoscono i byte corrispondenti. Lo schema risultante è mostrato nel PDF.

---

## 10. Monoimpulsore (edge detector)

Un **monoimpulsore** (o edge detector) genera un impulso della durata esatta di un periodo di clock quando l'ingresso (anche asincrono) compie una transizione da 0 a 1. Serve anche a sincronizzare il segnale asincrono.

Vengono proposte diverse realizzazioni:

- **Monoimpulsore 1**: un FF‑D e un AND. L'ingresso asincrono arriva direttamente al FF e all'AND insieme all'uscita negata del FF ritardata di un clock. Produce un impulso di un clock se non c'è metastabilità. In caso di metastabilità, l'uscita può rimanere alta per meno di un clock o per più di un clock (con una coda), a seconda di come la metastabilità viene interpretata. Questo può essere accettabile se l'uscita alimenta una rete di Moore che sincronizza ulteriormente.

- **Monoimpulsore 2**: utilizza due FF‑D in cascata (sincronizzatore) e un AND tra l'uscita del secondo FF e la negata del primo. Così facendo, anche in presenza di metastabilità, l'uscita è garantita durare esattamente un periodo di clock (anche se con un possibile ritardo aggiuntivo di un ciclo). Il prezzo è un ritardo fisso di un clock anche in condizioni normali.

- **Versioni errate**: si mostrano circuiti che non producono un singolo impulso, ad esempio quando l'ingresso rimane a 1 per più cicli, l'uscita può commutare a ogni clock.

---

## 11. Contatori

Un **contatore** è una rete sincrona senza ingressi (nella forma più semplice) che percorre ciclicamente tutti gli stati interni. Le uscite coincidono con lo stato interno.

**Contatore binario modulo N**: gli stati sono i primi N numeri binari.

### 11.1 Contatore binario x4 (modulo 4)

Ha 2 bit di stato $Q_1 Q_0$ che evolvono secondo la sequenza: $00 \to 01 \to 10 \to 11 \to 00 \dots$

Si può realizzare in modo inefficiente con MUX e adder oppure sfruttando le regolarità:

- Il bit meno significativo $Q_0$ commuta ad ogni clock: $Q_0^{n+1} = Q_0'$. Basta quindi un FF‑D con ingresso $D_0 = Q_0'$.
- Il bit $Q_1$ commuta solo quando $Q_0 = 1$. Vale la relazione $Q_1^{n+1} = Q_1 \oplus Q_0$. Inoltre, $Q_1 \oplus Q_0$ si può ottenere con una porta EXOR, oppure usando un AND e un EXOR come si vedrà con i comandi sincroni.

La realizzazione con **adder** non è la più efficiente ma evidenzia il legame: incrementare significa sommare 1. Sommando 1 a $Q_1 Q_0$, il bit $S_0 = Q_0 \oplus 1 = Q_0'$, $S_1 = Q_1 \oplus Q_0$, $R_1 = Q_1 Q_0$ (riporto). Le due realizzazioni (con EXOR o con sommatore) sono logicamente equivalenti.

### 11.2 Comandi sincroni: ENABLE

Il comando $EN$ abilita il conteggio: quando $EN = 1$, il contatore incrementa; quando $EN = 0$, mantiene lo stato.

Per il contatore x4 con EN si può usare un MUX per ogni FF che seleziona tra il valore attuale e quello successivo. Questa struttura può essere ottimizzata:

- Per $Q_0$: il MUX equivale a un EXOR tra $EN$ e $Q_0$: $D_0 = EN \oplus Q_0$ (perché se $EN=0$, $D_0 = Q_0$; se $EN=1$, $D_0 = Q_0'$).
- Per $Q_1$: $D_1 = Q_1 \oplus (EN \cdot Q_0)$. La porta AND combina $EN$ e $Q_0$ per abilitare la commutazione solo quando entrambi sono 1.

Questi schemi si generalizzano per contatori più grandi: ogni bit $Q_i$ deve commutare quando tutti i bit di peso inferiore valgono 1 (in conteggio avanti).

### 11.3 Comandi sincroni: RESET e LOAD

- **RESET sincrono**: riporta il contatore a 0 al successivo fronte di clock. Normalmente prioritario su EN. Si realizza con MUX o forzando gli ingressi D.
- **LOAD sincrono**: carica un valore esterno $I[k-1..0]$. Nell'esempio x4 con LD e EN, due MUX in cascata gestiscono le priorità.

### 11.4 Comandi UP/DOWN (avanti/indietro)

Un contatore bidirezionale aggiunge un ingresso $U/D'$: $1$ = conteggio crescente, $0$ = decrescente.

Osservazioni per il contatore x4 con EN e U/D':

- $Q_0$ commuta sempre (sia avanti che indietro), quindi $D_0 = EN \oplus Q_0$ rimane invariato.
- $Q_1$ commuta quando $Q_0$ passa da $0$ a $1$ (conteggio avanti) o da $1$ a $0$ (conteggio indietro). Pertanto la condizione per il toggle di $Q_1$ diventa $EN \cdot Q_0$ se $U/D'=1$, oppure $EN \cdot Q_0'$ se $U/D'=0$. Serve quindi un MUX che selezioni tra $Q_0$ e $Q_0'$ in base a $U/D'$, e poi una AND con $EN$, il tutto in OR con l'uscita dell'EXOR come prima. Lo schema finale è mostrato nel PDF.

### 11.5 Contatore binario x8 (modulo 8)

La regola generale: $Q_i$ commuta quando tutti i bit $Q_{i-1} \dots Q_0$ sono a $1$. Per $i=2$, $Q_2$ commuta quando $Q_1 Q_0 = 11$, cioè quando $Q_1 \cdot Q_0 = 1$. Quindi $D_2 = Q_2 \oplus (Q_1 \cdot Q_0)$.

Aggiungendo un EN, si ha $D_2 = Q_2 \oplus (EN \cdot Q_1 \cdot Q_0)$. Realizzabile con una AND a 3 ingressi e un EXOR.

### 11.6 Incremento della base di conteggio (cascata di contatori)

Per realizzare un contatore modulo $M$ grande si possono collegare in cascata più contatori con **carry out (CO)**. Il CO del contatore meno significativo segnala quando tutti i suoi bit sono $1$ (conteggio avanti) o $0$ (indietro). Collegando CO all'EN del contatore successivo, questo incrementa solo quando il primo ha terminato un ciclo.

Per esempio, un contatore x32 si ottiene da un x8 (3 bit) e un x4 (2 bit) in cascata. L'EN complessivo del contatore composto va collegato all'EN dello stadio meno significativo e contemporaneamente condizionare il CO diretto allo stadio successivo in AND con EN, per garantire che il tutto conti solo quando EN=1.

### 11.7 Decremento della base di conteggio (modulo M < N)

Dato un contatore modulo N (es. x16) e volendo un contatore modulo M (es. x10), si può resettare il contatore quando raggiunge lo stato M (o meglio M-1, per tornare a 0). Si usa l'ingresso di RESET sincrono.

- **Soluzione semplice**: realizzare il mintermine corrispondente a $M-1$ e mandarlo al RESET (tenendo conto anche di EN). Per x10 a partire da x16, $M-1 = 9 = 1001_2$, quindi $RES = Q_3 Q_0$ (trascurando gli stati più alti mai raggiunti) con in AND anche $EN$.
- **Ottimizzazione**: poiché il contatore non raggiungerà mai stati superiori a $M-1$, non è necessario l'intero mintermine; basta l'AND dei bit che sono 1 nella rappresentazione binaria di $M-1$. Per $9_{10}=1001_2$, occorrono solo $Q_3$ e $Q_0$, perché 9 è il più piccolo numero con entrambi quei bit a 1.

### 11.8 Divisore di frequenza

Ogni uscita $Q_i$ di un contatore binario con $EN=1$ fisso produce un'onda quadra di frequenza $f/2^{i+1}$. Ad esempio, con un clock a $1024\,\text{Hz}$, l'uscita $Q_9$ (contatore x1024) ha frequenza $1\,\text{Hz}$. Utile per generare basi di tempo.

---

## 12. Esercizio: Timer

Specifiche: timer pilotato da clock a 8 Hz. Ingresso $START$ (sincrono), bus $TIME[?..0]$ (numero senza segno di cicli da attendere). Dal clock successivo a $START=1$, l'uscita $ON$ resta alta per un numero di cicli pari al valore su $TIME$ in quel momento. Durata massima 10 secondi.

Analisi:
- 10 secondi a 8 Hz $\rightarrow$ $10 \times 8 = 80$ cicli. Per rappresentare 80 occorrono 7 bit ($2^7=128$). Quindi $TIME[6..0]$.
- Si può usare un contatore con load (LD) e conteggio all'indietro.
  1. Stato di attesa: contatore fermo a 0, $ON = 0$.
  2. Quando $START=1$, al fronte successivo si carica il valore di $TIME$ (comando LD) e si abilita il conteggio all'indietro ($U/D'=0$).
  3. Si attiva $ON = 1$ ogni volta che il contatore $\neq 0$ (cioè l'OR di tutti i bit $Q_i$). Quando il contatore torna a 0, $ON$ si spegne.
- Il contatore necessario è x128, realizzabile con un x8 e due x4 in cascata (es. $8 \times 4 \times 4 = 128$). Si aggiungono i circuiti per LD, U/D'=0 e l'OR di uscita.

---

## 13. Riconoscitore di sequenze – Soluzione 3 (con contatore)

Riprendendo il riconoscitore a 3 byte (FF-27-30), la soluzione più efficiente in termini di flip‑flop usa un **contatore x4** (2 FF) invece di shift register.

Idea: il contatore tiene traccia di quanti simboli corretti consecutivi sono stati visti.

- Stato 0: nessun simbolo corretto, attesa di `FF`.
- Stato 1: visto `FF`, attesa di `27`.
- Stato 2: visti `FF` e `27`, attesa di `30`.
- Stato 3: sequenza completata, $OUT=1$ e rimane tale fino al reset.

Funzionamento:
- Se il contatore è nello stato `00` (attesa `FF`) e arriva `FF`, si dà un enable (o un incremento) per passare allo stato `01`.
- Se è in `01` e arriva `27`, si passa a `10`.
- Se è in `10` e arriva `30`, si passa a `11`.
- In qualsiasi altro caso (simbolo errato), si forza il contatore a `00` (reset sincrono o load di 0).
- Raggiunto lo stato `11`, il contatore non deve più cambiare fino a reset: l'ENABLE viene disabilitato via $OUT'$.

Il circuito richiede:
- Logica combinatoria per riconoscere i tre simboli ($DECFF$, $DEC27$, $DEC30$).
- Un contatore x4 con EN e LOAD sincrono, oppure EN e RESET sincrono (prioritario), a seconda della modalità di ritorno a 0.
- Decodifica dello stato per sapere quale simbolo attendere.
- Un segnale $LOAD = (ATTESA30 \cdot DEC30 + \dots)$ per gestire il ritorno a 0 in caso di simbolo errato.

**Problema della soluzione iniziale**: se dopo $FF-27$ arriva di nuovo $FF$, la decodifica di `ATTESA30` è `1` (perché lo stato corrente è `10`), e $DECFF=1$ attiva il LOAD di `00`, cancellando i progressi. Invece in tal caso la sequenza corretta potrebbe essere $FF-27-FF-27-30$, dove dopo il secondo $FF$ la rete dovrebbe rimanere in attesa di $27$ (stato `01`) non resettarsi.

**Soluzione modificata**: si raffinano le condizioni di LOAD. Invece di resettare a zero per ogni simbolo errato, si carica lo stato appropriato:
- Dallo stato `01` (attesa 27), se arriva `FF` (cioè di nuovo il primo simbolo), si carica `01` (perché l'ultimo `FF` può essere l'inizio di una nuova sequenza).
- Nello stato `10` (attesa 30), se arriva `FF`, si carica `01` (come se si fosse ripartiti da `FF`).
- Nello stato `10`, se arriva `27`, si carica `00` (due `27` di fila rompono la sequenza).
E così via. La logica di LOAD diventa:

\[
LOAD = \text{ATTESA30} \cdot (EN \cdot DECFF) + \text{ATTESA27} \cdot (EN \cdot \overline{DECFF} \cdot \overline{DEC27}) + \dots
\]

e i dati caricati sono codificati opportunamente. Il PDF mostra il circuito dettagliato.

---

## 14. Esercizio: riconoscitore non consecutivo

Variante: la sequenza FF, 27, 30 può essere riconosciuta anche se intervallata da altri simboli, purché compaia nello stesso ordine. Es.: `FF-7A-E0-9F-27-B2-30`. In questo caso il contatore deve incrementare solo quando viene visto il simbolo atteso, ma **non** deve resettare a 0 se il simbolo è sbagliato: deve rimanere nello stato corrente. Basta quindi rimuovere il reset/load forzato a 0, e semplicemente dare EN=1 solo quando il simbolo in ingresso corrisponde a quello atteso nello stato attuale. Se il simbolo non corrisponde, il contatore mantiene lo stato. L'uscita si attiva al raggiungimento dello stato 3.

---

## 15. Clock gating e clock skew

Il **clock gating** consiste nell'usare una porta AND (o altro) per fermare il clock a certi flip‑flop, ad esempio per non aggiornare un registro quando non serve. Tuttavia introduce problemi:

1. **Alee (glitch)**: Se il segnale di abilitazione non è perfettamente sincrono, la porta AND può generare fronti di clock spuri dovuti a transizioni multiple della logica combinatoria. Possono campionare dati errati.
2. **Clock skew**: Il clock "gated" arriva ai FF con un ritardo (introdotto dalla porta AND) rispetto al clock originale. Questo sfasamento può far sì che circuiti a valle campionino valori già aggiornati invece di quelli precedenti, rompendo la temporizzazione sincrona. Ad esempio, un contatore collegato a un registro con clock gated: se lo skew è elevato, il registro potrebbe vedere l'uscita già incrementata del contatore anziché il valore stabile precedente.

**Regola**: non usare porte combinatorie per generare il clock dei flip‑flop. Invece, si utilizza sempre un segnale di enable (WE, EN) collegato all'ingresso D di un MUX o a una logica che decide se il FF deve aggiornarsi. In questo modo il clock rimane pulito e non ci sono problemi di glitch o skew.

---

## 16. Schema di sintesi diretta

La progettazione diretta con reti notevoli segue un flusso tipico:
1. Analizzare le specifiche e individuare gli elementi di memoria necessari (contatore, registro, shift register…).
2. Scegliere i componenti e i loro segnali di controllo (EN, LD, RES, U/D…).
3. Progettare la logica combinatoria di supporto (decodifiche, condizioni di enable, calcolo dei dati da caricare).
4. Verificare le temporizzazioni e la correttezza con simulazioni.

Questo metodo è molto usato nella progettazione digitale perché sfrutta blocchi già ottimizzati e riduce gli errori.