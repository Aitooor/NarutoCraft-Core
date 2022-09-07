package es.narutocraft.narutocraftcore.objects.freeze;

public class FreezeHandler {

    public static void disable() {
        for (Freeze freeze : Freeze.getFreezes().values()) {
            freeze.unFreezePlayer(false);
        }
    }
}
