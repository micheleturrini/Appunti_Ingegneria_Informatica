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
**Flusso di controllo**: l’interazione non avviene in momenti prefissati, ma quando l’utente agisce sui controlli grafici. Non è il programma ad avere il controllo (no `println`/`readln`), bensì l’utente.
**Programmazione a eventi**: si stabilisce come reagire agli eventi che si verificano quando l’utente agisce sui controlli. Il comportamento è descrivibile solo a posteriori come “interaction history” – non predicibile, quindi difficile da progettare e testare.

## Due livelli di operatività in una finestra grafica

1. **Livello pixel**: la finestra è una “tela con puntini colorati”. Si accendono/spegnono singoli pixel. Origine in alto a sinistra, asse y verso il basso.
2. **Livello controlli evoluti**: si usano componenti già pronti (bottoni, slider, liste, aree di testo, ecc.). La finestra funge da contenitore; i componenti vengono aggiunti a un contenitore con un **layout manager** per l’adattamento automatico a diverse dimensioni/rotazioni.

## Livello pixel – esempi e criticità

- Rettangolo pieno, rettangolo vuoto, scritte.
- **Trasformazioni**:
  - Asse y cresce verso il basso → conversione coordinate.
  - Valori reali (x, f(x)) → coordinate intere dei pixel.
  - Gestione di punti in cui la funzione non esiste o è discontinua.

## Livello componenti grafici (widget)

- La finestra è un contenitore su cui “appendere” componenti (come post‑it).
- **Layout manager**: ogni finestra ha un gestore di layout che colloca i widget secondo politiche predefinite (adattamento automatico).
- **Compiti del progettista GUI**:
  - **Parte strutturale**: quali e quanti widget, come disposti.
  - **Parte comportamentale**: come reagire agli eventi.

## Framework grafici in Java: storia

- **AWT** (java.awt): da Java 1 (1996), in disuso.
- **Swing** (javax.swing): da Java 2 (1998), indipendente dalla piattaforma, dal 2015 in maintenance mode.
- **JavaFX** (javafx.*): da Java 8 (2014), da Java 11 modulo separato. Approccio moderno con Stage/Scene, supporto di grafici, effetti, animazioni, descrizione via FXML.

## Da Swing a JavaFX – analogie e differenze

- **Concetti comuni**: evento, ascoltatore (listener), tipologie di widget, impostazione generale.
- **Novità JavaFX**:
  - Palcoscenico (`Stage`) e scena (`Scene`).
  - Applicazione estende `Application`, non serve sempre un `main` esplicito (viene aggiunto automaticamente in JavaFX project, in Eclipse serve plugin).
  - Per disegnare a pixel si usa un `Canvas` (invece di interferire nel processo di disegno del pannello).
  - Supporto nativo per grafici, animazioni, effetti.
  - Possibilità di descrivere la GUI con file FXML.

