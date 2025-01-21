package ntcgoer.sharingmodule.http;

@FunctionalInterface
public interface HttpFunction<R> {
    R trigger();
}
