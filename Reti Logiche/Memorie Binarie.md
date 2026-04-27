- Le **memorie binarie** sono circuiti che memorizzano il valore di un singolo bit.
- Sono componenti primitivi fondamentali per realizzare reti sincrone e circuiti a livello architetturale (es. RAM, registri).
- Tre tipi principali di memoria binaria trattati:
    - **Latch SR**
    - **Latch CD** (o D‑latch)
    - **Flip‑flop D**
- Differiscono per *quando* e *come* viene comandata la scrittura del bit, cioè per la sensibilità ai comandi di modifica.
![[7_rsa_per_memorie binarie.pdf#page=2&rect=65,17,695,144|7_rsa_per_memorie binarie, p.2|500]]
### Il Latch SR
![[7_rsa_per_memorie binarie.pdf#page=3&rect=212,355,508,475|7_rsa_per_memorie binarie, p.3|200]]
- Ha **due ingressi**: **S** (Set) e **R** (Reset).
- Ha **due uscite**: **Q** (valore memorizzato) e **Q'** (complemento).
- Funzionamento definito dalla tabella:
    - S=0, R=0 → *Memorizza*: Q mantiene l’ultimo valore scritto.
    - S=1, R=0 → *Scrivi “1”*: Q=1.
    - S=0, R=1 → *Scrivi “0”*: Q=0.
    - S=1, R=1 → **Configurazione vietata** (le uscite non sono più complementari e lo stato diventa imprevedibile).
#### Sintesi come RSA
![[7_rsa_per_memorie binarie.pdf#page=4&rect=17,11,685,462|7_rsa_per_memorie binarie, p.4|600]]

Sintesi a NOR
![[7_rsa_per_memorie binarie.pdf#page=5&rect=301,-1,479,206|7_rsa_per_memorie binarie, p.5|200]]
Se faccio lo stesso con i NAND ottengo un latch S'R' (Per la logica negativa)

**Uscite complementari**
- Nello schema a **NAND o NOR**, l’uscita X (complementare) risulta essere effettivamente Q’ senza bisogno di un NOT aggiuntivo, **a patto che non si applichi la configurazione vietata** S=1,R=1.
- Nella condizione proibita entrambe le uscite vanno a 1, perdendo la complementarità. Per questo motivo tale configurazione è da evitare.

**Stato iniziale e comandi di inizializzazione**
All’accensione, il valore memorizzato (stato) è **casuale**, a meno che non si usi un segnale di reset.
Esistono varianti dotate di due ingressi aggiuntivi, normalmente attivi bassi, **prioritari** rispetto a S e R:
![[7_rsa_per_memorie binarie.pdf#page=9&rect=34,1,714,226|7_rsa_per_memorie binarie, p.9|500]]
- **PRE'** (preset): quando **PRE'=0 il latch memorizza “1”.**
- **CLR'** (clear): quando **CLR'=0 il latch memorizza “0”.**
PRE' e CLR' **non possono mai essere attivi contemporaneamente**.

Le funzioni logiche di costo minimo sono:
- S₂ = PRE + CLR'·S  (per una versione con latch a NOR)
- R₂ = CLR + PRE'·R
- Per un latch a NAND (ingressi attivi bassi) si usano le espressioni complementate:
    - S₂' = PRE'·(CLR + S')
    - R₂' = CLR'·(PRE + R')
- Grazie a proprietà associative degli OR, le porte OR aggiuntive possono essere assorbite nella struttura stessa del latch (ad esempio nei NOR che già presentano una negazione in uscita).
![[7_rsa_per_memorie binarie.pdf#page=12&rect=25,63,701,419|7_rsa_per_memorie binarie, p.12|500]]
#### 2.5 Tempo minimo di scrittura e metastabilità
- Per scrivere correttamente un “1” o uno “0”, gli ingressi S ed R devono rimanere attivi per una durata minima **tₕ** (pulse duration).
- Tale durata minima è circa 2τₚ (due volte il ritardo di propagazione di una porta).
- Se gli ingressi sono attivi per meno di tₕ, il latch può entrare in **metastabilità**.

##### Approfondimento sulla metastabilità:
- In uno stato stabile, l’anello di retroazione mantiene Q = q (stato futuro = stato presente).
- I segnali interni sono in realtà **analogici** (possono assumere qualsiasi valore tra L e H).
- La relazione ingresso‑uscita della rete combinatoria retroazionata presenta tratti con pendenza **< 1** intorno ai valori nominali H e L (stabilità: un piccolo disturbo viene attenuato, il segnale torna al valore nominale).
- Per unire questi tratti deve esistere una zona con pendenza **> 1** che incrocia la retta Q = q in un terzo punto: lo **stato metastabile M**.
- Analogia meccanica: due valli (H e L) separate da una collina (M). Una massa sulla cima della collina può restare ferma, ma un minimo disturbo la farà cadere in una valle in modo **casuale**.
- Se il latch finisce in metastabilità a causa di un comando troppo breve, l’uscita potrà assestarsi su 0 o 1 in modo imprevedibile, annullando la scrittura intesa.

---

### 3. Il Latch CD (o D‑Latch)

#### 3.1 Comportamento e comando

- Il latch CD è una memoria binaria che memorizza un bit e lo rende disponibile in forma vera e negata.
- Ha due ingressi: **C** (enable, comando) e **D** (dato).
- Funzionamento:
    - C = 0 → *Memorizza*: Q mantiene l’ultimo valore scritto.
    - C = 1 → *Scrivi il valore di D*: Q = D (la memoria si dice **trasparente** perché le variazioni di D si ripercuotono su Q con un piccolo ritardo).
- Vantaggio: non si dice *cosa* scrivere, ma *quando* campionare D. Pertanto, pilotando opportunamente C, si può comandare la scrittura simultanea di molti latch con un unico segnale di enable.

#### 3.2 Sintesi del latch CD

- **Sintesi a partire dal latch SR**: è sufficiente una rete combinatoria che traduca i comandi (C,D) in segnali S ed R per il latch SR.
    - Tabella di trascodifica:
        - Memorizza (C=0) → S=0, R=0.
        - Scrivi “1” (C=1, D=1) → S=1, R=0.
        - Scrivi “0” (C=1, D=0) → S=0, R=1.
    - Rete combinatoria: S = C·D, R = C·D'.
    - Il latch CD risulta quindi composto da una rete di campionamento (due AND) seguita da un latch SR.
- **Sintesi diretta** (senza inizializzazione):
    - Utilizzando un latch S̅R̅ (a NAND) e negando i segnali prodotti dalla rete di campionamento, si ottiene una struttura con 4 NAND e 1 NOT.
    - Poiché quando C=1 il NAND superiore produce D', si può usare questa uscita al posto del NOT, eliminando così la porta NOT: schema finale con **4 NAND** (due per il campionamento, due per la retroazione).
- Anche il latch CD può essere dotato di ingressi di inizializzazione PRE' e CLR' impiegando il latch SR con init.

#### 3.3 Tempi caratteristici (set‑up, hold, risposta)

- **tₕ** (o t_hold per il latch): durata minima del comando C=1 per evitare metastabilità.
- **tₛᵤ** (set‑up time): tempo minimo durante il quale D deve essere stabile **prima** della discesa di C (fronte di chiusura del campionamento).
- **tₕ** (hold time): tempo minimo durante il quale D deve rimanere stabile **dopo** la discesa di C.
- D deve essere costante per tutto l’intervallo tₛᵤ + tₕ attorno al fronte di discesa di C. Ciò evita cambiamenti simultanei degli ingressi, proibiti nelle RSA.
- **tₚ** (response time): ritardo con cui Q riflette una variazione di D (quando C=1). È tipicamente diverso per transizioni LH e HL.

#### 3.4 Uscita trasparente e retroazioni pericolose

- Quando C=1, Q è **trasparente** (segue D con un piccolo ritardo). Ne consegue che una connessione in retroazione diretta (es. D = Q') crea un anello combinatorio.
- In tali condizioni la rete può diventare instabile: se D dipende da Q (ad esempio D = NOT Q), quando C=1 la rete oscillerà senza mai raggiungere uno stato stabile, violando i vincoli di setup e hold. Allo spegnimento di C lo stato memorizzato sarà casuale.
- **Il semplice latch CD non garantisce stabilità in configurazioni con retroazione diretta**; per questo si usa il flip‑flop D.

---

### 4. Il Flip‑Flop D (“Edge‑Triggered”)

#### 4.1 Caratteristiche generali

- Il flip‑flop D è un elemento di memoria **positive edge‑triggered**, cioè sensibile ai fronti di salita del segnale di clock (CLK).
- Campiona il valore di D in corrispondenza della transizione 0→1 di CLK.
- Dopo un breve tempo di propagazione, l’uscita Q assume il valore campionato e **non segue più D** fino al prossimo fronte di salita. In altre parole, le uscite **non sono trasparenti**.
- Questa caratteristica lo rende **robusto in qualunque montaggio in retroazione**, perché le uscite cambiano solo in istanti ben determinati (i fronti del clock) e mai durante la fase di campionamento.

#### 4.2 Tempi caratteristici (analoghi al latch CD)

- **Tempo di set‑up (tₛᵤ)** : D stabile prima del fronte di salita di CLK.
- **Tempo di hold (tₕ)** : D stabile dopo il fronte di salita.
- **Tempo di risposta (tₚ)** : massima durata del transitorio su Q e Q' dopo il fronte attivo.
- Come per il latch CD, D deve rimanere costante per tutto tₛᵤ + tₕ.

#### 4.3 Realizzazioni

##### a) Flip‑Flop D Master‑Slave
- Due latch CD in cascata: il **Master** (collegato a D) controllato da C, lo **Slave** controllato da C' (C negato).
- Quando C=0, il Master è trasparente (segue D), lo Slave memorizza il valore precedente di QM.
- Quando C va a 1 (fronte di salita), il Master memorizza l’ultimo valore di D e lo Slave diventa trasparente, riportando in uscita Q il valore appena memorizzato dal Master.
- L’uscita commuta solo sul fronte di salita e successivamente non risente più delle variazioni di D.
- **Problema di clock gating**: il NOT su C introduce un ritardo che può causare una sovrapposizione di trasparenza tra Master e Slave durante la commutazione. In quel caso D potrebbe passare direttamente all’uscita (trasparenza indesiderata).
- Soluzione: utilizzare un **clock a due fasi** (Φ₁, Φ₂ non sovrapposte) che garantisca sempre un breve intervallo con entrambi i latch in memorizzazione, eliminando il NOT.

##### b) Flip‑Flop D “Edge‑Triggered” standard (6 NAND)
- Sintesi ottimizzata ottenuta tramite CAD: utilizza solo **6 porte NAND** e non presenta clock gating (D entra in un solo NAND, fan‑out unitario).
- La rete ha **tre variabili di stato** (anziché le due di un Master‑Slave) e implementa comunque il comportamento edge‑triggered.
- Dispone di ingressi asincroni **PRE'** e **CLR'**, prioritari rispetto a CLK e D, che forzano rispettivamente Q=1 e Q=0 in modo asincrono.

#### 4.4 Uso in retroazione e come elemento di ritardo

- Grazie all’assenza di trasparenza, un FF‑D può essere collegato in anello senza rischi di instabilità. Esempio: collegando Q' all’ingresso D si realizza un **Toggle Flip‑Flop** (ad ogni fronte di salita CLK, Q commuta).
- In presenza di un segnale di clock periodico di periodo T, il FF‑D si comporta come un **elemento di ritardo**: Q(t) = D(t−T). Questa proprietà è alla base delle reti sincrone, dove il clock suddivide il tempo in intervalli discreti.

---

### 5. Riepilogo dei concetti chiave

- **Tre memorie fondamentali**: Latch SR, Latch CD, Flip‑Flop D.
- **Latch SR**: due ingressi di comando (S,R), configurazione 11 vietata, semplice, richiede attenzione ai tempi di pilotaggio.
- **Latch CD**: un ingresso di abilitazione (C) e uno di dato (D), trasparente quando C=1, utile per campionare dati. Esposto a rischi di instabilità in caso di retroazione diretta.
- **Flip‑Flop D**: campiona sul fronte di salita del clock, uscite non trasparenti, elemento fondamentale per reti sincrone robuste.
- **Metastabilità**: fenomeno in cui una memoria binaria può rimanere in uno stato intermedio instabile se i comandi sono troppo brevi; l’esito della scrittura diventa casuale.
- **Vincoli temporali**: ogni memoria ha tempi minimi di set‑up, hold e durata del comando per garantirne il funzionamento corretto.
- **Inizializzazione**: ingressi asincroni di preset e clear permettono di forzare uno stato noto all’avvio del sistema.