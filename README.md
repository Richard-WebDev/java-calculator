# Java Calculator

Calcolatrice con interfaccia grafica in **Java**, costruita attorno a una gerarchia di classi che modella le operazioni matematiche.

Progetto realizzato come prova d'esame del modulo Java, all'interno del percorso formativo Web Developer AI presso la Steve Jobs Academy (Catania). La traccia era libera: la scelta del dominio e della struttura delle classi è mia.

---

## L'idea alla base

Una calcolatrice si può scrivere con uno `switch` e quattro righe di aritmetica. L'obiettivo qui era un altro: usare il problema per applicare l'astrazione.

Ogni operazione matematica condivide la stessa natura — ha un simbolo che la identifica e prende due valori restituendo un risultato — ma il calcolo che esegue è diverso ogni volta. È esattamente il caso in cui serve una classe astratta.

`Operation` definisce cosa ogni operazione **deve** avere, senza sapere cosa faranno le sottoclassi:

```java
public abstract class Operation {

    private String nameOperation;

    public Operation(String nameOperation) {
        this.nameOperation = nameOperation;
    }

    public String getNameOperation() {
        return this.nameOperation;
    }

    public abstract double mathOperation(double value1, double value2);
}
```

Il metodo `mathOperation` è astratto: è un contratto che ogni sottoclasse è obbligata a rispettare. Non è possibile creare un'operazione generica, come nella realtà non esiste un "calcolo generico".

---

## Le sottoclassi

Ognuna passa il proprio simbolo al costruttore della classe madre con `super` e implementa il calcolo con `@Override`:

| Classe | Simbolo | Comportamento |
|---|---|---|
| `Addition` | `+` | Somma i due valori |
| `Subtraction` | `-` | Sottrae il secondo dal primo |
| `Multiplication` | `*` | Moltiplica i due valori |
| `Division` | `/` | Divide, lanciando un'eccezione se il divisore è zero |

`Division` è l'unica che aggiunge un controllo: prima di dividere verifica il divisore e, se è zero, lancia un `ArithmeticException` invece di lasciare che il programma produca un risultato non valido.

```java
@Override
public double mathOperation(double value1, double value2) {

    if (value2 == 0) {
        throw new ArithmeticException("Error");
    }

    return value1 / value2;
}
```

---

## Concetti applicati

- **Classe astratta** — `Operation` non è istanziabile e serve solo come base comune
- **Metodo astratto** — `mathOperation` impone un contratto alle sottoclassi
- **Ereditarietà** — `extends` e chiamata al costruttore della classe madre con `super`
- **Override** — ogni sottoclasse ridefinisce il metodo secondo la propria natura
- **Incapsulamento** — il campo `nameOperation` è privato, accessibile solo tramite getter
- **Eccezioni** — gestione della divisione per zero con `throw`

---

## L'interfaccia grafica

L'interfaccia riproduce l'estetica di una calcolatrice tascabile anni Ottanta: corpo bicolore, display su fondo scuro con cifre ciano da tubo VFD, tasto AC rosso.

> **Nota sulla realizzazione:** l'interfaccia grafica Swing è stata generata con l'assistenza dell'intelligenza artificiale, secondo l'indicazione esplicita del docente. L'obiettivo didattico del progetto riguardava la progettazione a oggetti, non la costruzione dell'interfaccia. La modellazione delle classi e la logica delle operazioni sono di mia realizzazione.

---

## Compilazione ed esecuzione

Nessuna dipendenza esterna: Swing fa parte della libreria standard.

```
javac *.java
java CalculatorGUI
```

Richiede un JDK installato. Verifica con `javac --version`.

---

## Sviluppi possibili

- **Sfruttare il polimorfismo nella GUI.** Attualmente l'interfaccia dichiara quattro campi separati, uno per operazione, e sceglie quale usare con uno `switch`. Una `Map<String, Operation>` che associa ogni simbolo alla sua operazione permetterebbe di richiamare `mathOperation` senza sapere quale sottoclasse si sta usando: è il polimorfismo applicato al caso concreto.
- **Messaggi di errore più espliciti.** L'eccezione della divisione porta un messaggio generico.
- **Estendere le operazioni.** La struttura è già predisposta: una nuova operazione richiede solo una nuova sottoclasse di `Operation`, senza toccare le esistenti.
- **Cronologia dei calcoli**, come esercizio sui generici.

---

## Tecnologie

- **Java** — libreria standard
- **Swing** per l'interfaccia grafica
- **Visual Studio Code** come ambiente di sviluppo

---

## Autore

**Riccardo Mazza** — [@Richard-WebDev](https://github.com/Richard-WebDev)
Studente Web Developer AI, Steve Jobs Academy