## Struttura base
Classe che estende `javafx.application.Application`.
![[132-Grafica in JavaFX.pdf#page=22&rect=371,94,710,391|132-Grafica in JavaFX, p.22|400]]
- Metodi ereditati: `
	- **init()** per eventuale set-up
	- **start(Stage stage)** per **creare la GUI** (**Obbligatorio**)
	- **stop()** per eventuale shut-down
- Nello `start`:
  - Si imposta il titolo, dimensioni, posizione dello `Stage`.
  - Si crea un `Pane` (radice), poi una `Scene` (che contiene il `Pane`), infine `stage.setScene(scene)` e `stage.show()`.
  
  ```java
  public class Esempio extends Application {
      public void start(Stage stage) {
          stage.setTitle("Titolo");
          stage.show();
      }
  }
  ```
In un progetto Java standard occorre scrivere il `main` che chiama `launch(args)`; nei progetti JavaFX è automatico.

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//...

public static void main(String[] args) {
	launch(args);
}
```

**Stage**
- **Impostare larghezza/altezza**: `stage.setWidth(200); stage.setHeight(100);`
- **Limiti di ridimensionamento**: `setMinWidth`, `setMaxWidth`, `setMinHeight`, `setMaxHeight`.
- **Posizionamento assoluto**: `stage.setX(300); stage.setY(300);`
- **Posizionamento relativo allo schermo**: recuperare i bounds dello schermo con `Screen.getPrimary().getVisualBounds()`.

```java
public class EsJavaFX02bis extends Application {
	public void start(Stage stage){
		stage.setTitle("Esempio 2");
		stage.setWidth(200);
		stage.setHeight(100);
		javafx.geometry.Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		System.out.println(screen); // per me: 1920 x 1040
		stage.setX(screen.getMinX() + screen.getWidth() - stage.getWidth());
		stage.setY(screen.getMinY() + screen.getHeight() - stage.getHeight());
		stage.show();
	}
}
```
## La Scena (Scene)

- `Scene root, larghezza, altezza, colore`
- Esempio: `Scene scene = new Scene(root, 300, 50, Color.YELLOW);`
- Il `Pane` radice può essere di vari tipi (es. `FlowPane`, `BorderPane`, `AnchorPane`, ecc.).

## Disegno a pixel con Canvas
Si crea un `Canvas( larghezza, altezza )`.
- Si aggiunge al pannello: `panel.getChildren().add(canvas);`
- Si ottiene il `GraphicsContext` con `canvas.getGraphicsContext2D()`.
- Metodi di disegno:
  - `setFill(Color)`, `fillRect(x,y,w,h)`
  - `setStroke(Color)`, `strokeRect(x,y,w,h)`
  - `setFont(Font.font(...))`, `fillText(string, x, y)`
- Conversione coordinate mondo‑schermo per disegnare funzioni matematiche:
  - `int ix = Math.round((x - xMin) * fattoreScalaX);`
  - `int iy = altezza - Math.round((y - yMin) * fattoreScalaY);` (perché y cresce verso il basso).
- Per disegnare una funzione continua: `strokeLine(ixP, iyP, ix, iy)`. Per punti singoli: `strokeLine(ix,iy,ix,iy)`.
- Attenzione alle discontinuità: conviene disegnare solo punti (non collegare segmenti) oppure verificare se il salto è eccessivo.

### Grafica a componenti (senza gestione di eventi)
Strutturalmente, un’applicazione JavaFX è fatta di **nodi**.
Il pannello inserito nella **scena** è il **nodo radice (root node)**
- ogni altro componente aggiunto al pannello è un **nodo-figlio**
- alcuni nodi possono essere **gruppi di altri nodi (altri pannelli)**
#### Layout Pane (gestori di layout)
Non si usano coordinate assolute, ogni **pannello ha un gestore che dispone i figli secondo una politica che si possa adattare alle necessità**.
![[132-Grafica in JavaFX.pdf#page=38&rect=17,42,712,420|132-Grafica in JavaFX, p.38|700]]
Es.
![[132-Grafica in JavaFX.pdf#page=39&rect=34,62,677,429|132-Grafica in JavaFX, p.39|600]]
#### Controlli (javafx.scene.control)
- **Bottoni**: `Button`, `ToggleButton`, `CheckBox`, `RadioButton`
- **Componenti di testo**: `Label` (solo output), `TextField`, `PasswordField`, `TextArea`
- **Liste e tabelle**: `ListView`, `ComboBox`, `ChoiceBox`, `TableView`
- **Regolazioni**: `Slider`, `Spinner`, `DatePicker`, `ColorPicker`
- **Dialoghi**: `FileChooser`, `Alert`
- **Grafici**: `LineChart`, `BarChart`, `PieChart`, `BubbleChart`, ecc.
![[132-Grafica in JavaFX.pdf#page=41&rect=27,43,708,433|132-Grafica in JavaFX, p.41|700]]
#### Label
```java
stage.setTitle("Esempio 6");
FlowPane panel = new FlowPane();
Label l1 = new Label("Etichetta 1");
Label l2 = new Label("Etichetta 2");
l1.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
l2.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 18)); panel.getChildren().addAll(l1,l2);
Scene scene = new Scene(panel,Color.WHITE);
stage.setScene(scene);
stage.show();
```
- `Label l = new Label("Testo");`
- Impostare font: `l.setFont(Font.font("Courier New", FontWeight.BOLD, 24));`
- Aggiungere a un pannello: `panel.getChildren().addAll(l1, l2);`
#### ImageView
```java
stage.setTitle("Esempio 7");
BorderPane panel = new BorderPane();
panel.setCenter(new ImageView( new Image("alberi.jpg", 400, 300, true, false)));
Scene scene = new Scene(panel,Color.WHITE);
stage.setScene(scene); stage.show();
```
#### Componenti di testo input
- `TextField`: riga singola, `setText()`, `getText()`, `setEditable(false)`.
- `PasswordField`: come TextField ma maschera l'input.
- `TextArea`: multi‑riga, `appendText()`, `setPrefColumnCount()`.
Generano opportuni **eventi** se
- si preme **ENTER sulla tastiera** (tranne TextArea)
- **cambia il testo contenuto**

Es GraphicApplication
Classe che estende `Application`, contiene una `TextArea` non editabile come output.
```java
Label l = new Label("Area di output");
ta = new TextArea();
ta.setPrefColumnCount(25);
ta.setEditable(false);

public void main(){ ta.setText("Welcome"); }
public void setTitolo(String titolo){
	this.titolo=titolo;
	primaryStage.setTitle(titolo);
}
public void print(String txt){ ta.appendText(txt); }
```
Il contenuto dell'area è pilotato in toto da programma. Non reagisce all'input dell'utente.
### Event‑Driven Programming
I controlli **generano eventi quando l'utente agisce su di essi** o quando cambiano proprietà.
Bisogna stabilire:
  - **Chi** viene informato (ascoltatore/listener). parte strutturale
  - **Come** reagire (metodo di gestione). parte comportamentale

 **Architettura MVC e MVVM**
- **MVC**: Model (dati), View (grafica), Controller (mediatore). Non totale disaccoppiamento.
- **MVVM**: ViewModel espone proprietà osservabili; JavaFX adotta questo schema (proprietà observable, binding).
Non siamo obbligati a creare tre classi, ma dobbiamo capire l'organizzazione del framework.
![[132-Grafica in JavaFX.pdf#page=53&rect=22,43,705,429|132-Grafica in JavaFX, p.53|600]]

**Eventi in JavaFX**![[132-Grafica in JavaFX.pdf#page=57&rect=25,46,698,410|132-Grafica in JavaFX, p.57|600]]Per gestire un evento si implementa l'interfaccia `EventHandler<T>` con il metodo `handle(T event)`.
Un evento è un oggetto sottoclasse di `java.util.EventObject`.
Tipi comuni:
- `ActionEvent` (pulsanti, combo, invio in TextField)
- `ChangeEvent` (proprietà osservabili)
- `MouseEvent`
- `KeyEvent`






#### Button (Listeners)
premuto genera un evento di azione, ossia un'opportuna istanza di ActionEvent.
Diverse modalità di implementazione dell'ascoltatore:
  1. La classe dell'applicazione implementa `EventHandler<ActionEvent>` e si passa `this`.
  2. Classe separata che implementa `EventHandler`, riceve i riferimenti necessari nel costruttore.
  3. Classe interna anonima.
  4. **Lambda expression** (consigliata per codice breve): `b.setOnAction(event -> { ... });`
  5. **Method reference**: `b.setOnAction(this::myHandle);`

## Esempio Rosso/Blu

- Due pulsanti: uno imposta sfondo rosso, l'altro blu.
- Con un unico listener che discrimina la sorgente (`event.getSource() == b1`) oppure due listener separati (più semplice con lambda).

#### TextField passivo (solo lettura da programma)
- `TextField txt2 = new TextField(); txt2.setEditable(false);`
- Pulsante che copia il contenuto di un TextField editabile in quello non editabile.

#### PasswordField e Tooltip

- `PasswordField` nasconde i caratteri.
- `setTooltip(new Tooltip("Messaggio"))` per aggiungere un tooltip.

#### TextArea con conteggio caratteri

- Pulsante “Aggiorna” che legge `area.getText().length()` e scrive in una Label.

#### ComboBox
**Scegliere elementi da una lista pop-up** prestabilita
![[132-Grafica in JavaFX.pdf#page=79&rect=111,42,613,186|132-Grafica in JavaFX, p.79|600]]
`ComboBox<String> cb = new ComboBox<>();`
- se editabile, permette anche all'utente di scrivere un valore diverso da quelli proposti
- genera un **evento azione quando si sceglie un elemento**
```java
public class EsJavaFX19 extends Application {
    private ComboBox<String> cb;
    private TextField txt1;
    public void start(Stage stage) {
        stage.setTitle("Esempio 19");
        FlowPane panel = new FlowPane();
        cb = new ComboBox<>();
        cb.setPrefWidth(100);
        cb.setItems(
            FXCollections.observableArrayList("Rosso","Giallo","Verde","Blu")
        );
        panel.getChildren().add(cb,txt1);
        cb.setOnAction(
            event -> txt1.setText("Opzione corrente: " + cb.getValue() +
                "(" + cb.getSelectionModel().getSelectedIndex() + ")")
				// getValue recupera l'elemento selezionato
        );
        Scene scene = new Scene(panel);
        stage.setScene(scene);
        stage.show();
    }
}
```
#### DatePicker
`DatePicker picker = new DatePicker();`
Consente di scegliere una data dal calendario pop-up
Il formato dipende dal Locale e dal Formatter impostati
![[132-Grafica in JavaFX.pdf#page=81&rect=41,41,708,243|132-Grafica in JavaFX, p.81|600]]
```java
public class EsJavaFX22 extends Application {
    private DatePicker picker, picker2;
    private DateTimeFormatter formatter;
    private TextField txt1;
    public void start(Stage stage) {
        stage.setTitle("Esempio 22");
        FlowPane panel = new FlowPane();
        picker = new DatePicker();
        picker2 = new DatePicker();
        picker.setOnAction( event -> {
            LocalDate date = picker.getValue();
            txt1.setText("Data selezionata: " + formatter.format(date));
            picker2.setValue(picker.getValue().plusDays(1));
        });
    }

	picker2.setOnAction( event -> {
    if (picker.getValue()==null) return;
    long p = ChronoUnit.DAYS.between(picker.getValue(), picker2.getValue());
    picker2.setOnClickListener(new Tooltip("Durata soggiorno: " + p + " notti"));
});
}
```
#### ColorPicker
`ColorPicker picker = new ColorPicker();`
- `picker.getValue().getRed()`, `getGreen()`, `getBlue()`

## Slider
`Slider slider = new Slider(min, max, initialValue);`
Lo slider è una **barra di regolazione, che consente di scegliere un valore muovendo un cursore su una scala**
```java
public class EsJavaFX21 extends Application {
    private Slider slider;
    private TextField txt1;
    private NumberFormat formatter;
    public void start(Stage stage) {
        formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        stage.setTitle("Esempio 21");
        FlowPane panel = new FlowPane();
        txt1 = new TextField(); txt1.setEditable(false);
        slider = new Slider(0,10,5); // min, max, iniziale
        slider.valueProperty().addListener(
            (changed, oldval, newval) ->
                txt1.setText("Valore corrente: " + formatter.format(newval)
                    + " (era: " + formatter.format(oldval) + ")"
                )
        );
        panel.getChildren().add(slider,txt1);
        Scene scene = new Scene(panel);
        stage.setScene(scene);
        stage.show();
    }
}
```
```java
//mostrare, con etichette, ogni 1 unità
slider.setShowTickMarks(true);
slider.setShowTickLabels(true);
slider.setMajorTickUnit(1);
//mostrare ogni 1 unità
slider.setMinorTickCount(1);
//in caso di movimento tramite tasti freccia: muovere a blocchi di 1 unità
slider.setBlockIncrement(1);
//orza il cursore a stare sui tick
slider.setSnapToTicks(true);
// Orientamento
slider.setOrientation(Orientation.VERTICAL);
```
## Spinner
`Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
Lo Spinner consente di **scegliere un valore muovendo freccine su/giù (o sx/dx)**
- i valori possono essere interi, reali, liste o user-defined
- necessaria factory
- eventuale listener sulla proprietà osservabile **valueProperty**
![[132-Grafica in JavaFX.pdf#page=89&rect=8,46,697,267|132-Grafica in JavaFX, p.89|600]]


## ProgressBar e ProgressIndicator

- `ProgressBar bar = new ProgressBar(0.25);` (25%).
- `bar.progressProperty().addListener(...)`
- `bar.setProgress(0.456);` aggiorna il valore.
- Formattatore per percentuali: `NumberFormat.getPercentInstance()`.

## FileChooser

- Dialogo per aprire/salvare file.
- `FileChooser chooser = new FileChooser();`
- `chooser.setTitle("Apri file");`
- Filtri estensioni: `chooser.getExtensionFilters().addAll(new ExtensionFilter("Testo", "*.txt"), ...);`
- Apertura singola: `File selected = chooser.showOpenDialog(stage);`
- Apertura multipla: `List<File> files = chooser.showOpenMultipleDialog(stage);`
- Salvataggio: `chooser.showSaveDialog(stage);`
- **Attenzione**: l'utente può premere Annulla → gestire il caso `selected == null`.

## Famiglia Toggle (interruttori a due posizioni)

- **ToggleButton**: si preme e rimane premuto (ON), si ripreme e torna OFF.
- **CheckBox**: simile, ma con icona a casella spuntata/non spuntata.
- **RadioButton**: usato in gruppo (`ToggleGroup`), solo uno selezionabile alla volta.

## CheckBox – esempio

- `CheckBox ck = new CheckBox("Opzione");`
- `ck.setOnAction(event -> { if (ck.isSelected()) ... });`
- Gestione di due checkbox: listener separati per ciascuna (per riflettere l’ultima azione) e un metodo comune per lo stato globale.

## RadioButton e ToggleGroup

- `ToggleGroup tg = new ToggleGroup();`
- `RadioButton rb1 = new RadioButton("Op1"); rb1.setToggleGroup(tg);`
- Ascoltatore sul singolo: `rb1.setOnAction(this::handle);` (distingue tramite `event.getSource()`).
- Metodo migliore: osservare la proprietà `selectedToggleProperty()` del gruppo:
  ```java
  tg.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
      String testo = ((RadioButton)newVal).getText();
  });
  ```

## ListView

- `ListView<String> listview = new ListView<>();`
- Popolare: `listview.setItems(FXCollections.observableArrayList("Rosso","Giallo",...));`
- Altezza preferita: `listview.setPrefHeight(listview.getItems().size()*24 + 2);`
- Selezione singola: `listview.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {...});`
- Selezione multipla: `listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);` e per recuperare tutti gli elementi selezionati usare `getSelectedItems()`.
- Modifica dinamica degli elementi: `listview.getItems().add("Nuovo")` o `.remove("Elemento")`.

## Grafici (Chart family)

- **Passi generali per grafici XY**:
  1. Creare gli assi (`CategoryAxis` per categorie, `NumberAxis` per valori).
  2. Creare il grafico (es. `BarChart<X,Y>`, `LineChart`, `BubbleChart`).
  3. Creare una o più serie (`XYChart.Series<X,Y>`).
  4. Popolare la serie con `.getData().add(new XYChart.Data<>(x, y))`.
  5. Aggiungere la/e serie al grafico con `chart.getData().add(series)`.
- **BarChart**:
  - Asse orizzontale: categorie (es. tipi di frutta). Asse verticale: numeri.
  - Più serie affiancate.
- **BubbleChart**:
  - Richiede assi numerici per entrambi gli assi.
  - Ogni punto ha una terza dimensione (raggio della bolla) – opzionale.
- **PieChart**:
  - Nessun asse; un'unica serie di `PieChart.Data`.
  - Popolare con `FXCollections.observableArrayList(new PieChart.Data("Mele", 30), ...)`.
  - Creare `PieChart chart = new PieChart(dati);`
- **Animazione dei grafici**:
  - `chart.setAnimated(true);`
  - Modificare dinamicamente i valori: `serie.getData().get(index).setYValue(nuovoValore);` (per BarChart) o `dati.get(index).setPieValue(nuovoValore);` (per PieChart).

## Scene Builder – progettazione visuale della GUI

- Strumento esterno (Gluon) per disegnare la GUI e generare file FXML.
- **Configurazione in Eclipse**:
  - Installare il plugin e(fx)clipse o il plugin Gluon.
  - Impostare il path dell’eseguibile di Scene Builder in `Window > Preferences > JavaFX`.
- **Flusso di lavoro**:
  1. Creare un nuovo JavaFX project in Eclipse.
  2. Creare un file FXML (tramite New → Other → JavaFX → FXML Document).
  3. Aprire il file FXML con Scene Builder (tasto destro → Open with Scene Builder).
  4. Trascinare i controlli sulla scena, impostare layout e proprietà.
  5. Assegnare identificatori (`fx:id`) per i componenti che verranno usati nel codice.
  6. Assegnare nomi di metodo (`onAction="nomeMetodo"`) per la gestione eventi (opzionale).
  7. Specificare la classe controller nell’elemento root: `fx:controller="pacchetto.NomeController"`.
  8. Salvare il FXML; in Eclipse il file viene aggiornato.
  9. Implementare la classe controller con campi annotati `@FXML` (per i componenti con fx:id) e metodi `@FXML` (per i gestori eventi).
  10. Nel `start` dell'`Application` caricare il FXML:
      ```java
      FXMLLoader loader = new FXMLLoader(getClass().getResource("nomefile.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
      ```
  11. In alternativa, si può non specificare gli `onAction` nel FXML e collegare i gestori nel metodo `initialize()` del controller (chiamato automaticamente dopo il caricamento).

## Annotazioni @FXML e metodo initialize

- `@FXML` davanti a dichiarazioni di campi (es. `private Button mioBottone;`) e metodi privati che devono essere accessibili dal loader.
- Metodo `initialize()`: viene eseguito automaticamente dopo che tutti i componenti FXML sono stati iniettati; utile per inizializzare listener, popolare liste, ecc.

## Personalizzazione dinamica (es. cambiare colore pulsanti a ogni click)

- Nel controller, nel metodo gestore, modificare lo stile CSS del bottone:
  ```java
  bottone.setStyle("-fx-background-color: red;");
  ```
- Oppure cambiare testo, abilitazione, ecc.

## Considerazioni finali

- JavaFX permette sia la programmazione a basso livello (pixel con Canvas) sia ad alto livello (controlli e layout).
- La gestione degli eventi è flessibile (lambda, method reference, classi separate).
- L'architettura MVVM è favorita dalle proprietà osservabili (`Property`, `ObservableList`).
- Scene Builder semplifica la progettazione della GUI e separa la struttura (FXML) dalla logica (controller).