Ecco il testo ripulito dagli a capo superflui (rispettando tabelle e blocchi di codice) e con i tag dei code block corretti da `Java` a `java`:
## Struttura base

![[132-Grafica in JavaFX.pdf#page=22&rect=371,94,710,391|132-Grafica in JavaFX, p.22|400]]

Ogni applicazione JavaFX deve estendere `Application` e sovrascrivere il metodo `start(Stage stage)`.

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EsempioBase extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Titolo finestra");
        
        // 1. Creare il pannello (layout)
        FlowPane root = new FlowPane();
        
        // 2. Creare i componenti (Label, Button, ...)
        
        // 3. Aggiungere componenti al pannello
        // root.getChildren().addAll(comp1, comp2, ...);
        
        // 4. Creare la scena (larghezza/altezza opzionali)
        Scene scene = new Scene(root, 400, 300);
        
        // 5. Impostare la scena e mostrare
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
```

**Metodo `start`** : è il punto di ingresso della parte grafica.

**`Stage`** : la finestra principale.

**`Scene`** : contenitore che associa il pannello radice (root) alla finestra.

## Layout Panes

I pannelli dispongono automaticamente i componenti figli secondo una politica. **Non usare coordinate assolute**.

![[132-Grafica in JavaFX.pdf#page=38&rect=17,42,712,420|132-Grafica in JavaFX, p.38|700]]

Es.

![[132-Grafica in JavaFX.pdf#page=39&rect=34,62,677,429|132-Grafica in JavaFX, p.39|600]]

|**Pannello**|**Comportamento**|**Uso tipico**|
|---|---|---|
|`FlowPane`|Dispone in riga, va a capo quando non c’è spazio|Pannello generico, esercizi semplici|
|`VBox`|Disposizione verticale (una colonna)|Moduli, liste verticali|
|`HBox`|Disposizione orizzontale (una riga)|Barra pulsanti|
|`BorderPane`|5 aree: top, bottom, left, right, center|Interfacce con menu, barra di stato|
|`GridPane`|Griglia righe/colonne|Form complessi|
|`StackPane`|Componenti sovrapposti (uno sopra l’altro)|Immagini di sfondo|

**Esempio con BorderPane**:

```java
BorderPane root = new BorderPane();
root.setTop(new Label("Intestazione"));
root.setCenter(lista);
root.setBottom(new Button("OK"));
```

**Esempio con VBox**:

```java
VBox vbox = new VBox(10); // spaziatura verticale 10 pixel
vbox.getChildren().addAll(label, textField, button);
```

### Metodi comuni a tutti i pannelli (Region/Pane)

|**Metodo**|**Descrizione**|
|---|---|
|`getChildren().addAll(...)`|Aggiunge uno o più nodi figli al pannello|
|`getChildren().clear()`|Rimuove tutti i nodi figli|
|`setStyle(String)`|Imposta il colore di sfondo o altro via CSS (es. `"-fx-background-color: lightgray;"`)|
|`setPadding(Insets)`|Margine interno (es. `new Insets(10)`)|
|`setSpacing(double)`|Distanza tra componenti (solo VBox, HBox, FlowPane)|
|`setAlignment(Pos)`|Allinea il contenuto (es. `Pos.CENTER`)|

## Gestione degli eventi – tutti i modi

Un evento è un’istanza di una sottoclasse di `EventObject`. Per i pulsanti e toggle, l’evento più comune è `ActionEvent`.

Per ascoltare un evento, si usa un `EventHandler` (interfaccia funzionale con un solo metodo `handle`).

**Lambda expression (modo più usato)**

```java
button.setOnAction(event -> {
    System.out.println("Cliccato");
    // event.getSource() restituisce il componente che ha generato l'evento
});
```

**Method reference**

```java
button.setOnAction(this::gestisciClic);

private void gestisciClic(ActionEvent event) {
    // codice
}
```

**Classe esterna separata**

```java
class MioListener implements EventHandler<ActionEvent> {
    private Label label;
    public MioListener(Label lbl) { this.label = lbl; }
    public void handle(ActionEvent e) {
        label.setText("Cliccato");
    }
}
// Uso:
button.setOnAction(new MioListener(myLabel));
```

### ChangeListener (per proprietà osservabili)

Usato per slider, valueProperty, selectedItemProperty, ecc.

```java
slider.valueProperty().addListener((observable, oldValue, newValue) -> {
    // observable è la proprietà, oldValue e newValue sono i valori
    double corrente = newValue.doubleValue();
    //chiama myHandle?
});
```

La lambda ha 3 parametri.

**Attenzione**: non tutti i componenti generano `ActionEvent`. Lo Slider non lo genera, serve ChangeListener sulla proprietà `valueProperty`.

## Componente Label

**Ruolo**: visualizzare testo statico o dinamico (solo output, non interattivo).

**Costruttori**

```java
Label l1 = new Label();                    // vuoto
Label l2 = new Label("Testo iniziale");    // con testo
Label l3 = new Label("Testo", imageView);  // con immagine/icona
```

|**Metodo**|**Descrizione**|
|---|---|
|`setText(String)`|Cambia il testo visualizzato|
|`getText()`|Restituisce il testo corrente|
|`setFont(Font font)`|Imposta carattere, dimensione, stile|
|`setTextFill(Paint)`|Colore del testo (es. `Color.RED`)|
|`setStyle(String)`|CSS diretto (es. `"-fx-font-size: 20px;"`)|
|`setWrapText(boolean)`|Se `true`, manda a capo il testo automaticamente|
|`setGraphic(Node)`|Inserisce un elemento grafico (es. icona) di fianco al testo|
|`setAlignment(Pos)`|Allinea il testo all'interno dell'area della Label|

**Font**

```java
// Font normale
label.setFont(Font.font("Arial", 16));

// Grassetto
label.setFont(Font.font("Courier New", FontWeight.BOLD, 24));

// Corsivo
label.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 18));
```

## Componente Button

**Ruolo**: pulsante che genera `ActionEvent` al clic. Non mantiene lo stato.

**Costruttori**

```java
Button b1 = new Button();                // senza testo
Button b2 = new Button("Cliccami");      // con testo
Button b3 = new Button("", new ImageView(...)); // con icona
```

|**Metodo**|**Descrizione**|
|---|---|
|`setText(String)`|Cambia l’etichetta|
|`getText()`|Legge l'etichetta attuale|
|`setOnAction(EventHandler)`|Definisce l'azione da eseguire al clic|
|`setTooltip(Tooltip)`|Aggiunge un suggerimento al passaggio del mouse|
|`setDisable(boolean)`|Se `true`, disabilita il pulsante|
|`setDefaultButton(boolean)`|Se `true`, si attiva premendo INVIO|
|`setCancelButton(boolean)`|Se `true`, si attiva premendo ESC|
|`setGraphic(Node)`|Inserisce un'icona (es. `ImageView`)|
|`fire()`|Simula il clic via codice|

```java
button.setOnAction(event -> {
    // lambda expression
});
```

## Componenti di testo

### Metodi condivisi (da TextInputControl)

Questi metodi valgono per **TextField**, **PasswordField** e **TextArea**:

|**Metodo**|**Descrizione**|
|---|---|
|`getText()`|Legge il testo contenuto|
|`setText(String)`|Imposta/sovrascrive il testo|
|`clear()`|Cancella tutto il contenuto|
|`setEditable(boolean)`|Rende modificabile (`true`) o in sola lettura (`false`)|
|`setPromptText(String)`|Mostra un testo sfumato se il campo è vuoto|
|`appendText(String)`|Aggiunge testo in fondo senza sostituire il precedente|
|`selectAll()`|Seleziona tutto il testo|

### TextField (riga singola)

**Costruttori**:

```java
TextField tf = new TextField();                     // vuoto
TextField tf2 = new TextField("Testo iniziale");    // con testo
```

|**Metodo specifico**|**Descrizione**|
|---|---|
|`setPrefColumnCount(int)`|Imposta la larghezza preferita in caratteri|
|`setOnAction(EventHandler)`|Gestisce l'evento pressione tasto INVIO nel campo|

### PasswordField (come TextField ma nasconde i caratteri)

```java
PasswordField pf = new PasswordField();
pf.setPromptText("Inserisci password");
String pwd = pf.getText(); // restituisce la stringa effettiva decodificata
```

### TextArea (multi‑riga)

**Costruttori**:

```java
TextArea area = new TextArea();
TextArea area2 = new TextArea("Testo\nsu più righe");
```

|**Metodo specifico**|**Descrizione**|
|---|---|
|`setPrefRowCount(int)`|Numero di righe visibili|
|`setPrefColumnCount(int)`|Larghezza in caratteri|
|`setWrapText(boolean)`|Se `true`, va a capo in automatico senza tagliare le parole|

## Componente ComboBox

**Ruolo**: lista a discesa. Di default non editabile; se editabile l’utente può scrivere un valore personalizzato.

![[132-Grafica in JavaFX.pdf#page=79&rect=111,42,613,186|132-Grafica in JavaFX, p.79|600]]

```java
ComboBox<String> combo = new ComboBox<>();
```

|**Metodo**|**Descrizione**|
|---|---|
|`getValue()`|Restituisce l’elemento selezionato (o null se nessuno)|
|`setValue(T)`|Imposta la selezione a un elemento esistente|
|`getItems()`|Restituisce la `ObservableList` per usare `.add()` / `.addAll()`|
|`setItems(ObservableList)`|Sostituisce la lista degli elementi|
|`getSelectionModel().getSelectedIndex()`|Indice selezionato (0‑based)|
|`getSelectionModel().clearSelection()`|Deseleziona tutto|
|`setEditable(boolean)`|Permette all’utente di scrivere testo libero|
|`setPromptText(String)`|Testo mostrato quando nessuna selezione|
|`setPrefWidth(double)`|Larghezza preferita|
|`setOnAction(EventHandler)`|Evento scatenato al cambio di selezione|

**ComboBox editabile**

```java
combo.setEditable(true);
combo.setPromptText("Scegli o scrivi");
// Il testo scritto è accessibile con combo.getEditor().getText()
// ma attenzione: getValue() restituisce l'elemento selezionato dalla lista,
// non il testo libero.
```

## Componente Slider

**Ruolo**: barra di scorrimento per selezionare un valore numerico in un intervallo.

Costruttori

```java
Slider s1 = new Slider();               // min=0, max=100, valore=0
Slider s2 = new Slider(0, 100, 50);    // min, max, valore iniziale
```

|**Metodo**|**Descrizione**|
|---|---|
|`setMin(double)` / `setMax(double)`|Imposta i limiti inferiori e superiori|
|`setValue(double)`|Imposta valore corrente|
|`getValue()`|Restituisce il valore corrente (`double`)|
|`valueProperty()`|Proprietà osservabile (per il `ChangeListener`)|
|`setOrientation(Orientation)`|Es. `Orientation.VERTICAL` (default orizzontale)|
|`setShowTickMarks(boolean)`|Mostra i segni di spunta visivi|
|`setShowTickLabels(boolean)`|Mostra le etichette numeriche|
|`setMajorTickUnit(double)`|Distanza tra i tick principali|
|`setMinorTickCount(int)`|Numero di tick piccoli tra due tick grandi|
|`setSnapToTicks(boolean)`|Forza il cursore ad allinearsi esattamente sui tick|
|`setBlockIncrement(double)`|Incremento/decremento tramite frecce tastiera|

## Componente ToggleButton

**Ruolo**: pulsante a due stati (selezionato / deselezionato). Mantiene lo stato dopo il clic.

Costruttore

```java
ToggleButton tb = new ToggleButton("On/Off");
```

|**Metodo**|**Descrizione**|
|---|---|
|`isSelected()`|Restituisce `true` se il pulsante è nello stato premuto (ON)|
|`setSelected(boolean)`|Forza programmaticamente lo stato del pulsante|
|`setToggleGroup(ToggleGroup)`|Associa il pulsante a un gruppo (per opzioni mutuamente esclusive)|
|`setOnAction(EventHandler)`|Azione eseguita quando il pulsante viene cliccato|

## Componente CheckBox

**Ruolo**: casella di opzione (selezionata/non selezionata). Ogni CheckBox è indipendente.

Costruttore

```java
CheckBox chk = new CheckBox("Accetto i termini");
```

|**Metodo**|**Descrizione**|
|---|---|
|`isSelected()`|Restituisce `true` se la casella è spuntata|
|`setSelected(boolean)`|Imposta lo stato di spunta|
|`setAllowIndeterminate(boolean)`|Consente uno stato "terzo" (indeterminato/trattino)|
|`setOnAction(EventHandler)`|Azione eseguita al cambio di stato|

## Componente RadioButton con ToggleGroup

**Ruolo**: opzioni mutuamente esclusive (una sola per gruppo). I RadioButton vanno sempre raggruppati.

Creazione del gruppo

```java
ToggleGroup gruppo = new ToggleGroup();
```

|**Metodo di RadioButton**|**Descrizione**|
|---|---|
|`setToggleGroup(ToggleGroup)`|Associa il RadioButton al gruppo (fondamentale!)|
|`isSelected()`|Verifica se è il RadioButton correntemente selezionato|
|`setSelected(boolean)`|Seleziona forzatamente il RadioButton|

|**Metodo di ToggleGroup**|**Descrizione**|
|---|---|
|`getSelectedToggle()`|Restituisce il `Toggle` (RadioButton) attualmente selezionato|
|`selectedToggleProperty()`|Proprietà osservabile per usare il `ChangeListener`|
|`getToggles()`|Restituisce la lista di tutti i bottoni nel gruppo|

## Componente ListView

**Ruolo**: lista verticale con scrollbar. Permette selezione singola o multipla.

Popolamento

```java
ListView<String> list = new ListView<>();
ObservableList<String> items = FXCollections.observableArrayList(
    "Mela", "Pera", "Banana"
);
list.setItems(items);
```

|**Metodo**|**Descrizione**|
|---|---|
|`setItems(ObservableList)`|Popola la lista con gli elementi|
|`getItems()`|Restituisce gli elementi (per `.add()`, `.remove()`)|
|`getSelectionModel().getSelectedItem()`|Restituisce l'elemento selezionato|
|`getSelectionModel().getSelectedItems()`|Restituisce la lista degli elementi selezionati (in selezione multipla)|
|`getSelectionModel().setSelectionMode()`|Imposta `SelectionMode.SINGLE` o `MULTIPLE`|
|`getSelectionModel().clearSelection()`|Deseleziona tutto|
|`setPrefHeight(double)`|Altezza preferita in pixel|
|`setPrefWidth(double)`|Larghezza preferita in pixel|

## Componente ChoiceBox

**Ruolo**: simile a ListView ma mostra gli elementi in una tendina che si apre al clic. Occupa meno spazio.

|**Metodo**|**Descrizione**|
|---|---|
|`getValue()`|Restituisce l'elemento attualmente selezionato|
|`setValue(T)`|Imposta programmaticamente l'elemento selezionato|
|`setItems(ObservableList)`|Popola la tendina|
|`getItems()`|Permette di accedere e modificare gli elementi|
|`setPrefWidth(double)`|Larghezza della tendina|
|`setOnAction(EventHandler)`|Listener chiamato quando si sceglie un valore|

**Differenza con ComboBox**: ChoiceBox non è editabile (non si può scrivere). ComboBox sì, con `setEditable(true)`.

## Componente Spinner

**Ruolo**: campo con frecce su/giù per selezionare un valore numerico o da una lista.

Per numeri interi

```java
Spinner<Integer> spinner = new Spinner<>(min, max, valoreIniziale);
// Esempio: da 0 a 10, parte da 5
Spinner<Integer> spin = new Spinner<>(0, 10, 5);
```

|**Metodo**|**Descrizione**|
|---|---|
|`getValue()`|Restituisce il valore corrente|
|`getValueFactory().setValue(T)`|Imposta forzatamente un nuovo valore|
|`valueProperty()`|Proprietà osservabile per il ChangeListener|
|`increment()` / `increment(int)`|Aumenta il valore di uno step (o _n_ step) da codice|
|`decrement()` / `decrement(int)`|Diminuisce il valore di uno step (o _n_ step) da codice|
|`setEditable(boolean)`|Permette all'utente di digitare il numero direttamente|

## Componenti ProgressBar e ProgressIndicator

**Ruolo**: mostrare l’avanzamento di un’operazione (0.0 = 0%, 1.0 = 100%).

Costruzione

```java
ProgressBar bar = new ProgressBar(0.25);     // 25%
ProgressIndicator indicator = new ProgressIndicator(0.25);
```

|**Metodo**|**Descrizione**|
|---|---|
|`setProgress(double)`|Imposta l'avanzamento (da 0.0 a 1.0). Se passiamo `-1.0`, diventa indeterminata (animazione continua)|
|`getProgress()`|Legge l'avanzamento corrente|
|`progressProperty()`|Proprietà per il binding o ChangeListener|

## Componente DatePicker

**Ruolo**: selezione data tramite calendario pop‑up.

Costruzione

```java
DatePicker datePicker = new DatePicker();
```

|**Metodo**|**Descrizione**|
|---|---|
|`getValue()`|Restituisce la data selezionata come `LocalDate` (può essere `null`)|
|`setValue(LocalDate)`|Imposta programmaticamente la data|
|`setPromptText(String)`|Mostra un testo di suggerimento se vuoto|
|`setShowWeekNumbers(bool)`|Mostra i numeri della settimana nel calendario popup|
|`setEditable(boolean)`|Permette la digitazione testuale della data|

## Componente ColorPicker

**Ruolo**: selezionare un colore tramite una tavolozza.

Costruzione

```java
ColorPicker colorPicker = new ColorPicker();
```

|**Metodo**|**Descrizione**|
|---|---|
|`getValue()`|Restituisce un oggetto di tipo `Color`|
|`setValue(Color)`|Imposta un colore di default|
|`getCustomColors()`|Restituisce/modifica la lista dei colori custom salvati dall'utente|
|`setOnAction(EventHandler)`|Evento lanciato quando si sceglie il colore|

## Componente ImageView

**Ruolo**: visualizzare un’immagine.

Costruzione

```java
Image image = new Image("file:alberi.jpg"); // da file
ImageView imageView = new ImageView(image);
```

|**Metodo (ImageView)**|**Descrizione**|
|---|---|
|`setImage(Image)`|Sostituisce l'immagine visualizzata|
|`getImage()`|Restituisce l'oggetto `Image` caricato|
|`setFitWidth(double)`|Ridimensiona alla larghezza desiderata|
|`setFitHeight(double)`|Ridimensiona all'altezza desiderata|
|`setPreserveRatio(boolean)`|Se `true`, mantiene le proporzioni originali durante il resize|
|`setSmooth(boolean)`|Applica un filtro di qualità al resize dell'immagine|

## Dialogo FileChooser

**Ruolo**: finestra modale per aprire o salvare file.

Creazione

```java
FileChooser chooser = new FileChooser();
chooser.setTitle("Seleziona un file");
```

|**Metodo**|**Descrizione**|
|---|---|
|`setTitle(String)`|Imposta il titolo della finestra di dialogo|
|`setInitialDirectory(File)`|Apre il FileChooser in una cartella specifica di partenza|
|`getExtensionFilters()`|Restituisce la lista a cui fare `.add()` per i filtri di formato|
|`showOpenDialog(Window)`|Apre la finestra di selezione (singola) e restituisce un `File`|
|`showSaveDialog(Window)`|Apre la finestra di salvataggio e restituisce un `File`|
|`showOpenMultipleDialog(...)`|Restituisce una `List<File>` permettendo la selezione multipla|

## Grafica a pixel con Canvas

Un componente `Canvas` funge da vera e propria tela sulla quale poter disegnare grafici a pixel. Come qualsiasi altro elemento grafico di JavaFX, può essere aggiunto ai figli del pannello principale.

Per iniziare a tracciare forme, linee o testi, dal Canvas si deve recuperare l'entità denominata contesto grafico (`GraphicsContext`). Questo oggetto offre tutti i metodi operativi necessari, ad esempio quelli contrassegnati dal prefisso `strokeXXX` (per disegnare contorni vuoti) e `fillXXX` (per disegnare forme piene).

**Creazione del Canvas ed esempi base:**

```java
Canvas canvas = new Canvas(150, 130);
panel.getChildren().add(canvas); // Aggiunta del Canvas alla lista dei figli del pannello [cite: 6, 8]

