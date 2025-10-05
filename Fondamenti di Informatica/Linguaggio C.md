## Caratteristiche principali
Il linguaggio **C** (creato da Dennis Ritchie, 1972) è:
- **Efficientissimo** → tradotto in linguaggio macchina quasi diretto.
- **Sequenziale** → l’ordine delle operazioni conta.
- **Imperativo** → i programmi sono composti da istruzioni che modificano lo stato del calcolatore.
- **Strutturato a blocchi** → il codice è suddiviso in funzioni e blocchi racchiusi tra `{ }`.
- **Basato su espressioni** → ogni istruzione è spesso un’espressione che produce un valore.
![[03-IntroCompilatori+LinguaggioC.pdf#page=20&rect=40,115,652,317|03-IntroCompilatori+LinguaggioC, p.20|500]]
## Struttura
- **Direttive al preprocessore** (`#include`, `#define`) → vengono eseguite prima della compilazione.
- Il **punto di ingresso** di ogni programma è la funzione `main()`
- **Dichiarazioni e definizioni** → introducono nomi di costanti, variabili, tipi.
- **Sequenza di istruzioni** → frasi del linguaggio eseguite in ordine.
- `return 0;` → segnala che il programma è terminato correttamente.
```cpp
#include <stdio.h>

int main(void) {
    // Dichiarazione
    int numero = 10;

    // Istruzione
    printf("Il numero è: %d\n", numero);

    return 0;
}

```
## Elementi del linguaggio
Variabili e espressioni discusse a parte
### Parole chiave
Espressioni **riservate**, con significato predefinito (non possono essere usate come identificatori).  
Esempi:  
`int`, `return`, `if`, `else`, `while`, `for`, `switch`, `break`, `continue`, `void`, `struct`, `typedef`, `const`, `static` …
### Costanti
Valori fissi
```cpp
const float PI = 3.14159;
```
### Identificatori
- Sono i **nomi di variabili, funzioni, tipi**
- Regole:
    - Devono iniziare con una lettera o `_`.    
    - Possono contenere lettere, numeri, `_`.
    - Case sensitive (`pippo` ≠ `Pippo`).
`int contatore; 
float valore_medio;`
### Commenti
```cpp
/* commento su più 
righe */

// commento su una riga
```
## Variabili
Una variabile è un’**astrazione di una cella di memoria**.
- **L-Value** → l’indirizzo della variabile (dove sta in memoria).
- **R-Value** → il contenuto della variabile.
- **Identificatore** -> deve avere un nome 
#### Definizione di variabili
Si **definiscono** all'**inizio** del blocco e poi si usano
Ogni variabile deve avere:
- un **nome** (identificatore)
- un **tipo** (classe di valori che può assumere) *guarda Tipi di Dato*
- opzionalmente un valore iniziale (se non sovrascrivo il valore della variabile il pc usa quello che trova)
- un **scope** (campo di visibilità) vive all'interno del blocco in cui è iniazlizzata
- un **tempo di vita**

```cpp
float altezza = 1.75; // variabile reale 
char iniziale = 'A';  // variabile carattere`
int eta = 21;       // variabile intera 
```
> [!Chesani 26/09/25]
> Qualunque istruzione che tocca il ciarpame diventa ciarpame
> **Inizializzare sempre le variabili**
## Espressioni
Un’espressione è **una formula che produce un valore**.
Possono avere **Effetti collaterali** se assegnano un valore a una variabile, la **incrementano** o la **diminuiscono**
L'assegnamento è distruttivo

```cpp
int i = 4+3; // espressione semplice

// espressioni con effetti collaterali
int i = i-3;

int j = 0;
int i = j-3; // assegnamento

int i += 1 // incrementa di 1 (+,-,*.%,...)
```
#### Espressioni eterogenee
eseguendo un'espressione eterogenea entrambi gli operandi vengono **trasformati** temporaneamente in float (**promotion**) ma poi se necessario il risultato è **arrotondato per troncamento** (perdita di informazioni)
```cpp
#include <stdio.h>
int main() {
  int i, k; 
  float j; 
  i = 20; 
  k = i % 3; 
  i = i / 3; 
  
  k = i / 4.0F; // restituisce 1 perchè arrotondato
  j = i / 4.0F; // restituisce 1.5 perche è float
  return (0); 
}
```

## Tipi di Dato 
### Specificatori
- `void` → indefiniti
- `char` → caratteri (1 byte)    
- `int` → interi
- `float` → reali **precisione singola** (~6 cifre, 32 bit)
- `double` → reali **precisione doppia** (~15 cifre, 64 bit)
- `long double` → precisione quadrupla (128 bit, dipende dal compilatore)
Dichiarando un **float** il compilatore di default considera la variabile **double**
per avere una variabile float devo aggiungere una f
```cpp
float a = 0.3f;
```
### Modificatori
- `short` → almeno **16 bit**
- `long` → almeno **32 bit**
- `signed` → con segno (positivi + negativi)
- `unsigned` → senza segno (solo positivi)

`sizeof(long int) >= sizeof(int) >= sizeof(short int)`
I booleani non esistono (Convenzione: `0 = falso`, valore ≠ 0 = vero.)
### Rappresentazione in memoria.
![[04-TipiDato.pdf#page=5&rect=84,83,614,204|04-TipiDato, p.5|300]]
**Interi**
- **Naturali (unsigned)**: 0 … `2^N - 1`
    - 8 bit → 0..255
    - 16 bit → 0..65535
    - 32 bit → 0..4.294.967.295
- **Con segno (signed, complemento a 2)**: `[-2^(N-1), 2^(N-1)-1]`
    - 8 bit → -128..127
    - 16 bit → -32768..32767
    - 32 bit → -2.147.483.648..2.147.483.647
Nel seguente codice la variabile **short int è insufficiente per contenere il risultato** - **overflow**
```cpp
#include <stdio.h> 
int main(void) { 
	short int i;
	short int k;
	k = 10000;
	i = 30000 + k;
	return (0);
}
```
Nella **conversione** da reali a binario si **perdono cifre significative** e alcuni **numeri finiti possono diventare periodici**. - **approssimazione**
```cpp
#include <stdio.h> 
	int main(void) { 
	float k; 
	k = 5.6F; 
	k = k - 5.59F; 
	return (0); 
}
```
**Caratteri e stringhe**
**ASCII**
convenzione che **associa numeri a ogni carattere**
mettono le lettere in ordine alfabetico per poter **utilizzare gli stessi operatori per mettere in ordine lettere e numeri.**
## Operatori
### Operatori di calcolo
![[04-TipiDato.pdf#page=23&rect=155,156,558,390|04-TipiDato, p.23|300]]
**modulo = resto**
**Ordine di valutazione degli operandi** è stabilito dal compilatore
Attenzione:
```cpp
x = 3;
(x=5)+3; // NON SCRIVERE MAI
```
Nella **divisione** avviene un semantic overloading ma a/b è fra interi se sia a sia b sono interi, è fra reali in tutti gli altri casi. 
La **conversione** di tipo può essere fatta **automaticamente** dal compilatore o **manualmente** dal programma - Typecasting (cast)
Quando effettuata per troncamento possibile perdita di informazioni
```cpp
i = (int) sqrt(384); //( <tipo> ) <espressione>
```
### Operatori relazionali
![[04-TipiDato.pdf#page=36&rect=180,72,503,294|04-TipiDato, p.36||300]]
 > [!attention]
> = **assegna** un valore 
> == **chiede al computer se l'uguaglianza è vera** restituisce 1 o 0
### Operatori logici
![[04-TipiDato.pdf#page=38&rect=145,272,594,398|04-TipiDato, p.38||350]]
## Warnings e errori
Gli errori riguardano problemi sintattici nel codice
I warnings avvisano il programmatore di un possibile comportamento inaspettato
- **nessuna** rilevanza
- **rilevanti** come perdita di dati e approssimazioni per troncamento
> [!caution]
> Controllare tutti i Warning (per visualizzarli tutti bisogna fare **Rebuild project**)
## Priorità e associatività
l'**ordine di esecuzione delle operazioni** in una espressione è stabilito 
![[Microsoft PowerPoint - 04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni.pdf#page=4&rect=173,175,662,451|Microsoft PowerPoint - 04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni, p.4|350]]
![[Microsoft PowerPoint - 04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni.pdf#page=5&rect=167,105,656,432|Microsoft PowerPoint - 04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni, p.5|350]]
## Input e Output
da fare
## Istruzioni
esprimono azioni che modificano lo stato interno 
le **strutture di controllo aggregano funzioni semplici in funzioni complesse** e possono essere
- composte sequenziali (Blocco)
- condizionali (selezione)
- iterazione (ciclo)
### Blocco
![[07a-Istruzioninew.pdf#page=6&rect=30,240,464,404|07a-Istruzioninew, p.6|250]]
Posso **innestare** più blocchi
Tutte le istruzioni contenute in un blocco vengono eseguite **dall'alto al basso**

**Regole di visibilità**
Definiscono l'**area di validità** di un identificatore.
Una variabile dichiarata in un blocco è **visibile** soltanto all'**interno** di esso (**scope**) 
esistono diversi ambienti es. globale, main, funzione, blocco)
- In ambienti diversi si può definire lo stesso identificatore per denotare due oggetti diversi
- In ciascun ambiente un identificatore può essere definito una sola volta
- Se in un ambiente sono visibili due definizioni dello stesso identificatore, la definizione valida è quella dell’ambiente più vicino al punto di utilizzo

```cpp
int main () {
//sono un blocco :)
}
```

**Selezione**
- **Condizionale ternaria**
```cpp
(codizione ? se vero : se falso);
```
- **If else**
```cpp
if (n > 0) { /* inizio blocco */ 
	a = b + 5; 
	c = a;
}              /* fine blocco */ 
 else n = b;
```
- **Espressione di scelta multipla**
![[07a-Istruzioninew.pdf#page=23&rect=401,57,660,395|07a-Istruzioninew, p.23|250]]
```cpp
switch (variabile) {
	case n: conseguenza; break;
	case m: conseguenza2; break;
	// blabla
	default: conseguenza3;
}
```
Le **istruzioni non si escludono** a vicenda quindi devo inserire il **break**