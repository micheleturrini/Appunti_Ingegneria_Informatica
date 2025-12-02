## Caratteristiche principali
Il linguaggio **C** (creato da Dennis Ritchie, 1972) è:
- **Efficientissimo** → tradotto in linguaggio macchina quasi diretto.
- **Sequenziale** → l’ordine delle operazioni conta.
- **Imperativo** → i programmi sono composti da istruzioni che modificano lo stato del calcolatore.
- **Strutturato a blocchi** → il codice è suddiviso in funzioni e blocchi racchiusi tra `{ }`.
- **Basato su espressioni** → ogni istruzione è spesso un’espressione che produce un valore.
- Essendo il codice salvato in memoria posso modificarlo (esclusivo per c e assembler)
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
### Array
**collezione** finita di n variabili dello **stesso tipo**
sono **sempre numerati a partire da 0**
La dimensione è una **costante** (il computer alloca in precedenza da memoria necessaria)
```cpp
<tipo><nome>[ <espr-costante-postiva> ];
```
> [!attention]
> è possibile **accedere** a celle di memoria **non appartenenti** all'array
>  - casco in una **cella inesistente**
>  - casco in una cella non appartenente al programma (**access violation**)
>  - casco all'interno del programma --> pericolo
```cpp
#define DIM 4

int v[DIM] = {43,12,7,86};
```

Teoricamente se inserisco meno valori quelli non dichiarati vanno a 0 e se ne metto troppi gli ultimi vengono ignorati ma non è detto.

