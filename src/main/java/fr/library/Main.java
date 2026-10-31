package fr.library;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Library library = new Library();

        library.addBook(new Book("9780132350884", "Clean Code", "Robert Martin", 2008));
        library.addBook(new Book("9780134685991", "Effective Java", "Joshua Bloch", 2018));

        System.out.println("Nombre de livres au départ : " + library.size());

        library.displayBooks();

        System.out.println("--- Recherche du livre 'Effective Java' ---");
        Book foundBook = library.findBookByTitle("Effective Java");
        if (foundBook != null) {
            System.out.println("Livre trouvé : " + foundBook);
        } else {
            System.out.println("Livre non trouvé.");
        }
        System.out.println();

        System.out.println("--- Recherche des livres de 'Robert Martin' ---");
        List<Book> robertBooks = library.findByAuthor("Robert Martin");
        for (Book book : robertBooks) {
            System.out.println("- " + book.getTitle());
        }
        System.out.println();

        System.out.println("--- Vérification de l'existence d'un ISBN ---");
        String isbnToTest = "9780134685991";
        System.out.println("Est-ce que l'ISBN " + isbnToTest + " existe ? " + library.containsIsbn(isbnToTest));
        System.out.println();

        System.out.println("--- Suppression du livre 'Clean Code' (ISBN: 9780132350884) ---");
        library.removeBook("9780132350884");
        System.out.println();

        System.out.println("--- Contenu final de la bibliothèque ---");
        library.displayBooks();

        boolean existe = library.containsIsbn("9780134685991");
        System.out.println("Le livre existe-t-il ? " + existe);

    }
}

