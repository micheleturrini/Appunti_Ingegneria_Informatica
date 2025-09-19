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
# ## Intervalli (in $\mathbb{R}$)
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
**Es 1**
![[3. 19 Settembre 2025 Ing 1.pdf#page=12&rect=49,524,347,624|3. 19 Settembre 2025 Ing 1, p.12]]
Chiamo ogni eguaglianza P(0), P(1), ..., P(n)
Tramite il principio di induzione dobbiamo dimostrare che se ogni 

Se ho una successione infinita di affermazioni assumendo che la prima è vera e che questo implica la verità della seguente posso dimostrare che tutte sono vere.
![[3. 19 Settembre 2025 Ing 1.pdf#page=15&rect=82,225,577,399|3. 19 Settembre 2025 Ing 1, p.15|400]]![[3. 19 Settembre 2025 Ing 1.pdf#page=15&rect=119,102,517,149|3. 19 Settembre 2025 Ing 1, p.15|400]]
**Principio del minio in N**
Qualunque sottoinsieme non vuoto di N ha un **minimo**.
![[3. 19 Settembre 2025 Ing 1.pdf#page=18&rect=50,275,549,376|3. 19 Settembre 2025 Ing 1, p.18]]
 Dimostrazione del principio di induzione
 Procediamo per assurdo negando la tesi del Teorema
![[3. 19 Settembre 2025 Ing 1.pdf#page=20&rect=57,430,521,497|3. 19 Settembre 2025 Ing 1, p.20]]
m è il minimo di A
m non può essere uguale a 0 perche sappiamo da ipotesi che P(0) è vera
se m>0 allora posso scrivere m-1 che però è priva di significato perchè m è il minimo di A