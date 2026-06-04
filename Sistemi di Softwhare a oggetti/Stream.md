## Interfacce Funzionali
Queste sono le fondamenta. Ogni interfaccia rappresenta un "comportamento" che puoi passare come parametro.

* **`Predicate<T>`** (Il Filtro)
* **Cosa fa:** Prende un input `T` e restituisce un `boolean`.
* *Esempio Pratico:*
```java
Predicate<Integer> isPari = x -> x % 2 == 0;
isPari.test(4); // Restituisce true
```

* **`Function<T, R>`** (Il Trasformatore)
* **Cosa fa:** Prende un input `T` e restituisce un output `R`.
* *Esempio Pratico:*
```java
Function<String, Integer> conta = str -> str.length();
conta.apply("Java"); // Restituisce 4
```

* **`Consumer<T>`** (Il Consumatore)
* **Cosa fa:** Prende un input `T` e non restituisce nulla (`void`). Modifica o stampa.
* *Esempio Pratico:*
```java
Consumer<String> stampa = msg -> System.out.println(msg);
stampa.accept("Ciao"); // Stampa "Ciao" in console
```

* **`Supplier<T>`** (Il Fabbricante)
* **Cosa fa:** Non prende input `()`, ma restituisce un output `T`.
* *Esempio Pratico:*
```java
Supplier<Double> random = () -> Math.random();
random.get(); // Restituisce un numero casuale (es. 0.83)
```

* **`UnaryOperator<T>`** & **`BinaryOperator<T>`** (I Calcolatori)
* **Cosa fanno:** Funzioni speciali dove input e output sono dello stesso tipo. `Unary` prende un parametro, `Binary` ne prende due.
* *Esempio Unary:*
```java
UnaryOperator<Integer> raddoppia = n -> n * 2;
raddoppia.apply(5); // Restituisce 10
```
* *Esempio Binary:*
```java
BinaryOperator<Integer> somma = (a, b) -> a + b;
somma.apply(3, 4); // Restituisce 7
```
### Method References (`::`)
Una sintassi più corta per le lambda quando richiami semplicemente un metodo già esistente:
* `x -> System.out.println(x)` ➔ `System.out::println`
* `str -> str.toUpperCase()` ➔ `String::toUpperCase`
* `() -> new ArrayList<>()` ➔ `ArrayList::new`
## La Stream API: Regole d'Oro
Uno Stream **NON** è una struttura dati, ma un "tubo" in cui i dati scorrono.
1. **Non muta la sorgente:** I dati originali non cambiano mai.
2. **Lazy (Pigro):** Le operazioni intermedie non vengono eseguite finché non invochi un'operazione terminale.
3. **Trappola da Esame:** Uno stream si *consuma*. Una volta chiamata un'operazione terminale, lo stream è chiuso. Riutilizzarlo lancia `IllegalStateException`.
## Creazione (L'origine del Flusso)
* **Da Collection:**
```java
List<String> nomi = List.of("Anna", "Bob");
Stream<String> s = nomi.stream();
```
* **Da Array (Nativi):**
```java
int[] numeri = {1, 2, 3};
IntStream s = Arrays.stream(numeri);
```
* **Da Valori Diretti:**
```java
Stream<Integer> s = Stream.of(10, 20, 30);
```
* **Generazione Infinita:**
```java
Stream<Double> s = Stream.generate(Math::random);
```
## Operazioni Intermedie
*Restituiscono sempre un nuovo Stream. Sono lazy.*

