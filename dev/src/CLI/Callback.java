package CLI;

public abstract class Callback {
    String command;
    public abstract void call();

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