GraphicsContext g = canvas.getGraphicsContext2D(); // Recupero dell'entità che serve per disegnare [cite: 9, 12, 27]

// Uso dei metodi grafici del GraphicsContext [cite: 10, 30]
// Lo specifico font si imposta tramite la factory internalizzata Font.font [cite: 29]
g.setFont(Font.font("Serif", FontWeight.BOLD, 20)); 
g.setFill(Color.RED); // Colore figure piene [cite: 30, 32]
g.fillRect(20, 20, 100, 80); // Rettangolo pieno [cite: 30]

g.setFill(Color.BLUE);
g.strokeRect(30, 30, 80, 60); // Rettangolo (solo contorno) [cite: 30]
g.fillText("ciao", 50, 60);   // Testo sul canvas [cite: 30]
```

### Disegnare grafici di funzione su Canvas

Per effettuare il grafico di una funzione (ad esempio sinusoidale o fratta), poiché nell'API base non esiste un oggetto di tipo `FloatUnaryOperator`, si utilizza l'interfaccia funzionale generica `Function<Float, Float>`. Tramite il metodo `apply` si calcola programmaticamente la coordinata Y (es. `float y = f.apply(x);`).

Occorre in seguito implementare un algoritmo di trasformazione delle coordinate (da logiche della funzione a fisiche in pixel del display), applicando dei fattori di scala calcolati rispetto alle dimensioni del Canvas e ai limiti degli assi `xAxisMin`, `xAxisMax`, `yAxisMin` e `yAxisMax` preimpostati.

```java
// Variabili d'appoggio per calcolare i fattori di scala e convertire la funzione logica in pixel fisici
float fattoreDiScalax = larghezza / ((float) xAxisMax - xAxisMin); [cite: 46]
float fattoreDiScalay = altezza / ((float) yAxisMax - yAxisMin); [cite: 46]

