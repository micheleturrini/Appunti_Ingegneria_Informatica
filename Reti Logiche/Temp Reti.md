## Analisi con le mappe
1. Si scrive l’**espressione associata allo schema** e, se necessario, la si manipola fino ad ottenere una espressione **SP o PS**
![[3_reti_costo_minimo.pdf#page=40&rect=19,249,712,414|3_reti_costo_minimo, p.40|500]]
2. Si predispone una mappa di dimensioni adeguate e **si tracciano sulla mappa i RR che corrispondono ai termini dell’espressione**
![[3_reti_costo_minimo.pdf#page=40&rect=97,12,687,169|3_reti_costo_minimo, p.40|400]]
3. Nelle celle coperte da un RR si indica il valore «1» se l’espressione è SP, «0» se è PS; nelle celle non coperte da RR si inserisce «0» nel caso SP, «1» nel caso PS (non esiste il modo di risalire alle indifferenze)
![[3_reti_costo_minimo.pdf#page=41&rect=222,149,502,310|3_reti_costo_minimo, p.41|200]]
## Algebra binaria col NAND
**Algoritmo per la sintesi di una rete NAND a partire da una SP**
1.  Si parte da un’espressione SP, SPS, SPSP... e si introducono gli operatori “·” e le parentesi non indicati esplicitamente.
2.  Si sostituisce il simbolo “Freccia su” ad ogni simbolo “·”
3. Si sostituisce il simbolo “ Freccia su” ad ogni simbolo “+” e si complementano le variabili singole (non i prodotti) affiancate a tale simbolo senza aggiungere o togliere parentesi.
4. Opzionale) Se compaiono segnali di ingresso in forma negata e non sono disponibili, si sostituiscono con il NAND del segnale in forma vera.
## Sintesi tramite Dec Mux e ROM
Sono disponibili come **componenti elementari** anche **reti complesse** che fanno da **blocchi** per progetti articolati.
### Decoder
Converte da numero binario a 3 bit a codice «1 su 8».
*Una convenzione per indicare gli ingressi usa le prime 3 lettere, con la A ad indicare la cifra meno significativa. Ogni uscita ha solo una configurazione degli ingressi per cui vale «1», quella che codifica il numero dell’uscita stessa.*

Segnale di **enable**: l’ingresso EN=0 **disabilita tutte le uscite** (rappresenta la stringa di «zeri» del codice 1 su N)

![[4_dec_mux_rom.pdf#page=7&rect=-2,118,722,509|4_dec_mux_rom, p.7|400]]
> [!Fan Out]
> Ogni gate ha un **Fan out** ovvero il numero massimo di gate che possono essere connessi alla sua uscita. Per non rischiare incompatibilità si utilizza una **doppia negazione in entrata**. (si maschera la geometria interna)

**Sintesi con Decoder e OR**
Utilizzando opportunamente Decoder e gate OR **si può sintetizzare qualsiasi espressione in forma canonica SP**
Non servono mappe e non è una sintesi di costo minimo
Il tempo di progettazione quasi zero. 
Devo collegare gli ingressi ai bit di indirizzo nell’**ordine usato per definire i mintermini.**

Es. Full adder
![[4_dec_mux_rom.pdf#page=12&rect=38,0,630,330|4_dec_mux_rom, p.12| 500]]

**Decoder con più ingressi**
Oltre i 5 ingressi il numero di componenti per ogni decoder diventa ingestibile.
Soluzione: **composizione modulare**
Quando decodifico i bit di peso maggiore, genero i segnali di enable per i decoder collegati ai bit di minor peso, così i decoder di secondo livello non interessati dalla decodifica produrranno 0 su tutti i bit di uscita
Non posso connettere gli indirizzi in modo arbitrario. Devo collegare il bit più significativo all'ingresso corrispondente.
Indirizzi di peso minore (bit meno significativi) devono essere portati negli stadi a valle (es. AB)![[4_dec_mux_rom.pdf#page=15&rect=326,22,712,448|4_dec_mux_rom, p.15|300]]
### Multiplexer
Un **selettore** è l’equivalente Hardware di un «**if**»: permette di **decidere, tramite un segnale A, quale tra le due vie d’ingresso sarà replicata dall’uscita**
![[4_dec_mux_rom.pdf#page=17&rect=374,9,718,392|4_dec_mux_rom, p.17|300]]

Il concetto di selettore può essere generalizzato a n bit di indirizzo
![[4_dec_mux_rom.pdf#page=20&rect=186,14,583,336|4_dec_mux_rom, p.20|400]]

### Teorema di espansione di Shannon
Data una funzione F di n variabili binarie
$$f(x1​,…,xi​,…,xn​)=xi​⋅f(x1​,…,1,…,xn​)+xi​​⋅f(x1​,…,0,…,xn​)$$
**applicando ripetutamente il teorema** è possibile dedurre le seguenti espressioni generali
- **SP** ![[4_dec_mux_rom.pdf#page=23&rect=25,230,709,324|4_dec_mux_rom, p.23]]
- **PS** ![[4_dec_mux_rom.pdf#page=23&rect=21,22,712,114|4_dec_mux_rom, p.23]]
Es. full adder vd. slide 25
### Mux come rete programmabile
- L'espressione generale di Shannon $\sum m(i) \cdot F(i)$ è perfettamente implementabile con un MUX.
- I bit di indirizzo del MUX sono le variabili di ingresso della funzione ($x_1...x_n$).
- Sulle vie di ingresso del MUX ($I_0...I_{2^n-1}$) vanno collegati i valori **costanti** (0 o 1) che la funzione assume per ogni combinazione di ingresso, presi direttamente dalla tabella della verità.
- In questa configurazione, il MUX viene chiamato **Look-Up Table (LUT)** . È un dispositivo programmabile: "programmare" la funzione significa semplicemente collegare le vie a `Vcc` (1 logico) o `GND` (0 logico) in base alla tabella di verità.
Es. full adder
![[4_dec_mux_rom.pdf#page=26&rect=37,3,710,489|4_dec_mux_rom, p.26|600]]

**Composizione di MUX**
Per rendere coerenti i bit di programmazione con la tabella della verità, è consigliabile far corrispondere gli indirizzi dei dispositivi a valle ai bit di maggior peso.
![[4_dec_mux_rom.pdf#page=28&rect=57,-1,712,425|4_dec_mux_rom, p.28|600]]
### Reti programmabili
La realizzazione di circuiti è estremamente costosa, per ammortizzare i costi conviene renderli versatili.
> [!Rete Programmabile]
> Una rete combinatoria programmabile è un circuito in cui la **relazione ingresso/uscita** può essere modificata agendo su un insieme di segnali interni detti **bit di programmazione**.
- Una tabella della verità può essere vista come una **memoria**: le righe della tabella sono le locazioni di memoria, identificate da un **indirizzo** (la combinazione degli ingressi), e il contenuto di ogni locazione è il valore (o i valori) dell'uscita.
- Un circuito che implementa questa "memoria" in modo fisso è una **Read Only Memory (ROM)**
### ROM
> [!ROM]
> Una ROM è un circuito che **memorizza in modo permanente un array di dati**. È intrinsecamente un generatore di funzioni booleane.

![[4_dec_mux_rom.pdf#page=33&rect=40,19,662,369|4_dec_mux_rom, p.33|500]]
![[4_dec_mux_rom.pdf#page=35&rect=146,36,718,396|4_dec_mux_rom, p.35|400]]
**Chiudere un contatto equivale dunque a memorizzare un «1» piuttosto che uno «0»**
- Una ROM con `n` ingressi di indirizzo e `k` uscite di dato è detta ROM di dimensione **$2^n \times k$** .
- Può memorizzare $2^n$ parole (dati), ciascuna di `k` bit.
- La capacità si misura in Byte, KB, MB. Ad esempio, una ROM con $n=12$ bit di indirizzo ha $2^{12} = 4096$ locazioni. Se ogni locazione è di 8 bit, la capacità è di 4KB.

**Tipi di Memorie Non Volatili:**
- **ROM (Mask ROM):** Programmate in fabbrica con una maschera. Costo iniziale alto, adatte solo per grandi volumi. Non modificabili.
- **PROM (Programmable ROM):** Programmabili una sola volta dall'utente (OTP). Si bruciano i collegamenti (fusibili) per memorizzare gli 0.
- **EPROM (Erasable PROM):** Programmabili e cancellabili tramite esposizione ai raggi UV. Richiedono di essere rimosse dal circuito.
- **EEPROM e Flash Memory:** Riscrivibili elettricamente, senza bisogno di rimuoverle dal circuito. (chiavette) Sono la base per i dispositivi logici riconfigurabili.
## Aritmetica binaria e ALU
In alcuni casi  la somma di due numeri a n bit può non essere rappresentabile con il numero di bit disponibile (**overflow**).
![[5_aritmetica_binaria_alu.pdf#page=3&rect=43,8,695,190|5_aritmetica_binaria_alu, p.3]]
### **Circuiti Sommatori (Adder)**
Per evitare tabelle di verità con `2²ⁿ` righe, si usa un approccio modulare.

**Half Adder (HA)** blu
- **Funzione:** Somma due bit `a` e `b` (nessun riporto in ingresso).
- **Uscite:**
    - `somma (s) = a ⊕ b` (XOR)    
    - `riporto (r) = a · b` (AND)

**Full Adder (FA)** giallo
- **Funzione:** Somma tre bit: `a`, `b` e il riporto in ingresso `rᵢ` (carry-in).
- **Uscite:**
    - `somma (sᵢ)`   
    - `riporto in uscita (rᵢ₊₁)`    
- **Realizzazione:** Può essere costruito con due Half Adder e una porta OR.
    - Il primo HA somma `a` e `b` (produce `s₁` e `r₁`).
    - Il secondo HA somma `s₁` e `rᵢ` (produce `sᵢ` finale e `r₂`).
    - La porta OR combina i riporti `r₁` e `r₂` per generare `rᵢ₊₁`.
- **Espressioni minime**
    - `sᵢ = a ⊕ b ⊕ rᵢ`
    - `rᵢ₊₁ = a·b + (a⊕b)·rᵢ` (Questa forma è utile per implementazioni più veloci come il carry lookahead).

![[5_aritmetica_binaria_alu.pdf#page=5&rect=509,174,700,444|5_aritmetica_binaria_alu, p.5|150]]
![[5_aritmetica_binaria_alu.pdf#page=5&rect=23,6,716,171|5_aritmetica_binaria_alu, p.5|500]]
### **Adder**
Per realizzare un sommatore a n bit.
- **Struttura:** Si collegano in serie un Half Adder per il bit meno significativo (LSB) e `n-1` Full Adder per i bit successivi.
- **Funzionamento:** Il riporto generato da ogni stadio (`rᵢ₊₁`) diventa l'ingresso di riporto (`rᵢ`) per lo stadio successivo.
- **Carry Out (`Cout`):** Il riporto dell'ultimo Full Adder (`rₙ`) indica la validità del risultato (overflow per numeri senza segno).
![[5_aritmetica_binaria_alu.pdf#page=7&rect=22,15,688,226|5_aritmetica_binaria_alu, p.7|500]]
- **Carry In (`Cin`):** Sostituendo l'Half Adder con un Full Adder, si ottiene un ingresso aggiuntivo `Cin`. Questo permette di collegare più sommatori a `n` bit in cascata per elaborare numeri con un numero di bit maggiore (es. 2n bit).
![[5_aritmetica_binaria_alu.pdf#page=8&rect=0,6,720,301|5_aritmetica_binaria_alu, p.8|500]]
### **Complementi e Sottrazione**
**Complemento a 1**
- **Definizione:** Il complemento a `(β-1)` di un numero `A` di `n` cifre in base `β` è: `(βⁿ - 1) - A`.
- **Proprietà in binario (β=2):** Il complemento a 1 si ottiene semplicemente invertendo (NOT) ogni bit del numero.
    - _Esempio:_ Il complemento a 1 di `(001110)₂` è (2^6 -1)-001110 = `(110001)₂`.
- Si ottiene facendo il **NOT** su ogni cifra

**Complemento a 2**
- **Definizione:** Il complemento a `β` di un numero `A` di `n` cifre è: `βⁿ - A`.
- **Calcolo in binario:**
    1. **Metodo 1:** Si calcola il complemento a 1 e poi si somma 1.
	    Esempio: NOT(001110)+1 = 110001 + 1 = 110010
    2. **Metodo 2:** Si parte da destra, si lasciano invariati tutti i bit fino al primo 1 (incluso), e si complementano tutti i bit a sinistra.
- **Proprietà:** Complementare due volte un numero restituisce il numero originale.
- **Utilità:**
    1. Permette di implementare la **sottrazione come somma**, usando lo stesso circuito dell'adder. 
    2. È la base per la **rappresentazione dei numeri con segno**.  

**Sottrazione di Numeri Senza Segno (`A - B` con `A >= B`)**
- **Metodo:** Si somma `A` con il complemento a 2 di `B`. Il risultato corretto è dato dai primi `n` bit della somma.
- **Spiegazione:** `A + (2ⁿ - B) = (A - B) + 2ⁿ`. Il termine `2ⁿ` diventa il carry out.    ![[5_aritmetica_binaria_alu.pdf#page=12&rect=77,36,575,242|5_aritmetica_binaria_alu, p.12|400]]
- **Segnalazione Overflow per la Sottrazione ():**
    - **`Cout = 1`** → Operazione eseguita correttamente (`A >= B`).
    - **`Cout = 0`** → Overflow (`A < B`), il risultato non è rappresentabile come numero senza segno. (Regola opposta alla somma).
![[5_aritmetica_binaria_alu.pdf#page=13&rect=46,4,702,208|5_aritmetica_binaria_alu, p.13|500]]

**Adder Subtracter**
circuito comandato da un bit di input se = 1 realizza la somma altrimenti la sottrazione.![[5_aritmetica_binaria_alu.pdf#page=17&rect=2,40,722,265|5_aritmetica_binaria_alu, p.17]]
![[5_aritmetica_binaria_alu.pdf#page=18&rect=1,40,724,265|5_aritmetica_binaria_alu, p.18]]
### Notazione
Se mando anche solo un BUS in ingresso in un gate sottointendo che l'operazione si ripete per ogni linea del bus.
![[5_aritmetica_binaria_alu.pdf#page=15&rect=33,14,650,236|5_aritmetica_binaria_alu, p.15|400]]
(si aggiunge una linea obliqua sul collegamento)
Questo vale SEMPRE.
### **Numeri con segno**
Una stringa di bit è solo una sequenza; la sua interpretazione è una convenzione.

**Rappresentazione Segno e Valore Assoluto**
- **Struttura:** Il bit più significativo (MSB) è il segno (0=+, 1=-). I bit restanti sono il valore assoluto.
- **Intervallo:** `-(2ⁿ⁻¹-1)` ... `+(2ⁿ⁻¹-1)`.
- **Svantaggio:** Doppia rappresentazione dello zero (es. +0 e -0) rende le operazioni complicate e i circuiti costosi.

**Rappresentazione in Complemento a 2**
- **Struttura:**
    - **Numeri positivi:** Come in segno e valore assoluto (MSB = 0).
    - **Numeri negativi:** Si rappresentano come il complemento a 2 del loro valore assoluto positivo.
- **Intervallo (asimmetrico):** `-2ⁿ⁻¹` ... `+(2ⁿ⁻¹-1)`. (Es. con 4 bit: da -8 a +7).
- **Vantaggi:**
    - Rappresentazione unica dello zero.
    - Le operazioni elementari tra numeri rappresentati in complemento a 2 danno come risultato ancora numeri in complemento a 2.
- **Calcolo del valore:** Si usa la formula posizionale, dove il MSB ha peso negativo.
    - _Esempio:_ `(11100)₂` in complemento a 2 = `-1·2⁴ + 1·2³ + 1·2² + 0·2¹ + 0·2⁰ = -16 + 8 + 4 = -4`.
- **Estensione del segno:** Per aumentare il numero di bit, si **replica il bit più significativo** (MSB) a sinistra.
    - _Esempio:_ `(11100)₂` (-4) su 5 bit diventa `(1111100)₂` (-4) su 7 bit
    - _Esempio:_ `(01100)₂` (12) su 5 bit diventa `(0001100)₂` (12) su 7 bit.
> [!attention]
> **una stringa di bit è sempre e solo una stringa di bit**
L’interpretazione è «negli occhi di chi guarda»

**Aritmetica con Segno e Overflow**
- **Overflow:** Si verifica quando il risultato esce dall'intervallo `[-2ⁿ⁻¹, 2ⁿ⁻¹-1]`. **Non è più segnalato dal Carry Out.**
    
- **Regola di rilevazione** L'overflow si verifica se:
	- il riporto **nell'ultimo e nel penultimo stadio sono diversi**
![[5_aritmetica_binaria_alu.pdf#page=24&rect=46,11,696,192|5_aritmetica_binaria_alu, p.24|600]]
	- Gli operandi hanno lo **stesso segno** e il **segno del risultato è diverso** da quello degli operandi.

**Adder e subtracter con segno**
- **Overflow**: L'adder per numeri con segno deve avere un'uscita aggiuntiva, **Overflow Flag (`OF`)**, che segnala questa condizione. `OF = rₙ₋₁ ⊕ rₙ` (dove `rₙ` è `Cout` e `rₙ₋₁` è il riporto del penultimo stadio).
> [!attention]
> L'**overflow** vale solo per i bit **con segno**
se non hanno segno si usa il carryout
- **Sottrazione con segno:** Funziona allo stesso modo: `(±A) - (±B) = (±A) + (∓B)` (cioè si somma il complemento a 2 del sottraendo).
- **Input:** `A[n-1:0]`, `B[n-1:0]`, un bit di controllo `S/D'` (o `K₀`).
- **Output:** `U[n-1:0]` (risultato), `CF` (Carry Flag).
- **Funzionamento:**
    - Se `K₀ = 1` (somma): `U = A + B`.
    - Se `K₀ = 0` (sottrazione): `U = A - B`, ottenuta come `A + (NOT B) + 1`.
- **Flag:** `CF` è il `Cout` dell'adder. Nella sottrazione senza segno, `CF=1` indica successo; nella somma senza segno, `CF=1` indica overflow.
### **Unità Aritmetico-Logica (ALU)**
una rete combinatoria che esegue **operazioni aritmetiche e logiche su due operandi**. Le istruzioni riguardanti l'operazione da fare vengono portate tramite il **codice operativo (op-code)** e eventuali anomalie vengono segnalate dalle **flag**.
![[5_aritmetica_binaria_alu.pdf#page=27&rect=474,131,694,336|5_aritmetica_binaria_alu, p.27|200]]

**Espansione con Operazioni Aritmetiche e Logiche**
Collegando **due MUX a uno 0** posso fare le seguenti operazioni.
![[5_aritmetica_binaria_alu.pdf#page=30&rect=0,16,719,314|5_aritmetica_binaria_alu, p.30|500]]
![[5_aritmetica_binaria_alu.pdf#page=31&rect=339,18,704,426|5_aritmetica_binaria_alu, p.31|200]]
Baipassando gli ingressi a e B (110) ottengo valori costanti.

Aggiungendo un nuovo ingresso **M** e un **and** collegato a **tutti i riporti** posso selezionare fra **operazioni logiche (no riporti M = 0) o aritmetiche (con riporti M = 1)**
![[5_aritmetica_binaria_alu.pdf#page=33&rect=26,29,672,458|5_aritmetica_binaria_alu, p.33|400]]
Il carry in senza riporti è ininfluente
![[5_aritmetica_binaria_alu.pdf#page=34&rect=338,13,580,423|5_aritmetica_binaria_alu, p.34|200]]

Aggiungo un AND e un OR controllati da K3 per implementare anche le loro funzioni.
- Quando `K3 = 0`, la rete viene disabilitata. In questo caso l’uscita finale è semplicemente quella dell’adder (come nelle slide precedenti).
- Quando `K3 = 1`, l’uscita finale è data da `(A & B) | (uscita_adder)`.
L’operazione `(A & B) | (uscita_adder)` permette di ottenere sia AND che OR a seconda di cosa produce l’adder in quel momento.
Si ottengono le seguenti **operazioni logiche**
![[5_aritmetica_binaria_alu.pdf#page=36&rect=338,15,663,425|5_aritmetica_binaria_alu, p.36|250]]

Il circuito può essere **semplificato** sostituendo i mux
![[5_aritmetica_binaria_alu.pdf#page=38&rect=0,8,716,356|5_aritmetica_binaria_alu, p.38|500]]
Aggiungo poi  ulteriori **flag**:
- Zero Flag (ZF): segnala se il risultato è zero.
- Sign Flag (SF): segnala se il risultato è negativo (quando interpretato come numero rappresentato in complemento a 2)

**Opzioni più veloci**
L'adder così costruito è molto lento.
si utilizza il **carry lookahead** (più costoso in termini di gate)
![[5_aritmetica_binaria_alu.pdf#page=46&rect=50,3,667,455|5_aritmetica_binaria_alu, p.46|400]]
