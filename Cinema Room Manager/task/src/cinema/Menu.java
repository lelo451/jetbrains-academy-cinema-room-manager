package cinema;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static java.text.MessageFormat.format;

public interface Menu extends Runnable {
    static Menu create() {
        return new SimpleMenu();
    }

    static Menu create(String title) {
        return new SimpleMenu().set(Property.TITLE, title);
    }

    Menu set(Property property, String value);
    Menu add(String key, String description, Runnable action);
    Menu add(String description, Runnable action);
    Menu disable();
    Menu disable(String key);
    Menu enable(String key);
    Menu onlyOnce();
    Menu addExit();

    enum Property {
        TITLE("Choose your action:"),
        FORMAT("{0}. {1}"),
        ERROR("Please enter the number from 1 to {0} or 0 for exit."),
        EXIT("Exit"),
        EXIT_KEY("0");

        private final String value;

        Property(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class SimpleMenu implements Menu {

        protected final Map<String, MenuEntry> menu = new LinkedHashMap<>();
        protected final Map<Property, String> properties = new EnumMap<>(Property.class);
        protected MenuEntry defaultEntry = new MenuEntry("Incorrect option",
                () -> System.out.println(format(get(Property.ERROR), menu.size())));
        protected boolean isOnlyOnce;
        protected Scanner scanner = new Scanner(System.in);

        @Override
        public SimpleMenu set(Property property, String value) {
            properties.put(property, value);
            return this;
        }

        @Override
        public SimpleMenu add(String key, String description, Runnable action) {
            menu.put(key, new MenuEntry(description, action));
            return this;
        }

        @Override
        public SimpleMenu add(String description, Runnable action) {
            return this.add(String.valueOf(menu.size() + 1), description, action);
        }

        @Override
        public SimpleMenu disable() {
            disable(String.valueOf(menu.size()));
            return this;
        }

        @Override
        public SimpleMenu disable(String key) {
            menu.get(key).isEnabled = false;
            return this;
        }

        @Override
        public SimpleMenu enable(String key) {
            menu.get(key).isEnabled = true;
            return this;
        }

        @Override
        public SimpleMenu onlyOnce() {
            isOnlyOnce = true;
            return this;
        }

        @Override
        public SimpleMenu addExit() {
            menu.put(get(Property.EXIT_KEY), new MenuEntry(get(Property.EXIT), this::onlyOnce));
            return this;
        }

        @Override
        public void run() {
            do {
                System.out.println();
                System.out.println(get(Property.TITLE));
                menu.forEach((key, entry) -> {
                    if (entry.isEnabled) {
                        System.out.println(format(get(Property.FORMAT), key, entry));
                    }
                });
                final var key = scanner.nextLine().toLowerCase();
                System.out.println();
                menu.getOrDefault(key, defaultEntry).run();
            } while (!isOnlyOnce);
        }

        String get(Property property) {
            return properties.getOrDefault(property, property.getValue());
        }

        static final class MenuEntry implements Runnable {
            private final String description;
            private final Runnable action;
            boolean isEnabled = true;

            MenuEntry(final String description, final Runnable action) {
                this.description = description;
                this.action = action;
            }

            @Override
            public String toString() {
                return description;
            }

            @Override
            public void run() {
                action.run();
            }
        }
    }
}