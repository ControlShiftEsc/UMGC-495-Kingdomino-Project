import java.io.Serial;

public class InvalidPlayerNameException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public InvalidPlayerNameException(String errorMessage) {
    super(errorMessage);
  }
}
