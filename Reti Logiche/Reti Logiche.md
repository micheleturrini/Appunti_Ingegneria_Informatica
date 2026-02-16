[[0 - Index]]
Simulatore Digital

## Macchine Digitali
I microchip sono **schede elettroniche miniaturizzate** composte da **transistor**.
La M. Digitale è un sistema artificiale:
- progettato per **immagazzinare**, **elaborare** e **comunicare** **informazioni**
- impiegando **segnali digitali**, ovvero **grandezze fisiche** contraddistinte da un insieme discreto (finito, non continuo) di valori significativi

Le **grandezze fisiche** che noi **percepiamo** sono **analogiche**.

> [!attention]
> Perché in informatica si usa il **digitale**?

Es. segnali di fumo.
**Analogico** (la dimensione della nuvola definisce il colore delle tende)
In un segnale analogico l’informazione è rappresentata da ogni possibile valore.
![[1_segnali_codifica.pdf#page=13&rect=21,60,701,471|1_segnali_codifica, p.13|500]]
In teoria posso **facilmente** rappresentare **infinite informazioni** ma in pratica **ogni piccola perturbazione (rumore)** trasforma il mio segnale in uno valido ma di **significato differente**.
Trasmettere e ricevere è **difficile e costoso.**
![[1_segnali_codifica.pdf#page=14&rect=21,268,354,437|1_segnali_codifica, p.14|200]]

**Digitale**
 In un segnale digitale, invece, **è l’intervallo in cui si trova la grandezza** a rappresentare l’informazione. (se la nuvola è alta tra 0 e 1 metri = viola).
![[1_segnali_codifica.pdf#page=15&rect=21,65,704,467|1_segnali_codifica, p.15|500]]
Accetto di rappresentare **meno informazioni** con una stessa grandezza fisica per ottenere **maggior robustezza** nel trasferire l’informazione e **minor complessità** (e costo) dei dispositivi necessari.
![[1_segnali_codifica.pdf#page=17&rect=25,23,314,169|1_segnali_codifica, p.17|200]]

> [!success]
> Il segnale binario è più **robusto e economico.** (passa meno info)
> Al di sotto di un segnale binario c'è un segnale analogico interpretato in modo binario.

**Variabili Binarie** (Bit)
possono assumere i due soli valori «0» e «1»
**non sono numeri,** ma solo valori logici (**simboli**) che indicano se il **segnale è sopra o sotto soglia.
![[1_segnali_codifica.pdf#page=19&rect=36,16,670,178|1_segnali_codifica, p.19|500]]

Un singolo bit trasmette pochissima informazione quindi **utilizzo più bit**. (più fuochi)
Essendo due gli stati di un bit il numero di stringhe diverse è uguale alle **potenze di 2.**
![[1_segnali_codifica.pdf#page=22&rect=38,85,689,248|1_segnali_codifica, p.22|400]]

I **Transistor**
Un transistor è un interruttore ad azionamento elettronico
- estremamente precisi ed economici.
- **ingresso ed uscita della stessa natura**, entrambe elettriche: è possibile usare l’**uscita di un transistor per pilotarne altri**.