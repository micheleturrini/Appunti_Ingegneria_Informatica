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

$$T_0 \;\ge\; \max(t_r, t_i) \;+\; t_{pd,G} \;+\; t_{su}$$

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
![[8_reti_sequenziali_sincrone.pdf#page=19&rect=74,2,650,405|8_reti_sequenziali_sincrone, p.19|700]]
#### Modello di Moore
L'uscita $F$ dipende **solo dallo stato presente** $y$: $z = F(y)$. Non c'è percorso combinatorio diretto tra ingressi e uscite.
**Implicazioni**:
- L'uscita varia **in sincrono col clock**, con un periodo di clock di **ritardo** rispetto alla variazione degli ingressi.
- Gli ingressi possono variare anche in modo asincrono (purché rispettino setup/hold): l'uscita rimane stabile durante il periodo.
Principale limite: **latenza** maggiore **(risposta potenzialmente ritardata di un colpo di clock).**
![[8_reti_sequenziali_sincrone.pdf#page=21&rect=3,6,716,437|8_reti_sequenziali_sincrone, p.21|700]]
### Modello di Mealy
L'uscita $F$ dipende sia dallo stato presente $y$ **che dagli ingressi** $x$: $z = F(y, x)$. Esiste un percorso combinatorio diretto tra $x$ e $z$.

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
**Sincronizzatore a due flip-flop D**:

- Il segnale asincrono $x$ viene campionato da un primo FF-D, la cui uscita $x_1$ viene poi campionata da un secondo FF-D producendo $x_{\text{sync}}$.
- Se il campionamento del primo FF avviene in violazione dei tempi di setup/hold, il FF1 può entrare in **metastabilità**.
- Tuttavia, è molto probabile che la metastabilità si risolva entro un periodo di clock. Il secondo FF campiona $x_1$ quando è ormai stabile, garantendo che $x_{\text{sync}}$ non sia mai metastabile.
- L'incertezza sulla transizione di $x$ si traduce in un'incertezza sul **ritardo** con cui $x_{\text{sync}}$ commuta (1 o 2 cicli di clock dopo il fronte reale di $x$).

Si possono usare più di due FF in cascata per ridurre ulteriormente la probabilità di metastabilità alla rete a valle.

## 9. Sintesi formale di una RSS

Procedura in 5 passi:

1.  **Comprensione delle specifiche e grafo degli stati**.
2.  **Tabella di flusso**.
3.  **Codifica degli stati** e tabella delle transizioni.
4.  **Sintesi della parte combinatoria** (funzioni $D_i$ per i FF e uscita $z$).
5.  **Schema logico**.

---

### 9.1 Grafo degli stati

Simile a quello delle RSA, ma con differenze fondamentali:

- Si assume che gli ingressi siano **sincroni**: all'inizio del clock si ricevono i nuovi ingressi, che rimangono stabili per tutto il periodo.
- La permanenza su ogni arco (transizione) dura **un intero periodo di clock**.
- **Non esistono più stati stabili per configurazione d'ingresso**; uno stato viene raggiunto e immediatamente al clock successivo può evolvere anche se gli ingressi non cambiano (se la $G$ prevede un diverso stato futuro).
- Occorre considerare **tutte le configurazioni d'ingresso** per ogni stato (a meno che non siano impossibili per le specifiche).
- Non ci sono automaticamente indifferenze sulle uscite quando cambiano (a differenza delle RSA).

**Esempio**: cassaforte con sequenza da riconoscere `00-11-11-01` (per sfruttare la possibilità di ingressi ripetuti).

- **Moore**:
  Stati: A (reset), B (visto `00`), C (visto `00-11`), D (visto `00-11-11`), E (sequenza riconosciuta).
  L'uscita si attiva nello stato E, quindi al periodo di clock successivo all'ultimo ingresso `01`.
- **Mealy**:
  Stati: A, B, C, D.
  L'uscita $z=1$ viene attivata sull'arco da D quando arriva `01`; quindi nello **stesso** periodo di clock dell'ultimo simbolo.

---

### 9.2 Tabella di flusso

Riporta, per ogni stato presente e per ogni configurazione d'ingresso, lo **stato futuro** e l'**uscita** (eventualmente nella notazione di Mealy: $S^*,\,z$).

Esempio per Mealy (solo righe significative):

\[
\begin{array}{c|cccc}
\text{Stato} & 00 & 01 & 11 & 10 \\
\hline
A & B,0 & A,0 & A,0 & A,0 \\
B & B,0 & A,0 & C,0 & A,0 \\
C & A,0 & A,0 & D,0 & A,0 \\
D & A,0 & A,1 & D,0 & A,0 \\
\end{array}
\]

---

### 9.3 Codifica degli stati

La codifica è **arbitraria** (es. A=00, B=01, C=10, D=11). Non esistono corse critiche perché il campionamento filtra le transizioni multiple. Bit di stato spesso indicati con $Q_1 Q_0$ (uscite dei FF). Una codifica opportuna può minimizzare la complessità combinatoria, ma non è oggetto di questa trattazione.

---

### 9.4 Sintesi combinatoria

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

---

### 9.5 Schema logico

Si disegnano i flip-flop D con i segnali $D_i$ e il clock comune. La rete combinatoria per $D_i$ e $z$ è realizzata con porte logiche.

I segnali asincroni in ingresso devono essere **sincronizzati** con un sincronizzatore a 2 FF-D. Attenzione: la sincronizzazione singola non basta quando **due o più segnali asincroni codificano un'informazione multi-bit**, perché il ritardo relativo tra di essi potrebbe far campionare valori transitori incoerenti (non appartenenti alla codifica). Per questi casi servono tecniche aggiuntive (es. codifica Gray e sincronizzatori con segnali di handshake).

---

## 10. Limiti della cassaforte sincrona

Dallo schema logico finale **non è immediato capire quale sequenza sia riconosciuta**; la rete diventa una "scatola nera". Eventuali modifiche alla sequenza target richiedono un nuovo progetto. Inoltre, per sequenze di ingressi asincroni con più bit, la semplice sincronizzazione con un FF per bit non garantisce la correttezza (problema della coerenza dei segnali multi-bit).