* **`filter(Predicate)`**: Fa passare solo gli elementi che rispettano la condizione.
```java
stream.filter(n -> n > 10) // Tiene solo i maggiori di 10
```
* **`map(Function)`**: Trasforma ogni elemento (es. estrae un campo).
```java
stream.map(Persona::getNome) // Da Stream<Persona> a Stream<String>

List<String> nomi = Arrays.asList("Mario", "Luigi", "Peach");

// Vogliamo sapere la lunghezza di ogni nome
List<Integer> lunghezze = nomi.stream()
    .map(nome -> nome.length()) // Mappa: da String passiamo a Integer
    .collect(Collectors.toList());

// Input (3 elementi): ["Mario", "Luigi", "Peach"]
// Output (3 elementi): [5, 5, 5]
```
* **`flatMap(Function)`**: Appiattisce stream annidati ("schiaccia" liste dentro liste).
```java
streamDiListe.flatMap(lista -> lista.stream()) // Unica lista piatta

// Abbiamo una lista di classi, ogni classe è una lista di nomi
List<List<String>> scuola = Arrays.asList(
    Arrays.asList("Alice", "Bob"),    // Classe 1
    Arrays.asList("Carlo", "Diana")   // Classe 2
);

// Ottieni uno Stream<String> (Solo i nomi sfusi sul nastro)
List<String> tuttiGliStudenti = scuola.stream()
    .flatMap(classe -> classe.stream()) // Apre il cestino e ne fa scorrere il contenuto
    .collect(Collectors.toList());

// Output: ["Alice", "Bob", "Carlo", "Diana"]
```
- `mapToObj(function)` crea stream di oggetti compessi
```java
// Vogliamo creare una lista di stringhe numerate
List<String> etichette = IntStream.rangeClosed(1, 3) // Partiamo da primitivi: 1, 2, 3
    .mapToObj(n -> "Posto numero: " + n)             // Promuoviamo ogni 'int' a 'String' (Oggetto)
    .collect(Collectors.toList());                   // Ora possiamo usare i Collectors!

// Output: ["Posto numero: 1", "Posto numero: 2", "Posto numero: 3"]
```
* **`distinct()`**: Rimuove i duplicati (basandosi su `equals()`).
```java
Stream.of(1, 2, 2, 3).distinct() // Diventa: 1, 2, 3
```
* **`sorted()` / `sorted(Comparator)**`: Ordina gli elementi.
```java
stream.sorted(Comparator.reverseOrder()) // Ordine decrescente
```
* **`limit(n)`**: Tronca lo stream ai primi `n` elementi.
```java
stream.limit(5) // Prende solo i primi 5
```
* **`skip(n)`**: Ignora i primi `n` elementi.
```java
stream.skip(2) // Parte dal 3° elemento in poi
```
* **`peek(Consumer)`**: "Spia" l'elemento per debug, senza modificarlo.
```java
stream.peek(x -> System.out.println("Transita: " + x))
```
- `boxed()` inscatola i tipi primitivi in un wrapper
```java
List<Integer> listaNumeri = IntStream.range(1, 5).boxed() // Da IntStream a Stream<Integer>
.collect(Collectors.toList());
```
- `takeWhile(Predicate)   dropWhile(Predicate)` takeWhile fa passare gli elementi _finché_ la condizione è vera, al primo 'false' chiude il flusso. dropWhile scarta gli elementi _finché_ la condizione è vera, al primo 'false' fa passare tutto il resto. comodi su stream ordinati.
```java
List<Integer> presi = Stream.of(1, 2, 3, 4, 5, 9)
    .takeWhile(n -> n < 4)
    .collect(Collectors.toList());
// Output: [1, 2, 3] (si ferma al 4, ignora il resto)
```
- `Stream.concat(Stream, Stream)` unisce due flussi
```java
Stream<String> s1 = Stream.of("A", "B");
Stream<String> s2 = Stream.of("C", "D");
Stream<String> uniti = Stream.concat(s1, s2);
// Flusso risultante: "A", "B", "C", "D"
```
## Operazioni Terminali (La Chiusura)
*Chiudono lo stream e restituiscono un valore concreto, un `Optional`, o `void`.*

