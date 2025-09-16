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

L'implicazione materiale $p \implies q$ è la proposizione che vale **falsa** solo quando $p$ è vera e $q$ è falsa; è vera in tutti gli altri casi. Intuitivamente: "se $p$ allora $q$".
$$\begin{array}{c|c|c} p & q & p\implies q\\\hline \text{V} & \text{V} & \text{V}\\ \text{V} & \text{F} & \text{F}\\ \text{F} & \text{V} & \text{V}\\ \text{F} & \text{F} & \text{V} \end{array}$$​​

Questo comporta il concetto di **verità per vacuità**: se l'antecedente $p$ è falso, l'implicazione $p\implies q$ è automaticamente vera.
  Equivalentemente: $p\implies q \equiv \lnot(p\land\lnot q)$.
- **Negazione**:  
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
