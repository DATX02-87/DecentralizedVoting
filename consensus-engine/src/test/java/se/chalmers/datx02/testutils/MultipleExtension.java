package se.chalmers.datx02.testutils;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

public class MultipleExtension<T extends BeforeAfterAllCallback> implements BeforeAllCallback, AfterAllCallback {
    private final List<T> extensions;

    public MultipleExtension(List<T> extensions) {
        this.extensions = extensions;
    }

    public T getExtension(int i) {
        return extensions.get(i);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        for (BeforeAfterAllCallback ba : extensions) {
            ba.afterAll(context);
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        for (BeforeAfterAllCallback ba : extensions) {
            ba.beforeAll(context);
        }
    }
}
