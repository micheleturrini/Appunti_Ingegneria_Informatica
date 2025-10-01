[[0 - Index]]

## Esame
**Prova scritta** Esercizi specifici + domanda di teoria
Appelli
- 3 (Gennaio Febbraio)
- 2 (Giugno Luglio)
- 1 (Settembre 26)
## Notazioni
### Insiemi  numerici
N (insieme dei numeri naturali)
Z (insieme dei numeri interi)
Q (insieme nei numeri razionali)
R (insieme numeri reali)
### Insiemi e appartenenza
- **Appartiene**:  
  $x \in A$ → $x$ appartiene all’insieme $A$.  
- **Non appartiene**:  
  $x \notin A$ → $x$ non appartiene all’insieme $A$.  
**Insieme Numerabile**
Un insieme che può essere messo in **corrispondenza biunivoca con i numeri naturali** (gli elementi di un insieme numerabile possono essere enumerati, consentendo la possibilità di elencarli in una sequenza)
R non è numerabile.
### Quantificatori
- **Per ogni**:  
  $\forall$  
- **Tale che**:  
  $\mid$
- **Esiste**:  
  $\exists$
- **Non esiste**:  
  $\nexists$  
- **Esiste unico elemento**:  
  $\exists!$  
### Logica
- **Et (congiunzione)**:  
  $p \land q$.  
- **Vel (disgiunzione)**:  
  $p \lor q$.  
- **Implicazione**:    $p \implies q$.  
  Se l'ipotesi è **vera** allora **sicuramente la tesi è vera**
  Se l'ipotesi è **falsa** allora **non è detto che la tesi sia falsa**
$$\begin{array}{c|c|c} p & q & p\implies q\\\hline \text{V} & \text{V} & \text{V}\\ \text{V} & \text{F} & \text{F}\\ \text{F} & \text{V} & \text{V}\\ \text{F} & \text{F} & \text{V} \end{array}$$​    Equivalentemente: $p\implies q \equiv \lnot(q\land\lnot p)$.
   $p \iff q$ : **se e solo se**
-  **Negazione**:  
  $\lnot p$.  
### Operazioni insiemistiche
- **Inclusione fra insiemi**:  
  $A \subseteq B$ (sottoinsieme, anche uguale).  
  $A \subset B$ (sottoinsieme proprio).  
- **Intersezione**:  
  $A \cap B$.  
- **Unione**:  
  $A \cup B$.  
- **Insieme vuoto**:  
  $\varnothing$. 
- **Sottrazione (differenza insiemistica)**:  
  $A \setminus B$.  
### Intervalli (in $\mathbb{R}$)
- **Chiuso**:  
  $[a,b] = \{x \in \mathbb{R} \mid a \leq x \leq b\}$.  
- **Aperto**:  
  $(a,b) = \{x \in \mathbb{R} \mid a < x < b\}$.  
- **Semichiuso**:  
  $[a,b) = \{x \in \mathbb{R} \mid a \leq x < b\}$,  
  $(a,b] = \{x \in \mathbb{R} \mid a < x \leq b\}$.  
- **Infiniti**:  
  $(-\infty, a),\ [a, +\infty)$.  
### Relazioni
- **Relazione fra insiemi**:  
  Una relazione $R$ tra $A$ e $B$ è un sottoinsieme del prodotto cartesiano:  
$$
  R \subseteq A \times B = \{(a,b) \mid a \in A, b \in B\}
  $$
- **Relazione inversa**:    $$
  R^{-1} = \{(b,a) \mid (a,b) \in R\}
  $$
## Funzione 
'E una relazione fra A (ass. Dominio) e B (ass. Codominio) in cui ad ogni elemento del dominio deve corrispondere un unico elemento del codominio
I tre elementi sono:
- Relazione
- Dominio
- Codominio
Funzione **Iniettiva** -> passa due volte per la stessa y
Funzione **Suriettiva** -> occupa tutto il codominio
Funzione **Biettiva** -> suriettiva e iniettiva -> invertibile
L'insieme immagine è l'insieme di tutti gli elementi del codominio a cui corrisponde un elemento del dominio.
Per rendere una funzione invertibile posso manipolare il dominio e devo ricavare la x e sostituirla alla y

