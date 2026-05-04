# Reti Logiche - Cheat Sheet

## 1. Gate Fondamentali

| Gate | Simbolo | Espressione | Tabella di verità (A,B) |
|------|--------|-------------|-------------------------|
| NOT | ¬A, A' | Y = A' | 0→1, 1→0 |
| AND | A·B | Y = A·B | 1 solo se entrambi 1 |
| OR | A+B | Y = A+B | 1 se almeno uno 1 |
| NAND | A↑B | Y = (A·B)' | NOT di AND |
| NOR | A↓B | Y = (A+B)' | NOT di OR |
| XOR | A⊕B | Y = A⊕B | 1 se dispari ingressi 1 |
| XNOR | A⊙B | Y = (A⊕B)' | 1 se pari ingressi 1 |

**Proprietà importanti:**
- AND e OR hanno più ingressi (fan-in)
- NAND e NOR sono **universali**: con essi si realizza qualsiasi funzione
- I gate hanno **ritardo di propagazione** τ_p
- **Fan-out**: numero max di gate pilotabili in uscita

---

## 2. Algebra di Boole – Identità Principali

| Proprietà | Formule |
|-----------|---------|
| Commutativa | A·B = B·A, A+B = B+A |
| Associativa | (A·B)·C = A·(B·C), (A+B)+C = A+(B+C) |
| Distributiva | A·(B+C) = A·B + A·C, A+(B·C) = (A+B)·(A+C) |
| Idempotenza | A·A = A, A+A = A |
| Identità | A·1 = A, A+0 = A |
| Limite | A·0 = 0, A+1 = 1 |
| Involuzione | (A')' = A |
| Complemento | A·A' = 0, A+A' = 1 |
| Assorbimento | A + A·B = A, A·(A+B) = A |
| Combinazione | A·B + A·B' = A, (A+B)·(A+B') = A |
| Consenso | A·B + A'·C + B·C = A·B + A'·C |
| De Morgan | (A·B)' = A' + B', (A+B)' = A'·B' |

---

## 3. Forme Canoniche

### Mintermini (prodotti) – SP
- Per ogni riga con uscita **1** → prodotto delle variabili (diretta se=1, negata se=0)
- **Prima forma canonica SP**: somma logica di tutti i mintermini
- Notazione: \( F = \sum m(i) \) dove i è l'indice della riga

### Maxtermini (somme) – PS
- Per ogni riga con uscita **0** → somma delle variabili (diretta se=0, negata se=1)
- **Seconda forma canonica PS**: prodotto logico di tutti i maxtermini
- Notazione: \( F = \prod M(i) \)

**Relazione**: \( m(i) = M(i)' \)

---

## 4. Mappe di Karnaugh – Costo Minimo

### Regole di base
- Mappa a 2ⁿ celle per n variabili (n ≤ 6)
- Codifica **Gray**: celle adiacenti differiscono per un solo bit
- **Adiacenza** anche tra bordi opposti (avvolgimento)
- **RR (Raggruppamenti Rettangolari)**: 2ᵏ celle (k=0..n) formanti rettangolo/quadrato

### Individuazione implicanti/implicati

| Caso SP (copertura degli 1) | Caso PS (copertura degli 0) |
|-----------------------------|-----------------------------|
| RR di 1 (e don't care) → **implicante** | RR di 0 (e don't care) → **implicato** |
| RR massimo (non contenibile) → **implicante primo** | RR massimo → **implicato primo** |
| Implicante primo che copre un 1 non coperto da altri → **essenziale** | Implicato primo che copre uno 0 non coperto da altri → **essenziale** |

**Termine prodotto/somma risultante**: solo le variabili che non cambiano nel RR
- SP: variabile = 1 → forma vera, = 0 → negata, variabile → non compare
- PS: variabile = 0 → forma vera, = 1 → negata, variabile → non compare

### Procedura per espressione minima
1. Tracciare RR di dimensione massima che coprono celle con valore desiderato (1 per SP, 0 per PS)
2. Identificare **RR essenziali** (coprono almeno una cella non copribile altrimenti)
3. Coprire le celle rimanenti con il minor numero possibile di RR (eventualmente non massimi, ma preferire i più grandi)
4. Scrivere l'espressione come somma (SP) o prodotto (PS) dei termini corrispondenti

### Don't care (–)
- Possono essere usati come 1 (SP) o 0 (PS) per formare RR più grandi
- Mai obbligatori da coprire

---

## 5. Reti a Due Livelli con NAND/NOR

### Sintesi con NAND a partire da SP
1. Scrivere espressione SP esplicitando i prodotti (e parentesi)
2. Sostituire ogni `·` con `↑` (NAND)
3. Sostituire ogni `+` con `↓` (NOR) **e** complementare le variabili singole adiacenti al `+` (senza aggiungere/togliere parentesi)
4. (Opzionale) Se servono ingressi negati e non sono disponibili, sostituirli con NAND dell'ingresso vero (es. A' = A↑A)

