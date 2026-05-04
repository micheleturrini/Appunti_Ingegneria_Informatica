[[0 - Index]]
- Le **memorie binarie** sono circuiti che memorizzano il valore di un singolo bit.
- Sono componenti primitivi fondamentali per realizzare reti sincrone e circuiti a livello architetturale (es. RAM, registri).
- Tre tipi principali di memoria binaria trattati:
    - **Latch SR**
    - **Latch CD** (o D‑latch)
    - **Flip‑flop D**
- Differiscono per *quando* e *come* viene comandata la scrittura del bit, cioè per la sensibilità ai comandi di modifica.
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
#### Tempo minimo di scrittura e metastabilità
Per scrivere correttamente un “1” o uno “0”, gli ingressi S ed R devono rimanere attivi per una **durata minima $t_{w}$ (pulse duration).**
Tale durata minima è circa 2τₚ (due volte il ritardo di propagazione di una porta).
Se gli ingressi sono attivi per meno di $t_{w}$, il latch può entrare in **metastabilità**.

**metastabilità**
In uno stato stabile, l’anello di retroazione mantiene Q = q (stato futuro = stato presente).
I segnali interni sono in realtà **analogici** (possono assumere qualsiasi valore tra L e H).
![[7_rsa_per_memorie binarie.pdf#page=16&rect=414,0,715,338|7_rsa_per_memorie binarie, p.16|200]]
**il circuito deve rimanere stabile anche a fronte di disturbi sui segnali analogici sottostanti** (inevitabilmente presenti): 
![[7_rsa_per_memorie binarie.pdf#page=17&rect=51,-1,720,269|7_rsa_per_memorie binarie, p.17]]
La relazione ingresso‑uscita della rete combinatoria retroazionata presenta tratti con pendenza **< 1** intorno ai valori nominali H e L (stabilità: un piccolo disturbo viene attenuato, il segnale torna al valore nominale).
Per unire questi tratti deve esistere una zona con pendenza **> 1** che incrocia la retta Q = q in un terzo punto: lo **stato metastabile M**.
Analogia meccanica: due valli (H e L) separate da una collina (M). Una massa sulla cima della collina può restare ferma, ma un minimo disturbo la farà cadere in una valle in modo **casuale**.
**Se il latch finisce in metastabilità a causa di un comando troppo breve, l’uscita potrà assestarsi su 0 o 1 in modo imprevedibile**, annullando la scrittura intesa.
![[7_rsa_per_memorie binarie.pdf#page=20&rect=48,1,688,252|7_rsa_per_memorie binarie, p.20]]
### Il Latch CD (o D‑Latch)
![[7_rsa_per_memorie binarie.pdf#page=22&rect=452,262,685,377|7_rsa_per_memorie binarie, p.22|200]]Il latch CD è una memoria binaria che memorizza un bit e lo rende disponibile in forma vera e negata.
- Ha due ingressi: **C** (enable, comando) e **D** (dato).
- Funzionamento:
    - C = 0 → *Memorizza*: Q mantiene l’ultimo valore scritto.
    - C = 1 → *Scrivi il valore di D*: Q = D (la memoria si dice **trasparente** perché le variazioni di D si ripercuotono su Q con un piccolo ritardo).
- Vantaggio: non si dice *cosa* scrivere, ma *quandlo* campionare D. Pertanto, pilotando opportunamente C, si può comandare la scrittura simultanea di molti latch con un unico segnale di enable.
#### Sintesi del latch CD
**Sintesi a partire dal latch SR**: è sufficiente una rete combinatoria che traduca i comandi (C,D) in segnali S ed R per il latch SR.
 - Tabella di trascodifica:
    - Memorizza (C=0) → S=0, R=0.
    - Scrivi “1” (C=1, D=1) → S=1, R=0.
        - Scrivi “0” (C=1, D=0) → S=0, R=1.
    - Rete combinatoria: S = C·D, R = C·D'.
    - Il latch CD risulta quindi composto da una rete di campionamento (due AND) seguita da un latch SR.
![[7_rsa_per_memorie binarie.pdf#page=26&rect=105,197,615,443|7_rsa_per_memorie binarie, p.26|500]]

- **Sintesi diretta** (senza inizializzazione):
    - Utilizzando un latch S̅R̅ (a NAND) e negando i segnali prodotti dalla rete di campionamento, si ottiene una struttura con 4 NAND e 1 NOT.
    - Poiché quando C=1 il NAND superiore produce D', si può usare questa uscita al posto del NOT, eliminando così la porta NOT: schema finale con **4 NAND** (due per il campionamento, due per la retroazione).
- Anche il latch CD può essere dotato di ingressi di inizializzazione PRE' e CLR' impiegando il latch SR con init.

#### Tempi caratteristici (set‑up, hold, risposta)
![[7_rsa_per_memorie binarie.pdf#page=29&rect=124,-1,645,252|7_rsa_per_memorie binarie, p.29|500]]
- **tₕ** (o t_hold per il latch): durata minima del comando C=1 per evitare metastabilità.
- **tₛᵤ** (set‑up time): tempo minimo durante il quale D deve essere stabile **prima** della discesa di C (fronte di chiusura del campionamento).
- **tₕ** (hold time): tempo minimo durante il quale D deve rimanere stabile **dopo** la discesa di C.
- D deve essere costante per tutto l’intervallo tₛᵤ + tₕ attorno al fronte di discesa di C. Ciò evita cambiamenti simultanei degli ingressi, proibiti nelle RSA.
- **tₚ** (response time): ritardo con cui Q riflette una variazione di D (quando C=1). È tipicamente diverso per transizioni LH e HL.

#### Uscita trasparente e retroazioni
![[7_rsa_per_memorie binarie.pdf#page=30&rect=13,68,711,342|7_rsa_per_memorie binarie, p.30|500]]
Quando C=1, Q è **trasparente** (segue D con un piccolo ritardo). Ne consegue che una **connessione in retroazione** diretta (es. D = Q') crea uno stato di **oscillazione**.
![[7_rsa_per_memorie binarie.pdf#page=33&rect=391,108,684,355|7_rsa_per_memorie binarie, p.33|200]]
**Il semplice latch CD non garantisce stabilità in configurazioni con retroazione diretta**; per questo si usa il flip‑flop D.

### Il Flip‑Flop D (“Edge‑Triggered”)
![[7_rsa_per_memorie binarie.pdf#page=34&rect=228,341,490,465|7_rsa_per_memorie binarie, p.34|200]]
Il flip‑flop D è un elemento di memoria **positive edge‑triggered**, cioè sensibile ai fronti di salita del segnale di clock (CLK).
**Campiona il valore di D in corrispondenza della transizione 0→1 di CLK.**
Dopo un breve tempo di propagazione, l’uscita Q assume il valore campionato e **non segue più D** fino al prossimo fronte di salita. In altre parole, le uscite **non sono trasparenti**.
Questa caratteristica lo rende **robusto in qualunque montaggio in retroazione**, perché le uscite cambiano solo in istanti ben determinati (i fronti del clock) e mai durante la fase di campionamento.
![[7_rsa_per_memorie binarie.pdf#page=34&rect=31,5,681,159|7_rsa_per_memorie binarie, p.34|500]]

**Tempi caratteristici (analoghi al latch CD)**
- **Tempo di set‑up (tₛᵤ)** : D stabile prima del fronte di salita di CLK.
- **Tempo di hold (tₕ)** : D stabile dopo il fronte di salita.
- **Tempo di risposta (tₚ)** : massima durata del transitorio su Q e Q' dopo il fronte attivo.
- Come per il latch CD, D deve rimanere costante per tutto tₛᵤ + tₕ.
![[7_rsa_per_memorie binarie.pdf#page=35&rect=67,4,671,284|7_rsa_per_memorie binarie, p.35|600]]
#### Realizzazioni
**Flip‑Flop D Master‑Slave**
![[7_rsa_per_memorie binarie.pdf#page=36&rect=123,276,578,401|7_rsa_per_memorie binarie, p.36|400]]
- Due latch CD in cascata: il **Master** (collegato a D) controllato da C, lo **Slave** controllato da C' (C negato).
- Quando C=0, il Master è trasparente (segue D), lo Slave memorizza il valore precedente di QM.
- Quando C va a 1 (fronte di salita), il Master memorizza l’ultimo valore di D e lo Slave diventa trasparente, riportando in uscita Q il valore appena memorizzato dal Master.

**Problema di clock gating**: il NOT su C introduce un ritardo che può causare una sovrapposizione di trasparenza tra Master e Slave durante la commutazione. In quel caso D potrebbe passare direttamente all’uscita (trasparenza indesiderata).
Soluzione: utilizzare un **clock a due fasi** (Φ₁, Φ₂ non sovrapposte) che garantisca sempre un breve intervallo con entrambi i latch in memorizzazione, eliminando il NOT.
![[7_rsa_per_memorie binarie.pdf#page=37&rect=88,0,612,361|7_rsa_per_memorie binarie, p.37|500]]

**Flip‑Flop D “Edge‑Triggered” standard (6 NAND)**
-Sintesi ottimizzata utilizza solo **6 porte NAND** e non presenta clock gating (D entra in un solo NAND, fan‑out unitario).
La rete ha **tre variabili di stato** (anziché le due di un Master‑Slave) e implementa comunque il comportamento edge‑triggered.
Dispone di ingressi asincroni **PRE'** e **CLR'**, prioritari rispetto a CLK e D, che forzano rispettivamente Q=1 e Q=0 in modo asincrono.
![[7_rsa_per_memorie binarie.pdf#page=38&rect=271,11,686,267|7_rsa_per_memorie binarie, p.38|400]]
#### Uso in retroazione e come elemento di ritardo
Grazie **all’assenza di trasparenza**, un FF‑D **può essere collegato in anello senza rischi di instabilità**. Esempio: collegando Q' all’ingresso D si realizza un **Toggle Flip‑Flop** (ad ogni fronte di salita CLK, Q commuta).
![[7_rsa_per_memorie binarie.pdf#page=39&rect=382,156,702,404|7_rsa_per_memorie binarie, p.39|300]]
In presenza di un segnale di clock periodico di periodo T, il FF‑D si comporta come un **elemento di ritardo**: Q(t) = D(t−T). Questa proprietà è alla base delle reti sincrone, dove **il clock suddivide il tempo in intervalli discreti.**