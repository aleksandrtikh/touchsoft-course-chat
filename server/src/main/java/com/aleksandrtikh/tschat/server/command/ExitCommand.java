package com.aleksandrtikh.tschat.server.command;


import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.service.UserService;

public class ExitCommand implements Command {
    private final User user;
    private final UserService service = new UserService();

    public void execute() {
            if (service.isUserInChat(user)) {
                new LeaveCommand(user).execute();
            }
            service.unfreeUser(user);
            service.unregisterUser(user);
    }


    public ExitCommand(User user) {
        this.user = user;
        }
}