* **`collect(Collector)`**: Imbottiglia i risultati finali in List, Set o Map.
```java
List<String> ris = stream.collect(Collectors.toList());
```
* **`reduce(BinaryOperator)`**: Riduce tutti i dati a un singolo valore cumulativo.
```java
Optional<Integer> somma = stream.reduce((parziale, prossimo) -> parziale + prossimo);

// con valore di partenza
Integer somma = stream.reduce(0, (parziale, prossimo) -> parziale + prossimo);

//trova la parola piu lunga
.reduce("", (a, b) -> a.length() > b.length() ? a : b);
```
* **`forEach(Consumer)`**: Esegue un'azione terminale su ogni elemento.
```java
stream.forEach(System.out::println);
```
* **`count()`**: Conta quanti elementi sono arrivati alla fine.
```java
long quanti = stream.count();
```
* **`min(Comparator)` / `max(Comparator)**`: Trova l'estremo.
```java
Optional<Integer> max = stream.max(Integer::compareTo);
```
* **`findFirst()`**: Prende il primo elemento utile.
```java
Optional<String> primo = stream.filter(s -> s.startsWith("A")).findFirst();
```
* I Matchers (Restituiscono `boolean`):
```java
boolean cEUnPari = stream.anyMatch(n -> n % 2 == 0); // Almeno uno
boolean tuttiPositivi = stream.allMatch(n -> n > 0); // Tutti quanti
boolean nessunoZero = stream.noneMatch(n -> n == 0); // Nessuno
```
- `Collectors.joining()` unisce più stringhe in una sola
```java
List<String> nomi = Arrays.asList("Anna", "Marco", "Luca");

// 1. Unione semplice (tutto attaccato)
String uniti = nomi.stream().collect(Collectors.joining()); 
// Output: "AnnaMarcoLuca"

// 2. Unione con delimitatore (IL PIÙ USATO ALL'ESAME)
String conVirgola = nomi.stream().collect(Collectors.joining(", ")); 
// Output: "Anna, Marco, Luca"

// 3. Unione con delimitatore, prefisso e suffisso
String completo = nomi.stream().collect(Collectors.joining(", ", "[", "]")); 
// Output: "[Anna, Marco, Luca]"
```
- `Collectors.partitioningBy()` divide il flusso in **esattamente due fazioni** (Vero o Falso) basandosi su un `Predicate`.
```java
List<Integer> numeri = Arrays.asList(1, 2, 3, 4, 5, 6);

// Vogliamo dividere i numeri in "Pari" (true) e "Dispari" (false)
Map<Boolean, List<Integer>> partizione = numeri.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

System.out.println("Pari: " + partizione.get(true));  // Output: Pari: [2, 4, 6]
System.out.println("Dispari: " + partizione.get(false)); // Output: Dispari: [1, 3, 5]
```
- `Collectors.groupingBy()` Prende gli elementi del tuo stream e li smista all'interno di una `Map`.
```java
List<String> animali = Arrays.asList("Cane", "Gatto", "Lupo", "Oca", "Panda");

// Vogliamo raggruppare gli animali in base alla lunghezza del loro nome
Map<Integer, List<String>> raggruppatiPerLunghezza = animali.stream()
    .collect(Collectors.groupingBy(String::length));

// Risultato nella Mappa:
// Chiave 3 -> ["Oca"]
// Chiave 4 -> ["Cane", "Lupo"]
// Chiave 5 -> ["Gatto", "Panda"]

// Contiamo quanti animali ci sono per ogni lunghezza
Map<Integer, Long> conteggioPerLunghezza = animali.stream()
    .collect(Collectors.groupingBy(
        String::length,       // 1. Criterio di raggruppamento (La Chiave)
        Collectors.counting() // 2. Cosa fare con gli elementi nel secchiello (Il Valore)
    ));

// Risultato:
// 3 -> 1 (perché c'è solo "Oca")
// 4 -> 2 (perché ci sono "Cane" e "Lupo")
// 5 -> 2 (perché ci sono "Gatto" e "Panda")
```
- `Collectors.toMap()` posiziona i valori in una mappa
```java
st.collect(Collectors.toMap(keyMapper, valueMapper));
```
- `toArray(IntFunction)` converte lo stream in un array tipizzato
```java
String[] arrayNomi = streamNomi.toArray(String[]::new); //chiama il cotruttore
// Se lo stream è di primitivi (es. IntStream), basta solo .toArray()
int[] arrayNumeri = intStream.toArray();
```
- `Collectors.mapping`estrarre o trasformare un dato _mentre_ lo si sta raccogliendo dentro un groupingBy
```java
Map<String, List<String>> nomiPerCorso = studenti.stream()
    .collect(Collectors.groupingBy(
        Studente::getCorso, 
        Collectors.mapping(Studente::getNome, Collectors.toList()) // Estrae il nome e lo mette in lista
    ));
```
- `Collectors.collectingAndThen()` accogli e fai un'ultima modifica
```java
List<String> listaImmutabile = stream.collect(
    Collectors.collectingAndThen(
        Collectors.toList(),        // 1. Prima crea la lista
        Collections::unmodifiableList // 2. Poi la rende immutabile
    )
);
```
- `Collectors.minBy(Comparator)  Collectors.maxBy(Comparator)` Trova l'elemento minimo o massimo durante una fase di raccolta.
```java
Map<String, Optional<Studente>> migliorePerCorso = studenti.stream()
    .collect(Collectors.groupingBy(
        Studente::getCorso,
        Collectors.maxBy(Comparator.comparing(Studente::getVoto))
    ));
```
## Stream Primitivi (Prestazioni ottimali)
Per evitare l'autoboxing (es. `Integer` vs `int`), usa `IntStream`, `DoubleStream`, `LongStream`. Si ottengono con metodi come `mapToInt()`.