### Sintesi con NOR a partire da PS
Procedura duale.

---

## 6. Decoder

- Converte n ingressi in 2ⁿ uscite, una sola attiva per ogni combinazione
- **Enable** (EN): se EN=0 tutte le uscite sono 0
- **Fan-out** limitato: per pilotare più gate si usa doppia negazione in ingresso

### Sintesi con Decoder + OR
- Qualsiasi funzione in forma SP canonica si realizza collegando le uscite del decoder (corrispondenti ai mintermini) a una porta OR
- Non richiede mappe, tempo di progettazione quasi zero
- **Ordine ingressi**: i bit di indirizzo devono seguire l'ordine usato per i mintermini

### Decoder a più ingressi (composizione modulare)
- Bit più significativi → primo livello di decoder, le cui uscite abilitano i decoder di secondo livello
- I bit meno significativi vanno agli ingressi dei decoder di secondo livello

---

## 7. Multiplexer (MUX)

- Selettore: sceglie tra 2ⁿ ingressi tramite n bit di indirizzo

### Teorema di Shannon
\[
f(x_1,...,x_n) = x_i \cdot f(x_1,...,1,...,x_n) + x_i' \cdot f(x_1,...,0,...,x_n)
\]

Applicato ripetutamente si ottiene:
- Forma SP: \( f = \sum_{i=0}^{2^n-1} m(i) \cdot f(i) \)
- Forma PS: \( f = \prod_{i=0}^{2^n-1} (M(i) + f(i)) \)

### MUX come LUT (Look-Up Table)
- Collegare agli ingressi del MUX i valori costanti 0/1 presi dalla tabella di verità
- Gli ingressi di indirizzo sono le variabili della funzione

### Composizione di MUX
- Per funzioni con più variabili si usano MUX in cascata
- È consigliabile far corrispondere gli indirizzi dei dispositivi a valle ai bit di maggior peso

---

## 8. ROM (Read Only Memory)

- Struttura: matrice di celle disposte su righe (parole) e colonne (bit)
- Dimensioni: \( 2^n \times k \) (n indirizzi, k uscite di dato)
- Programmare: connettere (o meno) le celle per memorizzare 1 o 0
- Tipi: Mask ROM, PROM, EPROM, EEPROM, Flash

---

## 9. Aritmetica Binaria

### Half Adder (HA)
\[ s = a \oplus b,\quad r = a \cdot b \]

### Full Adder (FA)
\[ s_i = a \oplus b \oplus r_i \]
\[ r_{i+1} = a\cdot b + (a\oplus b)\cdot r_i \]

### Adder a n bit
- Collegare un HA per LSB e FA per i successivi
- **Carry ripple**: lento, semplice
- **Carry lookahead**: veloce, più gate

