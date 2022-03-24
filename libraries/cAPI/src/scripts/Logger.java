package scripts;

// much of this taken from JustJ scripter application
public class Logger {

        private final Class<?> clazz;

        public Logger(Class<?> clazz) {
            this.clazz = clazz;
        }

        public void info(String message) {
            System.out.println(String.format("%s - INFO: %s", getPrefix(), message));
        }

        public void debug(String message) {
            System.out.println(String.format("%s - DEBUG: %s", getPrefix(), message));
        }

        public void error(String message) {
            System.out.println(String.format("%s - ERROR: %s", getPrefix(), message));
        }

        public String getDebugName() {
            return clazz.getSimpleName();
        }

        public String getPrefix() {
            return String.format("[%s]", getDebugName());
        }
}