### Sommatoria e calcolo combinatorio
- **Sommatoria**
- **Coefficiente binomiale** (Quante sottoinsiemi n posso formare in m)![[2. 18 Settembre 2025 Ing.pdf#page=49&rect=30,88,172,153|2. 18 Settembre 2025 Ing, p.49|200]]- **Proprietà**
	- ![[2. 18 Settembre 2025 Ing.pdf#page=58&rect=50,434,193,503|2. 18 Settembre 2025 Ing, p.58|150]]
	- ![[2. 18 Settembre 2025 Ing.pdf#page=58&rect=53,337,298,394|2. 18 Settembre 2025 Ing, p.58|200]]
- **fattoriale**
## Binomio di Newton
é un algoritmo che permette di trovare il quadrato di ogni binomio.
Si dimostra cercando ogni possibile combinazione.
- Scrivo tutti i monomi di grado n ma mancano i coefficienti
- conto in quanti modi diversi posso ottenere il monomio
![[3. 19 Settembre 2025 Ing 1.pdf#page=2&rect=41,593,486,688|3. 19 Settembre 2025 Ing 1, p.2|300]]
Scrivendo i risultati della formula incrementando n si ottiene una struttura simile al **triangolo di tartaglia** (I numeri corrispondono)

## Il principio di induzione
è un metodo per dimostrare formule e teoremi che dipendono da un parametro

Data un **proposizione** P che dipende da un numero naturale n () P(0), P(1), ..., P(n)) tramite il principio di induzione si può dimostrare che se ho una **successione infinita** di affermazioni assumendo che **la prima è vera** e che **questo implica la verità della seguente** posso dimostrare che **tutte sono vere**.![[3. 19 Settembre 2025 Ing 1.pdf#page=15&rect=82,225,577,399|3. 19 Settembre 2025 Ing 1, p.15|400]]![[3. 19 Settembre 2025 Ing 1.pdf#page=15&rect=119,102,517,149|3. 19 Settembre 2025 Ing 1, p.15|400]]
### Dimostrazione
**Principio del minio in N**
Qualunque sottoinsieme non vuoto di N ha un **minimo**.
![[3. 19 Settembre 2025 Ing 1.pdf#page=18&rect=50,275,549,376|3. 19 Settembre 2025 Ing 1, p.18|500]]
*Vedi  la dimostrazione sul PDF*
Procediamo per assurdo negando la tesi del Teorema
m è il minimo di A
m non può essere uguale a 0 perchè sappiamo da ipotesi che P(0) è vera
se m>0 allora posso scrivere m-1 che però è priva di significato perchè m è il minimo di A

**Esempio 1** *Vedi  la dimostrazione sul PDF*
![[3. 19 Settembre 2025 Ing 1.pdf#page=23&rect=62,523,364,614|3. 19 Settembre 2025 Ing 1, p.23|300]]
**Esempio 2** (Disuguaglianaza di Bernulli) *Vedi  la dimostrazione sul PDF*
![[3. 19 Settembre 2025 Ing 1.pdf#page=26&rect=120,504,412,560|3. 19 Settembre 2025 Ing 1, p.26|300]]
**Esempio 3** a casa 
**Esempio 4** (Biomio di Newton)
Percorso di ipotesi verificate tramite il teorema di induzione

## I numeri razionali
**Non sono tutti i numeri** -> mancano gli irrazionali come le radici.
![[4. 23 Settembre 2025 Ing.pdf#page=4&rect=223,696,390,742|4. 23 Settembre 2025 Ing, p.4|200]]
*Dimostrazione sul PDF*
![[4. 23 Settembre 2025 Ing.pdf#page=11&rect=137,591,291,639|4. 23 Settembre 2025 Ing, p.11|200]]
*Dimostrazione sul PDF*
**I numeri razionali non sono sufficienti a descrivere una funzione perchè lasciano dei buchi**
## I numeri reali
L'insieme andrebbe costruito a partire dai numeri razionali rendendo l'insieme non numerabile.
*Dim non numerabilità*
![[4. 23 Settembre 2025 Ing.pdf#page=42&rect=68,590,512,631|4. 23 Settembre 2025 Ing, p.42|500]]
(posso costruire un numero diverso da tutti gli altri fra 0 e 1+)

L'insieme dei numeri reali è continuo

*Def di **Maggiorante**, **Minorante**, **Limitato** , **Minimo**, **Massimo** Pg 21 PDF*
Se esiste il minimo di un insieme questo è il massimo dei minoranti.
Se esiste il massimo di un insieme questo è il minimo dei maggioranti.

**Proprietà di completezza di R**
Ogni sottoinsieme finito di R ha un massimo e un minimo.
*pag 31 PDF*

## Successioni Numeriche
una successione di numeri reali è una funzione

Non confondere la successione (Funzione) con l'insieme degli elementi (Immagine)
Nella successione è prescritto l'ordine degli elementi

## limite
**Limite Finito** (convergente)
![[25 Settembre 2025 Ing.pdf#page=10&rect=10,144,586,543|25 Settembre 2025 Ing, p.10|500]]
Per ogni epsilon positivo esiste una posizione per cui **da quel punto in poi tutti gli elementi della successione stanno dentro il delta.**
Per avere punti via via più vicini al limite devo aumentare di n![[25 Settembre 2025 Ing.pdf#page=16&rect=31,375,572,565|25 Settembre 2025 Ing, p.16|500]]

**Limite infinito** (divergente)
![[25 Settembre 2025 Ing.pdf#page=21&rect=13,152,591,783|25 Settembre 2025 Ing, p.21|500]]
Per ogni epsilon positivo esiste una posizione per cui **da quel punto in poi tutti gli elementi della successione saranno tutti maggiori o tutti minori.**

**Se il limite c'è è UNICO**
Esistono successioni che non hanno un limite
### Teorema del confronto
Se un numero è compreso fra due numeri che tendono allo stesso numero anche il primo numero tende allo stesso numero.
### Ordine degli infiniti
![[OrdineInfiniti.png]]

### Esercizi 
è possibile stimare alcuni limiti e poi usare il teorema del confronto
![[6. 26 Settembre 2025 Ing 1.pdf#page=31&rect=47,618,257,694|6. 26 Settembre 2025 Ing 1, p.31|200]]
+ esercizio
## Successioni 
### Successioni monotone
Successioni **Crescenti o decrescenti**
Una successione crescente o decrescente si dice **monotona**

Le successioni monotone **hanno sempre un limite** che è il limite della successione (inferiore, decrescente - superiore, crescente).
**Dim** pag 40 
## Potenze e radici
L'esponenziale a esponente irrazionale si definisce come limite di un esponenziale a potente razionale 
## Logaritmo
il logaritmo in base a di y è l'esponente dare a a per ottenere y

## Trigonometria
![[Trigonometria cheatsheet.jpg]]