* **`sum()`**: Calcola la somma totale.
```java
int totale = intStream.sum();
```
* **`average()`**: Calcola la media aritmetica.
```java
OptionalDouble media = intStream.average();
```
* **`summaryStatistics()`**: Il coltellino svizzero (Count, Min, Max, Sum, Avg).
```java
IntSummaryStatistics stats = intStream.summaryStatistics();
System.out.println(stats.getMax());
```
- `range()` sostituisce il ciclo for
```java
IntStream.range(0, n) // 1. Crea uno stream di indici: da 0 a (n - 1)
```
- `Stream.iterate()` sostituisce un for complesso
```java
// Vogliamo generare le prime 5 potenze di 2
List<Integer> potenze = Stream.iterate(2, n -> n * 2) // Parto da 2, e ogni volta raddoppio
    .limit(5) // FONDAMENTALE: mi fermo dopo 5 giri
	    .collect(Collectors.toList());
```
## `Optional<T>` (Come salvarsi dalle NullPointerException)
Gli stream restituiscono un `Optional` quando il risultato potrebbe non esistere (es. cerchi un elemento ma lo stream è vuoto).

* **`isPresent()`**: Controlla se la scatola è piena.
```java
if (opt.isPresent()) { ... }
```
* **`ifPresent(Consumer)`**: Se c'è qualcosa, esegui il codice.
```java
opt.ifPresent(val -> System.out.println("Trovato: " + val));
```
* **`get()`**: Estrae brutalmente il valore (Lancia eccezione se vuoto! Attenzione all'esame).
```java
String valore = opt.get(); 
```
* **`orElse(Default)`**: Ottimo piano B. Se vuoto, usa il valore indicato.
```java
String finale = opt.orElse("Valore di Default");
```
* **`orElseThrow()`**: Lancia esplicitamente un'eccezione custom se vuoto.
```java
String sicuro = opt.orElseThrow(() -> new RuntimeException("Vuoto!"));
```
## Snippet Strategici
**A. Il Classico: Filtra, Mappa, Ordina e Colleziona**
```java
List<String> parole = Arrays.asList("Albero", "casa", "Amico", "cane", "Ape");
List<String> risultato = parole.stream()
    .filter(p -> p.toLowerCase().startsWith("a")) // 1. Filtra
    .map(String::toUpperCase)                     // 2. Mappa (Method Reference)
    .sorted()                                     // 3. Ordina
    .collect(Collectors.toList());                // 4. Termina e raccoglie
// Output: [ALBERO, AMICO, APE]
```

**B. Estrazione Campo (Oggetto ➔ Campo specifico)**
```java
Set<String> nomiBravi = studenti.stream()
    .filter(s -> s.getVoto() > 25)
    .map(Studente::getNome)       // Estrae solo la Stringa 'nome'
    .collect(Collectors.toSet()); // toSet() elimina automaticamente gli omonimi
```

**C. Il Potere di `groupingBy` (Creare Mappe)**
```java
List<String> parole = Arrays.asList("java", "c", "go", "php");
Map<Integer, List<String>> parolePerLunghezza = parole.stream()
    .collect(Collectors.groupingBy(String::length));
// Chiave: lunghezza (es. 4) -> Valore: Lista di parole (es. ["java"])
```

**D. Somma ottimizzata (`mapToInt`)**
```java
int sommaVoti = studenti.stream()
    .mapToInt(Studente::getVoto) // Genera un IntStream primitivo
    .sum();                      // Operazione terminale super efficiente
```

**E. Regola d'oro delle Lambda: Variabili "Effectively Final"**
Le Lambda non possono modificare le variabili dichiarate al di fuori di esse.
```java
// ERRORE DA BOCCIATURA:
int contatore = 0;
lista.stream().forEach(x -> contatore++); // Errore di compilazione!
// SOLUZIONE CORRETTA:
long contatoreGiusto = lista.stream().count();
```
## Da vecchi esami
**CrittoCruciverba**
```java
public String parolaIniziale() {
List<String> parole;
Cruciverba cruciverba = this.getCruciverba(); // Ottieni l'oggetto Cruciverba dal Controller
parole = IntStream.range(0, cruciverba.getNumRighe()) // 1. Crea uno stream di indici: da 0 a (numRighe - 1)
.mapToObj(cruciverba::paroleInRiga) // 2. Per ogni indice, ottieni l'array String[] delle parole
.flatMap(Arrays::stream) // 3. Appiattisci tutti gli String[] in un unico Stream<String>
.filter(p -> this.numeroCaratteriDistinti(p) == this.getModalita().numeroCaratteriDistinti()) // filtro per lunghezza
.collect(Collectors.toList());

int i = r.nextInt(parole.size());
return parole.get(i);
}
```