// 1. Scorrimento della X sui pixel della tela del canvas [cite: 75]
for (int ix = 1; ix < g.getCanvas().getWidth(); ix++) {
    // Trasformazione del pixel (ix) nella coordinata X logica
    float x = xAxisMin + ((float) ix) / fattoreDiScalax; [cite: 75]
    
    // Calcolo della corrispondente Y chiamando la lambda function 
    float y = f.apply(x); 
    
    // 2. Passaggio delle coordinate alla funzione di utilità custom (es: setPixel o setLine) [cite: 81]
    setLine(g, xPrev, yPrev, x, y); 
    xPrev = x; 
    yPrev = y; [cite: 81]
}
```

**Regole e calcoli nella stampa dei punti:**

All'interno dei metodi personalizzati (`setPixel` e `setLine`), il passaggio chiave è il calcolo degli indici matematici finali.

- L'indice x: `int ix = Math.round((x - xAxisMin) * fattoreDiScalax);`.
- L'indice y (sottratto all'altezza totale per l'inversione dell'asse Y classico dei display): `int iy = altezza - Math.round((y - yAxisMin) * fattoreDiScalay);`.

Un punto cruciale per prevenire anomalie visive è aggiungere un controllo all'inizio di queste routine per accertarsi che le coordinate restino entro i margini grafici (es. verificando che `x < xAxisMin || x > xAxisMax || y < yAxisMin || y > yAxisMax` generino un `return`). Questo accorgimento è di vitale importanza soprattutto quando si affronta il grafico di funzioni discontinue; se omettiamo i controlli (ed impieghiamo la funzione `setLine`), JavaFX finirà per collegare visivamente i salti della funzione originando falsi segmenti verticali. Infine, per accendere fisicamente un singolo pixel dopo averne tracciato le coordinate logiche si usa `g.strokeLine(ix, iy, ix, iy);` (per un punto) oppure `g.strokeLine(ixP, iyP, ix, iy);` (per collegare due punti continui).

## Schema generale per i grafici XY

Tutti i grafici basati su assi (BarChart, LineChart, AreaChart, ScatterChart, BubbleChart) seguono gli stessi passi.

**Passi da compiere**

1. **Predisporre gli assi** – di solito un `CategoryAxis` (categorie testuali) e un `NumberAxis` (valori numerici). Per BubbleChart servono due `NumberAxis` (x e y entrambi numerici).
2. **Creare l’oggetto Chart** – es. `new BarChart<>(asseX, asseY)`. Impostare il titolo con `setTitle(String)`.
3. **Predisporre le serie di dati** – una o più istanze di `XYChart.Series<X, Y>`.
4. **Popolare ciascuna serie** – aggiungere `new XYChart.Data<>(xValue, yValue)` alla serie.
5. **Aggiungere le serie al grafico** – `chart.getData().addAll(serie1, serie2, ...)`.

### Tipi di assi

|**Asse**|**Tipo**|**Uso**|
|---|---|---|
|`CategoryAxis`|valori testuali (categorie)|per l’asse orizzontale di BarChart, LineChart, AreaChart, ScatterChart|
|`NumberAxis`|valori numerici|per l’asse verticale (o per entrambi in BubbleChart)|

## Componente BarChart

Grafico a barre: una barra per ogni coppia (categoria, valore). Più serie affiancano le barre.

### Esempio – una sola serie (vendite di frutta a Modena)

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EsempioBarChart extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("BarChart esempio");
        
        // 1. Assi
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tipi di frutta");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Vendite");
        
        // 2. Grafico
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Andamento vendite frutta");
        
        // 3. Serie
        XYChart.Series<String, Number> modena = new XYChart.Series<>();
        modena.setName("Modena");
        
        // 4. Popolamento
        modena.getData().add(new XYChart.Data<>("Mele", 30));
        modena.getData().add(new XYChart.Data<>("Pere", 15));
        modena.getData().add(new XYChart.Data<>("Arance", 50));
        
        // 5. Aggiunta al grafico
        chart.getData().add(modena);
        
        FlowPane root = new FlowPane(chart);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
```

