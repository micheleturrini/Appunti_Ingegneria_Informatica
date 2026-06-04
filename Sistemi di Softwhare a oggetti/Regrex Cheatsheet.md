[[0 - Index]]
**il doppio backslash `\\`**. Poiché il backslash è già un carattere di _escape_ per le stringhe in Java (es. `\n` per andare a capo), per scrivere una vera regex devi "scappare" il backslash stesso. Quindi, la regex `\d` in Java si scrive sempre `\\d`.

**Classi di caratteri (Cosa cerco?):**
- `\\d` : Un numero qualsiasi (da 0 a 9).
- `\\D` : Qualsiasi cosa _tranne_ un numero.
- `\\s` : Uno spazio bianco (spazio, tab, a capo).
- `\\S` : Qualsiasi cosa _tranne_ uno spazio bianco.
- `\\w` : Un carattere alfanumerico (lettere, numeri o underscore `_`).
- `\\W` : Un carattere non alfanumerico (es. punteggiatura).
- `.` : Il carattere "jolly". Vale per _qualsiasi_ carattere (tranne l'a capo).

**Quantificatori (Quante volte lo cerco?):*
- `+` : Una o più volte. (Es. `\\d+` = almeno un numero, ma anche mille numeri consecutivi).
- `*` : Zero o più volte. (Può anche non esserci).
- `?` : Zero o una volta. (Rende l'elemento _opzionale_).
- `{n}` : Esattamente _n_ volte. (Es. `\\d{3}` = esattamente 3 numeri).
- `{n,m}` : Da _n_ a _m_ volte. (Es. `\\w{2,4}` = da 2 a 4 lettere/numeri).

**Insiemi e Gruppi (Logica avanzata):**
- `[abc]` : Solo 'a', 'b', o 'c'.
- `[a-z]` : Tutte le lettere minuscole dalla 'a' alla 'z'.
- `[^0-9]` : Il cappelletto `^` dentro le quadre significa **NOT**. Qui significa: tutto _tranne_ i numeri da 0 a 9.
- `(abc)` : Raggruppa i caratteri nella sequenza esatta "abc".
- `A|B` : Operatore OR. Cerca il pattern A oppure il pattern B.

**Ancore (Dove lo cerco?):**
- `^` : Inizio della stringa (se usato fuori dalle parentesi quadre).
- `$` : Fine della stringa.

> [!danger]
> Cercando di splittare con uno qualsiasi dei caratteri speciali per utilizzarlo come carattere e non come elemento logico devo precederlo con `\\`
### i Metodi di Java per le Regex
Java ti offre tre metodi comodissimi direttamente nella classe `String`:
1. **`stringa.matches(regex)`**: Restituisce `true` se l'INTERA stringa corrisponde perfettamente al pattern.
2. **`stringa.split(regex)`: Taglia la stringa in un array, usando il pattern come "forbice".** rimuove i caratteri forbice.
3. **`stringa.replaceAll(regex, sostituto)`**: Trova tutte le parti che combaciano con il pattern e le sostituisce con un'altra stringa.

```java
// Abbiamo una riga di un file CSV dove i campi sono separati da virgola
// MA a volte c'è uno spazio dopo la virgola, a volte no.
String rigaCsv = "Marco, Rossi,    25,Roma, Studente";

// Tagliamo dove c'è una virgola, seguita OPZIONALMENTE (*) da spazi bianchi (\\s)
String[] campi = rigaCsv.split(",\\s*");

for (String campo : campi) {
    System.out.println("-" + campo + "-");
}
// Output pulitissimo, senza spazi iniziali/finali nei campi:
// -Marco-
// -Rossi-
// -25-
// -Roma-
// -Studente-
```