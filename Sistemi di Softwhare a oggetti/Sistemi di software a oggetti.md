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