package tangenziale.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import tangenziale.model.Autostrada;
import tangenziale.model.Tipologia;

public class MyAutostradeReader implements AutostradeReader {

	// SONO DUE WHILE INNESTATI, è POSSIBILE FARE UNA FUNZIONE PRIVATA AUSILIARIA
	// CHE TROVA I CASELLI E INSERIRE SOLO IL METODO DI ESSA NEL WHILE PER LA
	// PERSISTENZA DEL PROFILOs

	public Map<String, Autostrada> leggiAutostrade(Reader reader) throws IOException, BadFileFormatException {
		// passaggi per fare un apersistenza
		// 1. controllo che il reader non sia nullo
		Objects.requireNonNull(reader, "reader non deve essere nullo");
		// 2. creo il mio buffered reader
		var myReader = new BufferedReader(reader);

		// 3. creo la mappa/lista/collection/array dove andrò a mettere le mie cose
		// lette
		Map<String, Autostrada> profili = new TreeMap<>();

		// 4. stringa di supporto dove immagazzinare le cose lette momentaneamente
		String line;
		// 5. ciclo per leggere (in questo caso serve per l'eggere l'intestazione che è
		// una riga solo e dice il tipo della strada)
		while ((line = myReader.readLine()) != null) {
			// 6.se trovo uno spazio bianco lo salto
			if (line.isBlank())
				continue;
			// 7.variabile dove vado a inserire il primo elemento letto, in questo caso è il
			// tipo della strada
			var tipologia = (line.startsWith(Autostrada.TANGENZIALE)) ? Tipologia.TANGENZIALE : Tipologia.AUTOSTRADA;
			// 8. memorizzo in una stringa quello che ho letto ovvero il tipo della strada
			String id = line.trim();

			// 9. creo un amappa dove inserire tutti i caselli letti e i loro rispettivi
			// chilometri
			SortedMap<Integer, String> caselli = new TreeMap<>();
			// 10. il ciclo try serve per verificare che non ci sia una fine del file
			// inattesa
			try {
				// 11. ciclo per leggre i cselli appartenenti alle strade
				while (!(line = myReader.readLine()).equals(AutostradeReader.END_TAG)) {
					// 12. array di stringhe necessario per immagazzinare i pezzi di riga e
					// assegnarli alle rispettive variabili
					String[] items = line.split("\\s*\\t+\\s*");
					// 13. controllo che nell'array di itmes ci sioano solo 2 elementi altrimenti il
					// file è fatto male
					if (items.length != 2)
						throw new BadFileFormatException("formato riga errato: " + line);
					// 14. inizializzo la mia varibaile dove mettero i kilometri letti
					int km;
					// 15. verifico che la mia varibile dichiarata venga parsata a intero da stringa
					try {
						km = Integer.parseInt(items[0]);
					}
					// 16. e se il parsing non avviene correttamente lancio una number formata
					// exception
					catch (NumberFormatException e) {
						throw new BadFileFormatException("progressiva km illegale nella riga: " + line);
					}
					// 17. array di stringhe necessario per immagazzinare i pezzi di riga e
					// assegnarli alle rispettive variabili
					String nome = items[1];

					// --- controlli di consistenza sulla singola autostrada ---
					//
					// NB: il controllo sull'esistenza di due caselli omonimi lo farebbe giÃ 
					// Autostrada,
					// quindi questo Ã¨ sovrabbondante
					if (caselli.entrySet().stream().map(Entry::getValue).filter(n -> n.equals(nome)).count() > 0)
						throw new BadFileFormatException("Impossibile avere due caselli omonimi : " + line);

					// Invece, Autostrada non puÃ² verificare se nel file ci fossero due progressive
					// km identiche
					// perchÃ© al secondo inserimento in mappa, il secondo sovrascriverebbe
					// semplicemente il primo
					// Quindi, occorre controllarlo qui, prima di effettuare l'inserimento
					if (caselli.get(km) != null)
						throw new BadFileFormatException(
								"Impossibile avere due caselli alla stessa progressiva km: " + line);
					// 18. letti eimmagazzinati nelle variabili tutti i pezzi di riga li inserisco
					// nella mappa (pezzo necessario alla costruzione dei profili stradali)
					caselli.put(km, nome);
				}
				// 19.letti eimmagazzinati nelle variabili tutti i pezzi di riga li inserisco
				// nella mappa
				profili.put(id, new Autostrada(tipologia, id, caselli));
			} catch (NullPointerException e) {
				throw new BadFileFormatException("Fine file inattesa, file terminato inaspettatamente");
			}
		}

		// --- controlli di consistenza globale sulla rete autostradale nel suo
		// complesso ---
		if (profili.entrySet().stream().map(Entry::getValue).map(Autostrada::tipologia)
				.filter(t -> t == Tipologia.TANGENZIALE).count() != 1)
			throw new BadFileFormatException("Manca la tangenziale!");

		return profili;
	}

}

/*
 * if (reader == null) throw new IllegalArgumentException("reader is null");
 * BufferedReader rdr = new BufferedReader(reader); Map<String, Autostrada> map
 * = new TreeMap<>(); SortedMap<Integer, String> mapAuto = new TreeMap<>();
 * String line; Tipologia tipo = null; int autoInt = 0; while ((line =
 * rdr.readLine()) != null) { if (line.isBlank()) { continue; } // skip blank
 * lines}
 * 
 * String[] parts = line.split("\\t");
 * 
 * String tipoAsString = parts[0]; if (parts.length != 1) throw new
 * BadFileFormatException("badly formatted line");
 * 
 * if(parts[0].isEmpty()) throw new
 * BadFileFormatException("non ci sono i chilometri");
 * 
 * if(parts[1].isEmpty()) throw new
 * BadFileFormatException("non c'è il nome dell casello");
 * 
 * if (parts.length == 1) { tipo = Tipologia.valueOf(tipoAsString); }
 * 
 * autoInt = Integer.valueOf(autoInt);
 * 
 * mapAuto.put(autoInt, parts[1]);
 * 
 * if (parts[0].equals(END_TAG)) { Autostrada auto = new Autostrada(tipo,
 * parts[1].trim(), mapAuto); map.put(tipoAsString, auto); }
 * 
 * } return map;
 * 
 * }
 * 
 * }
 */
