[[0 - Index]]
## Lambda expression - introduzione
Le **lambda expression** sono un modo compatto per definire **funzioni anonime** (cioè senza nome) direttamente nel codice, in uno stile simile alla notazione matematica.

**Sintassi base:**
```
(argomenti) -> corpo
```

**Esempi:**
```java
x -> 2*x + 1
(x, y) -> Math.sqrt(x*x + y*y)
```

Nei linguaggi tradizionali il codice e i dati erano separati: le funzioni avevano un nome e un corpo fisso. Le lambda permettono di **trattare le funzioni come oggetti**:
- **assegnabili a variabili**
- **passabili come argomenti ad altre funzioni**
- **restituibili da funzioni**
Questo permette di scrivere codice più flessibile e riutilizzabile (es. librerie con comportamento personalizzabile).

**Tipo di una lambda in Java**
Java ha un approccio **nominale**: ogni tipo di funzione ha un nome predefinito (interfaccia funzionale).

| Tipo di funzione | Interfaccia Java | Metodo da chiamare |
|----------------|------------------|--------------------|
| un solo argomento `int -> int` | `IntUnaryOperator` | `applyAsInt(int)` |
| un solo argomento `double -> double` | `DoubleUnaryOperator` | `applyAsDouble(double)` |
| due argomenti `double, double -> double` | `DoubleBinaryOperator` | `applyAsDouble(double, double)` |
| un argomento `T -> R` (tipi diversi) | `Function<T,R>` | `apply(T)` |
| due argomenti `T,U -> R` | `BiFunction<T,U,R>` | `apply(T,U)` |

**Regole di naming:**
- Se dominio e codominio coincidono → `XXXOperator` (es. `IntUnaryOperator`)
- Altrimenti → `Function` o `BiFunction`
- Varianti per tipi primitivi: `IntFunction`, `DoubleBinaryOperator`, ecc. per evitare continui boxing e unboxing.
Es.
```java
IntUnaryOperator f = x -> 2*x + 1;
DoubleUnaryOperator g = x -> 2 * Math.sin(x/2);
DoubleBinaryOperator h = (x, y) -> Math.sqrt(x*x + y*y);
```

**Chiamata di una lambda in Java**
In Java non si può usare la notazione `f(5)` direttamente. Bisogna chiamare il metodo specifico dell'interfaccia (es. `applyAsInt`, `applyAsDouble`).

```java
System.out.println(f.applyAsInt(5));          // 11
System.out.println(g.applyAsDouble(Math.PI/2)); // ~1.414
System.out.println(h.applyAsDouble(3, 4));    // 5.0
```
Il tipo della lambda è una **normale interfaccia** che contiene un metodo astratto non posso nasconderlo.

**Tradizionale:**
```java
int f(int x) { return 2*x + 1; }
System.out.println(f(5));
```

**Lambda:**
```java
IntUnaryOperator f = x -> 2*x + 1;
System.out.println(f.applyAsInt(5));
```

#### Utilità principale: passare comportamenti
Le lambda eccellono quando vogliamo **parametrizzare il comportamento** di una funzione.

> [!success]
> Il vero valore aggiunto è che **permettono di assegnare un oggetto-funzione a una variabile e quindi anche di passarlo come argomento a un’altra funzione** (o restituirlo)
rendere **parametrico il comportamento di certe funzioni** (librerie), iniettando comportamento
definire **funzioni di ordine superiore**

**Esempio: calcolatrice generica**
```java
public static double calc(DoubleUnaryOperator op, double arg) {
    return op.applyAsDouble(arg);
}
```
Possiamo passare diverse lambda (anche definite sul momento):
```java
DoubleUnaryOperator f1 = x -> 2 * Math.sin(x/2);
DoubleUnaryOperator f2 = y -> Math.sqrt(y);

System.out.println(calc(f1, Math.PI));      // 2.0
System.out.println(calc(f2, 1.0/9.0));      // 0.333...
System.out.println(calc(x -> x+1, 4.5));    // 5.5
System.out.println(calc(r -> Math.sin(r), Math.PI/4)); // ~0.707
```
#### Method reference (riferimento a metodo)
Se esiste già un metodo che fa quello che serve, possiamo usare la sintassi compatta `NomeClasse::nomeMetodo` (per metodi statici) o `oggetto::nomeMetodo` (per metodi di istanza).

**Esempio con metodo statico:**
```java
// Supponiamo nella classe Main ci sia:
private static double g(double w) { return w*w - 4; }

// Invece di:
calc(x -> g(x), 3.0);
// Possiamo scrivere:
calc(Main::g, 3.0);
```

**Esempio con metodo di istanza (tipico nella grafica JavaFX):**
```java
Button b = new Button("Cliccami");
b.setOnAction(this::myHandle);   // this::nomeMetodo
```
# JavaFX