**Nota**: i tipi generici `BarChart<String, Number>` e `XYChart.Series<String, Number>` devono corrispondere al tipo dell’asse delle categorie (String) e a quello dell’asse dei valori (Number).

## Componente BubbleChart

Grafico a bolle: ogni dato ha coordinate x, y (entrambe numeriche) e la bolla ha una dimensione opzionale (di solito si usa un terzo valore). In JavaFX base, `BubbleChart` richiede due `NumberAxis` e i dati sono `XYChart.Data<Number, Number>`.

### Esempio – parabola e retta

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EsempioBubbleChart extends Application {
    @Override
    public void start(Stage stage) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y");
        
        BubbleChart<Number, Number> chart = new BubbleChart<>(xAxis, yAxis);
        chart.setTitle("Funzioni varie");
        
        // Serie parabola (y = x^2 / 10 approssimata)
        XYChart.Series<Number, Number> parabola = new XYChart.Series<>();
        parabola.setName("parabola");
        parabola.getData().addAll(
            new XYChart.Data<>(0, 0),
            new XYChart.Data<>(10, 10),
            new XYChart.Data<>(15, 22),
            new XYChart.Data<>(20, 40),
            new XYChart.Data<>(30, 90)
        );
        
        // Serie retta a 45°
        XYChart.Series<Number, Number> retta = new XYChart.Series<>();
        retta.setName("retta 45");
        retta.getData().addAll(
            new XYChart.Data<>(0, 0),
            new XYChart.Data<>(5, 5),
            new XYChart.Data<>(15, 15),
            new XYChart.Data<>(20, 20),
            new XYChart.Data<>(30, 30),
            new XYChart.Data<>(40, 40),
            new XYChart.Data<>(50, 50)
        );
        
        chart.getData().addAll(parabola, retta);
        
        FlowPane root = new FlowPane(chart);
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }
}
```

**Attenzione**: il costruttore di `BubbleChart` si aspetta due `NumberAxis`. La dimensione della bolla può essere controllata con `setExtraValue()` (non mostrato qui).

## Componente PieChart (grafico a torta)

Non usa assi. I dati sono una `ObservableList<PieChart.Data>`.

### Passi

1. Creare la lista di `PieChart.Data` (etichetta, valore).
2. Creare il `PieChart` passando la lista nel costruttore.
3. (Opzionale) impostare titolo e animazione.

**Nota**: `PieChart.Data` non è tipizzato; i valori sono `double`.

## Animazione dei grafici (aggiornamento dinamico)

Tutti i grafici supportano l’animazione automatica quando i dati cambiano. Basta abilitarla con `setAnimated(true)` (di solito è già true di default, ma è meglio impostarlo esplicitamente).

### Esempio di aggiornamento su BarChart

```java
// Supponendo di avere una serie 'ferrara' già aggiunta al grafico
chart.setAnimated(true);

