[[0 - Index]]
1. **Nome del Progetto e della Cartella:** Deve essere nel formato `CognomeNome-matricola` (es. `RossiMario-0000123456` oppure `Rossi Mario-0000123456`). Sia il progetto su Eclipse che la cartella fisica sul computer devono avere questo nome.
2. **I due file da consegnare:** Devi caricare su EOL (Esami On Line) **esattamente due file distinti**:
   - **Il file ZIP del progetto:** Deve chiamarsi `CognomeNome-matricola.zip`.
   - **Il file JAR eseguibile:** Deve chiamarsi `CognomeNome-matricola.jar`.
3. **Restrizioni e Attenzioni:**
   - **Il formato dell'archivio:** Deve essere **solo ed esclusivamente .zip**. Non sono accettati formati .7z, .rar o altri.
   - **Contenuto dello ZIP:** Deve contenere l'intero progetto, in particolare assicurati che ci siano i file sorgente `.java` (non solo i compilati `.class`). Non devi includere il file PDF del testo dell'esame.
   - **Il file JAR:** Deve essere un "JAR eseguibile", ovvero deve contenere l'indicazione della classe `main` da lanciare.

Ecco i passaggi pratici per soddisfare questi requisiti utilizzando Eclipse:
#### 0. Prima di Iniziare
Rinomina subito la cartella con lo startkit in`CognomeNome-matricola` (es. `RossiMario-0000123456`)

**Sei pronto per cominciare a scrivere il codice :)**
#### 1. Rinominare il Progetto e la Cartella
1. Vai nel **Package Explorer** (o Project Explorer) di Eclipse, fai clic col tasto destro sul nome attuale del tuo progetto (che al momento sembra essere "2021-07-06-RupestriFerrovieDentinia").
2. Scegli **Refactor > Rename...**
3. Inserisci il nuovo nome nel formato `CognomeNome-matricola` (es. `RossiMario-0000123456`) e dai OK.
4. (Se hai d**imenticato lo step 0**)_Attenzione alla cartella fisica:_ A volte Eclipse rinomina il progetto ma non la cartella sul disco. Per sicurezza, clicca col tasto destro sul progetto, fai **Show In > System Explorer** (o Properties > Resource > Location). Verifica che la cartella vera e propria si chiami `CognomeNome-matricola`. Se ha ancora il nome vecchio, chiudi Eclipse, rinominala a mano dal tuo sistema operativo, riapri Eclipse e re-importa il progetto. 
#### 2. Creare il file JAR Eseguibile
1. Fai clic col tasto destro sulla cartella del tuo progetto in Eclipse.
2. Seleziona **Export...**
3. Nella finestra che si apre, espandi la cartella **Java** e seleziona **Runnable JAR file** (è fondamentale che sia _Runnable_, non un JAR normale), poi clicca su _Next_.
4. **Launch configuration:** Dal menu a tendina, seleziona il file che contiene il tuo `main` che avvia l'applicazione . _Nota: devi aver eseguito l'app almeno una volta da Eclipse affinché appaia in questa lista._
5. **Export destination:** Clicca su _Browse..._, scegli dove salvare il file (es. sul Desktop) e chiamalo esattamente `CognomeNome-matricola.jar`.
6. **Library handling:** Lascia l'opzione predefinita (di solito "Extract required libraries into generated JAR").
7. Clicca su **Finish**. (Se appaiono avvisi su warning di compilazione, puoi dare OK).
#### 3. Creare il file ZIP con l'intero progetto
Il modo più sicuro per non dimenticare nulla (soprattutto i file `.txt` e i file `.java`) e rispettare le regole è fare lo ZIP direttamente dal sistema operativo, non da Eclipse:
1. Fai clic col tasto destro sul progetto in Eclipse e seleziona **Show In > System Explorer** (in Windows) per aprire la cartella dove è salvato il progetto.
2. Controlla di avere davanti la tua cartella nominata `CognomeNome-matricola`.
3. Assicurati che dentro ci siano la cartella `src` (con i tuoi `.java`), `test`, `bin`, e i vari file `.txt` delle ferrovie. Assicurati di eliminare il PDF del testo se lo avevi messo lì dentro.
4. Fai clic col tasto destro sull'intera cartella `CognomeNome-matricola` e comprimila in ZIP:
   - **Su Windows:** Tasto destro > Invia a > Cartella compressa.
5. Il sistema creerà il file `CognomeNome-matricola.zip`.

Alla fine di questi passaggi, avrai sul tuo desktop (o dove li hai salvati) **solo due file**: il `.zip` e il `.jar`. Sono questi due che dovrai caricare sulla piattaforma per concludere l'esame.