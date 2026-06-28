package my_shop.common.exceptions;

public class MaxAddressesReachedException extends RuntimeException{
    public MaxAddressesReachedException(String message) {
        super(message);
    }
}
