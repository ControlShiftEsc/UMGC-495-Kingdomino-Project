import java.io.Serial;

public class InvalidPlayerNumberException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public InvalidPlayerNumberException(String errorMessage) {
    super(errorMessage);
  }
}
