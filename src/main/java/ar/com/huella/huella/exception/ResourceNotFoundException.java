package ar.com.huella.huella.exception;


public class ResourceNotFoundException extends RuntimeException{
      public ResourceNotFoundException(String message) {
        super(message);
    }
}