Button aggiorna = new Button("Aggiorna Ferrara");
aggiorna.setOnAction(event -> {
    // Modifica i valori Y della serie
    ferrara.getData().get(0).setYValue(45);  // Mele
    ferrara.getData().get(1).setYValue(5);   // Pere
    ferrara.getData().get(2).setYValue(69);  // Arance
});
```

**Importante**: per i PieChart, la lista `dati` deve essere quella originale passata al costruttore del `PieChart`. Modificando i valori con `setPieValue()`, il grafico si aggiorna con animazione.

## Tabella riassuntiva componenti più usati

|**Componente**|**Costruttore tipico**|**Evento principale**|**Lettura valore**|**Scrittura valore**|
|---|---|---|---|---|
|Label|`new Label("testo")`|nessuno|`getText()`|`setText()`|
|Button|`new Button("testo")`|`setOnAction`|–|–|
|TextField|`new TextField()`|`setOnAction` (INVIO)|`getText()`|`setText()`|
|TextArea|`new TextArea()`|–|`getText()`|`setText()` / `appendText()`|
|ComboBox|`new ComboBox<>()`|`setOnAction`|`getValue()`|`setValue()`|
|Slider|`new Slider(min,max,val)`|`valueProperty().addListener`|`getValue()`|`setValue()`|
|ToggleButton|`new ToggleButton("testo")`|`setOnAction`|`isSelected()`|`setSelected()`|
|CheckBox|`new CheckBox("testo")`|`setOnAction`|`isSelected()`|`setSelected()`|
|RadioButton|`new RadioButton("testo")`|via ToggleGroup|`getSelectedToggle()`|`setSelected()`|
|ListView|`new ListView<>()`|`selectedItemProperty().addListener`|`getSelectionModel().getSelectedItem()`|`setItems()`|
|ChoiceBox|`new ChoiceBox<>()`|`setOnAction`|`getValue()`|`setValue()`|
|Spinner|`new Spinner<>(min,max,init)`|`valueProperty().addListener`|`getValue()`|`getValueFactory().setValue()`|
|ColorPicker|`new ColorPicker()`|`setOnAction`|`getValue()`|`setValue()`|
|DatePicker|`new DatePicker()`|`setOnAction`|`getValue()`|`setValue()`|
|ProgressBar|`new ProgressBar(val)`|`progressProperty().addListener`|`getProgress()`|`setProgress()`|
