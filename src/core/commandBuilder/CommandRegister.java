package core.commandBuilder;


import core.commandBuilder.Commands.Dev.Dev_SpawnZombie;

public class CommandRegister {


    public CommandRegister() {
        registerCommands();
    }

    public void registerCommands(){
        new CMD("Dev")
                .RegisterSubCommand("SpawnZombie", new Dev_SpawnZombie()).RegisterCommand();
    }



}
