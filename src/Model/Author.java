package Model;

public class Author {
        private int authorId;
        private String authorName;
        private String nationality;

        public Author(int authorId, String authorName, String nationality) {
            this.authorId = authorId;
            this.authorName = authorName;
            this.nationality = nationality;
        }

        public int getAuthorId() {
            return authorId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getNationality() {
            return nationality;
        }

        public void displayAuthor() {
            System.out.println("Author ID: " + authorId);
            System.out.println("Author Name: " + authorName);
            System.out.println("Nationality: " + nationality);
        }

}
