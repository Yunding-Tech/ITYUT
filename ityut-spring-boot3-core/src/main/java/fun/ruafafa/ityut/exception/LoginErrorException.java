package fun.ruafafa.ityut.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginErrorException extends RuntimeException {
    public LoginErrorException(String message) {
        super(message);
    }
}
