package es.narutocraft.narutocraftcore.annotations;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class RegisterExecutor {

    public RegisterExecutor() {
        execute(new Reflections("es.narutocraft.narutocraftcore"));
    }

    private void execute(Reflections reflections) {
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Register.class)) {
            Object obj = null;
            try {
                obj = clazz.getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (obj instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) obj, NarutoCraftCore.getInstance());
            }
        }

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(CommandAlias.class)) {

            Object obj = null;
            try {
                obj = clazz.getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                e.printStackTrace();
                Bukkit.getLogger().info(clazz.getSimpleName());
            }

            NarutoCraftCore.getCmdManager().registerCommand((BaseCommand) obj);
        }
    }
}