### Sottrazione con complemento a 2
- Complemento a 2 di B: \( B' + 1 \)
- Sottrazione A – B = A + (complemento a 2 di B)
- Per numeri senza segno: Cout=1 → ok (A≥B); Cout=0 → overflow (A<B)

### Adder–Subtracter
- Un bit di controllo (K₀) seleziona addizione (K₀=1) o sottrazione (K₀=0)
- Sottrazione: invertire B e impostare carry-in = 1

### Overflow nei numeri con segno (complemento a 2)
- **Regola**: overflow se carry in ultimo stadio ≠ carry out dell'ultimo stadio
  \[ OF = r_{n-1} \oplus r_n \]
- Alternativa: segni operandi uguali e segno risultato diverso

### Flag
| Flag | Significato |
|------|-------------|
| CF (Carry Flag) | Carry out dell'adder (unsigned overflow) |
| OF (Overflow Flag) | Overflow con segno (CF ⊕ carry penultimo) |
| ZF (Zero Flag) | Risultato = 0 |
| SF (Sign Flag) | Risultato negativo (MSB = 1) |

---

## 10. ALU (Arithmetic Logic Unit)

- Rete combinatoria con operazioni aritmetiche e logiche su due operandi
- Selezionate tramite **codice operativo (op-code)**
- Costruita con adder, porte logiche, multiplexer

### Espansione con operazioni aggiuntive
- Usando MUX e porte AND/OR si aggiungono funzioni come A AND B, A OR B, ecc.
- Un bit di controllo può separare operazioni logiche (carry inibito) da aritmetiche

---

## 11. Notazioni e Convenzioni

### Bus
- Un gruppo di segnali viene rappresentato con una linea spessa e una barra obliqua
- Es: `A[3:0]` indica 4 bit, `A[2]` è un singolo bit
- Quando un bus entra in un gate, l'operazione si replica per ogni bit

### Don't care
- Nelle specifiche: `–` indica valore non definito (il progettista può scegliere 0 o 1)
- Nelle mappe di Karnaugh si possono usare per formare RR più grandi

### Ritardi
- τ_p: ritardo di propagazione di un gate
- Ritardo inerziale: impulsi più brevi di τ_p non vengono propagati
- Ncasc: numero di gate in cascata (per valutare la velocità)

### Costo di una rete
- Ngate: numero di gate
- Nconn: numero totale di ingressi dei gate (conteggia ogni pin)
- Ncasc: profondità della rete

---

## 12. Trucchi e Cose da Ricordare

- **NAND = AND + NOT**, **NOR = OR + NOT**
- **NAND universale**: con NAND si realizzano NOT, AND, OR
- **Per passare da SP a NAND**: sostituire `·` con NAND e `+` con NOR complementando le variabili singole. Aggiungere parentesi se necessario.
- **Decoder**: le uscite sono i mintermini, basta fare OR per funzioni SP.
- **MUX come LUT**: gli indirizzi sono le variabili, gli ingressi dati sono i valori della tabella di verità.
- **Composizione di decodifica**: i bit più significativi vanno al primo decoder; i bit meno significativi vanno agli ingressi dei decoder abilitati.
- **Overflow con segno**: guarda i due carry più significativi (diversi = overflow).
- **Estensione del segno**: replicare il MSB a sinistra.
- **Codice Gray**: cambia un solo bit tra configurazioni successive.
- **ASCII/Unicode**: UTF-8 è retrocompatibile con ASCII.
- **Display 7 segmenti**: sono 7 funzioni parallele di 4 ingressi (BCD). Le combinazioni non usate sono don't care.
- **Encoders**: per ridurre complessità si usano don't care sulle configurazioni impossibili.

## 13. Esempi Rapidi di Applicazione

### Full Adder (uscite S e R)

**SP minima con Karnaugh**  
S = a⊕b⊕r  
R = a·b + a·r + b·r

**Con NAND**  
R = (a↑b) ↑ (a↑r) ↑ (b↑r)   (dopo De Morgan e trasformazioni)
### Sommatore a 4 bit con carry lookahead
- Genera: G_i = a_i·b_i, P_i = a_i⊕b_i
- Carry: c_{i+1} = G_i + P_i·c_i
- Anticipo: c_1 = G_0 + P_0·c_0, c_2 = G_1 + P_1·G_0 + P_1·P_0·c_0, ecc.
### ALU a 1 bit (bit-slice)
- Ingressi: a_i, b_i, c_i, op-code
- Uscite: risultato, carry out
- Usa MUX per selezionare tra somma (FA), AND, OR, ecc.
