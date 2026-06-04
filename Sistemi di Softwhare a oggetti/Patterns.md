## Persistenza

MyReader Template
```java
public class MyReader implements Reader {
	@Override
	public Train read(Reader rdr) throws IOException {
if (reader == null)

throw new IllegalArgumentException("reader null");

BufferedReader br = new BufferedReader(reader);

  

//Dichiara dover salvi i dati

Menu result = new Menu();

  

String line;

while ((line = br.readLine()) != null) {

  

// skip empty lines

// if (line.isBlank()) continue;

String[] tokens = line.split("\\t+");

  

if (tokens.length != TOKEN_COUNT)

throw new BadFileFormatException("wrong token number on line" + line);

  

/*

* try { //parsing } catch (DateTimeParseException e) { throw new

* BadFileFormatException("Error in time formatting" + line); }

*/

return result;

}

}
}
```