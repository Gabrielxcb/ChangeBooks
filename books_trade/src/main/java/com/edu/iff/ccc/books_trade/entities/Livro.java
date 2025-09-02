@Entity

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull;
    private Long id;

    @NotEmpty(message = "O nome do livro não pode ser vazio.")
    private String titulo;

    @NotEmpty(message = "O nome do autor não pode ser vazio.")
    private String autor;
}