Gli array si utilizzano **elemento per elemento**
```cpp
int F[25], frequenza[25]; F = frequenza; /* NO */

for (i=0; i<25; i++){
	F[i] = frequenza[i];
}
```
In C un **array è in realtà un puntatore** che **punta a un’area di memoria pre-allocata**, di dimensione prefissata
![[12a-ArrayStringhe.pdf#page=29&rect=119,185,570,274|12a-ArrayStringhe, p.29|300]]
Il nome dell’array è un **sinonimo per il suo indirizzo iniziale**
![[12a-ArrayStringhe.pdf#page=29&rect=438,75,673,104|12a-ArrayStringhe, p.29|150]]
**I valori vengono passati agli array per riferimento** quindi non è possibile
- assegnare un array a un altro array
- che una funzione restituisca un array
- passare un array come parametro a una funzione non significa affatto passare l’intero array
si può adottare direttamente la **notazione a puntatori** 
![[12a-ArrayStringhe.pdf#page=36&rect=275,83,448,117|12a-ArrayStringhe, p.36|100]]
![[12a-ArrayStringhe.pdf#page=37&rect=235,237,487,356|12a-ArrayStringhe, p.37|150]]
Oltre alle operazioni di somma e sottrazione di una costante si possono fare **Assegnamento e differenza fra puntatori**
```cpp
int *p,*q;
p=q; p-q; p=p-q;
```
Le altre operazioni NON sono consentite
Esempio
```cpp
double a[2], *p, *q;
p=a;
q=p+1; /* q =&a[1] */
printf(“%d\n”, q-p);/* stampa 1 */
printf(“%d\n”, (int) q - (int) p); /* stampa 8 */
```

Esempio Massimo di un array
```cpp
int findMax(int v[], int dim) {
	int i, max;
	for (max=v[0], i=1; i<dim; i++){
		if (v[i]>max) max=v[i];
	}
	return max;
}
```
> [!important]
> per passare un array a una funzione devo **passare nome e dimensione e implementare in ciclo all'interno dell'array**
#### Array di puntatori
Posso riempire un array con qualsiasi elemento tra cui i puntatori.
I vari puntatori sono memorizzati in celle contigue. Possono invece non essere contigue le celle che loro puntano
```cpp
char * mesi[] = {“Gennaio”,”Febbraio”, ”Marzo”,”Aprile”,”Maggio”,”Giugno”, ”Luglio”,”Agosto”, ”Settembre”,”Ottobre”, ”Novembre”,”Dicembre”};
```
![[14-Matricishort.pdf#page=16&rect=137,40,574,414|14-Matricishort, p.16|300]]
```cpp
char * stringhe[4]; //definisce un vettore di 4 puntatori a carattere

int *d[10];
```
Uno dei vantaggi offerti dall’**uso di vettori di puntatori** consiste nel fatto che si possono **realizzare righe di lunghezza variabile**
### Matrici
Le matrici in c sono **array di array**.
Le matrici in memoria sono rappresentate tramite **linearizzazione** per righe (le righe vengono scritte una dopo l'altra)
```cpp
<tipo> <identificatore>[dim1][dim2]…[dimn]

float M[20][30];
int Q[20][30][40][40];
```
![[14-Matricishort.pdf#page=1&rect=491,34,712,236|14-Matricishort, p.1|150]]
Per accedere all’ elemento che si trova nella **riga i e nella colonna j** si utilizza la notazione
```cpp
M[i][j]
Elemento: *(&M[0][0] + 30*i+j)
```
![[14-Matricishort.pdf#page=3&rect=13,299,706,341|14-Matricishort, p.3]]
Posso definire il tipo di vettore multidimensionale
```cpp
typedef <tipo> <ident>[dim1][dim2]…[dimn]

typedef float MatReali [20][30];
MatReali Mat;
```
Per inizializzare la matrice mi comporto come in un array (una **riga** alla volta)
```cpp
int matrix[4][4]={{1,0,0,0},{0,1,0,0}, {0,0,1,0}, {0,0,0,1}}
```
Per passare una matrice a una funzione
```cpp
f(float par[20][30],…);
```
> [!attention]
> Come negli array è responsabilità del programmatore **controllare di non accedere a zone di memoria non appartenenti alla matrice**.
### Stringhe
Una stringa di caratteri in C è **un array di caratteri terminato dal carattere '\0’** (codificato come 0 in codice ascii)
![[12a-ArrayStringhe.pdf#page=18&rect=180,207,539,313|12a-ArrayStringhe, p.18|200]]
*Un vettore di N caratteri può dunque ospitare stringhe lunghe al più N-1 caratteri, perché una cella è destinata al terminatore '\0'*
Un array di N caratteri può essere usato per **memorizzare anche stringhe più corte di N-1**
```cpp
char s[4] = {'a', 'p', 'e', '\0'};
char s[4] = "ape"

// forma esplicita
char str[4]; int i;
for (i=0; i < 3; i++){
	scanf(“%c”, &str[i]);
	str[3] = ‘\0’
}

// forma comoda
char str[4] ;
scanf(“%s”, str); //non c'è l'operatore &
```
> [!attention]
> Nella **scanf** lo spazio è un **separatore e conclude la stringa**

Esempio ordinamento stringhe
```cpp
int main() {
	char s1[] = ”Maria";
	char s2[] = ”Marta";
	int i=0, risultato;
	while (s1[i]!='\0' && s2[i]!='\0' && s1[i]==s2[i]){
	i++;
	risultato = s1[i]-s2[i]; 
	……
}
```
![[12a-ArrayStringhe.pdf#page=24&rect=395,32,691,125|12a-ArrayStringhe, p.24|200]]

Esempio copia di una stringa
```cpp
int main() {
	char s[] = "Nel mezzo del cammin di";
	char s2[40]; // dimensione larga per sicurezza
	int i=0;
	for (i=0; s[i]!='\0';i++){
		s2[i] = s[i];
		s2[i] = '\0'; // inserimento del terminatore
	}
}

void strcpy(char dest[], char source[]) {
	while (*source) {
		*(dest++) = *(source++);
	}
	*dest = '\0';
}

// scritture complicate
while (*source) {......} // *source restituisce un valore diverso da 0 pino a che non arrivo al carattere terminatore e quindi il while prosegue
```
*La seconda versione è causa di gravi problemi di sicurezza (se tento di copiare una stringa troppo lunga inizio a sovrascrivere la memoria del pc)*
#### Libreria string.h
String **length**
```cpp
int strlen(char* str);
```
restituisce la lunghezza della stringa (non conta il carattere terminatore).

String **compare**
```cpp
int strcmp(char* str1, char* str2);
```
- La funzione restituisce un valore
	- **<0** se str1 **precede** lessicograficamente str2
	- **>0** se str1 **segue** lessicograficamente str2
	- **=0** se str1 e str2 sono **identiche**

String **cat** 
```cpp
char* strcat(char* dest, char* src)
```
La funzione **appende** la stringa src in coda alla stringa dest (se dest le può contenere entrambe)

String **Search**
```cpp
char* strchr(char* str, char car);
```
La funzione **ricerca il carattere car all’interno della stringa** str (compreso il terminatore) e restituisce
- La posizione della prima occorrenza del carattere nella stringa
- NULL se il carattere non viene trovato

String **str**
```cpp
char* strstr(char* str, char* sub);
```
La funzione **ricerca la stringa sub all’interno della stringa** str e restituisce
- La posizione della prima occorrenza della stringa sub
- NULL se la stringa non viene trovata
### Strutture
sono necessarie per rappresentare **strutture di dati complesse** presenti nella mente umana.
![[12d-Strutture_251111_093346.pdf#page=1&rect=155,185,705,370|12d-Strutture_251111_093346, p.1|400]]
```cpp
struct [<etichetta>] {
	{ <definizione-di-variabile> }
} <nomeVariabile> ;
```
**Definizione**
```cpp
struct persona {
	char nome[20];
	int eta;
	float stipendio;
} pers ;

struct punto {
	int x, y;
} p1, p2 ; // definisco due punti distinti secondo la struttura punto

struct linea { //posso innestare le strutture
	struct punto coord1;
	struct punto coord2;
} l1;
```
Persona è solo un' etichetta, è **opzionale** e serve per dichiarare altre variabili dello stesso tipo

**Accesso valori**
```cpp
p1.x = 10; p1.y = 20;
p2.x = -1; p2.y = 12;

nomeVariabileStruct.NomeCampo = ....
```
> [!important]
> Vengono utilizzate come normalissime variabili

esempio
```cpp
int main(){
	struct frutto {
		char nome[20];
		int peso;
		} f1 = {"mela", 70};
	struct frutto f2 = {"arancio", 50};
	int peso = f1.peso + f2.peso;
} 
```
L’indirizzo di memoria di una variabile di tipo struct coincide con l’indirizzo di memoria del suo primo membro.
Possiamo verificare la dimensione in byte di una struttura utilizzando l’operatore **sizeof**.

A **differenza** di quanto accade con gli **array**, **il nome della struttura rappresenta la struttura nel suo complesso**. (l'intera struttura viene passata per copia - **tutti i campi vengono copiati, uno per uno**)
Creando una **struttura** che **contiene un array** posso **passarlo** interamente **per copia**
```cpp
int main(){
	struct string20 {
	char s[20];
	}
	s1 = {"Paolino Paperino" }, s2 = {"Gastone Paperone" };
	s1 = s2; /* FUNZIONA! */
}
```
#### Tipi definiti dall'utente
In C l’utente può introdurre nuovi tipi tramite una definizione di tipo
- aumenta la leggibilità del programma
- consente di ragionare per astrazioni
E' possibile:
- **ridefinire** tipi esistenti
```cpp
typedef TipoEsistente NuovoTipo;

//esempio
typedef int MioIntero;
MioIntero X,Y,Z;
int W;
```
- definire **tipi strutturati**
Ciò consente di **non dover più ripetere la definizione** per esteso ogni volta che si definisce una nuova variabile
```cpp
typedef struct {... } Persona;
persona p1, p2; /* due strutture “persona” */
```
I tipi personalizzati si scrivono pero convenzione con la Maiuscola (rappresentano la categoria -> un concetto più ampio)

> [!attention]
> typedef **cambia la semantica nelle dichiarazioni/definizioni**
```cpp
//time risulta una variabile se
struct { int hour, minute, second; } time ;
// time risulta un tipo di dato se
typedef { int hour, minute, second; } time ;
```

E' possibile **innestare** le strutture

Per dichiarare struttura e tipo insieme:
```cpp
typedef struct addressStruct {
	char street[80];
	char postalCode[8];
	char city[30];
	char state[20];
} Address;
```
#### Tipi enumerativi
Un tipo enumerativo è un tipo di dato che consiste in un **insieme ristretto di valori.**
La loro funzione è semplicemente quella di rendere più leggibile il codice.
```cpp
typedef enum { lu, ma, me, gi, ve, sa, dom} Giorni;

Giorni Giorno = dom;
if (Giorno == dom) /* giorno festivo */
else /* giorno feriale */
```
Gli indici (che non vedo mai) vengono assegnati come in un array
```cpp
g = 5; /* equivale a g = sa */
```
SCONSIGLIATO!
### Modificatori
- **`short` → almeno 16 bit**
- **`long` → almeno 32 bit**
- **`signed` → con segno (positivi + negativi)**
- **`unsigned` → senza segno (solo positivi)**

**`sizeof(long int) >= sizeof(int) >= sizeof(short int)`**
**I booleani non esistono (Convenzione: `0 = falso`, valore ≠ 0 = vero.)**
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
![[04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni.pdf#page=4&rect=173,175,662,451|Microsoft PowerPoint - 04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni, p.4|350]]
![[04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni.pdf#page=5&rect=167,105,656,432|Microsoft PowerPoint - 04b-Espressioni.ppt - Compatibility Mode - 04b-Espressioni, p.5|350]]
## Input e Output
non ci sono strumenti di default
**devo includere stdio.h**

**Input**
```cpp
scanf(<stringa-formato>, <sequenza-variabili>);

//esempio
int X;
float Y;
scanf("%d%f", &X, &Y);
```
Scanf legge una serie di valore in base alle informazioni nella **stringa formato** e memorizza nelle variabili i valori letti.
- restituisce il **numero di valori letti e memorizzati**, oppure il codice EOF in caso di end of file
- gli identificatori sono sempre preceduti da **&**
- la <stringa_formato> può contenere dei **caratteri qualsiasi (scartati durante la lettura)**, che si prevede vengano **immessi dall’esterno** insieme ai dati da leggere
```cpp
scanf("%d:%d:%d", &a, &b, &c);
```
richiede che i tre dati vengano immessi separati dal carattere “:”

Per ragioni di **sicurezza** la scanf è deprecata
```cpp
// per usarla aggiungo all'inizio
#define _CRT_SECURE_NO_DEPRECATE
//oppure posso usare:
scanf_s("%c %c", a, 1, b, 4)
```
la scanfs ( solo per i caratteri char devo **specificare il numero di caratteri accettati**)

**Output**
```cpp
printf(<stringa-formato>,<sequenza-elementi>);

//esempio
printf("Quadrato di %d: %d", k, k*k);
```
restituisce il numero di caratteri scritti

**Stringa di formato** (descrive esattamente quello che deve esserci in input)
- se legge un formato **diverso da %c**, **ignora** i caratteri separatori (spazi, tab, invio, etc.)
- se legge un formato **%c**, allora **restituisce anche** i caratteri separatori

**Formati e caratteri**
![[06a-Input-Outputnew.pdf#page=9&rect=33,71,508,417|06a-Input-Outputnew, p.9|300]]
esempio
```cpp
char Nome='F';
char Cognome='R';
printf("%s\n%c. %c. \n\n%s\n", "Programma scritto da:", Nome, Cognome,"Fine");

//output
Programma scritto da:
F. R.

Fine
```

> [!attention]
> L' I/O è **bufferizzato**: i caratteri letti da tastiera sono **memorizzati in un buffer**.
> 	In architetture Windows, il tasto di INVIO corrisponde a 2 (**DUE**!) caratteri (CR LF): il primo è interpretato come separatore, ma il **secondo rimane nel buffer ed è preso come carattere inserito dall’utente (solo da file).**
> 	
```cpp
scanf("%*c"); /* letto e buttato via */
```

**getchar() e putchar()**
![[06a-Input-Outputnew.pdf#page=35&rect=56,33,662,469|06a-Input-Outputnew, p.35|300]]
## Istruzioni
esprimono azioni che modificano lo stato interno 
le **strutture di controllo aggregano funzioni semplici in funzioni complesse** e possono essere
- composte sequenziali (Blocco)
- condizionali (selezione)******
- iterazione (ciclo)
### Blocco
![[07a-Istruzioninew.pdf#page=6&rect=30,240,464,404|07a-Istruzioninew, p.6|250]]
Posso **innestare** più blocchi
Tutte le istruzioni contenute in un blocco vengono eseguite **dall'alto al basso**

**Regole di visibilità**
Definiscono l'**area di validità** di un identificatore.
Una variabile dichiarata in un blocco è **visibile** soltanto all'**interno** di esso (**scope**) 
esistono diversi ambienti es. globale, main, funzione, blocco)
- Un identificatore NON è visibile **prima** della sua dichiarazione 
- Un identificatore definito in un ambiente è visibile in tutti gli ambienti in esso **contenuti**
- Se in un ambiente sono visibili due definizioni dello stesso identificatore, la definizione valida è quella dell’ambiente **più vicino** al punto di utilizzo 
- In ambienti diversi si può definire lo **stesso identificatore** per denotare due oggetti diversi
- In ciascun ambiente un identificatore può essere **definito una sola volta**

```cpp
int main () {
//sono un blocco :)
}
```
### Selezione
- **Condizionale ternaria**
```cpp
(codizione ? se vero : se falso);
```
- **If else**
```cpp
if (n > 0) {
	a = b + 5; 
	c = a;
}
else if (condizione){
	azione;
}
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

**Valutazione in corto circuito** 
Se il programma deve valutare una sequenza di condizioni in presenza di un **||**  appena ne trova una **vera non valuta le seguenti** e esce dall' if.
Se il programma deve valutare una sequenza di condizioni in presenza di un **&&**  appena ne trova una **falsa non valuta le seguenti** e esce dall' if.
(se le seguenti condizioni includono delle operazioni queste non vengono eseguite)

AGGIUNGERE CODICE E SPOSTARE DOVE HA SENSO; AGGIUNGERE C
### Ciclo
**Eseguono** un blocco di codice **fino a quando una condizione non si verifica**
hanno u**n solo punto di ingresso e un solo punto di uscita**
Esistono tre modi in C (quasi equivalenti):
- **While**
testa la condizione, se è vera esegue il blocco
![[07a-Istruzioninew.pdf#page=27&rect=74,64,310,319|07a-Istruzioninew, p.27|200]]
```cpp
<while> ::= 
	while(<condizione>) <istruzione>
```
Esempio
```cpp
#include <stdio.h> 
int main() /* Media di N voti*/ {
	int sum,voto,N,i;
    float media;
    printf(“Quanti sono i voti?”);
    scanf("%d",&N);
    sum = 0;
    i = 1;
    while (i <= N) {
		printf(“Dammi il voto n.%d:”,i);
        scanf("%d",&voto);
        sum=sum+voto;
        i=i+1;
    } 
	media=((float)sum)/N;/* ipotesi: N>0 */ 
	printf("Risultato: %f",media)
}
```
Nella linea 15 il casting viene eseguito prima della divisione quindi si salvano i decimali.
Per evitare errori è necessario filtrare i dati forniti dall'utonto. (es 0)
- **Do-while**
esegue il blocco poi verifica la condizione la prima volta
![[07a-Istruzioninew.pdf#page=32&rect=65,109,292,320|07a-Istruzioninew, p.32|200]]
```cpp
<do-while> ::=
	do <istruzione> while(<condizione>);
```
Esempio
```cpp
do {
	F = I*F;
	I = I+1;
} while (I <= N);
```
Programma che legge un una serie di valori fino a un simbolo
![[07a-Istruzioninew.pdf#page=35&rect=92,249,367,326|07a-Istruzioninew, p.35|250]]
- **For**
Permette di iterare un determinato numero di volte
![[07a-Istruzioninew.pdf#page=38&rect=39,30,282,324|07a-Istruzioninew, p.38|200]]
```cpp
<for> ::=
	for(<espr-iniz>;<cond>;<espr-modifica>) 
	<istruzione>
```
**espr-iniz** viene eseguita solo prima di iniziare l'iterazione
verifica la **condizione**
viene eseguita l'**istruzione**
**espr-modifica** viene eseguita a ogni iterazione **dopo l'istruzione**

![[07a-Istruzioninew.pdf#page=39&rect=53,191,620,432|07a-Istruzioninew, p.39|400]]
Esempio
```cpp
for(i=1; i<=N; i++) {
	printf(“Dammi il voto n.%d:”,i);
	scanf("%d",&voto);
	sum=sum+voto;
}
```

**Counter**
![[07a-Istruzioninew.pdf#page=50&rect=37,74,653,429|07a-Istruzioninew, p.50|400]]
*Il loro comportamento può variare molto in base a piccole sottigliezze quindi CONTROLLARE  in debug*
## Buona programmazione
### Efficienza
(in questo corso irrilevante)
determinazione di un algoritmo che consumi poche risorse
### Modularità
irrilevante in programmi piccoli
Si divide il macroproblema in sottoproblemi e si integrano le sottoparti.
### Ordine
Facile comprensione del codice da parte di chi non l’ha scritto ma vorrà manutenerlo
### Camel Casing
```cpp
rowIndex, columnIndex, colorConverter… 
void swapValues(int &firstValue, int &secondValue);
void saveToFile(int buffer[], int bufferSize);
```
## Preprocessore
![[07c-Preprocessore.pdf#page=1&rect=71,76,791,204|07c-Preprocessore, p.1]]
- Agisce prima del compilatore e processa direttamente il file sorgente
- Esegue direttive possibilmente contenute nel file sorgente
- **Sostituisce soltanto testo con testo**

**Direttive**
- **define**
  definisce una **regola di ricerca e sostituzione**: ogni occorrenza di Identificatore verrà sostituita da testo
```cpp
#define Identificatore_testo corrispondente
#define BOOLEAN int //esempio

//PRIMA DEL PROCESSING
#define RADICEDI2 1.4142F
int main() { 
	float lato = 18;
	float diagonale = lato * RADICEDI2;
}
// dopo il processing
int main() { 
	float lato = 18;
	float diagonale = lato * 1.4142F;
}
```
- **include**
  **include il contenuto del file specificato** esattamente nella posizione in cui si trova la direttiva stessa
```cpp
#include <libreria.h> // cerca nelle librerie di sistema
#include “miofile.h”  // cerca nella cartella del programma
```
- **ifndef**
	in laboratorio
## Funzioni
![[08-Funzioni.pdf#page=5&rect=245,282,575,449|08-Funzioni, p.5|300]]
**Riceve una o più variabili** quando invocata e **produce un risultato**.
L'**interfaccia** di una funzione comprende
- **nome** della funzione 
- lista dei **parametri** (ci metteremo anche il nome)
- **tipo del valore** da essa **denotato**

esempio **servitore**
```cpp
int max (int x, int y ){
	if (x>y) return x ;
		else return y;
}
```
• Il **simbolo max denota il nome** della funzione 
• Le variabili intere **x e y sono i parametri** della funzione
• Il **valore restituito** è di tipo intero **int**

esempio **cliente**
```cpp
main(){
	int z = 8;
	int m;
	m = max(z , 4);
}
```
**Definizione di una funzione**
```cpp
<definizione-di-funzione> ::= <tipoValore> <nome>(<parametri-formali>)
{ <corpo>
}

<corpo> ::= <blablacodice> return<risultato>
```
- **parametri-formali**
	- o una lista vuota: **void**
	- o una **lista di variabili** (ciascuna con il proprio tipo e separate da virgole) visibili solo entro il corpo della funzione
- **tipoValore**
	- deve coincidere con il tipo del valore restituito dalla funzione
	- se non indicato esplicitamente, si **sottintende int** (mai non dichiarare)
	- se non si desidera valore di ritorno, **void** (vedi procedure)
- **corpo**
	- possono essere presenti **definizioni e/o dichiarazioni** **locali** (visibilità limitata al blocco della funzione) (parte def./dich.) e un insieme di **istruzioni** (parte istruzioni)
	- all'interno del corpo **i parametri formali sono trattati come variabili**
	- Non è possibile **definire** una funzione all’interno di un’altra (ma si può **dichiarare**)
	- **return (risultato)** definisce il risultato della funzione. (anche **il main ritorna 0 se tutto è andato** **bene** oppure altri numeri che indicano cosa è andato storto)
> [!attention]
> eventuali istruzioni **successive** alla return **non saranno mai eseguite**
   *quando possibile mettere una sola return in fondo* 

**Chiamata di funzione**
```cpp
<nomefunzione> ( <parametri-attuali> )

<parametri-attuali> ::= [ <espressione> ] { , <espressione> }
```

**Passaggio dei parametri**
- **per valore o copia** (by value) si fa una copia e se il servitore fa errori non perdo l'originale (protegge ma limita alcune possibilità)
- (**per riferimento** (by reference) il servitore avendo un riferimento può agire sull'originale.)
**In C esiste solo il passaggio per copia** --> il servitore può fornire risultati solo attraverso il return
questa soluzione per dati voluminosi è lenta
### La ricorsione
Una funzione al suo interno può **invocare se stessa**.
> [!important]
> Qualunque **algoritmo iterativo (while..) può essere scritto con un algoritmo ricorsivo.**
> il ricorsivo è più complesso

```cpp
int funzione(int n){
	if (caso base){
		return risMin;
	} else {
		return operazione con funzione(n-1);
	}
}
```
La funzione **chiama una serie di funzioni innestate** fino a che  non si verifica il **caso base**.
Quando il caso base si verifica iniziano una **serie di return che calcolano il valore**.

Negli esempi visti finora **si inizia a sintetizzare il risultato SOLO DOPO che si sono aperte tutte le chiamate**, “a ritroso”, mentre le chiamate si chiudono.
Le chiamate ricorsive **decompongono** via via il problema, ma **non calcolano nulla**
Il **risultato** viene **sintetizzato** a partire dalla **fine**, perché prima occorre arrivare al caso “**banale**”: 
il caso “banale” fornisce il valore di partenza.

Ricorsione **lineare** - fattoriale
```cpp
int fact(int n) {
	if (n<=0) return 1;
	else return n*fact(n-1);
}
```
![[09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA.pdf#page=9&rect=97,251,742,456|Microsoft PowerPoint - 09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA, p.9|400]]

Ricorsione **bilineare** - successione di Fibonacci
ricorre su due rami (n-1 e n-2)
```cpp
unsigned fibonacci(unsigned n) {
	if (n<2) return n;
	else return fibonacci(n-1)+fibonacci(n-2);
}
```
![[09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA.pdf#page=15&rect=90,99,657,434|Microsoft PowerPoint - 09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA, p.15|400]]

### Struttura
**Modello RUN-TIME**
quando una funzione viene invocata
- si crea una nuova attivazione (istanza) del servitore
- viene allocata la memoria
- passaggio dei parametri
- si trasferisce il controllo al servitore
- il codice esegue

**Record di attivazione**
È il “**mondo della funzione**”: contiene tutto ciò che ne caratterizza l’esistenza
- i **parametri** ricevuti
- le **variabili** locali 
- l’indirizzo di ritorno (**Return Address** RA) indica il punto a cui tornare (nel codice del cliente) al  termine della funzione, per permettere al cliente di proseguire
- **collegamento** fra record di attivazione e cliente (**Dynamic Link** DL)
![[09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA.pdf#page=24&rect=228,102,620,409|Microsoft PowerPoint - 09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA, p.24|300]]

In caso di funzioni innestate **i record si accumulano secondo una logica LIFO**
![[09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA.pdf#page=27&rect=108,43,707,203|Microsoft PowerPoint - 09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA, p.27|400]]

record di una **funzione ricorsiva**
![[09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA.pdf#page=34&rect=132,113,447,425|Microsoft PowerPoint - 09a-ricorsioneRA.ppt - Compatibility Mode - 09a-ricorsioneRA, p.34|200]]


**Ricorsiva Tail**
E' una funzione in cui le cui **chiamate ricorsive** sono **l'ultima operazione eseguita** dalla funzione stessa

Esempio MCD
```cpp
int mcd(int m, int n) {
if(m == n)
	return m;
else return (m > n) ? mcd(m-n, n) : mcd(m, n-m);
}
```
### Procedure
Funzioni che non restituiscono un valore

```cpp
void p(int x) { x = x * 2; printf(“%d”, x); }
```
## I puntatori
Sono un sistema per avere un passaggio per **riferimento** senza implementarlo.

Prendendo come esempio lo swap la funzione con passaggio per copia **scambierebbe le copie e non i valori originali**

Se il cliente **passa per copia gli indirizzi delle celle contenenti i valori** e non i valori stessi ottengo gli **stessi vantaggi** del passaggio per **riferimento**
![[10a-Procedure.ppt - Compatibility Mode - 10a-Procedure.pdf#page=13&rect=162,83,757,325|10a-Procedure.ppt - Compatibility Mode - 10a-Procedure, p.13|400]]
E' necessario un operatore apposito che **prende in ingresso il nome della variabile** e **restituisce l'indirizzo** e un operatore che **interpreta l'indirizzo** come tale e **legge il valore corrispondente**
- operatore **estrazione di indirizzo** &
- operatore di **dereferenziamento** *
![[10a-Procedure.ppt - Compatibility Mode - 10a-Procedure.pdf#page=17&rect=116,278,647,426|10a-Procedure.ppt - Compatibility Mode - 10a-Procedure, p.17|300]]
![[10a-Procedure.ppt - Compatibility Mode - 10a-Procedure.pdf#page=17&rect=117,110,646,224|10a-Procedure.ppt - Compatibility Mode - 10a-Procedure, p.17|300]]

**Il tipo puntatore** a **T**  è un tipo che denota l’indirizzo di memoria di una variabile di tipo T
Un **puntatore a T** è una variabile di “tipo puntatore a T” destinata a  contenere l’indirizzo di una variabile di tipo T
Il tipo del puntatore indica il tipo del dato contenuto nell’indirizzo di memoria puntato.
La funzione non dovrebbe **MAI alterare il valore del puntatore**
```cpp
<tipo> * <nomevariabile> ;

int *p;
int* p;
int * p;
int * p = NULL; //punta a una zona di memoria non accessibile (equivale allo 0)
```

Esempio swap con puntatori
```cpp
void scambia(int* a, int* b) { //ricevo due indirizzi
	int t; //variabile temporanea
	t = *a; //eseguo l'operazione di scambio fra i valori
	a = *b;
	*b = t;
}
int main(){
	int y=5, x=33;
	scambia(&x, &y); //fornisco due indirizzi
}
```
![[10a-Procedure.ppt - Compatibility Mode - 10a-Procedure.pdf#page=23&rect=95,201,639,353|10a-Procedure.ppt - Compatibility Mode - 10a-Procedure, p.23|400]]

## Suddivisione si più file
'E comodo e "obbligatorio" dividere i progetti in più file dichiarando le funzioni in un file diverso dal main.
Sono necessari tre elementi:
- **Main**.c
```cpp
# include "Funzione.h"

int main() {
	chiamata funzione
}
```
- **funzione**.h (contiene le dichiarazioni) -header
```cpp
int funzione (dichiarazione ingressi);
```
- **Funzione**.c (contiene la funzione)
```cpp
int funzione (dichiarazione ingressi){
	lavoro funzione
	return;
}
```
## Librerie standard
| Uso                             | Libreria    |
| ------------------------------- | ----------- |
| input/output                    | **stdio.h** |
| funzioni matematiche            | math.h      |
| gestione di stringhe            | string.h    |
| operazioni su caratteri         | ctype.h     |
| gestione dinamica della memoria | stdlib.h    |
| ricerca e ordinamento           | stdlib.h    |
### Stdio.h
Esistono 3 canali standard (**stream**)
- **stdin** (collegato alla tastiera)
- **stdout** (collegato alla consolle)
- **stderr** (collegato alla console - per gli errori)
sui canali I/O fluiscono **sequenze di caratteri** terminate dal carattere **End-Of-File** che non è contenuto nel file.

```cpp
putchar(ch); // scrive 1 carattere
ch = getchar(); // legge un carattere
```

**printf** e **scanf** sono molto più pratiche.
## Files
Un file è un’astrazione di memorizzazione di dimensione potenzialmente illimitata ad **accesso sequenziale**
A livello di sistema operativo **un file è denotato univocamente dal suo nome assoluto,** che comprende il **percorso** e il **nome relativo**
```
Windows
	C:\temp\prova1.c
Linux
	/usr/temp/prova1.c
```

Esistono 2 tipi di file
- File **BINARI** (sono sequenze ordinate di bytes)
- File di **TESTO** (sono sequenze ordinate di caratteri organizzati in righe)
I file **binari basterebbero** ma l'uomo ragiona in caratteri

Per utilizzare un file è necessario **associare l'indirizzo al nome di una variabile**.
```cpp
#include <stdio.h>

FILE * prova;
prova = fopen("indirizzo/nome", "modo");
```
Il valore restituito da `fopen()` è un puntatore a FILE
- **NULL** in caso l’apertura sia fallita
- controllarlo è il solo modo per sapere se il file si sia davvero aperto
-  se non si è aperto, il programma usualmente non deve proseguire -> funzione `exit()`

| Keyletter | Mode                                     |
| --------- | ---------------------------------------- |
| r         | apertura in **lettura** (read)           |
| w         | apertura in **scrittura** (write)        |
| a         | apertura in **aggiunta** (append)        |
| t         | apertura in modalità **testo** (default) |
| b         | apertura in modalità **binaria**         |
| +         | apertura con possibilità di **modifica** |

> [!attention]
> Al termine del programma **tutti i file aperti devono essere chiusi**
```cpp
fclose(prova);
```
Il valore restituito da `fclose()` è un intero
- 0 se tutto è andato bene
- EOF (valore intero negativo) in caso di errore
Prima della chiusura **tutti i buffer vengono svuotati**

Per capire se il file è finito provo a leggere e **se la lettura fallisce sono in fondo**.
Se fallisce restituisce **EOF** (non è un carattere ma un **codice di errore**)

![[16a-filetesto.pdf#page=14&rect=56,230,678,428|16a-filetesto, p.14]]
`getchar()` e `putchar()` sono semplicemente delle scorciatoie linguistiche per `fgetc()` e `fputc()`
![[16a-filetesto.pdf#page=14&rect=127,39,478,85|16a-filetesto, p.14|300]]

esempio
```cpp
N=fscanf(fd, "%d %d", &x, &y);
```
N puo essere:
- **2** sono stati letti (convertiti con successo) due inter
- **1** un solo intero è stato letto con successo
- **0** non è stato possibile leggere nessuno dei due numeri interi
- **EOF** nessun intero è stato letto da file, perchè si è incontrata la fine del file prima ancora di riuscire a leggere il primo intero

esempio compilazione di una struttura
```
Rossi Mario M 1947
Ferretti Paola F 1982
Verdi Marco M 1988
Bolognesi Annarita F 1976
```
```cpp
#define DIM 30
#include <stdio.h>
#include <stdlib.h>

typedef struct { char cognome[31], nome[31], sesso[2]; int anno;} persona;

int main(void) {
	persona v[DIM];
	int k=0;
	FILE* f;ù
	//verifica se il file esiste
	if ((f=fopen("people.txt", "r"))==NULL) {
			perror("Il file non esiste!"); exit(1);
	}
	// legge finchè ci sono caratteri
	while(fscanf(f,"%s%s%s%d\n", v[k].cognome, v[k].nome, v[k].sesso, &(v[k].anno)) != EOF){
		k++;
	}
}
```
Nell'esempio non viene gestita la lunghezza dell'array (rischio di overflow)

Per leggere anche campi che contengono spazi è possibile
- Assegnare un numero definito di caratteri a ogni campo `fscanf(f,"%10c",…)` (occorre aggiungere un terminatore)
### File binari
Un file binario è una **pura sequenza di byte** usabile per memorizzare su file **informazioni di qualsiasi natura**. (snapshot della memoria, immagini, audio, musica).
La **fine del file** è SEMPRE rilevata in base all’**esito** delle operazioni di **lettura**.

```cpp
int fwrite(addr, int dim, int n, FILE *f);
```
- scrive sul file **n** elementi, ognuno grande **dim** byte
- gli elementi da scrivere vengono prelevati dalla memoria a partire dall’indirizzo **addr**
- restituisce il numero di elementi (non di byte) effettivamente scritti
```cpp
int fread(addr, int dim, int n, FILE *f);
```
- legge dal file **n** elementi, ognuno grande dim **byte** (complessivamente, tenta di leggere quindi **n*dim** byte)
- gli elementi da leggere vengono scritti in memoria a partire dall’indirizzo **addr**
- restituisce il numero di elementi (non di byte) effettivamente letti
> [!attention]
> Controllare il valore restituito è il SOLO MODO per sapere che cosa è stato letto, e in particolare per scoprire se il file è terminato.

esempio stampo una serie di caratteri
```cpp
int main(void){
	FILE *fp; char msg[] = "Ah, l'esame\nsi avvicina!";
	if ((fp = fopen("testo.txt","wb"))==NULL){
		exit(1); /* Errore di apertura */}
	fwrite(msg, strlen(msg)+1, 1, fp); //salva anche il terminatore di stringa
	fclose(fp);
}
```

esempio lettura e compilazione CSV
```cpp
int readField(char buffer[], char sep, FILE *f) {
	int i = 0;
	char ch = fgetc(f);
	while (ch != sep && ch != 10 && ch != EOF) {
		buffer[i] = ch;
		i++;
		ch = fgetc(f);
	}
	buffer[i] = '\0';
	return i;
}
```

## Ambiente locale e globale
**Variabili Globali**
Le variabili possono essere dichiarate anche **all'esterno di una funzione** e questo le rende **variabili globali**.
> [!attention]
> Usarle è PERICOLOSO e quindi vanno dichiarate solo se necessario.
> Se tutti possono modificarle **facilmente vengono danneggiate**

Per dichiarare (non definire) una variabile globale uso
```cpp
extern int trentadue;
```
La definisco come una normale variabile.

esempio
```cpp
int ultimoValore = 0;
int prossimoDispari(void){
	return 1 + 2 * ultimoValore++; }
```
```cpp
int prossimoDispari(void);
```

esempio su più file
```cpp
extern int trentadue;// da inserire in tutti i file in cui si usa
float fahrToCelsius(float f) {
	return 5.0/9 * (f-trentadue);
}
```
```cpp
int trentadue = 32;
```

**Variabili Static**
Sono variabili visibile da tutti **solo all'interno del file in cui vengono dichiarate**.
```cpp
static int ultimoValore = 0;
int prossimoDispari(void){
	return 1 + 2 * ultimoValore++;
}
```
## Allocazione della memoria
Esistono aree dati separate nel sistema.
- Area ad **allocazione automatica** -> Riguarda le variabili (non static) con scope locale. La memoria viene automaticamente allocata sullo stack (record di attivazione) e automaticamente deallocata al termine della funzione in cui è dichiarata .
- Area ad **allocazione statica** -> Riguarda le variabili globali e le variabili locali dichiarate static. La memoria viene allocata prima dell’esecuzione del programma e rilasciata al termine.
- Area ad **allocazione dinamica** -> Memoria richiesta a runtime esplicitamente dal programmatore (mediante specifiche funzioni di libreria). Viene allocata dinamicamente nella **memoria heap** a cui si può accedere solo mediante puntatori. Deve anche essere rilasciata esplicitamente.

Dover specificare a priori la dimensione di ogni array è inefficiente.
Sarebbe molto utile poter dimensionare un array dopo aver scoperto quanto grande deve essere

Per chiedere memoria a runtime si usa
```cpp
#include <stdlib.h>

void * malloc(size_t dim);
```
- **dim** = dimensione in byte dell'area da allocare
- restituisce **NULL** se non basta la memoria
- malloc() restituisce un puro indirizzo, ossia un **puntatore “senza tipo"** (devo utilizzare un casting)

```cpp
float *p;
p = (float*) malloc(12);

int *p;
p = (int*) malloc(5*sizeof(int));
```

Per riconsegnare la memoria a runtime
```cpp
void free(void* p);
```


Per semplificare la sintassi inizializzo la funzione
```cpp
#include <stdlib.h>
char* alloca(int n){
	return (char*) malloc(n*sizeof(char));
}
```


L'array non può essere espanso secondo necessita

Genero un nuovo array con la nuova dimensione, copio i valori dal vecchio al nuovo e sostituisco il vecchio

RISCHI
- **Dangling reference**
```cpp
int *p;
p=(int*)malloc(5*sizeof(int));
free(p); p[0] = 13;
p[1] = 18;...
*(p+4) = -20;
```
Il puntatore esiste ancora anche se la memoria non è più allocata (punto a ciarpame)
- **Memory leak** 1
Lenta perdita di memoria
```cpp
a = malloc (...);
b = malloc (...);
a = b;
```
la **memoria** originaria di **a è allocata ma non posso liberarla**.
Corretto
```cpp
a = malloc (...);
b = malloc (...);
free(a);
a = b;

```
- **Memory leak** 2
```cpp
void func(void) {
	void* c = malloc( 50 );
}

int main(void) {
	while (1) func();
}
```
Lentamente tutta la memoria viene allocata e...


## Tipi di dato astratto
definisce una categoria concettuale con le sue proprietà

esempio counter
counter.h
```cpp
typedef unsigned int counter;
void reset(counter*);
void inc(counter*);
```
counter.c
```cpp
#include "counter.h"
void reset(counter *c){ *c=0; }
void inc(counter* c){ (*c)++; }
```

## Liste
Sono strutture dati che permettono di aggiungere elementi liberamente.
Gli elementi all'interno di una lista hanno lo stesso tipo.
![[22-liste.pdf#page=5&rect=40,54,685,301|22-liste, p.5|500]]
Per invocare la head e la tail devo sempre verificare che la lista non sia vuota.
### Rappresentazione ad elementi collegati
La lista è un insieme di **nodi** formati da
- elemento (**payload**)
- **collegamento** al prossimo nodo (arco)
![[22-liste.pdf#page=9&rect=33,42,655,128|22-liste, p.9|500]]
La **sequenzialità** degli elementi della lista **non è più rappresentata mediante l’adiacenza** delle locazioni di memorizzazione

//CAPIRE
```cpp
typedef struct list_element {
	int value;
	struct list_element *next;
} node;
typedef node *list;

int main() {
	list root = NULL, L;
	root = (list) malloc(sizeof(item));
	root->value = 1;
	root->next = NULL;
	L = root;
}
```
Capire structure sharing


esercizio ricerca in una lista
```cpp
int ricerca(int e, list l) {
	int trovato = 0;
	while ((l!=NULL)&&!trovato)
		if (l->value == e) trovato = 1;
		else l = l->next;
	return trovato;
}
```
esercizio



Costruzione di una lista
Sono definite nel file header hce possiamo portare all'esame

Per comodità si definiscono altre funzioni per operare sulle liste
![[22-liste.pdf#page=27&rect=35,65,679,410|22-liste, p.27|500]]

Per usare in modo sicuro la condivisione di strutture, è necessario:
- NON effettuare free() -> uso inefficiente heap in linguaggi privi di garbage collection (evitare rischio di riferimenti pendenti)
- realizzare liste come entità non modificabili: ogni modifica comporta la creazione di nuova lista (evitare rischio di effetti collaterali indesiderati)