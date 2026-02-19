[[0 - Index]]

Mail: sistemidisoftwhareaoggetti@live.unibo.it

Un linguaggio a oggetti è necessario per gestire problemi di ampiezza superiore.
## Nuove Idee
- distinzione **variabili** modificabili/ **valori** non modificabili (**var** vs **val**)
- strutture dati immodificabili: "compute by synthesis"
- abolizione dei tipi primitivi: "everything is an object" (bisogna ridefinire le operazioni per ogni oggetto)

Java è pensato per essere estremamente versatile e portabile.
I collegamenti del linker vengono eseguiti a runtime rendendo il programma più lento ma flessibile che sa gestire automaticamente aggiornamenti.

Perchè il codice possa funzionare su diversi processori la compilazione viene effettuata su JVM (java virtual machine) e poi tradotta nell'assembly specifico del processore.

FARE APPUNTI PART SU JAVA




## Novità rispetto al C
- **sostituire** meccanismi e costrutti linguistici poco chiari, **sintatticamente obsoleti**.
- **intercettare** a compile-time quanti più **errori** possibile
### Struttura delle operazioni
Si inverte il punto di vista rispetto al c
```cpp
operation(comp, argomenti);

fprintf(fout, "Hello!");
```
diventa
```java
comp.operation(argomenti);
```
- **comp** = a CHI mi rivolgo
- **operation** = COSA gli chiedo di fare
es.
```java
System.out.println("Hello!");

// alternativa sempl
IO.println("Hello!");
```
out è un sottocomponente di System.
### Elementi di base
- **STATICI** (es. libreria matematica) --> CLASSI
- **DINAMICHE** create durante l'esecuzione --> OGGETTI
### Gestione della memoria
- sostituzione dei puntatori con **riferimenti**
- dereferenziamento automatico
- **allocazione** e **deallocazione** **automatica** della memoria
- **garbage collector**
- **type safety** = type system stringente + type inference + controlli a run-time

## Il Main
Il main è contenuto in una **classe pubblica dedicata**.
```java
public class MyProg {
// il main (anch'esso pubblico) ... 
}
```
Il main ha **sempre** come **argomento un oggetto** (array di stringhe).
Il tipo dii **ritorno** è sempre **void**.

```java
public class MyProg {
	public static void main(String[] args){
	
	}
}
```
**static** significa che esiste dall'inizio.

es.
```java
public class MyProg {
	public static void main(String[] args){
		int x=3, y=4;
		int z = x+y;
	}
}
```
## Compilazione e esecuzione da terminale
`javac MyProg.java` restituisce MyProg.class

Perm eseguire il programma devo attivare l'infrastruttura java
javac MyProg.java
## Classi
Il **nome** della classe **inizia con una maiuscola** (CamelCase) e deve essere contenuta in fun file con lo stesso nome .java

Le classi **partizionano** lo spazio dei **nomi** (possono esistere nomi uguali in classi diverse)
```java
Math.sin(Math.PI/3)
```

## Documentazione
La costruzione della **documentazione** è fatta in automatico da **javadoc** a patto che i commenti vengano fatti secondo la sintassi specifica.
Es.
```java
/**
*commento col doppio asterisco all'inizio
*/

/** File Esempio.java
* Applicazione Java da linea di comando
* Stampa la classica frase di benvenuto
@author Enrico Denti
@version 1.0, 02/02/2025
*/
```
Per comodità è possibile utilizzare il markdown
```java
///Tre sbarre su ogni riga
/// ## Titoli
///- elenchi puntati
```
Per richiedere la generazione della documentazione si usa
`javadoc –d docs Esempio0.java`  (-d indica la directory)

## Tipi di Base
**Boolean** (separato dagli interi) ammette solo **false** e **true** che non sono 0 e 1

**Int** (le diverse lunghezze vengono standardizzate e la sintassi semplificata)
- **byte**  (1 byte) -128 ... +127
- **short**  (2 byte) -32768 ... +32767
- **int** (4 byte) -2 10^9  …... +2 10^9
- **long** (8 byte) -9 10^18 ……+9 10^18 
**Float**
- **float** (4 byte) - 10^45 ... +10^38
- **double** (8 byte) -10^324 ... +10^308

Sono ammessi solo gli assegnamenti che non generano una perdita di informazioni
```java
double x = 3.54F; /// permesso
float f = 3.54; /// da errore
```
oppure posso fare un **cast**
```java
float f = (float) 3.54; 
```

**Caratteri**
127 caratteri non bastano quindi si assegnano più byte a ogni carattere 1-4
(1.114.112 caratteri)
![[102-Linguaggi e piattaforme.pdf#page=81&rect=229,48,682,241|102-Linguaggi e piattaforme, p.81|300]]
C'è ancora la corrispondenza diretta fra caratteri e numeri come in C.
```java
char ch = 'A';
ch = 72;
char cStran = '\u2122'
```





## il Deployment del softwhare
L'eseguibile non è un file unico ma un **archivio zippato .jar** (non da estrarre) che contiene
- Per **librerie**, bastano le classi (file .class)
- Per **applicazioni**, occorre anche specificare la posizione del main (in un file di testo)
![[103-Deployment.pdf#page=4&rect=540,208,703,431|103-Deployment, p.4|150]]
Per creare un file JAR come archivio di classi (zip) `jar cf nomearchivio.jar classi`
- un elenco di classi (.class) da includerem DAFINIRE

Per creare un jar eseguibile
`jar cmf info.txt nomeapp.jar classi`dove info.txt contiene "Main-Class: NomeclasseMain"
oppure
`jar cef NomeclasseMain nomeapp.jar classi`
`jar --create --main-class NomeclasseMain --file nomeapp.jar classi`
es.
`jar cmf infoApp MyApp.jar Esempio.class CodFisc.class`
`jar cmf infoApp MyApp.jar Esempio.class *.class` (prende tutte e sole le classi contenute nella cartella)

Per eseguire un jar aprendo il terminale `java -jar nomefile.jar` 

Per compilare includendo delle librerie.
![[103-Deployment.pdf#page=14&rect=10,56,325,429|103-Deployment, p.14||200]]
Vedi esempio comoleto